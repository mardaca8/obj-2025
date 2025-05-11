package editor;

public abstract class Editor  {
    public String text;
    private static int changesCount = 0;

    public Editor(String text) {
        this.text = text;
    }

    public Editor() {
        this("");
    }

    
    public void put(String newtext) {
        text = text + newtext; 
        changesCount++;          
    }

    
    public void copy(int length) {
        if (length > text.length()) {
            length = text.length();
        }
        text = text.substring(0, length);
    }
    
    public static int getChangesCount() {
        return changesCount;
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