package link.parzival.dsa.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.HeldenObjekt;
import link.parzival.dsa.WaffenObjekt.Distanzklasse;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Color;

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
	 * Create the dialog.
	 */
	public EvasionDialog(HeldenObjekt hero, boolean withDk, Distanzklasse distanzklasse) {
		setBounds(100, 100, 450, 130);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			chkboxGezieltesAusweichen = new JCheckBox("gezieltes Ausweichen");
			chkboxGezieltesAusweichen.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JCheckBox source = (JCheckBox) e.getSource();
					gezieltesAusweichen = source.isSelected();
				}
			});
			chkboxGezieltesAusweichen.setBounds(6, 6, 175, 23);
			contentPanel.add(chkboxGezieltesAusweichen);
		}
		{
			JLabel lblEnemyCount = new JLabel("Anzahl der Gegner");
			lblEnemyCount.setHorizontalAlignment(SwingConstants.TRAILING);
			lblEnemyCount.setBounds(232, 10, 125, 16);
			contentPanel.add(lblEnemyCount);
		}
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
			comboBoxEnemyCount.setBounds(369, 6, 75, 27);
			contentPanel.add(comboBoxEnemyCount);
		}
		{
			lblEvasionNotPossible = new JLabel("Bei mehr als 3 Gegnern ist das Ausweichen nicht mehr möglich!");
			lblEvasionNotPossible.setForeground(Color.RED);
			lblEvasionNotPossible.setFont(new Font("Lucida Grande", Font.BOLD, 13));
			lblEvasionNotPossible.setVisible(false);
			lblEvasionNotPossible.setHorizontalAlignment(SwingConstants.CENTER);
			lblEvasionNotPossible.setBounds(6, 44, 438, 16);
			contentPanel.add(lblEvasionNotPossible);
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

	public int showDialog() {
		setVisible(true);
        return state;
		
	}

}
