import editor.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EditorGUI extends JFrame {
    private SpellCheckEditor editor;
    private JTextArea textArea;
    private JTextField inputField;
    private JLabel changesLabel;

    

    public EditorGUI() {
        EditorCreator factory = new SpellCheckEditorCreator();
        editor = (SpellCheckEditor) factory.createEditor();

        setTitle("Teksto Redaktorius");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        inputField = new JTextField();
        add(inputField, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton putButton = new JButton("Add text");
        JButton resetButton = new JButton("Reset");
        
        buttonsPanel.add(putButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(loadButton);
        add(buttonsPanel, BorderLayout.SOUTH);

        // Mygtukų veikimas
        putButton.addActionListener(e -> {
            editor.put(inputField.getText());
            updateDisplay();
        });

        resetButton.addActionListener(e -> {
            editor.reset();
            updateDisplay();
        });

        saveButton.addActionListener(e -> {
            SaveThread saveThread = new SaveThread(editor);
            saveThread.start();
        });
        
        loadButton.addActionListener(e -> {
            LoadThread loadThread = new LoadThread(editor);
            loadThread.start();
            try {
                loadThread.join();
                updateDisplay();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            
        });
        setVisible(true);
    }

    private void updateDisplay() {
        textArea.setText(editor.text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorGUI::new);
    }
}
