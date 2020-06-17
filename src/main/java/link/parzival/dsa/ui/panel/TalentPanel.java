package link.parzival.dsa.ui.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import link.parzival.dsa.Konstanten;
import link.parzival.dsa.BerechnungsHelfer;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.object.TalentObjektPlatzhalter;
import link.parzival.dsa.object.enumeration.EigenschaftEnum;
import link.parzival.dsa.ui.WuerfelHelferGUI;
import link.parzival.dsa.ui.dialog.TalentAuswahlDialog;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import java.awt.BorderLayout;
import java.util.ResourceBundle;
import java.awt.Font;

public class TalentPanel extends JPanel {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$
    
    /**
     * 
     */
    private static final long serialVersionUID           = -4466855899017287952L;
    private JComboBox<EigenschaftEnum> pruefEigenschaft1 = null;
    private JComboBox<EigenschaftEnum> pruefEigenschaft2 = null;
    private JComboBox<EigenschaftEnum> pruefEigenschaft3 = null;
    private JSpinner pruefModifier                       = null;
    private TalentObjekt talent                          = new TalentObjektPlatzhalter();
    private HeldenObjekt heldenObjekt                    = new HeldenObjekt();
    /**
     * @return the hero
     */
    public HeldenObjekt holeHeldenObjekt() {
        return heldenObjekt;
    }

    /**
     * @param heldenObjekt ein HeldenObjekt
     */
    public void setzeHeldenObjekt(HeldenObjekt heldenObjekt) {
        this.heldenObjekt = heldenObjekt;
    }

    private JLabel lblAbility;
    private JLabel lblTawValue;

