package link.parzival.dsa.ui.panel;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.object.FernwaffenObjekt;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.ParadeObjekt;
import link.parzival.dsa.object.WaffenObjekt;
import link.parzival.dsa.object.enumeration.DKEnum;
import link.parzival.dsa.ui.DzDiceHelperUi;
import link.parzival.dsa.ui.dialog.DistanceChangeDialog;
import link.parzival.dsa.ui.dialog.EvasionDialog;
import link.parzival.dsa.ui.dialog.FernkampfDialog;

import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataListener;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.BorderLayout;

public class CombatPanel extends JPanel {
	
	private enum ParadenOption {
		Waffe,Schild
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4466855899017287952L;
	private JComboBox<String> comboBoxRightWeaponHand;
	private JLabel lblRightWeaponHand;
	private JComboBox<String> comboBoxOwnDK;
	private JCheckBox chkUseDK;
	private JComboBox<DKEnum> comboBoxCurrentDK;
	private JLabel lblCurrentDK;
	private JLabel lblOwnDK;
	private JLabel lblNewLabel;
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
	private JButton btnHopsen;
	
	private WaffenObjekt waffenObjekt;
	private ParadeObjekt paradeObjekt;
	private FernwaffenObjekt fernwaffenObjekt;
	private JButton btnFernkampf;
	private JLabel lblNewLabel_1;
	private JTextField tfInitiative;
	private JComboBox<String> attackeModifier;
	
	HeldenObjekt hero;
	private JPanel panel;
	private JSeparator separator;
	private JPanel panel_1;
	private JPanel panel_2;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JPanel panel_3;
	private JSeparator separator_3;
	private JLabel lblFernkampfWaffe;
	private JComboBox<String> comboBoxFernkampfwaffe;

	/**
	 * @param hero the HeldenObjekt to use
	 */
	@SuppressWarnings("unchecked")
	public CombatPanel(HeldenObjekt hero) {
		this.hero = hero;
		
		if (hero.getWaffen().size() > 0) {
			setWaffenObjekt(hero.getWaffen().get(0));
		}

		if (hero.getParadeWaffen().size() > 0) {
			setParadeObjekt(hero.getParadeWaffen().get(0));
		}
		
		if(hero.getFernWaffen().size() > 0) {
			setFernwaffenObjekt(hero.getFernWaffen().get(0));
		}
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 120, 0, 10, 0, 128, 82, 2, 60, 70, 100, 0};
		gridBagLayout.rowHeights = new int[]{15, 30, 15, 27, 27, 0, 15, 29, 29, 15, 29, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.gridwidth = 11;
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		add(panel_3, gbc_panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		separator_3 = new JSeparator();
		panel_3.add(separator_3, BorderLayout.NORTH);
		
		lblNewLabel = new JLabel("Kampf");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 15));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		add(lblNewLabel, gbc_lblNewLabel);
		
