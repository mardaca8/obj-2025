import editor.Translate;
import editor.WordToTranslateNotFoundException;

public class EditorTest {
    public static void main(String[] args) {

        // EditorTools editor[] = { new SpellCheck()};

        // ((SpellCheck) editor[0]).addWord("hello");
        // editor[0].put("heklo wotld jaga");
        // System.out.println(editor[0]);
        // editor[0].spellcheck(); 
        // System.out.println(editor[0]); 
        // editor[0].reset();

        Translate editor1 = new Translate();
        editor1.put("hello");
        System.out.println(editor1);
        editor1.addWord("hello", "labas");
        try {
            editor1.translate(); 
        } catch (WordToTranslateNotFoundException e) {
            System.out.println(e.toString());
        }
        System.out.println(editor1);
        editor1.reset(); 

        editor1.put("world");
        System.out.println(editor1);
        editor1.addWord("hello", "labas");
        try {
            editor1.translate(); 
        } catch (WordToTranslateNotFoundException e) {
            System.out.println(e.toString());
        }
        System.out.println(editor1);

        // for (EditorTools i : editor) {
        //     i.reset();
        // }

        //System.out.println(Editor.getChangesCount());
    }
}

