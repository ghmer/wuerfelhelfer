package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.util.ResourceBundle;

public class PatzerBeschreibungDialog extends JDialog {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$

	/**
	 * 
	 */
	private static final long serialVersionUID = -6015712592515827295L;
	private final JPanel contentPanel = new JPanel();
	/**
	 * Create the dialog.
	 */
	public PatzerBeschreibungDialog(String patzerBeschreibung) {
		setBounds(100, 100, 300, 275);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JTextArea textAreaPatzerBeschreibung = new JTextArea(patzerBeschreibung);
			textAreaPatzerBeschreibung.setEnabled(false);
			textAreaPatzerBeschreibung.setEditable(false);
			textAreaPatzerBeschreibung.setWrapStyleWord(true);
			textAreaPatzerBeschreibung.setLineWrap(true);
			textAreaPatzerBeschreibung.setRows(5);
			GridBagConstraints gbc_textAreaPatzerBeschreibung = new GridBagConstraints();
			gbc_textAreaPatzerBeschreibung.fill = GridBagConstraints.BOTH;
			gbc_textAreaPatzerBeschreibung.gridx = 0;
			gbc_textAreaPatzerBeschreibung.gridy = 0;
			contentPanel.add(textAreaPatzerBeschreibung, gbc_textAreaPatzerBeschreibung);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(BUNDLE.getString("PatzerBeschreibungDialog.okButton.text")); //$NON-NLS-1$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand(BUNDLE.getString("PatzerBeschreibungDialog.okButton.actionCommand")); //$NON-NLS-1$
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
