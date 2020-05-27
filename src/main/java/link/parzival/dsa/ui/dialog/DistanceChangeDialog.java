package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.Constants;
import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.WaffenObjekt;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class DistanceChangeDialog extends JDialog {

	private enum Hopser {
		verkürzen,verlängern
	}
	
	private static final long serialVersionUID 	= 9196060912407937075L;
	private JComboBox<Hopser> comboBoxDirection = new JComboBox<>();
	private final JPanel contentPanel	 		= new JPanel();
	private JComboBox<Integer> dkAenderung		= new JComboBox<>();
	private String rollCommand 					= null;
	private int state 							= Constants.DIALOG_CANCEL_STATE;

	/**
	 * @param hero the HeldenObjekt to set
	 * @param waffenObjekt the WaffenObjekt to set
	 */
	public DistanceChangeDialog(HeldenObjekt hero, WaffenObjekt waffenObjekt) {
		setBounds(100, 100, 450, 100);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setLocationRelativeTo(getParent());
		setFont(getParent().getFont());
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{125, 30, 64, 125, 0};
		gbl_contentPanel.rowHeights = new int[]{28, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		comboBoxDirection = new JComboBox<Hopser>();
		comboBoxDirection.setModel(new DefaultComboBoxModel<Hopser>(Hopser.values()));
		GridBagConstraints gbc_comboBoxDirection = new GridBagConstraints();
		gbc_comboBoxDirection.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxDirection.insets = new Insets(0, 0, 0, 5);
		gbc_comboBoxDirection.gridx = 0;
		gbc_comboBoxDirection.gridy = 0;
		contentPanel.add(comboBoxDirection, gbc_comboBoxDirection);
		
		JLabel lblNewLabel = new JLabel("um");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		contentPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		dkAenderung = new JComboBox<Integer>();
		dkAenderung.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2}));
		dkAenderung.setSelectedIndex(0);
		GridBagConstraints gbc_distanzklassenAenderung = new GridBagConstraints();
		gbc_distanzklassenAenderung.anchor = GridBagConstraints.WEST;
		gbc_distanzklassenAenderung.insets = new Insets(0, 0, 0, 5);
		gbc_distanzklassenAenderung.gridx = 2;
		gbc_distanzklassenAenderung.gridy = 0;
		contentPanel.add(dkAenderung, gbc_distanzklassenAenderung);
		
		JLabel lblNewLabel_1 = new JLabel("Distanzklassen");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.gridx = 3;
		gbc_lblNewLabel_1.gridy = 0;
		contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						boolean verdoppeln = ((int) dkAenderung.getSelectedItem() == 2) ? true : false;
						boolean verkuerzen = (((Hopser) comboBoxDirection.getSelectedItem()).equals(Hopser.verkürzen)) ? true: false;
						setRollCommand(DsaCalculatorUtil.getChangeDistanceEffectiveRoll(waffenObjekt, verdoppeln, verkuerzen));
						state = Constants.DIALOG_OK_STATE;
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
						state = Constants.DIALOG_CANCEL_STATE;
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
