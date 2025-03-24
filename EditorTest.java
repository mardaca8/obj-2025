import editor.Editor;
import editor.spellcheck.SpellCheck;
import editor.translate.Translate;

public class EditorTest {
    public static void main(String[] args) {
        Editor editor = new Editor();
        //spellcheck
        SpellCheck spellCheck = new SpellCheck();
        editor.put(spellCheck.replace("helo "));
        //trasnlate
        Translate translate = new Translate();
        editor.put(translate.translate("world ", true));
        editor.println();
        editor.put("This is a very long text that will be cut off because it is too long. ");
        editor.println();
        editor.put("This is a very long text that will be cut off because it is too long.", 3);
        editor.println();
        editor.put("This is a new text.", 10);
        editor.println();
        editor.copy(5);
        editor.println();

    }
}

