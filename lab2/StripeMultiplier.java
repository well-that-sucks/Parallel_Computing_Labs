import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

class StripeMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Future<StripeWorkerResult>> fres = new ArrayList<Future<StripeWorkerResult>>();

        IntStream.range(0, matrix2.getDimX())
                .forEach(idx -> fres.add(pool.submit(new StripeWorker(matrix1, matrix2, idx))));

        double[][] values = new double[matrix1.getDimY()][];
        IntStream.range(0, matrix1.getDimY()).forEach(idx -> values[idx] = new double[matrix2.getDimX()]);

        ArrayList<StripeWorkerResult> swResults = new ArrayList<StripeWorkerResult>();
        fres.forEach(future -> {
            try {
                swResults.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println(swResults.size());

        for (StripeWorkerResult swres : swResults) {
            int offset = swres.getOffset();
            ArrayList<Double> tvalues = swres.getValues(false);
            for (int i = 0; i < matrix1.getDimY(); ++i) {
                values[i][(i + offset) % matrix2.getDimX()] = tvalues.get(i);
            }
        }

        pool.shutdown();

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(new Matrix(values), duration);
    }
}
