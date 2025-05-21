package editor;

import java.io.*;
import java.util.*;

public class TranslateEditor extends Editor implements Serializable {
    public LinkedList<String> englishWords;
    public LinkedList<String> lithuanianWords;

    public TranslateEditor(String text) {
        super(text);
        englishWords = new LinkedList<>();
        lithuanianWords = new LinkedList<>();
    }

    public TranslateEditor() {
        this("");
    }

    public String getEnglishWords() {
        StringBuilder englishContent = new StringBuilder();
        for (String word : englishWords) {
            englishContent.append(word).append("\n");
        }
        return englishContent.toString();
    }

    public String getLithuanianWords() {
        StringBuilder lithuanianContent = new StringBuilder();
        for (String word : lithuanianWords) {
            lithuanianContent.append(word).append("\n");
        }
        return lithuanianContent.toString();
    }

    public void addWord(String english, String lithuanian) {
        englishWords.add(english);
        lithuanianWords.add(lithuanian);
    }

    public String translate(boolean toLithuanian) {
        String[] words = text.split("\\s+");
        StringBuilder translatedText = new StringBuilder();

        for (String word : words) {
            String translatedWord = translateWord(word, toLithuanian);
            translatedText.append(translatedWord).append(" ");
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

    @Override
    public void updateState(Editor loadedEditor) {
        super.updateState(loadedEditor);
        if (loadedEditor instanceof TranslateEditor) {
            TranslateEditor loadedTranslateEditor = (TranslateEditor) loadedEditor;
            this.englishWords = new LinkedList<>(loadedTranslateEditor.englishWords);
            this.lithuanianWords = new LinkedList<>(loadedTranslateEditor.lithuanianWords);
        }
    }

    public void translate() {
        text = translate(true);
    }

    @Override
    public String toString() {
        return "Translate{" + "text='" + text + "'}";
    }
}
