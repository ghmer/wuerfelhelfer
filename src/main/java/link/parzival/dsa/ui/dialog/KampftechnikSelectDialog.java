package link.parzival.dsa.ui.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import link.parzival.dsa.object.KampftechnikObjekt;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class KampftechnikSelectDialog extends JDialog {
	private static final long serialVersionUID = -6188151027743634074L;
	private final JPanel contentPanel = new JPanel();
	private ButtonGroup buttonGroup = new ButtonGroup();
	
	private String selectedElement				= null;
	public static final int CANCEL_STATE 		= 1;
	public static final int OK_STATE 			= 0;
	private int state 							= CANCEL_STATE;

	/**
	 * Create the dialog.
	 */
	public KampftechnikSelectDialog(List<KampftechnikObjekt> kampftechniken) {
		setBounds(100, 100, 450, 300);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		{
			boolean firstElement = true;
			for(KampftechnikObjekt kampftechnik : kampftechniken) {
				JRadioButton radioButton = new JRadioButton(kampftechnik.getName());
				radioButton.setHorizontalAlignment(SwingConstants.LEFT);
				radioButton.setSelected(firstElement);
				firstElement = false;
				buttonGroup.add(radioButton);
				contentPanel.add(radioButton);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Enumeration<AbstractButton> buttonEnum = buttonGroup.getElements();
						while(buttonEnum.hasMoreElements()) {
							JRadioButton button = (JRadioButton) buttonEnum.nextElement();
							if(button.isSelected()) {
								setSelectedElement(button.getText());
								break;
							}
						}
						
						state = OK_STATE;
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	/**
	 * @return the selectedElement
	 */
	public String getSelectedElement() {
		return selectedElement;
	}

	/**
	 * @param selectedElement the selectedElement to set
	 */
	public void setSelectedElement(String selectedElement) {
		this.selectedElement = selectedElement;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * @return the state of the dialog
	 */
	public int showDialog() {
		setVisible(true);
        return state;	
	}

}
