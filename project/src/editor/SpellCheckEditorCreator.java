package editor;

/**
 * TranslateEditorCreator is a class that implements the EditorCreator interface.
 * It provides a method to create instances of TranslateEditor.
 */
public class SpellCheckEditorCreator implements EditorCreator {
    /**
     * Creates a new instance of SpellCheckEditor with an empty text.
     *
     * @return a new SpellCheckEditor instance
     */
    @Override
    public SpellCheckEditor createEditor() {
        return new SpellCheckEditor(); 
    }
}