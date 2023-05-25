
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Character, Integer> romanToArabicMap = new HashMap<>();

    static {
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input: ");
        String input = scanner.nextLine();

        try {
            String output = calc(input);
            System.out.println("Output: " + output);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new Exception("Недопустимое количество операндов. Допускается использование только двух операндов.");
        }

        String firstOperand = parts[0];
        String operator = parts[1];
        String secondOperand = parts[2];

        boolean isRomanNumeral = isRomanNumber(firstOperand) && isRomanNumber(secondOperand);
        boolean isArabicNumeral = isArabicNumber(firstOperand) && isArabicNumber(secondOperand);

        if (!isRomanNumeral && !isArabicNumeral) {
            throw new Exception("Только одна система счисления должна быть использована (римская или арабская).");
        }

        int a, b;

        if (isRomanNumeral) {
            a = convertRomanToArabic(firstOperand);
            b = convertRomanToArabic(secondOperand);
            if (a < 1 || b < 1 || b > 10)
                throw new Exception("Числа должны быть в диапазоне от 1 до 10.");
        } else {
            a = Integer.parseInt(firstOperand);
            b = Integer.parseInt(secondOperand);

            if (a < 1 || b < 1 || b > 10)
            throw new Exception("Числа должны быть в диапазоне от 1 до 10.");
        }

        int result;
        switch (operator) {
            case "+":
                result = a + b;
                break;
            case "-":
                result = a - b;
                break;
            case "*":
                result = a * b;
                break;
            case "/":
                result = a / b;
                break;
            default:

                throw new Exception("Недопустимый оператор. Допускаются только операторы +, -, *, /.");
        }

        if (isRomanNumeral) {
            if (result <= 0) {
                throw new Exception("В римской системе числа должны быть положительными.");
            }
            return convertArabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isRomanNumber(String input) {
        return input.matches("[IVXLCDM]+");
    }

    private static boolean isArabicNumber(String input) {
        return input.matches("\\d+");
    }

    private static int convertRomanToArabic(String romanNumeral)  {
        int result = 0;
        int prevValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            char c = romanNumeral.charAt(i);

            int currentValue = romanToArabicMap.get(c);

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
                prevValue = currentValue;
            }
        }

        return result;
    }

    private static String convertArabicToRoman(int number) {
        StringBuilder romanNumeral = new StringBuilder();
        int[] arabicValues = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                romanNumeral.append(romanSymbols[i]);
                number -= arabicValues[i];
            }
        }

        return romanNumeral.toString();
    }
}

