package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import link.parzival.dsa.Konstanten;
import link.parzival.dsa.BerechnungsHelfer;
import link.parzival.dsa.object.FernwaffenObjekt;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;
import java.util.ResourceBundle;

public class FernkampfDialog extends JDialog {
    private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$

    private static final long serialVersionUID          = -5503871340629603661L;
    private JButton backButton                          = new JButton();
    private List<JRadioButton>bewegungGroup             = new ArrayList<>();
    private JCheckBox chkBoxDreivierteldeckung          = new JCheckBox();
    private JCheckBox chkBoxGezielterSchuss             = new JCheckBox();
    private JCheckBox chkboxHalbdeckung                 = new JCheckBox();
    private JCheckBox chkBoxKoerperteilBewegung         = new JCheckBox();
    private JComboBox<String> comboSchuetzentyp         = new JComboBox<>();
    private final JPanel contentPanel                   = new JPanel();
    private List<JRadioButton>entfernungGroup           = new ArrayList<>();

    private int erschwernis                             = 0;
    private FernwaffenObjekt fernwaffenObjekt           = null;
    private JButton forwardButton                       = new JButton();
    private List<JRadioButton> groessenGroup            = new ArrayList<>();
    private JLabel lblErschwernisBewegungValue          = new JLabel();
    private JLabel lblErschwernisEntfernungValue        = new JLabel();
    private JLabel lblErschwernisGezielterSchussValue    = new JLabel();
    private JLabel lblKompletteErschwernisValue            = new JLabel();
    private JLabel lblErschwernisModifikatorenValue     = new JLabel();

    private JLabel lblErschwernisSichtValue             = new JLabel();
    
    private JLabel lblErschwernisZielgroesseValue       = new JLabel();
    private JLabel lblWaffeDistanzMittel                = new JLabel();
    private JLabel lblWaffeDistanzNah                   = new JLabel();
    private JLabel lblWaffeDistanzSehrNah               = new JLabel();
    private JLabel lblWaffeDistanzSehrWeit              = new JLabel();
    private JLabel lblWaffeDistanzWeit                  = new JLabel();
    private JLabel lblZielHumanoid                      = new JLabel();
    private JLabel lblZielTier                          = new JLabel();
    private List<JCheckBox> modifikatorGroup            = new ArrayList<>();
    private List<JCheckBox> nachteilListe               = new ArrayList<>();
    private String rollCommand                          = null;
    private List<JRadioButton>sichtGroup                = new ArrayList<>();
    private List<JRadioButton>sichtModGroup             = new ArrayList<>();
    private JSpinner spinnerAnsage                      = new JSpinner();
    private JSpinner spinnerGegnerInDistanzH            = new JSpinner();
    private JSpinner spinnerGegnerInDistanzNS           = new JSpinner();
    private JSpinner spinnerGroessenModifikator         = new JSpinner();
    private JSpinner spinnerZielen                      = new JSpinner();
    private int state                                   = Konstanten.DIALOG_CANCEL_STATE;
    private JTabbedPane tabbedPane                      = new JTabbedPane();
    private List<JCheckBox> vorteilListe                = new ArrayList<>();
    private List<JRadioButton>zielHumanoidGroup         = new ArrayList<>();    
    private List<JRadioButton>zielTierGroup             = new ArrayList<>();

