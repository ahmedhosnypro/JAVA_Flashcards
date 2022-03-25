package flashcards;

public class Card {
    private final String term;
    private String definition;
    private int wrongAnswers;

    public Card(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }

    public Card(String term, String definition, int wrongAnswers) {
        this.term = term;
        this.definition = definition;
        this.wrongAnswers = wrongAnswers;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void incrementWrongAnswers() {
        wrongAnswers++;
    }

    public void resetWrongAnswers() {
        wrongAnswers = 0;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }

    public int getWrongAnswersCount() {
        return wrongAnswers;
    }
}
