package editor.translate;
import editor.Editor;
import java.util.LinkedList;

public class Translate extends Editor {
    private LinkedList<String> englishWords;
    private LinkedList<String> lithuanianWords;

    public Translate() {
        englishWords = new LinkedList<>();
        lithuanianWords = new LinkedList<>();
        initializeDictionaries();
    }

    private void initializeDictionaries() {
        englishWords.add("hello");
        lithuanianWords.add("labas");
        englishWords.add("world");
        lithuanianWords.add("pasaulis");
        // Add more words as needed
    }

    public String translate(String text, boolean toLithuanian) {
        LinkedList<String> sourceWords = toLithuanian ? englishWords : lithuanianWords;
        LinkedList<String> targetWords = toLithuanian ? lithuanianWords : englishWords;

        for (int i = 0; i < sourceWords.size(); i++) {
            text = text.replace(sourceWords.get(i), targetWords.get(i));
        }
        return text;
    }
}
