import java.util.Random;

class MatrixGenerator {
    public static Matrix generate(int dimY, int dimX, int borderLeft, int borderRight, boolean roundResults) {
        Matrix newMatrix = new Matrix(dimY, dimX);

        for (int i = 0; i < dimY; ++i) {
            for (int j = 0; j < dimX; ++j) {
                double t = new Random().nextDouble(borderLeft, borderRight);
                if (roundResults) {
                    t = Math.floor(t);
                }
                newMatrix.setElement(t, i, j);
            }
        }
        return newMatrix;
    }
}
