package editor;

public abstract class Editor implements TextOperations {
    protected String text;
    private static int changesCount = 0;

    public Editor(String text) {
        this.text = text;
    }

    public Editor() {
        this("");
    }

    @Override
    public void put(String newtext) {
        text = text + newtext; 
        changesCount++;          
    }

    @Override
    public void copy(int length) {
        if (length > text.length()) {
            length = text.length();
        }
        text = text.substring(0, length);
    }
    
    public static int getChangesCount() {
        return changesCount;
    }

    @Override
    public void reset() {
        text = "";
        changesCount = 0;
    }

    @Override
    public String toString() {
        return "Editor{" + "text='" + text + "'}";
    }

}