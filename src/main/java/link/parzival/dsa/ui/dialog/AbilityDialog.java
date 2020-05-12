package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;

import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class AbilityDialog extends JDialog {

	private enum AbilityTypeEnum {
		Talent, Magie
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 5001756584965996173L;
	public static final int OK_STATE = 0;
	public static final int CANCEL_STATE = 1;
	private final JPanel contentPanel = new JPanel();
	
	private JTextField textFieldSearch;
    private JTable table = null;
    private int state = CANCEL_STATE;
    
    
    private String selectedAbilityName = null;
	private JComboBox<AbilityTypeEnum> comboBox;

	/**
	 * @param hero the HeldenObjekt to use
	 */
	public AbilityDialog(HeldenObjekt hero) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 450, 298);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{326, 102, 0};
		gbl_contentPanel.rowHeights = new int[]{28, 0, 12, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		
		textFieldSearch = new JTextField();
		textFieldSearch.setFocusable(true);
		textFieldSearch.requestFocusInWindow();
		textFieldSearch.grabFocus();
		textFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				generateTable(hero, e);
			}
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				generateTable(hero, e);
				
			}
		});
		GridBagConstraints gbc_textFieldSearch = new GridBagConstraints();
		gbc_textFieldSearch.anchor = GridBagConstraints.NORTH;
		gbc_textFieldSearch.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldSearch.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldSearch.gridx = 0;
		gbc_textFieldSearch.gridy = 0;
		contentPanel.add(textFieldSearch, gbc_textFieldSearch);
		textFieldSearch.setColumns(10);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldSearch.setText("");
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<AbilityTypeEnum>(AbilityTypeEnum.values()));
		comboBox.setSelectedIndex(0);
		comboBox.transferFocus();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.anchor = GridBagConstraints.SOUTH;
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		contentPanel.add(comboBox, gbc_comboBox);
		
		JSeparator separator = new JSeparator();
		GridBagConstraints gbc_separator = new GridBagConstraints();
		gbc_separator.insets = new Insets(0, 0, 5, 0);
		gbc_separator.fill = GridBagConstraints.BOTH;
		gbc_separator.gridwidth = 2;
		gbc_separator.gridx = 0;
		gbc_separator.gridy = 1;
		contentPanel.add(separator, gbc_separator);
		
		table = new JTable();
		GridBagConstraints gbc_table_1 = new GridBagConstraints();
		gbc_table_1.gridwidth = 2;
		gbc_table_1.insets = new Insets(0, 0, 0, 5);
		gbc_table_1.fill = GridBagConstraints.BOTH;
		gbc_table_1.gridx = 0;
		gbc_table_1.gridy = 2;
		contentPanel.add(table, gbc_table_1);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cancelButton = new JButton("Abbruch");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	
	
	private void generateTable(HeldenObjekt hero, DocumentEvent e) {
		Document document = e.getDocument();
		String text = null;
		try {
			text = document.getText(0, document.getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		
		if(text != null && text.length() > 0) {
			List<TalentObjekt> talente = hero.getTalente();
			List<TalentObjekt> zauber  = hero.getZauber();
			List<TalentObjekt> matches = new ArrayList<>();
			
			AbilityTypeEnum chosenEnum = (AbilityTypeEnum)comboBox.getSelectedItem();
			
			if(chosenEnum == AbilityTypeEnum.Talent) {
				for(TalentObjekt objekt : talente) {
					if(objekt.getName().toLowerCase().startsWith(text.toLowerCase())) {
						matches.add(objekt);
					}
				}
			} else {
				for(TalentObjekt objekt : zauber) {
					if(objekt.getName().toLowerCase().startsWith(text.toLowerCase())) {
						matches.add(objekt);
					}
				}
			}
			
			if(matches.size() > 0) {
				String[][] tableRows = new String[matches.size()][4];
				for(int i = 0; i < matches.size(); i++) {
					TalentObjekt objekt = matches.get(i);
					tableRows[i] = new String[]{
							objekt.getName(), 
							String.valueOf(objekt.getTalentwert()), 
							String.format("(%s/%s/%s)", objekt.getProbenTalent1(), objekt.getProbenTalent2(), objekt.getProbenTalent3()),
							objekt.getBe()};
				}
				
				if(this.table != null) {
					contentPanel.remove(this.table);
				}
				
				String[] columnNames = {"Name des Talents/Zaubers", "TaW", "Probe", "BE"};
				JTable table = new JTable(tableRows, columnNames) {

					private static final long serialVersionUID = -5753532534134796588L;

					@Override
					public boolean isCellEditable(int row, int column) {                
		                return false;               
					};
				};
								
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.addMouseListener(new MouseAdapter() {
				    public void mousePressed(MouseEvent mouseEvent) {
				        JTable sourceTable 	= (JTable) mouseEvent.getSource();
				        Point point 		= mouseEvent.getPoint();
				        int row 			= sourceTable.rowAtPoint(point);
				        if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
				        	
				        	selectedAbilityName = (String) sourceTable.getValueAt(row, 0);				        	
				        	
				            state = OK_STATE;
				            dispose();
				        }
				    }
				});
				
				table.setBorder(new LineBorder(new Color(0, 0, 0)));

				GridBagConstraints gbc_table_1 = new GridBagConstraints();
				gbc_table_1.gridwidth = 2;
				gbc_table_1.insets = new Insets(0, 0, 0, 5);
				gbc_table_1.fill = GridBagConstraints.BOTH;
				gbc_table_1.gridx = 0;
				gbc_table_1.gridy = 2;
				table.repaint();
				
				contentPanel.add(table, gbc_table_1);
				contentPanel.updateUI();
				this.table = table;
			}
			
		}
	}
	

	public String getSelectedAbility() {
		return selectedAbilityName;
	}

	public int showDialog() {
		setVisible(true);
        return state;
		
	}
}
