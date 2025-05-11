import editor.*;

public class EditorTest {
    public static void main(String[] args) {



        EditorCreator factory1 = new SpellCheckEditorCreator();
        SpellCheckEditor editor1 = (SpellCheckEditor) factory1.createEditor();

        EditorCreator factory2 = new TranslateEditorCreator();
        TranslateEditor editor2 = (TranslateEditor) factory2.createEditor();

        // editor1.put("hlllo");
        // System.out.print("1: ");
        // System.out.println(editor1);
    

       

    

       
        // System.out.print("1: ");
        // System.out.println(editor1);
        // System.out.print("2: ");
        // System.out.println(editor2);

        

    }
}

