import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

class MatrixGenerator {
    public static Matrix generate(int dimY, int dimX, int borderLeft, int borderRight, boolean roundResults) {
        final long borderShifted = Math.abs((long) borderLeft) + Math.abs((long) borderRight);
        double[] arr = new Random().doubles(dimX * dimY).map(elem -> {
            if (roundResults) {
                return (int) (elem * borderShifted - Math.abs(borderLeft));
            }
            return elem * borderShifted - Math.abs(borderLeft);
        }).toArray();
        ArrayList<ArrayList<Double>> matrixValues = new ArrayList<ArrayList<Double>>();
        IntStream.range(0, dimY).forEach(i -> {
            ArrayList<Double> row = new ArrayList<Double>();
            IntStream.range(0, dimX).forEach(j -> row.add(arr[i * dimY + j]));
            matrixValues.add(row);
        });
        return new Matrix(matrixValues);
    }
}
