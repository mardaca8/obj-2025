package editor;

public class Editor {
    private StringBuilder text;
    private final int MAX_LENGTH = 1000;

    // konstruktorius
    public Editor() {
        this("");
    }

    // konstruktorius su parametru
    public Editor(String initText) {
        this.text = new StringBuilder(initText);
    }

    
    public String gettext() {
        return text.toString();
    }

    
    public void settext(String newtext) {
        this.text = new StringBuilder(newtext);
    }

    // metodas put text
    public void put(String newtext) {
        if (text.length() + newtext.length() <= MAX_LENGTH) {
            text.append(newtext);
        } else {
            System.out.println("text to long");
        }
    }

    // perkrautas metodas put 
    public void put(String newtext, int symbolcount) {
        if (symbolcount > newtext.length()) {
            symbolcount = newtext.length();
        }
        put(newtext.substring(0, symbolcount));
    }

    // metodas copy 
    public String copy(int symbolcount) {
        if (symbolcount > text.length()) {
            symbolcount = text.length();
        }
        return text.substring(0, symbolcount);
    }

    // println metodas 
    public void println() {
        System.out.println("editor: " + text);
    }
}
