package editor;

public class TranslateEditorCreator implements EditorCreator {
    @Override
    public TraslateEditor createEditor() {
        return new TranslatEditor();
    }
}