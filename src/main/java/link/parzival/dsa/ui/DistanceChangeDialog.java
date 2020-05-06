package link.parzival.dsa.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.DsaCalculatorUtil;
import link.parzival.dsa.HeldenObjekt;
import link.parzival.dsa.WaffenObjekt;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		contentPanel.setLayout(null);
		
		comboBoxDirection = new JComboBox<Hopser>();
		comboBoxDirection.setModel(new DefaultComboBoxModel<Hopser>(Hopser.values()));
		comboBoxDirection.setBounds(6, 6, 125, 27);
		contentPanel.add(comboBoxDirection);
		
		JLabel lblNewLabel = new JLabel("um");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(143, 10, 30, 16);
		contentPanel.add(lblNewLabel);
		
		distanzklassenAenderung = new JComboBox<Integer>();
		distanzklassenAenderung.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {1, 2}));
		distanzklassenAenderung.setSelectedIndex(0);
		distanzklassenAenderung.setBounds(185, 5, 64, 26);
		contentPanel.add(distanzklassenAenderung);
		
		JLabel lblNewLabel_1 = new JLabel("Distanzklassen");
		lblNewLabel_1.setBounds(269, 10, 125, 16);
		contentPanel.add(lblNewLabel_1);
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
