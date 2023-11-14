package hexlet.code;

public class App {
    public static void main(String[] args) {
        Validator v = new Validator();
        boolean checkExample = v.number().required().isValid(null);
        System.out.println(checkExample);
    }
}

