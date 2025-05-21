package editor;

import java.beans.PropertyEditor;
import javax.swing.text.AbstractDocument;
import java.io.*;

public class LoadThread extends Thread {
    public Editor editor;

    public LoadThread() {
       
    }

    @Override
    public void run() { 
        try {
            
			ObjectInputStream oi = new ObjectInputStream(new FileInputStream("editor_state.bin"));
            Editor loadedEditor = (Editor) oi.readObject();
            if (loadedEditor instanceof TranslateEditor) {
                editor = new TranslateEditor();
            } else if (loadedEditor instanceof SpellCheckEditor) {
                editor = new SpellCheckEditor();
            } 
            oi.close();
            System.err.println("loaded: " + loadedEditor);
            synchronized (this) {
                editor.updateState(loadedEditor);
            }

        } catch (IOException e) {
            System.err.println("Error loading editor state: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading editor state: " + e.getMessage());
        }
    }

}