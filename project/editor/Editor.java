package editor;

import java.io.*;

public class Editor implements Serializable {
    public String text;

    public Editor(String text) {
        this.text = text;
    }

    public Editor() {
        this("");
    }

    public synchronized void updateState(Editor loadedEditor) {
        this.text = loadedEditor.text;
    }
    
    public void put(String newtext) {
        text = text + newtext;           
    }

    
    public void copy(int length) {
        if (length > text.length()) {
            length = text.length();
        }
        text = text.substring(0, length);
    }
    
    public void reset() {
        text = "";
    }

    @Override
    public String toString() {
        return "Editor{" + "text='" + text + "'}";
    }

}