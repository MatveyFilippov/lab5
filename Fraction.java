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

    static Fraction make_frac (double a){
        int b = (int) a;
        int zero = 0;
        while (b/a != 1){
            a = a * 10;
            b = (int) a;
            zero++;
            if (zero > 6){
                break;
            }
        }
        String one = "1";
        for (int i = 0; i < zero; i++){
            one = one + "0";
        }
        String time = b + "/" + one;
        return new Fraction(time);
    }

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

    public static double razdelitel (String input){
        double answer;
        String regex = "^-?" + "[0-9]+" + "/" + "-?" + "[1-9]+" + "[0-9]*" + "\s" + "[-*/+]" + "\s" + "-?" + "[0-9]+" + "/" + "-?" + "[1-9]+" + "[0-9]*";
        Pattern firs_frac = Pattern.compile(regex);
        Matcher get_first_frac = firs_frac.matcher(input);
        if (get_first_frac.find()){
            int end = get_first_frac.end();
            int length = input.length();

            StringBuilder time = new StringBuilder(input);
            StringBuilder another = new StringBuilder();
            if ((length - end) > 0){
                another = time.delete(0, end);
                time = new StringBuilder(input);
                time.delete(end, length);
            }

            answer = all_manipulations(String.valueOf(time));
            Fraction already_get = make_frac(answer);

            while (another.length() != 0){
                Pattern another_frac = Pattern.compile("^\s" + "[-*/+]" + "\s" + "-?" + "[0-9]+" + "/" + "-?" + "[1-9]+" + "[0-9]*");
                Matcher get_next_frac = another_frac.matcher(String.valueOf(another));
                if (get_next_frac.find()){
                    end = get_next_frac.end();
                    length = String.valueOf(another).length();
                    time = another;
                    if ((length - end) > 0){
                        time.delete(end, length);
                    }
                    answer = all_manipulations(already_get + String.valueOf(time));
                    already_get = make_frac(answer);
                    another.delete(0, end);
                }
                else{
                    System.out.println("Введено не верное выражение!");
                    answer = 999999999;
                    break;
                }
            }
        }
        else{
            System.out.println("Введено не верное выражение!");
            answer = 999999999;
        }
        return answer;
    }

    private static double all_manipulations (String input){
        int something = input.indexOf(" ") + 1;
        int length = input.length();

        StringBuilder time = new StringBuilder(input);
        time.delete(something - 1, length);
        Fraction first = new Fraction(String.valueOf(time));

        time = new StringBuilder(input);
        time.delete(0, something + 2);
        Fraction second = new Fraction(String.valueOf(time));

        time = new StringBuilder(input);
        String what_do = String.valueOf(time.charAt(something));

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