package link.parzival.dsa.ui;

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

public class LizenzDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -789044538891465510L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	public LizenzDialog() {
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
		JTextArea textArea = new JTextArea(getLizenz());
		//textArea.setText(getLizenz());
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
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	@SuppressWarnings("resource")
	private String getLizenz() {
		String text = new Scanner(LizenzDialog.class.getResourceAsStream("/LICENCE.txt"), "UTF-8").useDelimiter("\\A").next();
		
		return text;
	}

}
