import java.util.HashMap;

class Main {
    public static void main(String[] args) {
        HashMap<String, Multiplier> multipliers = new HashMap<String, Multiplier>();
        multipliers.put("Serial multiplier", new SerialMultiplier());
        multipliers.put("Stripe multiplier", new StripeMultiplier());
        multipliers.put("Fox multiplier", new FoxMultiplier());
        Tester tester = new Tester(multipliers);
        tester.test();
    }
}
