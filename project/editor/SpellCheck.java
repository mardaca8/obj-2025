package editor;

import java.util.LinkedList;
import java.util.List;

public class SpellCheck extends Editor {
    private List<String> dictionary;

    public SpellCheck(String text) {
        super(text); 
        dictionary = new LinkedList<>();
    }

    public SpellCheck() {
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

    public void reset() {
        text = "";
        dictionary.clear();
    }

    @Override
    public void process() {
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
}
