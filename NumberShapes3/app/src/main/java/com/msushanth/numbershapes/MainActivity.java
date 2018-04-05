package com.msushanth.numbershapes;

import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    class Number {
        int number;

        public boolean isTriangular() {
            int x = 1;
            int triangularNumber = 1;
            while(triangularNumber < number) {
                x++;
                triangularNumber = triangularNumber + x;
            }
            if(triangularNumber == number) {
                return true;
            }
            else {
                return false;
            }
        }

        public boolean isSquare() {
            double squareRoot = Math.sqrt(number);
            if(squareRoot == Math.floor(squareRoot)) {
                return true;
            }
            else {
                return false;
            }
        }
    }




    public void testNumber(View view) {
        EditText usersNumber = (EditText) findViewById(R.id.usersNumber);

        if(usersNumber.getText().toString().isEmpty()) {
            showToast("Please enter a number");
            return;
        }


        Number myNumber = new Number();

        myNumber.number = Integer.parseInt(usersNumber.getText().toString());

        String message = "";

        if(myNumber.isSquare() && myNumber.isTriangular()) {
            message = "The number is both square and triangular.";
        }
        else if(myNumber.isSquare() && !myNumber.isTriangular()) {
            message = "The number is only square.";
        }
        else if(!myNumber.isSquare() && myNumber.isTriangular()) {
            message = "The number is only triangular";
        }
        else {
            message = "The number is neither square nor triangular";
        }

        showToast(message);
    }




    public void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        if( v != null) v.setGravity(Gravity.CENTER);
        toast.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
