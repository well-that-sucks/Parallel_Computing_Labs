import java.util.ArrayList;
import java.util.concurrent.Callable;

class FoxWorkerResult {
    private final ArrayList<Double> values;
    private final int index;

    public FoxWorkerResult(ArrayList<Double> values, int index) {
        this.values = new ArrayList<Double>();
        this.values.addAll(values);
        this.index = index;
    }

    public FoxWorkerResult(int index) {
        this.values = new ArrayList<Double>();
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public ArrayList<Double> getValues(boolean performCopying) {
        if (!performCopying) {
            return this.values;
        }
        ArrayList<Double> valuesCopied = new ArrayList<Double>();
        valuesCopied.addAll(this.values);
        return valuesCopied;
    }

    public FoxWorkerResult setValues(ArrayList<Double> values) {
        this.values.clear();
        this.values.addAll(values);
        return this;
    }
}

class FoxWorker implements Callable<FoxWorkerResult> {
    private final double[] rowRM;
    private final double elemLM;
    private FoxWorkerResult res;

    public FoxWorker(double[] rowRM, double element, int index) {
        this.rowRM = rowRM;
        this.elemLM = element;
        this.res = new FoxWorkerResult(index);
    }

    @Override
    public FoxWorkerResult call() {
        ArrayList<Double> values = new ArrayList<Double>();
        for (int i = 0; i < this.rowRM.length; ++i) {
            values.add(this.rowRM[i] * this.elemLM);
        }
        return this.res.setValues(values);
    }
}
