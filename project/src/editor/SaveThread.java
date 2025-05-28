package editor;

import java.io.*;

/**
 * SaveThread is a thread that saves the state of an Editor object to a file.
 */
public class SaveThread extends Thread {
    /** The editor whose state will be saved. */
    public Editor editor;

    /**
     * Constructs a SaveThread with an empty editor.
     * @param editor the editor whose state is to be saved
     */
    public SaveThread(Editor editor) {
        this.editor = editor;
    }

    /**
     * Constructs a SaveThread with the specified editor.
     *
     */
    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("editor_state.bin"));
            out.writeObject(editor);
            out.close();
            System.err.println("saved: " + editor);
        } catch (IOException e) {
            System.err.println("Error saving editor state: " + e.getMessage());
        }
    }
}