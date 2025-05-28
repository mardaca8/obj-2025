package editor;

import java.io.*;
import java.util.*;

/**
 * TranslateEditor is a specialized editor that allows for translation between English and Lithuanian words.
 */
public class TranslateEditor extends Editor implements Serializable {
    /** Lists to store English and Lithuanian words. */
    public LinkedList<String> englishWords;
    /** Lists to store Lithuanian words corresponding to the English words. */
    public LinkedList<String> lithuanianWords;

    /**
     * Constructs a TranslateEditor with the specified text and initializes empty lists for English and Lithuanian words.
     *
     * @param text the initial text of the editor
     */
    public TranslateEditor(String text) {
        super(text);
        englishWords = new LinkedList<>();
        lithuanianWords = new LinkedList<>();
    }

    /**
     * Constructs a TranslateEditor with an empty text and initializes empty lists for English and Lithuanian words.
     */
    public TranslateEditor() {
        this("");
    }

    /**
     * Returns the English words in the editor as a formatted string.
     *
     * @return a string containing all English words, each on a new line
     */
    public String getEnglishWords() {
        StringBuilder englishContent = new StringBuilder();
        for (String word : englishWords) {
            englishContent.append(word).append("\n");
        }
        return englishContent.toString();
    }

    /**
     * Returns the Lithuanian words in the editor as a formatted string.
     *
     * @return a string containing all Lithuanian words, each on a new line
     */
    public String getLithuanianWords() {
        StringBuilder lithuanianContent = new StringBuilder();
        for (String word : lithuanianWords) {
            lithuanianContent.append(word).append("\n");
        }
        return lithuanianContent.toString();
    }

    /**
     * Adds a new word pair (English and Lithuanian) to the editor.
     *
     * @param english      the English word
     * @param lithuanian   the Lithuanian translation of the English word
     */
    public void addWord(String english, String lithuanian) {
        englishWords.add(english);
        lithuanianWords.add(lithuanian);
    }

    /**
     * Translates the text in the editor from English to Lithuanian or vice versa.
     *
     * @param toLithuanian if true, translates from English to Lithuanian; if false, translates from Lithuanian to English
     * @return the translated text
     */
    public String translate(boolean toLithuanian) {
        String[] words = text.split("\\s+");
        StringBuilder translatedText = new StringBuilder();

        for (String word : words) {
            String translatedWord = translateWord(word, toLithuanian);
            translatedText.append(translatedWord).append(" ");
        }

        return translatedText.toString().trim();
    }

    /**
     * Translates a single word from English to Lithuanian or vice versa.
     *
     * @param word         the word to be translated
     * @param toLithuanian if true, translates from English to Lithuanian; if false, translates from Lithuanian to English
     * @return the translated word, or the original word if no translation is found
     */
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

    /**
     * Resets the state of the TranslateEditor by clearing the lists of English and Lithuanian words.
     *
     */
    @Override
    public void reset() {
        super.reset();
        englishWords.clear();
        lithuanianWords.clear();
    }

    /**
     * Updates the state of the TranslateEditor with the state of another Editor.
     * If the loaded editor is an instance of TranslateEditor, it copies the English and Lithuanian words.
     *
     * @param loadedEditor the Editor from which to load the state
     */
    @Override
    public void updateState(Editor loadedEditor) {
        super.updateState(loadedEditor);
        if (loadedEditor instanceof TranslateEditor) {
            TranslateEditor loadedTranslateEditor = (TranslateEditor) loadedEditor;
            this.englishWords = new LinkedList<>(loadedTranslateEditor.englishWords);
            this.lithuanianWords = new LinkedList<>(loadedTranslateEditor.lithuanianWords);
        }
    }

    /**
     * Translates the text in the editor from English to Lithuanian.
     * @param toLithuanian if true, translates from English to Lithuanian; if false, translates from Lithuanian to English
     */
    public void translation(boolean toLithuanian) {
        text = translate(toLithuanian);
    }

    /**
     * Translates the text in the editor from Lithuanian to English.
     */
    @Override
    public String toString() {
        return "Translate{" + "text='" + text + "'}";
    }
}
