import java.util.ArrayList;
import java.util.stream.IntStream;

class SerialMultiplier extends Multiplier {

    @Override
    protected Pair<Matrix, Long> multiply(Matrix matrix1, Matrix matrix2) {
        long startTime = System.nanoTime();

        ArrayList<ArrayList<Double>> matrixValues = new ArrayList<ArrayList<Double>>();
        IntStream.range(0, matrix1.getDimY()).forEach(i -> {
            ArrayList<Double> row = new ArrayList<Double>();
            IntStream.range(0, matrix2.getDimX()).forEach(j -> {
                row.add(IntStream.range(0, matrix1.getDimX())
                        .mapToDouble(idx -> matrix1.getRow(i, false).get(idx) * matrix2.getColumn(j, false).get(idx))
                        .reduce((a, b) -> a + b).getAsDouble());
            });
            matrixValues.add(row);
        });

        long duration = (System.nanoTime() - startTime);

        return new Pair<Matrix, Long>(new Matrix(matrixValues), duration);
    }
}
