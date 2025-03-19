package HelloWorld;

public class HelloGoodbye {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java HelloGoodbye <name1> <name2>");
        } else {
            System.out.println("Hello " + args[0] + " and " + args[1] + ".");
            System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
        }
        }
}
