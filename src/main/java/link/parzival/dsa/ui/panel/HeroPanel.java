package link.parzival.dsa.ui.panel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeListener;

import link.parzival.dsa.object.HeldenObjekt;

import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HeroPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4145721306607452199L;
	private JLabel lblHeroName;
	private JSpinner spinnerLebensenergie;
	private JSpinner spinnerAstralenergie;
	private JSpinner spinnerBehinderung;
	private JSpinner spinnerAusdauer;
	
	/**
	 * @param hero the HeldenObjekt to use
	 */
	public HeroPanel(HeldenObjekt hero) {
		setLayout(null);
		
		lblHeroName = new JLabel(hero.getName());
		lblHeroName.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeroName.setFont(new Font("Friedolin", Font.PLAIN, 36));
		lblHeroName.setBounds(6, 6, 658, 40);
		add(lblHeroName);
		
		JLabel lblMut = new JLabel("Mut");
		lblMut.setHorizontalAlignment(SwingConstants.TRAILING);
		lblMut.setBounds(6, 64, 120, 16);
		add(lblMut);
		
		JLabel lblKlugheit = new JLabel("Klugheit");
		lblKlugheit.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKlugheit.setBounds(6, 92, 120, 16);
		add(lblKlugheit);
		
		JLabel lblIntuition = new JLabel("Intuition");
		lblIntuition.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIntuition.setBounds(6, 120, 120, 16);
		add(lblIntuition);
		
		JLabel lblCharisma = new JLabel("Charisma");
		lblCharisma.setHorizontalAlignment(SwingConstants.TRAILING);
		lblCharisma.setBounds(6, 148, 120, 16);
		add(lblCharisma);
		
		JLabel lblFingerfertigkeit = new JLabel("Fingerfertigkeit");
		lblFingerfertigkeit.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFingerfertigkeit.setBounds(210, 64, 120, 16);
		add(lblFingerfertigkeit);
		
		JLabel lblGewandheit = new JLabel("Gewandheit");
		lblGewandheit.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGewandheit.setBounds(210, 92, 120, 16);
		add(lblGewandheit);
		
		JLabel lblKonstitution = new JLabel("Konstitution");
		lblKonstitution.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKonstitution.setBounds(210, 120, 120, 16);
		add(lblKonstitution);
		
		JLabel lblKoerperkraft = new JLabel("Körperkraft");
		lblKoerperkraft.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKoerperkraft.setBounds(210, 148, 120, 16);
		add(lblKoerperkraft);
		
		JLabel lblLebensenergie = new JLabel("Lebensenergie");
		lblLebensenergie.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLebensenergie.setBounds(414, 64, 120, 16);
		add(lblLebensenergie);
		
		spinnerLebensenergie = new JSpinner();
		((DefaultEditor) spinnerLebensenergie.getEditor()).getTextField().setEditable(false);
		spinnerLebensenergie.setValue(Integer.valueOf(hero.getLebensenergie()));
		spinnerLebensenergie.setBounds(546, 59, 60, 26);
		add(spinnerLebensenergie);
		
		JLabel lblAstralenergie = new JLabel("Astralenergie");
		lblAstralenergie.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAstralenergie.setBounds(414, 92, 120, 16);
		add(lblAstralenergie);
		
		spinnerAstralenergie = new JSpinner();
		((DefaultEditor) spinnerAstralenergie.getEditor()).getTextField().setEditable(false);
		spinnerAstralenergie.setValue(Integer.valueOf(hero.getAstralenergie()));
		spinnerAstralenergie.setBounds(546, 87, 60, 26);
		add(spinnerAstralenergie);
		
		JLabel lblBehinderung = new JLabel("Behinderung");
		lblBehinderung.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBehinderung.setBounds(414, 120, 120, 16);
		add(lblBehinderung);
		
		spinnerBehinderung = new JSpinner();
		((DefaultEditor) spinnerBehinderung.getEditor()).getTextField().setEditable(false);
		spinnerBehinderung.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int newVal = (Integer)spinnerBehinderung.getValue();
				hero.setBehinderung(newVal);
			}
		});
		spinnerBehinderung.setValue(Integer.valueOf(hero.getBehinderung()));
		spinnerBehinderung.setBounds(546, 115, 60, 26);
		add(spinnerBehinderung);
		
		JLabel lblAusdauer = new JLabel("Ausdauer");
		lblAusdauer.setHorizontalAlignment(SwingConstants.TRAILING);
		lblAusdauer.setBounds(414, 148, 120, 16);
		add(lblAusdauer);
		
		spinnerAusdauer = new JSpinner();
		((DefaultEditor) spinnerAusdauer.getEditor()).getTextField().setEditable(false);
		spinnerAusdauer.setValue(Integer.valueOf(hero.getAusdauer()));
		spinnerAusdauer.setBounds(546, 143, 60, 26);
		add(spinnerAusdauer);
		
		JButton btnMut = new JButton(String.valueOf(hero.getMut()));
		btnMut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Mut", hero.getMut()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnMut.setBounds(138, 59, 60, 29);
		add(btnMut);
		
		JButton btnKlugheit = new JButton(String.valueOf(hero.getKlugheit()));
		btnKlugheit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Klugheit", hero.getKlugheit()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnKlugheit.setBounds(138, 87, 60, 29);
		add(btnKlugheit);
		
		JButton btnIntuition = new JButton(String.valueOf(hero.getIntuition()));
		btnIntuition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Intuition", hero.getIntuition()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnIntuition.setBounds(138, 115, 60, 29);
		add(btnIntuition);
		
		JButton btnCharisma = new JButton(String.valueOf(hero.getCharisma()));
		btnCharisma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Charisma", hero.getCharisma()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnCharisma.setBounds(138, 143, 60, 29);
		add(btnCharisma);
		
		JButton btnFingerFertigkeit = new JButton(String.valueOf(hero.getFingerfertigkeit()));
		btnFingerFertigkeit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Fingerfertigkeit", hero.getFingerfertigkeit()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnFingerFertigkeit.setBounds(342, 58, 60, 29);
		add(btnFingerFertigkeit);
		
		JButton btnGewandtheit = new JButton(String.valueOf(hero.getGewandtheit()));
		btnGewandtheit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Gewandtheit", hero.getGewandtheit()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnGewandtheit.setBounds(342, 87, 60, 29);
		add(btnGewandtheit);
		
		JButton btnKonstitution = new JButton(String.valueOf(hero.getKonstitution()));
		btnKonstitution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Konstitution", hero.getKonstitution()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnKonstitution.setBounds(342, 115, 60, 29);
		add(btnKonstitution);
		
		JButton btnKoerperkraft = new JButton(String.valueOf(hero.getKoerperkraft()));
		btnKoerperkraft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Koerperkraft", hero.getKoerperkraft()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		btnKoerperkraft.setBounds(342, 143, 60, 29);
		add(btnKoerperkraft);
	}
}
