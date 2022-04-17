import java.util.ArrayList;
import java.util.Collections;
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
        IntStream.range(0, this.dimY).forEach(idx -> this.elements.add(new ArrayList<Double>(this.dimX)));
        try {
            Collections.copy(this.elements, elements);
            makeTransposedMatrix();
        } catch (IndexOutOfBoundsException | UnsupportedOperationException e) {
            e.printStackTrace();
        }

    }

    private void makeTransposedMatrix() {
        ArrayList<ArrayList<Double>> telements = new ArrayList<ArrayList<Double>>();
        for (int j = 0; j < this.dimX; ++j) {
            ArrayList<Double> tRow = new ArrayList<Double>();
            for (int i = 0; i < this.dimY; ++i) {
                tRow.add(this.elements.get(i).get(j));
            }
            telements.add(tRow);
        }
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

    public ArrayList<Double> getRow(int rowIndex) {
        ArrayList<Double> returnRow = new ArrayList<Double>();
        returnRow.addAll(this.elements.get(rowIndex));
        return returnRow;
    }

    public ArrayList<Double> getColumn(int columnIndex) {
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
