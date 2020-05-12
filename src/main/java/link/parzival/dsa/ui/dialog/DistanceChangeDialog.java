package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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

	/**
	 * 
	 */
	private static final long serialVersionUID = 9196060912407937075L;

	private final JPanel contentPanel = new JPanel();
	
	public static final int OK_STATE = 0;
	public static final int CANCEL_STATE = 1;
	
	private int state = CANCEL_STATE;
	
	private String rollCommand = null;

	private JComboBox<Integer> distanzklassenAenderung;

	private JComboBox<Hopser> comboBoxDirection;
	
	private enum Hopser {
		verkürzen,verlängern
	}

	/**
	 * @param hero the HeldenObjekt to set
	 * @param waffenObjekt the WaffenObjekt to set
	 */
	public DistanceChangeDialog(HeldenObjekt hero, WaffenObjekt waffenObjekt) {
		setBounds(100, 100, 400, 100);
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
		gbc_comboBoxDirection.anchor = GridBagConstraints.SOUTH;
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
		
		distanzklassenAenderung = new JComboBox<Integer>();
		distanzklassenAenderung.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2}));
		distanzklassenAenderung.setSelectedIndex(0);
		GridBagConstraints gbc_distanzklassenAenderung = new GridBagConstraints();
		gbc_distanzklassenAenderung.anchor = GridBagConstraints.NORTHWEST;
		gbc_distanzklassenAenderung.insets = new Insets(0, 0, 0, 5);
		gbc_distanzklassenAenderung.gridx = 2;
		gbc_distanzklassenAenderung.gridy = 0;
		contentPanel.add(distanzklassenAenderung, gbc_distanzklassenAenderung);
		
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
						boolean verdoppeln = ((int) distanzklassenAenderung.getSelectedItem() == 2) ? true : false;
						boolean verkuerzen = (((Hopser) comboBoxDirection.getSelectedItem()).equals(Hopser.verkürzen)) ? true: false;
						setRollCommand(DsaCalculatorUtil.getChangeDistanceEffectiveRoll(waffenObjekt, verdoppeln, verkuerzen));
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
	 * @return the state of the dialog
	 */
	public int showDialog() {
		setVisible(true);
		return state;
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
}
