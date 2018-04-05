package com.msushanth.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {

    boolean runOnce = true;

    //0 is yellow, 1 is red
    int activePlayer = 0;

    boolean gameCompleted = false;

    //How many times each player has won
    int redWinnings = 0;
    int yellowWinnings = 0;

    //2 means unplayed
    int[] board = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};




    public void dropIn(View view) {

        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString()) - 1;

        if(board[tappedCounter] == 2 && !gameCompleted) {
            counter.setTranslationY(-1200f);
            board[tappedCounter] = activePlayer;

            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.yellow);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.red);
                activePlayer = 0;
            }

            counter.animate().translationYBy(1200f).rotation(180f).setDuration(200);

            //true if game has completed without a winner
            boolean noWinner = true;
            for (int boardLocations : board) {
                if (boardLocations == 2) {
                    noWinner = false;
                }
            }
            if (!checkWinner() && noWinner) {
                displayWinner("Tie game!");
                gameCompleted = true;
            }

        }

    }



    public boolean checkWinner() {
        for (int[] winningPosition : winningPositions) {
            if ((board[winningPosition[0]] == board[winningPosition[1]]) &&
                    (board[winningPosition[1]] == board[winningPosition[2]]) &&
                    (board[winningPosition[0]] != 2)) {
                String player = "";
                if (activePlayer == 0) {
                    player = "red";
                    redWinnings++;
                } else {
                    player = "yellow";
                    yellowWinnings++;
                }
                String message = "Player " + player + " has won!";
                displayWinner(message);
                gameCompleted = true;
                changeWinnings();
                return true;
            }
        }
        return false;
    }



    public void displayWinner(String message) {
        TextView winnerText = (TextView) findViewById(R.id.winnerMessage);
        winnerText.setText(message);

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.VISIBLE);

        Button button = (Button) findViewById(R.id.resetButton);
        button.setVisibility(View.GONE);
    }



    public void changeWinnings() {
        TextView redScore = (TextView) findViewById(R.id.redScore);
        redScore.setText("Red: " + redWinnings);

        TextView yellowScore = (TextView) findViewById(R.id.yellowScore);
        yellowScore.setText("Yellow: " + yellowWinnings);
    }




    public void playAgain(View view) {
        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.GONE);

        Button button = (Button) findViewById(R.id.resetButton);
        button.setVisibility(View.VISIBLE);

        for(int x=0; x < board.length; x++) {
            board[x] = 2;
        }

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i < gridLayout.getChildCount(); i++) {
            ((ImageView) gridLayout.getChildAt(i)).setImageResource(0);
        }

        activePlayer = 0;

        gameCompleted = false;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layout = (LinearLayout) findViewById(R.id.playAgainLayout);
        layout.setVisibility(View.GONE);
    }



    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(runOnce) {
            GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
            int boardHeight = gridLayout.getHeight();
            int boardWidth = gridLayout.getWidth();
            updatePieceLocations(boardHeight, boardWidth);
        }
    }





    public void updatePieceLocations(int boardHeight, int boardWidth) {
        runOnce = false;

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        float heightOfBoard = (float) (boardWidth);
        float widthOfBoard = (float) (boardWidth);

        RelativeLayout.LayoutParams gridLayoutParams =new RelativeLayout.LayoutParams(gridLayout.getLayoutParams());
        gridLayoutParams.height = (int) heightOfBoard;//convertDpToPixel(heightOfBoard);
        gridLayoutParams.width = (int) widthOfBoard; //convertDpToPixel(widthOfBoard);
        gridLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        gridLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        gridLayout.setLayoutParams(gridLayoutParams);


        ImageView [] boardPieces = new ImageView[gridLayout.getChildCount()];
        for(int i=0; i < gridLayout.getChildCount(); i++) {
            boardPieces[i] = (ImageView) gridLayout.getChildAt(i);
        }

        for(int i=0; i < gridLayout.getChildCount(); i++) {
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(boardPieces[i].getLayoutParams());
            lp.setMargins(0, 0, 0, 0);
            boardPieces[i].setLayoutParams(lp);
        }

        float gaps = (heightOfBoard-convertDpToPixel(270))/5;

        GridLayout.LayoutParams lp = new GridLayout.LayoutParams(boardPieces[0].getLayoutParams());
        lp.setMargins((int) (gaps/2), (int) (gaps/2), (int) (gaps), (int) (gaps));
        boardPieces[0].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[1].getLayoutParams());
        lp.setMargins((int) (gaps), (int) (gaps/2), (int) (gaps), (int) (gaps));
        boardPieces[1].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[2].getLayoutParams());
        lp.setMargins((int) (gaps), (int) (gaps/2), (int) (gaps/2), (int) (gaps));
        boardPieces[2].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[3].getLayoutParams());
        lp.setMargins((int) (gaps/2), (int) (gaps), (int) (gaps), (int) (gaps));
        boardPieces[3].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[4].getLayoutParams());
        lp.setMargins((int) (gaps), (int) (gaps), (int) (gaps), (int) (gaps));
        boardPieces[4].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[5].getLayoutParams());
        lp.setMargins((int) (gaps), (int) (gaps), (int) (gaps/2), (int) (gaps));
        boardPieces[5].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[6].getLayoutParams());
        lp.setMargins((int) (gaps/2), (int) (gaps), (int) (gaps), (int) (gaps/2));
        boardPieces[6].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[7].getLayoutParams());
        lp.setMargins((int) (gaps), (int) (gaps), (int) (gaps), (int) (gaps/2));
        boardPieces[7].setLayoutParams(lp);

        lp = new GridLayout.LayoutParams(boardPieces[8].getLayoutParams());
        lp.setMargins((int) (gaps), (int) (gaps), (int) (gaps/2), (int) (gaps/2));
        boardPieces[8].setLayoutParams(lp);
    }



    public int convertDpToPixel(float dp){
        return (int) ((dp * getResources().getDisplayMetrics().density) + 0.5);
    }

}
