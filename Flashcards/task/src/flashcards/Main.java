package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static ArrayList<String> cards = new ArrayList<>();
    static ArrayList<String> definitions = new ArrayList<>();

    public static void main(String[] args) {
        startAction();
    }

    private static void startAction() {
        System.out.println("Input the action (add, remove, import, export, ask, exit):");
        String action = scanner.nextLine();
        switch (action) {
            case "add" -> {
                addCard();
                startAction();
            }
            case "remove" -> {
                removeCard();
                startAction();
            }
            case "import" -> {
                importCards();
                startAction();
            }
            case "export" -> {
                int exportCards = exportCards();
                System.out.println(exportCards + " cards have been saved.");
                startAction();
            }
            case "ask" -> {
                ask();
                startAction();
            }
            case "exit" -> System.out.println("Bye bye!");
            default -> startAction();
        }
    }

    private static void addCard() {
        System.out.println("The card:");
        String card = scanner.nextLine();
        if (cards.contains(card)) {
            System.out.println("The card \"" + card + "\" already exists.");
            return;
        } else {
            cards.add(card);
        }

        System.out.println("The definition of the card:");
        String definition = scanner.nextLine();
        if (definitions.contains(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            cards.remove(card);
        } else {
            definitions.add(definition);
            System.out.println("The pair (\"" + card + "\":\"" + definition + "\") has been added.");
        }
    }

    private static void removeCard() {
        System.out.println("Which card?");
        String card = scanner.nextLine();
        if (cards.contains(card)) {
            int index = cards.indexOf(card);
            cards.remove(card);
            definitions.remove(index);
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + card + "\": there is no such card.");
        }
    }

    private static void importCards() {
        int importedCards = 0;
        System.out.println("File name:");
        String fileName = scanner.nextLine();

        File file = new File(fileName);

        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNext()) {
                    String card = fileScanner.nextLine();
                    if (cards.contains(card)) {
                        int cardIndex = cards.indexOf(card);
                        definitions.set(cardIndex, fileScanner.nextLine());
                        importedCards++;
                        continue;
                    } else {
                        cards.add(card);
                    }
                    String definition = fileScanner.nextLine();
                    if (!definitions.contains(definition)) {
                        definitions.add(definition);
                        importedCards++;
                    } else {
                        cards.remove(card);
                    }
                }
                System.out.println(importedCards + " cards have been loaded.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found.");
        }
    }

    private static int exportCards() {
        int exportCards = 0;
        System.out.println("File name:");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file)) {
            for (int i = 0; i < cards.size(); i++) {
                if (i == cards.size() - 1) {
                    writer.write(cards.get(i) + "\n" + definitions.get(i));
                } else {
                    writer.write(cards.get(i) + "\n" + definitions.get(i) + "\n");
                }

                exportCards++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exportCards;
    }

    private static void ask() {
        System.out.println("How many times to ask?");
        int times = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < times; i++) {
            System.out.println("Print the definition of \"" + cards.get(i) + "\":");

            String definition = scanner.nextLine();
            if (definition.equals(definitions.get(i))) {
                System.out.println("Correct!");
            } else if (definitions.contains(definition)) {
                int rightCardIndex = definitions.indexOf(definition);
                System.out.println("Wrong. The right answer is \"" + definitions.get(i) +
                        "\", but your definition is correct for \"" + cards.get(rightCardIndex) + "\"");
            } else {
                System.out.println("Wrong. The right answer is \"" + definitions.get(i) + "\".");
            }
        }
    }
}
