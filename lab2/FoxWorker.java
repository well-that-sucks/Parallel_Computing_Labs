class FoxWorker implements Runnable {
    private final Matrix result;
    private final double[] rowRM;
    private final double elemLM;
    private final int index;

    public FoxWorker(Matrix result, double[] rowRM, double element, int index) {
        this.result = result;
        this.rowRM = rowRM;
        this.elemLM = element;
        this.index = index;
    }

    @Override
    public void run() {
        for (int j = 0; j < this.rowRM.length; ++j) {
            this.result.setElement(this.result.getElement(this.index, j) + (this.rowRM[j] * this.elemLM), 
            this.index, j);
        }
    }
}
