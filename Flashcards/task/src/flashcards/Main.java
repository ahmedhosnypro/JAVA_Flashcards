package flashcards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input the number of cards:");
        Scanner scanner = new Scanner(System.in);
        int cards = Integer.parseInt(scanner.nextLine());
        ArrayList<String> terms = new ArrayList<>();
        ArrayList<String> definitions = new ArrayList<>();
        for (int i = 1; i <= cards; i++) {
            System.out.println("Card #" + i + ":");
            terms.add(scanner.nextLine());
            System.out.println("The definition for card #" + i + ":");
            definitions.add(scanner.nextLine());
        }

        for (int i = 0; i < cards; i++) {
            System.out.println("Print the definition of \"" + terms.get(i) + "\"");
            String userInput = scanner.nextLine();
            if (userInput.equals(definitions.get(i))) {
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong. The right answer is \"" + definitions.get(i) + "\".");
            }
        }
    }
}
