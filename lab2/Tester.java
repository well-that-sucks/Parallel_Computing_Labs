import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

class Tester {
    private final HashMap<String, Multiplier> multipliers;
    private String filename;
    private String result;

    public Tester(HashMap<String, Multiplier> multipliers) {
        this.multipliers = multipliers;
        this.filename = "";
        this.result = "";
    }

    public Tester(HashMap<String, Multiplier> multipliers, String filename) {
        this.multipliers = multipliers;
        this.filename = filename;
        this.result = "";
    }

    public void test() {
        testGivenSize("Small size 1", 250, 250, 250, 250);
        testGivenSize("Small size 2", 500, 500, 500, 500);
        testGivenSize("Medium size 1", 1000, 1000, 1000, 1000);
        testGivenSize("Medium size 2", 3000, 3000, 3000, 3000);
        //testGivenSize("Large size", 5000, 5000, 5000, 5000);
        printResult();
        if (!filename.equals("")) {
            try {
                Files.write(Paths.get(filename), this.result.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void testGivenSize(String testName, int dimY1, int dimX1, int dimY2, int dimX2) {
        Matrix matrix1 = MatrixGenerator.generate(dimY1, dimX1, -1000, 1000, true);
        Matrix matrix2 = MatrixGenerator.generate(dimY2, dimX2, -1000, 1000, true);

        HashMap<String, Long> results = new HashMap<String, Long>();
        this.multipliers.forEach((k, v) -> {
            try {
                results.put(k, v.run(matrix1, matrix2).getValue());
            } catch (DimensionsNotMatchingException e) {
                e.printStackTrace();
            }
        });
        this.result += transformResults(testName, results) + "\n";
    }

    private String transformResults(String testName, HashMap<String, Long> results) {
        StringBuffer res = new StringBuffer(testName + "\n");
        results.forEach((k, v) -> res.append(k + ": " + (v / 1000000000.0) + " seconds\n"));
        return res.toString();
    }

    public void clearResult() {
        this.result = "";
    }

    public void printResult() {
        System.out.println(this.result);
    }
}
