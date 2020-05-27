package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

public class LizenzDialog extends JDialog {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$
	public enum License {
		Ubuntu,GPL,Apache
	}
	private static final long serialVersionUID 	= -789044538891465510L;
	private final JPanel contentPanel 			= new JPanel();
	
	/**
	 * @param license the type of license to show
	 */
	public LizenzDialog(License license) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		{
			GridBagLayout gbl_contentPanel = new GridBagLayout();
			gbl_contentPanel.columnWidths = new int[]{440, 0};
			gbl_contentPanel.rowHeights = new int[]{20, 0};
			gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
			gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
			contentPanel.setLayout(gbl_contentPanel);
		}
		JTextArea textArea = new JTextArea(getLizenz(license));
		textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setViewportView(textArea);
		scrollPane.getPreferredSize();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPanel.add(scrollPane, gbc_scrollPane);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton(BUNDLE.getString("LizenzDialog.okButton.text")); //$NON-NLS-1$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand(BUNDLE.getString("LizenzDialog.okButton.actionCommand")); //$NON-NLS-1$
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	/**
	 * @param license which license to present
	 * @return the license text
	 */
	@SuppressWarnings("resource")
	private String getLizenz(License license) {
		String text = null;
		switch(license) {
			case Apache: {
				text = new Scanner(LizenzDialog.class.getResourceAsStream("/LICENCE_Flatlaf_Apache.txt"), "UTF-8").useDelimiter("\\A").next();
				break;
			}
			case GPL: 	 {
				text = new Scanner(LizenzDialog.class.getResourceAsStream("/LICENCE_Friedolin_Font.txt"), "UTF-8").useDelimiter("\\A").next();
				break;
			}
			case Ubuntu: {
				text = new Scanner(LizenzDialog.class.getResourceAsStream("/LICENCE_Ubuntu_Font.txt"), "UTF-8").useDelimiter("\\A").next();
				break;
			}	
		}
		
		return text;
	}

}
