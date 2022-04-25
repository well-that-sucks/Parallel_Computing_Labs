import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class StripeMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Matrix result = new Matrix(matrix1.getDimY(), matrix2.getDimX());

        IntStream.range(0, matrix2.getDimX())
                .forEach(idx -> pool.submit(new StripeWorker(result, matrix1, matrix2, idx)));

        pool.shutdown();

        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(result, duration);
    }
}
