import editor.Editor;
import editor.SpellCheck;
import editor.Translate;

public class EditorTest {
    public static void main(String[] args) {

        SpellCheck editor = new SpellCheck(); 
        editor.addWord("hello");
        editor.put("hallo wotld jaga");
        System.out.println(editor);
        editor.process(); 
        System.out.println(editor); 


        Translate editor2 = new Translate(); 
        editor2.addWord("hello", "labas");
        editor2.put("hello world java");
        System.out.println(editor2);
        editor2.process(); 
        System.out.println(editor2); 

        Editor neweditor;
        neweditor = editor2;

        neweditor.reset();
        
        System.out.println(neweditor);
       
        //System.out.println(Editor.getChangesCount());
    }
}

