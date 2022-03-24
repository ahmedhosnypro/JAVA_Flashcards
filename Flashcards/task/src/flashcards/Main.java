package flashcards;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Input the number of cards:");
        Scanner scanner = new Scanner(System.in);
        int cards = Integer.parseInt(scanner.nextLine());
        ArrayList<String> terms = new ArrayList<>();
        ArrayList<String> definitions = new ArrayList<>();
        for (int i = 1; i <= cards; i++) {
            System.out.println("Card #" + i + ":");
            while (true) {
                String term = scanner.nextLine();
                if (terms.contains(term)) {
                    System.out.println("The term \"" + term + "\" already exists. Try again:");
                } else {
                    terms.add(term);
                    break;
                }
            }

            System.out.println("The definition for card #" + i + ":");
            while (true) {
                String definition = scanner.nextLine();
                if (definitions.contains(definition)) {
                    System.out.println(" The definition \"" + definition + "\" already exists. Try again:");
                } else {
                    definitions.add(definition);
                    break;
                }
            }
        }

        for (int i = 0; i < cards; i++) {
            System.out.println("Print the definition of \"" + terms.get(i) + "\"");
            String userInput = scanner.nextLine();
            if (userInput.equals(definitions.get(i))) {
                System.out.println("Correct!");
            } else if (definitions.contains(userInput)) {
                int rightTermIndex = definitions.indexOf(userInput);
                System.out.println("Wrong. The right answer is \"" + definitions.get(i) +
                        "\", but your definition is correct for \"" + terms.get(rightTermIndex) + "\"");
            } else {
                System.out.println("Wrong. The right answer is \"" + definitions.get(i) + "\".");
            }
        }
    }
}
