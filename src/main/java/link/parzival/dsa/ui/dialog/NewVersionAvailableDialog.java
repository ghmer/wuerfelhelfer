package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.Constants;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NewVersionAvailableDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4387568017758375162L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	public NewVersionAvailableDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{194, 0};
		gbl_contentPanel.rowHeights = new int[]{16, 16, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblTop = new JLabel("Eine neue Version kann auf");
			lblTop.setHorizontalAlignment(SwingConstants.CENTER);
			GridBagConstraints gbc_lblTop = new GridBagConstraints();
			gbc_lblTop.gridheight = 2;
			gbc_lblTop.anchor = GridBagConstraints.SOUTH;
			gbc_lblTop.insets = new Insets(0, 0, 5, 0);
			gbc_lblTop.gridx = 0;
			gbc_lblTop.gridy = 0;
			contentPanel.add(lblTop, gbc_lblTop);
		}
		{
			JLabel lblLinkNewVersion = new JLabel(Constants.APP_URL);
			lblLinkNewVersion.setCursor(new Cursor(Cursor.HAND_CURSOR));
			lblLinkNewVersion.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					try {
						Desktop.getDesktop().browse(new URI(Constants.APP_URL));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			lblLinkNewVersion.setForeground(Color.BLUE.darker());
			GridBagConstraints gbc_lblLinkNewVersion = new GridBagConstraints();
			gbc_lblLinkNewVersion.insets = new Insets(0, 0, 5, 0);
			gbc_lblLinkNewVersion.gridx = 0;
			gbc_lblLinkNewVersion.gridy = 2;
			contentPanel.add(lblLinkNewVersion, gbc_lblLinkNewVersion);
		}
		{
			JLabel lblBottom = new JLabel("heruntergeladen werden.");
			GridBagConstraints gbc_lblBottom = new GridBagConstraints();
			gbc_lblBottom.anchor = GridBagConstraints.NORTH;
			gbc_lblBottom.gridx = 0;
			gbc_lblBottom.gridy = 3;
			contentPanel.add(lblBottom, gbc_lblBottom);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

}
