package editor;

import java.io.*;

public class LoadThread extends Thread {
    private final Editor editor;

    public LoadThread(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void run() { 
        try {
            DataInputStream in = new DataInputStream(new FileInputStream("editor_state.bin"));
            int length=in.readInt();
            byte[] data=new byte[length];
            in.readFully(data);
            String str = new String(data,"UTF-8");
            System.out.println("Loaded: " + str);
            synchronized (this) {
                editor.reset();
                editor.put(str);
            }
            System.out.println("Editor state updated successfully.");

        } catch (IOException e) {
            System.err.println("Error loading editor state: " + e.getMessage());
        }
    }

}