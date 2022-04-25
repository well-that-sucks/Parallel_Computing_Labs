public class AtomicDouble {
    private double value;

    public AtomicDouble() {
        this.value = 0;
    }

    public AtomicDouble(double value) {
        this.value = value;
    }

    public synchronized double get() {
        return this.value;
    }

    public synchronized void set(double newValue) {
        this.value = newValue;
    }

    public synchronized void add(double offset) {
        this.value += offset;
    }
}