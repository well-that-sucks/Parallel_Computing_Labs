import java.util.Arrays;

class Matrix {
    private int dimX;
    private int dimY;
    private double[][] elements;
    private double[][] elementsTransposed;

    public Matrix(double[][] values) {
        this.dimY = values.length;
        this.dimX = values[0].length;
        this.elements = Arrays.copyOf(values, values.length);
        makeTransposedMatrix();
    }

    private void makeTransposedMatrix() {
        this.elementsTransposed = new double[this.dimX][];
        for (int j = 0; j < this.dimX; ++j) {
            this.elementsTransposed[j] = new double[this.dimY];
            for (int i = 0; i < this.dimY; ++i) {
                this.elementsTransposed[j][i] = this.elements[i][j];
            }
        }
    }

    public double[][] getTransposedMatrix() {
        return this.elementsTransposed;
    }

    public int getDimX() {
        return this.dimX;
    }

    public int getDimY() {
        return this.dimY;
    }

    public double[] getRow(int rowIndex, boolean performCopying) {
        if (!performCopying) {
            return this.elements[rowIndex];
        }
        return Arrays.copyOf(this.elements[rowIndex], this.elements[rowIndex].length);
    }

    public double[] getColumn(int columnIndex, boolean performCopying) {
        if (!performCopying) {
            return this.elementsTransposed[columnIndex];
        }
        return Arrays.copyOf(this.elementsTransposed[columnIndex], this.elementsTransposed[columnIndex].length);
    }

    public void print() {
        System.out.println("Resulting matrix:");
        for (int i = 0; i < this.dimY; ++i) {
            for (int j = 0; j < this.dimX; ++j) {
                System.out.print(this.elements[i][j] + " ");
            }
            System.out.println();
        }
    }
}
