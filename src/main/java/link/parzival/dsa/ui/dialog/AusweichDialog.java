
package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.Konstanten;
import link.parzival.dsa.BerechnungsHelfer;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.enumeration.DKEnum;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ResourceBundle;

public class AusweichDialog extends JDialog {

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$

    private static final long serialVersionUID      = 6393904558592079317L;
    private JCheckBox chkboxGezieltesAusweichen     = new JCheckBox();
    private JComboBox<String> comboBoxEnemyCount    = new JComboBox<>();
    private final JPanel contentPanel               = new JPanel();

    private int enemyCount                          = 1;
    private boolean gezieltesAusweichen             = false;
    private JLabel lblEvasionNotPossible            = new JLabel();

    private JButton okButton                        = new JButton();
    private String rollCommand                      = null;
    private int state                               = Konstanten.DIALOG_CANCEL_STATE;

    /**
     * @param hero
     *            the HeldenObjekt to set
     * @param initiative
     *            the Initiative to use
     * @param withDk
     *            whether to use Distanzklassen
     * @param distanzklasse
     *            the Distanzklasse to use
     */
    public AusweichDialog(HeldenObjekt hero, int initiative, boolean withDk, DKEnum distanzklasse) {
        setBounds(100, 100, 450, 130);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setModal(true);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 175, 51, 125, 75, 0 };
        gbl_contentPanel.rowHeights = new int[] { 27, 16, 0 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);

        lblEvasionNotPossible = new JLabel(BUNDLE.getString("EvasionDialog.lblEvasionNotPossible.text")); //$NON-NLS-1$
        lblEvasionNotPossible.setForeground(Color.RED);
        lblEvasionNotPossible.setVisible(false);

        comboBoxEnemyCount = new JComboBox<>();
        comboBoxEnemyCount.setModel(new DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "mehr" }));
        comboBoxEnemyCount.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                @SuppressWarnings("unchecked")
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String selection = (String) cb.getSelectedItem();

                if (selection.equalsIgnoreCase("mehr")) {
                    lblEvasionNotPossible.setVisible(true);
                    okButton.setEnabled(false);
                } else {
                    enemyCount = Integer.parseInt(selection);
                    lblEvasionNotPossible.setVisible(false);
                    lblEvasionNotPossible.setFont(getFont().deriveFont(Font.BOLD, 13));
                    okButton.setEnabled(true);
                }
            }
        });
        chkboxGezieltesAusweichen = new JCheckBox(BUNDLE.getString("EvasionDialog.chkboxGezieltesAusweichen.text")); //$NON-NLS-1$
        chkboxGezieltesAusweichen.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox source = (JCheckBox) e.getSource();
                gezieltesAusweichen = source.isSelected();
            }
        });
        GridBagConstraints gbc_chkboxGezieltesAusweichen = new GridBagConstraints();
        gbc_chkboxGezieltesAusweichen.anchor = GridBagConstraints.NORTH;
        gbc_chkboxGezieltesAusweichen.fill = GridBagConstraints.HORIZONTAL;
        gbc_chkboxGezieltesAusweichen.insets = new Insets(0, 0, 5, 5);
        gbc_chkboxGezieltesAusweichen.gridx = 0;
        gbc_chkboxGezieltesAusweichen.gridy = 0;
        contentPanel.add(chkboxGezieltesAusweichen, gbc_chkboxGezieltesAusweichen);

        JLabel lblEnemyCount = new JLabel(BUNDLE.getString("EvasionDialog.lblEnemyCount.text")); //$NON-NLS-1$
        lblEnemyCount.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblEnemyCount = new GridBagConstraints();
        gbc_lblEnemyCount.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblEnemyCount.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnemyCount.gridx = 2;
        gbc_lblEnemyCount.gridy = 0;
        contentPanel.add(lblEnemyCount, gbc_lblEnemyCount);

        GridBagConstraints gbc_comboBoxEnemyCount = new GridBagConstraints();
        gbc_comboBoxEnemyCount.anchor = GridBagConstraints.NORTH;
        gbc_comboBoxEnemyCount.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxEnemyCount.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxEnemyCount.gridx = 3;
        gbc_comboBoxEnemyCount.gridy = 0;
        contentPanel.add(comboBoxEnemyCount, gbc_comboBoxEnemyCount);

        lblEvasionNotPossible.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblEvasionNotPossible = new GridBagConstraints();
        gbc_lblEvasionNotPossible.anchor = GridBagConstraints.NORTH;
        gbc_lblEvasionNotPossible.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblEvasionNotPossible.gridwidth = 4;
        gbc_lblEvasionNotPossible.gridx = 0;
        gbc_lblEvasionNotPossible.gridy = 1;
        contentPanel.add(lblEvasionNotPossible, gbc_lblEvasionNotPossible);

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        okButton = new JButton(BUNDLE.getString("EvasionDialog.okButton.text")); //$NON-NLS-1$
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setRollCommand(BerechnungsHelfer.berechneEffektivesAusweichen(hero, initiative, enemyCount,
                        gezieltesAusweichen, withDk, distanzklasse));
                state = Konstanten.DIALOG_OK_STATE;
                dispose();
            }
        });
        okButton.setActionCommand(BUNDLE.getString("EvasionDialog.okButton.actionCommand")); //$NON-NLS-1$
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton(BUNDLE.getString("EvasionDialog.cancelButton.text")); //$NON-NLS-1$
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                state = Konstanten.DIALOG_CANCEL_STATE;
                dispose();
            }
        });
        cancelButton.setActionCommand(BUNDLE.getString("EvasionDialog.cancelButton.actionCommand")); //$NON-NLS-1$
        buttonPane.add(cancelButton);

    }

    /**
     * @return the rollCommand
     */
    public String getRollCommand() {
        return rollCommand;
    }

    /**
     * @param rollCommand
     *            the rollCommand to set
     */
    public void setRollCommand(String rollCommand) {
        this.rollCommand = rollCommand;
    }

    /**
     * @return the state of the dialog
     */
    public int showDialog() {
        setVisible(true);
        return state;
    }

}
