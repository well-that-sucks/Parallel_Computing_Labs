import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

class StripeWorkerResult {

    private double value;
    private final int posX;
    private final int posY;

    public StripeWorkerResult(double value, int i, int j) {
        this.value = value;
        this.posX = j;
        this.posY = i;
    }

    public StripeWorkerResult(int i, int j) {
        this.posX = j;
        this.posY = i;
        this.value = 0;
    }

    public int getPosX() {
        return this.posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}

class StripeWorker implements Callable<StripeWorkerResult> {

    private final ArrayList<Double> row;
    private final ArrayList<Double> col;
    private StripeWorkerResult res;

    public StripeWorker(Matrix matrix1, Matrix matrix2, int i, int j) {
        this.row = matrix1.getRow(i);
        this.col = matrix2.getColumn(j);
        this.res = new StripeWorkerResult(i, j);
    }

    @Override
    public StripeWorkerResult call() {
        double val = IntStream.range(0, row.size()).mapToObj(idx -> (row.get(idx) * col.get(idx)))
                .reduce((a, b) -> a + b).get();
        res.setValue(val);
        return this.res;
    }
}
