package editor;

/**
 * Interface for creating instances of Editor.
 * This allows for different implementations of Editor to be created
 * without specifying the exact class.
 */
public interface EditorCreator {
    /**
     * Creates a new instance of Editor.
     * @return a new Editor instance
     */
    Editor createEditor();
}