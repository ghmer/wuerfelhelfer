package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.ui.WuerfelHelferGUI;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.util.ResourceBundle;

public class PatzerTabellenDialog extends JDialog {

    public enum PatzerTyp {
        Nahkampf, Fernkampf
    }

    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$
    private static final long serialVersionUID = 7916749926071186310L;
    private final JPanel contentPanel = new JPanel();

    public static final String[] patzerBeschreibungenNahkampf = {
            "Auf jeden Fall INI -4 wegen Desorientierung; Patzender muss in der folgenden Runde Aktionen aufwenden, um eine neue Waffe zu ziehen. Handelt es sich bei der Waffe um eine sehr stabile Waffe mit einem BF von 0 oder weniger, so wird dieses Ergebnis als 9-10 (Waffe verloren) gewertet und der BF der Waffe steigt um 2 (was bei einer wirklich unzerstörbaren Waffe natürlich nichts ausmacht). Handelt es sich um eine natürliche Waffe (also Faust oder Fuß), so wird dieser Patzer gewertet wie eine 12 (Schwerer Eigentreffer).",
            "Der Patzende liegt auf dem Boden; er gilt in den folgenden Runden so lange als am Boden, bis er erfolgreich aufstehen kann: Dies benötigt eine Aktion 'Position' und eine um seine BE erschwerte GE-Probe. Ein Held mit der SF Standfest oder den Vorteilen (Herausragende) Balance kann einen Sturz in ein Stolpern verwandeln, wenn ihm eine GE-Probe (Standfest: +2, Balance: unmodifiziert; Herausragende Balance: -4), erschwert um die BE, gelingt. INI -2 wegen Desorientierung.",
            "Der Unglücksrabe verliert 2 Punkte INI wegen Desorientierung.",
            "Der Patzende muss in der folgenden Runde die Aktion 'Position' aufwenden, um eine GE-Probe abzulegen, bei deren Gelingen er an seine Waffe gelangt; oder aber er wechselt die Waffe oder flieht. Handelt es sich bei der Waffe um eine natürliche (angewachsene), so wird das Ergebnis als 3-5 (Sturz) gewertet. INI -2 wegen Desorientierung.",
            "Der Betroffene erleidet Waffenschaden durch eigene Waffe (TP auswürfeln; keine zusätzlichen TP aus hoher KK oder Ansagen) und eventuell sogar eine Wunde (bei mehr als KO/2 SP) mit den dort genannten Folgen; INI -3 wegen Desorientierung.",
            "INI -4. Der Betroffene erleidet schweren Schaden durch eigene Waffe (TP auswürfeln und verdoppeln; keine zusätzlichen TP aus hoher KK oder Ansagen) und eventuell sogar eine Wunde (bei mehr als KO/2 SP) mit den dort genannten Folgen; INI -4 wegen Desorientierung." };

    public static final String[] patzerBeschreibungenFernkampf = {
            "INI -4; der Schuss geht ungezielt daneben, und von irgendwo hört man ein hässliches Knacken. Bogen, Armbrust oder Speer sind so schwer beschädigt, dass eine Reparatur nicht lohnt. Der Schütze verliert alle verbleibenden Angriffs- und Abwehr-Aktionen dieser Runde.",
            "INI -3; der Schuss geht ungezielt daneben, das Projektil landet vor den Füßen des Schützen. Die Sehne des Bogens ist gerissen, die Mechanik der Armbrust ernsthaft verklemmt - es sind mindestens 30 Aktionen nötig, um die Waffe wieder schussfähig zu machen. Bei einer Wurfwaffe bedeutet dieses Resultat dasselbe wie eine 2: Messer oder Speer sind zerbrochen. Der Schütze verliert alle verbleibenden Angriffs- und Abwehr-Aktionen dieser Runde.",
            "INI -2; der Schuss geht ungezielt daneben, das Projektil ist verloren; die Mechanik der Armbrust blockiert oder die Sehne droht vom Bogen zu rutschen - der Schütze benötigt zwei Aktionen, um die Waffe wieder schussfähig zu machen. Bei Wurfwaffen gilt, dass das Projektil ebenfalls verloren ist und der Werfer zwei Aktionen benötigt, um sein verlorenes Gleichgewicht wiederzufinden.",
            "INI -3; der Schuss löst sich unbeabsichtigt und trifft den am nächsten an der geplanten Flugbahn stehenden befreundeten Helden. Würfeln Sie für diesen Trefferpunkte gemäß der Entfernung aus; Ansagen (aus angesagten Fernkampfangriffen) kommen jedoch nicht zum Tragen. Ist kein Gefährte in der Nähe, der getroffen werden könnte, hat der Schütze sich selbst verletzt (TP gemäß geringster Entfernung)." };

