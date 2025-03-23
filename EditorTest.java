public class EditorTest {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.paste("Hello, ");
        editor.paste("world!");
        editor.println();
        editor.paste("This is a very long text that will be cut off because it is too long.");
        editor.println();
        editor.paste("This is a very long text that will be cut off because it is too long.", 10);
        editor.println();
        editor.settext("This is a new text.");
        editor.println();
        editor.paste("This is a new text.");
        editor.println();
        editor.paste("This is a new text.", 10);
        editor.println();
        System.out.println(editor.copy(5));
    }
}

