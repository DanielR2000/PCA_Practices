import practicas_pca.TesterRun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class AlgoritmoBucketSortPart2 implements TesterRun{
    @Override
    public ArrayList<Integer> bucketSort(List<Integer> numbers, int num_threads) {
        /* CODIGO A COMPLETAR POR EL ALUMNO */

        ArrayList<ArrayList<Integer>> numberList = new ArrayList<>(); //ArrayList de Arraylist de Integer
        ArrayList<Future<ArrayList<Integer>>> futures = new ArrayList<>();

        for (int i = 0; i< num_threads; i++){
            numberList.add(new ArrayList<>()); //Settear lista de threads
        }

        for(Integer num : numbers){
            long position = (long)num - Integer.MIN_VALUE;
            long totalRange = (long)Integer.MAX_VALUE - (long)Integer.MIN_VALUE;
            long bucketIndex = position * num_threads / totalRange;
            int index = (int)bucketIndex;
            //int index = (int)Math.max(0, Math.min(bucketIndex, num_threads - 1)); Usar en caso de outOfBounds por problemas de desbordamiento (no debería ser necesario)
            numberList.get(index).add(num);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(num_threads);

        for (final ArrayList<Integer> bucket : numberList) {
            Callable<ArrayList<Integer>> tarea = () -> {
                Collections.sort(bucket);
                return bucket;
            };
            Future<ArrayList<Integer>> future = executorService.submit(tarea);
            futures.add(future);
        }

        ArrayList<Integer> result = new ArrayList<>();

        for (Future<ArrayList<Integer>> f : futures){
            try {
              result.addAll(f.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executorService.shutdown();
        return result;
    }
}
