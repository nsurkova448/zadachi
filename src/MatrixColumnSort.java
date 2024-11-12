import java.util.Scanner;

public class MatrixColumnSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ввод размеров матрицы
        System.out.print("Введите количество строк: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество столбцов: ");
        int cols = scanner.nextInt();

        // Создание и заполнение матрицы
        int[][] matrix = new int[rows][cols];
        System.out.println("Введите элементы матрицы:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        // Вычисление характеристик столбцов
        double[] characteristics = new double[cols];
        for (int j = 0; j < cols; j++) {
            double sum = 0;
            for (int i = 0; i < rows; i++) {
                sum += Math.abs(matrix[i][j]);
            }
            characteristics[j] = sum;
        }

        // Сортировка столбцов по убыванию характеристик
        for (int i = 0; i < cols - 1; i++) {
            for (int j = 0; j < cols - i - 1; j++) {
                if (characteristics[j] < characteristics[j + 1]) {
                    // Обмен характеристик
                    double temp = characteristics[j];
                    characteristics[j] = characteristics[j + 1];
                    characteristics[j + 1] = temp;

                    // Обмен столбцов
                    for (int k = 0; k < rows; k++) {
                        int tempElement = matrix[k][j];
                        matrix[k][j] = matrix[k][j + 1];
                        matrix[k][j + 1] = tempElement;
                    }
                }
            }
        }

        // Вывод отсортированной матрицы
        System.out.println("Отсортированная матрица:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}