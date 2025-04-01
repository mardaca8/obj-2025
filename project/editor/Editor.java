package editor;

public class Editor {
    protected String text;
    private static int changesCount = 0;

    public Editor(String text) {
        this.text = text;
        incChangesCount();
    }

    public Editor() {
        this("");
    }

    public void put(String newtext) {
        text = text + newtext; 
        incChangesCount();          
    }

    public void copy(int length) {
        if (length > text.length()) {
            length = text.length();
        }
        text = text.substring(0, length);
    }

    private final void incChangesCount() {
        changesCount++;
    }
    
    public static int getChangesCount() {
        return changesCount;
    }

    public void process() {
        incChangesCount();
    }

    public void reset() {
        text = "";
        changesCount = 0;
    }

    @Override
    public String toString() {
        return "Editor{" + "text='" + text + "'}";
    }
}