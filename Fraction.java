import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <b>Собственная ошибка (основная)</b>, сделанная для удобсва, выбрасывает сообщение которое мы будем задавать позже
 * @author MatveyFilippov
 * @version 1.0
 */
class Ex_not_fraction extends Exception{
    public Ex_not_fraction(String print_err) {
        System.err.println(print_err);
    }
}

/**
 * <b>Собственная ошибка (дополнительная)</b>, выбрасывается только в случае деления на ноль
 * @author MatveyFilippov
 * @version 1.0
 */
class Ex_here_nol extends Exception{
    public Ex_here_nol(){
        System.err.println("Делить на ноль нельзя");
    }
}


/**
 * <b>Класс  дробей</b>, включающий возможности упрощения дроби и превращения её в число
 * @author MatveyFilippov
 * @version 1.0
 * @see #Fraction(String) Создание дроби
 * @see #make_double() Превращение строковой дроби в число
 * @see #cut() Сокращение дроби
 */
class Fraction{

    private final String a;

    /**Проверка на дробь и её возможное дополнение до дефолтного занчения 1, если нет числа после или до слеша
     * @throws Ex_not_fraction выводит ошибку, если была помещена не дробь
     * @throws Ex_here_nol выводит ошибку, если дробь делится на ноль
     */
    public Fraction (String input) throws Ex_not_fraction, Ex_here_nol {
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
            throw new Ex_not_fraction("Была введена не дробь");
        }
    }

    /**Превращение строковой дроби в число*/
    public double make_double() {
        String f = a;

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

    /**Сокращение дроби по принципу поиска общего делителя (от 999 до 2)*/
    public Fraction cut() throws Ex_here_nol, Ex_not_fraction {
        String f = a;

        int slash = f.indexOf("/");
        int length = f.length();

        StringBuilder time = new StringBuilder(f);
        time.delete(slash, length);
        double first = Double.parseDouble(String.valueOf(time));

        time = new StringBuilder(f);
        time.delete(0, slash+1);
        double second = Double.parseDouble(String.valueOf(time));

        int num;
        for (num = 999; num > 1; num--){
            if (first % num == 0 && second % num == 0){
                first = first/num;
                second = second/num;
                break;
            }
        }

        f = (int) first + "/" + (int) second;

        return new Fraction(f);
    }

    public String toString() {
        return a;
    }

}
