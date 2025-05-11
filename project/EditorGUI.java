import editor.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class EditorGUI extends JFrame {
    private Editor editor;
    private JTextArea textArea;
    private JTextField inputField;
    private JLabel changesLabel;
    
    

    public EditorGUI() {
        JDialog popupDialog = new JDialog(this, "Editor", true);
        popupDialog.setSize(200, 100);
        popupDialog.setLayout(new FlowLayout());

        JButton translateButton = new JButton("Translate Editor");
        JButton spellCheckButton = new JButton("Spellcheck Editor");

        popupDialog.add(translateButton);
        popupDialog.add(spellCheckButton);

        translateButton.addActionListener(e -> {
            editor = new TranslateEditor();
            popupDialog.dispose(); 
        });

        spellCheckButton.addActionListener(e -> {
            editor = new SpellCheckEditor();
            popupDialog.dispose(); 
        });

        popupDialog.setLocationRelativeTo(this);
        popupDialog.setVisible(true);

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
            SaveThread saveThread = new SaveThread(editor.text);
            saveThread.start();
        });
        
        loadButton.addActionListener(e -> {
            LoadThread loadThread = new LoadThread(editor);
            loadThread.start();
            try {
                loadThread.join();
                updateDisplay();
                System.out.println(editor.text);
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
