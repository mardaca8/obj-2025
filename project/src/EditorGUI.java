

import editor.*;
import java.awt.*;
import javax.swing.*;

/**
 * EditorGUI is a graphical user interface for a text editor that allows users to create, load, and manipulate text.
 * It supports different types of editors (TranslateEditor and SpellCheckEditor) and provides various functionalities
 * such as adding words, translating, spell checking, and saving the editor state.
 * 
 */
public class EditorGUI extends JFrame {
    /**
     * The main editor instance that holds the text and provides editing functionalities.
     * It can be an instance of TranslateEditor or SpellCheckEditor.
     */
    private Editor editor = new Editor();
    /**
     * A text area to display the content of the editor.
     * This area is non-editable and shows the current text in the editor.
     */
    private JTextArea textArea;
    /**
     * A text field for user input.
     * This field allows users to enter text that will be added to the editor.
     */
    private JTextField inputField;
    /**
     * A label to display changes made in the editor.
     * This label is updated whenever the text in the editor changes.
     */
    private JLabel changesLabel;
    /**
     * A flag to determine whether the translation mode is enabled.
     * If true, the editor will translate text to Lithuanian; otherwise, it will not.
     */
    private boolean translateMode = true;
    
    /**
     * Constructs the EditorGUI and initializes the user interface.
     * It allows the user to choose between creating a new editor or loading an existing one.
     */
    public EditorGUI() {
        // pasirenkame redaktoriu
        JDialog popupDialog = new JDialog(this, "Editor", true);
        popupDialog.setSize(200, 120);
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
        setSize(900, 700);
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
        JButton findAndReplacButton = new JButton("Find and Replace");
        JButton switchEditorButton = new JButton("Switch Editor");
        JCheckBox translateModeSwitch = new JCheckBox("Translate to Lithuanian");

        buttonsPanel.add(switchEditorButton);
        buttonsPanel.add(resetButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.add(findAndReplacButton);
        
        if (editor instanceof TranslateEditor) {
                buttonsPanel.add(translateButton);
                buttonsPanel.add(addWordButton);
                buttonsPanel.add(translateModeSwitch);
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
                buttonsPanel.add(translateModeSwitch);
            } else if (editor instanceof SpellCheckEditor) {
                buttonsPanel.remove(translateModeSwitch);
                buttonsPanel.remove(translateButton);
                buttonsPanel.add(spellCheckButton);
                buttonsPanel.add(addWordButton);
            }
            buttonsPanel.revalidate();
            buttonsPanel.repaint();
        });

        findAndReplacButton.addActionListener(e -> {
            String oldText = JOptionPane.showInputDialog(this, "Enter text to find:");
            String newText = JOptionPane.showInputDialog(this, "Enter text to replace with:");
            if (oldText != null && newText != null) {
                editor.findAndReplace(oldText, newText);
                updateDisplay();
            }
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
        
        translateModeSwitch.setSelected(translateMode);
        translateModeSwitch.addActionListener(e -> {
            translateMode = translateModeSwitch.isSelected();
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
                lithuanianWords.setText(((TranslateEditor) editor).getLithuanianWords());
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
                ((TranslateEditor) editor).translation(translateMode);
                updateDisplay();
            });
        
            spellCheckButton.addActionListener(e -> {
                ((SpellCheckEditor) editor).spellcheck();
                updateDisplay();
            });
        
        setVisible(true);
    }

    /**
     * Updates the text area to display the current text of the editor.
     * This method is called whenever the editor's text changes.
     */
    private void updateDisplay() {
        textArea.setText(editor.text);
    }

    /**
     * The main method to run the EditorGUI application.
     * It initializes the GUI on the Event Dispatch Thread (EDT).
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorGUI::new);
    }
}
