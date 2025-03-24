package editor.spellcheck;

import editor.Editor;
import java.util.LinkedList;
import java.util.List;

public class SpellCheck extends Editor {
    private List<String> dictionary;

    public SpellCheck() {
        dictionary = new LinkedList<>();
        initializeDictionaries();
    }

    private void initializeDictionaries() {
        dictionary.add("hello");
        dictionary.add("world");
        dictionary.add("java");
        dictionary.add("python");
        // Add more words as needed
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

        return word; // Return the original word if no correction is found
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

        return diffCount == 1;
    }
}

