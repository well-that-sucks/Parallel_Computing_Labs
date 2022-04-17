import java.util.ArrayList;
import java.util.stream.IntStream;

class Matrix {
    private int dimX;
    private int dimY;
    private ArrayList<ArrayList<Double>> elements;
    private ArrayList<ArrayList<Double>> elementsTransposed;

    public Matrix(ArrayList<ArrayList<Double>> elements) {
        // Add check whether 2nd dimension size is consistent
        this.dimY = elements.size();
        this.dimX = elements.get(0).size();
        this.elements = new ArrayList<ArrayList<Double>>();
        IntStream.range(0, this.dimY).forEach(i -> {
            ArrayList<Double> row = new ArrayList<Double>();
            row.addAll(elements.get(i));
            this.elements.add(row);
        });
        makeTransposedMatrix();
    }

    private void makeTransposedMatrix() {
        ArrayList<ArrayList<Double>> telements = new ArrayList<ArrayList<Double>>();
        IntStream.range(0, this.dimX).forEach(j -> {
            ArrayList<Double> row = new ArrayList<Double>();
            IntStream.range(0, this.dimY).forEach(i -> row.add(this.elements.get(i).get(j)));
            telements.add(row);
        });
        this.elementsTransposed = telements;
    }

    public ArrayList<ArrayList<Double>> getTransposedMatrix() {
        return this.elementsTransposed;
    }

    public int getDimX() {
        return this.dimX;
    }

    public int getDimY() {
        return this.dimY;
    }

    public ArrayList<Double> getRow(int rowIndex, boolean performCopying) {
        if (!performCopying) {
            return this.elements.get(rowIndex);
        }
        ArrayList<Double> returnRow = new ArrayList<Double>();
        returnRow.addAll(this.elements.get(rowIndex));
        return returnRow;
    }

    public ArrayList<Double> getColumn(int columnIndex, boolean performCopying) {
        if (!performCopying) {
            return this.elementsTransposed.get(columnIndex);
        }
        ArrayList<Double> returnCol = new ArrayList<Double>();
        returnCol.addAll(this.elementsTransposed.get(columnIndex));
        return returnCol;
    }

    public void print() {
        System.out.println("Resulting matrix:");
        this.elements.forEach(arr -> {
            arr.forEach(element -> System.out.print(element + " "));
            System.out.println();
        });
    }
}
