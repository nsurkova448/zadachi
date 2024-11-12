import java.util.*;

public class OddOccurrencesFinder {
    public static String findOddOccurrence(String text) {
        // Разбиваем текст на слова и приводим к нижнему регистру
        String[] words = text.toLowerCase().split("\\s+");

        // Создаем Map для подсчета вхождений слов
        Map<String, Integer> wordCount = new HashMap<>();

        // Подсчитываем вхождения каждого слова
        for (String word : words) {
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
        }

        // Ищем слово с нечетным количеством вхождений
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                return entry.getKey();
            }
        }

        // Если такого слова нет, возвращаем null
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите текст:");
        String text = scanner.nextLine();

        String result = findOddOccurrence(text);

        if (result != null) {
            System.out.println("Слово с нечетным количеством вхождений: " + result);
        } else {
            System.out.println("Все слова встречаются четное количество раз.");
        }
    }
}