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

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.object.FernwaffenObjekt;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.DefaultComboBoxModel;

public class FernkampfDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5503871340629603661L;
	private final JPanel contentPanel 					= new JPanel();
	private List<JRadioButton> groessenGroup 			= new ArrayList<>();
	private List<JRadioButton>entfernungGroup 			= new ArrayList<>();
	private List<JRadioButton>sichtGroup 				= new ArrayList<>();
	private List<JRadioButton>sichtModGroup 			= new ArrayList<>();
	private List<JRadioButton>zielHumanoidGroup 		= new ArrayList<>();
	private List<JRadioButton>zielTierGroup 			= new ArrayList<>();
	private List<JRadioButton>bewegungGroup 			= new ArrayList<>();
	private List<JCheckBox> vorteilListe 				= new ArrayList<>();
	private List<JCheckBox> nachteilListe 				= new ArrayList<>();
	private List<JCheckBox> modifikatorGroup 			= new ArrayList<>();

	public static final int OK_STATE					= 0;
	public static final int CANCEL_STATE 				= 1;
	private int state 									= CANCEL_STATE;
	private String rollCommand 							= null;
	private JLabel lblWaffeDistanzSehrNah 				= new JLabel();
	private JLabel lblWaffeDistanzNah 					= new JLabel();
	private JLabel lblWaffeDistanzMittel				= new JLabel();
	private JLabel lblWaffeDistanzWeit 					= new JLabel();
	private JLabel lblWaffeDistanzSehrWeit 				= new JLabel();

	private FernwaffenObjekt fernwaffenObjekt 			= null;
	
	private JTabbedPane tabbedPane 						= new JTabbedPane();
	private JButton forwardButton 						= new JButton();
	private JButton backButton 							= new JButton();
	private JCheckBox chkBoxKoerperteilBewegung			= new JCheckBox();
	private JLabel lblZielTier 							= new JLabel();
	private JLabel lblZielHumanoid 						= new JLabel();
	private JSpinner spinnerGegnerInDistanzH 			= new JSpinner();
	private JSpinner spinnerGegnerInDistanzNS 			= new JSpinner();
	private JCheckBox chkBoxDreivierteldeckung 			= new JCheckBox();
	private JCheckBox chkboxHalbdeckung 				= new JCheckBox();
	private JSpinner spinnerAnsage 						= new JSpinner();
	private JSpinner spinnerZielen 						= new JSpinner();
	private JSpinner spinnerGroessenModifikator 		= new JSpinner();
	private JCheckBox chkBoxGezielterSchuss 			= new JCheckBox();
	private JComboBox<String> comboSchuetzentyp 		= new JComboBox<>();
	private JLabel lblErschwernisKomplettWert 			= new JLabel();
	private JLabel lblErschwernisGezielterSchussValue 	= new JLabel();
	private JLabel lblErschwernisModifikatorenValue 	= new JLabel();
	private JLabel lblErschwernisSichtValue 			= new JLabel();
	private JLabel lblErschwernisBewegungValue 			= new JLabel();
	private JLabel lblErschwernisEntfernungValue 		= new JLabel();
	private JLabel lblErschwernisZielgroesseValue 		= new JLabel();
	
	private int erschwernis = 0;

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
						JRadioButton rdbWinzig = new JRadioButton("winzig");
						groessenButtonGroup.add(rdbWinzig);
						groessenSelectionPanel.add(rdbWinzig);

						JRadioButton rdbSehrKlein = new JRadioButton("sehr klein");
						groessenButtonGroup.add(rdbSehrKlein);
						groessenSelectionPanel.add(rdbSehrKlein);

						JRadioButton rdbKlein = new JRadioButton("klein");
						groessenButtonGroup.add(rdbKlein);
						groessenSelectionPanel.add(rdbKlein);

						JRadioButton rdbMittel = new JRadioButton("mittel");
						groessenButtonGroup.add(rdbMittel);
						rdbMittel.setSelected(true);
						groessenSelectionPanel.add(rdbMittel);

						JRadioButton rdbGross = new JRadioButton("groß");
						groessenButtonGroup.add(rdbGross);
						groessenSelectionPanel.add(rdbGross);

						JRadioButton rdbSehrGross = new JRadioButton("sehr groß");
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
					JLabel lblNewLabel = new JLabel("Modifikator");
					GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
					gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
					gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
					gbc_lblNewLabel.gridx = 2;
					gbc_lblNewLabel.gridy = 0;
					zielgroessenPanel.add(lblNewLabel, gbc_lblNewLabel);
				}
				{
					JLabel lblNewLabel_1 = new JLabel("Halbdeckung");
					GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
					gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
					gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
					gbc_lblNewLabel_1.gridx = 2;
					gbc_lblNewLabel_1.gridy = 1;
					zielgroessenPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
				}
				{
					chkboxHalbdeckung = new JCheckBox("");
					GridBagConstraints gbc_chkboxHalbdeckung = new GridBagConstraints();
					gbc_chkboxHalbdeckung.anchor = GridBagConstraints.EAST;
					gbc_chkboxHalbdeckung.insets = new Insets(0, 0, 5, 5);
					gbc_chkboxHalbdeckung.gridx = 1;
					gbc_chkboxHalbdeckung.gridy = 1;
					zielgroessenPanel.add(chkboxHalbdeckung, gbc_chkboxHalbdeckung);
				}
				{
					JLabel lblNewLabel_2 = new JLabel("Dreivierteldeckung");
					GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
					gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
					gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
					gbc_lblNewLabel_2.gridx = 2;
					gbc_lblNewLabel_2.gridy = 2;
					zielgroessenPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
				}
				{
					chkBoxDreivierteldeckung = new JCheckBox("");
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

				JLabel lblNewLabel_9 = new JLabel("Distanz zum Gegner");
				GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
				gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_9.gridx = 0;
				gbc_lblNewLabel_9.gridy = 0;
				entfernungPanel.add(lblNewLabel_9, gbc_lblNewLabel_9);

				JLabel lblNewLabel_10 = new JLabel("Reichweite der Waffe (Meter)");
				GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
				gbc_lblNewLabel_10.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLabel_10.gridx = 1;
				gbc_lblNewLabel_10.gridy = 0;
				entfernungPanel.add(lblNewLabel_10, gbc_lblNewLabel_10);

				ButtonGroup entfernungButtonGroup = new ButtonGroup();
				JRadioButton rdbSehrNah = new JRadioButton("sehr nah");
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

				JRadioButton rdbNah = new JRadioButton("nah");
				entfernungButtonGroup.add(rdbNah);
				GridBagConstraints gbc_rdbNah = new GridBagConstraints();
				gbc_rdbNah.anchor = GridBagConstraints.WEST;
				gbc_rdbNah.insets = new Insets(0, 0, 5, 5);
				gbc_rdbNah.gridx = 0;
				gbc_rdbNah.gridy = 2;
				entfernungPanel.add(rdbNah, gbc_rdbNah);
				entfernungGroup.add(rdbNah);

				lblWaffeDistanzNah = new JLabel("0");
				GridBagConstraints gbc_lblWaffeDistanzNah = new GridBagConstraints();
				gbc_lblWaffeDistanzNah.anchor = GridBagConstraints.WEST;
				gbc_lblWaffeDistanzNah.insets = new Insets(0, 0, 5, 0);
				gbc_lblWaffeDistanzNah.gridx = 1;
				gbc_lblWaffeDistanzNah.gridy = 2;
				entfernungPanel.add(lblWaffeDistanzNah, gbc_lblWaffeDistanzNah);

				JRadioButton rdbMittel = new JRadioButton("mittel");
				entfernungButtonGroup.add(rdbMittel);
				rdbMittel.setSelected(true);
				GridBagConstraints gbc_rdbMittel = new GridBagConstraints();
				gbc_rdbMittel.anchor = GridBagConstraints.WEST;
				gbc_rdbMittel.insets = new Insets(0, 0, 5, 5);
				gbc_rdbMittel.gridx = 0;
				gbc_rdbMittel.gridy = 3;
				entfernungPanel.add(rdbMittel, gbc_rdbMittel);
				entfernungGroup.add(rdbMittel);

				lblWaffeDistanzMittel = new JLabel("0");
				GridBagConstraints gbc_lblWaffeDistanzMittel = new GridBagConstraints();
				gbc_lblWaffeDistanzMittel.anchor = GridBagConstraints.WEST;
				gbc_lblWaffeDistanzMittel.insets = new Insets(0, 0, 5, 0);
				gbc_lblWaffeDistanzMittel.gridx = 1;
				gbc_lblWaffeDistanzMittel.gridy = 3;
				entfernungPanel.add(lblWaffeDistanzMittel, gbc_lblWaffeDistanzMittel);

				JRadioButton rdbWeit = new JRadioButton("weit");
				entfernungButtonGroup.add(rdbWeit);
				GridBagConstraints gbc_rdbWeit = new GridBagConstraints();
				gbc_rdbWeit.anchor = GridBagConstraints.WEST;
				gbc_rdbWeit.insets = new Insets(0, 0, 5, 5);
				gbc_rdbWeit.gridx = 0;
				gbc_rdbWeit.gridy = 4;
				entfernungPanel.add(rdbWeit, gbc_rdbWeit);
				entfernungGroup.add(rdbWeit);

				lblWaffeDistanzWeit = new JLabel("0");
				GridBagConstraints gbc_lblWaffeDistanzWeit = new GridBagConstraints();
				gbc_lblWaffeDistanzWeit.anchor = GridBagConstraints.WEST;
				gbc_lblWaffeDistanzWeit.insets = new Insets(0, 0, 5, 0);
				gbc_lblWaffeDistanzWeit.gridx = 1;
				gbc_lblWaffeDistanzWeit.gridy = 4;
				entfernungPanel.add(lblWaffeDistanzWeit, gbc_lblWaffeDistanzWeit);

				JRadioButton rdbSehrWeit = new JRadioButton("sehr weit");
				entfernungButtonGroup.add(rdbSehrWeit);
				GridBagConstraints gbc_rdbSehrWeit = new GridBagConstraints();
				gbc_rdbSehrWeit.insets = new Insets(0, 0, 0, 5);
				gbc_rdbSehrWeit.anchor = GridBagConstraints.WEST;
				gbc_rdbSehrWeit.gridx = 0;
				gbc_rdbSehrWeit.gridy = 5;
				entfernungPanel.add(rdbSehrWeit, gbc_rdbSehrWeit);
				entfernungGroup.add(rdbSehrWeit);

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
			JRadioButton rdbUnbeweglich = new JRadioButton("unbewegliches / fest montiertes Ziel");
			bewegungButtonGroup.add(rdbUnbeweglich);
			GridBagConstraints gbc_rdbtnNewRadioButton_15 = new GridBagConstraints();
			gbc_rdbtnNewRadioButton_15.gridwidth = 2;
			gbc_rdbtnNewRadioButton_15.anchor = GridBagConstraints.WEST;
			gbc_rdbtnNewRadioButton_15.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnNewRadioButton_15.gridx = 0;
			gbc_rdbtnNewRadioButton_15.gridy = 0;
			bewegungPanel.add(rdbUnbeweglich, gbc_rdbtnNewRadioButton_15);

			JRadioButton rdbStillstehend = new JRadioButton("Stillstehendes Ziel");
			bewegungButtonGroup.add(rdbStillstehend);
			rdbStillstehend.setSelected(true);
			GridBagConstraints gbc_rdbtnNewRadioButton_15_1 = new GridBagConstraints();
			gbc_rdbtnNewRadioButton_15_1.gridwidth = 2;
			gbc_rdbtnNewRadioButton_15_1.anchor = GridBagConstraints.WEST;
			gbc_rdbtnNewRadioButton_15_1.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnNewRadioButton_15_1.gridx = 0;
			gbc_rdbtnNewRadioButton_15_1.gridy = 1;
			bewegungPanel.add(rdbStillstehend, gbc_rdbtnNewRadioButton_15_1);

			JRadioButton rdbLeichteBewegung = new JRadioButton("leichte Bewegung des Ziels");
			bewegungButtonGroup.add(rdbLeichteBewegung);
			GridBagConstraints gbc_rdbtnNewRadioButton_15_1_1 = new GridBagConstraints();
			gbc_rdbtnNewRadioButton_15_1_1.gridwidth = 2;
			gbc_rdbtnNewRadioButton_15_1_1.anchor = GridBagConstraints.WEST;
			gbc_rdbtnNewRadioButton_15_1_1.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnNewRadioButton_15_1_1.gridx = 0;
			gbc_rdbtnNewRadioButton_15_1_1.gridy = 2;
			bewegungPanel.add(rdbLeichteBewegung, gbc_rdbtnNewRadioButton_15_1_1);

			JRadioButton rdbSchnelleBewegung = new JRadioButton("schnelle Bewegung des Ziels");
			bewegungButtonGroup.add(rdbSchnelleBewegung);
			GridBagConstraints gbc_rdbtnNewRadioButton_15_1_2 = new GridBagConstraints();
			gbc_rdbtnNewRadioButton_15_1_2.gridwidth = 2;
			gbc_rdbtnNewRadioButton_15_1_2.anchor = GridBagConstraints.WEST;
			gbc_rdbtnNewRadioButton_15_1_2.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnNewRadioButton_15_1_2.gridx = 0;
			gbc_rdbtnNewRadioButton_15_1_2.gridy = 3;
			bewegungPanel.add(rdbSchnelleBewegung, gbc_rdbtnNewRadioButton_15_1_2);

			JRadioButton rdbSehrSchnelleBewegung = new JRadioButton("sehr schnelle Bewegung / Ausweichbewegungen");
			bewegungButtonGroup.add(rdbSehrSchnelleBewegung);
			GridBagConstraints gbc_rdbtnNewRadioButton_15_1_3 = new GridBagConstraints();
			gbc_rdbtnNewRadioButton_15_1_3.gridwidth = 2;
			gbc_rdbtnNewRadioButton_15_1_3.anchor = GridBagConstraints.WEST;
			gbc_rdbtnNewRadioButton_15_1_3.insets = new Insets(0, 0, 5, 0);
			gbc_rdbtnNewRadioButton_15_1_3.gridx = 0;
			gbc_rdbtnNewRadioButton_15_1_3.gridy = 4;
			bewegungPanel.add(rdbSehrSchnelleBewegung, gbc_rdbtnNewRadioButton_15_1_3);

			JRadioButton rdbKampfgetuemmel = new JRadioButton("Kampfgetümmel");
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

			JLabel lblNewLabel_7 = new JLabel("Gegner in Distanz H:");
			GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
			gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_7.gridx = 0;
			gbc_lblNewLabel_7.gridy = 6;
			bewegungPanel.add(lblNewLabel_7, gbc_lblNewLabel_7);

			spinnerGegnerInDistanzH = new JSpinner();
			GridBagConstraints gbc_spinner = new GridBagConstraints();
			gbc_spinner.anchor = GridBagConstraints.WEST;
			gbc_spinner.insets = new Insets(0, 0, 5, 0);
			gbc_spinner.gridx = 1;
			gbc_spinner.gridy = 6;
			bewegungPanel.add(spinnerGegnerInDistanzH, gbc_spinner);

			JLabel lblNewLabel_8 = new JLabel("Gegner in Distanz NS:");
			GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
			gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_8.insets = new Insets(0, 0, 0, 5);
			gbc_lblNewLabel_8.gridx = 0;
			gbc_lblNewLabel_8.gridy = 7;
			bewegungPanel.add(lblNewLabel_8, gbc_lblNewLabel_8);

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

				JLabel lblNewLabel_11 = new JLabel("Lichtquelle");
				GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
				gbc_lblNewLabel_11.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_11.gridx = 0;
				gbc_lblNewLabel_11.gridy = 0;
				sichtPanel.add(lblNewLabel_11, gbc_lblNewLabel_11);

				JLabel lblNewLabel_12 = new JLabel("Modifikatoren");
				GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
				gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_12.gridx = 2;
				gbc_lblNewLabel_12.gridy = 0;
				sichtPanel.add(lblNewLabel_12, gbc_lblNewLabel_12);

				JLabel lblNewLabel_13 = new JLabel("Vorteile");
				GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
				gbc_lblNewLabel_13.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLabel_13.gridx = 4;
				gbc_lblNewLabel_13.gridy = 0;
				sichtPanel.add(lblNewLabel_13, gbc_lblNewLabel_13);
				
				ButtonGroup lichtButtonGroup = new ButtonGroup();
				JRadioButton rdbNormaleSicht = new JRadioButton("Tageslicht");
				lichtButtonGroup.add(rdbNormaleSicht);
				rdbNormaleSicht.setSelected(true);
				GridBagConstraints gbc_rdbNormaleSicht = new GridBagConstraints();
				gbc_rdbNormaleSicht.anchor = GridBagConstraints.WEST;
				gbc_rdbNormaleSicht.insets = new Insets(0, 0, 5, 5);
				gbc_rdbNormaleSicht.gridx = 0;
				gbc_rdbNormaleSicht.gridy = 1;
				sichtPanel.add(rdbNormaleSicht, gbc_rdbNormaleSicht);
				sichtGroup.add(rdbNormaleSicht);

				JCheckBox chkboxVorteilEntfernungssinn = new JCheckBox("Entfernungssinn");
				vorteilListe.add(chkboxVorteilEntfernungssinn);
				GridBagConstraints gbc_chckbxNewCheckBox_8 = new GridBagConstraints();
				gbc_chckbxNewCheckBox_8.anchor = GridBagConstraints.WEST;
				gbc_chckbxNewCheckBox_8.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxNewCheckBox_8.gridx = 4;
				gbc_chckbxNewCheckBox_8.gridy = 1;
				sichtPanel.add(chkboxVorteilEntfernungssinn, gbc_chckbxNewCheckBox_8);

				JRadioButton rdbDaemmerung = new JRadioButton("Dämmerung");
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
				JRadioButton rdbDunst = new JRadioButton("Dunst");
				sichtModButtonGroup.add(rdbDunst);
				GridBagConstraints gbc_rdbDunst = new GridBagConstraints();
				gbc_rdbDunst.anchor = GridBagConstraints.WEST;
				gbc_rdbDunst.insets = new Insets(0, 0, 5, 5);
				gbc_rdbDunst.gridx = 2;
				gbc_rdbDunst.gridy = 1;
				sichtPanel.add(rdbDunst, gbc_rdbDunst);
				sichtModGroup.add(rdbDunst);
				
				JRadioButton rdbNebel = new JRadioButton("Nebel");
				sichtModButtonGroup.add(rdbNebel);
				GridBagConstraints gbc_rdbNebel = new GridBagConstraints();
				gbc_rdbNebel.anchor = GridBagConstraints.WEST;
				gbc_rdbNebel.insets = new Insets(0, 0, 5, 5);
				gbc_rdbNebel.gridx = 2;
				gbc_rdbNebel.gridy = 2;
				sichtPanel.add(rdbNebel, gbc_rdbNebel);
				sichtModGroup.add(rdbNebel);

				JCheckBox chkboxDaemmerungssicht = new JCheckBox("Dämmerungssicht");
				vorteilListe.add(chkboxDaemmerungssicht);
				GridBagConstraints gbc_chckbxNewCheckBox_9 = new GridBagConstraints();
				gbc_chckbxNewCheckBox_9.anchor = GridBagConstraints.WEST;
				gbc_chckbxNewCheckBox_9.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxNewCheckBox_9.gridx = 4;
				gbc_chckbxNewCheckBox_9.gridy = 2;
				sichtPanel.add(chkboxDaemmerungssicht, gbc_chckbxNewCheckBox_9);

				JRadioButton rdbMondlicht = new JRadioButton("Mondlicht");
				lichtButtonGroup.add(rdbMondlicht);
				GridBagConstraints gbc_rdbMondlicht = new GridBagConstraints();
				gbc_rdbMondlicht.anchor = GridBagConstraints.WEST;
				gbc_rdbMondlicht.insets = new Insets(0, 0, 5, 5);
				gbc_rdbMondlicht.gridx = 0;
				gbc_rdbMondlicht.gridy = 3;
				sichtPanel.add(rdbMondlicht, gbc_rdbMondlicht);
				sichtGroup.add(rdbMondlicht);

				JCheckBox chkboxNachtsicht = new JCheckBox("Nachtsicht");
				vorteilListe.add(chkboxNachtsicht);
				GridBagConstraints gbc_chckbxNewCheckBox_10 = new GridBagConstraints();
				gbc_chckbxNewCheckBox_10.anchor = GridBagConstraints.WEST;
				gbc_chckbxNewCheckBox_10.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxNewCheckBox_10.gridx = 4;
				gbc_chckbxNewCheckBox_10.gridy = 3;
				sichtPanel.add(chkboxNachtsicht, gbc_chckbxNewCheckBox_10);

				JRadioButton rdbSternenlicht = new JRadioButton("Sternenlicht");
				lichtButtonGroup.add(rdbSternenlicht);
				GridBagConstraints gbc_rdbSternenlicht = new GridBagConstraints();
				gbc_rdbSternenlicht.anchor = GridBagConstraints.WEST;
				gbc_rdbSternenlicht.insets = new Insets(0, 0, 5, 5);
				gbc_rdbSternenlicht.gridx = 0;
				gbc_rdbSternenlicht.gridy = 4;
				sichtPanel.add(rdbSternenlicht, gbc_rdbSternenlicht);
				sichtGroup.add(rdbSternenlicht);

				JLabel lblNewLabel_14 = new JLabel("Nachteile");
				GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
				gbc_lblNewLabel_14.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLabel_14.gridx = 4;
				gbc_lblNewLabel_14.gridy = 4;
				sichtPanel.add(lblNewLabel_14, gbc_lblNewLabel_14);

				JRadioButton rdbFinsternis = new JRadioButton("Finsternis");
				lichtButtonGroup.add(rdbFinsternis);
				GridBagConstraints gbc_rdbFinsternis = new GridBagConstraints();
				gbc_rdbFinsternis.anchor = GridBagConstraints.WEST;
				gbc_rdbFinsternis.insets = new Insets(0, 0, 5, 5);
				gbc_rdbFinsternis.gridx = 0;
				gbc_rdbFinsternis.gridy = 5;
				sichtPanel.add(rdbFinsternis, gbc_rdbFinsternis);
				sichtGroup.add(rdbFinsternis);

				JCheckBox chkboxEinaeugig = new JCheckBox("Einäugig");
				nachteilListe.add(chkboxEinaeugig);
				GridBagConstraints gbc_chckbxNewCheckBox_12 = new GridBagConstraints();
				gbc_chckbxNewCheckBox_12.anchor = GridBagConstraints.WEST;
				gbc_chckbxNewCheckBox_12.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxNewCheckBox_12.gridx = 4;
				gbc_chckbxNewCheckBox_12.gridy = 5;
				sichtPanel.add(chkboxEinaeugig, gbc_chckbxNewCheckBox_12);

				JRadioButton rdbUnsichtbar = new JRadioButton("Unsichtbares Ziel");
				lichtButtonGroup.add(rdbUnsichtbar);
				GridBagConstraints gbc_rdbUnsichtbar = new GridBagConstraints();
				gbc_rdbUnsichtbar.insets = new Insets(0, 0, 5, 5);
				gbc_rdbUnsichtbar.anchor = GridBagConstraints.WEST;
				gbc_rdbUnsichtbar.gridx = 0;
				gbc_rdbUnsichtbar.gridy = 6;
				sichtPanel.add(rdbUnsichtbar, gbc_rdbUnsichtbar);
				sichtGroup.add(rdbUnsichtbar);

				JCheckBox chkboxFarbenblind = new JCheckBox("Farbenblind");
				nachteilListe.add(chkboxFarbenblind);
				GridBagConstraints gbc_chckbxNewCheckBox_13 = new GridBagConstraints();
				gbc_chckbxNewCheckBox_13.anchor = GridBagConstraints.WEST;
				gbc_chckbxNewCheckBox_13.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxNewCheckBox_13.gridx = 4;
				gbc_chckbxNewCheckBox_13.gridy = 6;
				sichtPanel.add(chkboxFarbenblind, gbc_chckbxNewCheckBox_13);

				JCheckBox chkboxKurzsichtig = new JCheckBox("Kurzsichtig");
				nachteilListe.add(chkboxKurzsichtig);
				GridBagConstraints gbc_chckbxNewCheckBox_14 = new GridBagConstraints();
				gbc_chckbxNewCheckBox_14.insets = new Insets(0, 0, 5, 0);
				gbc_chckbxNewCheckBox_14.anchor = GridBagConstraints.WEST;
				gbc_chckbxNewCheckBox_14.gridx = 4;
				gbc_chckbxNewCheckBox_14.gridy = 7;
				sichtPanel.add(chkboxKurzsichtig, gbc_chckbxNewCheckBox_14);

				JCheckBox chkboxNachtblind = new JCheckBox("Nachtblind");
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

				JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Steilschuss nach unten");
				modifikatorGroup.add(chckbxNewCheckBox_1);
				modPanel_1.add(chckbxNewCheckBox_1);

				JCheckBox chckbxNewCheckBox_1_1 = new JCheckBox("Steilwurf nach unten");
				modifikatorGroup.add(chckbxNewCheckBox_1_1);
				modPanel_1.add(chckbxNewCheckBox_1_1);

				JCheckBox chckbxNewCheckBox_1_2 = new JCheckBox("Steilschuss nach oben");
				modifikatorGroup.add(chckbxNewCheckBox_1_2);
				modPanel_1.add(chckbxNewCheckBox_1_2);

				JCheckBox chckbxNewCheckBox_1_3 = new JCheckBox("Steilwurf nach oben");
				modifikatorGroup.add(chckbxNewCheckBox_1_3);
				modPanel_1.add(chckbxNewCheckBox_1_3);

				JCheckBox chckbxNewCheckBox_1_4 = new JCheckBox("böiger Seitenwind");
				modifikatorGroup.add(chckbxNewCheckBox_1_4);
				modPanel_1.add(chckbxNewCheckBox_1_4);

				JCheckBox chckbxNewCheckBox_1_5 = new JCheckBox("starker böiger Seitenwind");
				modifikatorGroup.add(chckbxNewCheckBox_1_5);
				modPanel_1.add(chckbxNewCheckBox_1_5);

				JCheckBox chckbxNewCheckBox_1_6 = new JCheckBox("Schnellschuss");
				modifikatorGroup.add(chckbxNewCheckBox_1_6);
				modPanel_1.add(chckbxNewCheckBox_1_6);

				JCheckBox chckbxNewCheckBox_5 = new JCheckBox("Unter Wasser");
				modifikatorGroup.add(chckbxNewCheckBox_5);
				modPanel_1.add(chckbxNewCheckBox_5);

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

				JLabel lblNewLabel_3 = new JLabel("Ansage");
				GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
				gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
				gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_3.gridx = 1;
				gbc_lblNewLabel_3.gridy = 0;
				panel_1.add(lblNewLabel_3, gbc_lblNewLabel_3);
				
				SpinnerNumberModel spinnerModelZielen = new SpinnerNumberModel(0, 0, 4, 1); 
				spinnerZielen = new JSpinner(spinnerModelZielen);
				
				GridBagConstraints gbc_spinnerZielen = new GridBagConstraints();
				gbc_spinnerZielen.anchor = GridBagConstraints.EAST;
				gbc_spinnerZielen.insets = new Insets(0, 0, 0, 5);
				gbc_spinnerZielen.gridx = 0;
				gbc_spinnerZielen.gridy = 1;
				panel_1.add(spinnerZielen, gbc_spinnerZielen);

				JLabel lblNewLabel_4 = new JLabel("Zielen");
				GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
				gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_4.gridx = 1;
				gbc_lblNewLabel_4.gridy = 1;
				panel_1.add(lblNewLabel_4, gbc_lblNewLabel_4);

				JPanel modPanel_2 = new JPanel();
				GridBagConstraints gbc_modPanel_2 = new GridBagConstraints();
				gbc_modPanel_2.anchor = GridBagConstraints.NORTHEAST;
				gbc_modPanel_2.gridx = 1;
				gbc_modPanel_2.gridy = 1;
				modifikatorPanel.add(modPanel_2, gbc_modPanel_2);
				modPanel_2.setLayout(new BoxLayout(modPanel_2, BoxLayout.Y_AXIS));

				JCheckBox chckbxNewCheckBox_2 = new JCheckBox("zweiter Schuss pro KR");
				modifikatorGroup.add(chckbxNewCheckBox_2);
				modPanel_2.add(chckbxNewCheckBox_2);

				JCheckBox chckbxNewCheckBox_2_1 = new JCheckBox("zweiter Wurf pro KR");
				modifikatorGroup.add(chckbxNewCheckBox_2_1);
				modPanel_2.add(chckbxNewCheckBox_2_1);

				JCheckBox chckbxNewCheckBox_2_2 = new JCheckBox("Schuss von stehendem Tier");
				modifikatorGroup.add(chckbxNewCheckBox_2_2);
				modPanel_2.add(chckbxNewCheckBox_2_2);

				JCheckBox chckbxNewCheckBox_2_3 = new JCheckBox("Wurf von stehenden Tier");
				modifikatorGroup.add(chckbxNewCheckBox_2_3);
				modPanel_2.add(chckbxNewCheckBox_2_3);

				JCheckBox chckbxNewCheckBox_2_4 = new JCheckBox("Schuss vom Reittier im Schritt");
				modifikatorGroup.add(chckbxNewCheckBox_2_4);
				modPanel_2.add(chckbxNewCheckBox_2_4);

				JCheckBox chckbxNewCheckBox_2_5 = new JCheckBox("Wurf vom Reittier im Schritt");
				modifikatorGroup.add(chckbxNewCheckBox_2_5);
				modPanel_2.add(chckbxNewCheckBox_2_5);

				JCheckBox chckbxNewCheckBox_2_6 = new JCheckBox("Schuss im Galopp");
				modifikatorGroup.add(chckbxNewCheckBox_2_6);
				modPanel_2.add(chckbxNewCheckBox_2_6);

				JCheckBox chckbxNewCheckBox_2_7 = new JCheckBox("Wurf im Galopp");
				modifikatorGroup.add(chckbxNewCheckBox_2_7);
				modPanel_2.add(chckbxNewCheckBox_2_7);

				JCheckBox chckbxNewCheckBox_3 = new JCheckBox("Schuss ohne Sattel");
				modifikatorGroup.add(chckbxNewCheckBox_3);
				modPanel_2.add(chckbxNewCheckBox_3);

				JCheckBox chckbxNewCheckBox_4 = new JCheckBox("Wurf ohne Sattel");
				modifikatorGroup.add(chckbxNewCheckBox_4);
				modPanel_2.add(chckbxNewCheckBox_4);
				
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

				chkBoxGezielterSchuss = new JCheckBox("Gezielten Schuss verwenden");
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

				lblZielHumanoid = new JLabel("Humanoid");
				lblZielHumanoid.setVerticalAlignment(SwingConstants.TOP);
				GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
				gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
				gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel_5.gridx = 0;
				gbc_lblNewLabel_5.gridy = 0;
				zielPanel_1.add(lblZielHumanoid, gbc_lblNewLabel_5);

				JRadioButton radioZielHumanoidKopf = new JRadioButton("Kopf");
				gezielterSchussGroup.add(radioZielHumanoidKopf);
				radioZielHumanoidKopf.setEnabled(false);
				GridBagConstraints gbc_rdbtnNewRadioButton = new GridBagConstraints();
				gbc_rdbtnNewRadioButton.anchor = GridBagConstraints.WEST;
				gbc_rdbtnNewRadioButton.insets = new Insets(0, 0, 5, 5);
				gbc_rdbtnNewRadioButton.gridx = 0;
				gbc_rdbtnNewRadioButton.gridy = 1;
				zielPanel_1.add(radioZielHumanoidKopf, gbc_rdbtnNewRadioButton);
				zielHumanoidGroup.add(radioZielHumanoidKopf);

				JRadioButton radioZielHumanoidHand = new JRadioButton("Hand");
				gezielterSchussGroup.add(radioZielHumanoidHand);
				radioZielHumanoidHand.setEnabled(false);
				GridBagConstraints gbc_rdbtnNewRadioButton_5 = new GridBagConstraints();
				gbc_rdbtnNewRadioButton_5.anchor = GridBagConstraints.WEST;
				gbc_rdbtnNewRadioButton_5.insets = new Insets(0, 0, 5, 0);
				gbc_rdbtnNewRadioButton_5.gridx = 2;
				gbc_rdbtnNewRadioButton_5.gridy = 1;
				zielPanel_1.add(radioZielHumanoidHand, gbc_rdbtnNewRadioButton_5);
				zielHumanoidGroup.add(radioZielHumanoidHand);

				JRadioButton radioZielHumanoidBrust = new JRadioButton("Brust");
				gezielterSchussGroup.add(radioZielHumanoidBrust);
				radioZielHumanoidBrust.setEnabled(false);
				GridBagConstraints gbc_rdbtnNewRadioButton_1 = new GridBagConstraints();
				gbc_rdbtnNewRadioButton_1.anchor = GridBagConstraints.WEST;
				gbc_rdbtnNewRadioButton_1.insets = new Insets(0, 0, 5, 5);
				gbc_rdbtnNewRadioButton_1.gridx = 0;
				gbc_rdbtnNewRadioButton_1.gridy = 2;
				zielPanel_1.add(radioZielHumanoidBrust, gbc_rdbtnNewRadioButton_1);
				zielHumanoidGroup.add(radioZielHumanoidBrust);

				JRadioButton radioZielHumanoidFuss = new JRadioButton("Fuß");
				gezielterSchussGroup.add(radioZielHumanoidFuss);
				radioZielHumanoidFuss.setEnabled(false);
				GridBagConstraints gbc_radioZielHumanoidFuss = new GridBagConstraints();
				gbc_radioZielHumanoidFuss.anchor = GridBagConstraints.WEST;
				gbc_radioZielHumanoidFuss.insets = new Insets(0, 0, 5, 0);
				gbc_radioZielHumanoidFuss.gridx = 2;
				gbc_radioZielHumanoidFuss.gridy = 2;
				zielPanel_1.add(radioZielHumanoidFuss, gbc_radioZielHumanoidFuss);
				zielHumanoidGroup.add(radioZielHumanoidFuss);

				JRadioButton radioZielHumanoidArme = new JRadioButton("Arme");
				gezielterSchussGroup.add(radioZielHumanoidArme);
				radioZielHumanoidArme.setEnabled(false);
				GridBagConstraints gbc_radioZielHumanoidArme = new GridBagConstraints();
				gbc_radioZielHumanoidArme.anchor = GridBagConstraints.WEST;
				gbc_radioZielHumanoidArme.insets = new Insets(0, 0, 5, 5);
				gbc_radioZielHumanoidArme.gridx = 0;
				gbc_radioZielHumanoidArme.gridy = 3;
				zielPanel_1.add(radioZielHumanoidArme, gbc_radioZielHumanoidArme);
				zielHumanoidGroup.add(radioZielHumanoidArme);

				JRadioButton radioZielHumanoidAuge = new JRadioButton("Auge");
				gezielterSchussGroup.add(radioZielHumanoidAuge);
				radioZielHumanoidAuge.setEnabled(false);
				GridBagConstraints gbc_radioZielHumanoidAuge = new GridBagConstraints();
				gbc_radioZielHumanoidAuge.anchor = GridBagConstraints.WEST;
				gbc_radioZielHumanoidAuge.insets = new Insets(0, 0, 5, 0);
				gbc_radioZielHumanoidAuge.gridx = 2;
				gbc_radioZielHumanoidAuge.gridy = 3;
				zielPanel_1.add(radioZielHumanoidAuge, gbc_radioZielHumanoidAuge);
				zielHumanoidGroup.add(radioZielHumanoidAuge);

				JRadioButton radioZielHumanoidBauch = new JRadioButton("Bauch");
				gezielterSchussGroup.add(radioZielHumanoidBauch);
				radioZielHumanoidBauch.setEnabled(false);
				GridBagConstraints gbc_radioZielHumanoidBauch = new GridBagConstraints();
				gbc_radioZielHumanoidBauch.anchor = GridBagConstraints.WEST;
				gbc_radioZielHumanoidBauch.insets = new Insets(0, 0, 5, 5);
				gbc_radioZielHumanoidBauch.gridx = 0;
				gbc_radioZielHumanoidBauch.gridy = 4;
				zielPanel_1.add(radioZielHumanoidBauch, gbc_radioZielHumanoidBauch);
				zielHumanoidGroup.add(radioZielHumanoidBauch);

				JRadioButton radioZielHumanoidHerz = new JRadioButton("Herz");
				gezielterSchussGroup.add(radioZielHumanoidHerz);
				radioZielHumanoidHerz.setEnabled(false);
				GridBagConstraints gbc_radioZielHumanoidHerz = new GridBagConstraints();
				gbc_radioZielHumanoidHerz.insets = new Insets(0, 0, 5, 0);
				gbc_radioZielHumanoidHerz.anchor = GridBagConstraints.WEST;
				gbc_radioZielHumanoidHerz.gridx = 2;
				gbc_radioZielHumanoidHerz.gridy = 4;
				zielPanel_1.add(radioZielHumanoidHerz, gbc_radioZielHumanoidHerz);
				zielHumanoidGroup.add(radioZielHumanoidHerz);

				JRadioButton radioZielHumanoidBeine = new JRadioButton("Beine");
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

				lblZielTier = new JLabel("Vierbeiner");
				lblZielTier.setVerticalAlignment(SwingConstants.TOP);
				zielPanel_2.add(lblZielTier);

				JRadioButton radioZielTierRumpf = new JRadioButton("Rumpf");
				gezielterSchussGroup.add(radioZielTierRumpf);
				radioZielTierRumpf.setEnabled(false);
				zielPanel_2.add(radioZielTierRumpf);

				JRadioButton radioZielTierBein = new JRadioButton("Bein");
				gezielterSchussGroup.add(radioZielTierBein);
				radioZielTierBein.setEnabled(false);
				zielPanel_2.add(radioZielTierBein);

				JRadioButton radioZielTierVStelle = new JRadioButton("verwundbare Stelle");
				gezielterSchussGroup.add(radioZielTierVStelle);
				radioZielTierVStelle.setEnabled(false);
				zielPanel_2.add(radioZielTierVStelle);

				JRadioButton radioZielTierKopf = new JRadioButton("Kopf");
				gezielterSchussGroup.add(radioZielTierKopf);
				radioZielTierKopf.setEnabled(false);
				zielPanel_2.add(radioZielTierKopf);

				JRadioButton radioZielTierSchwanz = new JRadioButton("Schwanz");
				gezielterSchussGroup.add(radioZielTierSchwanz);
				radioZielTierSchwanz.setEnabled(false);
				zielPanel_2.add(radioZielTierSchwanz);

				JRadioButton radioZielTierSinnesorgan = new JRadioButton("Sinnesorgan");
				gezielterSchussGroup.add(radioZielTierSinnesorgan);
				radioZielTierSinnesorgan.setEnabled(false);
				zielPanel_2.add(radioZielTierSinnesorgan);

				zielTierGroup.add(radioZielTierRumpf);
				zielTierGroup.add(radioZielTierBein);
				zielTierGroup.add(radioZielTierVStelle);
				zielTierGroup.add(radioZielTierKopf);
				zielTierGroup.add(radioZielTierSchwanz);
				zielTierGroup.add(radioZielTierSinnesorgan);

				chkBoxKoerperteilBewegung = new JCheckBox("Körperteil in Bewegung");
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
			
			JLabel lblNewLabel_5 = new JLabel("Zusammenfassung");
			lblNewLabel_5.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
			GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
			gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
			gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_5.gridx = 0;
			gbc_lblNewLabel_5.gridy = 0;
			summaryPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
			
			JLabel lblNewLabel_6 = new JLabel("Erschwernis durch Zielgröße");
			GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
			gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_6.gridx = 0;
			gbc_lblNewLabel_6.gridy = 1;
			summaryPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);
			
			lblErschwernisZielgroesseValue = new JLabel("0");
			GridBagConstraints gbc_lblErschwernisZielgroesseValue = new GridBagConstraints();
			gbc_lblErschwernisZielgroesseValue.anchor = GridBagConstraints.EAST;
			gbc_lblErschwernisZielgroesseValue.insets = new Insets(0, 0, 5, 5);
			gbc_lblErschwernisZielgroesseValue.gridx = 2;
			gbc_lblErschwernisZielgroesseValue.gridy = 1;
			summaryPanel.add(lblErschwernisZielgroesseValue, gbc_lblErschwernisZielgroesseValue);
			
			JLabel lblNewLabel_15 = new JLabel("Erschwernis durch Entfernung");
			GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
			gbc_lblNewLabel_15.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_15.gridx = 0;
			gbc_lblNewLabel_15.gridy = 2;
			summaryPanel.add(lblNewLabel_15, gbc_lblNewLabel_15);
			
			lblErschwernisEntfernungValue = new JLabel("0");
			GridBagConstraints gbc_lblErschwernisEntfernungValue = new GridBagConstraints();
			gbc_lblErschwernisEntfernungValue.anchor = GridBagConstraints.EAST;
			gbc_lblErschwernisEntfernungValue.insets = new Insets(0, 0, 5, 5);
			gbc_lblErschwernisEntfernungValue.gridx = 2;
			gbc_lblErschwernisEntfernungValue.gridy = 2;
			summaryPanel.add(lblErschwernisEntfernungValue, gbc_lblErschwernisEntfernungValue);
			
			JLabel lblNewLabel_16 = new JLabel("Erschwernis durch Bewegung");
			GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
			gbc_lblNewLabel_16.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_16.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_16.gridx = 0;
			gbc_lblNewLabel_16.gridy = 3;
			summaryPanel.add(lblNewLabel_16, gbc_lblNewLabel_16);
			
			lblErschwernisBewegungValue = new JLabel("0");
			GridBagConstraints gbc_lblErschwernisBewegungValue = new GridBagConstraints();
			gbc_lblErschwernisBewegungValue.anchor = GridBagConstraints.EAST;
			gbc_lblErschwernisBewegungValue.insets = new Insets(0, 0, 5, 5);
			gbc_lblErschwernisBewegungValue.gridx = 2;
			gbc_lblErschwernisBewegungValue.gridy = 3;
			summaryPanel.add(lblErschwernisBewegungValue, gbc_lblErschwernisBewegungValue);
			
			JLabel lblNewLabel_17 = new JLabel("Erschwernis durch Sicht");
			GridBagConstraints gbc_lblNewLabel_17 = new GridBagConstraints();
			gbc_lblNewLabel_17.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_17.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_17.gridx = 0;
			gbc_lblNewLabel_17.gridy = 4;
			summaryPanel.add(lblNewLabel_17, gbc_lblNewLabel_17);
			
			lblErschwernisSichtValue = new JLabel("0");
			GridBagConstraints gbc_lblErschwernisSichtValue = new GridBagConstraints();
			gbc_lblErschwernisSichtValue.anchor = GridBagConstraints.EAST;
			gbc_lblErschwernisSichtValue.insets = new Insets(0, 0, 5, 5);
			gbc_lblErschwernisSichtValue.gridx = 2;
			gbc_lblErschwernisSichtValue.gridy = 4;
			summaryPanel.add(lblErschwernisSichtValue, gbc_lblErschwernisSichtValue);
			
			JLabel lblNewLabel_18 = new JLabel("Erschwernis durch Modifikatoren");
			GridBagConstraints gbc_lblNewLabel_18 = new GridBagConstraints();
			gbc_lblNewLabel_18.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_18.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_18.gridx = 0;
			gbc_lblNewLabel_18.gridy = 5;
			summaryPanel.add(lblNewLabel_18, gbc_lblNewLabel_18);
			
			lblErschwernisModifikatorenValue = new JLabel("0");
			GridBagConstraints gbc_lblErschwernisModifikatorenValue = new GridBagConstraints();
			gbc_lblErschwernisModifikatorenValue.anchor = GridBagConstraints.EAST;
			gbc_lblErschwernisModifikatorenValue.insets = new Insets(0, 0, 5, 5);
			gbc_lblErschwernisModifikatorenValue.gridx = 2;
			gbc_lblErschwernisModifikatorenValue.gridy = 5;
			summaryPanel.add(lblErschwernisModifikatorenValue, gbc_lblErschwernisModifikatorenValue);
			
			JLabel lblNewLabel_19 = new JLabel("Erschwernis durch gezielten Schuss");
			GridBagConstraints gbc_lblNewLabel_19 = new GridBagConstraints();
			gbc_lblNewLabel_19.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_19.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_19.gridx = 0;
			gbc_lblNewLabel_19.gridy = 6;
			summaryPanel.add(lblNewLabel_19, gbc_lblNewLabel_19);
			
			lblErschwernisGezielterSchussValue = new JLabel("0");
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
			
			JLabel lblKompletteErschwernisValue = new JLabel("Komplette Erschwernis");
			lblKompletteErschwernisValue.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			GridBagConstraints gbc_lblNewLabel_20 = new GridBagConstraints();
			gbc_lblNewLabel_20.anchor = GridBagConstraints.EAST;
			gbc_lblNewLabel_20.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_20.gridx = 0;
			gbc_lblNewLabel_20.gridy = 8;
			summaryPanel.add(lblKompletteErschwernisValue, gbc_lblNewLabel_20);
			
			lblErschwernisKomplettWert = new JLabel("0");
			lblErschwernisKomplettWert.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			GridBagConstraints gbc_lblErschwernisKomplettWert = new GridBagConstraints();
			gbc_lblErschwernisKomplettWert.anchor = GridBagConstraints.EAST;
			gbc_lblErschwernisKomplettWert.insets = new Insets(0, 0, 5, 5);
			gbc_lblErschwernisKomplettWert.gridx = 2;
			gbc_lblErschwernisKomplettWert.gridy = 8;
			summaryPanel.add(lblErschwernisKomplettWert, gbc_lblErschwernisKomplettWert);
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				backButton = new JButton("zurück");
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
				forwardButton = new JButton("weiter");
				forwardButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JButton button = (JButton)e.getSource();
						String text = button.getText();
						
						if(text.equalsIgnoreCase("roll")) {
							setRollCommand(DsaCalculatorUtil.getFernkampfRollCommand(
									getFernwaffenObjekt().getFk(), 
									erschwernis));
							state = OK_STATE;
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
	
	protected void updateSummary() {
		int index = tabbedPane.getSelectedIndex();
		int modGroesse 		= 0;
		int modEntfernung	= 0;
		int modBewegung		= 0;
		int modSicht		= 0;
		int modModifikatoren= 0;
		int modGezielt		= 0;
		int resultat        = 0;
		if(index == 6) {
			modGroesse 		= berechneErschwerungGroesse();
			modEntfernung	= berechneErschwerungEntfernung();
			modBewegung     = berechneErschwerungBewegung();
			modSicht   		= berechneErschwerungSicht();
			modModifikatoren= berechneErschwerungModifikatoren();
			modGezielt      = berechneErschwerungGezielterSchuss();
			
			resultat = modGroesse + modEntfernung + modBewegung + modSicht + modModifikatoren + modGezielt;
			erschwernis = resultat;
		}
		
		lblErschwernisZielgroesseValue.setText(String.valueOf(modGroesse));
		lblErschwernisEntfernungValue.setText(String.valueOf(modEntfernung));
		lblErschwernisBewegungValue.setText(String.valueOf(modBewegung));
		lblErschwernisSichtValue.setText(String.valueOf(modSicht));
		lblErschwernisModifikatorenValue.setText(String.valueOf(modModifikatoren));
		lblErschwernisGezielterSchussValue.setText(String.valueOf(modGezielt));
		
		lblErschwernisKomplettWert.setText(String.valueOf(resultat));
	}
	
	private int berechneErschwerungGroesse() {
		int result = 0;
		String groesse = null;
		for(JRadioButton rb : groessenGroup) {
			if(rb.isSelected()) {
				groesse = rb.getText();
				break;
			}
		}
		
		int modifikator = (Integer) spinnerGroessenModifikator.getValue();
		boolean halbdeckung = chkboxHalbdeckung.isSelected();
		boolean dreivierteldeckung = chkBoxDreivierteldeckung.isSelected();
		
		result = DsaCalculatorUtil.getFernkampfGroessenModifikator(groesse, modifikator, halbdeckung, dreivierteldeckung);
		
		return result;
	}
	
	private int berechneErschwerungEntfernung() {
		int result = 0;
		String entfernung = null;
		for(JRadioButton rb : entfernungGroup) {
			if(rb.isSelected()) {
				entfernung = rb.getText();
				break;
			}
		}
		result = DsaCalculatorUtil.getFernkampfEntfernungsModifikator(entfernung);
		
		return result;
	}
	
	private int berechneErschwerungSicht() {
		int result = 0;
		String lichtquelle = "";
		boolean dunst = false;
		boolean nebel = false;
		boolean entfernungssinn = false;
		boolean daemmerungssicht = false;
		boolean nachtsicht = false;
		boolean einaeugig = false;
		boolean farbenblind = false;
		boolean kurzsichtig = false;
		boolean nachtblind = false;
		
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
				case "dämmerungssicht"  : daemmerungssicht 	= true; break;
				case "nachtsicht" 		: nachtsicht 		= true; break;
				case "entfernungssinn"  : entfernungssinn 	= true; break;
				}
			}
		}
		
		for(JCheckBox cb : nachteilListe) {
			if(cb.isSelected()) {
				switch(cb.getText().toLowerCase()) {
				case "einäugig" 	: einaeugig 	= true; break;
				case "farbenblind"	: farbenblind 	= true; break;
				case "kurzsichtig"	: kurzsichtig   = true; break;
				case "nachtblind"	: nachtblind    = true; break;
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
		result = DsaCalculatorUtil.getFernkampfSichtModifikator(
				lichtquelle, 
				dunst, nebel, 
				entfernungssinn, daemmerungssicht, nachtsicht, 
				einaeugig, farbenblind, kurzsichtig, nachtblind, 
				entfernung, 
				getFernwaffenObjekt());
		
		return result;
	}
	
	private int berechneErschwerungBewegung() {
		int result = 0;
		String bewegung = null;
		int anzahlGegnerInDistanzH 	= 0;
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
		
		result = DsaCalculatorUtil.getFernkampfBewegungsModifikator(bewegung, anzahlGegnerInDistanzH, anzahlGegnerInDistanzNS);
		
		return result;
	}
	
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
		
		result = DsaCalculatorUtil.getFernkampfModifikatorenModifikator(modifikatoren, schuetzentyp, ansage, zielen);
		
		return result;
	}
	
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
			result = DsaCalculatorUtil.getFernkampfGezielterSchussModifikator(schuetzentyp, humanoid, inBewegung, trefferzone, zielgroesse);
		}
		
		return result;
	}

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

	protected void enableGezielterSchuss(boolean selected) {
		enableButtonsInGroup(zielHumanoidGroup, selected);
		enableButtonsInGroup(zielTierGroup, selected);
		chkBoxKoerperteilBewegung.setEnabled(selected);
		lblZielHumanoid.setEnabled(selected);
		lblZielTier.setEnabled(selected);
	}
	
	protected void enableButtonsInGroup(List<JRadioButton> buttonGroup, boolean enabled) {
		for(JRadioButton rb : buttonGroup) {
			rb.setEnabled(enabled);
		}
	}

	/**
	 * @return the rollCommand
	 */
	public String getRollCommand() {
		return rollCommand;
	}
	
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
			forwardButton.setText("roll");
		}
		
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

	public FernwaffenObjekt getFernwaffenObjekt() {
		return fernwaffenObjekt;
	}

	public void setFernwaffenObjekt(FernwaffenObjekt fernwaffenObjekt) {
		this.fernwaffenObjekt = fernwaffenObjekt;
	}

	private void setFernwaffenDistanzen() {
		lblWaffeDistanzSehrNah.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(0)));
		lblWaffeDistanzNah.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(1)));
		lblWaffeDistanzMittel.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(2)));
		lblWaffeDistanzWeit.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(3)));
		lblWaffeDistanzSehrWeit.setText(Integer.toString(getFernwaffenObjekt().getEntfernungList().get(4)));

		repaint();
	}
}
