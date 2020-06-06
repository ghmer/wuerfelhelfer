package link.parzival.dsa.ui.panel;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import link.parzival.dsa.Konstanten;
import link.parzival.dsa.BerechnungsHelfer;
import link.parzival.dsa.object.FernwaffenObjekt;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.ParadeObjekt;
import link.parzival.dsa.object.WaffenObjekt;
import link.parzival.dsa.object.enumeration.AttackeOptionEnum;
import link.parzival.dsa.object.enumeration.DKEnum;
import link.parzival.dsa.object.enumeration.ParadeOptionEnum;
import link.parzival.dsa.ui.WuerfelHelferGUI;
import link.parzival.dsa.ui.dialog.DistanzwechselDialog;
import link.parzival.dsa.ui.dialog.AusweichDialog;
import link.parzival.dsa.ui.dialog.FernkampfDialog;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;
import javax.swing.JSpinner;
import java.util.ResourceBundle;

public class KampfPanel extends JPanel {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$
    private static final long serialVersionUID              = -4466855899017287952L;
    private JComboBox<String> comboBoxRightWeaponHand       = null;
    private JLabel lblRightWeaponHand                       = null;
    private JComboBox<String> comboBoxOwnDK                 = null;
    private JCheckBox chkUseDK                              = null;
    private JComboBox<DKEnum> comboBoxCurrentDK             = null;
    private JLabel lblCurrentDK                             = null;
    private JLabel lblKampfHeadline                         = null;
    private JButton btnInitiative                           = null;
    private JButton btnAusweichen                           = null;
    private JLabel lblSchild                                = null;
    private JComboBox<String> comboBoxSchildhand            = null;
    private JLabel lblAttackeModifier                       = null;
    private JButton btnAttacke                              = null;
    private JLabel lblParade                                = null;
    private JComboBox<ParadeOptionEnum> comboBoxParade      = null;
    private JLabel lblParadeModifier                        = null;
    private JSpinner paradeModifier                         = null;
    private JButton btnParade                               = null;
    private JButton btnHopsen                               = null;

    private WaffenObjekt waffenObjekt                       = null;
    private ParadeObjekt paradeObjekt                       = null;
    private FernwaffenObjekt fernwaffenObjekt               = null;
    private JButton btnFernkampf                            = null;
    private JLabel lblInitiativeEingeben                    = null;
    private JSpinner tfInitiative                           = null;
    private JSpinner attackeModifier                        = null;

    HeldenObjekt hero                                       = null;
    
    private JPanel separatorPanel1                          = null;
    private JSeparator separator_1                          = null;
    private JPanel separatorPanel2                          = null;
    private JSeparator separator_2                          = null;
    private JPanel separatorPanel3                          = null;
    private JSeparator separator_3                          = null;
    private JPanel separatorPanel4                          = null;
    private JSeparator separator_4                          = null;
    private JLabel lblFernkampfWaffe                        = null;
    private JComboBox<String> comboBoxFernkampfwaffe        = null;
    private JLabel lblAttackeMit                            = null;
    private JComboBox<AttackeOptionEnum> comboBoxAttacke    = null;
    private JLabel lblDkWaffe                               = null;

    /**
     * @return das HeldenObjekt
     */
    public HeldenObjekt holeHeldenObjekt() {
        return hero;
    }

    /**
     * @param heldenObjekt Ein HeldenObjekt
     */
    public void setzeHeldenObjekt(HeldenObjekt heldenObjekt) {
        this.hero = heldenObjekt;
        if (heldenObjekt.getWaffen().size() > 0) {
            setWaffenObjekt(heldenObjekt.getWaffen().get(0));
        }

        if (heldenObjekt.getParadeWaffen().size() > 0) {
            setParadeObjekt(heldenObjekt.getParadeWaffen().get(0));
        }

        if (heldenObjekt.getFernWaffen().size() > 0) {
            setFernwaffenObjekt(heldenObjekt.getFernWaffen().get(0));
        }

        comboBoxRightWeaponHand.setModel(new DefaultComboBoxModel<>(heldenObjekt.getWaffenNamenAsArray()));
        comboBoxSchildhand.setModel(new DefaultComboBoxModel<>(heldenObjekt.getParadeWaffenNamenAsArray()));
        comboBoxSchildhand.insertItemAt("", 0);
        comboBoxFernkampfwaffe.setModel(new DefaultComboBoxModel<>(heldenObjekt.getFernWaffenNamenAsArray()));
        updateDistanzklasse();
    }

