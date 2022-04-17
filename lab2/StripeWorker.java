import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

class StripeWorkerResult {
    private ArrayList<Double> values;
    private final int offset;

    public StripeWorkerResult(ArrayList<Double> values, int offset) {
        this.values = new ArrayList<Double>();
        this.values.addAll(values);
        this.offset = offset;
    }

    public StripeWorkerResult(int offset) {
        this.values = new ArrayList<Double>();
        this.offset = offset;
    }

    public int getOffset() {
        return this.offset;
    }

    public ArrayList<Double> getValues(boolean performCopying) {
        if (!performCopying) {
            return this.values;
        }
        ArrayList<Double> valuesCopied = new ArrayList<Double>();
        valuesCopied.addAll(this.values);
        return valuesCopied;
    }

    public StripeWorkerResult setValues(ArrayList<Double> values) {
        this.values.clear();
        this.values.addAll(values);
        return this;
    }

}

class StripeWorker implements Callable<StripeWorkerResult> {
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int offset;
    private StripeWorkerResult res;

    public StripeWorker(Matrix matrix1, Matrix matrix2, int offset) {
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.offset = offset;
        this.res = new StripeWorkerResult(offset);
    }

    @Override
    public StripeWorkerResult call() {
        if (this.offset > 100) {
            return this.res;
        }
        ArrayList<Double> values = new ArrayList<Double>();
        // In favour of performance there is used reference passing and assigning
        // Change getRow() or getColumn() to corresponding getRowCopy() or
        // getColumnCopy() to ensure everything is safe
        IntStream.range(0, matrix1.getDimY()).forEach(i -> {
            values.add(IntStream.range(0, matrix1.getDimX())
                    .mapToObj(idx -> (matrix1.getRow(i, false).get(idx)
                            * matrix2.getColumn((i + offset) % matrix2.getDimX(), false).get(idx)))
                    .reduce((a, b) -> a + b)
                    .get());
        });
        
        return this.res.setValues(values);
    }
}
