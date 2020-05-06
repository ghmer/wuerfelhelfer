package link.parzival.dsa.ui;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.HeldenObjekt;
import link.parzival.dsa.ParadeObjekt;
import link.parzival.dsa.WaffenObjekt;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import link.parzival.dsa.WaffenObjekt.Distanzklasse;
import javax.swing.JTextField;

public class CombatPanel extends JPanel {
	
	private enum ParadenOption {
		Waffe,Schild
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4466855899017287952L;
	private JSeparator topWeaponSeparator;
	private JSeparator bottomWeaponSeparator;
	private JComboBox<String> comboBoxRightWeaponHand;
	private JLabel lblRightWeaponHand;
	private JComboBox<String> comboBoxOwnDK;
	private JCheckBox chkUseDK;
	private JComboBox<Distanzklasse> comboBoxCurrentDK;
	private JLabel lblCurrentDK;
	private JLabel lblOwnDK;
	private JLabel lblNewLabel;
	private JSeparator separator;
	private JButton btnInitiative;
	private JButton btnAusweichen;
	private JLabel lblSchild;
	private JComboBox<String> comboBoxSchildhand;
	private JLabel lblAttackeModifier;
	private JButton btnAttacke;
	private JLabel lblParade;
	private JComboBox<ParadenOption> comboBoxParade;
	private JLabel lblParadeModifier;
	private JComboBox<String> paradeModifier;
	private JButton btnParade;
	private JSeparator dkSeparator;
	private JButton btnHopsen;
	
	private WaffenObjekt waffenObjekt;
	private ParadeObjekt paradeObjekt;
	private JButton btnFernkampf;
	private JLabel lblNewLabel_1;
	private JTextField tfInitiative;
	private JComboBox<String> attackeModifier;
	
	HeldenObjekt hero;

