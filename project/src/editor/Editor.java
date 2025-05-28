package editor;

import java.io.*;


/**
 * Represents a simple text editor that can hold and manipulate text.
 * It supports operations like updating state, adding text, finding and replacing text, and resetting the content.
 */
public class Editor implements Serializable {
    /** The text content of the editor.
     */
    public String text;

    /**
     * Constructs an Editor with the specified text.
     *
     * @param text the initial text of the editor
     */
    public Editor(String text) {
        this.text = text;
    }

    /**
     * Constructs an Editor with an empty text.
     */
    public Editor() {
        this("");
    }

    /**
     * Returns the current text in the editor.
     *
     * @param loadedEditor the editor whose state is to be updated
     */
    public synchronized void updateState(Editor loadedEditor) {
        this.text = loadedEditor.text;
    }
    
    /**
     * Returns the current text in the editor.
     *
     * @param newtext the text to set in the editor
     */
    public void put(String newtext) {
        text = text + newtext;           
    }

    /**
     * Returns the current text in the editor.
     *
     * @param oldText the text to find in the editor
     * @param newText the text to replace the old text with
     */
    public void findAndReplace(String oldText, String newText) {
        text = text.replace(oldText, newText);
    }
    
    /**
     * Returns the current text in the editor.
     *
     */
    public void reset() {
        text = "";
    }

    /**
     * Returns the current text in the editor.
     *
     */
    @Override
    public String toString() {
        return "Editor{" + "text='" + text + "'}";
    }

}