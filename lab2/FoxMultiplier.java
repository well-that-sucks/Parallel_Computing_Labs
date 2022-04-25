import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class FoxMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        /*AtomicDouble[][] values = new AtomicDouble[matrix1.getDimY()][];
        for (int i = 0; i < matrix1.getDimY(); ++i) {
            values[i] = new AtomicDouble[matrix2.getDimX()];
            for (int j = 0; j < matrix2.getDimX(); ++j) {
                values[i][j] = new AtomicDouble();
            }
        }*/
        double[][] values = new double[matrix1.getDimY()][];
        for (int i = 0; i < matrix1.getDimY(); ++i) {
            values[i] = new double[matrix2.getDimX()];
            for (int j = 0; j < matrix2.getDimX(); ++j) {
                values[i][j] = 0;
            }
        }


        for (int k = 0; k < matrix1.getDimY(); ++k) {
            CountDownLatch latch = new CountDownLatch(matrix1.getDimY());
            for (int i = 0; i < matrix1.getDimY(); ++i) {
                double[] rowRM = matrix2.getRow((i + k) % matrix2.getDimY(), false);
                double elemLM = matrix1.getRow(i, false)[(i + k) % matrix1.getDimY()];
                int index = i;
                pool.submit(() -> {
                    for (int j = 0; j < rowRM.length; ++j) {
                        values[index][j] += rowRM[j] * elemLM;
                    }
                    latch.countDown();
                });
                //pool.submit(new FoxWorker(values, matrix2.getRow((i + k) % matrix2.getDimY(), false),
                        //matrix1.getRow(i, false)[(i + k) % matrix1.getDimY()], i));
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(new Matrix(values, matrix1.getDimY(), matrix2.getDimX()), duration);
    }
}
