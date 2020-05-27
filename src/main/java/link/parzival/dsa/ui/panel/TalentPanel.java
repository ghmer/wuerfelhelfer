package link.parzival.dsa.ui.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import link.parzival.dsa.Constants;
import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.object.TalentObjektPlatzhalter;
import link.parzival.dsa.object.enumeration.EigenschaftEnum;
import link.parzival.dsa.ui.DzDiceHelperUi;
import link.parzival.dsa.ui.dialog.AbilityDialog;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import java.awt.BorderLayout;

public class TalentPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466855899017287952L;
	private JComboBox<EigenschaftEnum> pruefEigenschaft1;
	private JComboBox<EigenschaftEnum> pruefEigenschaft2;
	private JComboBox<EigenschaftEnum> pruefEigenschaft3;
	private JSpinner pruefModifier;
	
	private String selectedAbilityName = null;
	
	TalentObjekt talent = new TalentObjektPlatzhalter();
	HeldenObjekt hero   = new HeldenObjekt();
	/**
	 * @return the hero
	 */
	public HeldenObjekt getHero() {
		return hero;
	}

	/**
	 * @param hero the hero to set
	 */
	public void setHero(HeldenObjekt hero) {
		this.hero = hero;
	}

	private JLabel lblAbility;
	private JLabel lblTawValue;

	public TalentPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{181, 40, 70, 70, 70, 75, 100, 0};
		gridBagLayout.rowHeights = new int[]{17, 0, 17, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.gridwidth = 7;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		
		JButton btnNewButton = new JButton("Probe auswählen");
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbilityDialog dialog = new AbilityDialog(hero);
				dialog.setFont(getFont());
				dialog.setLocationRelativeTo(getRootPane());
				switch (dialog.showDialog()) {
			    case Constants.DIALOG_OK_STATE:
			    	setSelectedAbilityName(dialog.getSelectedAbility());
			        break;
				}
				
				TalentObjekt talentObjekt = null;
				if(getSelectedAbilityName() != null) {
					for(TalentObjekt objekt : hero.getTalente()) {
						if(objekt.getName().equalsIgnoreCase(getSelectedAbilityName())) {
							talentObjekt = objekt;
							break;
						}
					}
				}
				
				if(talentObjekt != null) {
					setTalentProbe(talentObjekt);
				}
				
			}
		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridwidth = 7;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		add(btnNewButton, gbc_btnNewButton);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.gridwidth = 7;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator_1 = new JSeparator();
		panel_1.add(separator_1, BorderLayout.NORTH);
		
		JLabel lblAbilityName = new JLabel("Fähigkeit");
		GridBagConstraints gbc_lblAbilityName = new GridBagConstraints();
		gbc_lblAbilityName.anchor = GridBagConstraints.NORTH;
		gbc_lblAbilityName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAbilityName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAbilityName.gridx = 0;
		gbc_lblAbilityName.gridy = 3;
		add(lblAbilityName, gbc_lblAbilityName);
		
		JLabel lblTaw = new JLabel("TaW");
		lblTaw.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTaw = new GridBagConstraints();
		gbc_lblTaw.anchor = GridBagConstraints.NORTH;
		gbc_lblTaw.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTaw.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaw.gridx = 1;
		gbc_lblTaw.gridy = 3;
		add(lblTaw, gbc_lblTaw);
		
		JLabel lblE1 = new JLabel("E1");
		lblE1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblE1 = new GridBagConstraints();
		gbc_lblE1.anchor = GridBagConstraints.NORTH;
		gbc_lblE1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblE1.insets = new Insets(0, 0, 5, 5);
		gbc_lblE1.gridx = 2;
		gbc_lblE1.gridy = 3;
		add(lblE1, gbc_lblE1);
		
		JLabel lblE2 = new JLabel("E2");
		lblE2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblE2 = new GridBagConstraints();
		gbc_lblE2.anchor = GridBagConstraints.NORTH;
		gbc_lblE2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblE2.insets = new Insets(0, 0, 5, 5);
		gbc_lblE2.gridx = 3;
		gbc_lblE2.gridy = 3;
		add(lblE2, gbc_lblE2);
		
		JLabel lblE3 = new JLabel("E3");
		lblE3.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblE3 = new GridBagConstraints();
		gbc_lblE3.anchor = GridBagConstraints.NORTH;
		gbc_lblE3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblE3.insets = new Insets(0, 0, 5, 5);
		gbc_lblE3.gridx = 4;
		gbc_lblE3.gridy = 3;
		add(lblE3, gbc_lblE3);
		
		JLabel lblMod = new JLabel("Mod");
		lblMod.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblMod = new GridBagConstraints();
		gbc_lblMod.anchor = GridBagConstraints.NORTH;
		gbc_lblMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblMod.gridx = 5;
		gbc_lblMod.gridy = 3;
		add(lblMod, gbc_lblMod);
		
		lblAbility = new JLabel(talent.getName());
		GridBagConstraints gbc_lblAbility = new GridBagConstraints();
		gbc_lblAbility.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAbility.insets = new Insets(0, 0, 0, 5);
		gbc_lblAbility.gridx = 0;
		gbc_lblAbility.gridy = 4;
		add(lblAbility, gbc_lblAbility);
		
		lblTawValue = new JLabel(String.valueOf(talent.getTalentwert()));
		lblTawValue.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTawValue = new GridBagConstraints();
		gbc_lblTawValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTawValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblTawValue.gridx = 1;
		gbc_lblTawValue.gridy = 4;
		add(lblTawValue, gbc_lblTawValue);
		
		JButton btnCreateRoll = new JButton("würfeln");
		btnCreateRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getSelectedAbilityName() == null) {
					JOptionPane.showMessageDialog(getRootPane(), "Es wurde noch keine Probe ausgewählt!");
					return;
				}
				EigenschaftEnum pruefung1 = (EigenschaftEnum)pruefEigenschaft1.getSelectedItem();
				EigenschaftEnum pruefung2 = (EigenschaftEnum)pruefEigenschaft2.getSelectedItem();
				EigenschaftEnum pruefung3 = (EigenschaftEnum)pruefEigenschaft3.getSelectedItem();
				int modifier  		  	  = (Integer)pruefModifier.getValue();
				
				int pruefAuswahl = 0;
				if(pruefung1.equals(EigenschaftEnum.NA)) pruefAuswahl += 1;
				if(pruefung2.equals(EigenschaftEnum.NA)) pruefAuswahl += 10;
				if(pruefung3.equals(EigenschaftEnum.NA)) pruefAuswahl += 100;
				
				int effectiveModifier 	= DsaCalculatorUtil.calculateModifier(modifier, hero.getBehinderung(), talent);
				String rollCommand		= null;
				
				switch(pruefAuswahl) {
				case 1  : {
					// erste Auswahl war NA
					String rollFormatString   = "!%s,%s,%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							hero.getFertigkeit(pruefung2),
							hero.getFertigkeit(pruefung3),
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
				case 10 : {
					// zweite Auswahl war NA
					String rollFormatString   = "!%s,%s,%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							hero.getFertigkeit(pruefung1),
							hero.getFertigkeit(pruefung3),
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
				case 11 : {
					// erste und zweite Auswahl war NA
					String rollFormatString   = "!%s,%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							hero.getFertigkeit(pruefung3),
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
				case 100: {
					// dritte Auswahl war NA
					String rollFormatString   = "!%s,%s,%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							hero.getFertigkeit(pruefung1),
							hero.getFertigkeit(pruefung2),
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
				case 101: {
					// erste und dritte Auswahl war NA
					String rollFormatString   = "!%s,%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							hero.getFertigkeit(pruefung2),
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
				case 110: {
					// alle Auswahlen waren NA. Prüfe rein auf Talentwert
					String rollFormatString   = "!%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
				case 111: {
					// alle Auswahlen haben eine ordentliche Probenauswahl
					String rollFormatString   = "!%s,%s,%s,%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							hero.getFertigkeit(pruefung1),
							hero.getFertigkeit(pruefung2),
							hero.getFertigkeit(pruefung3),
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
				default : {
					String rollFormatString   = "!%s,%s,%s,%s,%s  %s";
					rollCommand  = String.format(rollFormatString, 
							hero.getFertigkeit(pruefung1),
							hero.getFertigkeit(pruefung2),
							hero.getFertigkeit(pruefung3),
							talent.getTalentwert(),
							effectiveModifier,
							talent.getName());
					break;
				}
			}
			
				
			DzDiceHelperUi.copyToClipboard(rollCommand);
			
			}
		});
		
		pruefEigenschaft1 = new JComboBox<>();
		pruefEigenschaft1.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft1.setSelectedItem(talent.getProbenTalent1());
		GridBagConstraints gbc_pruefEigenschaft1 = new GridBagConstraints();
		gbc_pruefEigenschaft1.fill = GridBagConstraints.HORIZONTAL;
		gbc_pruefEigenschaft1.insets = new Insets(0, 0, 0, 5);
		gbc_pruefEigenschaft1.gridx = 2;
		gbc_pruefEigenschaft1.gridy = 4;
		add(pruefEigenschaft1, gbc_pruefEigenschaft1);
		
		pruefEigenschaft2 = new JComboBox<>();
		pruefEigenschaft2.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft2.setSelectedItem(talent.getProbenTalent2());
		GridBagConstraints gbc_pruefEigenschaft2 = new GridBagConstraints();
		gbc_pruefEigenschaft2.fill = GridBagConstraints.HORIZONTAL;
		gbc_pruefEigenschaft2.insets = new Insets(0, 0, 0, 5);
		gbc_pruefEigenschaft2.gridx = 3;
		gbc_pruefEigenschaft2.gridy = 4;
		add(pruefEigenschaft2, gbc_pruefEigenschaft2);
		
		pruefEigenschaft3 = new JComboBox<>();
		pruefEigenschaft3.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft3.setSelectedItem(talent.getProbenTalent3());
		GridBagConstraints gbc_pruefEigenschaft3 = new GridBagConstraints();
		gbc_pruefEigenschaft3.fill = GridBagConstraints.HORIZONTAL;
		gbc_pruefEigenschaft3.insets = new Insets(0, 0, 0, 5);
		gbc_pruefEigenschaft3.gridx = 4;
		gbc_pruefEigenschaft3.gridy = 4;
		add(pruefEigenschaft3, gbc_pruefEigenschaft3);
		
		pruefModifier = new JSpinner();
		GridBagConstraints gbc_pruefModifier = new GridBagConstraints();
		gbc_pruefModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_pruefModifier.insets = new Insets(0, 0, 0, 5);
		gbc_pruefModifier.gridx = 5;
		gbc_pruefModifier.gridy = 4;
		add(pruefModifier, gbc_pruefModifier);
		GridBagConstraints gbc_btnCreateRoll = new GridBagConstraints();
		gbc_btnCreateRoll.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCreateRoll.gridx = 6;
		gbc_btnCreateRoll.gridy = 4;
		add(btnCreateRoll, gbc_btnCreateRoll);
	}

	protected void setTalentProbe(TalentObjekt talentObjekt) {
		this.talent = talentObjekt;
		pruefEigenschaft1.setSelectedItem(talentObjekt.getProbenTalent1());
		pruefEigenschaft2.setSelectedItem(talentObjekt.getProbenTalent2());
		pruefEigenschaft3.setSelectedItem(talentObjekt.getProbenTalent3());
		
		lblAbility.setText(talentObjekt.getName());
		lblTawValue.setText(String.valueOf(talentObjekt.getTalentwert()));
	}

	/**
	 * @return the selectedAbilityName
	 */
	public String getSelectedAbilityName() {
		return selectedAbilityName;
	}

	/**
	 * @param selectedAbilityName the selectedAbilityName to set
	 */
	public void setSelectedAbilityName(String selectedAbilityName) {
		this.selectedAbilityName = selectedAbilityName;
	}
}
