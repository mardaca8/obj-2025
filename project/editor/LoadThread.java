package editor;

import java.io.*;

public class LoadThread extends Thread {
    private Editor editor;

    public LoadThread(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void run() { 
        try {
			ObjectInputStream oi = new ObjectInputStream(new FileInputStream("editor_state.txt"));
            Editor loadedEditor = (Editor) oi.readObject();
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