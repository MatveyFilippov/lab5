import java.util.Scanner;

class Checks{

    public static Fraction frac (String print){
        Scanner in = new Scanner(System.in);
        Fraction frac;
        do {
            System.out.print(print);
            frac = new Fraction (in.nextLine());
        } while (String.valueOf(frac).equals("again"));
        return frac;
    }

}

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Нажмите ENTER чтоб начать");
        String ENTER = in.nextLine();
        System.out.println();

        Fraction first = Checks.frac("Введите дробь: ");
        System.out.println("Ваша дробь = " + first  + " = " + first.make_double());
        System.out.println();

        String input;
        do{
            System.out.print("Введите выражиние (например: -1/3 - 4/5 + 33/7) или stop, если хотите закончить: ");
            input = in.nextLine();
            double answer = Manipulations.razdelitel(input);
            if (answer != 999999999){
                Fraction frac = Manipulations.make_frac(answer);
                System.out.println("Ответ: " + frac + " = " + answer);
            }
            System.out.println();
        }while (!input.equals("stop"));

        System.out.println("Был рад поработать для вас!");
    }
}