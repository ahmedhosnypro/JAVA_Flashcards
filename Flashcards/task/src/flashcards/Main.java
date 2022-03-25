package flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    static final Scanner scanner = new Scanner(System.in);
    static StringBuilder logs = new StringBuilder();
    static ArrayList<Card> cards = new ArrayList<>();

    public static void main(String[] args) {
        startAction();
    }

    private static void startAction() {
        print("\nInput the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        String action = read();
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
                exportCards();
                startAction();
            }
            case "ask" -> {
                ask();
                startAction();
            }
            case "log" -> {
                log();
                startAction();
            }
            case "hardest card" -> {
                hardestCard();
                startAction();
            }
            case "reset stats" -> {
                resetStats();
                startAction();
            }
            case "exit" -> System.out.println("Bye bye!");
            default -> startAction();
        }
    }

    private static void addCard() {
        print("The card:");
        String term = read();

        for (Card card : cards) {
            if (card.getTerm().equals(term)) {
                print("The card \"" + term + "\" already exists.");
                return;
            }
        }

        print("The definition of the card:");
        String definition = read();

        for (Card card : cards) {
            if (card.getDefinition().equals(definition)) {
                print("The definition \"" + definition + "\" already exists.");
                return;
            }
        }

        cards.add(new Card(term, definition));
        print("The pair (\"" + term + "\":\"" + definition + "\") has been added.");
    }

    private static void removeCard() {
        print("Which card?");
        String term = read();

        boolean isRemoved = false;
        var it = cards.iterator();
        while (it.hasNext()) {
            var card = it.next();
            if (card.getTerm().equals(term)) {
                it.remove();
                print("The card has been removed.");
                isRemoved = true;
                break;
            }
        }

        if (!isRemoved) {
            print("Can't remove \"" + term + "\": there is no such card.");
        }
    }

    private static void importCards() {
        int importedCards = 0;
        print("File name:");
        String fileName = read();
        File file = new File(fileName);

        if (file.exists()) {
            try (Scanner fileScanner = new Scanner(file)) {
                while (fileScanner.hasNext()) {
                    String term = fileScanner.nextLine();
                    String definition = fileScanner.nextLine();
                    int wrongAnswers = Integer.parseInt(fileScanner.nextLine());
                    importedCards += importedCard(term, definition, wrongAnswers);
                }
                print(importedCards + " cards have been loaded.");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            print("File not found.");
        }
    }

    private static int importedCard(String term, String definition, int wrongAnswers) {
        for (var card : cards) {
            if (card.getTerm().equals(term)) {
                card.setDefinition(definition);
                card.setWrongAnswers(wrongAnswers);
                return 1;
            }
        }

        for (var card : cards) {
            if (card.getDefinition().equals(definition)) {
                return 0;
            }
        }
        cards.add(new Card(term, definition, wrongAnswers));
        return 1;
    }

    private static void exportCards() {
        int exportCards = 0;
        print("File name:");
        String fileName = read();
        File file = new File(fileName);
        try (FileWriter writer = new FileWriter(file)) {
            int index = 0;
            for (var card : cards) {
                if (index == cards.size() - 1) {
                    writer.write(card.getTerm() + "\n" + card.getDefinition() + "\n" + card.getWrongAnswersCount());
                } else {
                    writer.write(card.getTerm() + "\n" + card.getDefinition() + "\n" + card.getWrongAnswersCount() + "\n");
                }
                index++;
                exportCards++;
            }
            print(exportCards + " cards have been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ask() {
        print("How many times to ask?");
        int times = Integer.parseInt(read());

        for (int i = 0; i < times; i++) {
            print("Print the definition of \"" + cards.get(i).getTerm() + "\":");

            String definition = read();
            if (definition.equals(cards.get(i).getDefinition())) {
                print("Correct!");
            } else if (findDefinition(definition)) {
                print("Wrong. The right answer is \"" + cards.get(i).getDefinition() + "\", but your definition is correct for \"" + rightCard(definition) + "\"");
                cards.get(i).incrementWrongAnswers();
            } else {
                print("Wrong. The right answer is \"" + cards.get(i).getDefinition() + "\".");
                cards.get(i).incrementWrongAnswers();
            }
        }
    }

    private static void log() {
        print("File name:");
        String fileName = read();
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(logs.toString());
            print("The log has been saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void hardestCard() {
        if (cards.stream().anyMatch(card -> card.getWrongAnswersCount() > 0)) {
            int maxWrongAnswersLevel = wrongAnswersLevel();
            var hardestCard = getHardCards(maxWrongAnswersLevel);

            StringBuilder stats = new StringBuilder();
            if (hardestCard.size() > 1) {
                stats.append("The hardest cards are ");
                hardestCard.forEach(term -> stats.append("\"").append(term).append("\", "));
                stats.replace(stats.length() - 2, stats.length() - 1, "");
                stats.append(". You have ").append(maxWrongAnswersLevel).append(" errors answering them.");
            } else if (hardestCard.size() == 1) {
                stats.append("The hardest card is ");
                hardestCard.forEach(term -> stats.append("\"").append(term).append("\""));
                stats.append(". You have ").append(maxWrongAnswersLevel).append(" errors answering it.");
            }
            print(stats.toString());
        } else {
            print("There are no cards with errors.");
        }
    }

    private static void resetStats() {
        cards.forEach(Card::resetWrongAnswers);
        print("Card statistics have been reset.");
    }

    private static void print(String output) {
        System.out.println(output);
        logs.append(output).append("\n");
    }

    private static String read() {
        String input = scanner.nextLine();
        logs.append(input).append("\n");
        return input;
    }

    private static boolean findDefinition(String definition) {
        return cards.stream().anyMatch(card -> card.getDefinition().equals(definition));
    }

    private static String rightCard(String definition) {
        for (var card : cards) {
            if (card.getDefinition().equals(definition)) {
                return card.getTerm();
            }
        }
        return "";
    }

    private static int wrongAnswersLevel() {
        return cards.stream().mapToInt(Card::getWrongAnswersCount).reduce(0, Math::max);
    }

    private static ArrayList<String> getHardCards(int maxWrongAnswersLevel) {
        return cards.stream().filter(card -> card.getWrongAnswersCount() == maxWrongAnswersLevel).map(Card::getTerm).collect(Collectors.toCollection(ArrayList::new));
    }
}
