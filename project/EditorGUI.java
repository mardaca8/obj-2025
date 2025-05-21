
import editor.*;
import java.awt.*;
import javax.swing.*;


public class EditorGUI extends JFrame {
    private Editor editor = new Editor();
    private JTextArea textArea;
    private JTextField inputField;
    private JLabel changesLabel;
    
    public EditorGUI() {
        // pasirenkame redaktoriu
        JDialog popupDialog = new JDialog(this, "Editor", true);
        popupDialog.setSize(150, 150);
        popupDialog.setLayout(new FlowLayout());

        JButton createEditor = new JButton("Create Editor");
        JButton loadButton = new JButton("Load Editor");

        popupDialog.add(createEditor);
        popupDialog.add(loadButton);

        createEditor.addActionListener(e -> {
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
        setSize(600, 600);
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
        JButton switchEditorButton = new JButton("Switch Editor");

        buttonsPanel.add(switchEditorButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(saveButton);

        if (editor instanceof TranslateEditor) {
                buttonsPanel.add(translateButton);
                buttonsPanel.add(addWordButton);
        } else if (editor instanceof SpellCheckEditor) {
                buttonsPanel.add(spellCheckButton);
                buttonsPanel.add(addWordButton);
        }

        switchEditorButton.addActionListener(e -> {
            if (editor instanceof TranslateEditor) {
                editor = new SpellCheckEditor(editor.text);
            } else {
                editor = new TranslateEditor(editor.text);
            }
            updateDisplay();

            if (editor instanceof TranslateEditor) {
                buttonsPanel.remove(spellCheckButton);
                buttonsPanel.add(translateButton);
                buttonsPanel.add(addWordButton);
            } else if (editor instanceof SpellCheckEditor) {
                buttonsPanel.remove(translateButton);
                buttonsPanel.add(spellCheckButton);
                buttonsPanel.add(addWordButton);
            }
            buttonsPanel.revalidate();
            buttonsPanel.repaint();
        });

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
        
        
            translateButton.addActionListener(e -> {
                ((TranslateEditor) editor).translate();
                updateDisplay();
            });
        
            spellCheckButton.addActionListener(e -> {
                ((SpellCheckEditor) editor).spellcheck();
                updateDisplay();
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
