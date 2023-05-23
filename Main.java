
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input: ");
        String input = scanner.nextLine();
        String[] parts = input.split(" ");

        if (parts.length != 3) {
            throw new Exception("Недопустимое количество операндов. Допускается использование только двух операндов.");
        }

        int a, b, result = 0;
        boolean isLatinNumber = isLatinNumber(parts[0]);

        if (isLatinNumber(parts[2]) != isLatinNumber) {
            throw new Exception("т.к. используются одновременно разные системы счисления");
        }

        if (isLatinNumber(parts[0]) && isLatinNumber(parts[2])) {
            a = RomanNumeralConverter.convertToArabic(parts[0]);
            b = RomanNumeralConverter.convertToArabic(parts[2]);
        } else {
            a = Integer.parseInt(parts[0]);
            b = Integer.parseInt(parts[2]);
        }

        switch (parts[1]) {
            case "+":
                result = a + b;
                break;
            case "*":
                result = a * b;
                break;
            case "-":
                result = a - b;
                break;
            case "/":
                result = a / b;
                break;
            default:
                throw new Exception("т.к. формат математической операции не удовлетворяет заданию - оператор (+, -, /, *)");
        }

        if (isLatinNumber(parts[0]) && isLatinNumber(parts[2])) {
            if (result <= 0) {
                throw new Exception("т.к. в римской системе числа должны быть положительными");
            }

            System.out.println("Output: " + RomanNumeralConverter.convertToRoman(result));
        } else {


            System.out.println("Output: " + result);
        }
    }

    private static boolean isLatinNumber(String part) {
        String romanPattern = "(I|II|III|IV|V|VI|VII|VIII|IX|X)";
        return part.matches(romanPattern);
    }
}

class RomanNumeralConverter {
    private static final Map<Character, Integer> romanToArabicMap = new HashMap<>();
    private static final Map<Integer, String> arabicToRomanMap = new HashMap<>();

    static {
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);
        romanToArabicMap.put('L', 50);
        romanToArabicMap.put('C', 100);
        romanToArabicMap.put('D', 500);
        romanToArabicMap.put('M', 1000);

        arabicToRomanMap.put(1, "I");
        arabicToRomanMap.put(4, "IV");
        arabicToRomanMap.put(5, "V");
        arabicToRomanMap.put(9, "IX");
        arabicToRomanMap.put(10, "X");
        arabicToRomanMap.put(40, "XL");
        arabicToRomanMap.put(50, "L");
        arabicToRomanMap.put(90, "XC");
        arabicToRomanMap.put(100, "C");
        arabicToRomanMap.put(400, "CD");
        arabicToRomanMap.put(500, "D");
        arabicToRomanMap.put(900, "CM");
        arabicToRomanMap.put(1000, "M");
    }

    public static int convertToArabic(String romanNumeral) {
        int result = 0;
        int prevValue = 0;

        for (int i = romanNumeral.length() - 1; i >= 0; i--) {
            char c = romanNumeral.charAt(i);

            if (!romanToArabicMap.containsKey(c)) {
                throw new IllegalArgumentException("Неверный символ в римском числе: " + c);
            }

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

    public static String convertToRoman(int arabicNumeral) {


        StringBuilder romanNumeral = new StringBuilder();

        while (arabicNumeral > 0) {
            int closestValue = findClosestValue(arabicNumeral);

            if (closestValue == arabicNumeral) {
                romanNumeral.append(arabicToRomanMap.get(closestValue));
                arabicNumeral -= closestValue;
            } else {
                romanNumeral.append(arabicToRomanMap.get(closestValue));
                arabicNumeral -= closestValue;
            }
        }

        return romanNumeral.toString();
    }

    private static int findClosestValue(int arabicNumeral) {
        int closestValue = 1;

        for (int value : arabicToRomanMap.keySet()) {
            if (value <= arabicNumeral) {
                closestValue = value;
            } else {
                break;
            }
        }

        return closestValue;
    }
}