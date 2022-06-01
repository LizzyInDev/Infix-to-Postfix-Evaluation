public class Driver {

    public static void main(String[] args) {
        Parser p = new Parser();
        Evaluate e = new Evaluate();
        p.parser();
        e.postfixStack();
    }
}
