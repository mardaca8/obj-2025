package editor;

import java.util.*;
import java.io.*;

public class SpellCheckEditor extends Editor implements Cloneable {
    private List<String> dictionary;

    public SpellCheckEditor(String text) {
        super(text); 
        dictionary = new LinkedList<>();
    }

    public SpellCheckEditor() {
        this("");
    }
    
    public void addWord(String word) {
        dictionary.add(word);
    }

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
    
    @Override
    public void reset() {
        super.reset();
        dictionary.clear();
    }

    
    public void spellcheck() {
        String[] words = text.split("\\s+");
        StringBuilder correctedText = new StringBuilder();

        for (String word : words) {
            correctedText.append(replace(word)).append(" ");
        }

        text = correctedText.toString().trim();
    }

    @Override
    public String toString() {
        return "SpellCheck{" + "text='" + text + "'}";
    }

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
