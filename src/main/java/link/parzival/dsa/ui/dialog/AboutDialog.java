
package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.Konstanten;
import link.parzival.dsa.object.enumeration.LizenzTypEnum;
import link.parzival.dsa.ui.UIHelfer;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ResourceBundle;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JSeparator;

public class AboutDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID  = -7021894361816829788L;
    private static final ResourceBundle BUNDLE  = ResourceBundle.getBundle("link.parzival.dsa.ui.messages"); //$NON-NLS-1$
    private final JPanel contentPanel           = new JPanel();

    public AboutDialog() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(100, 100, 600, 550);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);

        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[] { 100, 0, 0 };
        gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 20, 0 };
        gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
        contentPanel.setLayout(gbl_contentPanel);

        JTextArea textArea = new JTextArea(UIHelfer.getLizenz(LizenzTypEnum.MIT));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setViewportView(textArea);
        scrollPane.getPreferredSize();

        JLabel lblHeaderWithVersion = new JLabel(String.format("%s v.%s", //$NON-NLS-1$
                BUNDLE.getString("AboutDialog.lblHeaderWithVersion.text"), Konstanten.VERSION_EXTERNAL));
        lblHeaderWithVersion.setFont(getFont().deriveFont(Font.PLAIN, 20));
        GridBagConstraints gbc_lblHeaderWithVersion = new GridBagConstraints();
        gbc_lblHeaderWithVersion.gridwidth = 2;
        gbc_lblHeaderWithVersion.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblHeaderWithVersion.insets = new Insets(0, 0, 5, 0);
        gbc_lblHeaderWithVersion.gridx = 0;
        gbc_lblHeaderWithVersion.gridy = 0;
        contentPanel.add(lblHeaderWithVersion, gbc_lblHeaderWithVersion);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.NORTH;
        gbc_panel.gridwidth = 2;
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 1;
        contentPanel.add(panel, gbc_panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        JSeparator separator = new JSeparator();
        panel.add(separator);

        JLabel lblNewLabel_1 = new JLabel(BUNDLE.getString("AboutDialog.lblNewLabel_1.text")); //$NON-NLS-1$
        lblNewLabel_1.setFont(getFont());
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 2;
        contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

        JLabel lblNewLabel_3 = new JLabel(BUNDLE.getString("AboutDialog.lblNewLabel_3.text")); //$NON-NLS-1$
        lblNewLabel_3.setFont(getFont());
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_3.gridx = 1;
        gbc_lblNewLabel_3.gridy = 2;
        contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);

        JLabel lblNewLabel_2 = new JLabel(BUNDLE.getString("AboutDialog.lblNewLabel_2.text")); //$NON-NLS-1$
        lblNewLabel_2.setFont(getFont());
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 3;
        contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

        JLabel lblNewLabel_4 = new JLabel(BUNDLE.getString("AboutDialog.lblNewLabel_4.text")); //$NON-NLS-1$
        lblNewLabel_4.setFont(getFont());
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_4.gridx = 1;
        gbc_lblNewLabel_4.gridy = 3;
        contentPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 4;
        contentPanel.add(scrollPane, gbc_scrollPane);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        
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
