import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // 1. Leer el archivo
        String filename = "/home/daniel/IdeaProjects/Practica_1_PCA/numeros_aleatorios_25000000.txt";  // Cambia esto si el archivo está en `src/`
        List<Integer> numbers = readNumbersFromFile(filename);

        if (numbers.isEmpty()) {
            System.out.println("El archivo está vacío o no se pudo leer.");
            return;
        }

        int numThreads = 10;  // Ajusta el número de hilos según el hardware

        // 2. Medir tiempo de Bucket Sort
        AlgoritmoBucketSortPart2 sorter = new AlgoritmoBucketSortPart2();
        long startBucketSort = System.nanoTime();
        ArrayList<Integer> sortedBucket = sorter.bucketSort(new ArrayList<>(numbers), numThreads);
        long endBucketSort = System.nanoTime();

        // 3. Medir tiempo de Collections.sort()
        long startCollectionSort = System.nanoTime();
        ArrayList<Integer> sortedCollection = new ArrayList<>(numbers);
        Collections.sort(sortedCollection);
        long endCollectionSort = System.nanoTime();

        // 4. Comparar tiempos
        long timeBucketSort = endBucketSort - startBucketSort;
        long timeCollectionSort = endCollectionSort - startCollectionSort;
        double improvement = (double) timeCollectionSort / timeBucketSort;

        // 5. Verificar si las listas están ordenadas correctamente
        boolean isCorrect = sortedBucket.equals(sortedCollection);

        // 6. Mostrar resultados
        System.out.println("Tiempo total empleado (BucketSort): " + (timeBucketSort / 1e6) + " ms");
        System.out.println("Tiempo total empleado (Collections.sort): " + (timeCollectionSort / 1e6) + " ms");
        System.out.println("Mejora tiempo paralelo: " + String.format("%.2fx", improvement));
        System.out.println("La ordenación se realizó correctamente: " + isCorrect);
    }

    // Método para leer números desde un archivo
    public static List<Integer> readNumbersFromFile(String filename) {
        List<Integer> numbers = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");  // Dividir por espacios o tabulaciones
                for (String part : parts) {
                    try {
                        int num = Integer.parseInt(part);
                        numbers.add(num);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al leer valor: '" + part + "'");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error al abrir el archivo: " + e.getMessage());
        }
        return numbers;
    }
}


