import editor.*;

public class EditorTest {
    public static void main(String[] args) {



        EditorCreator factory = new SpellCheckEditorCreator();
        SpellCheckEditor editor1 = (SpellCheckEditor) factory.createEditor();

        editor1.put("hlllo");
        System.out.print("1: ");
        System.out.println(editor1);
    

        SpellCheckEditor editor2 = editor1.clone();

        editor2.addWord("hello");

        editor1.spellcheck();
        editor2.spellcheck();

       
        System.out.print("1: ");
        System.out.println(editor1);
        System.out.print("2: ");
        System.out.println(editor2);

        

    }
}

