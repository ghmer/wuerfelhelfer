package link.parzival.dsa.ui.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.object.enumeration.EigenschaftEnum;
import link.parzival.dsa.ui.DzDiceHelperUi;

import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JSpinner;

public class AbilityPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466855899017287952L;
	private JComboBox<EigenschaftEnum> pruefEigenschaft1;
	private JComboBox<EigenschaftEnum> pruefEigenschaft2;
	private JComboBox<EigenschaftEnum> pruefEigenschaft3;
	private JSpinner pruefModifier;

	/**
	 * @param talent the Talent to Display
	 * @param hero the HeldenObjekt to use
	 */
	public AbilityPanel(TalentObjekt talent, HeldenObjekt hero) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{181, 40, 70, 70, 70, 75, 111, 0};
		gridBagLayout.rowHeights = new int[]{16, 29, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblAbilityName = new JLabel("Fähigkeit");
		GridBagConstraints gbc_lblAbilityName = new GridBagConstraints();
		gbc_lblAbilityName.anchor = GridBagConstraints.NORTH;
		gbc_lblAbilityName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAbilityName.insets = new Insets(0, 0, 5, 5);
		gbc_lblAbilityName.gridx = 0;
		gbc_lblAbilityName.gridy = 0;
		add(lblAbilityName, gbc_lblAbilityName);
		
		JLabel lblTaw = new JLabel("TaW");
		lblTaw.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTaw = new GridBagConstraints();
		gbc_lblTaw.anchor = GridBagConstraints.NORTH;
		gbc_lblTaw.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTaw.insets = new Insets(0, 0, 5, 5);
		gbc_lblTaw.gridx = 1;
		gbc_lblTaw.gridy = 0;
		add(lblTaw, gbc_lblTaw);
		
		JLabel lblE1 = new JLabel("E1");
		lblE1.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblE1 = new GridBagConstraints();
		gbc_lblE1.anchor = GridBagConstraints.NORTH;
		gbc_lblE1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblE1.insets = new Insets(0, 0, 5, 5);
		gbc_lblE1.gridx = 2;
		gbc_lblE1.gridy = 0;
		add(lblE1, gbc_lblE1);
		
		JLabel lblE2 = new JLabel("E2");
		lblE2.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblE2 = new GridBagConstraints();
		gbc_lblE2.anchor = GridBagConstraints.NORTH;
		gbc_lblE2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblE2.insets = new Insets(0, 0, 5, 5);
		gbc_lblE2.gridx = 3;
		gbc_lblE2.gridy = 0;
		add(lblE2, gbc_lblE2);
		
		JLabel lblE3 = new JLabel("E3");
		lblE3.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblE3 = new GridBagConstraints();
		gbc_lblE3.anchor = GridBagConstraints.NORTH;
		gbc_lblE3.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblE3.insets = new Insets(0, 0, 5, 5);
		gbc_lblE3.gridx = 4;
		gbc_lblE3.gridy = 0;
		add(lblE3, gbc_lblE3);
		
		JLabel lblMod = new JLabel("Mod");
		lblMod.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblMod = new GridBagConstraints();
		gbc_lblMod.anchor = GridBagConstraints.NORTH;
		gbc_lblMod.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblMod.insets = new Insets(0, 0, 5, 5);
		gbc_lblMod.gridx = 5;
		gbc_lblMod.gridy = 0;
		add(lblMod, gbc_lblMod);
		
		JLabel lblAbility = new JLabel(talent.getName());
		GridBagConstraints gbc_lblAbility = new GridBagConstraints();
		gbc_lblAbility.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAbility.insets = new Insets(0, 0, 0, 5);
		gbc_lblAbility.gridx = 0;
		gbc_lblAbility.gridy = 1;
		add(lblAbility, gbc_lblAbility);
		
		JLabel lblTawValue = new JLabel(String.valueOf(talent.getTalentwert()));
		lblTawValue.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblTawValue = new GridBagConstraints();
		gbc_lblTawValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTawValue.insets = new Insets(0, 0, 0, 5);
		gbc_lblTawValue.gridx = 1;
		gbc_lblTawValue.gridy = 1;
		add(lblTawValue, gbc_lblTawValue);
		
		JButton btnCreateRoll = new JButton("roll");
		btnCreateRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EigenschaftEnum pruefung1 = (EigenschaftEnum)pruefEigenschaft1.getSelectedItem();
				EigenschaftEnum pruefung2 = (EigenschaftEnum)pruefEigenschaft2.getSelectedItem();
				EigenschaftEnum pruefung3 = (EigenschaftEnum)pruefEigenschaft3.getSelectedItem();
				int modifier  		  	  = (Integer)pruefModifier.getValue();
				
				String rollFormatString   = "!%s,%s,%s,%s,%s  %s";
				
				int effectiveModifier	  = DsaCalculatorUtil.calculateModifier(modifier, hero.getBehinderung(), talent);
				String rollCommand 		  = String.format(rollFormatString, 
												hero.getFertigkeit(pruefung1),
												hero.getFertigkeit(pruefung2),
												hero.getFertigkeit(pruefung3),
												talent.getTalentwert(),
												effectiveModifier,
												talent.getName());
				
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
		gbc_pruefEigenschaft1.gridy = 1;
		add(pruefEigenschaft1, gbc_pruefEigenschaft1);
		
		pruefEigenschaft2 = new JComboBox<>();
		pruefEigenschaft2.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft2.setSelectedItem(talent.getProbenTalent2());
		GridBagConstraints gbc_pruefEigenschaft2 = new GridBagConstraints();
		gbc_pruefEigenschaft2.fill = GridBagConstraints.HORIZONTAL;
		gbc_pruefEigenschaft2.insets = new Insets(0, 0, 0, 5);
		gbc_pruefEigenschaft2.gridx = 3;
		gbc_pruefEigenschaft2.gridy = 1;
		add(pruefEigenschaft2, gbc_pruefEigenschaft2);
		
		pruefEigenschaft3 = new JComboBox<>();
		pruefEigenschaft3.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft3.setSelectedItem(talent.getProbenTalent3());
		GridBagConstraints gbc_pruefEigenschaft3 = new GridBagConstraints();
		gbc_pruefEigenschaft3.fill = GridBagConstraints.HORIZONTAL;
		gbc_pruefEigenschaft3.insets = new Insets(0, 0, 0, 5);
		gbc_pruefEigenschaft3.gridx = 4;
		gbc_pruefEigenschaft3.gridy = 1;
		add(pruefEigenschaft3, gbc_pruefEigenschaft3);
		
		pruefModifier = new JSpinner();
		GridBagConstraints gbc_pruefModifier = new GridBagConstraints();
		gbc_pruefModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_pruefModifier.insets = new Insets(0, 0, 0, 5);
		gbc_pruefModifier.gridx = 5;
		gbc_pruefModifier.gridy = 1;
		add(pruefModifier, gbc_pruefModifier);
		GridBagConstraints gbc_btnCreateRoll = new GridBagConstraints();
		gbc_btnCreateRoll.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCreateRoll.gridx = 6;
		gbc_btnCreateRoll.gridy = 1;
		add(btnCreateRoll, gbc_btnCreateRoll);
	}
}
