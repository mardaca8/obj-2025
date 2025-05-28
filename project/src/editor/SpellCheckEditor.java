package editor;

import java.io.*;
import java.util.*;

/**
 * SpellCheckEditor is an extension of the Editor class that provides spell-checking functionality.
 * It maintains a dictionary of words and can replace misspelled words with the closest match from the dictionary.
 */

public class SpellCheckEditor extends Editor implements Cloneable, Serializable {
    /** The dictionary of words used for spell-checking. */
    public List<String> dictionary;

    /**
     * Constructs a SpellCheckEditor with the specified text and initializes an empty dictionary.
     *
     * @param text the initial text of the editor
     */
    public SpellCheckEditor(String text) {
        super(text); 
        dictionary = new LinkedList<>();
    }

    /**
     * Constructs a SpellCheckEditor with an empty text and initializes an empty dictionary.
     */
    public SpellCheckEditor() {
        this("");
    }

    /**
     * Adds a word to the dictionary.
     *
     * @param word the word to be added to the dictionary
     */
    public void addWord(String word) {
        dictionary.add(word);
    }

    /**
     * Replaces a word with the closest match from the dictionary if it is not found in the dictionary.
     * A word is considered a match if it differs by exactly one character.
     *
     * @param word the word to be checked and possibly replaced
     * @return the original word if it is found in the dictionary, or the closest match if it differs by one character
     */
    public String replace(String word) {
        if (dictionary.contains(word)) {
            return word;
        }

        for (String dictWord : dictionary) {
            if (isOneCharDifferent(word, dictWord)) {
                return dictWord;
            }
        }

        return word; 
    }

    /**
     * Returns the content of the dictionary as a string, with each word on a new line.
     *
     * @return a string representation of the dictionary
     */
    public String getDictionary() {
        StringBuilder dictContent = new StringBuilder();
        for (String word : dictionary) {
            dictContent.append(word).append("\n");
        }
        return dictContent.toString();
    }

    /**
     * Checks if two words differ by exactly one character.
     *
     * @param word1 the first word
     * @param word2 the second word
     * @return true if the words differ by one character, false otherwise
     */
    private boolean isOneCharDifferent(String word1, String word2) {
        if (word1.length() != word2.length()) {
            return false;
        }

        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
                if (diffCount > 1) {
                    return false;
                }
            }
        }

        return true;
    }
    
    /**
     * Resets the editor's text and clears the dictionary.
     */
    @Override
    public void reset() {
        super.reset();
        dictionary.clear();
    }

    /**
     * Updates the state of the SpellCheckEditor with the state of another Editor.
     * If the loaded editor is an instance of SpellCheckEditor, it copies the dictionary.
     *
     * @param loadedEditor the Editor from which to load the state
     */
    @Override
    public void updateState(Editor loadedEditor) {
        super.updateState(loadedEditor);
        if (loadedEditor instanceof SpellCheckEditor) {
            SpellCheckEditor loadedSpellCheck = (SpellCheckEditor) loadedEditor;
            this.dictionary = new LinkedList<>(loadedSpellCheck.dictionary);
        }
    }

    /**
     * Performs spell-checking on the text by replacing each word with its closest match from the dictionary.
     * If a word is not found in the dictionary, it remains unchanged.
     */
    public void spellcheck() {
        String[] words = text.split("\\s+");
        StringBuilder correctedText = new StringBuilder();

        for (String word : words) {
            correctedText.append(replace(word)).append(" ");
        }

        text = correctedText.toString().trim();
    }

    /**
     * Returns a string representation of the SpellCheckEditor, including its text.
     *
     * @return a string representation of the SpellCheckEditor
     */
    @Override
    public String toString() {
        return "SpellCheck{" + "text='" + text + "'}";
    }

    /**
     * Creates a clone of the SpellCheckEditor.
     * The cloned editor has its own dictionary, but shares the text with the original.
     *
     * @return a cloned instance of SpellCheckEditor
     */
    @Override
    public SpellCheckEditor clone() {
        try {
            SpellCheckEditor cloned = (SpellCheckEditor) super.clone();

            cloned.dictionary = new LinkedList<>(this.dictionary);
            return cloned;

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }


}
