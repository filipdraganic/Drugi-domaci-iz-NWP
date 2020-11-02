package domaci;

public class Main {

    public static void main(String[] args) {
        Test test = new Test();
        DIEngine engine = DIEngine.getInstance(test);
        engine.pumpIt(test);
        test.print();
    }
}
