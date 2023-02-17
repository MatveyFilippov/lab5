import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Checks{

    public static String pattern (String print, String regex){
        Scanner in = new Scanner(System.in);
        System.out.print(print);
        String ok = in.nextLine();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ok);
        while (!matcher.find()) {
            System.out.print("Ошибка ввода, попробуйте снова: ");
            ok = in.nextLine();
            matcher = pattern.matcher(ok);
        }
        return ok;
    }

    public static Fraction frac (String print){
        Scanner in = new Scanner(System.in);
        Fraction frac;
        do {
            System.out.print(print);
            String a = in.nextLine();
            frac = new Fraction (a);
        } while (String.valueOf(frac).equals("again"));
        return frac;
    }

}

public class Main {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Нажмите ENTER чтоб начать");
        String ENTER = in.nextLine();
        System.out.println();

        Fraction frac;
        do {
            System.out.print("Введите дробь: ");
            String a = in.nextLine();
            frac = new Fraction (a);
        } while (String.valueOf(frac).equals("again"));
        System.out.println("Ваша дробь = " + frac);
        System.out.println();

        Fraction first = Checks.frac("Введите первую дробь: ");
        System.out.println("Ваша дробь = " + first.make_double());
        Fraction second = Checks.frac("Введите вторую дробь: ");
        System.out.println("Ваша дробь = " + second.make_double());
        System.out.println();

        System.out.println(first + " + " + second + " = " + Manipulations.plus(first, second));
        System.out.println(first + " - " + second + " = " + Manipulations.minus(first, second));
        System.out.println(first + " * " + second + " = " + Manipulations.product(first, second));
        System.out.println(first + " / " + second + " = " + Manipulations.quotient(first, second));
        System.out.println();

        String i = Checks.pattern("Введите выражение (например -1/2 + 14/11): ", "^-?" + "[0-9]+" + "/" + "-?" + "[1-9]+" + "[0-9]*" + "\s" + "[-*/+]" + "\s" + "-?" + "[0-9]+" + "/" + "-?" + "[1-9]+" + "[0-9]*$");
        System.out.println("Ответ: " + Manipulations.all_manipulations(i));

        Fraction s = Manipulations.make_frac(Manipulations.all_manipulations(i));
        System.out.println("Ответ ввиде дроби: " + s);
    }
}