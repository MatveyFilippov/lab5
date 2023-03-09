import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Ex_here_nol {
        Scanner in = new Scanner(System.in);
        System.out.print("Нажмите ENTER чтоб начать");
        String ENTER = in.nextLine();

        String input;
        do{
            System.out.println();
            System.out.print("Введите выражение (например: -1/3 - 4/5 + 33/7) или stop, если хотите закончить: ");
            input = in.nextLine();
            try {
                double answer = Manipulations.razdelitel(input);
                Fraction frac = Manipulations.make_frac(answer);
                System.out.println("Ответ: " + frac + " = " + answer);
            } catch(Ex_not_frac ex){
                if (input.equals("stop")){
                    System.out.println("Был рад поработать для вас!");
                }
                else{
                    System.out.println("Введено неверное выражение!");
                    System.out.println("Проверьте выражение на наличие лишних знаков или нехватки пробелов");
                }
            }
        }while (!input.equals("stop"));
    }
}