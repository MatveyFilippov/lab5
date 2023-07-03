import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Класс  манипуляций с дробями</b>
 * @author MatveyFilippov
 * @version 1.0
 * @see #make_fraction(double) Превращение числа в строковую дробь
 * @see #with_brackets(String) Счёт выражения со скобками
 * @see #infinity_string(String) Счёт выражения без скобок
 */
class Manipulations {

    /**
     * Превращение числа в строковую дробь по принципу умножения на 10, пока не останется чисел после запятой.
     * После превращения применяется метод сокращения дроби, описанный в классе дробей
     */
    protected static Fraction make_fraction(double a) throws Ex_here_nol, Ex_not_fraction {
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
        return new Fraction(time).cut();
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

    /**
     * Считает количество открытых и закрытых скобок, если количество неверное то вызывыет собственную ошибку
     * Разбивает строку с учётом скобок и полученное выражение отправляет на метод infinity_string
     * @throws Ex_not_fraction выводит сообщение о не открытых/закрытых скобках
     * @see #infinity_string(String) метод счёта бесконечной строки
     */
    public static double with_brackets (String input) throws Ex_here_nol, Ex_not_fraction {
        double answer;

        Pattern open = Pattern.compile("\\(");
        Pattern close = Pattern.compile("\\)");

        StringBuilder time = new StringBuilder(input);

        Matcher open_bracket = open.matcher(time);
        Matcher close_bracket = close.matcher(time);

        while (open_bracket.find() || close_bracket.find()){
            open_bracket = open.matcher(time);
            if (open_bracket.find()){
                int start = open_bracket.end();
                time.delete(start-1, start);
            } else{
                throw new Ex_not_fraction("Скобка закрыта, но не открыта");
            }
            close_bracket = close.matcher(time);
            if (close_bracket.find()){
                int end = close_bracket.end();
                time.delete(end-1, end);
            } else{
                throw new Ex_not_fraction("Скобка открыта, но не закрыта");
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
            Fraction fraction_in_bracket = make_fraction(infinity_string(String.valueOf(time)));
            start = start + time_start;
            if (i > 1){
                end = end+1;
            }
            already.replace(start-1, end, String.valueOf(fraction_in_bracket));
            close_bracket = close.matcher(already);
            open_bracket = open.matcher(already);
            time = new StringBuilder(already);
        }
        answer = infinity_string(String.valueOf(already));
        return answer;
    }

    /**
     * Сканирует начало строки на наличие дроби и сохраняет в ответ
     * После ищет знак выражения и дробь, полученную строку решает через метод манипуляций и сохраняет в ответ
     * @throws Ex_not_fraction выводит сообщение, если в выражении лишний знак
     * @see #all_manipulations(String) метод всех манипуляций
     */
    public static double infinity_string(String input) throws Ex_here_nol, Ex_not_fraction {
        double answer;
        Pattern firs = Pattern.compile("^-?" + "[0-9]*" + "/" + "-?" + "[0-9]*");
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
                Pattern another_fraction = Pattern.compile("^\s" + "[-*/+]" + "\s" + "-?" + "[0-9]*" + "/" + "-?" + "[0-9]*");
                Matcher get_next_fraction = another_fraction.matcher(String.valueOf(another));
                if (get_next_fraction.find()){
                    end = get_next_fraction.end();
                    length = String.valueOf(another).length();
                    time = new StringBuilder(another);
                    if ((length - end) > 0){
                        time.delete(end, length);
                    }
                    answer = all_manipulations(already_get + String.valueOf(time));
                    already_get = make_fraction(answer);
                    another.delete(0, end);
                }
                else{
                    throw new Ex_not_fraction("Введено неверное выражение!");
                }
            }
        }
        else{
            throw new Ex_not_fraction("Введено неверное выражение!");
        }
        return answer;
    }

    private static double all_manipulations (String input) throws Ex_not_fraction, Ex_here_nol {
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