    /**
     * Create the dialog.
     * 
     * @param patzerTyp gibt den Typ der Patzertabelle an
     */
    public PatzerTabellenDialog(PatzerTyp patzerTyp) {
        setTitle(String.format("Patzertabelle: %s", patzerTyp.name()));
        setBounds(100, 100, 300, 275);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 0, 0 };
        gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_contentPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);
        {
            JButton btnPatzerWuerfeln = new JButton(BUNDLE.getString("PatzerTabellenDialog.btnPatzerWuerfeln.text")); //$NON-NLS-1$
            btnPatzerWuerfeln.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    WuerfelHelferGUI.copyToClipboard(DsaCalculatorUtil.getPatzerWurf(), getContentPane());
                }
            });
            GridBagConstraints gbc_btnPatzerWuerfeln = new GridBagConstraints();
            gbc_btnPatzerWuerfeln.fill = GridBagConstraints.HORIZONTAL;
            gbc_btnPatzerWuerfeln.insets = new Insets(0, 0, 5, 0);
            gbc_btnPatzerWuerfeln.gridx = 0;
            gbc_btnPatzerWuerfeln.gridy = 0;
            contentPanel.add(btnPatzerWuerfeln, gbc_btnPatzerWuerfeln);
        }
        {
            JPanel panel = new JPanel();
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.anchor = GridBagConstraints.NORTH;
            gbc_panel.fill = GridBagConstraints.HORIZONTAL;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 1;
            contentPanel.add(panel, gbc_panel);
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            {
                JSeparator separator = new JSeparator();
                panel.add(separator);
            }
        }
        {
            GridBagConstraints gbc_table = new GridBagConstraints();
            gbc_table.insets = new Insets(0, 0, 5, 0);
            gbc_table.fill = GridBagConstraints.BOTH;
            gbc_table.gridx = 0;
            gbc_table.gridy = 2;

            String[][] tableRows = prepareTableEntries(patzerTyp);

            String[] columnNames = { "2w6", "Auswirkung" };
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

                    switch (patzerTyp) {
                    case Nahkampf: {
                        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                            if (row >= 0 && row <= patzerBeschreibungenNahkampf.length) {
                                String patzerBeschreibung = patzerBeschreibungenNahkampf[row];
                                PatzerBeschreibungDialog patzerBeschreibungDialog = new PatzerBeschreibungDialog(
                                        patzerBeschreibung);
                                patzerBeschreibungDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                patzerBeschreibungDialog.setLocationRelativeTo(getRootPane());
                                patzerBeschreibungDialog.setFont(getFont());
                                patzerBeschreibungDialog.setVisible(true);
                            }
                        }
                        break;
                    }
                    case Fernkampf: {
                        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                            if (row >= 0 && row <= patzerBeschreibungenFernkampf.length) {
                                String patzerBeschreibung = patzerBeschreibungenFernkampf[row];
                                PatzerBeschreibungDialog patzerBeschreibungDialog = new PatzerBeschreibungDialog(
                                        patzerBeschreibung);
                                patzerBeschreibungDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                patzerBeschreibungDialog.setLocationRelativeTo(getRootPane());
                                patzerBeschreibungDialog.setFont(getFont());
                                patzerBeschreibungDialog.setVisible(true);
                            }
                        }
                        break;
                    }
                    }
                }
            });

            table.getColumnModel().getColumn(0).setMaxWidth(75);

            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            contentPanel.updateUI();
            JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setViewportView(table);

            contentPanel.add(scrollPane, gbc_table);

        }
        {
            JLabel lblClickForMoreInfo = new JLabel(BUNDLE.getString("PatzerTabellenDialog.lblClickForMoreInfo.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblClickForMoreInfo = new GridBagConstraints();
            gbc_lblClickForMoreInfo.gridx = 0;
            gbc_lblClickForMoreInfo.gridy = 3;
            contentPanel.add(lblClickForMoreInfo, gbc_lblClickForMoreInfo);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton(BUNDLE.getString("PatzerTabellenDialog.okButton.text")); //$NON-NLS-1$
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                okButton.setActionCommand(BUNDLE.getString("PatzerTabellenDialog.okButton.actionCommand")); //$NON-NLS-1$
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
        }
    }

    private String[][] prepareTableEntries(PatzerTyp patzerTyp) {
        String[][] tableRows = null;
        switch (patzerTyp) {
        case Nahkampf: {
            tableRows = new String[6][2];
            tableRows[0] = new String[] { "2", "Waffe zerstört" };
            tableRows[1] = new String[] { "3-5", "Sturz" };
            tableRows[2] = new String[] { "6-8", "Stolpern" };
            tableRows[3] = new String[] { "9-10", "Waffe verloren" };
            tableRows[4] = new String[] { "11", "An eigener Waffe verletzt" };
            tableRows[5] = new String[] { "12", "Schwerer Eigentreffer" };
            break;
        }

        case Fernkampf: {
            tableRows = new String[4][2];
            tableRows[0] = new String[] { "2", "Waffe zerstört" };
            tableRows[1] = new String[] { "3", "Waffe beschädigt" };
            tableRows[2] = new String[] { "4-10", "Fehlschuss" };
            tableRows[3] = new String[] { "11-12", "Kameraden getroffen" };
            break;
        }

        default: {
            tableRows = new String[0][0];
        }
        }

        return tableRows;
    }

}
