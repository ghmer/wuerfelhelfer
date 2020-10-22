package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JCheckBox;
import java.awt.Insets;
import javax.swing.JSpinner;
import java.awt.Font;
import javax.swing.JSeparator;

public class KampfbedingungenDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = -6167824207259826983L;
    private final JPanel contentPanel = new JPanel();

    /**
     * Create the dialog.
     */
    public KampfbedingungenDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 459, 560);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{0, 20, 0, 0, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 20, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel lblSicht = new JLabel("Sicht");
            lblSicht.setFont(getFont().deriveFont(Font.BOLD, 13));
            GridBagConstraints gbc_lblSicht = new GridBagConstraints();
            gbc_lblSicht.anchor = GridBagConstraints.WEST;
            gbc_lblSicht.insets = new Insets(0, 0, 5, 5);
            gbc_lblSicht.gridx = 0;
            gbc_lblSicht.gridy = 0;
            contentPanel.add(lblSicht, gbc_lblSicht);
        }
        {
            JLabel lblNewLabel_1 = new JLabel("Position");
            lblNewLabel_1.setFont(getFont().deriveFont(Font.BOLD, 13));
            GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
            gbc_lblNewLabel_1.gridwidth = 2;
            gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
            gbc_lblNewLabel_1.gridx = 2;
            gbc_lblNewLabel_1.gridy = 0;
            contentPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);
        }
        {
            JCheckBox chckbxNewCheckBox = new JCheckBox("Mondlicht");
            GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
            gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox.gridx = 0;
            gbc_chckbxNewCheckBox.gridy = 1;
            contentPanel.add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
        }
        {
            JCheckBox chckbxNewCheckBox_11 = new JCheckBox("Gegner: am Boden liegend");
            GridBagConstraints gbc_chckbxNewCheckBox_11 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_11.gridwidth = 2;
            gbc_chckbxNewCheckBox_11.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_11.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_11.gridx = 2;
            gbc_chckbxNewCheckBox_11.gridy = 1;
            contentPanel.add(chckbxNewCheckBox_11, gbc_chckbxNewCheckBox_11);
        }
        {
            JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Sternlicht");
            GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_1.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_1.gridx = 0;
            gbc_chckbxNewCheckBox_1.gridy = 2;
            contentPanel.add(chckbxNewCheckBox_1, gbc_chckbxNewCheckBox_1);
        }
        {
            JCheckBox chckbxNewCheckBox_12 = new JCheckBox("Selbst: am Boden liegend");
            GridBagConstraints gbc_chckbxNewCheckBox_12 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_12.gridwidth = 2;
            gbc_chckbxNewCheckBox_12.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_12.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_12.gridx = 2;
            gbc_chckbxNewCheckBox_12.gridy = 2;
            contentPanel.add(chckbxNewCheckBox_12, gbc_chckbxNewCheckBox_12);
        }
        {
            JCheckBox chckbxNewCheckBox_2 = new JCheckBox("Dunkelheit");
            GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_2.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_2.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_2.gridx = 0;
            gbc_chckbxNewCheckBox_2.gridy = 3;
            contentPanel.add(chckbxNewCheckBox_2, gbc_chckbxNewCheckBox_2);
        }
        {
            JCheckBox chckbxNewCheckBox_13 = new JCheckBox("Gegner: kniend");
            GridBagConstraints gbc_chckbxNewCheckBox_13 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_13.gridwidth = 2;
            gbc_chckbxNewCheckBox_13.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_13.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_13.gridx = 2;
            gbc_chckbxNewCheckBox_13.gridy = 3;
            contentPanel.add(chckbxNewCheckBox_13, gbc_chckbxNewCheckBox_13);
        }
        {
            JCheckBox chckbxNewCheckBox_3 = new JCheckBox("unsichtbarer Gegner");
            GridBagConstraints gbc_chckbxNewCheckBox_3 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_3.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_3.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_3.gridx = 0;
            gbc_chckbxNewCheckBox_3.gridy = 4;
            contentPanel.add(chckbxNewCheckBox_3, gbc_chckbxNewCheckBox_3);
        }
        {
            JCheckBox chckbxNewCheckBox_14 = new JCheckBox("Selbst: kniend");
            GridBagConstraints gbc_chckbxNewCheckBox_14 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_14.gridwidth = 2;
            gbc_chckbxNewCheckBox_14.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_14.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_14.gridx = 2;
            gbc_chckbxNewCheckBox_14.gridy = 4;
            contentPanel.add(chckbxNewCheckBox_14, gbc_chckbxNewCheckBox_14);
        }
        {
            JCheckBox chckbxNewCheckBox_15 = new JCheckBox("Gegner: Fliegend");
            GridBagConstraints gbc_chckbxNewCheckBox_15 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_15.gridwidth = 2;
            gbc_chckbxNewCheckBox_15.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_15.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_15.gridx = 2;
            gbc_chckbxNewCheckBox_15.gridy = 5;
            contentPanel.add(chckbxNewCheckBox_15, gbc_chckbxNewCheckBox_15);
        }
        {
            JLabel lblNewLabel = new JLabel("Umgebung");
            lblNewLabel.setFont(getFont().deriveFont(Font.BOLD, 13));
            GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
            gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
            gbc_lblNewLabel.gridx = 0;
            gbc_lblNewLabel.gridy = 6;
            contentPanel.add(lblNewLabel, gbc_lblNewLabel);
        }
        {
            JLabel lblNewLabel_2 = new JLabel("sonstiges");
            lblNewLabel_2.setFont(getFont().deriveFont(Font.BOLD, 13));
            GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
            gbc_lblNewLabel_2.gridwidth = 2;
            gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
            gbc_lblNewLabel_2.gridx = 2;
            gbc_lblNewLabel_2.gridy = 6;
            contentPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);
        }
        {
            JCheckBox chckbxNewCheckBox_4 = new JCheckBox("Wasser: Knietief");
            GridBagConstraints gbc_chckbxNewCheckBox_4 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_4.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_4.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_4.gridx = 0;
            gbc_chckbxNewCheckBox_4.gridy = 7;
            contentPanel.add(chckbxNewCheckBox_4, gbc_chckbxNewCheckBox_4);
        }
        {
            JCheckBox chckbxNewCheckBox_16 = new JCheckBox("Kampf mit falscher Hand");
            GridBagConstraints gbc_chckbxNewCheckBox_16 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_16.gridwidth = 2;
            gbc_chckbxNewCheckBox_16.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_16.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_16.gridx = 2;
            gbc_chckbxNewCheckBox_16.gridy = 7;
            contentPanel.add(chckbxNewCheckBox_16, gbc_chckbxNewCheckBox_16);
        }
        {
            JCheckBox chckbxNewCheckBox_5 = new JCheckBox("Wasser: Hüfttief");
            GridBagConstraints gbc_chckbxNewCheckBox_5 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_5.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_5.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_5.gridx = 0;
            gbc_chckbxNewCheckBox_5.gridy = 8;
            contentPanel.add(chckbxNewCheckBox_5, gbc_chckbxNewCheckBox_5);
        }
        {
            JCheckBox chckbxNewCheckBox_17 = new JCheckBox("SF: Linkhand");
            GridBagConstraints gbc_chckbxNewCheckBox_17 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_17.gridwidth = 2;
            gbc_chckbxNewCheckBox_17.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_17.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_17.gridx = 2;
            gbc_chckbxNewCheckBox_17.gridy = 8;
            contentPanel.add(chckbxNewCheckBox_17, gbc_chckbxNewCheckBox_17);
        }
        {
            JCheckBox chckbxNewCheckBox_6 = new JCheckBox("Wasser: Schultertief");
            GridBagConstraints gbc_chckbxNewCheckBox_6 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_6.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_6.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_6.gridx = 0;
            gbc_chckbxNewCheckBox_6.gridy = 9;
            contentPanel.add(chckbxNewCheckBox_6, gbc_chckbxNewCheckBox_6);
        }
        {
            JCheckBox chckbxNewCheckBox_18 = new JCheckBox("SF: Beidhändig I");
            GridBagConstraints gbc_chckbxNewCheckBox_18 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_18.gridwidth = 2;
            gbc_chckbxNewCheckBox_18.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_18.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_18.gridx = 2;
            gbc_chckbxNewCheckBox_18.gridy = 9;
            contentPanel.add(chckbxNewCheckBox_18, gbc_chckbxNewCheckBox_18);
        }
        {
            JCheckBox chckbxNewCheckBox_7 = new JCheckBox("unter Wasser");
            GridBagConstraints gbc_chckbxNewCheckBox_7 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_7.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_7.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_7.gridx = 0;
            gbc_chckbxNewCheckBox_7.gridy = 10;
            contentPanel.add(chckbxNewCheckBox_7, gbc_chckbxNewCheckBox_7);
        }
        {
            JCheckBox chckbxNewCheckBox_19 = new JCheckBox("Verteidiger überrumpelt");
            GridBagConstraints gbc_chckbxNewCheckBox_19 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_19.gridwidth = 2;
            gbc_chckbxNewCheckBox_19.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_19.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_19.gridx = 2;
            gbc_chckbxNewCheckBox_19.gridy = 10;
            contentPanel.add(chckbxNewCheckBox_19, gbc_chckbxNewCheckBox_19);
        }
        {
            JCheckBox chckbxNewCheckBox_8 = new JCheckBox("eng: Lange Schwungwaffe");
            GridBagConstraints gbc_chckbxNewCheckBox_8 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_8.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_8.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_8.gridx = 0;
            gbc_chckbxNewCheckBox_8.gridy = 11;
            contentPanel.add(chckbxNewCheckBox_8, gbc_chckbxNewCheckBox_8);
        }
        {
            JCheckBox chckbxNewCheckBox_20 = new JCheckBox("Verteidiger schlafend");
            GridBagConstraints gbc_chckbxNewCheckBox_20 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_20.gridwidth = 2;
            gbc_chckbxNewCheckBox_20.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_20.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_20.gridx = 2;
            gbc_chckbxNewCheckBox_20.gridy = 11;
            contentPanel.add(chckbxNewCheckBox_20, gbc_chckbxNewCheckBox_20);
        }
        {
            JCheckBox chckbxNewCheckBox_9 = new JCheckBox("eng: Kurze Schwungwaffe");
            GridBagConstraints gbc_chckbxNewCheckBox_9 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_9.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_9.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_9.gridx = 0;
            gbc_chckbxNewCheckBox_9.gridy = 12;
            contentPanel.add(chckbxNewCheckBox_9, gbc_chckbxNewCheckBox_9);
        }
        {
            JCheckBox chckbxNewCheckBox_21 = new JCheckBox("Ziel vollkommen unbeweglich");
            GridBagConstraints gbc_chckbxNewCheckBox_21 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_21.gridwidth = 2;
            gbc_chckbxNewCheckBox_21.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_21.insets = new Insets(0, 0, 5, 0);
            gbc_chckbxNewCheckBox_21.gridx = 2;
            gbc_chckbxNewCheckBox_21.gridy = 12;
            contentPanel.add(chckbxNewCheckBox_21, gbc_chckbxNewCheckBox_21);
        }
        {
            JCheckBox chckbxNewCheckBox_10 = new JCheckBox("eng: Stangenwaffe");
            GridBagConstraints gbc_chckbxNewCheckBox_10 = new GridBagConstraints();
            gbc_chckbxNewCheckBox_10.anchor = GridBagConstraints.WEST;
            gbc_chckbxNewCheckBox_10.insets = new Insets(0, 0, 5, 5);
            gbc_chckbxNewCheckBox_10.gridx = 0;
            gbc_chckbxNewCheckBox_10.gridy = 13;
            contentPanel.add(chckbxNewCheckBox_10, gbc_chckbxNewCheckBox_10);
        }
        {
            JSpinner spinner = new JSpinner();
            GridBagConstraints gbc_spinner = new GridBagConstraints();
            gbc_spinner.anchor = GridBagConstraints.WEST;
            gbc_spinner.insets = new Insets(0, 0, 5, 5);
            gbc_spinner.gridx = 2;
            gbc_spinner.gridy = 13;
            contentPanel.add(spinner, gbc_spinner);
        }
        {
            JLabel lblNewLabel_3 = new JLabel("Gegner in Überzahl");
            GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
            gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
            gbc_lblNewLabel_3.gridx = 3;
            gbc_lblNewLabel_3.gridy = 13;
            contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
        }
        {
            JSpinner spinner = new JSpinner();
            GridBagConstraints gbc_spinner = new GridBagConstraints();
            gbc_spinner.insets = new Insets(0, 0, 5, 5);
            gbc_spinner.gridx = 2;
            gbc_spinner.gridy = 14;
            contentPanel.add(spinner, gbc_spinner);
        }
        {
            JLabel lblNewLabel_3 = new JLabel("Freunde in Überzahl");
            GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
            gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
            gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel_3.gridx = 3;
            gbc_lblNewLabel_3.gridy = 14;
            contentPanel.add(lblNewLabel_3, gbc_lblNewLabel_3);
        }
        {
            JPanel panel = new JPanel();
            GridBagConstraints gbc_panel = new GridBagConstraints();
            gbc_panel.gridwidth = 4;
            gbc_panel.insets = new Insets(0, 0, 5, 0);
            gbc_panel.anchor = GridBagConstraints.NORTH;
            gbc_panel.fill = GridBagConstraints.HORIZONTAL;
            gbc_panel.gridx = 0;
            gbc_panel.gridy = 15;
            contentPanel.add(panel, gbc_panel);
            panel.setLayout(new BorderLayout(0, 0));
            JSeparator separator = new JSeparator();
            panel.add(separator, BorderLayout.NORTH);
        }
        {
            JLabel lblNewLabel_4 = new JLabel("Gesamterschwernis:");
            lblNewLabel_4.setFont(getFont().deriveFont(Font.BOLD, 13));
            GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
            gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
            gbc_lblNewLabel_4.gridx = 0;
            gbc_lblNewLabel_4.gridy = 16;
            contentPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);
        }
        {
            JLabel lblNewLabel_5 = new JLabel("0");
            lblNewLabel_5.setFont(getFont().deriveFont(Font.BOLD, 13));
            GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
            gbc_lblNewLabel_5.anchor = GridBagConstraints.WEST;
            gbc_lblNewLabel_5.insets = new Insets(0, 0, 0, 5);
            gbc_lblNewLabel_5.gridx = 1;
            gbc_lblNewLabel_5.gridy = 16;
            contentPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
        
        pack();
    }
}
