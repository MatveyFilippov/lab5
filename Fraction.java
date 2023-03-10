import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Ex_not_frac extends Exception{
    public Ex_not_frac(String print_err) {
        System.err.println(print_err);
    }
}

class Ex_here_nol extends Exception{
    public Ex_here_nol(){
        System.err.println("Делить на ноль нельзя");
    }
}

class Fraction{

    private final String a;

    public Fraction (String input) throws Ex_not_frac, Ex_here_nol {
        Pattern no_extra_sign = Pattern.compile("^-?" + "[0-9]*" + "/" + "-?" + "[0-9]*$");
        Matcher ok = no_extra_sign.matcher(input);
        if (ok.find()) {
            Pattern slash_zero = Pattern.compile("/-?0");
            Matcher zero = slash_zero.matcher(input);
            if (!zero.find()){
                int slash = input.indexOf("/");
                int len = input.length() - 1;
                if (slash == 0){
                    input = 1 + input;
                }
                if (slash == len){
                    input = input + 1;
                }
                this.a = input;

            }
            else {
                throw new Ex_here_nol();
            }
        }
        else {
            throw new Ex_not_frac("Была введена не дробь");
        }
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

    public String toString() {
        return a;
    }

}

class Manipulations {

    protected static Fraction make_frac (double a) throws Ex_here_nol, Ex_not_frac {
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

    private static double plus(Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a+b;
    }

    private static double minus (Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a-b;
    }

    private static double product(Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a*b;
    }

    private static double quotient (Fraction first, Fraction second) {
        double a = first.make_double();
        double b = second.make_double();
        return a/b;
    }

    public static double with_brackets (String input) throws Ex_here_nol, Ex_not_frac {
        double answer;

        Pattern open = Pattern.compile("\\(");
        Pattern close = Pattern.compile("\\)");
        Matcher open_bracket = open.matcher(input);
        Matcher close_bracket = close.matcher(input);

        StringBuilder time = new StringBuilder(input);

        while (open_bracket.find() || close_bracket.find()){
            open_bracket = open.matcher(time);
            if (open_bracket.find()){
                int start = open_bracket.end();
                time.delete(start-1, start);
            } else{
                throw new Ex_not_frac("Скобка закрыта, но не открыта");
            }
            close_bracket = close.matcher(time);
            if (close_bracket.find()){
                int end = close_bracket.end();
                time.delete(end-1, end);
            } else{
                throw new Ex_not_frac("Скобка открыта, но не закрыта");
            }
            open_bracket = open.matcher(time);
            close_bracket = close.matcher(time);
        }

        time = new StringBuilder(input);
        StringBuilder already = new StringBuilder(input);
        open_bracket = open.matcher(time);
        close_bracket = close.matcher(time);
        int i = 0;
        while (open_bracket.find() && close_bracket.find()){
            i++;
            int start = open_bracket.end();
            int end = close_bracket.end();
            int time_start = 0;
            time.delete(0, start).delete(end-start-1, time.length());
            open_bracket = open.matcher(time);
            while (open_bracket.find()){
                time_start = open_bracket.end();
                time.delete(0, time_start);
                open_bracket = open.matcher(time);
            }
            Fraction fraction_in_bracket = make_frac(infinity_string(String.valueOf(time)));
            start = start + time_start;
            already.replace(start-1, end, String.valueOf(fraction_in_bracket));
            System.out.println(already);
            close_bracket = close.matcher(already);
            open_bracket = open.matcher(already);
            time = new StringBuilder(already);
        }
        System.out.println(already);
        answer = infinity_string(String.valueOf(already));
        return answer;
    }

    public static double infinity_string(String input) throws Ex_here_nol, Ex_not_frac {
        double answer;
        Pattern firs = Pattern.compile("^-?" + "[0-9]+" + "/" + "-?" + "[1-9]+" + "[0-9]*");
        Matcher get_first = firs.matcher(input);

        if (get_first.find()){
            int end = get_first.end();
            int length = input.length();
            StringBuilder another = new StringBuilder();

            StringBuilder time = new StringBuilder(input);
            if ((length - end) > 0){
                another = time.delete(0, end);
                time = new StringBuilder(input);
                time.delete(end, length);
            }

            Fraction already_get = new Fraction(String.valueOf(time));
            answer = already_get.make_double();

            while (another.length() != 0){
                Pattern another_frac = Pattern.compile("^\s" + "[-*/+]" + "\s" + "-?" + "[0-9]+" + "/" + "-?" + "[1-9]+" + "[0-9]*");
                Matcher get_next_frac = another_frac.matcher(String.valueOf(another));
                if (get_next_frac.find()){
                    end = get_next_frac.end();
                    length = String.valueOf(another).length();
                    time = new StringBuilder(another);
                    if ((length - end) > 0){
                        time.delete(end, length);
                    }
                    answer = all_manipulations(already_get + String.valueOf(time));
                    already_get = make_frac(answer);
                    another.delete(0, end);
                }
                else{
                    throw new Ex_not_frac("Введено неверное выражение!");
                }
            }
        }
        else{
            throw new Ex_not_frac("Введено неверное выражение!");
        }
        return answer;
    }

    private static double all_manipulations (String input) throws Ex_not_frac, Ex_here_nol {
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