    public TalentPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{10, 180, 40, 75, 75, 75, 75, 100, 0};
        gridBagLayout.rowHeights = new int[]{15, 0, 15, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        
        JPanel separatorPanel1 = new JPanel();
        GridBagConstraints gbc_separatorPanel1 = new GridBagConstraints();
        gbc_separatorPanel1.gridwidth = 8;
        gbc_separatorPanel1.insets = new Insets(0, 0, 5, 0);
        gbc_separatorPanel1.fill = GridBagConstraints.BOTH;
        gbc_separatorPanel1.gridx = 0;
        gbc_separatorPanel1.gridy = 0;
        add(separatorPanel1, gbc_separatorPanel1);
        separatorPanel1.setLayout(new BorderLayout(0, 0));
        
        JSeparator separator = new JSeparator();
        separatorPanel1.add(separator, BorderLayout.NORTH);
        
        JButton btnProbeWaehlen = new JButton(BUNDLE.getString("TalentPanel.btnProbeWaehlen.text")); //$NON-NLS-1$
        
        btnProbeWaehlen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TalentAuswahlDialog dialog = new TalentAuswahlDialog(heldenObjekt);
                dialog.setFont(getFont());
                dialog.setLocationRelativeTo(getRootPane());
                switch (dialog.showDialog()) {
                case Konstanten.DIALOG_OK_STATE:
                    TalentObjekt talentObjekt = dialog.getSelectedAbility();
                    if(talentObjekt != null) {
                        setTalentProbe(talentObjekt);
                    }
                    
                    break;
                }
            }
        });
        
        JLabel lblTalenteHeadline = new JLabel(BUNDLE.getString("TalentPanel.lblTalenteHeadline.text")); //$NON-NLS-1$
        lblTalenteHeadline.setFont(getFont().deriveFont(Font.BOLD, 15));
        GridBagConstraints gbc_lblTalenteHeadline = new GridBagConstraints();
        gbc_lblTalenteHeadline.anchor = GridBagConstraints.WEST;
        gbc_lblTalenteHeadline.insets = new Insets(0, 0, 5, 5);
        gbc_lblTalenteHeadline.gridx = 1;
        gbc_lblTalenteHeadline.gridy = 1;
        add(lblTalenteHeadline, gbc_lblTalenteHeadline);
        GridBagConstraints gbc_btnProbeWaehlen = new GridBagConstraints();
        gbc_btnProbeWaehlen.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnProbeWaehlen.gridwidth = 5;
        gbc_btnProbeWaehlen.insets = new Insets(0, 0, 5, 0);
        gbc_btnProbeWaehlen.gridx = 3;
        gbc_btnProbeWaehlen.gridy = 1;
        add(btnProbeWaehlen, gbc_btnProbeWaehlen);
        
        JPanel separatorPanel2 = new JPanel();
        GridBagConstraints gbc_separatorPanel2 = new GridBagConstraints();
        gbc_separatorPanel2.gridwidth = 8;
        gbc_separatorPanel2.insets = new Insets(0, 0, 5, 0);
        gbc_separatorPanel2.fill = GridBagConstraints.BOTH;
        gbc_separatorPanel2.gridx = 0;
        gbc_separatorPanel2.gridy = 2;
        add(separatorPanel2, gbc_separatorPanel2);
        separatorPanel2.setLayout(new BorderLayout(0, 0));
        
        JSeparator separator_1 = new JSeparator();
        separatorPanel2.add(separator_1, BorderLayout.NORTH);
        
        JLabel lblTalentName = new JLabel(BUNDLE.getString("TalentPanel.lblTalentName.text")); //$NON-NLS-1$
        lblTalentName.setFont(getFont().deriveFont(Font.BOLD, 13));
        GridBagConstraints gbc_lblTalentName = new GridBagConstraints();
        gbc_lblTalentName.anchor = GridBagConstraints.NORTH;
        gbc_lblTalentName.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblTalentName.insets = new Insets(0, 0, 5, 5);
        gbc_lblTalentName.gridx = 1;
        gbc_lblTalentName.gridy = 3;
        add(lblTalentName, gbc_lblTalentName);
        
        JLabel lblTaw = new JLabel(BUNDLE.getString("TalentPanel.lblTaw.text")); //$NON-NLS-1$
        lblTaw.setFont(getFont().deriveFont(Font.BOLD, 13));
        lblTaw.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblTaw = new GridBagConstraints();
        gbc_lblTaw.anchor = GridBagConstraints.NORTH;
        gbc_lblTaw.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblTaw.insets = new Insets(0, 0, 5, 5);
        gbc_lblTaw.gridx = 2;
        gbc_lblTaw.gridy = 3;
        add(lblTaw, gbc_lblTaw);
        
        JLabel lblE1 = new JLabel(BUNDLE.getString("TalentPanel.lblE1.text")); //$NON-NLS-1$
        lblE1.setFont(getFont().deriveFont(Font.BOLD, 13));
        lblE1.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblE1 = new GridBagConstraints();
        gbc_lblE1.anchor = GridBagConstraints.NORTH;
        gbc_lblE1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblE1.insets = new Insets(0, 0, 5, 5);
        gbc_lblE1.gridx = 3;
        gbc_lblE1.gridy = 3;
        add(lblE1, gbc_lblE1);
        
        JLabel lblE2 = new JLabel(BUNDLE.getString("TalentPanel.lblE2.text")); //$NON-NLS-1$
        lblE2.setFont(getFont().deriveFont(Font.BOLD, 13));
        lblE2.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblE2 = new GridBagConstraints();
        gbc_lblE2.anchor = GridBagConstraints.NORTH;
        gbc_lblE2.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblE2.insets = new Insets(0, 0, 5, 5);
        gbc_lblE2.gridx = 4;
        gbc_lblE2.gridy = 3;
        add(lblE2, gbc_lblE2);
        
        JLabel lblE3 = new JLabel(BUNDLE.getString("TalentPanel.lblE3.text")); //$NON-NLS-1$
        lblE3.setFont(getFont().deriveFont(Font.BOLD, 13));
        lblE3.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblE3 = new GridBagConstraints();
        gbc_lblE3.anchor = GridBagConstraints.NORTH;
        gbc_lblE3.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblE3.insets = new Insets(0, 0, 5, 5);
        gbc_lblE3.gridx = 5;
        gbc_lblE3.gridy = 3;
        add(lblE3, gbc_lblE3);
        
        JLabel lblMod = new JLabel(BUNDLE.getString("TalentPanel.lblMod.text")); //$NON-NLS-1$
        lblMod.setFont(getFont().deriveFont(Font.BOLD, 13));
        lblMod.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblMod = new GridBagConstraints();
        gbc_lblMod.anchor = GridBagConstraints.NORTH;
        gbc_lblMod.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblMod.insets = new Insets(0, 0, 5, 5);
        gbc_lblMod.gridx = 6;
        gbc_lblMod.gridy = 3;
        add(lblMod, gbc_lblMod);
        
        lblAbility = new JLabel(talent.getName());
        GridBagConstraints gbc_lblAbility = new GridBagConstraints();
        gbc_lblAbility.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblAbility.insets = new Insets(0, 0, 0, 5);
        gbc_lblAbility.gridx = 1;
        gbc_lblAbility.gridy = 4;
        add(lblAbility, gbc_lblAbility);
        
        lblTawValue = new JLabel(String.valueOf(talent.getTalentwert()));
        lblTawValue.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblTawValue = new GridBagConstraints();
        gbc_lblTawValue.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblTawValue.insets = new Insets(0, 0, 0, 5);
        gbc_lblTawValue.gridx = 2;
        gbc_lblTawValue.gridy = 4;
        add(lblTawValue, gbc_lblTawValue);
        
        JButton btnCreateRoll = new JButton(BUNDLE.getString("TalentPanel.btnCreateRoll.text")); //$NON-NLS-1$
        btnCreateRoll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(getTalentProbe() == null) {
                    JOptionPane.showMessageDialog(getRootPane(), "Es wurde noch keine Probe ausgewählt!");
                    return;
                }
                EigenschaftEnum pruefung1 = (EigenschaftEnum)pruefEigenschaft1.getSelectedItem();
                EigenschaftEnum pruefung2 = (EigenschaftEnum)pruefEigenschaft2.getSelectedItem();
                EigenschaftEnum pruefung3 = (EigenschaftEnum)pruefEigenschaft3.getSelectedItem();
                int modifier              = (Integer)pruefModifier.getValue();
                
                int pruefAuswahl = 0;
                if(pruefung1.equals(EigenschaftEnum.NA)) pruefAuswahl += 1;
                if(pruefung2.equals(EigenschaftEnum.NA)) pruefAuswahl += 10;
                if(pruefung3.equals(EigenschaftEnum.NA)) pruefAuswahl += 100;
                
                int effectiveModifier     = BerechnungsHelfer.berechneEffektivenTalentModifikator(modifier, heldenObjekt.getBehinderung(), talent);
                String rollCommand        = null;
                
                switch(pruefAuswahl) {
                case 1  : {
                    // erste Auswahl war NA
                    String rollFormatString   = "!%s,%s,%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            heldenObjekt.getFertigkeit(pruefung2),
                            heldenObjekt.getFertigkeit(pruefung3),
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
                case 10 : {
                    // zweite Auswahl war NA
                    String rollFormatString   = "!%s,%s,%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            heldenObjekt.getFertigkeit(pruefung1),
                            heldenObjekt.getFertigkeit(pruefung3),
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
                case 11 : {
                    // erste und zweite Auswahl war NA
                    String rollFormatString   = "!%s,%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            heldenObjekt.getFertigkeit(pruefung3),
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
                case 100: {
                    // dritte Auswahl war NA
                    String rollFormatString   = "!%s,%s,%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            heldenObjekt.getFertigkeit(pruefung1),
                            heldenObjekt.getFertigkeit(pruefung2),
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
                case 101: {
                    // erste und dritte Auswahl war NA
                    String rollFormatString   = "!%s,%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            heldenObjekt.getFertigkeit(pruefung2),
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
                case 110: {
                    // Auswahl zwei und drei waren NA
                    String rollFormatString   = "!%s,%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            heldenObjekt.getFertigkeit(pruefung1),
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
                case 111: {
                    // alle Auswahlen waren NA. Prüfe auf Talentwert
                    String rollFormatString   = "!%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
                default : {
                    String rollFormatString   = "!%s,%s,%s,%s,%s  %s";
                    rollCommand  = String.format(rollFormatString, 
                            heldenObjekt.getFertigkeit(pruefung1),
                            heldenObjekt.getFertigkeit(pruefung2),
                            heldenObjekt.getFertigkeit(pruefung3),
                            talent.getTalentwert(),
                            effectiveModifier,
                            talent.getName());
                    break;
                }
            }
                            
            WuerfelHelferGUI.copyToClipboard(rollCommand);            
            }
        });
        
        pruefEigenschaft1 = new JComboBox<>();
        pruefEigenschaft1.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
        pruefEigenschaft1.setSelectedItem(talent.getProbenTalent1());
        GridBagConstraints gbc_pruefEigenschaft1 = new GridBagConstraints();
        gbc_pruefEigenschaft1.fill = GridBagConstraints.HORIZONTAL;
        gbc_pruefEigenschaft1.insets = new Insets(0, 0, 0, 5);
        gbc_pruefEigenschaft1.gridx = 3;
        gbc_pruefEigenschaft1.gridy = 4;
        add(pruefEigenschaft1, gbc_pruefEigenschaft1);
        
        pruefEigenschaft2 = new JComboBox<>();
        pruefEigenschaft2.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
        pruefEigenschaft2.setSelectedItem(talent.getProbenTalent2());
        GridBagConstraints gbc_pruefEigenschaft2 = new GridBagConstraints();
        gbc_pruefEigenschaft2.fill = GridBagConstraints.HORIZONTAL;
        gbc_pruefEigenschaft2.insets = new Insets(0, 0, 0, 5);
        gbc_pruefEigenschaft2.gridx = 4;
        gbc_pruefEigenschaft2.gridy = 4;
        add(pruefEigenschaft2, gbc_pruefEigenschaft2);
        
        pruefEigenschaft3 = new JComboBox<>();
        pruefEigenschaft3.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
        pruefEigenschaft3.setSelectedItem(talent.getProbenTalent3());
        GridBagConstraints gbc_pruefEigenschaft3 = new GridBagConstraints();
        gbc_pruefEigenschaft3.fill = GridBagConstraints.HORIZONTAL;
        gbc_pruefEigenschaft3.insets = new Insets(0, 0, 0, 5);
        gbc_pruefEigenschaft3.gridx = 5;
        gbc_pruefEigenschaft3.gridy = 4;
        add(pruefEigenschaft3, gbc_pruefEigenschaft3);
        
        pruefModifier = new JSpinner();
        GridBagConstraints gbc_pruefModifier = new GridBagConstraints();
        gbc_pruefModifier.fill = GridBagConstraints.HORIZONTAL;
        gbc_pruefModifier.insets = new Insets(0, 0, 0, 5);
        gbc_pruefModifier.gridx = 6;
        gbc_pruefModifier.gridy = 4;
        add(pruefModifier, gbc_pruefModifier);
        GridBagConstraints gbc_btnCreateRoll = new GridBagConstraints();
        gbc_btnCreateRoll.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnCreateRoll.gridx = 7;
        gbc_btnCreateRoll.gridy = 4;
        add(btnCreateRoll, gbc_btnCreateRoll);
    }

    protected void setTalentProbe(TalentObjekt talentObjekt) {
        this.talent = talentObjekt;
        pruefEigenschaft1.setSelectedItem(talentObjekt.getProbenTalent1());
        pruefEigenschaft2.setSelectedItem(talentObjekt.getProbenTalent2());
        pruefEigenschaft3.setSelectedItem(talentObjekt.getProbenTalent3());
        pruefModifier.setValue(0);
        
        lblAbility.setText(talentObjekt.getName());
        lblTawValue.setText(String.valueOf(talentObjekt.getTalentwert()));
    }
    
    protected TalentObjekt getTalentProbe() {
        return this.talent;
    }
}
