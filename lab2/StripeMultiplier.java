import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

class StripeMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newCachedThreadPool();
        // ExecutorService pool = Executors.newFixedThreadPool(1);
        ArrayList<Future<StripeWorkerResult>> fres = new ArrayList<Future<StripeWorkerResult>>();

        IntStream.range(0, matrix2.getDimX())
                .forEach(idx -> fres.add(pool.submit(new StripeWorker(matrix1, matrix2, idx))));

        Double[][] tempValues = new Double[matrix1.getDimY()][];
        IntStream.range(0, matrix1.getDimY()).forEach(idx -> tempValues[idx] = new Double[matrix2.getDimX()]);

        fres.forEach(future -> {
            try {
                StripeWorkerResult swres = future.get();
                swres.getValues(false).stream().forEach(value -> {
                    IntStream.range(0, matrix1.getDimY()).forEach(idx -> {
                        tempValues[idx][(idx + swres.getOffset()) % matrix2.getDimX()] = value;
                    });
                });
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        ArrayList<ArrayList<Double>> matrixValues = new ArrayList<ArrayList<Double>>(
                Arrays.asList(tempValues).stream().map(arr -> new ArrayList<Double>(Arrays.asList(arr))).toList());
        pool.shutdown();

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(new Matrix(matrixValues), duration);
    }
}
