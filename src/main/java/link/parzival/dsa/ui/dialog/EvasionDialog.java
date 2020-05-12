package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.enumeration.DKEnum;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class EvasionDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6393904558592079317L;
	public static final int OK_STATE = 0;
	public static final int CANCEL_STATE = 1;
	private final JPanel contentPanel = new JPanel();
	
	private JButton okButton;
	private JLabel lblEvasionNotPossible;
	
    private int state = CANCEL_STATE;
    private boolean gezieltesAusweichen = false;
	private String rollCommand = null;
    
    private int enemyCount = 1;
	private JCheckBox chkboxGezieltesAusweichen;
	private JComboBox<String> comboBoxEnemyCount;

	/**
	 * @param hero the HeldenObjekt to set
	 * @param withDk whether to use Distanzklassen
	 * @param distanzklasse the Distanzklasse to use
	 */
	public EvasionDialog(HeldenObjekt hero, boolean withDk, DKEnum distanzklasse) {
		setBounds(100, 100, 450, 130);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[]{175, 51, 125, 75, 0};
			gbl_contentPanel.rowHeights = new int[]{27, 16, 0};
			gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
			gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			contentPanel.setLayout(gbl_contentPanel);
			{
				lblEvasionNotPossible = new JLabel("Bei mehr als 3 Gegnern ist das Ausweichen nicht mehr möglich!");
				lblEvasionNotPossible.setForeground(Color.RED);
				lblEvasionNotPossible.setFont(new Font("Lucida Grande", Font.BOLD, 13));
				lblEvasionNotPossible.setVisible(false);
				{
					comboBoxEnemyCount = new JComboBox<>();
					comboBoxEnemyCount.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "mehr"}));
					comboBoxEnemyCount.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							@SuppressWarnings("unchecked")
							JComboBox<String> cb = (JComboBox<String>) e.getSource();
							String selection     = (String)cb.getSelectedItem();
							
							if(selection.equalsIgnoreCase("mehr")) {
								lblEvasionNotPossible.setVisible(true);
								okButton.setEnabled(false);
							} else {
								enemyCount = Integer.parseInt(selection);
								lblEvasionNotPossible.setVisible(false);
								okButton.setEnabled(true);
							}				
						}
					});
					chkboxGezieltesAusweichen = new JCheckBox("gezieltes Ausweichen");
					chkboxGezieltesAusweichen.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JCheckBox source = (JCheckBox) e.getSource();
							gezieltesAusweichen = source.isSelected();
						}
					});
					GridBagConstraints gbc_chkboxGezieltesAusweichen = new GridBagConstraints();
					gbc_chkboxGezieltesAusweichen.anchor = GridBagConstraints.NORTH;
					gbc_chkboxGezieltesAusweichen.fill = GridBagConstraints.HORIZONTAL;
					gbc_chkboxGezieltesAusweichen.insets = new Insets(0, 0, 5, 5);
					gbc_chkboxGezieltesAusweichen.gridx = 0;
					gbc_chkboxGezieltesAusweichen.gridy = 0;
					contentPanel.add(chkboxGezieltesAusweichen, gbc_chkboxGezieltesAusweichen);
					{
						JLabel lblEnemyCount = new JLabel("Anzahl der Gegner");
						lblEnemyCount.setHorizontalAlignment(SwingConstants.TRAILING);
						GridBagConstraints gbc_lblEnemyCount = new GridBagConstraints();
						gbc_lblEnemyCount.fill = GridBagConstraints.HORIZONTAL;
						gbc_lblEnemyCount.insets = new Insets(0, 0, 5, 5);
						gbc_lblEnemyCount.gridx = 2;
						gbc_lblEnemyCount.gridy = 0;
						contentPanel.add(lblEnemyCount, gbc_lblEnemyCount);
					}
					GridBagConstraints gbc_comboBoxEnemyCount = new GridBagConstraints();
					gbc_comboBoxEnemyCount.anchor = GridBagConstraints.NORTH;
					gbc_comboBoxEnemyCount.fill = GridBagConstraints.HORIZONTAL;
					gbc_comboBoxEnemyCount.insets = new Insets(0, 0, 5, 0);
					gbc_comboBoxEnemyCount.gridx = 3;
					gbc_comboBoxEnemyCount.gridy = 0;
					contentPanel.add(comboBoxEnemyCount, gbc_comboBoxEnemyCount);
				}
				lblEvasionNotPossible.setHorizontalAlignment(SwingConstants.CENTER);
				GridBagConstraints gbc_lblEvasionNotPossible = new GridBagConstraints();
				gbc_lblEvasionNotPossible.anchor = GridBagConstraints.NORTH;
				gbc_lblEvasionNotPossible.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblEvasionNotPossible.gridwidth = 4;
				gbc_lblEvasionNotPossible.gridx = 0;
				gbc_lblEvasionNotPossible.gridy = 1;
				contentPanel.add(lblEvasionNotPossible, gbc_lblEvasionNotPossible);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setRollCommand(DsaCalculatorUtil.getEffectiveEvadingRoll(hero, enemyCount, gezieltesAusweichen, withDk, distanzklasse));
						state = OK_STATE;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						state = CANCEL_STATE;
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	/**
	 * @return the rollCommand
	 */
	public String getRollCommand() {
		return rollCommand;
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
		setVisible(true);
        return state;	
	}

}
