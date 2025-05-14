
import editor.*;
import java.awt.*;
import javax.swing.*;


public class EditorGUI extends JFrame {
    private Editor editor;
    private JTextArea textArea;
    private JTextField inputField;
    private JLabel changesLabel;
    
    public EditorGUI() {
        // pasirenkame redaktoriu
        JDialog popupDialog = new JDialog(this, "Editor", true);
        popupDialog.setSize(200, 100);
        popupDialog.setLayout(new FlowLayout());

        JButton tranButton = new JButton("Translate Editor");
        JButton spellButton = new JButton("Spellcheck Editor");

        popupDialog.add(tranButton);
        popupDialog.add(spellButton);

        tranButton.addActionListener(e -> {
            editor = new TranslateEditor();
            popupDialog.dispose();
            
        });

        spellButton.addActionListener(e -> {
            editor = new SpellCheckEditor();
            popupDialog.dispose();
            
        });

        popupDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });

        popupDialog.setLocationRelativeTo(this);
        popupDialog.setVisible(true);
        
        // sukuriame GUI
        setTitle("Teksto Redaktorius");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        inputField = new JTextField();
        add(inputField, BorderLayout.NORTH);
        
        inputField.addActionListener(e -> {
            editor.put(inputField.getText());
            updateDisplay();
            inputField.setText("");
        });

        // mygtukai
        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton resetButton = new JButton("Reset");
        JButton addWordButton = new JButton("Add Word");
        JButton translateButton = new JButton("Translate");
        JButton spellCheckButton = new JButton("Spell Check");

        buttonsPanel.add(resetButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(loadButton);
        buttonsPanel.add(addWordButton);

        if (editor instanceof TranslateEditor) {
            buttonsPanel.add(translateButton);
        }
        else if (editor instanceof SpellCheckEditor) { 
            buttonsPanel.add(spellCheckButton);
        }

        add(buttonsPanel, BorderLayout.SOUTH);

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
        
        addWordButton.addActionListener(e -> {
            String word = JOptionPane.showInputDialog(this, "Enter word:");
            if (word != null && !word.trim().isEmpty()) {
                if (editor instanceof TranslateEditor) {
                    String translation = JOptionPane.showInputDialog(this, "Enter translation:");
                    if (translation != null && !translation.trim().isEmpty()) {
                        ((TranslateEditor) editor).addWord(word, translation);
                    }
                } else if (editor instanceof SpellCheckEditor) {
                    ((SpellCheckEditor) editor).addWord(word);
                }
            }
        });
        
        if (editor instanceof TranslateEditor) {
            translateButton.addActionListener(e -> {
                try {((TranslateEditor) editor).translate();}
                catch (WordToTranslateNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "Word not found: " + ex.getWord(), "Error", JOptionPane.ERROR_MESSAGE);
                }
                updateDisplay();
            });
        }
        else if (editor instanceof SpellCheckEditor) {
            spellCheckButton.addActionListener(e -> {
                ((SpellCheckEditor) editor).spellcheck();
                updateDisplay();
            });
        }
        
        setVisible(true);
    }

    private void updateDisplay() {
        textArea.setText(editor.text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorGUI::new);
    }
}
