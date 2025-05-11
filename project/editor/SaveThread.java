package editor;

import java.io.*;


public class SaveThread extends Thread {
    private final String text;

    public SaveThread(String text) {
        this.text = text;
    }

    @Override
    public void run() {
        try {
            System.out.println("Saved: " + text);
            DataOutputStream out = new DataOutputStream(new FileOutputStream("editor_state.bin"));
            byte[] data=text.getBytes("UTF-8");
            out.writeInt(data.length);
            out.write(data);
            System.out.println("Editor state saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving editor state: " + e.getMessage());
        }
    }
}