	/**
	 * @param talent the Talent to Display
	 * @param hero the HeldenObjekt to use
	 */
	@SuppressWarnings("unchecked")
	public CombatPanel(HeldenObjekt hero) {
		this.hero = hero;
		setLayout(null);
		
		if (hero.getWaffen().size() > 0) {
			setWaffenObjekt(hero.getWaffen().get(0));
		}

		if (hero.getParadeWaffen().size() > 0) {
			setParadeObjekt(hero.getParadeWaffen().get(0));
		}
		
		
		
		
		topWeaponSeparator = new JSeparator();
		topWeaponSeparator.setBounds(6, 48, 658, 9);
		add(topWeaponSeparator);
		
		lblRightWeaponHand = new JLabel("rechte Waffenhand");
		lblRightWeaponHand.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRightWeaponHand.setBounds(6, 64, 120, 16);
		add(lblRightWeaponHand);
		
		comboBoxRightWeaponHand = new JComboBox<>();
		comboBoxRightWeaponHand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				String waffenName = (String) cb.getSelectedItem();
				
				setWaffenObjekt(hero.getWaffeByName(waffenName));
				updateDistanzklasse();
			}
		});
		comboBoxRightWeaponHand.setModel(new DefaultComboBoxModel<>(hero.getWaffenNamenAsArray()));
		comboBoxRightWeaponHand.setBounds(138, 60, 526, 27);
		add(comboBoxRightWeaponHand);
		
		bottomWeaponSeparator = new JSeparator();
		bottomWeaponSeparator.setBounds(6, 124, 658, 9);
		add(bottomWeaponSeparator);
		
		chkUseDK = new JCheckBox("Distanzklasse verwenden");
		chkUseDK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox sourceOfEvent = (JCheckBox)e.getSource();
				enableDkElements(sourceOfEvent.isSelected());
			}
		});
		chkUseDK.setBounds(6, 222, 210, 23);
		add(chkUseDK);
		
		lblOwnDK = new JLabel("DK Waffe");
		lblOwnDK.setHorizontalAlignment(SwingConstants.TRAILING);
		lblOwnDK.setBounds(396, 226, 70, 16);
		add(lblOwnDK);
		
		comboBoxOwnDK = new JComboBox<>();
		comboBoxOwnDK.setBounds(482, 222, 70, 27);
		updateDistanzklasse();
		add(comboBoxOwnDK);
		
		lblCurrentDK = new JLabel("aktuelle DK");
		lblCurrentDK.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCurrentDK.setBounds(206, 226, 80, 16);
		add(lblCurrentDK);
		
		comboBoxCurrentDK = new JComboBox<>();
		comboBoxCurrentDK.setModel(new DefaultComboBoxModel<Distanzklasse>(Distanzklasse.values()));
		comboBoxCurrentDK.setBounds(298, 222, 100, 27);
		add(comboBoxCurrentDK);
		
		lblNewLabel = new JLabel("Kampf");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		lblNewLabel.setBounds(6, 18, 100, 27);
		add(lblNewLabel);
		
		separator = new JSeparator();
		separator.setBounds(6, 6, 658, 12);
		add(separator);
		
		btnInitiative = new JButton("Initiative!");
		btnInitiative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rollCommand = DsaCalculatorUtil.getEffectiveInitiativeRoll(hero, waffenObjekt, paradeObjekt);
				DzDiceHelperUi.copyToClipboard(rollCommand);
			}
		});
		btnInitiative.setBounds(6, 141, 140, 29);
		add(btnInitiative);
		
		btnAusweichen = new JButton("Ausweichen!");
		btnAusweichen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EvasionDialog dialog = new EvasionDialog(hero, chkUseDK.isSelected(), chkUseDK.isSelected()? (Distanzklasse)comboBoxCurrentDK.getSelectedItem() : null);
				dialog.setFont(getFont());
				dialog.setLocationRelativeTo(btnAusweichen.getRootPane());
				switch (dialog.showDialog()) {
			    case EvasionDialog.OK_STATE:
			    	DzDiceHelperUi.copyToClipboard(dialog.getRollCommand());
			        break;
				}
			}
		});
		btnAusweichen.setBounds(6, 176, 140, 29);
		add(btnAusweichen);
		
		lblSchild = new JLabel("Schild");
		lblSchild.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSchild.setBounds(6, 99, 120, 16);
		add(lblSchild);
		
		comboBoxSchildhand = new JComboBox<>();
		comboBoxSchildhand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> source = (JComboBox<String>) e.getSource();
				String paradeObjektName = (String) source.getSelectedItem();
				
				if(paradeObjektName != null && !paradeObjektName.isEmpty()) {
					setParadeObjekt(hero.getParadeWaffeByName(paradeObjektName));
				} else {
					setParadeObjekt(null);
				}
			}
		});
		comboBoxSchildhand.setModel(new DefaultComboBoxModel<>(hero.getParadeWaffenNamenAsArray()));
		comboBoxSchildhand.insertItemAt("", 0);
		if(getParadeObjekt() != null) {
			comboBoxSchildhand.setSelectedItem(getParadeObjekt().getName());
		}
		
		comboBoxSchildhand.setBounds(138, 95, 526, 27);
		add(comboBoxSchildhand);
		
		lblAttackeModifier = new JLabel("Mod.");
		lblAttackeModifier.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAttackeModifier.setBounds(410, 146, 60, 16);
		add(lblAttackeModifier);
		
		attackeModifier = new JComboBox<>();
		attackeModifier.setBounds(482, 142, 70, 27);
		attackeModifier.setModel(new ComboBoxModel<String>() {
			
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
		attackeModifier.setSelectedIndex(8);
		add(attackeModifier);
		
		btnAttacke = new JButton("Attacke!");
		btnAttacke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int effectiveDistance = DsaCalculatorUtil.getDistanceBetween(getSelectedWeaponDistance(), getCombatWeaponDistance());				
				int modificator = getAttackeModifier();
				
				if(useDistanceClasses()) {
					if(effectiveDistance >= 2 || effectiveDistance <= -2) {
						JOptionPane.showMessageDialog( btnAttacke.getRootPane(), "Attacke aufgrund der aktuellen Entfernung zum Gegner nicht möglich. Wähle Hopsen!" );
					} else {
						String rollCommand = DsaCalculatorUtil.getEffectiveAttackRoll(getWaffenObjekt(), modificator, useDistanceClasses(), getCombatWeaponDistance(), getSelectedWeaponDistance());
						DzDiceHelperUi.copyToClipboard(rollCommand);
					}
				} else {
					String rollCommand = DsaCalculatorUtil.getEffectiveAttackRoll(getWaffenObjekt(), modificator, useDistanceClasses(), getCombatWeaponDistance(), getSelectedWeaponDistance());
					DzDiceHelperUi.copyToClipboard(rollCommand);
				}
			}
		});
		btnAttacke.setBounds(564, 141, 100, 29);
		add(btnAttacke);
		
		lblParade = new JLabel("Parade mit");
		lblParade.setHorizontalAlignment(SwingConstants.TRAILING);
		lblParade.setBounds(186, 181, 100, 16);
		add(lblParade);
		
		comboBoxParade = new JComboBox<ParadenOption>();
		comboBoxParade.setToolTipText("Waffe: Parade wird mit Waffe berechnet\nSchild: Parade wird mit Schild/Parierwaffe berechnet");
		comboBoxParade.setModel(new DefaultComboBoxModel<ParadenOption>(ParadenOption.values()));
		comboBoxParade.setSelectedIndex(0);
		comboBoxParade.setBounds(298, 177, 100, 27);
		add(comboBoxParade);
		
		lblParadeModifier = new JLabel("Mod.");
		lblParadeModifier.setHorizontalAlignment(SwingConstants.TRAILING);
		lblParadeModifier.setBounds(410, 181, 60, 16);
		add(lblParadeModifier);
		
		paradeModifier = new JComboBox<String>();
		paradeModifier.setModel(new ComboBoxModel<String>() {
			
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
		paradeModifier.setSelectedIndex(8);
		paradeModifier.setBounds(482, 177, 70, 27);
		add(paradeModifier);
		
		btnParade = new JButton("Parade!");
		btnParade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ParadenOption paradenOption = (ParadenOption)comboBoxParade.getSelectedItem();
				
				int modificator = getParadeModifier();
				if(paradeObjekt == null && paradenOption.equals(ParadenOption.Schild)) {
					JOptionPane.showMessageDialog(getParent(), "Du hast doch gar keinen Schild!");
					return;
				}
				
				if(useDistanceClasses()) {
					int effectiveDistance = DsaCalculatorUtil.getDistanceBetween(getSelectedWeaponDistance(), getCombatWeaponDistance());
					if(effectiveDistance >= 2) {
						JOptionPane.showMessageDialog( btnAttacke.getRootPane(), "Parade aufgrund der aktuellen Entfernung zum Gegner nicht möglich. Wähle Hopsen!" );
						return;
					}
				}
				
				switch(paradenOption) {
					case Schild : {
						String rollCommand = DsaCalculatorUtil.getEffectiveShieldParadeRoll(getWaffenObjekt(), getParadeObjekt(), modificator, getInitiative(), useDistanceClasses(), getCombatWeaponDistance(), getSelectedWeaponDistance());
						DzDiceHelperUi.copyToClipboard(rollCommand);
						break;
					}
					case Waffe: {
						String rollCommand = DsaCalculatorUtil.getEffectiveWeaponParadeRoll(getWaffenObjekt(), modificator, getInitiative(), useDistanceClasses(), getCombatWeaponDistance(), getSelectedWeaponDistance());
						DzDiceHelperUi.copyToClipboard(rollCommand);
						break;
					}
				}
			}
		});
		btnParade.setBounds(564, 176, 100, 29);
		add(btnParade);
		
		dkSeparator = new JSeparator();
		dkSeparator.setBounds(6, 206, 658, 12);
		add(dkSeparator);
		
		btnHopsen = new JButton("Hopsen!");
		btnHopsen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DistanceChangeDialog dialog = new DistanceChangeDialog(hero, getWaffenObjekt());
				dialog.setFont(getFont());
				dialog.setLocationRelativeTo(btnHopsen.getRootPane());
				switch (dialog.showDialog()) {
			    case DistanceChangeDialog.OK_STATE:
			    	DzDiceHelperUi.copyToClipboard(dialog.getRollCommand());
			        break;
				}
			}
		});
		btnHopsen.setBounds(564, 222, 100, 29);
		add(btnHopsen);
		
		btnFernkampf = new JButton("Fernkampf");
		btnFernkampf.setEnabled(false);
		btnFernkampf.setBounds(547, 19, 117, 29);
		add(btnFernkampf);
		
		lblNewLabel_1 = new JLabel("Initiative eingeben");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNewLabel_1.setBounds(158, 146, 128, 16);
		add(lblNewLabel_1);
		
		tfInitiative = new JTextField();
		tfInitiative.setText("0");
		tfInitiative.setBounds(298, 141, 82, 26);
		add(tfInitiative);
		tfInitiative.setColumns(10);
		
		enableDkElements(chkUseDK.isSelected());
	}
	
	protected void updateDistanzklasse() {
		comboBoxOwnDK.setModel(new DefaultComboBoxModel<>(getWaffenObjekt().getDistanzklassenAsArray()));
		
	}

	protected void enableDkElements(boolean visible) {
		lblOwnDK.setVisible(visible);
		lblCurrentDK.setVisible(visible);
		comboBoxOwnDK.setVisible(visible);
		comboBoxCurrentDK.setVisible(visible);
		dkSeparator.setVisible(visible);
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
	
	public WaffenObjekt.Distanzklasse getSelectedWeaponDistance() {
		WaffenObjekt.Distanzklasse result = null;
		String selectedItem = (String) comboBoxOwnDK.getSelectedItem();
		result = Distanzklasse.valueOf(selectedItem);
		
		return result;
	}
	
	public WaffenObjekt.Distanzklasse getCombatWeaponDistance() {
		WaffenObjekt.Distanzklasse result = null;
		result = (Distanzklasse) comboBoxCurrentDK.getSelectedItem();
		
		return result;
	}
	
	public boolean useDistanceClasses() {
		boolean useDk = false;
		useDk = (boolean) chkUseDK.isSelected();
		return useDk;
	}
	
	public int getParadeModifier() {
		String modStr	= (String) paradeModifier.getSelectedItem();
		int modIntVal 	= Integer.valueOf(modStr);
		
		return modIntVal;
	}
	
	public int getAttackeModifier() {
		String modStr	= (String) attackeModifier.getSelectedItem();
		int modIntVal 	= Integer.valueOf(modStr);
		
		return modIntVal;
	}
	
	public int getInitiative() {
		int ini = hero.getBasisinitiative();
		String iniStr = null;
		try {
			iniStr = tfInitiative.getText();
			if(iniStr != null && !iniStr.isEmpty()) {
				ini = Integer.valueOf(iniStr);
			}
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		
		return ini;
	}
}
