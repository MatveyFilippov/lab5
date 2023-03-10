import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Ex_here_nol, Ex_not_frac {
        Scanner in = new Scanner(System.in);
        System.out.print("Нажмите ENTER чтоб начать");
        String ENTER = in.nextLine();

        String input;
        do{
            System.out.println();
            System.out.print("Введите выражение (например: -1/3 - 4/5 + 33/7) или stop, если хотите закончить: ");
            input = in.nextLine();
            Pattern open = Pattern.compile("\\(");
            Matcher open_bracket = open.matcher(input);
            try {
                double answer;
                if(open_bracket.find()){
                    answer = Manipulations.with_brackets(input);
                }
                else{
                    answer = Manipulations.infinity_string(input);
                }
                Fraction frac = Manipulations.make_frac(answer);
                System.out.println("Ответ: " + frac + " = " + answer);
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