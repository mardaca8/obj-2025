package editor;

public class SpellCheckEditorCreator implements EditorCreator {
    @Override
    public SpellCheckEditor createEditor() {
        return new SpellCheckEditor(); 
    }
}