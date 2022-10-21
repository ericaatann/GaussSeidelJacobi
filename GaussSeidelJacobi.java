import java.util.Scanner;
import java.io.*;

public class GaussSeidelJacobi {

    // Jacobi Method
    static void jacobi(double arr[][], double x[], double error) {
        int maxIterations = 50;
        double maxerror = 0;
        int n = x.length;
        double Tempx[] = new double[n];
        for (int i = 0; i < n; i++) {
            Tempx[i] = 0;
        }

        // repeat until max iterations have been reached
        for (int y = 1; y <= maxIterations; y++) {
            maxerror = 0.0;
            for (int i = 0; i < n; i++) {
                double sum = 0.0;
                // calculating the sum
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        sum = sum + arr[i][j] * x[j];
                    }
                    // x1 = (b1-(a12*x2+a13*x3+...+a1n*xn))/a11 = (b1-sum)/a11
                    double temp = (arr[i][n] - sum) / arr[i][i];
                    double relativeError = Math.abs((x[i] - temp / temp));

                    if (relativeError > maxerror) {
                        maxerror = relativeError;
                    }
                    Tempx[i] = temp;
                }
            }
            // update the values of x from the Tempx vector
            for (int i = 0; i < n; i++) {
                x[i] = Tempx[i];
            }
            // print solution
            System.out.print("\nI" + y + ": [");

            for (int i = 0; i < n; i++) {
                System.out.print(String.format("%.4f", x[i]) + " ");
            }
            System.out.print("\b]");

            // if it converges before the max iterations, then just jump to here
            if (maxerror < error) {
                System.out.println("\nConverged");
                return;
            }
        }
        System.out.println("It does not converge for th e max iteration at 50 : error was not reached");
    }

    // Gauss Seidel Method
    static void gaussSeidel(double arr[][], double x[], double error) {
        int maxIterations = 50;
        double maxerror = 0;
        int n = x.length;

        // repeat until max iterations have been reached
        for (int k = 1; k <= maxIterations; k++) {
            maxerror = 0.0;
            for (int i = 0; i < n; i++) {
                double sum = 0.0;
                // calculates the sum for the row
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        sum = sum + arr[i][j] * x[j];
                    }
                }
                // x1 = (b1-(a12*x2+a13*x3+...+a1n*xn))/a11 = (b1-sum)/a11
                double temp = (arr[i][n] - sum) / arr[i][i];
                double relativeError = Math.abs((x[i] - temp / temp));
                if (relativeError > maxerror) {
                    maxerror = relativeError;
                }
                x[i] = temp;
            }
            // print solution
            System.out.print("\nI" + k + ": [");
            for (int i = 0; i < n; i++) {
                System.out.print(String.format("%.4f", x[i]) + " ");
            }
            System.out.print("\b]");

            // if it converges before the max iterations, then just jump to here
            if (maxerror < error) {
                System.out.println("\nConverged");
                return;
            }
        }
        System.out.println("It does not converge for th e max iteration at 50 : error was not reached");
    }

    public static void main(String[] args) {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of linear equations: ");
        n = sc.nextInt();

        if (n < 2 || n > 10) {
            System.out.println("enter n between 2 to 10");
            return;
        }
        double arr[][] = new double[n][n + 1];
        double x[] = new double[n];
        System.out.println("1. Enter the coefficients: ");
        System.out.println("2. Enter the file name to get the coefficients: ");
        System.out.println("Select an option: ");
        int option = sc.nextInt();
        switch (option) {
            case 1:
                System.out.println("Enter values for each row: ");
                System.out.println("Enter each row values: ");
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j <= n; j++) {
                        arr[i][j] = sc.nextInt();
                    }
                }
                break;
            case 2:
                System.out.println("Enter the file name: ");
                try {
                    sc.nextLine();
                    String filename;
                    filename = sc.nextLine();
                    File file = new File(filename);
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    int i = 0;
                    while ((line = br.readLine()) != null) {
                        String[] val = line.split(" ");
                        for (int j = 0; j < n + 1; j++) {
                            arr[i][j] = Double.parseDouble(val[j]);
                        }
                        i++;
                    }
                } catch (Exception e) {
                    System.out.println("File not found!");
                    return;
                }
                break;
            default:
                System.out.println("Select an option: ");
                return;
        }
        System.out.println("Enter the desired stopping error: ");
        double error = sc.nextDouble();
        System.out.println("Enter the starting solutions for the system of linear equations: ");
        for (int i = 0; i < n; i++) {
            System.out.print("\nX[ " + (i + 1) + "]: ");
            x[i] = sc.nextDouble();
        }

        // Printing matrices
        System.out.println("Displaying input from User: ");
        System.out.print("Matrix A: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println(arr[i][j] + "");
            }
            System.out.println();
        }
        System.out.println("\nMatrix B: ");
        for (int i = 0; i < n; i++) {
            System.out.println(arr[i][n]);
        }
        System.out.println("\nStopping error: " + error);
        System.out.println("Starting solution: ");
        System.out.print("\n[");

        // storing values
        double temp[] = new double[n];
        for (int i = 0; i < n; i++) {
            temp[i] = x[i];
        }
        System.out.println("-----Jacobi Method-----");
        System.out.println("Displaying the solutions of each iteration made using the Jacobi Method: ");
        jacobi(arr, temp, error);

        System.out.println("-----Gauss Seidel Method-----");
        System.out.println("Displaying the solutions of each iteration made using the Gauss Seidel Method: ");
        for (int i = 0; i < n; i++) {
            temp[i] = x[i];
        }
        gaussSeidel(arr, temp, error);
    }
}

