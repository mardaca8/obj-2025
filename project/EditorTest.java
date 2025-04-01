import editor.Editor;
import editor.SpellCheck;
import editor.Translate;

public class EditorTest {
    public static void main(String[] args) {

        Editor editor[] = { new SpellCheck(), new Translate() };

        ((SpellCheck) editor[0]).addWord("hello");
        editor[0].put("hallo wotld jaga");
        System.out.println(editor[0]);
        editor[0].process(); 
        System.out.println(editor[0]); 
        editor[0].reset();

        ((Translate) editor[1]).addWord("hello", "labas");
        editor[1].put("hello world java");
        System.out.println(editor[1]);
        editor[1].process(); 
        System.out.println(editor[1]); 

        for (Editor e : editor) {
            e.reset();
        }
       
        //System.out.println(Editor.getChangesCount());
    }
}

