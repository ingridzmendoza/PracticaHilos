import java.util.ArrayList;
import java.util.Scanner;

public class FactorialThread implements Runnable {

    // método para calcular el factorial
    public static long calcFactorial(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("El cálculo de un factorial está restringido a números positivos.");
        } else if (num == 0) {
            return 1;
        }

        long factorial = 1;

        for (int i = 1; i <= num; i++) {
            factorial *= i;
        }

        return factorial;
    }

    //constructor para el factorial a calcular
    private int num;
    public FactorialThread(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        long factorial = calcFactorial(num);
        System.out.println(num + "! = " + factorial);
    }

    public static void main(String[] args) {

        // inputs del usuario almacenados en un arraylist
        ArrayList<Integer> listaNum = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de valores (n): ");
        int n = scanner.nextInt();

        System.out.println("Ingrese los valores");
        for (int i = 1; i <= n; i++) {
            System.out.print("\tNúmero " + i + ": ");
            int numero = scanner.nextInt();
            listaNum.add(numero);
        }

        System.out.println("Números ingresados: " + listaNum);

        // calcular factorial de cada número en hilos separados
        System.out.println("\nFactoriales:");
        for (int numero : listaNum) {
            Thread thread = new Thread(new FactorialThread(numero));
            thread.start();
        }
    }
}


