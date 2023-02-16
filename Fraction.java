import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Fraction {

    private final String a;

    public Fraction (String input) {
        Pattern no_extra_sign = Pattern.compile("^-?" + "[0-9]*" + "/" + "-?" + "[0-9]*$");
        Matcher ok = no_extra_sign.matcher(input);
        if (!ok.find()) {
            System.out.println("Это не дробь!");
            input = "again";
        }
        Pattern slash_zero = Pattern.compile("/-?0");
        Matcher zero = slash_zero.matcher(input);
        if (zero.find()){
            System.out.println("На ноль делить нельзя!");
            input = "again";
        }
        if (!input.equals("again")){
            int slash = input.indexOf("/");
            int len = input.length() - 1;
            if (slash == 0){
                input = 1 + input;
            }
            if (slash == len){
                input = input + 1;
            }
        }
        this.a = input;
    }

    public double make_double() {
        String f = String.valueOf(a);

        int slash = f.indexOf("/");
        int length = f.length();

        StringBuilder time = new StringBuilder(f);
        time.delete(slash, length);
        double first = Double.parseDouble(String.valueOf(time));

        time = new StringBuilder(f);
        time.delete(0, slash+1);
        double second = Double.parseDouble(String.valueOf(time));

        return (first/second);
    }

    public static Fraction defolt_fraction (){
        return new Fraction("1/1");
    }

    public String toString() {
        return a;
    }
}

class Manipulations {

    public static double plus(Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a+b;
    }

    public static double minus (Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a-b;
    }

    public static double product(Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a*b;
    }

    public static double quotient (Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a/b;
    }

    public static double all_manipulations (String input){
        int something = input.indexOf(" ") + 1;
        int length = input.length();

        StringBuilder time = new StringBuilder(input);
        time.delete(something - 1, length);
        Fraction first = new Fraction(String.valueOf(time));

        time = new StringBuilder(input);
        time.delete(0, something + 2);
        Fraction second = new Fraction(String.valueOf(time));

        time = new StringBuilder(input);
        time.charAt(something);
        String what_do = String.valueOf(time);

        double result = 0;
        if (what_do.equals("+")){
            result = Manipulations.plus(first, second);
        }
        if (what_do.equals("*")){
            result = Manipulations.product(first, second);
        }
        if (what_do.equals("-")){
            result = Manipulations.minus(first, second);
        }
        if (what_do.equals("/")){
            result = Manipulations.quotient(first, second);
        }

        return result;
    }
}