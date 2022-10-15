import java.io.IOException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

//Входные данные:
//Арабрские или римские числа со значеними от 1 до 10, оператор +, -, /, *
//
//Выходные данные:
//Решенное арифметическое выражение.
//При вводе римских чисел, ответ должен быть выведен римскими цифрами, соответственно, при вводе арабских - ответ ожидается арабскими

public class Main {
    public static void main(String[] args) throws IOException, DataFormatException{

        Scanner in = new Scanner(System.in);
        String userIn = in.nextLine().trim();
        System.out.println(calc(userIn));
        in.close();

/*
        TestIntToRoman();
        TestRomanToInt();
        TestCalc();
*/
    }


    public static String calc(String input) throws IOException, DataFormatException {
        input=input.replaceAll(" +","");                                          //Заменяем пробелы пустотой, разделителем будет оператор (+, -, /, *).
        input=input.toUpperCase();                                                                //Меняем регистор для исключения ошибки прочтения из-за разницы в регистрах.
        String[] userSplit = input.split("[+-/*/]");                                        // Делим строки на операнды.

        if (userSplit.length == 1)                                                                // Выводим исключение поскольку, операнд введен один.
            throw new IOException("//т.к. строка не является математической операцией");          // Или между числами стоит символ не являющийся оператором.
        if (userSplit.length > 2)                                                                 // Выводим исключение поскольку, операнд введено более двух.
            throw new IOException("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");

        char operand=input.charAt(userSplit[0].length());                                          // Вычисляем оператор.
        boolean aIsRoman = userSplit[0].matches("[IVX]+");                                   //Усливия состоят ли операнды целиком из римских символов
        boolean bIsRoman = userSplit[1].matches("[IVX]+");                                   //

        if ((aIsRoman&& !bIsRoman)||(!aIsRoman&& bIsRoman) )
            throw new NumberFormatException("// т.к. используются одновременно разные системы счисления"); // выводим исключение если одно из чисел является риским а другое арабским




        int a,b;
        int result;

        if (!aIsRoman&&!bIsRoman){                                                                    //Преобразование арабских значений в Int
            try {
                a = Integer.parseInt(userSplit[0]);
                b = Integer.parseInt(userSplit[1]);
            }catch(Exception e){
                throw new IOException("Введено некорректное число");                                   //Вывод ошибки преобразования string в int
            }
        } else{                                                                                        //Преобразование римских значений в Int
            a=RomanToInt(userSplit[0]);
            b=RomanToInt(userSplit[1]);
        }


        if (!(a>0&&a<=10)||!(b>0&&b<=10))                                                               // Вывод исключения при значения операндов не входящих в диапазон от 1 до 10
            throw new IOException("//т.к. калькудятор принимает на вход числа от 1 до 10 включительно");

        switch (operand) {                                                                              // Выполнения арифметических операций +,-,/,*
            case '+' -> result = (a + b);
            case '-' -> result = (a - b);
            case '/' -> result = (a / b);
            case '*' -> result = (a * b);
            default -> throw new DataFormatException("//т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }

        if (!aIsRoman&&!bIsRoman)
            return Integer.toString(result);                                                            // Вывод ответа для арабских чисел
        else
        if (result>0)
            return IntToRoman(result);                                                                  // Вывод ответа для римских чисел
        else
            throw new RuntimeException("//т.к. в римской системе нет отрицательных и нулевых чисел");   //Вывод исключения для римских чисел <=0
    }


    public static int RomanToInt(String val) {// Метод расчитан на значения от 01 до 39, начиная с 40 идет символ (XL)
        if (val.endsWith(Roman.IV.name())) return Roman.IV.value + RomanToInt(val.substring(0, val.length() - 2));// В первую очередь проверяются входжения сочетаний сиволов IV,IX для исключения
        if (val.endsWith(Roman.IX.name())) return Roman.IX.value + RomanToInt(val.substring(0, val.length() - 2));
        if (val.endsWith("VX")) throw new NumberFormatException("Введено некорректное число");
        if (val.endsWith(Roman.X.name())) return Roman.X.value + RomanToInt(val.substring(0, val.length() - 1));
        if (val.endsWith(Roman.V.name())) return Roman.V.value + RomanToInt(val.substring(0, val.length() - 1));
        if (val.endsWith(Roman.I.name())) return Roman.I.value + RomanToInt(val.substring(0, val.length() - 1));
        if(val.isEmpty())return 0;                                                                  // при достижении пустой строки рекурсия завершается
        throw new NumberFormatException("Введено некорректное число");
    }

    public static String IntToRoman(int val) {
        String result="";                                                                            // Корректное отображение вплоть до 399, начиная с 400 идет символ D (CD)
        result+=Roman.C.RepeatName(val / Roman.C.value);val%=Roman.C.value;                        //
        result+=Roman.XC.RepeatName(val / Roman.XC.value);val%=Roman.XC.value;                     //
        result+=Roman.L.RepeatName(val / Roman.L.value);val%=Roman.L.value;                        //
        result+=Roman.XL.RepeatName(val / Roman.XL.value);val%=Roman.XL.value;                     //
        result+=Roman.X.RepeatName(val / Roman.X.value);val%=Roman.X.value;                        //
        result+=Roman.IX.RepeatName(val / Roman.IX.value);val%=Roman.IX.value;                     //
        result+=Roman.V.RepeatName(val / Roman.V.value);val%=Roman.V.value;                        //
        result+=Roman.IV.RepeatName(val / Roman.IV.value);val%=Roman.IV.value;                     //
        result+= Roman.I.RepeatName(val);                                                            //
        return result;                                                                                //
    }



    // Tests

    static void TestIntToRoman(){
        System.out.println("Тест метода IntToRoman");
        for (int i = 1; i <= 399; i++) {
            System.out.print(i+" ");
            System.out.println(IntToRoman(i)+" ");
        }
    }
    static void TestRomanToInt(){
        System.out.println("Тест метода RomanToInt");
        for (int i = 1; i <= 39; i++) {
            System.out.print(IntToRoman(i)+" ");
            System.out.println(RomanToInt(IntToRoman(i)));
        }
    }
    static void TestCalc()throws IOException, DataFormatException{
        System.out.println("Тест метода Calc");
        String formula;
        for (int i = 1; i < 11; i++) {
            for (int j = 1; j < 11; j++) {
                formula=i+"+"+j;
                System.out.println(formula+"="+calc(formula));
                formula=i+"-"+j;
                System.out.println(formula+"="+calc(formula));
                formula=i+"*"+j;
                System.out.println(formula+"="+calc(formula));
                formula=i+"/"+j;
                System.out.println(formula+"="+calc(formula));
                if(i-j>0) {
                    formula = IntToRoman(i) + "+" + IntToRoman(j);
                    System.out.println(formula + "=" + calc(formula));
                    formula = IntToRoman(i) + "-" + IntToRoman(j);
                    System.out.println(formula + "=" + calc(formula));
                    formula = IntToRoman(i) + "*" + IntToRoman(j);
                    System.out.println(formula + "=" + calc(formula));
                    formula = IntToRoman(i) + "/" + IntToRoman(j);
                    System.out.println(formula + "=" + calc(formula));
                }
            }
        }
    }
}






