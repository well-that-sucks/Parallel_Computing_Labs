import java.util.HashMap;

class Main {
    public static void main(String[] args) {
        /*
         * // Make generator
         * ArrayList<ArrayList<Double>> matrix1 = new ArrayList<ArrayList<Double>>();
         * matrix1.add(new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 10.0)));
         * matrix1.add(new ArrayList<Double>(Arrays.asList(4.0, 5.0, 6.0, 11.0)));
         * matrix1.add(new ArrayList<Double>(Arrays.asList(7.0, 8.0, 9.0, 12.0)));
         *     
         * ArrayList<ArrayList<Double>> matrix2 = new ArrayList<ArrayList<Double>>();
         * matrix2.add(new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0)));
         * matrix2.add(new ArrayList<Double>(Arrays.asList(10.0, 4.0, 5.0)));
         * matrix2.add(new ArrayList<Double>(Arrays.asList(6.0, 11.0, 7.0)));
         * matrix2.add(new ArrayList<Double>(Arrays.asList(8.0, 9.0, 12.0)));
         * 
         * Matrix mmatrix1 = new Matrix(matrix1);
         * Matrix mmatrix2 = new Matrix(matrix2);
         * StripeMultiplier sm = new StripeMultiplier();
         * Matrix res = null;
         * try {
         * res = sm.run(mmatrix1, mmatrix2);
         * res.print();
         * } catch (DimensionsNotMatchingExcpetion e) {
         * e.printStackTrace();
         * }
         */
        HashMap<String, Multiplier> multipliers = new HashMap<String, Multiplier>();
        multipliers.put("Serial multiplier", new SerialMultiplier());
        multipliers.put("Stripe multiplier", new StripeMultiplier());
        Tester tester = new Tester(multipliers);
        tester.test();

    }
}
