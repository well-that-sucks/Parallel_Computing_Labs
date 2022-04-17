abstract class Multiplier {

    public Pair<Matrix, Long> run(Matrix matrix1, Matrix matrix2) throws DimensionsNotMatchingException {
        if (!checkDimensions(matrix1, matrix2)) {
            throw new DimensionsNotMatchingException("Matricies dimensions have to match each other!");
        }
        return multiply(matrix1, matrix2);
    }

    protected boolean checkDimensions(Matrix matrix1, Matrix matrix2) {
        return (matrix1.getDimX() == matrix2.getDimY());
    }

    abstract protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2);

}