    @SuppressWarnings("unchecked")
    public KampfPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 10, 0, 40, 40, 75, 75, 75, 75, 100, 0 };
        gridBagLayout.rowHeights = new int[] { 15, 30, 15, 27, 27, 0, 15, 29, 29, 15, 29, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        setLayout(gridBagLayout);

        separatorPanel1 = new JPanel();
        GridBagConstraints gbc_separatorPanel1 = new GridBagConstraints();
        gbc_separatorPanel1.gridwidth = 9;
        gbc_separatorPanel1.insets = new Insets(0, 0, 5, 0);
        gbc_separatorPanel1.fill = GridBagConstraints.BOTH;
        gbc_separatorPanel1.gridx = 0;
        gbc_separatorPanel1.gridy = 0;
        add(separatorPanel1, gbc_separatorPanel1);
        separatorPanel1.setLayout(new BorderLayout(0, 0));

        separator_1 = new JSeparator();
        separatorPanel1.add(separator_1, BorderLayout.NORTH);

        lblKampfHeadline = new JLabel(BUNDLE.getString("KampfPanel.lblKampfHeadline.text")); //$NON-NLS-1$
        lblKampfHeadline.setFont(new Font("Lucida Grande", Font.BOLD, 15));
        GridBagConstraints gbc_lblKampfHeadline = new GridBagConstraints();
        gbc_lblKampfHeadline.anchor = GridBagConstraints.WEST;
        gbc_lblKampfHeadline.insets = new Insets(0, 0, 5, 5);
        gbc_lblKampfHeadline.gridx = 1;
        gbc_lblKampfHeadline.gridy = 1;
        add(lblKampfHeadline, gbc_lblKampfHeadline);

        chkUseDK = new JCheckBox(BUNDLE.getString("KampfPanel.chkUseDK.text")); //$NON-NLS-1$
        chkUseDK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBox sourceOfEvent = (JCheckBox) e.getSource();
                enableDkElements(sourceOfEvent.isSelected());
            }
        });

        btnFernkampf = new JButton(BUNDLE.getString("KampfPanel.btnFernkampf.text")); //$NON-NLS-1$
        btnFernkampf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (getFernwaffenObjekt() == null) {
                    JOptionPane.showMessageDialog(getParent(), "Du hast doch gar keine Fernkampfwaffe!");
                    return;
                }
                FernkampfDialog dialog = new FernkampfDialog(getFernwaffenObjekt());
                dialog.setFont(getFont());
                dialog.setLocationRelativeTo(btnFernkampf.getRootPane());
                switch (dialog.showDialog()) {
                case Konstanten.DIALOG_OK_STATE:
                    WuerfelHelferGUI.copyToClipboard(dialog.getRollCommand());
                    break;
                }

            }
        });

        btnInitiative = new JButton(BUNDLE.getString("KampfPanel.btnInitiative.text")); //$NON-NLS-1$
        btnInitiative.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String rollCommand = BerechnungsHelfer.berechneEffektiveInitiative(hero, waffenObjekt, paradeObjekt);
                WuerfelHelferGUI.copyToClipboard(rollCommand);
            }
        });
        GridBagConstraints gbc_btnInitiative = new GridBagConstraints();
        gbc_btnInitiative.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnInitiative.gridwidth = 2;
        gbc_btnInitiative.insets = new Insets(0, 0, 5, 5);
        gbc_btnInitiative.gridx = 4;
        gbc_btnInitiative.gridy = 1;
        add(btnInitiative, gbc_btnInitiative);

        lblInitiativeEingeben = new JLabel(BUNDLE.getString("KampfPanel.lblInitiativeEingeben.text")); //$NON-NLS-1$
        lblInitiativeEingeben.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblInitiativeEingeben = new GridBagConstraints();
        gbc_lblInitiativeEingeben.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblInitiativeEingeben.insets = new Insets(0, 0, 5, 5);
        gbc_lblInitiativeEingeben.gridx = 6;
        gbc_lblInitiativeEingeben.gridy = 1;
        add(lblInitiativeEingeben, gbc_lblInitiativeEingeben);

        tfInitiative = new JSpinner();
        GridBagConstraints gbc_tfInitiative = new GridBagConstraints();
        gbc_tfInitiative.fill = GridBagConstraints.HORIZONTAL;
        gbc_tfInitiative.insets = new Insets(0, 0, 5, 5);
        gbc_tfInitiative.gridx = 7;
        gbc_tfInitiative.gridy = 1;
        add(tfInitiative, gbc_tfInitiative);
        GridBagConstraints gbc_btnFernkampf = new GridBagConstraints();
        gbc_btnFernkampf.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnFernkampf.insets = new Insets(0, 0, 5, 0);
        gbc_btnFernkampf.gridx = 8;
        gbc_btnFernkampf.gridy = 1;
        add(btnFernkampf, gbc_btnFernkampf);

        separatorPanel2 = new JPanel();
        GridBagConstraints gbc_separatorPanel2 = new GridBagConstraints();
        gbc_separatorPanel2.gridwidth = 9;
        gbc_separatorPanel2.insets = new Insets(0, 0, 5, 0);
        gbc_separatorPanel2.fill = GridBagConstraints.BOTH;
        gbc_separatorPanel2.gridx = 0;
        gbc_separatorPanel2.gridy = 2;
        add(separatorPanel2, gbc_separatorPanel2);
        separatorPanel2.setLayout(new BorderLayout(0, 0));

        separator_2 = new JSeparator();
        separatorPanel2.add(separator_2, BorderLayout.NORTH);

        lblRightWeaponHand = new JLabel(BUNDLE.getString("KampfPanel.lblRightWeaponHand.text")); //$NON-NLS-1$
        lblRightWeaponHand.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblRightWeaponHand = new GridBagConstraints();
        gbc_lblRightWeaponHand.anchor = GridBagConstraints.EAST;
        gbc_lblRightWeaponHand.insets = new Insets(0, 0, 5, 5);
        gbc_lblRightWeaponHand.gridx = 1;
        gbc_lblRightWeaponHand.gridy = 3;
        add(lblRightWeaponHand, gbc_lblRightWeaponHand);

        comboBoxRightWeaponHand = new JComboBox<>();
        comboBoxRightWeaponHand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String waffenName = (String) cb.getSelectedItem();

                setWaffenObjekt(hero.getWaffeByName(waffenName));
                updateDistanzklasse();
            }
        });

        GridBagConstraints gbc_comboBoxRightWeaponHand = new GridBagConstraints();
        gbc_comboBoxRightWeaponHand.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxRightWeaponHand.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxRightWeaponHand.gridwidth = 7;
        gbc_comboBoxRightWeaponHand.gridx = 2;
        gbc_comboBoxRightWeaponHand.gridy = 3;
        add(comboBoxRightWeaponHand, gbc_comboBoxRightWeaponHand);

        comboBoxSchildhand = new JComboBox<>();
        comboBoxSchildhand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> source = (JComboBox<String>) e.getSource();
                String paradeObjektName = (String) source.getSelectedItem();

                if (paradeObjektName != null && !paradeObjektName.isEmpty()) {
                    setParadeObjekt(hero.getParadeWaffeByName(paradeObjektName));
                } else {
                    setParadeObjekt(null);
                }
            }
        });

        lblSchild = new JLabel(BUNDLE.getString("KampfPanel.lblSchild.text")); //$NON-NLS-1$
        lblSchild.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblSchild = new GridBagConstraints();
        gbc_lblSchild.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblSchild.insets = new Insets(0, 0, 5, 5);
        gbc_lblSchild.gridx = 1;
        gbc_lblSchild.gridy = 4;
        add(lblSchild, gbc_lblSchild);
        GridBagConstraints gbc_comboBoxSchildhand = new GridBagConstraints();
        gbc_comboBoxSchildhand.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxSchildhand.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxSchildhand.gridwidth = 7;
        gbc_comboBoxSchildhand.gridx = 2;
        gbc_comboBoxSchildhand.gridy = 4;
        add(comboBoxSchildhand, gbc_comboBoxSchildhand);

        lblFernkampfWaffe = new JLabel(BUNDLE.getString("KampfPanel.lblFernkampfWaffe.text")); //$NON-NLS-1$
        lblFernkampfWaffe.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblFernkampfWaffe = new GridBagConstraints();
        gbc_lblFernkampfWaffe.anchor = GridBagConstraints.EAST;
        gbc_lblFernkampfWaffe.insets = new Insets(0, 0, 5, 5);
        gbc_lblFernkampfWaffe.gridx = 1;
        gbc_lblFernkampfWaffe.gridy = 5;
        add(lblFernkampfWaffe, gbc_lblFernkampfWaffe);

        comboBoxFernkampfwaffe = new JComboBox<String>();
        comboBoxFernkampfwaffe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox<String> cb = (JComboBox<String>) e.getSource();
                String waffenName = (String) cb.getSelectedItem();

                setFernwaffenObjekt(hero.getFernWaffeByName(waffenName));
            }
        });

        GridBagConstraints gbc_comboBoxFernkampfwaffe = new GridBagConstraints();
        gbc_comboBoxFernkampfwaffe.gridwidth = 7;
        gbc_comboBoxFernkampfwaffe.insets = new Insets(0, 0, 5, 0);
        gbc_comboBoxFernkampfwaffe.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxFernkampfwaffe.gridx = 2;
        gbc_comboBoxFernkampfwaffe.gridy = 5;
        add(comboBoxFernkampfwaffe, gbc_comboBoxFernkampfwaffe);

        separatorPanel3 = new JPanel();
        GridBagConstraints gbc_separatorPanel3 = new GridBagConstraints();
        gbc_separatorPanel3.gridwidth = 9;
        gbc_separatorPanel3.insets = new Insets(0, 0, 5, 0);
        gbc_separatorPanel3.fill = GridBagConstraints.BOTH;
        gbc_separatorPanel3.gridx = 0;
        gbc_separatorPanel3.gridy = 6;
        add(separatorPanel3, gbc_separatorPanel3);
        separatorPanel3.setLayout(new BorderLayout(0, 0));

        separator_3 = new JSeparator();
        separatorPanel3.add(separator_3, BorderLayout.CENTER);

        btnAusweichen = new JButton(BUNDLE.getString("KampfPanel.btnAusweichen.text")); //$NON-NLS-1$
        btnAusweichen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AusweichDialog dialog = new AusweichDialog(hero, getInitiative(), chkUseDK.isSelected(),
                        chkUseDK.isSelected() ? (DKEnum) comboBoxCurrentDK.getSelectedItem() : null);
                dialog.setFont(getFont());
                dialog.setLocationRelativeTo(btnAusweichen.getRootPane());
                switch (dialog.showDialog()) {
                case Konstanten.DIALOG_OK_STATE:
                    WuerfelHelferGUI.copyToClipboard(dialog.getRollCommand());
                    break;
                }
            }
        });

        lblAttackeMit = new JLabel(BUNDLE.getString("KampfPanel.lblAttackeMit.text")); //$NON-NLS-1$
        lblAttackeMit.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblAttackeMit = new GridBagConstraints();
        gbc_lblAttackeMit.gridwidth = 2;
        gbc_lblAttackeMit.anchor = GridBagConstraints.EAST;
        gbc_lblAttackeMit.insets = new Insets(0, 0, 5, 5);
        gbc_lblAttackeMit.gridx = 3;
        gbc_lblAttackeMit.gridy = 7;
        add(lblAttackeMit, gbc_lblAttackeMit);

        comboBoxAttacke = new JComboBox<AttackeOptionEnum>();
        comboBoxAttacke.setToolTipText(BUNDLE.getString("KampfPanel.comboBoxAttacke.toolTipText")); //$NON-NLS-1$
        comboBoxAttacke.setModel(new DefaultComboBoxModel<>(AttackeOptionEnum.values()));
        comboBoxAttacke.setSelectedIndex(0);
        GridBagConstraints gbc_comboBoxAttacke = new GridBagConstraints();
        gbc_comboBoxAttacke.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxAttacke.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxAttacke.gridx = 5;
        gbc_comboBoxAttacke.gridy = 7;
        add(comboBoxAttacke, gbc_comboBoxAttacke);

        lblAttackeModifier = new JLabel(BUNDLE.getString("KampfPanel.lblAttackeModifier.text")); //$NON-NLS-1$
        lblAttackeModifier.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblAttackeModifier = new GridBagConstraints();
        gbc_lblAttackeModifier.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblAttackeModifier.insets = new Insets(0, 0, 5, 5);
        gbc_lblAttackeModifier.gridx = 6;
        gbc_lblAttackeModifier.gridy = 7;
        add(lblAttackeModifier, gbc_lblAttackeModifier);

        btnAttacke = new JButton(BUNDLE.getString("KampfPanel.btnAttacke.text")); //$NON-NLS-1$
        btnAttacke.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int modificator = getAttackeModifier();

                AttackeOptionEnum option = (AttackeOptionEnum) comboBoxAttacke.getSelectedItem();
                switch (option) {
                case Waffe: {
                    if (getWaffenObjekt() == null && option.equals(AttackeOptionEnum.Waffe)) {
                        JOptionPane.showMessageDialog(getParent(), "Du trägst doch gar keine Waffe!");
                        return;
                    }
                    if (useDistanceClasses()) {
                        int effectiveDistance = BerechnungsHelfer.berechneDistanz(getSelectedWeaponDistance(),
                                getCombatWeaponDistance());

                        if (effectiveDistance >= 2 || effectiveDistance <= -2) {
                            JOptionPane.showMessageDialog(btnAttacke.getRootPane(),
                                    "Attacke aufgrund der aktuellen Entfernung zum Gegner nicht möglich. Wähle Hopsen!");
                        } else {
                            String rollCommand = BerechnungsHelfer.berechneEffektiveWaffenAttacke(getWaffenObjekt(),
                                    modificator, useDistanceClasses(), getCombatWeaponDistance(),
                                    getSelectedWeaponDistance());
                            WuerfelHelferGUI.copyToClipboard(rollCommand);
                        }
                    } else {
                        String rollCommand = BerechnungsHelfer.berechneEffektiveWaffenAttacke(getWaffenObjekt(),
                                modificator, useDistanceClasses(), getCombatWeaponDistance(),
                                getSelectedWeaponDistance());
                        WuerfelHelferGUI.copyToClipboard(rollCommand);
                    }
                    break;
                }
                default: {
                    String selectedKampfTechnikName = ((AttackeOptionEnum) comboBoxAttacke.getSelectedItem()).name();
                    applyKampftechnikAttack(hero, modificator, selectedKampfTechnikName);

                    break;
                }
                }
            }
        });

        attackeModifier = new JSpinner();
        GridBagConstraints gbc_attackeModifier = new GridBagConstraints();
        gbc_attackeModifier.fill = GridBagConstraints.HORIZONTAL;
        gbc_attackeModifier.insets = new Insets(0, 0, 5, 5);
        gbc_attackeModifier.gridx = 7;
        gbc_attackeModifier.gridy = 7;
        add(attackeModifier, gbc_attackeModifier);
        GridBagConstraints gbc_btnAttacke = new GridBagConstraints();
        gbc_btnAttacke.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnAttacke.insets = new Insets(0, 0, 5, 0);
        gbc_btnAttacke.gridx = 8;
        gbc_btnAttacke.gridy = 7;
        add(btnAttacke, gbc_btnAttacke);
        GridBagConstraints gbc_btnAusweichen = new GridBagConstraints();
        gbc_btnAusweichen.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnAusweichen.insets = new Insets(0, 0, 5, 5);
        gbc_btnAusweichen.gridx = 1;
        gbc_btnAusweichen.gridy = 8;
        add(btnAusweichen, gbc_btnAusweichen);

        lblParade = new JLabel(BUNDLE.getString("KampfPanel.lblParade.text")); //$NON-NLS-1$
        lblParade.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblParade = new GridBagConstraints();
        gbc_lblParade.gridwidth = 2;
        gbc_lblParade.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblParade.insets = new Insets(0, 0, 5, 5);
        gbc_lblParade.gridx = 3;
        gbc_lblParade.gridy = 8;
        add(lblParade, gbc_lblParade);

        btnParade = new JButton(BUNDLE.getString("KampfPanel.btnParade.text")); //$NON-NLS-1$
        btnParade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ParadeOptionEnum paradenOption = (ParadeOptionEnum) comboBoxParade.getSelectedItem();

                int modificator = getParadeModifier();

                if (getWaffenObjekt() == null && paradenOption.equals(ParadeOptionEnum.Waffe)) {
                    JOptionPane.showMessageDialog(getParent(), "Du trägst doch gar keine Waffe!");
                    return;
                }

                if (paradeObjekt == null && paradenOption.equals(ParadeOptionEnum.Schild)) {
                    JOptionPane.showMessageDialog(getParent(), "Du hast doch gar keinen Schild!");
                    return;
                }

                if (useDistanceClasses()) {
                    int effectiveDistance = BerechnungsHelfer.berechneDistanz(getSelectedWeaponDistance(),
                            getCombatWeaponDistance());
                    if (effectiveDistance >= 2) {
                        JOptionPane.showMessageDialog(btnAttacke.getRootPane(),
                                "Parade aufgrund der aktuellen Entfernung zum Gegner nicht möglich. Wähle Hopsen!");
                        return;
                    }
                }

                switch (paradenOption) {
                case Schild: {
                    String rollCommand = BerechnungsHelfer.berechneEffektiveSchildParade(getWaffenObjekt(),
                            getParadeObjekt(), modificator, getInitiative(), useDistanceClasses(),
                            getCombatWeaponDistance(), getSelectedWeaponDistance());
                    WuerfelHelferGUI.copyToClipboard(rollCommand);
                    break;
                }
                case Waffe: {
                    String rollCommand = BerechnungsHelfer.berechneEffektiveWaffenParade(getWaffenObjekt(), modificator,
                            getInitiative(), useDistanceClasses(), getCombatWeaponDistance(),
                            getSelectedWeaponDistance());
                    WuerfelHelferGUI.copyToClipboard(rollCommand);
                    break;
                }
                default: {
                    String selectedKampfTechnikName = ((ParadeOptionEnum) comboBoxParade.getSelectedItem()).name();
                    applyKampftechniParade(hero, modificator, getInitiative(), selectedKampfTechnikName);
                    break;
                }
                }
            }
        });

        comboBoxParade = new JComboBox<ParadeOptionEnum>();
        comboBoxParade.setToolTipText(BUNDLE.getString("KampfPanel.comboBoxParade.toolTipText")); //$NON-NLS-1$
        comboBoxParade.setModel(new DefaultComboBoxModel<ParadeOptionEnum>(ParadeOptionEnum.values()));
        comboBoxParade.setSelectedIndex(0);
        GridBagConstraints gbc_comboBoxParade = new GridBagConstraints();
        gbc_comboBoxParade.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxParade.insets = new Insets(0, 0, 5, 5);
        gbc_comboBoxParade.gridx = 5;
        gbc_comboBoxParade.gridy = 8;
        add(comboBoxParade, gbc_comboBoxParade);

        lblParadeModifier = new JLabel(BUNDLE.getString("KampfPanel.lblParadeModifier.text")); //$NON-NLS-1$
        lblParadeModifier.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblParadeModifier = new GridBagConstraints();
        gbc_lblParadeModifier.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblParadeModifier.insets = new Insets(0, 0, 5, 5);
        gbc_lblParadeModifier.gridx = 6;
        gbc_lblParadeModifier.gridy = 8;
        add(lblParadeModifier, gbc_lblParadeModifier);

        paradeModifier = new JSpinner();
        GridBagConstraints gbc_paradeModifier = new GridBagConstraints();
        gbc_paradeModifier.fill = GridBagConstraints.HORIZONTAL;
        gbc_paradeModifier.insets = new Insets(0, 0, 5, 5);
        gbc_paradeModifier.gridx = 7;
        gbc_paradeModifier.gridy = 8;
        add(paradeModifier, gbc_paradeModifier);
        GridBagConstraints gbc_btnParade = new GridBagConstraints();
        gbc_btnParade.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnParade.insets = new Insets(0, 0, 5, 0);
        gbc_btnParade.gridx = 8;
        gbc_btnParade.gridy = 8;
        add(btnParade, gbc_btnParade);

        separatorPanel4 = new JPanel();
        GridBagConstraints gbc_separatorPanel4 = new GridBagConstraints();
        gbc_separatorPanel4.gridwidth = 9;
        gbc_separatorPanel4.insets = new Insets(0, 0, 5, 0);
        gbc_separatorPanel4.fill = GridBagConstraints.BOTH;
        gbc_separatorPanel4.gridx = 0;
        gbc_separatorPanel4.gridy = 9;
        add(separatorPanel4, gbc_separatorPanel4);
        separatorPanel4.setLayout(new BorderLayout(0, 0));

        separator_4 = new JSeparator();
        separatorPanel4.add(separator_4, BorderLayout.CENTER);
        GridBagConstraints gbc_chkUseDK = new GridBagConstraints();
        gbc_chkUseDK.fill = GridBagConstraints.HORIZONTAL;
        gbc_chkUseDK.insets = new Insets(0, 0, 0, 5);
        gbc_chkUseDK.gridx = 1;
        gbc_chkUseDK.gridy = 10;
        add(chkUseDK, gbc_chkUseDK);

        lblCurrentDK = new JLabel(BUNDLE.getString("KampfPanel.lblCurrentDK.text")); //$NON-NLS-1$
        lblCurrentDK.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblCurrentDK = new GridBagConstraints();
        gbc_lblCurrentDK.gridwidth = 2;
        gbc_lblCurrentDK.anchor = GridBagConstraints.EAST;
        gbc_lblCurrentDK.insets = new Insets(0, 0, 0, 5);
        gbc_lblCurrentDK.gridx = 3;
        gbc_lblCurrentDK.gridy = 10;
        add(lblCurrentDK, gbc_lblCurrentDK);

        comboBoxCurrentDK = new JComboBox<>();
        comboBoxCurrentDK.setModel(new DefaultComboBoxModel<DKEnum>(DKEnum.values()));
        GridBagConstraints gbc_comboBoxCurrentDK = new GridBagConstraints();
        gbc_comboBoxCurrentDK.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxCurrentDK.insets = new Insets(0, 0, 0, 5);
        gbc_comboBoxCurrentDK.gridx = 5;
        gbc_comboBoxCurrentDK.gridy = 10;
        add(comboBoxCurrentDK, gbc_comboBoxCurrentDK);

        lblDkWaffe = new JLabel(BUNDLE.getString("KampfPanel.lblDkWaffe.text")); //$NON-NLS-1$
        lblDkWaffe.setVisible(false);
        lblDkWaffe.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblCombatDk = new GridBagConstraints();
        gbc_lblCombatDk.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblCombatDk.insets = new Insets(0, 0, 0, 5);
        gbc_lblCombatDk.gridx = 6;
        gbc_lblCombatDk.gridy = 10;
        add(lblDkWaffe, gbc_lblCombatDk);

        comboBoxOwnDK = new JComboBox<>();
        GridBagConstraints gbc_comboBoxOwnDK = new GridBagConstraints();
        gbc_comboBoxOwnDK.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBoxOwnDK.insets = new Insets(0, 0, 0, 5);
        gbc_comboBoxOwnDK.gridx = 7;
        gbc_comboBoxOwnDK.gridy = 10;
        add(comboBoxOwnDK, gbc_comboBoxOwnDK);

        btnHopsen = new JButton(BUNDLE.getString("KampfPanel.btnHopsen.text")); //$NON-NLS-1$
        btnHopsen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DistanzwechselDialog dialog = new DistanzwechselDialog(hero, getWaffenObjekt());
                dialog.setFont(getFont());
                dialog.setLocationRelativeTo(btnHopsen.getRootPane());
                switch (dialog.showDialog()) {
                case Konstanten.DIALOG_OK_STATE:
                    WuerfelHelferGUI.copyToClipboard(dialog.getRollCommand());
                    break;
                }
            }
        });
        GridBagConstraints gbc_btnHopsen = new GridBagConstraints();
        gbc_btnHopsen.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnHopsen.gridx = 8;
        gbc_btnHopsen.gridy = 10;
        add(btnHopsen, gbc_btnHopsen);
        updateDistanzklasse();
        if (getParadeObjekt() != null) {
            comboBoxSchildhand.setSelectedItem(getParadeObjekt().getName());
        }

        enableDkElements(chkUseDK.isSelected());
    }

    /**
     * 
     */
    protected void updateDistanzklasse() {
        if (getWaffenObjekt() != null) {
            comboBoxOwnDK.setModel(new DefaultComboBoxModel<>(getWaffenObjekt().getDistanzklassenAsArray()));
        }
    }

    /**
     * @param visible whether to set fields visible
     */
    protected void enableDkElements(boolean visible) {
        lblCurrentDK.setVisible(visible);
        lblDkWaffe.setVisible(visible);
        comboBoxOwnDK.setVisible(visible);
        comboBoxCurrentDK.setVisible(visible);
        btnHopsen.setVisible(visible);

        updateUI();
    }

    /**
     * @return the waffenObjekt
     */
    public WaffenObjekt getWaffenObjekt() {
        return waffenObjekt;
    }

    /**
     * @param waffenObjekt the waffenObjekt to set
     */
    public void setWaffenObjekt(WaffenObjekt waffenObjekt) {
        this.waffenObjekt = waffenObjekt;
    }

    /**
     * @return the paradeObjekt
     */
    public ParadeObjekt getParadeObjekt() {
        return paradeObjekt;
    }

    /**
     * @param paradeObjekt the paradeObjekt to set
     */
    public void setParadeObjekt(ParadeObjekt paradeObjekt) {
        this.paradeObjekt = paradeObjekt;
    }

    /**
     * @return the Distanzklasse of the selected weapon
     */
    public DKEnum getSelectedWeaponDistance() {
        DKEnum result = null;
        String selectedItem = (String) comboBoxOwnDK.getSelectedItem();
        result = DKEnum.valueOf(selectedItem);

        return result;
    }

    /**
     * @return the Distanzklasse of the Weapon
     */
    public DKEnum getCombatWeaponDistance() {
        DKEnum result = null;
        result = (DKEnum) comboBoxCurrentDK.getSelectedItem();

        return result;
    }

    /**
     * @return true or false
     */
    public boolean useDistanceClasses() {
        boolean useDk = false;
        useDk = (boolean) chkUseDK.isSelected();
        return useDk;
    }

    /**
     * @return the parade modifier
     */
    public int getParadeModifier() {
        int modIntVal = (Integer) paradeModifier.getValue();

        return modIntVal;
    }

    /**
     * @return the modifier
     */
    public int getAttackeModifier() {
        int modIntVal = (Integer) attackeModifier.getValue();

        return modIntVal;
    }

    /**
     * @return the FernwaffenObjekt
     */
    public FernwaffenObjekt getFernwaffenObjekt() {
        return fernwaffenObjekt;
    }

    /**
     * @param fernwaffenObjekt the FernwaffenObjekt to set
     */
    public void setFernwaffenObjekt(FernwaffenObjekt fernwaffenObjekt) {
        this.fernwaffenObjekt = fernwaffenObjekt;
    }

    /**
     * @return the initiative
     */
    public int getInitiative() {
        int ini = hero.getBasisinitiative();
        try {
            int iniVal = (Integer) tfInitiative.getValue();
            if (iniVal != 0 && iniVal > ini) {
                ini = iniVal;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return ini;
    }

    /**
     * @param hero                     the HeroObjekt to use
     * @param modificator              the Modifikator to apply
     * @param selectedKampfTechnikName the selected Kampftechnik
     */
    private void applyKampftechnikAttack(HeldenObjekt hero, int modificator, String selectedKampfTechnikName) {
        if (selectedKampfTechnikName != null && !selectedKampfTechnikName.isEmpty()) {
            if (useDistanceClasses()) {
                int effectiveDistance = BerechnungsHelfer.berechneDistanz(DKEnum.H, getCombatWeaponDistance());
                if (effectiveDistance >= 1 || effectiveDistance <= -1) {
                    JOptionPane.showMessageDialog(btnAttacke.getRootPane(),
                            "Attacke aufgrund der aktuellen Entfernung zum Gegner nicht möglich. Wähle Hopsen!");
                } else {

                    String rollCommand = BerechnungsHelfer.berechneEffektiveTechnischeAttacke(
                            hero.getKampftechnikByName(selectedKampfTechnikName), modificator, useDistanceClasses(),
                            getCombatWeaponDistance(), DKEnum.H);
                    WuerfelHelferGUI.copyToClipboard(rollCommand);
                }
            } else {
                String rollCommand = BerechnungsHelfer.berechneEffektiveTechnischeAttacke(
                        hero.getKampftechnikByName(selectedKampfTechnikName), modificator, useDistanceClasses(),
                        getCombatWeaponDistance(), DKEnum.H);
                WuerfelHelferGUI.copyToClipboard(rollCommand);
            }
        }
    }

    /**
     * @param hero                     the HeroObjekt to use
     * @param modificator              the Modifikator to apply
     * @param initiative               the Initiative in the current combat
     * @param selectedKampfTechnikName the selected Kampftechnik
     */
    private void applyKampftechniParade(HeldenObjekt hero, int modificator, int initiative,
            String selectedKampfTechnikName) {
        if (selectedKampfTechnikName != null && !selectedKampfTechnikName.isEmpty()) {
            if (useDistanceClasses()) {
                int effectiveDistance = BerechnungsHelfer.berechneDistanz(DKEnum.H, getCombatWeaponDistance());
                if (effectiveDistance >= 1 || effectiveDistance <= -1) {
                    JOptionPane.showMessageDialog(btnAttacke.getRootPane(),
                            "Attacke aufgrund der aktuellen Entfernung zum Gegner nicht möglich. Wähle Hopsen!");
                } else {

                    String rollCommand = BerechnungsHelfer.berechneEffektiveTechnischeParade(
                            hero.getKampftechnikByName(selectedKampfTechnikName), modificator, initiative,
                            useDistanceClasses(), getCombatWeaponDistance(), selectedKampfTechnikName);
                    WuerfelHelferGUI.copyToClipboard(rollCommand);
                }
            } else {
                String rollCommand = BerechnungsHelfer.berechneEffektiveTechnischeParade(
                        hero.getKampftechnikByName(selectedKampfTechnikName), modificator, initiative,
                        useDistanceClasses(), getCombatWeaponDistance(), selectedKampfTechnikName);
                WuerfelHelferGUI.copyToClipboard(rollCommand);
            }
        }
    }
}
