public class TriangularSquare {

    public static void main(String[] args) {

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

        Number myNumber = new Number();

        myNumber.number = 6;

        if(myNumber.isTriangular() ) {
            System.out.println(myNumber.number + " is a triangular number");
        } 
        else {
            System.out.println(myNumber.number + " is not a triangular number");
        }

        if(myNumber.isSquare()) {
            System.out.println(myNumber.number + " is a square number");
        }
        else {
            System.out.println(myNumber.number + " is not a square number");
        }
    }

}
