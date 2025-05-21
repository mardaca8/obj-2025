
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
        popupDialog.setSize(200, 150);
        popupDialog.setLayout(new FlowLayout());

        JButton tranButton = new JButton("Translate Editor");
        JButton spellButton = new JButton("Spellcheck Editor");
        JButton loadButton = new JButton("Load Editor");

        popupDialog.add(tranButton);
        popupDialog.add(spellButton);
        popupDialog.add(loadButton);

        tranButton.addActionListener(e -> {
            editor = new TranslateEditor();
            popupDialog.dispose();
            
        });

        spellButton.addActionListener(e -> {
            editor = new SpellCheckEditor();
            popupDialog.dispose();
            
        });

        loadButton.addActionListener(e -> {
            LoadThread loadThread = new LoadThread();
            loadThread.start();
            try {
                loadThread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            editor = loadThread.editor;
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
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setColumns(60);
        textArea.setEnabled(false);
        
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        updateDisplay();

        inputField = new JTextField();
        inputField.setText("Enter text");
        inputField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (inputField.getText().equals("Enter text")) {
                            inputField.setText("");
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (inputField.getText().isEmpty()) {
                            inputField.setText("Enter text");
                        }
                    }
                });
        add(inputField, BorderLayout.NORTH);
        
        inputField.addActionListener(e -> {
            editor.put(inputField.getText());
            updateDisplay();
            inputField.setText("");
        });

        // mygtukai
        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton resetButton = new JButton("Reset");
        JButton addWordButton = new JButton("Add Word");
        JButton translateButton = new JButton("Translate");
        JButton spellCheckButton = new JButton("Spell Check");

        buttonsPanel.add(resetButton);
        buttonsPanel.add(saveButton);
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
        
        
        addWordButton.addActionListener(e -> {
            if (editor instanceof TranslateEditor) {
                JDialog addWordDialog = new JDialog(this, "Add Word", true);
                addWordDialog.setSize(400, 300);
                addWordDialog.setLayout(new BorderLayout());

                JLabel englishWordsLabel = new JLabel("English Words:");
                addWordDialog.add(englishWordsLabel, BorderLayout.NORTH);
                JTextArea englishWords = new JTextArea();
                englishWords.setEditable(false);
                englishWords.setLineWrap(true);
                englishWords.setWrapStyleWord(true);
                englishWords.setText(((TranslateEditor) editor).getEnglishWords());
                englishWords.setEnabled(false);
                addWordDialog.add(new JScrollPane(englishWords), BorderLayout.WEST);

                JLabel lithuanianWordsLabel = new JLabel("Lithuanian Words:");
                addWordDialog.add(lithuanianWordsLabel, BorderLayout.CENTER);
                JTextArea lithuanianWords = new JTextArea();
                lithuanianWords.setEditable(false);
                lithuanianWords.setLineWrap(true);
                lithuanianWords.setWrapStyleWord(true);
                lithuanianWords.setText(((TranslateEditor) editor).getEnglishWords());
                lithuanianWords.setEnabled(false);
                addWordDialog.add(new JScrollPane(lithuanianWords), BorderLayout.EAST);

                JPanel inputPanel = new JPanel(new BorderLayout());
                JTextField wordField = new JTextField();
                JTextField translationField = new JTextField();

                wordField.setText("Enter English Word");
                translationField.setText("Enter Lithuanian Word");

                wordField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (wordField.getText().equals("Enter English Word")) {
                            wordField.setText("");
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (wordField.getText().isEmpty()) {
                            wordField.setText("Enter English Word");
                        }
                    }
                });

                translationField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (translationField.getText().equals("Enter Lithuanian Word")) {
                            translationField.setText("");
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (translationField.getText().isEmpty()) {
                            translationField.setText("Enter Lithuanian Word");
                        }
                    }
                });
                

                inputPanel.add(wordField, BorderLayout.CENTER);
                inputPanel.add(translationField, BorderLayout.SOUTH);
                addWordDialog.add(inputPanel, BorderLayout.SOUTH);

                translationField.addActionListener(e1 -> {
                    String word = wordField.getText().trim();
                    String translation = translationField.getText().trim();
                    if (!word.isEmpty()) {
                        ((TranslateEditor) editor).addWord(word, translation);
                        englishWords.setText(((TranslateEditor) editor).getEnglishWords());
                        lithuanianWords.setText(((TranslateEditor) editor).getLithuanianWords());
                        wordField.setText("");
                        translationField.setText("");
                    }
                });

                addWordDialog.setLocationRelativeTo(this);
                addWordDialog.setVisible(true);
                
            } else if (editor instanceof SpellCheckEditor) {
                JDialog addWordDialog = new JDialog(this, "Add Word", true);
                addWordDialog.setSize(400, 300);
                addWordDialog.setLayout(new BorderLayout());

                JLabel dictionaryLabel = new JLabel("Dictionary:");
                addWordDialog.add(dictionaryLabel, BorderLayout.NORTH);
                JTextArea dictionaryArea = new JTextArea();
                dictionaryArea.setEditable(false);
                dictionaryArea.setLineWrap(true);
                dictionaryArea.setWrapStyleWord(true);
                dictionaryArea.setText(((SpellCheckEditor) editor).getDictionary());
                dictionaryArea.setEnabled(false);
                addWordDialog.add(new JScrollPane(dictionaryArea), BorderLayout.CENTER);

                JPanel inputPanel = new JPanel(new BorderLayout());
                JTextField wordField = new JTextField();
                wordField.setText("Enter word");
                wordField.addFocusListener(new java.awt.event.FocusAdapter() {
                    @Override
                    public void focusGained(java.awt.event.FocusEvent e) {
                        if (wordField.getText().equals("Enter word")) {
                            wordField.setText("");
                        }
                    }

                    @Override
                    public void focusLost(java.awt.event.FocusEvent e) {
                        if (wordField.getText().isEmpty()) {
                            wordField.setText("Enter word");
                        }
                    }
                });
                JButton addButton = new JButton("Add");

                inputPanel.add(wordField, BorderLayout.CENTER);
                addWordDialog.add(inputPanel, BorderLayout.SOUTH);

                wordField.addActionListener(e1 -> {
                    String word = wordField.getText().trim();
                    if (!word.isEmpty()) {
                        ((SpellCheckEditor) editor).addWord(word);
                        dictionaryArea.setText(((SpellCheckEditor) editor).getDictionary());
                        wordField.setText("");
                    }
                });

                

                addWordDialog.setLocationRelativeTo(this);
                addWordDialog.setVisible(true);
            } 
        });
        
        if (editor instanceof TranslateEditor) {
            translateButton.addActionListener(e -> {
                ((TranslateEditor) editor).translate();
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
