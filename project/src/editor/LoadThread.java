package editor;

import java.io.*;

/**
 * LoadThread is a thread that loads the state of an Editor object from a file.
 */
public class LoadThread extends Thread {
    /** The editor whose state will be loaded. */
    public Editor editor = new Editor();

    /**
     * Constructs a LoadThread with an empty editor.
     */
    public LoadThread() {
    }

    /**
     * Constructs a LoadThread with the specified editor.
     *
     */
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