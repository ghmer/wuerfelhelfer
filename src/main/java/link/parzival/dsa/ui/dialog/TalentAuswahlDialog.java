package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import link.parzival.dsa.Konstanten;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.object.enumeration.FaehigkeitsTypEnum;

import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JLabel;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class TalentAuswahlDialog extends JDialog {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$

    /**
     * 
     */
    private static final long serialVersionUID              = 5001756584965996173L;
    public static final Logger _LOG                         = Logger.getLogger(TalentAuswahlDialog.class.getName());
    private final JPanel contentPanel                       = new JPanel();

    private JTextField textFieldSearch                      = null;
    private int state                                       = Konstanten.DIALOG_CANCEL_STATE;

    private String selectedAbilityName                      = null;
    private JComboBox<FaehigkeitsTypEnum> cbSelectedAbility = null;
    JScrollPane scrollPane                                  = null;
    TalentObjekt selectedAbility                            = null;

    /**
     * @param heldenObjekt ein HeldenObjekt
     */
    public TalentAuswahlDialog(HeldenObjekt heldenObjekt) {
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 0, 0, 102, 0 };
        gbl_contentPanel.rowHeights = new int[] { 28, 0, 12, 0 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);

        textFieldSearch = new JTextField();
        textFieldSearch.setFocusable(true);
        textFieldSearch.requestFocusInWindow();
        textFieldSearch.grabFocus();
        textFieldSearch.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {

            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                generateTable(heldenObjekt, e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generateTable(heldenObjekt, e);

            }
        });

        JLabel lblNewLabel = new JLabel(BUNDLE.getString("AbilityDialog.lblNewLabel.text")); //$NON-NLS-1$
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        contentPanel.add(lblNewLabel, gbc_lblNewLabel);
        GridBagConstraints gbc_textFieldSearch = new GridBagConstraints();
        gbc_textFieldSearch.anchor = GridBagConstraints.NORTH;
        gbc_textFieldSearch.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldSearch.insets = new Insets(0, 0, 5, 5);
        gbc_textFieldSearch.gridx = 1;
        gbc_textFieldSearch.gridy = 0;
        contentPanel.add(textFieldSearch, gbc_textFieldSearch);
        textFieldSearch.setColumns(10);

        cbSelectedAbility = new JComboBox<>();
        cbSelectedAbility.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                textFieldSearch.setText("");
                generateTable(heldenObjekt);
            }
        });
        cbSelectedAbility.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        cbSelectedAbility.setModel(new DefaultComboBoxModel<FaehigkeitsTypEnum>(FaehigkeitsTypEnum.values()));
        cbSelectedAbility.setSelectedIndex(0);
        cbSelectedAbility.transferFocus();
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.anchor = GridBagConstraints.SOUTH;
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.insets = new Insets(0, 0, 5, 0);
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 0;
        contentPanel.add(cbSelectedAbility, gbc_comboBox);

        JSeparator separator = new JSeparator();
        GridBagConstraints gbc_separator = new GridBagConstraints();
        gbc_separator.insets = new Insets(0, 0, 5, 0);
        gbc_separator.fill = GridBagConstraints.BOTH;
        gbc_separator.gridwidth = 3;
        gbc_separator.gridx = 0;
        gbc_separator.gridy = 1;
        contentPanel.add(separator, gbc_separator);

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton cancelButton = new JButton(BUNDLE.getString("AbilityDialog.cancelButton.text")); //$NON-NLS-1$
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                cancelButton.setActionCommand(BUNDLE.getString("AbilityDialog.cancelButton.actionCommand")); //$NON-NLS-1$
                buttonPane.add(cancelButton);
            }
        }

        generateTable(heldenObjekt);
    }

    private void generateTable(HeldenObjekt heldenObjekt, DocumentEvent e) {
        Document document = e.getDocument();
        String text = null;
        try {
            text = document.getText(0, document.getLength());
        } catch (BadLocationException e1) {
            _LOG.severe(e1.getMessage());
        }
        generateTable(heldenObjekt, text);
    }

    private void generateTable(HeldenObjekt heldenObjekt) {
        generateTable(heldenObjekt, new String());
    }

    private void generateTable(HeldenObjekt heldenObjekt, String searchText) {
        List<TalentObjekt> talente = heldenObjekt.getTalente();
        List<TalentObjekt> zauber = heldenObjekt.getZauber();
        List<TalentObjekt> matches = new ArrayList<>();

        FaehigkeitsTypEnum chosenEnum = (FaehigkeitsTypEnum) cbSelectedAbility.getSelectedItem();
        switch (chosenEnum) {
        case Talent: {
            prepareMatchingAbilityList(searchText, talente, matches);
            break;
        }
        case Magie: {
            prepareMatchingAbilityList(searchText, zauber, matches);
            break;
        }

        default:
            break;
        }

        // if(matches.size() > 0) {}

        String[][] tableRows = null;
        if (matches.size() > 0) {
            tableRows = new String[matches.size()][4];
            for (int i = 0; i < matches.size(); i++) {
                TalentObjekt objekt = matches.get(i);
                tableRows[i] = new String[] {
                        objekt.getName(), String.valueOf(objekt.getTalentwert()), String.format("(%s/%s/%s)",
                                objekt.getProbenTalent1(), objekt.getProbenTalent2(), objekt.getProbenTalent3()),
                        objekt.getBe() };
            }
        } else {
            tableRows = new String[1][4];
            tableRows[0] = new String[] { "Keine Einträge vorhanden", "", "", "" };
        }

        if (this.scrollPane != null) {
            contentPanel.remove(this.scrollPane);
        }

        String[] columnNames = { "Name des Talents/Zaubers", "TaW", "Probe", "BE" };
        JTable table = new JTable(tableRows, columnNames) {

            private static final long serialVersionUID = -5753532534134796588L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable sourceTable = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = sourceTable.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {

                    selectedAbilityName = (String) sourceTable.getValueAt(row, 0);
                    List<TalentObjekt> searchList = null;
                    switch((FaehigkeitsTypEnum)cbSelectedAbility.getSelectedItem()) {
                        case Magie:  {
                            searchList = heldenObjekt.getZauber();
                            break;
                        }
                        case Talent: {
                            searchList = heldenObjekt.getTalente();
                            break;
                        }
                    }
                    
                    if(searchList != null && selectedAbilityName != null) {
                        for(TalentObjekt objekt : searchList) {
                            if(objekt.getName().equalsIgnoreCase(selectedAbilityName)) {
                                selectedAbility = objekt;
                                break;
                            }
                        }
                    }
                    state = Konstanten.DIALOG_OK_STATE;
                    dispose();
                }
            }
        });

        table.setBorder(new LineBorder(new Color(0, 0, 0)));

        GridBagConstraints gbc_table_1 = new GridBagConstraints();
        gbc_table_1.gridwidth = 3;
        gbc_table_1.fill = GridBagConstraints.BOTH;
        gbc_table_1.gridx = 0;
        gbc_table_1.gridy = 2;
        table.repaint();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        contentPanel.updateUI();

        JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(table);

        contentPanel.add(scrollPane, gbc_table_1);
        this.scrollPane = scrollPane;

    }

    /**
     * @param text suchtext
     * @param talente die liste der Talente/Zauber
     * @param matches die Liste der Treffer
     */
    private void prepareMatchingAbilityList(String text, List<TalentObjekt> talente, List<TalentObjekt> matches) {
        for (TalentObjekt objekt : talente) {
            if (text != null && text.length() > 0) {
                if (objekt.getName().toLowerCase().startsWith(text.toLowerCase())) {
                    matches.add(objekt);
                }
            } else {
                matches.add(objekt);
            }
        }
    }

    public String getSelectedAbilityName() {
        return selectedAbilityName;
    }
    
    /**
     * @return Typ der ausgewählten Fähigkeit (Talent oder Magie)
     */
    public FaehigkeitsTypEnum getSelectedAbilityType() {
        return (FaehigkeitsTypEnum) cbSelectedAbility.getSelectedItem();
    }
    
    public TalentObjekt getSelectedAbility() {
        return this.selectedAbility;
    }

    public int showDialog() {
        setVisible(true);
        return state;
    }
}
