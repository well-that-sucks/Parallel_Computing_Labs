import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class FoxMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        ArrayList<Future<FoxWorkerResult>> fres = new ArrayList<Future<FoxWorkerResult>>();

        for (int k = 0; k < matrix1.getDimY(); ++k) {
            for (int i = 0; i < matrix1.getDimY(); ++i) {
                fres.add(pool.submit(new FoxWorker(matrix2.getRow((i + k) % matrix2.getDimY(), false),
                        matrix1.getRow(i, false)[(i + k) % matrix1.getDimY()], i)));
            }
        }

        double[][] values = new double[matrix1.getDimY()][];
        for (int i = 0; i < matrix1.getDimY(); ++i) {
            values[i] = new double[matrix2.getDimX()];
            for (int j = 0; j < matrix2.getDimX(); ++j) {
                values[i][j] = 0;
            }
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);

        ArrayList<FoxWorkerResult> fwResults = new ArrayList<FoxWorkerResult>();
        fres.forEach(future -> {
            try {
                fwResults.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);

        for (FoxWorkerResult fwres : fwResults) {
            int index = fwres.getIndex();
            ArrayList<Double> tvalues = fwres.getValues(false);
            for (int j = 0; j < matrix2.getDimX(); ++j) {
                values[index][j] += tvalues.get(j);
            }
        }
        System.out.println((System.nanoTime() - startTime) / 1000000000.0);

        pool.shutdown();

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(new Matrix(values), duration);
    }
}
