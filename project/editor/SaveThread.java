package editor;

import java.io.*;


public class SaveThread extends Thread {
    public Editor editor;

    public SaveThread(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("editor_state.txt"));
            out.writeObject(editor);
            out.close();
            System.err.println("saved: " + editor);
        } catch (IOException e) {
            System.err.println("Error saving editor state: " + e.getMessage());
        }
    }
}