    /**
     * Create the dialog.
     */
    /**
     * @param fernwaffenObjekt the FernwaffenObjekt to be used
     */
    public FernkampfDialog(FernwaffenObjekt fernwaffenObjekt) {
        this.setFernwaffenObjekt(fernwaffenObjekt);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        setModal(true);
        setBounds(100, 100, 650, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 0, 0 };
        gbl_contentPanel.rowHeights = new int[] { 0, 0 };
        gbl_contentPanel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);
        {
            tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
            gbc_tabbedPane.anchor = GridBagConstraints.NORTH;
            gbc_tabbedPane.fill = GridBagConstraints.HORIZONTAL;
            gbc_tabbedPane.gridx = 0;
            gbc_tabbedPane.gridy = 0;
            tabbedPane.addChangeListener(new ChangeListener() {
                public void stateChanged(ChangeEvent e) {
                    updateButtons();
                    updateSummary();
                }
            });
            contentPanel.add(tabbedPane, gbc_tabbedPane);
            {
                JPanel zielgroessenPanel = new JPanel();
                tabbedPane.addTab("Größe", null, zielgroessenPanel, null);
                tabbedPane.setEnabledAt(0, true);
                GridBagLayout gbl_zielgroessenPanel = new GridBagLayout();
                gbl_zielgroessenPanel.columnWidths = new int[] { 199, 100, 0, 0 };
                gbl_zielgroessenPanel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
                gbl_zielgroessenPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
                gbl_zielgroessenPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
                zielgroessenPanel.setLayout(gbl_zielgroessenPanel);
                {
                    JPanel groessenSelectionPanel = new JPanel();
                    GridBagConstraints gbc_panel = new GridBagConstraints();
                    gbc_panel.gridheight = 4;
                    gbc_panel.insets = new Insets(0, 0, 0, 5);
                    gbc_panel.fill = GridBagConstraints.BOTH;
                    gbc_panel.gridx = 0;
                    gbc_panel.gridy = 0;
                    zielgroessenPanel.add(groessenSelectionPanel, gbc_panel);
                    groessenSelectionPanel.setLayout(new BoxLayout(groessenSelectionPanel, BoxLayout.Y_AXIS));
                    {
                        ButtonGroup groessenButtonGroup = new ButtonGroup();
                        JRadioButton rdbWinzig = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbWinzig.text")); //$NON-NLS-1$
                        rdbWinzig.setToolTipText(BUNDLE.getString("FernkampfDialog.rdbWinzig.toolTipText")); //$NON-NLS-1$
                        groessenButtonGroup.add(rdbWinzig);
                        groessenSelectionPanel.add(rdbWinzig);

                        JRadioButton rdbSehrKlein = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbSehrKlein.text")); //$NON-NLS-1$
                        rdbSehrKlein.setToolTipText(BUNDLE.getString("FernkampfDialog.rdbSehrKlein.toolTipText")); //$NON-NLS-1$
                        groessenButtonGroup.add(rdbSehrKlein);
                        groessenSelectionPanel.add(rdbSehrKlein);

                        JRadioButton rdbKlein = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbKlein.text")); //$NON-NLS-1$
                        rdbKlein.setToolTipText(BUNDLE.getString("FernkampfDialog.rdbKlein.toolTipText")); //$NON-NLS-1$
                        groessenButtonGroup.add(rdbKlein);
                        groessenSelectionPanel.add(rdbKlein);

                        JRadioButton rdbMittel = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbMittel.text")); //$NON-NLS-1$
                        rdbMittel.setToolTipText(BUNDLE.getString("FernkampfDialog.rdbMittel.toolTipText")); //$NON-NLS-1$
                        groessenButtonGroup.add(rdbMittel);
                        rdbMittel.setSelected(true);
                        groessenSelectionPanel.add(rdbMittel);

                        JRadioButton rdbGross = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbGross.text")); //$NON-NLS-1$
                        rdbGross.setToolTipText(BUNDLE.getString("FernkampfDialog.rdbGross.toolTipText")); //$NON-NLS-1$
                        groessenButtonGroup.add(rdbGross);
                        groessenSelectionPanel.add(rdbGross);

                        JRadioButton rdbSehrGross = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbSehrGross.text")); //$NON-NLS-1$
                        rdbSehrGross.setToolTipText(BUNDLE.getString("FernkampfDialog.rdbSehrGross.toolTipText")); //$NON-NLS-1$
                        groessenButtonGroup.add(rdbSehrGross);
                        groessenSelectionPanel.add(rdbSehrGross);

                        groessenGroup.add(rdbWinzig);
                        groessenGroup.add(rdbSehrKlein);
                        groessenGroup.add(rdbKlein);
                        groessenGroup.add(rdbMittel);
                        groessenGroup.add(rdbGross);
                        groessenGroup.add(rdbSehrGross);

                    }

                }
                
                SpinnerNumberModel sgmNumModel = new SpinnerNumberModel(0, 0, 10, 2);
                spinnerGroessenModifikator = new JSpinner(sgmNumModel);
                GridBagConstraints gbc_spinnerGroessenModifikator = new GridBagConstraints();
                gbc_spinnerGroessenModifikator.anchor = GridBagConstraints.EAST;
                gbc_spinnerGroessenModifikator.insets = new Insets(0, 0, 5, 5);
                gbc_spinnerGroessenModifikator.gridx = 1;
                gbc_spinnerGroessenModifikator.gridy = 0;
                zielgroessenPanel.add(spinnerGroessenModifikator, gbc_spinnerGroessenModifikator);
                {
                    JLabel lblModifikator = new JLabel(BUNDLE.getString("FernkampfDialog.lblModifikator.text")); //$NON-NLS-1$
                    GridBagConstraints gbc_lblModifikator = new GridBagConstraints();
                    gbc_lblModifikator.anchor = GridBagConstraints.WEST;
                    gbc_lblModifikator.insets = new Insets(0, 0, 5, 0);
                    gbc_lblModifikator.gridx = 2;
                    gbc_lblModifikator.gridy = 0;
                    zielgroessenPanel.add(lblModifikator, gbc_lblModifikator);
                }
                {
                    JLabel lblHalbdeckung = new JLabel(BUNDLE.getString("FernkampfDialog.lblHalbdeckung.text")); //$NON-NLS-1$
                    GridBagConstraints gbc_lblHalbdeckung = new GridBagConstraints();
                    gbc_lblHalbdeckung.anchor = GridBagConstraints.WEST;
                    gbc_lblHalbdeckung.insets = new Insets(0, 0, 5, 0);
                    gbc_lblHalbdeckung.gridx = 2;
                    gbc_lblHalbdeckung.gridy = 1;
                    zielgroessenPanel.add(lblHalbdeckung, gbc_lblHalbdeckung);
                }
                {
                    chkboxHalbdeckung = new JCheckBox(); //$NON-NLS-1$
                    GridBagConstraints gbc_chkboxHalbdeckung = new GridBagConstraints();
                    gbc_chkboxHalbdeckung.anchor = GridBagConstraints.EAST;
                    gbc_chkboxHalbdeckung.insets = new Insets(0, 0, 5, 5);
                    gbc_chkboxHalbdeckung.gridx = 1;
                    gbc_chkboxHalbdeckung.gridy = 1;
                    zielgroessenPanel.add(chkboxHalbdeckung, gbc_chkboxHalbdeckung);
                }
                {
                    JLabel lblDreivierteldeckung = new JLabel(BUNDLE.getString("FernkampfDialog.lblDreivierteldeckung.text")); //$NON-NLS-1$
                    GridBagConstraints gbc_lblDreivierteldeckung = new GridBagConstraints();
                    gbc_lblDreivierteldeckung.insets = new Insets(0, 0, 5, 0);
                    gbc_lblDreivierteldeckung.anchor = GridBagConstraints.WEST;
                    gbc_lblDreivierteldeckung.gridx = 2;
                    gbc_lblDreivierteldeckung.gridy = 2;
                    zielgroessenPanel.add(lblDreivierteldeckung, gbc_lblDreivierteldeckung);
                }
                {
                    chkBoxDreivierteldeckung = new JCheckBox(); //$NON-NLS-1$
                    
                    GridBagConstraints gbc_chkBoxDreivierteldeckung = new GridBagConstraints();
                    gbc_chkBoxDreivierteldeckung.insets = new Insets(0, 0, 5, 5);
                    gbc_chkBoxDreivierteldeckung.anchor = GridBagConstraints.EAST;
                    gbc_chkBoxDreivierteldeckung.gridx = 1;
                    gbc_chkBoxDreivierteldeckung.gridy = 2;
                    zielgroessenPanel.add(chkBoxDreivierteldeckung, gbc_chkBoxDreivierteldeckung);
                }
            }
            {
                JPanel entfernungPanel = new JPanel();
                tabbedPane.addTab("Entfernung", null, entfernungPanel, null);
                tabbedPane.setEnabledAt(1, true);
                GridBagLayout gbl_entfernungPanel = new GridBagLayout();
                gbl_entfernungPanel.columnWidths = new int[] { 150, 0, 0 };
                gbl_entfernungPanel.rowHeights = new int[] { 0, 23, 23, 23, 23, 23, 0 };
                gbl_entfernungPanel.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
                gbl_entfernungPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
                entfernungPanel.setLayout(gbl_entfernungPanel);

                JLabel lblDistanzZumGegner = new JLabel(BUNDLE.getString("FernkampfDialog.lblDistanzZumGegner.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblDistanzZumGegner = new GridBagConstraints();
                gbc_lblDistanzZumGegner.insets = new Insets(0, 0, 5, 5);
                gbc_lblDistanzZumGegner.gridx = 0;
                gbc_lblDistanzZumGegner.gridy = 0;
                entfernungPanel.add(lblDistanzZumGegner, gbc_lblDistanzZumGegner);

                JLabel lblReichweiteDerWaffe = new JLabel(BUNDLE.getString("FernkampfDialog.lblReichweiteDerWaffe.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblReichweiteDerWaffe = new GridBagConstraints();
                gbc_lblReichweiteDerWaffe.anchor = GridBagConstraints.WEST;
                gbc_lblReichweiteDerWaffe.insets = new Insets(0, 0, 5, 0);
                gbc_lblReichweiteDerWaffe.gridx = 1;
                gbc_lblReichweiteDerWaffe.gridy = 0;
                entfernungPanel.add(lblReichweiteDerWaffe, gbc_lblReichweiteDerWaffe);

                ButtonGroup entfernungButtonGroup = new ButtonGroup();
                JRadioButton rdbSehrNah = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbSehrNah.text")); //$NON-NLS-1$
                entfernungButtonGroup.add(rdbSehrNah);
                GridBagConstraints gbc_rdbSehrNah = new GridBagConstraints();
                gbc_rdbSehrNah.anchor = GridBagConstraints.WEST;
                gbc_rdbSehrNah.insets = new Insets(0, 0, 5, 5);
                gbc_rdbSehrNah.gridx = 0;
                gbc_rdbSehrNah.gridy = 1;
                entfernungPanel.add(rdbSehrNah, gbc_rdbSehrNah);
                entfernungGroup.add(rdbSehrNah);

                lblWaffeDistanzSehrNah = new JLabel("0");
                GridBagConstraints gbc_lblWaffeDistanzSehrNah = new GridBagConstraints();
                gbc_lblWaffeDistanzSehrNah.anchor = GridBagConstraints.WEST;
                gbc_lblWaffeDistanzSehrNah.insets = new Insets(0, 0, 5, 0);
                gbc_lblWaffeDistanzSehrNah.gridx = 1;
                gbc_lblWaffeDistanzSehrNah.gridy = 1;
                entfernungPanel.add(lblWaffeDistanzSehrNah, gbc_lblWaffeDistanzSehrNah);

                JRadioButton rdbDistanzNah = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbDistanzNah.text")); //$NON-NLS-1$
                entfernungButtonGroup.add(rdbDistanzNah);
                GridBagConstraints gbc_rdbDistanzNah = new GridBagConstraints();
                gbc_rdbDistanzNah.anchor = GridBagConstraints.WEST;
                gbc_rdbDistanzNah.insets = new Insets(0, 0, 5, 5);
                gbc_rdbDistanzNah.gridx = 0;
                gbc_rdbDistanzNah.gridy = 2;
                entfernungPanel.add(rdbDistanzNah, gbc_rdbDistanzNah);
                entfernungGroup.add(rdbDistanzNah);

                lblWaffeDistanzNah = new JLabel("0");
                GridBagConstraints gbc_lblWaffeDistanzNah = new GridBagConstraints();
                gbc_lblWaffeDistanzNah.anchor = GridBagConstraints.WEST;
                gbc_lblWaffeDistanzNah.insets = new Insets(0, 0, 5, 0);
                gbc_lblWaffeDistanzNah.gridx = 1;
                gbc_lblWaffeDistanzNah.gridy = 2;
                entfernungPanel.add(lblWaffeDistanzNah, gbc_lblWaffeDistanzNah);

                JRadioButton rdbDistanzMittel = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbDistanzMittel.text")); //$NON-NLS-1$
                entfernungButtonGroup.add(rdbDistanzMittel);
                rdbDistanzMittel.setSelected(true);
                GridBagConstraints gbc_rdbDistanzMittel = new GridBagConstraints();
                gbc_rdbDistanzMittel.anchor = GridBagConstraints.WEST;
                gbc_rdbDistanzMittel.insets = new Insets(0, 0, 5, 5);
                gbc_rdbDistanzMittel.gridx = 0;
                gbc_rdbDistanzMittel.gridy = 3;
                entfernungPanel.add(rdbDistanzMittel, gbc_rdbDistanzMittel);
                entfernungGroup.add(rdbDistanzMittel);

                lblWaffeDistanzMittel = new JLabel("0");
                GridBagConstraints gbc_lblWaffeDistanzMittel = new GridBagConstraints();
                gbc_lblWaffeDistanzMittel.anchor = GridBagConstraints.WEST;
                gbc_lblWaffeDistanzMittel.insets = new Insets(0, 0, 5, 0);
                gbc_lblWaffeDistanzMittel.gridx = 1;
                gbc_lblWaffeDistanzMittel.gridy = 3;
                entfernungPanel.add(lblWaffeDistanzMittel, gbc_lblWaffeDistanzMittel);

                JRadioButton rdbDistanzWeit = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbDistanzWeit.text")); //$NON-NLS-1$
                entfernungButtonGroup.add(rdbDistanzWeit);
                GridBagConstraints gbc_rdbDistanzWeit = new GridBagConstraints();
                gbc_rdbDistanzWeit.anchor = GridBagConstraints.WEST;
                gbc_rdbDistanzWeit.insets = new Insets(0, 0, 5, 5);
                gbc_rdbDistanzWeit.gridx = 0;
                gbc_rdbDistanzWeit.gridy = 4;
                entfernungPanel.add(rdbDistanzWeit, gbc_rdbDistanzWeit);
                entfernungGroup.add(rdbDistanzWeit);

                lblWaffeDistanzWeit = new JLabel("0");
                GridBagConstraints gbc_lblWaffeDistanzWeit = new GridBagConstraints();
                gbc_lblWaffeDistanzWeit.anchor = GridBagConstraints.WEST;
                gbc_lblWaffeDistanzWeit.insets = new Insets(0, 0, 5, 0);
                gbc_lblWaffeDistanzWeit.gridx = 1;
                gbc_lblWaffeDistanzWeit.gridy = 4;
                entfernungPanel.add(lblWaffeDistanzWeit, gbc_lblWaffeDistanzWeit);

                JRadioButton rdbDistanzSehrWeit = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbDistanzSehrWeit.text")); //$NON-NLS-1$
                entfernungButtonGroup.add(rdbDistanzSehrWeit);
                GridBagConstraints gbc_rdbDistanzSehrWeit = new GridBagConstraints();
                gbc_rdbDistanzSehrWeit.insets = new Insets(0, 0, 0, 5);
                gbc_rdbDistanzSehrWeit.anchor = GridBagConstraints.WEST;
                gbc_rdbDistanzSehrWeit.gridx = 0;
                gbc_rdbDistanzSehrWeit.gridy = 5;
                entfernungPanel.add(rdbDistanzSehrWeit, gbc_rdbDistanzSehrWeit);
                entfernungGroup.add(rdbDistanzSehrWeit);

                lblWaffeDistanzSehrWeit = new JLabel("0");
                GridBagConstraints gbc_lblWaffeDistanzSehrWeit = new GridBagConstraints();
                gbc_lblWaffeDistanzSehrWeit.anchor = GridBagConstraints.WEST;
                gbc_lblWaffeDistanzSehrWeit.gridx = 1;
                gbc_lblWaffeDistanzSehrWeit.gridy = 5;
                entfernungPanel.add(lblWaffeDistanzSehrWeit, gbc_lblWaffeDistanzSehrWeit);

                setFernwaffenDistanzen();
            }

            JPanel bewegungPanel = new JPanel();
            tabbedPane.addTab("Bewegung", null, bewegungPanel, null);
            tabbedPane.setEnabledAt(2, true);
            GridBagLayout gbl_bewegungPanel = new GridBagLayout();
            gbl_bewegungPanel.columnWidths = new int[] { 175, 300, 0 };
            gbl_bewegungPanel.rowHeights = new int[] { 23, 23, 23, 23, 23, 23, 0, 0, 0 };
            gbl_bewegungPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
            gbl_bewegungPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
            bewegungPanel.setLayout(gbl_bewegungPanel);

            ButtonGroup bewegungButtonGroup = new ButtonGroup();
            JRadioButton rdbUnbeweglich = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbUnbeweglich.text")); //$NON-NLS-1$
            bewegungButtonGroup.add(rdbUnbeweglich);
            GridBagConstraints gbc_rdbtnNewRadioButton_15 = new GridBagConstraints();
            gbc_rdbtnNewRadioButton_15.gridwidth = 2;
            gbc_rdbtnNewRadioButton_15.anchor = GridBagConstraints.WEST;
            gbc_rdbtnNewRadioButton_15.insets = new Insets(0, 0, 5, 0);
            gbc_rdbtnNewRadioButton_15.gridx = 0;
            gbc_rdbtnNewRadioButton_15.gridy = 0;
            bewegungPanel.add(rdbUnbeweglich, gbc_rdbtnNewRadioButton_15);

            JRadioButton rdbStillstehend = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbStillstehend.text")); //$NON-NLS-1$
            bewegungButtonGroup.add(rdbStillstehend);
            rdbStillstehend.setSelected(true);
            GridBagConstraints gbc_rdbtnNewRadioButton_15_1 = new GridBagConstraints();
            gbc_rdbtnNewRadioButton_15_1.gridwidth = 2;
            gbc_rdbtnNewRadioButton_15_1.anchor = GridBagConstraints.WEST;
            gbc_rdbtnNewRadioButton_15_1.insets = new Insets(0, 0, 5, 0);
            gbc_rdbtnNewRadioButton_15_1.gridx = 0;
            gbc_rdbtnNewRadioButton_15_1.gridy = 1;
            bewegungPanel.add(rdbStillstehend, gbc_rdbtnNewRadioButton_15_1);

            JRadioButton rdbLeichteBewegung = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbLeichteBewegung.text")); //$NON-NLS-1$
            bewegungButtonGroup.add(rdbLeichteBewegung);
            GridBagConstraints gbc_rdbtnNewRadioButton_15_1_1 = new GridBagConstraints();
            gbc_rdbtnNewRadioButton_15_1_1.gridwidth = 2;
            gbc_rdbtnNewRadioButton_15_1_1.anchor = GridBagConstraints.WEST;
            gbc_rdbtnNewRadioButton_15_1_1.insets = new Insets(0, 0, 5, 0);
            gbc_rdbtnNewRadioButton_15_1_1.gridx = 0;
            gbc_rdbtnNewRadioButton_15_1_1.gridy = 2;
            bewegungPanel.add(rdbLeichteBewegung, gbc_rdbtnNewRadioButton_15_1_1);

            JRadioButton rdbSchnelleBewegung = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbSchnelleBewegung.text")); //$NON-NLS-1$
            bewegungButtonGroup.add(rdbSchnelleBewegung);
            GridBagConstraints gbc_rdbtnNewRadioButton_15_1_2 = new GridBagConstraints();
            gbc_rdbtnNewRadioButton_15_1_2.gridwidth = 2;
            gbc_rdbtnNewRadioButton_15_1_2.anchor = GridBagConstraints.WEST;
            gbc_rdbtnNewRadioButton_15_1_2.insets = new Insets(0, 0, 5, 0);
            gbc_rdbtnNewRadioButton_15_1_2.gridx = 0;
            gbc_rdbtnNewRadioButton_15_1_2.gridy = 3;
            bewegungPanel.add(rdbSchnelleBewegung, gbc_rdbtnNewRadioButton_15_1_2);

            JRadioButton rdbSehrSchnelleBewegung = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbSehrSchnelleBewegung.text")); //$NON-NLS-1$
            bewegungButtonGroup.add(rdbSehrSchnelleBewegung);
            GridBagConstraints gbc_rdbtnNewRadioButton_15_1_3 = new GridBagConstraints();
            gbc_rdbtnNewRadioButton_15_1_3.gridwidth = 2;
            gbc_rdbtnNewRadioButton_15_1_3.anchor = GridBagConstraints.WEST;
            gbc_rdbtnNewRadioButton_15_1_3.insets = new Insets(0, 0, 5, 0);
            gbc_rdbtnNewRadioButton_15_1_3.gridx = 0;
            gbc_rdbtnNewRadioButton_15_1_3.gridy = 4;
            bewegungPanel.add(rdbSehrSchnelleBewegung, gbc_rdbtnNewRadioButton_15_1_3);

            JRadioButton rdbKampfgetuemmel = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbKampfgetuemmel.text")); //$NON-NLS-1$
            bewegungButtonGroup.add(rdbKampfgetuemmel);
            GridBagConstraints gbc_rdbtnNewRadioButton_15_1_4 = new GridBagConstraints();
            gbc_rdbtnNewRadioButton_15_1_4.gridwidth = 2;
            gbc_rdbtnNewRadioButton_15_1_4.insets = new Insets(0, 0, 5, 0);
            gbc_rdbtnNewRadioButton_15_1_4.anchor = GridBagConstraints.WEST;
            gbc_rdbtnNewRadioButton_15_1_4.gridx = 0;
            gbc_rdbtnNewRadioButton_15_1_4.gridy = 5;
            bewegungPanel.add(rdbKampfgetuemmel, gbc_rdbtnNewRadioButton_15_1_4);
            
            bewegungGroup.add(rdbUnbeweglich);
            bewegungGroup.add(rdbStillstehend);
            bewegungGroup.add(rdbLeichteBewegung);
            bewegungGroup.add(rdbSchnelleBewegung);            
            bewegungGroup.add(rdbSehrSchnelleBewegung);
            bewegungGroup.add(rdbKampfgetuemmel);

            JLabel lblGegnerInDistanzH = new JLabel(BUNDLE.getString("FernkampfDialog.lblGegnerInDistanzH.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblGegnerInDistanzH = new GridBagConstraints();
            gbc_lblGegnerInDistanzH.anchor = GridBagConstraints.EAST;
            gbc_lblGegnerInDistanzH.insets = new Insets(0, 0, 5, 5);
            gbc_lblGegnerInDistanzH.gridx = 0;
            gbc_lblGegnerInDistanzH.gridy = 6;
            bewegungPanel.add(lblGegnerInDistanzH, gbc_lblGegnerInDistanzH);

            spinnerGegnerInDistanzH = new JSpinner();
            GridBagConstraints gbc_spinner = new GridBagConstraints();
            gbc_spinner.anchor = GridBagConstraints.WEST;
            gbc_spinner.insets = new Insets(0, 0, 5, 0);
            gbc_spinner.gridx = 1;
            gbc_spinner.gridy = 6;
            bewegungPanel.add(spinnerGegnerInDistanzH, gbc_spinner);

            JLabel lblGegnerInDistanzNS = new JLabel(BUNDLE.getString("FernkampfDialog.lblGegnerInDistanzNS.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblGegnerInDistanzNS = new GridBagConstraints();
            gbc_lblGegnerInDistanzNS.anchor = GridBagConstraints.EAST;
            gbc_lblGegnerInDistanzNS.insets = new Insets(0, 0, 0, 5);
            gbc_lblGegnerInDistanzNS.gridx = 0;
            gbc_lblGegnerInDistanzNS.gridy = 7;
            bewegungPanel.add(lblGegnerInDistanzNS, gbc_lblGegnerInDistanzNS);

            spinnerGegnerInDistanzNS = new JSpinner();
            GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
            gbc_spinner_1.anchor = GridBagConstraints.WEST;
            gbc_spinner_1.gridx = 1;
            gbc_spinner_1.gridy = 7;
            bewegungPanel.add(spinnerGegnerInDistanzNS, gbc_spinner_1);
            {
                JPanel sichtPanel = new JPanel();
                tabbedPane.addTab("Sicht", null, sichtPanel, null);
                tabbedPane.setEnabledAt(3, true);
                GridBagLayout gbl_sichtPanel = new GridBagLayout();
                gbl_sichtPanel.columnWidths = new int[] { 141, 0, 0, 40, 0, 0 };
                gbl_sichtPanel.rowHeights = new int[] { 0, 23, 23, 23, 23, 23, 23, 0, 0, 0 };
                gbl_sichtPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
                gbl_sichtPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                        Double.MIN_VALUE };
                sichtPanel.setLayout(gbl_sichtPanel);

                JLabel lblLichtquelle = new JLabel(BUNDLE.getString("FernkampfDialog.lblLichtquelle.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblLichtquelle = new GridBagConstraints();
                gbc_lblLichtquelle.anchor = GridBagConstraints.WEST;
                gbc_lblLichtquelle.insets = new Insets(0, 0, 5, 5);
                gbc_lblLichtquelle.gridx = 0;
                gbc_lblLichtquelle.gridy = 0;
                sichtPanel.add(lblLichtquelle, gbc_lblLichtquelle);

                JLabel lblModifikatoren = new JLabel(BUNDLE.getString("FernkampfDialog.lblModifikatoren.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblModifikatoren = new GridBagConstraints();
                gbc_lblModifikatoren.insets = new Insets(0, 0, 5, 5);
                gbc_lblModifikatoren.gridx = 2;
                gbc_lblModifikatoren.gridy = 0;
                sichtPanel.add(lblModifikatoren, gbc_lblModifikatoren);

                JLabel lblVorteile = new JLabel(BUNDLE.getString("FernkampfDialog.lblVorteile.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblVorteile = new GridBagConstraints();
                gbc_lblVorteile.anchor = GridBagConstraints.WEST;
                gbc_lblVorteile.insets = new Insets(0, 0, 5, 0);
                gbc_lblVorteile.gridx = 4;
                gbc_lblVorteile.gridy = 0;
                sichtPanel.add(lblVorteile, gbc_lblVorteile);
                
                ButtonGroup lichtButtonGroup = new ButtonGroup();
                JRadioButton rdbNormaleSicht = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbNormaleSicht.text")); //$NON-NLS-1$
                lichtButtonGroup.add(rdbNormaleSicht);
                rdbNormaleSicht.setSelected(true);
                GridBagConstraints gbc_rdbNormaleSicht = new GridBagConstraints();
                gbc_rdbNormaleSicht.anchor = GridBagConstraints.WEST;
                gbc_rdbNormaleSicht.insets = new Insets(0, 0, 5, 5);
                gbc_rdbNormaleSicht.gridx = 0;
                gbc_rdbNormaleSicht.gridy = 1;
                sichtPanel.add(rdbNormaleSicht, gbc_rdbNormaleSicht);
                sichtGroup.add(rdbNormaleSicht);

                JCheckBox chkboxVorteilEntfernungssinn = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkboxVorteilEntfernungssinn.text")); //$NON-NLS-1$
                vorteilListe.add(chkboxVorteilEntfernungssinn);
                GridBagConstraints gbc_chckbxNewCheckBox_8 = new GridBagConstraints();
                gbc_chckbxNewCheckBox_8.anchor = GridBagConstraints.WEST;
                gbc_chckbxNewCheckBox_8.insets = new Insets(0, 0, 5, 0);
                gbc_chckbxNewCheckBox_8.gridx = 4;
                gbc_chckbxNewCheckBox_8.gridy = 1;
                sichtPanel.add(chkboxVorteilEntfernungssinn, gbc_chckbxNewCheckBox_8);

                JRadioButton rdbDaemmerung = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbDaemmerung.text")); //$NON-NLS-1$
                lichtButtonGroup.add(rdbDaemmerung);
                GridBagConstraints gbc_rdbDaemmerung = new GridBagConstraints();
                gbc_rdbDaemmerung.anchor = GridBagConstraints.WEST;
                gbc_rdbDaemmerung.insets = new Insets(0, 0, 5, 5);
                gbc_rdbDaemmerung.gridx = 0;
                gbc_rdbDaemmerung.gridy = 2;
                sichtPanel.add(rdbDaemmerung, gbc_rdbDaemmerung);
                sichtGroup.add(rdbDaemmerung);

                sichtModGroup = new ArrayList<>();
                ButtonGroup sichtModButtonGroup = new ButtonGroup();
                
                JRadioButton rdbtnKeine = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbtnKeine.text")); //$NON-NLS-1$
                rdbtnKeine.setSelected(true);
                sichtModButtonGroup.add(rdbtnKeine);
                GridBagConstraints gbc_rdbtnKeine = new GridBagConstraints();
                gbc_rdbtnKeine.anchor = GridBagConstraints.WEST;
                gbc_rdbtnKeine.insets = new Insets(0, 0, 5, 5);
                gbc_rdbtnKeine.gridx = 2;
                gbc_rdbtnKeine.gridy = 1;
                sichtPanel.add(rdbtnKeine, gbc_rdbtnKeine);
                sichtModGroup.add(rdbtnKeine);
                
                JRadioButton rdbDunst = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbDunst.text")); //$NON-NLS-1$
                sichtModButtonGroup.add(rdbDunst);
                GridBagConstraints gbc_rdbDunst = new GridBagConstraints();
                gbc_rdbDunst.anchor = GridBagConstraints.WEST;
                gbc_rdbDunst.insets = new Insets(0, 0, 5, 5);
                gbc_rdbDunst.gridx = 2;
                gbc_rdbDunst.gridy = 2;
                sichtPanel.add(rdbDunst, gbc_rdbDunst);
                sichtModGroup.add(rdbDunst);

                JCheckBox chkboxDaemmerungssicht = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkboxDaemmerungssicht.text")); //$NON-NLS-1$
                vorteilListe.add(chkboxDaemmerungssicht);
                GridBagConstraints gbc_chckbxNewCheckBox_9 = new GridBagConstraints();
                gbc_chckbxNewCheckBox_9.anchor = GridBagConstraints.WEST;
                gbc_chckbxNewCheckBox_9.insets = new Insets(0, 0, 5, 0);
                gbc_chckbxNewCheckBox_9.gridx = 4;
                gbc_chckbxNewCheckBox_9.gridy = 2;
                sichtPanel.add(chkboxDaemmerungssicht, gbc_chckbxNewCheckBox_9);

                JRadioButton rdbMondlicht = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbMondlicht.text")); //$NON-NLS-1$
                lichtButtonGroup.add(rdbMondlicht);
                GridBagConstraints gbc_rdbMondlicht = new GridBagConstraints();
                gbc_rdbMondlicht.anchor = GridBagConstraints.WEST;
                gbc_rdbMondlicht.insets = new Insets(0, 0, 5, 5);
                gbc_rdbMondlicht.gridx = 0;
                gbc_rdbMondlicht.gridy = 3;
                sichtPanel.add(rdbMondlicht, gbc_rdbMondlicht);
                sichtGroup.add(rdbMondlicht);
                
                JRadioButton rdbNebel = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbNebel.text")); //$NON-NLS-1$
                sichtModButtonGroup.add(rdbNebel);
                GridBagConstraints gbc_rdbNebel = new GridBagConstraints();
                gbc_rdbNebel.anchor = GridBagConstraints.WEST;
                gbc_rdbNebel.insets = new Insets(0, 0, 5, 5);
                gbc_rdbNebel.gridx = 2;
                gbc_rdbNebel.gridy = 3;
                sichtPanel.add(rdbNebel, gbc_rdbNebel);
                sichtModGroup.add(rdbNebel);

                JCheckBox chkboxNachtsicht = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkboxNachtsicht.text")); //$NON-NLS-1$
                vorteilListe.add(chkboxNachtsicht);
                GridBagConstraints gbc_chckbxNewCheckBox_10 = new GridBagConstraints();
                gbc_chckbxNewCheckBox_10.anchor = GridBagConstraints.WEST;
                gbc_chckbxNewCheckBox_10.insets = new Insets(0, 0, 5, 0);
                gbc_chckbxNewCheckBox_10.gridx = 4;
                gbc_chckbxNewCheckBox_10.gridy = 3;
                sichtPanel.add(chkboxNachtsicht, gbc_chckbxNewCheckBox_10);

                JRadioButton rdbSternenlicht = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbSternenlicht.text")); //$NON-NLS-1$
                lichtButtonGroup.add(rdbSternenlicht);
                GridBagConstraints gbc_rdbSternenlicht = new GridBagConstraints();
                gbc_rdbSternenlicht.anchor = GridBagConstraints.WEST;
                gbc_rdbSternenlicht.insets = new Insets(0, 0, 5, 5);
                gbc_rdbSternenlicht.gridx = 0;
                gbc_rdbSternenlicht.gridy = 4;
                sichtPanel.add(rdbSternenlicht, gbc_rdbSternenlicht);
                sichtGroup.add(rdbSternenlicht);

                JLabel lblNachteile = new JLabel(BUNDLE.getString("FernkampfDialog.lblNachteile.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblNachteile = new GridBagConstraints();
                gbc_lblNachteile.anchor = GridBagConstraints.WEST;
                gbc_lblNachteile.insets = new Insets(0, 0, 5, 0);
                gbc_lblNachteile.gridx = 4;
                gbc_lblNachteile.gridy = 4;
                sichtPanel.add(lblNachteile, gbc_lblNachteile);

                JRadioButton rdbFinsternis = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbFinsternis.text")); //$NON-NLS-1$
                lichtButtonGroup.add(rdbFinsternis);
                GridBagConstraints gbc_rdbFinsternis = new GridBagConstraints();
                gbc_rdbFinsternis.anchor = GridBagConstraints.WEST;
                gbc_rdbFinsternis.insets = new Insets(0, 0, 5, 5);
                gbc_rdbFinsternis.gridx = 0;
                gbc_rdbFinsternis.gridy = 5;
                sichtPanel.add(rdbFinsternis, gbc_rdbFinsternis);
                sichtGroup.add(rdbFinsternis);

                JCheckBox chkboxEinaeugig = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkboxEinaeugig.text")); //$NON-NLS-1$
                nachteilListe.add(chkboxEinaeugig);
                GridBagConstraints gbc_chckbxNewCheckBox_12 = new GridBagConstraints();
                gbc_chckbxNewCheckBox_12.anchor = GridBagConstraints.WEST;
                gbc_chckbxNewCheckBox_12.insets = new Insets(0, 0, 5, 0);
                gbc_chckbxNewCheckBox_12.gridx = 4;
                gbc_chckbxNewCheckBox_12.gridy = 5;
                sichtPanel.add(chkboxEinaeugig, gbc_chckbxNewCheckBox_12);

                JRadioButton rdbUnsichtbar = new JRadioButton(BUNDLE.getString("FernkampfDialog.rdbUnsichtbar.text")); //$NON-NLS-1$
                lichtButtonGroup.add(rdbUnsichtbar);
                GridBagConstraints gbc_rdbUnsichtbar = new GridBagConstraints();
                gbc_rdbUnsichtbar.insets = new Insets(0, 0, 5, 5);
                gbc_rdbUnsichtbar.anchor = GridBagConstraints.WEST;
                gbc_rdbUnsichtbar.gridx = 0;
                gbc_rdbUnsichtbar.gridy = 6;
                sichtPanel.add(rdbUnsichtbar, gbc_rdbUnsichtbar);
                sichtGroup.add(rdbUnsichtbar);

                JCheckBox chkboxFarbenblind = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkboxFarbenblind.text")); //$NON-NLS-1$
                nachteilListe.add(chkboxFarbenblind);
                GridBagConstraints gbc_chckbxNewCheckBox_13 = new GridBagConstraints();
                gbc_chckbxNewCheckBox_13.anchor = GridBagConstraints.WEST;
                gbc_chckbxNewCheckBox_13.insets = new Insets(0, 0, 5, 0);
                gbc_chckbxNewCheckBox_13.gridx = 4;
                gbc_chckbxNewCheckBox_13.gridy = 6;
                sichtPanel.add(chkboxFarbenblind, gbc_chckbxNewCheckBox_13);

                JCheckBox chkboxKurzsichtig = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkboxKurzsichtig.text")); //$NON-NLS-1$
                nachteilListe.add(chkboxKurzsichtig);
                GridBagConstraints gbc_chckbxNewCheckBox_14 = new GridBagConstraints();
                gbc_chckbxNewCheckBox_14.insets = new Insets(0, 0, 5, 0);
                gbc_chckbxNewCheckBox_14.anchor = GridBagConstraints.WEST;
                gbc_chckbxNewCheckBox_14.gridx = 4;
                gbc_chckbxNewCheckBox_14.gridy = 7;
                sichtPanel.add(chkboxKurzsichtig, gbc_chckbxNewCheckBox_14);

                JCheckBox chkboxNachtblind = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkboxNachtblind.text")); //$NON-NLS-1$
                nachteilListe.add(chkboxNachtblind);
                GridBagConstraints gbc_chckbxNewCheckBox_11 = new GridBagConstraints();
                gbc_chckbxNewCheckBox_11.anchor = GridBagConstraints.WEST;
                gbc_chckbxNewCheckBox_11.gridx = 4;
                gbc_chckbxNewCheckBox_11.gridy = 8;
                sichtPanel.add(chkboxNachtblind, gbc_chckbxNewCheckBox_11);
            }
            {
                JPanel modifikatorPanel = new JPanel();
                tabbedPane.addTab("Modifikatoren", null, modifikatorPanel, null);
                tabbedPane.setEnabledAt(4, true);
                GridBagLayout gbl_modifikatorPanel = new GridBagLayout();
                gbl_modifikatorPanel.columnWidths = new int[] { 210, 209 };
                gbl_modifikatorPanel.rowHeights = new int[] { 0, 183, 0 };
                gbl_modifikatorPanel.columnWeights = new double[] { 1.0, 1.0 };
                gbl_modifikatorPanel.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
                modifikatorPanel.setLayout(gbl_modifikatorPanel);

                modifikatorGroup = new ArrayList<>();
                
                comboSchuetzentyp = new JComboBox<>();
                comboSchuetzentyp.setModel(new DefaultComboBoxModel<>(new String[] {"Normaler Schütze", "Scharfschütze", "Meisterschütze"}));
                GridBagConstraints gbc_comboSchuetzentyp = new GridBagConstraints();
                gbc_comboSchuetzentyp.gridwidth = 2;
                gbc_comboSchuetzentyp.insets = new Insets(0, 0, 5, 5);
                gbc_comboSchuetzentyp.fill = GridBagConstraints.HORIZONTAL;
                gbc_comboSchuetzentyp.gridx = 0;
                gbc_comboSchuetzentyp.gridy = 0;
                modifikatorPanel.add(comboSchuetzentyp, gbc_comboSchuetzentyp);

                JPanel modPanel_1 = new JPanel();
                GridBagConstraints gbc_modPanel_1 = new GridBagConstraints();
                gbc_modPanel_1.anchor = GridBagConstraints.NORTHWEST;
                gbc_modPanel_1.insets = new Insets(0, 0, 0, 5);
                gbc_modPanel_1.gridx = 0;
                gbc_modPanel_1.gridy = 1;
                modifikatorPanel.add(modPanel_1, gbc_modPanel_1);
                modPanel_1.setLayout(new BoxLayout(modPanel_1, BoxLayout.Y_AXIS));

                JCheckBox chckbxSteilschussNachUnten = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSteilschussNachUnten.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSteilschussNachUnten);
                modPanel_1.add(chckbxSteilschussNachUnten);

                JCheckBox chckbxSteilwurfNachUnten = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSteilwurfNachUnten.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSteilwurfNachUnten);
                modPanel_1.add(chckbxSteilwurfNachUnten);

                JCheckBox chckbxSteilschussNachOben = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSteilschussNachOben.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSteilschussNachOben);
                modPanel_1.add(chckbxSteilschussNachOben);

                JCheckBox chckbxSteilwurfNachOben = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSteilwurfNachOben.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSteilwurfNachOben);
                modPanel_1.add(chckbxSteilwurfNachOben);

                JCheckBox chckbxBoeigerSeitenwind = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxBoeigerSeitenwind.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxBoeigerSeitenwind);
                modPanel_1.add(chckbxBoeigerSeitenwind);

                JCheckBox chckbxStarkerBoeigerSeitenwind = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxStarkerBoeigerSeitenwind.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxStarkerBoeigerSeitenwind);
                modPanel_1.add(chckbxStarkerBoeigerSeitenwind);

                JCheckBox chckbxSchnellschuss = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSchnellschuss.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSchnellschuss);
                modPanel_1.add(chckbxSchnellschuss);

                JCheckBox chckbxUnterWasser = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxUnterWasser.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxUnterWasser);
                modPanel_1.add(chckbxUnterWasser);

                JPanel panel_1 = new JPanel();
                modPanel_1.add(panel_1);
                GridBagLayout gbl_panel_1 = new GridBagLayout();
                gbl_panel_1.columnWidths = new int[] { 30, 60, 0 };
                gbl_panel_1.rowHeights = new int[] { 27, 0, 0 };
                gbl_panel_1.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
                gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
                panel_1.setLayout(gbl_panel_1);
                
                SpinnerNumberModel spinnerModelAnsage = new SpinnerNumberModel(0, 0, 100, 1);                 
                spinnerAnsage = new JSpinner(spinnerModelAnsage);
                GridBagConstraints gbc_spinnerAnsage = new GridBagConstraints();
                gbc_spinnerAnsage.anchor = GridBagConstraints.EAST;
                gbc_spinnerAnsage.insets = new Insets(0, 0, 5, 5);
                gbc_spinnerAnsage.gridx = 0;
                gbc_spinnerAnsage.gridy = 0;
                panel_1.add(spinnerAnsage, gbc_spinnerAnsage);

                JLabel lblModifikatorAnsage = new JLabel(BUNDLE.getString("FernkampfDialog.lblModifikatorAnsage.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblModifikatorAnsage = new GridBagConstraints();
                gbc_lblModifikatorAnsage.insets = new Insets(0, 0, 5, 0);
                gbc_lblModifikatorAnsage.anchor = GridBagConstraints.WEST;
                gbc_lblModifikatorAnsage.gridx = 1;
                gbc_lblModifikatorAnsage.gridy = 0;
                panel_1.add(lblModifikatorAnsage, gbc_lblModifikatorAnsage);
                
                SpinnerNumberModel spinnerModelZielen = new SpinnerNumberModel(0, 0, 4, 1); 
                spinnerZielen = new JSpinner(spinnerModelZielen);
                
                GridBagConstraints gbc_spinnerZielen = new GridBagConstraints();
                gbc_spinnerZielen.anchor = GridBagConstraints.EAST;
                gbc_spinnerZielen.insets = new Insets(0, 0, 0, 5);
                gbc_spinnerZielen.gridx = 0;
                gbc_spinnerZielen.gridy = 1;
                panel_1.add(spinnerZielen, gbc_spinnerZielen);

                JLabel lblModifikatorZielen = new JLabel(BUNDLE.getString("FernkampfDialog.lblModifikatorZielen.text")); //$NON-NLS-1$
                GridBagConstraints gbc_lblModifikatorZielen = new GridBagConstraints();
                gbc_lblModifikatorZielen.anchor = GridBagConstraints.WEST;
                gbc_lblModifikatorZielen.gridx = 1;
                gbc_lblModifikatorZielen.gridy = 1;
                panel_1.add(lblModifikatorZielen, gbc_lblModifikatorZielen);

                JPanel modPanel_2 = new JPanel();
                GridBagConstraints gbc_modPanel_2 = new GridBagConstraints();
                gbc_modPanel_2.anchor = GridBagConstraints.NORTHEAST;
                gbc_modPanel_2.gridx = 1;
                gbc_modPanel_2.gridy = 1;
                modifikatorPanel.add(modPanel_2, gbc_modPanel_2);
                modPanel_2.setLayout(new BoxLayout(modPanel_2, BoxLayout.Y_AXIS));

                JCheckBox chckbxZweiterSchussProKR = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxZweiterSchussProKR.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxZweiterSchussProKR);
                modPanel_2.add(chckbxZweiterSchussProKR);

                JCheckBox chckbxZweiterWurfProKR = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxZweiterWurfProKR.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxZweiterWurfProKR);
                modPanel_2.add(chckbxZweiterWurfProKR);

                JCheckBox chckbxSchussVonStehendemTier = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSchussVonStehendemTier.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSchussVonStehendemTier);
                modPanel_2.add(chckbxSchussVonStehendemTier);

                JCheckBox chckbxWurfVonStehendemTier = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxWurfVonStehendemTier.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxWurfVonStehendemTier);
                modPanel_2.add(chckbxWurfVonStehendemTier);

                JCheckBox chckbxSchussVonReittierImSchritt = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSchussVonReittierImSchritt.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSchussVonReittierImSchritt);
                modPanel_2.add(chckbxSchussVonReittierImSchritt);

                JCheckBox chckbxWurfVonReittierImSchritt = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxWurfVonReittierImSchritt.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxWurfVonReittierImSchritt);
                modPanel_2.add(chckbxWurfVonReittierImSchritt);

                JCheckBox chckbxSchussImGalopp = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSchussImGalopp.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSchussImGalopp);
                modPanel_2.add(chckbxSchussImGalopp);

                JCheckBox chckbxWurfImGalopp = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxWurfImGalopp.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxWurfImGalopp);
                modPanel_2.add(chckbxWurfImGalopp);

                JCheckBox chckbxSchussOhneSattel = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxSchussOhneSattel.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxSchussOhneSattel);
                modPanel_2.add(chckbxSchussOhneSattel);

                JCheckBox chckbxWurfOhneSattel = new JCheckBox(BUNDLE.getString("FernkampfDialog.chckbxWurfOhneSattel.text")); //$NON-NLS-1$
                modifikatorGroup.add(chckbxWurfOhneSattel);
                modPanel_2.add(chckbxWurfOhneSattel);
                
            }
            {
                JPanel zielPanel = new JPanel();
                tabbedPane.addTab("Ziel", null, zielPanel, null);
                tabbedPane.setEnabledAt(5, true);
                GridBagLayout gbl_zielPanel = new GridBagLayout();
                gbl_zielPanel.columnWidths = new int[] { 200, 0, 200, 0 };
                gbl_zielPanel.rowHeights = new int[] { 0, 16, 0, 0 };
                gbl_zielPanel.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
                gbl_zielPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
                zielPanel.setLayout(gbl_zielPanel);

                chkBoxGezielterSchuss = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkBoxGezielterSchuss.text")); //$NON-NLS-1$
                chkBoxGezielterSchuss.addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent e) {
                        JCheckBox chkBox = (JCheckBox)e.getSource();
                        enableGezielterSchuss(chkBox.isSelected());
                    }
                });
                GridBagConstraints gbc_chkBoxGezielterSchuss = new GridBagConstraints();
                gbc_chkBoxGezielterSchuss.gridwidth = 2;
                gbc_chkBoxGezielterSchuss.anchor = GridBagConstraints.WEST;
                gbc_chkBoxGezielterSchuss.insets = new Insets(0, 0, 5, 5);
                gbc_chkBoxGezielterSchuss.gridx = 0;
                gbc_chkBoxGezielterSchuss.gridy = 0;
                zielPanel.add(chkBoxGezielterSchuss, gbc_chkBoxGezielterSchuss);

                JPanel zielPanel_1 = new JPanel();
                zielPanel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
                zielPanel_1.setAlignmentY(Component.TOP_ALIGNMENT);

                GridBagConstraints gbc_zielPanel_1 = new GridBagConstraints();
                gbc_zielPanel_1.anchor = GridBagConstraints.NORTHWEST;
                gbc_zielPanel_1.insets = new Insets(0, 0, 5, 5);
                gbc_zielPanel_1.gridx = 0;
                gbc_zielPanel_1.gridy = 1;
                zielPanel.add(zielPanel_1, gbc_zielPanel_1);
                GridBagLayout gbl_zielPanel_1 = new GridBagLayout();
                gbl_zielPanel_1.columnWidths = new int[] { 69, 0, 0, 0 };
                gbl_zielPanel_1.rowHeights = new int[] { 16, 23, 23, 23, 23, 23, 0 };
                gbl_zielPanel_1.columnWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
                gbl_zielPanel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
                zielPanel_1.setLayout(gbl_zielPanel_1);
                
                ButtonGroup gezielterSchussGroup = new ButtonGroup();

                lblZielHumanoid = new JLabel(BUNDLE.getString("FernkampfDialog.lblZielHumanoid.text")); //$NON-NLS-1$
                lblZielHumanoid.setVerticalAlignment(SwingConstants.TOP);
                GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
                gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
                gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
                gbc_lblNewLabel_5.gridx = 0;
                gbc_lblNewLabel_5.gridy = 0;
                zielPanel_1.add(lblZielHumanoid, gbc_lblNewLabel_5);

                JRadioButton radioZielHumanoidKopf = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidKopf.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidKopf);
                radioZielHumanoidKopf.setEnabled(false);
                GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
                gbc_rdbtnNewRadioButton.anchor = GridBagConstraints.WEST;
                gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
                gbc_rdbtnNewRadioButton.gridx = 0;
                gbc_rdbtnNewRadioButton.gridy = 1;
                zielPanel_1.add(radioZielHumanoidKopf, gbc_rdbtnNewRadioButton);
                zielHumanoidGroup.add(radioZielHumanoidKopf);

                JRadioButton radioZielHumanoidHand = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidHand.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidHand);
                radioZielHumanoidHand.setEnabled(false);
                GridBagConstraints gbc_rdbtnNewRadioButton_5 = new GridBagConstraints();
                gbc_rdbtnNewRadioButton_5.anchor = GridBagConstraints.WEST;
                gbc_rdbtnNewRadioButton_5.insets = new Insets(0, 0, 5, 0);
                gbc_rdbtnNewRadioButton_5.gridx = 2;
                gbc_rdbtnNewRadioButton_5.gridy = 1;
                zielPanel_1.add(radioZielHumanoidHand, gbc_rdbtnNewRadioButton_5);
                zielHumanoidGroup.add(radioZielHumanoidHand);

                JRadioButton radioZielHumanoidBrust = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidBrust.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidBrust);
                radioZielHumanoidBrust.setEnabled(false);
                GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
                gbc_rdbtnNewRadioButton_1.anchor = GridBagConstraints.WEST;
                gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 5);
                gbc_rdbtnNewRadioButton_1.gridx = 0;
                gbc_rdbtnNewRadioButton_1.gridy = 2;
                zielPanel_1.add(radioZielHumanoidBrust, gbc_rdbtnNewRadioButton_1);
                zielHumanoidGroup.add(radioZielHumanoidBrust);

                JRadioButton radioZielHumanoidFuss = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidFuss.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidFuss);
                radioZielHumanoidFuss.setEnabled(false);
                GridBagConstraints gbc_radioZielHumanoidFuss = new GridBagConstraints();
                gbc_radioZielHumanoidFuss.anchor = GridBagConstraints.WEST;
                gbc_radioZielHumanoidFuss.insets = new Insets(0, 0, 5, 0);
                gbc_radioZielHumanoidFuss.gridx = 2;
                gbc_radioZielHumanoidFuss.gridy = 2;
                zielPanel_1.add(radioZielHumanoidFuss, gbc_radioZielHumanoidFuss);
                zielHumanoidGroup.add(radioZielHumanoidFuss);

                JRadioButton radioZielHumanoidArme = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidArme.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidArme);
                radioZielHumanoidArme.setEnabled(false);
                GridBagConstraints gbc_radioZielHumanoidArme = new GridBagConstraints();
                gbc_radioZielHumanoidArme.anchor = GridBagConstraints.WEST;
                gbc_radioZielHumanoidArme.insets = new Insets(0, 0, 5, 5);
                gbc_radioZielHumanoidArme.gridx = 0;
                gbc_radioZielHumanoidArme.gridy = 3;
                zielPanel_1.add(radioZielHumanoidArme, gbc_radioZielHumanoidArme);
                zielHumanoidGroup.add(radioZielHumanoidArme);

                JRadioButton radioZielHumanoidAuge = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidAuge.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidAuge);
                radioZielHumanoidAuge.setEnabled(false);
                GridBagConstraints gbc_radioZielHumanoidAuge = new GridBagConstraints();
                gbc_radioZielHumanoidAuge.anchor = GridBagConstraints.WEST;
                gbc_radioZielHumanoidAuge.insets = new Insets(0, 0, 5, 0);
                gbc_radioZielHumanoidAuge.gridx = 2;
                gbc_radioZielHumanoidAuge.gridy = 3;
                zielPanel_1.add(radioZielHumanoidAuge, gbc_radioZielHumanoidAuge);
                zielHumanoidGroup.add(radioZielHumanoidAuge);

                JRadioButton radioZielHumanoidBauch = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidBauch.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidBauch);
                radioZielHumanoidBauch.setEnabled(false);
                GridBagConstraints gbc_radioZielHumanoidBauch = new GridBagConstraints();
                gbc_radioZielHumanoidBauch.anchor = GridBagConstraints.WEST;
                gbc_radioZielHumanoidBauch.insets = new Insets(0, 0, 5, 5);
                gbc_radioZielHumanoidBauch.gridx = 0;
                gbc_radioZielHumanoidBauch.gridy = 4;
                zielPanel_1.add(radioZielHumanoidBauch, gbc_radioZielHumanoidBauch);
                zielHumanoidGroup.add(radioZielHumanoidBauch);

                JRadioButton radioZielHumanoidHerz = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidHerz.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidHerz);
                radioZielHumanoidHerz.setEnabled(false);
                GridBagConstraints gbc_radioZielHumanoidHerz = new GridBagConstraints();
                gbc_radioZielHumanoidHerz.insets = new Insets(0, 0, 5, 0);
                gbc_radioZielHumanoidHerz.anchor = GridBagConstraints.WEST;
                gbc_radioZielHumanoidHerz.gridx = 2;
                gbc_radioZielHumanoidHerz.gridy = 4;
                zielPanel_1.add(radioZielHumanoidHerz, gbc_radioZielHumanoidHerz);
                zielHumanoidGroup.add(radioZielHumanoidHerz);

                JRadioButton radioZielHumanoidBeine = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielHumanoidBeine.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielHumanoidBeine);
                radioZielHumanoidBeine.setEnabled(false);
                GridBagConstraints gbc_radioZielHumanoidBeine = new GridBagConstraints();
                gbc_radioZielHumanoidBeine.anchor = GridBagConstraints.WEST;
                gbc_radioZielHumanoidBeine.insets = new Insets(0, 0, 0, 5);
                gbc_radioZielHumanoidBeine.gridx = 0;
                gbc_radioZielHumanoidBeine.gridy = 5;
                zielPanel_1.add(radioZielHumanoidBeine, gbc_radioZielHumanoidBeine);
                zielHumanoidGroup.add(radioZielHumanoidBeine);

                JPanel zielPanel_2 = new JPanel();
                zielPanel_2.setAlignmentX(Component.LEFT_ALIGNMENT);
                zielPanel_2.setAlignmentY(Component.TOP_ALIGNMENT);
                zielPanel_2.setLayout(new BoxLayout(zielPanel_2, BoxLayout.Y_AXIS));
                GridBagConstraints gbc_zielPanel_2 = new GridBagConstraints();
                gbc_zielPanel_2.insets = new Insets(0, 0, 5, 0);
                gbc_zielPanel_2.anchor = GridBagConstraints.NORTHWEST;
                gbc_zielPanel_2.gridx = 2;
                gbc_zielPanel_2.gridy = 1;
                zielPanel.add(zielPanel_2, gbc_zielPanel_2);

                lblZielTier = new JLabel(BUNDLE.getString("FernkampfDialog.lblZielTier.text")); //$NON-NLS-1$
                lblZielTier.setVerticalAlignment(SwingConstants.TOP);
                zielPanel_2.add(lblZielTier);

                JRadioButton radioZielTierRumpf = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielTierRumpf.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielTierRumpf);
                radioZielTierRumpf.setEnabled(false);
                zielPanel_2.add(radioZielTierRumpf);

                JRadioButton radioZielTierBein = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielTierBein.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielTierBein);
                radioZielTierBein.setEnabled(false);
                zielPanel_2.add(radioZielTierBein);

                JRadioButton radioZielTierVStelle = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielTierVStelle.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielTierVStelle);
                radioZielTierVStelle.setEnabled(false);
                zielPanel_2.add(radioZielTierVStelle);

                JRadioButton radioZielTierKopf = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielTierKopf.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielTierKopf);
                radioZielTierKopf.setEnabled(false);
                zielPanel_2.add(radioZielTierKopf);

                JRadioButton radioZielTierSchwanz = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielTierSchwanz.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielTierSchwanz);
                radioZielTierSchwanz.setEnabled(false);
                zielPanel_2.add(radioZielTierSchwanz);

                JRadioButton radioZielTierSinnesorgan = new JRadioButton(BUNDLE.getString("FernkampfDialog.radioZielTierSinnesorgan.text")); //$NON-NLS-1$
                gezielterSchussGroup.add(radioZielTierSinnesorgan);
                radioZielTierSinnesorgan.setEnabled(false);
                zielPanel_2.add(radioZielTierSinnesorgan);

                zielTierGroup.add(radioZielTierRumpf);
                zielTierGroup.add(radioZielTierBein);
                zielTierGroup.add(radioZielTierVStelle);
                zielTierGroup.add(radioZielTierKopf);
                zielTierGroup.add(radioZielTierSchwanz);
                zielTierGroup.add(radioZielTierSinnesorgan);

                chkBoxKoerperteilBewegung = new JCheckBox(BUNDLE.getString("FernkampfDialog.chkBoxKoerperteilBewegung.text")); //$NON-NLS-1$
                chkBoxKoerperteilBewegung.setEnabled(false);
                GridBagConstraints gbc_chkBoxKoerperteilBewegung = new GridBagConstraints();
                gbc_chkBoxKoerperteilBewegung.gridwidth = 2;
                gbc_chkBoxKoerperteilBewegung.anchor = GridBagConstraints.WEST;
                gbc_chkBoxKoerperteilBewegung.insets = new Insets(0, 0, 0, 5);
                gbc_chkBoxKoerperteilBewegung.gridx = 0;
                gbc_chkBoxKoerperteilBewegung.gridy = 2;
                zielPanel.add(chkBoxKoerperteilBewegung, gbc_chkBoxKoerperteilBewegung);
            }
            
            JPanel summaryPanel = new JPanel();
            tabbedPane.addTab("Zusammenfassung", null, summaryPanel, null);
            tabbedPane.setEnabledAt(6, true);
            GridBagLayout gbl_summaryPanel = new GridBagLayout();
            gbl_summaryPanel.columnWidths = new int[]{200, 15, 30, 0};
            gbl_summaryPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            gbl_summaryPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
            gbl_summaryPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0};
            summaryPanel.setLayout(gbl_summaryPanel);
            
            JLabel lblZusammenfassungHeadline = new JLabel(BUNDLE.getString("FernkampfDialog.lblZusammenfassungHeadline.text")); //$NON-NLS-1$
            lblZusammenfassungHeadline.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
            GridBagConstraints gbc_lblZusammenfassungHeadline = new GridBagConstraints();
            gbc_lblZusammenfassungHeadline.anchor = GridBagConstraints.WEST;
            gbc_lblZusammenfassungHeadline.insets = new Insets(0, 0, 5, 5);
            gbc_lblZusammenfassungHeadline.gridx = 0;
            gbc_lblZusammenfassungHeadline.gridy = 0;
            summaryPanel.add(lblZusammenfassungHeadline, gbc_lblZusammenfassungHeadline);
            
            JLabel lblErschwernisZielgroesse = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisZielgroesse.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisZielgroesse = new GridBagConstraints();
            gbc_lblErschwernisZielgroesse.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisZielgroesse.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisZielgroesse.gridx = 0;
            gbc_lblErschwernisZielgroesse.gridy = 1;
            summaryPanel.add(lblErschwernisZielgroesse, gbc_lblErschwernisZielgroesse);
            
            lblErschwernisZielgroesseValue = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisZielgroesseValue.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisZielgroesseValue = new GridBagConstraints();
            gbc_lblErschwernisZielgroesseValue.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisZielgroesseValue.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisZielgroesseValue.gridx = 2;
            gbc_lblErschwernisZielgroesseValue.gridy = 1;
            summaryPanel.add(lblErschwernisZielgroesseValue, gbc_lblErschwernisZielgroesseValue);
            
            JLabel lblErschwernisEntfernung = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisEntfernung.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisEntfernung = new GridBagConstraints();
            gbc_lblErschwernisEntfernung.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisEntfernung.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisEntfernung.gridx = 0;
            gbc_lblErschwernisEntfernung.gridy = 2;
            summaryPanel.add(lblErschwernisEntfernung, gbc_lblErschwernisEntfernung);
            
            lblErschwernisEntfernungValue = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisEntfernungValue.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisEntfernungValue = new GridBagConstraints();
            gbc_lblErschwernisEntfernungValue.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisEntfernungValue.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisEntfernungValue.gridx = 2;
            gbc_lblErschwernisEntfernungValue.gridy = 2;
            summaryPanel.add(lblErschwernisEntfernungValue, gbc_lblErschwernisEntfernungValue);
            
            JLabel lblErschwernisBewegung = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisBewegung.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisBewegung = new GridBagConstraints();
            gbc_lblErschwernisBewegung.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisBewegung.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisBewegung.gridx = 0;
            gbc_lblErschwernisBewegung.gridy = 3;
            summaryPanel.add(lblErschwernisBewegung, gbc_lblErschwernisBewegung);
            
            lblErschwernisBewegungValue = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisBewegungValue.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisBewegungValue = new GridBagConstraints();
            gbc_lblErschwernisBewegungValue.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisBewegungValue.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisBewegungValue.gridx = 2;
            gbc_lblErschwernisBewegungValue.gridy = 3;
            summaryPanel.add(lblErschwernisBewegungValue, gbc_lblErschwernisBewegungValue);
            
            JLabel lblErschwernisSicht = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisSicht.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisSicht = new GridBagConstraints();
            gbc_lblErschwernisSicht.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisSicht.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisSicht.gridx = 0;
            gbc_lblErschwernisSicht.gridy = 4;
            summaryPanel.add(lblErschwernisSicht, gbc_lblErschwernisSicht);
            
            lblErschwernisSichtValue = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisSichtValue.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisSichtValue = new GridBagConstraints();
            gbc_lblErschwernisSichtValue.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisSichtValue.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisSichtValue.gridx = 2;
            gbc_lblErschwernisSichtValue.gridy = 4;
            summaryPanel.add(lblErschwernisSichtValue, gbc_lblErschwernisSichtValue);
            
            JLabel lblErschwernisModifikatoren = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisModifikatoren.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisModifikatoren = new GridBagConstraints();
            gbc_lblErschwernisModifikatoren.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisModifikatoren.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisModifikatoren.gridx = 0;
            gbc_lblErschwernisModifikatoren.gridy = 5;
            summaryPanel.add(lblErschwernisModifikatoren, gbc_lblErschwernisModifikatoren);
            
            lblErschwernisModifikatorenValue = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisModifikatorenValue.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisModifikatorenValue = new GridBagConstraints();
            gbc_lblErschwernisModifikatorenValue.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisModifikatorenValue.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisModifikatorenValue.gridx = 2;
            gbc_lblErschwernisModifikatorenValue.gridy = 5;
            summaryPanel.add(lblErschwernisModifikatorenValue, gbc_lblErschwernisModifikatorenValue);
            
            JLabel lblErschwernisGezielterSchuss = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisGezielterSchuss.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisGezielterSchuss = new GridBagConstraints();
            gbc_lblErschwernisGezielterSchuss.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisGezielterSchuss.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisGezielterSchuss.gridx = 0;
            gbc_lblErschwernisGezielterSchuss.gridy = 6;
            summaryPanel.add(lblErschwernisGezielterSchuss, gbc_lblErschwernisGezielterSchuss);
            
            lblErschwernisGezielterSchussValue = new JLabel(BUNDLE.getString("FernkampfDialog.lblErschwernisGezielterSchussValue.text")); //$NON-NLS-1$
            GridBagConstraints gbc_lblErschwernisGezielterSchussValue = new GridBagConstraints();
            gbc_lblErschwernisGezielterSchussValue.anchor = GridBagConstraints.EAST;
            gbc_lblErschwernisGezielterSchussValue.insets = new Insets(0, 0, 5, 5);
            gbc_lblErschwernisGezielterSchussValue.gridx = 2;
            gbc_lblErschwernisGezielterSchussValue.gridy = 6;
            summaryPanel.add(lblErschwernisGezielterSchussValue, gbc_lblErschwernisGezielterSchussValue);
            
            JPanel separatorPanel = new JPanel();
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.gridwidth = 4;
            gbc_panel.fill = GridBagConstraints.BOTH;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 7;
            separatorPanel.setLayout(new BorderLayout(0, 0));
            
            JSeparator separator = new JSeparator();
            separatorPanel.add(separator, BorderLayout.CENTER);
            summaryPanel.add(separatorPanel, gbc_panel);
            
            JLabel lblKompletteErschwernis = new JLabel(BUNDLE.getString("FernkampfDialog.lblKompletteErschwernis.text")); //$NON-NLS-1$
            lblKompletteErschwernis.setFont(new Font("Lucida Grande", Font.BOLD, 13));
            GridBagConstraints gbc_lblKompletteErschwernis = new GridBagConstraints();
            gbc_lblKompletteErschwernis.anchor = GridBagConstraints.EAST;
            gbc_lblKompletteErschwernis.insets = new Insets(0, 0, 5, 5);
            gbc_lblKompletteErschwernis.gridx = 0;
            gbc_lblKompletteErschwernis.gridy = 8;
            summaryPanel.add(lblKompletteErschwernis, gbc_lblKompletteErschwernis);
            
            lblKompletteErschwernisValue = new JLabel("0");
            lblKompletteErschwernisValue.setFont(new Font("Lucida Grande", Font.BOLD, 13));
            GridBagConstraints gbc_lblKompletteErschwernisValue = new GridBagConstraints();
            gbc_lblKompletteErschwernisValue.anchor = GridBagConstraints.EAST;
            gbc_lblKompletteErschwernisValue.insets = new Insets(0, 0, 5, 5);
            gbc_lblKompletteErschwernisValue.gridx = 2;
            gbc_lblKompletteErschwernisValue.gridy = 8;
            summaryPanel.add(lblKompletteErschwernisValue, gbc_lblKompletteErschwernisValue);
            
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                backButton = new JButton(BUNDLE.getString("FernkampfDialog.backButton.text")); //$NON-NLS-1$
                backButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {                        
                        tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() - 1);
                        updateButtons();
                    }
                });
                backButton.setEnabled(false);
                buttonPane.add(backButton);
                getRootPane().setDefaultButton(backButton);
            }
            {
                forwardButton = new JButton(BUNDLE.getString("FernkampfDialog.forwardButton.text")); //$NON-NLS-1$
                forwardButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton button = (JButton)e.getSource();
                        String text = button.getText();
                        
                        if(text.equalsIgnoreCase("würfeln")) {
                            setRollCommand(BerechnungsHelfer.getFernkampfRollCommand(
                                    getFernwaffenObjekt().getFk(), 
                                    erschwernis));
                            state = Konstanten.DIALOG_OK_STATE;
                            dispose();
                            
                        } else {
                            tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
                            updateButtons();
                            updateSummary();
                        }
                    }
                });
                

                buttonPane.add(forwardButton);
            }
        }
    }
    
    /**
     * @return die Erschwernis durch die Bewegung
     */
    private int berechneErschwerungBewegung() {
        int result                  = 0;
        String bewegung             = null;
        int anzahlGegnerInDistanzH  = 0;
        int anzahlGegnerInDistanzNS = 0;
        
        for(JRadioButton rb : bewegungGroup) {
            if(rb.isSelected()) {
                bewegung = rb.getText();
                break;
            }
        }
        
        if(bewegung.equalsIgnoreCase("kampfgetümmel")) {
            anzahlGegnerInDistanzH  = (Integer)spinnerGegnerInDistanzH.getValue();
            anzahlGegnerInDistanzNS = (Integer)spinnerGegnerInDistanzNS.getValue();
        }
        
        result = BerechnungsHelfer.berechneFernkampfBewegungsModifikator(bewegung, anzahlGegnerInDistanzH, anzahlGegnerInDistanzNS);
        
        return result;
    }
    
    /**
     * @return Die Erschwernis durch die Entfernung
     */
    private int berechneErschwerungEntfernung() {
        int result = 0;
        String entfernung = null;
        for(JRadioButton rb : entfernungGroup) {
            if(rb.isSelected()) {
                entfernung = rb.getText();
                break;
            }
        }
        result = BerechnungsHelfer.berechneFernkampfEntfernungsModifikator(entfernung);
        
        return result;
    }
    
    /**
     * @return Die Erschwernis durch einen Gezielten Schuss
     */
    private int berechneErschwerungGezielterSchuss() {
        int result = 0;
        boolean verwendeGezielterSchuss = chkBoxGezielterSchuss.isSelected();
        boolean humanoid = true;
        String trefferzone = null;
        if(verwendeGezielterSchuss) {
            for(JRadioButton rb : zielHumanoidGroup) {
                if(rb.isSelected()) {
                    trefferzone = rb.getText();
                    humanoid = true;
                }
            }
            
            if(humanoid == true && trefferzone == null) {
                humanoid = false;
            }
            
            if(!humanoid) {
                for(JRadioButton rb : zielTierGroup) {
                    if(rb.isSelected()) {
                        trefferzone = rb.getText();
                        humanoid = false;
                    }
                }
            }
            
            String schuetzentyp = (String)comboSchuetzentyp.getSelectedItem();
            String zielgroesse  =  null;
            for(JRadioButton rb : groessenGroup) {
                if(rb.isSelected()) {
                    zielgroesse = rb.getText();
                    break;
                }
            }
            boolean inBewegung = chkBoxKoerperteilBewegung.isSelected();
            result = BerechnungsHelfer.berechneFernkampfGezielterSchussModifikator(schuetzentyp, humanoid, inBewegung, trefferzone, zielgroesse);
        }
        
        return result;
    }
    
    /**
     * @return Die Erschwerung durch die Groesse des Ziels
     */
    private int berechneErschwerungGroesse() {
        int result = 0;
        String groesse = null;
        for(JRadioButton rb : groessenGroup) {
            if(rb.isSelected()) {
                groesse = rb.getText();
                break;
            }
        }
        
        int modifikator             = (Integer) spinnerGroessenModifikator.getValue();
        boolean halbdeckung         = chkboxHalbdeckung.isSelected();
        boolean dreivierteldeckung     = chkBoxDreivierteldeckung.isSelected();
        
        result = BerechnungsHelfer.berechneFernkampfGroessenModifikator(groesse, modifikator, halbdeckung, dreivierteldeckung);
        
        return result;
    }
    
    /**
     * @return die Erschwernis durch Modifikatoren
     */
    private int berechneErschwerungModifikatoren() {
        int result = 0;
        
        List<String> modifikatoren = new ArrayList<>();
        String schuetzentyp = (String)comboSchuetzentyp.getSelectedItem();
        int ansage = (Integer)spinnerAnsage.getValue();
        int zielen = (Integer)spinnerZielen.getValue();
        
        for(JCheckBox cb : modifikatorGroup) {
            if(cb.isSelected()) {
                modifikatoren.add(cb.getText());
            }
        }
        
        result = BerechnungsHelfer.berechneFernkampfModifikatoren(modifikatoren, schuetzentyp, ansage, zielen);
        
        return result;
    }
    
    /**
     * @return Die Erschwernis durch die Sicht
     */
    private int berechneErschwerungSicht() {
        int result                  = 0;
        String lichtquelle          = "";
        boolean dunst               = false;
        boolean nebel               = false;
        boolean entfernungssinn     = false;
        boolean daemmerungssicht    = false;
        boolean nachtsicht          = false;
        boolean einaeugig           = false;
        boolean farbenblind         = false;
        boolean kurzsichtig         = false;
        boolean nachtblind          = false;
        
        for(JRadioButton rb : sichtGroup) {
            if(rb.isSelected()) {
                lichtquelle= rb.getText();
                break;
            }
        }
        
        for(JRadioButton rb : sichtModGroup) {
            if(rb.isSelected()) {
                String modOption = rb.getText();
                if(modOption.equalsIgnoreCase("dunst")) {
                    dunst = true;
                }
                
                if(modOption.equalsIgnoreCase("nebel")) {
                    nebel = true;
                }
            }
        }
        
        for(JCheckBox cb : vorteilListe) {
            if(cb.isSelected()) {
                switch(cb.getText().toLowerCase()) {
                case "dämmerungssicht"  : daemmerungssicht  = true; break;
                case "nachtsicht"       : nachtsicht        = true; break;
                case "entfernungssinn"  : entfernungssinn   = true; break;
                }
            }
        }
        
        for(JCheckBox cb : nachteilListe) {
            if(cb.isSelected()) {
                switch(cb.getText().toLowerCase()) {
                case "einäugig"     : einaeugig     = true; break;
                case "farbenblind"  : farbenblind   = true; break;
                case "kurzsichtig"  : kurzsichtig   = true; break;
                case "nachtblind"   : nachtblind    = true; break;
                }
            }
        }
        
        String entfernung = null;
        for(JRadioButton rb : entfernungGroup) {
            if(rb.isSelected()) {
                entfernung = rb.getText();
                break;
            }
        }        
        result = BerechnungsHelfer.berechneFernkampfSichtModifikator(
                lichtquelle, 
                dunst, nebel, 
                entfernungssinn, daemmerungssicht, nachtsicht, 
                einaeugig, farbenblind, kurzsichtig, nachtblind, 
                entfernung, 
                getFernwaffenObjekt());
        
        return result;
    }
    
    /**
     * @param buttonGroup the buttonGroup to iterate over
     * @param enabled whether to enable the buttons in the group
     */
    protected void enableButtonsInGroup(List<JRadioButton> buttonGroup, boolean enabled) {
        for(JRadioButton rb : buttonGroup) {
            rb.setEnabled(enabled);
        }
    }

    /**
     * @param selected whether a Gezielter Schuss shall be made
     */
    protected void enableGezielterSchuss(boolean selected) {
        enableButtonsInGroup(zielHumanoidGroup, selected);
        enableButtonsInGroup(zielTierGroup, selected);
        chkBoxKoerperteilBewegung.setEnabled(selected);
        lblZielHumanoid.setEnabled(selected);
        lblZielTier.setEnabled(selected);
    }

    /**
     * 
     */
    protected void enableModifikatorOptions() {
        boolean schuss = true;
        switch(getFernwaffenObjekt().getTyp()) {
        case Ar: schuss = true;  break;
        case Bl: schuss = true;  break;
        case Bo: schuss = true;  break;
        case Sl: schuss = true;  break;
        case Di: schuss = false; break;
        case Wb: schuss = false; break;
        case Wm: schuss = false; break;
        case Ws: schuss = false; break;
        }
        
        if(schuss) {
            for(JCheckBox cb : modifikatorGroup) {
                if(cb.getText().toLowerCase().contains("wurf")) {
                    cb.setEnabled(false);
                }
            }
        } else {
            for(JCheckBox cb : modifikatorGroup) {
                if(cb.getText().toLowerCase().contains("schuss")) {
                    cb.setEnabled(false);
                }
            }
        }
    }
    
    /**
     * @return the FernwaffenObjekt
     */
    public FernwaffenObjekt getFernwaffenObjekt() {
        return fernwaffenObjekt;
    }

    /**
     * @return the rollCommand
     */
    public String getRollCommand() {
        return rollCommand;
    }
    
    /**
     * 
     */
    private void setFernwaffenDistanzen() {
        lblWaffeDistanzSehrNah.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(0)));
        lblWaffeDistanzNah.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(1)));
        lblWaffeDistanzMittel.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(2)));
        lblWaffeDistanzWeit.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(3)));
        lblWaffeDistanzSehrWeit.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(4)));

        repaint();
    }

    /**
     * @param fernwaffenObjekt the FernwaffenObjekt to set
     */
    public void setFernwaffenObjekt(FernwaffenObjekt fernwaffenObjekt) {
        this.fernwaffenObjekt = fernwaffenObjekt;
    }

    /**
     * @param rollCommand the rollCommand to set
     */
    public void setRollCommand(String rollCommand) {
        this.rollCommand = rollCommand;
    }

    /**
     * @return the state of the dialog
     */
    public int showDialog() {
        enableModifikatorOptions();
        setVisible(true);
        return state;
    }

    /**
     * updates buttons according to the current tab
     */
    protected void updateButtons() {
        int currentIndex = tabbedPane.getSelectedIndex();
        if( currentIndex == 0) {
            backButton.setEnabled(false);
            forwardButton.setText("weiter");
        }
        if(currentIndex > 0) {
            backButton.setEnabled(true);
            forwardButton.setText("weiter");
        }
        
        if(currentIndex == 6) {
            forwardButton.setText("würfeln");
        }
        
    }

    /**
     * updates the summary of the Fernkampf
     */
    protected void updateSummary() {
        int index               = tabbedPane.getSelectedIndex();
        int modGroesse          = 0;
        int modEntfernung       = 0;
        int modBewegung         = 0;
        int modSicht            = 0;
        int modModifikatoren    = 0;
        int modGezielt          = 0;
        int resultat            = 0;
        
        if(index == 6) {
            modGroesse       = berechneErschwerungGroesse();
            modEntfernung    = berechneErschwerungEntfernung();
            modBewegung      = berechneErschwerungBewegung();
            modSicht         = berechneErschwerungSicht();
            modModifikatoren = berechneErschwerungModifikatoren();
            modGezielt       = berechneErschwerungGezielterSchuss();
            
            resultat = modGroesse + modEntfernung + modBewegung + modSicht + modModifikatoren + modGezielt;
            erschwernis = resultat;
        }
        
        lblErschwernisZielgroesseValue.setText(String.valueOf(modGroesse));
        lblErschwernisEntfernungValue.setText(String.valueOf(modEntfernung));
        lblErschwernisBewegungValue.setText(String.valueOf(modBewegung));
        lblErschwernisSichtValue.setText(String.valueOf(modSicht));
        lblErschwernisModifikatorenValue.setText(String.valueOf(modModifikatoren));
        lblErschwernisGezielterSchussValue.setText(String.valueOf(modGezielt));
        
        lblKompletteErschwernisValue.setText(String.valueOf(resultat));
    }
}
