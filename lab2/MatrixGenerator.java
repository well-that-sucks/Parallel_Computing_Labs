import java.util.Random;

class MatrixGenerator {
    public static Matrix generate(int dimY, int dimX, int borderLeft, int borderRight, boolean roundResults) {
        final long borderShifted = Math.abs((long) borderLeft) + Math.abs((long) borderRight);
        double[] arr = new Random().doubles(dimX * dimY).map(elem -> {
            if (roundResults) {
                return (int) (elem * borderShifted - Math.abs(borderLeft));
            }
            return elem * borderShifted - Math.abs(borderLeft);
        }).toArray();

        double[][] matrixValues = new double[dimY][];
        for (int i = 0; i < dimY; ++i) {
            matrixValues[i] = new double[dimX];
            for (int j = 0; j < dimX; ++j) {
                matrixValues[i][j] = arr[i * dimY + j];
            }
        }
        return new Matrix(matrixValues);
    }
}
