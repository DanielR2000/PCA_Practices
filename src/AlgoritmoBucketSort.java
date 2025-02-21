import practicas_pca.TesterRun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoritmoBucketSort implements TesterRun{
    @Override
    public ArrayList<Integer> bucketSort(List<Integer> numbers, int num_threads) {
        /* CODIGO A COMPLETAR POR EL ALUMNO */

        // Creación de estructuras para organizar los datos
        ArrayList<Thread> threads = new ArrayList<>(); //Lista de threads
        ArrayList<ArrayList<Integer>> numberList = new ArrayList<>(); //ArrayList de Arraylist de Integer

        //Inicialización de los buckets
        for (int i = 0; i< num_threads; i++){
            numberList.add(new ArrayList<>()); //Settear lista de threads
        }
//-------------------------------------------------------------------------------------------
        //Distribuir los números en los buckets
        for(Integer num : numbers){
            long position = (long)num - Integer.MIN_VALUE; //Convierte el número a un valor positivo para evitar problemas con negativos.
            long totalRange = (long)Integer.MAX_VALUE - (long)Integer.MIN_VALUE; //Calcula el rango total de valores posibles para enteros en Java.
            long bucketIndex = position * num_threads / totalRange;//Calcula en qué bucket debe ir el número.
            int index = (int)bucketIndex;
            //int index = (int)Math.max(0, Math.min(bucketIndex, num_threads - 1)); Usar en caso de outOfBounds por problemas de desbordamiento (no debería ser necesario)
            numberList.get(index).add(num);//Añade el número al bucket correspondiente.
        }
        //Ordenar cada bucket en un hilo separado
        for (final ArrayList<Integer> bucket : numberList) {
            Thread thread = new Thread(() -> Collections.sort(bucket)); //Se crea un hilo (Thread) que ordena el bucket con Collections.sort(bucket).
            threads.add(thread); //Se agrega el hilo a la lista threads.
            thread.start(); //Se inicia el hilo con thread.start();
        }
        //Esperar a que todos los hilos terminen
        for(Thread t : threads){
            try {
                t.join(); //Esperar que cada hilo termine antes de seguir con el código
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //Combinar todos los buckets en una sola lista ordenada
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i< num_threads; i++){
            ArrayList<Integer> aux = numberList.get(i);
            result.addAll(aux);
        }

        return result; //lista final ordenada
    }
}



//Separecion de buckets
//Crear ExecutorService
//for (bucket)
    //crear la tarea
    //mandar ejecutar tarea (submit)
    //Almacenar el futuro
//for para recoger los resultados
//addAll
