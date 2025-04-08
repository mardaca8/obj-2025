package editor;

public class WordToTranslateNotFoundException extends EditorException {
    private final String word;

    public WordToTranslateNotFoundException(String message, String word) {
        super(message);
        this.word = word;
    }

    public String getWord() {
        return word;
    }
    
    @Override
    public String toString() {
        return "WordToTranslateNotFoundException{" + "word='" + word + '\'' + ", message='" + getMessage() + '\'' + '}';
    }
    
}