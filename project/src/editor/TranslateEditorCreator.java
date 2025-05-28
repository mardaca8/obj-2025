package editor;

/**
 * TranslateEditorCreator is a class that implements the EditorCreator interface.
 * It provides a method to create instances of TranslateEditor.
 */
public class TranslateEditorCreator implements EditorCreator {
    /**
     * Creates a new instance of TranslateEditor with an empty text.
     *
     * @return a new TranslateEditor instance
     */
    @Override
    public TranslateEditor createEditor() {
        return new TranslateEditor();
    }
}