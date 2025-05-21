package editor;

import java.io.*;

public class LoadThread extends Thread {
    public Editor editor = new Editor();

    public LoadThread() {
    }

    @Override
    public void run() { 
        try {
            
			ObjectInputStream oi = new ObjectInputStream(new FileInputStream("editor_state.bin"));
            Editor loadedEditor = (Editor) oi.readObject();
            System.err.println("loaded: " + loadedEditor);
            if (loadedEditor instanceof TranslateEditor) {
                editor = new TranslateEditor();
            } else if (loadedEditor instanceof SpellCheckEditor) {
                editor = new SpellCheckEditor();
            } 
            oi.close();
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