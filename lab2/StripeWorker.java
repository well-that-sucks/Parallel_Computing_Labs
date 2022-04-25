class StripeWorker implements Runnable {
    private final Matrix result;
    private final Matrix matrix1;
    private final Matrix matrix2;
    private final int offset;

    public StripeWorker(Matrix result, Matrix matrix1, Matrix matrix2, int offset) {
        this.result = result;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.offset = offset;
    }

    @Override
    public void run() {
        for (int i = 0; i < matrix1.getDimY(); ++i) {
            double sum = 0;
            for (int j = 0; j < matrix1.getDimX(); ++j) {
                sum += matrix1.getRow(i, false)[j] * matrix2.getColumn((i + offset) % matrix2.getDimX(), false)[j];
            }
            result.setElement(sum, i, (i + offset) % matrix2.getDimX());
        }
    }
}