		chkUseDK = new JCheckBox("DK verwenden");
		chkUseDK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBox sourceOfEvent = (JCheckBox)e.getSource();
				enableDkElements(sourceOfEvent.isSelected());
			}
		});
		
		btnFernkampf = new JButton("Fernkampf");
		btnFernkampf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(getFernwaffenObjekt() == null) {
					JOptionPane.showMessageDialog(getParent(), "Du hast doch gar keine Fernkampfwaffe!");
					return;
				}
				FernkampfDialog dialog = new FernkampfDialog(getFernwaffenObjekt());
				dialog.setFont(getFont());
				dialog.setLocationRelativeTo(btnFernkampf.getRootPane());
				switch (dialog.showDialog()) {
			    case DistanceChangeDialog.OK_STATE:
			    	DzDiceHelperUi.copyToClipboard(dialog.getRollCommand());
			        break;
				}
				
			}
		});
		GridBagConstraints gbc_btnFernkampf = new GridBagConstraints();
		gbc_btnFernkampf.anchor = GridBagConstraints.SOUTHEAST;
		gbc_btnFernkampf.insets = new Insets(0, 0, 5, 0);
		gbc_btnFernkampf.gridx = 10;
		gbc_btnFernkampf.gridy = 1;
		add(btnFernkampf, gbc_btnFernkampf);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 11;
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		add(panel_2, gbc_panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		separator_1 = new JSeparator();
		panel_2.add(separator_1, BorderLayout.NORTH);
		
		lblRightWeaponHand = new JLabel("rechte Waffenhand");
		lblRightWeaponHand.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblRightWeaponHand = new GridBagConstraints();
		gbc_lblRightWeaponHand.gridwidth = 4;
		gbc_lblRightWeaponHand.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblRightWeaponHand.insets = new Insets(0, 0, 5, 5);
		gbc_lblRightWeaponHand.gridx = 0;
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
		comboBoxRightWeaponHand.setModel(new DefaultComboBoxModel<>(hero.getWaffenNamenAsArray()));
		GridBagConstraints gbc_comboBoxRightWeaponHand = new GridBagConstraints();
		gbc_comboBoxRightWeaponHand.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxRightWeaponHand.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxRightWeaponHand.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxRightWeaponHand.gridwidth = 7;
		gbc_comboBoxRightWeaponHand.gridx = 4;
		gbc_comboBoxRightWeaponHand.gridy = 3;
		add(comboBoxRightWeaponHand, gbc_comboBoxRightWeaponHand);
		
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
		
		lblSchild = new JLabel("Schild");
		lblSchild.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblSchild = new GridBagConstraints();
		gbc_lblSchild.gridwidth = 4;
		gbc_lblSchild.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblSchild.insets = new Insets(0, 0, 5, 5);
		gbc_lblSchild.gridx = 0;
		gbc_lblSchild.gridy = 4;
		add(lblSchild, gbc_lblSchild);
		GridBagConstraints gbc_comboBoxSchildhand = new GridBagConstraints();
		gbc_comboBoxSchildhand.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxSchildhand.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxSchildhand.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxSchildhand.gridwidth = 7;
		gbc_comboBoxSchildhand.gridx = 4;
		gbc_comboBoxSchildhand.gridy = 4;
		add(comboBoxSchildhand, gbc_comboBoxSchildhand);
		
		lblFernkampfWaffe = new JLabel("Fernwaffe");
		lblFernkampfWaffe.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblFernkampfWaffe = new GridBagConstraints();
		gbc_lblFernkampfWaffe.gridwidth = 4;
		gbc_lblFernkampfWaffe.anchor = GridBagConstraints.EAST;
		gbc_lblFernkampfWaffe.insets = new Insets(0, 0, 5, 5);
		gbc_lblFernkampfWaffe.gridx = 0;
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
		comboBoxFernkampfwaffe.setModel(new DefaultComboBoxModel<>(hero.getFernWaffenNamenAsArray()));
		
		
		GridBagConstraints gbc_comboBoxFernkampfwaffe = new GridBagConstraints();
		gbc_comboBoxFernkampfwaffe.gridwidth = 7;
		gbc_comboBoxFernkampfwaffe.insets = new Insets(0, 0, 5, 0);
		gbc_comboBoxFernkampfwaffe.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxFernkampfwaffe.gridx = 4;
		gbc_comboBoxFernkampfwaffe.gridy = 5;
		add(comboBoxFernkampfwaffe, gbc_comboBoxFernkampfwaffe);
		
		btnInitiative = new JButton("Initiative!");
		btnInitiative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String rollCommand = DsaCalculatorUtil.getEffectiveInitiativeRoll(hero, waffenObjekt, paradeObjekt);
				DzDiceHelperUi.copyToClipboard(rollCommand);
			}
		});

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 11;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 6;
		add(panel, gbc_panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		separator = new JSeparator();
		panel.add(separator, BorderLayout.CENTER);
		GridBagConstraints gbc_btnInitiative = new GridBagConstraints();
		gbc_btnInitiative.gridwidth = 3;
		gbc_btnInitiative.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInitiative.anchor = GridBagConstraints.NORTH;
		gbc_btnInitiative.insets = new Insets(0, 0, 5, 5);
		gbc_btnInitiative.gridx = 1;
		gbc_btnInitiative.gridy = 7;
		add(btnInitiative, gbc_btnInitiative);
		
		lblNewLabel_1 = new JLabel("Initiative eingeben");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.gridwidth = 2;
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 4;
		gbc_lblNewLabel_1.gridy = 7;
		add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		btnAusweichen = new JButton("Ausweichen!");
		btnAusweichen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EvasionDialog dialog = new EvasionDialog(hero, chkUseDK.isSelected(), chkUseDK.isSelected()? (DKEnum)comboBoxCurrentDK.getSelectedItem() : null);
				dialog.setFont(getFont());
				dialog.setLocationRelativeTo(btnAusweichen.getRootPane());
				switch (dialog.showDialog()) {
			    case EvasionDialog.OK_STATE:
			    	DzDiceHelperUi.copyToClipboard(dialog.getRollCommand());
			        break;
				}
			}
		});
		
		tfInitiative = new JTextField();
		tfInitiative.setText("0");
		GridBagConstraints gbc_tfInitiative = new GridBagConstraints();
		gbc_tfInitiative.gridwidth = 2;
		gbc_tfInitiative.anchor = GridBagConstraints.NORTH;
		gbc_tfInitiative.fill = GridBagConstraints.HORIZONTAL;
		gbc_tfInitiative.insets = new Insets(0, 0, 5, 5);
		gbc_tfInitiative.gridx = 6;
		gbc_tfInitiative.gridy = 7;
		add(tfInitiative, gbc_tfInitiative);
		tfInitiative.setColumns(10);
		
		lblAttackeModifier = new JLabel("Mod.");
		lblAttackeModifier.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblAttackeModifier = new GridBagConstraints();
		gbc_lblAttackeModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblAttackeModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblAttackeModifier.gridx = 8;
		gbc_lblAttackeModifier.gridy = 7;
		add(lblAttackeModifier, gbc_lblAttackeModifier);
		
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
		
		attackeModifier = new JComboBox<>();
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
		GridBagConstraints gbc_attackeModifier = new GridBagConstraints();
		gbc_attackeModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_attackeModifier.insets = new Insets(0, 0, 5, 5);
		gbc_attackeModifier.gridx = 9;
		gbc_attackeModifier.gridy = 7;
		add(attackeModifier, gbc_attackeModifier);
		GridBagConstraints gbc_btnAttacke = new GridBagConstraints();
		gbc_btnAttacke.anchor = GridBagConstraints.NORTH;
		gbc_btnAttacke.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAttacke.insets = new Insets(0, 0, 5, 0);
		gbc_btnAttacke.gridx = 10;
		gbc_btnAttacke.gridy = 7;
		add(btnAttacke, gbc_btnAttacke);
		GridBagConstraints gbc_btnAusweichen = new GridBagConstraints();
		gbc_btnAusweichen.gridwidth = 3;
		gbc_btnAusweichen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAusweichen.anchor = GridBagConstraints.NORTH;
		gbc_btnAusweichen.insets = new Insets(0, 0, 5, 5);
		gbc_btnAusweichen.gridx = 1;
		gbc_btnAusweichen.gridy = 8;
		add(btnAusweichen, gbc_btnAusweichen);
		
		lblParade = new JLabel("Parade mit");
		lblParade.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblParade = new GridBagConstraints();
		gbc_lblParade.gridwidth = 2;
		gbc_lblParade.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblParade.insets = new Insets(0, 0, 5, 5);
		gbc_lblParade.gridx = 4;
		gbc_lblParade.gridy = 8;
		add(lblParade, gbc_lblParade);
		
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
		
		comboBoxParade = new JComboBox<ParadenOption>();
		comboBoxParade.setToolTipText("Waffe: Parade wird mit Waffe berechnet\nSchild: Parade wird mit Schild/Parierwaffe berechnet");
		comboBoxParade.setModel(new DefaultComboBoxModel<ParadenOption>(ParadenOption.values()));
		comboBoxParade.setSelectedIndex(0);
		GridBagConstraints gbc_comboBoxParade = new GridBagConstraints();
		gbc_comboBoxParade.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxParade.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxParade.gridwidth = 2;
		gbc_comboBoxParade.gridx = 6;
		gbc_comboBoxParade.gridy = 8;
		add(comboBoxParade, gbc_comboBoxParade);
		
		lblParadeModifier = new JLabel("Mod.");
		lblParadeModifier.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblParadeModifier = new GridBagConstraints();
		gbc_lblParadeModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblParadeModifier.insets = new Insets(0, 0, 5, 5);
		gbc_lblParadeModifier.gridx = 8;
		gbc_lblParadeModifier.gridy = 8;
		add(lblParadeModifier, gbc_lblParadeModifier);
		
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
		GridBagConstraints gbc_paradeModifier = new GridBagConstraints();
		gbc_paradeModifier.fill = GridBagConstraints.HORIZONTAL;
		gbc_paradeModifier.insets = new Insets(0, 0, 5, 5);
		gbc_paradeModifier.gridx = 9;
		gbc_paradeModifier.gridy = 8;
		add(paradeModifier, gbc_paradeModifier);
		GridBagConstraints gbc_btnParade = new GridBagConstraints();
		gbc_btnParade.anchor = GridBagConstraints.NORTH;
		gbc_btnParade.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnParade.insets = new Insets(0, 0, 5, 0);
		gbc_btnParade.gridx = 10;
		gbc_btnParade.gridy = 8;
		add(btnParade, gbc_btnParade);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 11;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 9;
		add(panel_1, gbc_panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		separator_2 = new JSeparator();
		panel_1.add(separator_2, BorderLayout.CENTER);
		GridBagConstraints gbc_chkUseDK = new GridBagConstraints();
		gbc_chkUseDK.fill = GridBagConstraints.HORIZONTAL;
		gbc_chkUseDK.anchor = GridBagConstraints.NORTH;
		gbc_chkUseDK.insets = new Insets(0, 0, 0, 5);
		gbc_chkUseDK.gridwidth = 4;
		gbc_chkUseDK.gridx = 1;
		gbc_chkUseDK.gridy = 10;
		add(chkUseDK, gbc_chkUseDK);
		
		lblCurrentDK = new JLabel("aktuelle DK");
		lblCurrentDK.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblCurrentDK = new GridBagConstraints();
		gbc_lblCurrentDK.gridwidth = 2;
		gbc_lblCurrentDK.anchor = GridBagConstraints.EAST;
		gbc_lblCurrentDK.insets = new Insets(0, 0, 0, 5);
		gbc_lblCurrentDK.gridx = 4;
		gbc_lblCurrentDK.gridy = 10;
		add(lblCurrentDK, gbc_lblCurrentDK);
		
		comboBoxCurrentDK = new JComboBox<>();
		comboBoxCurrentDK.setModel(new DefaultComboBoxModel<DKEnum>(DKEnum.values()));
		GridBagConstraints gbc_comboBoxCurrentDK = new GridBagConstraints();
		gbc_comboBoxCurrentDK.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxCurrentDK.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxCurrentDK.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxCurrentDK.gridwidth = 2;
		gbc_comboBoxCurrentDK.gridx = 6;
		gbc_comboBoxCurrentDK.gridy = 10;
		add(comboBoxCurrentDK, gbc_comboBoxCurrentDK);
		
		lblOwnDK = new JLabel("DK Waffe");
		lblOwnDK.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblOwnDK = new GridBagConstraints();
		gbc_lblOwnDK.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOwnDK.insets = new Insets(0, 0, 0, 5);
		gbc_lblOwnDK.gridwidth = 2;
		gbc_lblOwnDK.gridx = 7;
		gbc_lblOwnDK.gridy = 10;
		add(lblOwnDK, gbc_lblOwnDK);
		
		comboBoxOwnDK = new JComboBox<>();
		GridBagConstraints gbc_comboBoxOwnDK = new GridBagConstraints();
		gbc_comboBoxOwnDK.anchor = GridBagConstraints.NORTH;
		gbc_comboBoxOwnDK.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxOwnDK.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxOwnDK.gridx = 9;
		gbc_comboBoxOwnDK.gridy = 10;
		add(comboBoxOwnDK, gbc_comboBoxOwnDK);
		
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
		GridBagConstraints gbc_btnHopsen = new GridBagConstraints();
		gbc_btnHopsen.anchor = GridBagConstraints.NORTH;
		gbc_btnHopsen.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnHopsen.gridx = 10;
		gbc_btnHopsen.gridy = 10;
		add(btnHopsen, gbc_btnHopsen);
		updateDistanzklasse();
		if(getParadeObjekt() != null) {
			comboBoxSchildhand.setSelectedItem(getParadeObjekt().getName());
		}
		
		enableDkElements(chkUseDK.isSelected());
	}
	
	/**
	 * 
	 */
	protected void updateDistanzklasse() {
		if(getWaffenObjekt() != null) {
			comboBoxOwnDK.setModel(new DefaultComboBoxModel<>(getWaffenObjekt().getDistanzklassenAsArray()));	
		}
	}

	/**
	 * @param visible whether to set fields visible
	 */
	protected void enableDkElements(boolean visible) {
		lblOwnDK.setVisible(visible);
		lblCurrentDK.setVisible(visible);
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
		String modStr	= (String) paradeModifier.getSelectedItem();
		int modIntVal 	= Integer.valueOf(modStr);
		
		return modIntVal;
	}
	
	/**
	 * @return the modifier
	 */
	public int getAttackeModifier() {
		String modStr	= (String) attackeModifier.getSelectedItem();
		int modIntVal 	= Integer.valueOf(modStr);
		
		return modIntVal;
	}
	
	public FernwaffenObjekt getFernwaffenObjekt() {
		return fernwaffenObjekt;
	}

	public void setFernwaffenObjekt(FernwaffenObjekt fernwaffenObjekt) {
		this.fernwaffenObjekt = fernwaffenObjekt;
	}

	/**
	 * @return the initiative
	 */
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
