package editor;

import java.util.LinkedList;

public class Translate extends Editor {
    private LinkedList<String> englishWords;
    private LinkedList<String> lithuanianWords;

    public Translate(String text) {
        super(text);
        englishWords = new LinkedList<>();
        lithuanianWords = new LinkedList<>();
    }

    public Translate() {
        this("");
    }

    public void addWord(String english, String lithuanian) {
        englishWords.add(english);
        lithuanianWords.add(lithuanian);
    }

    public String translate(boolean toLithuanian) {
        String[] words = text.split("\\s+");
        StringBuilder translatedText = new StringBuilder();

        for (String word : words) {
            translatedText.append(translateWord(word, toLithuanian)).append(" ");
        }

        return translatedText.toString().trim();
    }

    private String translateWord(String word, boolean toLithuanian) {
        LinkedList<String> sourceWords = toLithuanian ? englishWords : lithuanianWords;
        LinkedList<String> targetWords = toLithuanian ? lithuanianWords : englishWords;

        for (int i = 0; i < sourceWords.size(); i++) {
            if (sourceWords.get(i).equalsIgnoreCase(word)) {
                return targetWords.get(i);
            }
        }
        return word;
    }

    public void reset() {
        text = "";
        englishWords.clear();
        lithuanianWords.clear();
    }

    @Override
    public void process() {
        text = translate(true);
    }

    @Override
    public String toString() {
        return "Translate{" + "text='" + text + "'}";
    }
}
