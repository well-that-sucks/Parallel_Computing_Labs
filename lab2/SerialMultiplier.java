class SerialMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();

        double[][] matrixValues = new double[matrix1.getDimY()][];
        for (int i = 0; i < matrix1.getDimY(); ++i) {
            matrixValues[i] = new double[matrix2.getDimX()];
            for (int j = 0; j < matrix2.getDimX(); ++j) {
                double sum = 0;
                double[] row = matrix1.getRow(i, false);
                double[] col = matrix2.getColumn(j, false);
                for (int k = 0; k < matrix1.getDimX(); ++k) {
                    sum += row[k] * col[k];
                }
                matrixValues[i][j] = sum;
            }
        }

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(new Matrix(matrixValues), duration);
    }
}
