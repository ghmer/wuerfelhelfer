package link.parzival.dsa.ui.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataListener;

import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.object.enumeration.EigenschaftEnum;
import link.parzival.dsa.ui.DzDiceHelperUi;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AbilityPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466855899017287952L;

	/**
	 * @param talent the Talent to Display
	 * @param hero the HeldenObjekt to use
	 */
	public AbilityPanel(TalentObjekt talent, HeldenObjekt hero) {
		setLayout(null);
		
		JLabel lblAbilityName = new JLabel("Fähigkeit");
		lblAbilityName.setBounds(6, 6, 181, 16);
		add(lblAbilityName);
		
		JLabel lblE1 = new JLabel("E1");
		lblE1.setHorizontalAlignment(SwingConstants.CENTER);
		lblE1.setBounds(251, 6, 70, 16);
		add(lblE1);
		
		JLabel lblE2 = new JLabel("E2");
		lblE2.setHorizontalAlignment(SwingConstants.CENTER);
		lblE2.setBounds(333, 6, 70, 16);
		add(lblE2);
		
		JLabel lblE3 = new JLabel("E3");
		lblE3.setHorizontalAlignment(SwingConstants.CENTER);
		lblE3.setBounds(415, 6, 70, 16);
		add(lblE3);
		
		JLabel lblMod = new JLabel("Mod");
		lblMod.setHorizontalAlignment(SwingConstants.CENTER);
		lblMod.setBounds(497, 6, 70, 16);
		add(lblMod);
		
		JLabel lblAbility = new JLabel(talent.getName());
		lblAbility.setBounds(6, 34, 181, 16);
		add(lblAbility);
		
		JComboBox<EigenschaftEnum> pruefEigenschaft1 = new JComboBox<>();
		pruefEigenschaft1.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft1.setSelectedItem(talent.getProbenTalent1());
		pruefEigenschaft1.setBounds(251, 30, 70, 27);
		add(pruefEigenschaft1);
		
		JComboBox<EigenschaftEnum> pruefEigenschaft2 = new JComboBox<>();
		pruefEigenschaft2.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft2.setSelectedItem(talent.getProbenTalent2());
		pruefEigenschaft2.setBounds(333, 30, 70, 27);
		add(pruefEigenschaft2);
		
		JComboBox<EigenschaftEnum> pruefEigenschaft3 = new JComboBox<>();
		pruefEigenschaft3.setModel(new DefaultComboBoxModel<EigenschaftEnum>(EigenschaftEnum.values()));
		pruefEigenschaft3.setSelectedItem(talent.getProbenTalent3());
		pruefEigenschaft3.setBounds(415, 30, 70, 27);
		add(pruefEigenschaft3);
				
		JComboBox<String> pruefModifier = new JComboBox<>();
		pruefModifier.setBounds(497, 29, 70, 27);
		pruefModifier.setModel(new ComboBoxModel<String>() {
			
			private String[] modifiers = new String[] {"-8","-7","-6","-5","-4","-3","-2","-1","0","+1","+2","+3","+4","+5","+6","+7","+8"};
			int index = 8;
			
			@Override
			public void addListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public String getElementAt(int index) {
				// TODO Auto-generated method stub
				return modifiers[index];
			}

			@Override
			public Object getSelectedItem() {
				return modifiers[index];
			}

			@Override
			public int getSize() {
				// TODO Auto-generated method stub
				return modifiers.length;
			}

			@Override
			public void removeListDataListener(ListDataListener l) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void setSelectedItem(Object anItem) {
				
				for(int i = 0; i < modifiers.length; i++) {
					if(modifiers[i].equals(anItem)) {
						index = i;
						break;
					}
				}
			}
			
		});
		pruefModifier.setSelectedIndex(8);
		add(pruefModifier);
		
		JButton btnCreateRoll = new JButton("roll");
		btnCreateRoll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EigenschaftEnum pruefung1 = (EigenschaftEnum)pruefEigenschaft1.getSelectedItem();
				EigenschaftEnum pruefung2 = (EigenschaftEnum)pruefEigenschaft2.getSelectedItem();
				EigenschaftEnum pruefung3 = (EigenschaftEnum)pruefEigenschaft3.getSelectedItem();
				String modifier  		  = (String)pruefModifier.getSelectedItem();
				
				String rollFormatString   = "!%s,%s,%s,%s,%s  %s";
				
				int effectiveModifier	  = calculateModifier(modifier, hero.getBehinderung(), talent);
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
		btnCreateRoll.setBounds(589, 29, 70, 29);
		add(btnCreateRoll);
		
		JLabel lblTaw = new JLabel("TaW");
		lblTaw.setHorizontalAlignment(SwingConstants.CENTER);
		lblTaw.setBounds(199, 6, 40, 16);
		add(lblTaw);
		
		JLabel lblTawValue = new JLabel(String.valueOf(talent.getTalentwert()));
		lblTawValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblTawValue.setBounds(199, 34, 40, 16);
		add(lblTawValue);
	}
	
	private int calculateModifier(String modifier, int behinderung, TalentObjekt talent) {
		int result = 0;
		int modifierInt = Integer.parseInt(modifier);
		
		if(talent.getBe() != null) {
			String talentBehinderung = talent.getBe();
			if(talentBehinderung.startsWith("BE")) {
				char operand = talentBehinderung.charAt(2);
				
				int length = talentBehinderung.length();
				int modInt = Integer.parseInt(talentBehinderung.substring(length-1, length));
				
				switch(operand) {
				case '+' : result = behinderung + modInt; break;
				case '-' : result = behinderung - modInt; break;
				case 'x' : result = behinderung * modInt; break;
				case '/' : result = behinderung / modInt; break;
				}
			}
		}
		
		result = result + modifierInt;
		
		return result;
	}
}
