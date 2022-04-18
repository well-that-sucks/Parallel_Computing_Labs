import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class FoxMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        Matrix result = new Matrix(matrix1.getDimY(), matrix2.getDimX());

        for (int k = 0; k < matrix1.getDimY(); ++k) {
            for (int i = 0; i < matrix1.getDimY(); ++i) {
                pool.submit(new FoxWorker(result, matrix2.getRow((i + k) % matrix2.getDimY(), false),
                        matrix1.getRow(i, false)[(i + k) % matrix1.getDimY()], i));
            }
        }

        pool.shutdown();

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(result, duration);
    }
}
