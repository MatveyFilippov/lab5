import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Калькулятор дробей");
        System.out.print("Нажмите ENTER чтоб начать");
        String input = in.nextLine();
        System.out.println("Можно считать выражения по типу: \"-1/3 * (33/7 + 14/5) - 4/5\"");
        System.out.println();

        do{
            System.out.println();
            System.out.print("Введите выражение или stop, если хотите закончить: ");
            input = in.nextLine();
            Pattern open_close = Pattern.compile("\\(|\\)");
            Matcher bracket = open_close.matcher(input);
            try {
                double answer;
                if(bracket.find()){
                    answer = Manipulations.with_brackets(input);
                }
                else{
                    answer = Manipulations.infinity_string(input);
                }
                Fraction fraction = Manipulations.make_frac(answer);
                System.out.println("Ответ: " + fraction + " = " + answer);
            }catch(Ex_here_nol ex){
                System.out.println("Проверьте выражение и введите заново без деления на 0");
            } catch(Ex_not_frac ex){
                if (input.equals("stop")){
                    System.out.println("Ой, сразу не понял, что на этом всё, был рад поработать для вас!");
                }
                else{
                    System.out.println("Проверьте выражение на наличие лишних знаков или нехватки пробелов");
                }
            }
        }while (!input.equals("stop"));
    }
}