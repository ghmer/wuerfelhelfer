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
	
	private JTextField textField;
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
		contentPanel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(6, 5, 326, 26);
		textField.setFocusable(true);
		textField.requestFocusInWindow();
		textField.grabFocus();
		textField.getDocument().addDocumentListener(new DocumentListener() {
			
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
		contentPanel.add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox<>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
			}
		});
		comboBox.setModel(new DefaultComboBoxModel<AbilityTypeEnum>(AbilityTypeEnum.values()));
		comboBox.setSelectedIndex(0);
		comboBox.transferFocus();
		comboBox.setBounds(344, 6, 100, 27);
		contentPanel.add(comboBox);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(6, 36, 440, 12);
		contentPanel.add(separator);
		
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
				
				table.setBounds(6, 52, 438, 179);
				table.repaint();
				
				contentPanel.add((table));
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
