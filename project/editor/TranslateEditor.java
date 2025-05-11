package editor;

import java.util.*;

public class TranslateEditor extends Editor {
    private LinkedList<String> englishWords;
    private LinkedList<String> lithuanianWords;

    public TranslateEditor(String text) {
        super(text);
        englishWords = new LinkedList<>();
        lithuanianWords = new LinkedList<>();
    }

    public TranslateEditor() {
        this("");
    }

    public void addWord(String english, String lithuanian) {
        englishWords.add(english);
        lithuanianWords.add(lithuanian);
    }

    public String translate(boolean toLithuanian) throws WordToTranslateNotFoundException {
        String[] words = text.split("\\s+");
        StringBuilder translatedText = new StringBuilder();

        for (String word : words) {
            String translatedWord = translateWord(word, toLithuanian);
            translatedText.append(translatedWord).append(" ");
            if (translatedWord.equals(word)) {
                throw new WordToTranslateNotFoundException("Word not found in dictionary", word);
            }
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

    @Override
    public void reset() {
        super.reset();
        englishWords.clear();
        lithuanianWords.clear();
    }

    public void translate() throws WordToTranslateNotFoundException {
        text = translate(true);
    }

    @Override
    public String toString() {
        return "Translate{" + "text='" + text + "'}";
    }
}
