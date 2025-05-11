package editor;

public class TranslateEditorCreator implements EditorCreator {
    @Override
    public TranslateEditor createEditor() {
        return new TranslateEditor();
    }
}