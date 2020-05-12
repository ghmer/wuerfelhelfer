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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

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
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{25, 120, 60, 120, 60, 120, 50, 25, 0};
		gridBagLayout.rowHeights = new int[]{40, 29, 21, 21, 24, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnIntuition = new JButton(String.valueOf(hero.getIntuition()));
		btnIntuition.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Intuition", hero.getIntuition()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		
		spinnerLebensenergie = new JSpinner();
		((DefaultEditor) spinnerLebensenergie.getEditor()).getTextField().setEditable(false);
		
		JButton btnMut = new JButton(String.valueOf(hero.getMut()));
		btnMut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Mut", hero.getMut()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		
		lblHeroName = new JLabel(hero.getName());
		lblHeroName.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeroName.setFont(new Font("Friedolin", Font.PLAIN, 36));
		GridBagConstraints gbc_lblHeroName = new GridBagConstraints();
		gbc_lblHeroName.fill = GridBagConstraints.BOTH;
		gbc_lblHeroName.insets = new Insets(0, 0, 5, 5);
		gbc_lblHeroName.gridwidth = 8;
		gbc_lblHeroName.gridx = 0;
		gbc_lblHeroName.gridy = 0;
		add(lblHeroName, gbc_lblHeroName);
		
		JLabel lblMut = new JLabel("Mut");
		lblMut.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblMut = new GridBagConstraints();
		gbc_lblMut.anchor = GridBagConstraints.EAST;
		gbc_lblMut.insets = new Insets(0, 0, 5, 5);
		gbc_lblMut.gridx = 1;
		gbc_lblMut.gridy = 1;
		add(lblMut, gbc_lblMut);
		GridBagConstraints gbc_btnMut = new GridBagConstraints();
		gbc_btnMut.anchor = GridBagConstraints.SOUTH;
		gbc_btnMut.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnMut.insets = new Insets(0, 0, 5, 5);
		gbc_btnMut.gridx = 2;
		gbc_btnMut.gridy = 1;
		add(btnMut, gbc_btnMut);
		
		JButton btnFingerFertigkeit = new JButton(String.valueOf(hero.getFingerfertigkeit()));
		btnFingerFertigkeit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Fingerfertigkeit", hero.getFingerfertigkeit()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		
		JLabel lblFingerfertigkeit = new JLabel("Fingerfertigkeit");
		lblFingerfertigkeit.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblFingerfertigkeit = new GridBagConstraints();
		gbc_lblFingerfertigkeit.anchor = GridBagConstraints.EAST;
		gbc_lblFingerfertigkeit.insets = new Insets(0, 0, 5, 5);
		gbc_lblFingerfertigkeit.gridx = 3;
		gbc_lblFingerfertigkeit.gridy = 1;
		add(lblFingerfertigkeit, gbc_lblFingerfertigkeit);
		GridBagConstraints gbc_btnFingerFertigkeit = new GridBagConstraints();
		gbc_btnFingerFertigkeit.anchor = GridBagConstraints.NORTH;
		gbc_btnFingerFertigkeit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFingerFertigkeit.insets = new Insets(0, 0, 5, 5);
		gbc_btnFingerFertigkeit.gridx = 4;
		gbc_btnFingerFertigkeit.gridy = 1;
		add(btnFingerFertigkeit, gbc_btnFingerFertigkeit);
		
		JLabel lblLebensenergie = new JLabel("Lebensenergie");
		lblLebensenergie.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblLebensenergie = new GridBagConstraints();
		gbc_lblLebensenergie.anchor = GridBagConstraints.EAST;
		gbc_lblLebensenergie.insets = new Insets(0, 0, 5, 5);
		gbc_lblLebensenergie.gridx = 5;
		gbc_lblLebensenergie.gridy = 1;
		add(lblLebensenergie, gbc_lblLebensenergie);
		spinnerLebensenergie.setValue(Integer.valueOf(hero.getLebensenergie()));
		GridBagConstraints gbc_spinnerLebensenergie = new GridBagConstraints();
		gbc_spinnerLebensenergie.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerLebensenergie.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerLebensenergie.gridx = 6;
		gbc_spinnerLebensenergie.gridy = 1;
		add(spinnerLebensenergie, gbc_spinnerLebensenergie);
		
		JButton btnKlugheit = new JButton(String.valueOf(hero.getKlugheit()));
		btnKlugheit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Klugheit", hero.getKlugheit()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		GridBagConstraints gbc_btnKlugheit = new GridBagConstraints();
		gbc_btnKlugheit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKlugheit.insets = new Insets(0, 0, 5, 5);
		gbc_btnKlugheit.gridx = 2;
		gbc_btnKlugheit.gridy = 2;
		add(btnKlugheit, gbc_btnKlugheit);
		
		spinnerAstralenergie = new JSpinner();
		((DefaultEditor) spinnerAstralenergie.getEditor()).getTextField().setEditable(false);
		
		JButton btnGewandtheit = new JButton(String.valueOf(hero.getGewandtheit()));
		btnGewandtheit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Gewandtheit", hero.getGewandtheit()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		GridBagConstraints gbc_btnGewandtheit = new GridBagConstraints();
		gbc_btnGewandtheit.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnGewandtheit.insets = new Insets(0, 0, 5, 5);
		gbc_btnGewandtheit.gridx = 4;
		gbc_btnGewandtheit.gridy = 2;
		add(btnGewandtheit, gbc_btnGewandtheit);
		spinnerAstralenergie.setValue(Integer.valueOf(hero.getAstralenergie()));
		GridBagConstraints gbc_spinnerAstralenergie = new GridBagConstraints();
		gbc_spinnerAstralenergie.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerAstralenergie.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerAstralenergie.gridx = 6;
		gbc_spinnerAstralenergie.gridy = 2;
		add(spinnerAstralenergie, gbc_spinnerAstralenergie);
		
		JLabel lblKlugheit = new JLabel("Klugheit");
		lblKlugheit.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblKlugheit = new GridBagConstraints();
		gbc_lblKlugheit.anchor = GridBagConstraints.EAST;
		gbc_lblKlugheit.insets = new Insets(0, 0, 5, 5);
		gbc_lblKlugheit.gridx = 1;
		gbc_lblKlugheit.gridy = 2;
		add(lblKlugheit, gbc_lblKlugheit);
		
		JLabel lblGewandheit = new JLabel("Gewandheit");
		lblGewandheit.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblGewandheit = new GridBagConstraints();
		gbc_lblGewandheit.anchor = GridBagConstraints.EAST;
		gbc_lblGewandheit.insets = new Insets(0, 0, 5, 5);
		gbc_lblGewandheit.gridx = 3;
		gbc_lblGewandheit.gridy = 2;
		add(lblGewandheit, gbc_lblGewandheit);
		
		JLabel lblAstralenergie = new JLabel("Astralenergie");
		lblAstralenergie.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblAstralenergie = new GridBagConstraints();
		gbc_lblAstralenergie.anchor = GridBagConstraints.EAST;
		gbc_lblAstralenergie.insets = new Insets(0, 0, 5, 5);
		gbc_lblAstralenergie.gridx = 5;
		gbc_lblAstralenergie.gridy = 2;
		add(lblAstralenergie, gbc_lblAstralenergie);
		GridBagConstraints gbc_btnIntuition = new GridBagConstraints();
		gbc_btnIntuition.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnIntuition.insets = new Insets(0, 0, 5, 5);
		gbc_btnIntuition.gridx = 2;
		gbc_btnIntuition.gridy = 3;
		add(btnIntuition, gbc_btnIntuition);
		
		spinnerBehinderung = new JSpinner();
		((DefaultEditor) spinnerBehinderung.getEditor()).getTextField().setEditable(false);
		spinnerBehinderung.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int newVal = (Integer)spinnerBehinderung.getValue();
				hero.setBehinderung(newVal);
			}
		});
		
		JButton btnKonstitution = new JButton(String.valueOf(hero.getKonstitution()));
		btnKonstitution.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Konstitution", hero.getKonstitution()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		GridBagConstraints gbc_btnKonstitution = new GridBagConstraints();
		gbc_btnKonstitution.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKonstitution.insets = new Insets(0, 0, 5, 5);
		gbc_btnKonstitution.gridx = 4;
		gbc_btnKonstitution.gridy = 3;
		add(btnKonstitution, gbc_btnKonstitution);
		spinnerBehinderung.setValue(Integer.valueOf(hero.getBehinderung()));
		GridBagConstraints gbc_spinnerBehinderung = new GridBagConstraints();
		gbc_spinnerBehinderung.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerBehinderung.insets = new Insets(0, 0, 5, 5);
		gbc_spinnerBehinderung.gridx = 6;
		gbc_spinnerBehinderung.gridy = 3;
		add(spinnerBehinderung, gbc_spinnerBehinderung);
		
		JLabel lblIntuition = new JLabel("Intuition");
		lblIntuition.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblIntuition = new GridBagConstraints();
		gbc_lblIntuition.anchor = GridBagConstraints.EAST;
		gbc_lblIntuition.insets = new Insets(0, 0, 5, 5);
		gbc_lblIntuition.gridx = 1;
		gbc_lblIntuition.gridy = 3;
		add(lblIntuition, gbc_lblIntuition);
		
		JLabel lblKonstitution = new JLabel("Konstitution");
		lblKonstitution.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblKonstitution = new GridBagConstraints();
		gbc_lblKonstitution.anchor = GridBagConstraints.EAST;
		gbc_lblKonstitution.insets = new Insets(0, 0, 5, 5);
		gbc_lblKonstitution.gridx = 3;
		gbc_lblKonstitution.gridy = 3;
		add(lblKonstitution, gbc_lblKonstitution);
		
		spinnerAusdauer = new JSpinner();
		((DefaultEditor) spinnerAusdauer.getEditor()).getTextField().setEditable(false);
		
		JButton btnKoerperkraft = new JButton(String.valueOf(hero.getKoerperkraft()));
		btnKoerperkraft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Koerperkraft", hero.getKoerperkraft()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		
		JLabel lblBehinderung = new JLabel("Behinderung");
		lblBehinderung.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblBehinderung = new GridBagConstraints();
		gbc_lblBehinderung.anchor = GridBagConstraints.EAST;
		gbc_lblBehinderung.insets = new Insets(0, 0, 5, 5);
		gbc_lblBehinderung.gridx = 5;
		gbc_lblBehinderung.gridy = 3;
		add(lblBehinderung, gbc_lblBehinderung);
		
		JButton btnCharisma = new JButton(String.valueOf(hero.getCharisma()));
		btnCharisma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				StringSelection selection = new StringSelection(String.format("!%s Charisma", hero.getCharisma()));
				clipboard.setContents(selection, null);
				
				JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
			}
		});
		GridBagConstraints gbc_btnCharisma = new GridBagConstraints();
		gbc_btnCharisma.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCharisma.insets = new Insets(0, 0, 0, 5);
		gbc_btnCharisma.gridx = 2;
		gbc_btnCharisma.gridy = 4;
		add(btnCharisma, gbc_btnCharisma);
		GridBagConstraints gbc_btnKoerperkraft = new GridBagConstraints();
		gbc_btnKoerperkraft.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnKoerperkraft.insets = new Insets(0, 0, 0, 5);
		gbc_btnKoerperkraft.gridx = 4;
		gbc_btnKoerperkraft.gridy = 4;
		add(btnKoerperkraft, gbc_btnKoerperkraft);
		spinnerAusdauer.setValue(Integer.valueOf(hero.getAusdauer()));
		GridBagConstraints gbc_spinnerAusdauer = new GridBagConstraints();
		gbc_spinnerAusdauer.fill = GridBagConstraints.HORIZONTAL;
		gbc_spinnerAusdauer.insets = new Insets(0, 0, 0, 5);
		gbc_spinnerAusdauer.gridx = 6;
		gbc_spinnerAusdauer.gridy = 4;
		add(spinnerAusdauer, gbc_spinnerAusdauer);
		
		JLabel lblCharisma = new JLabel("Charisma");
		lblCharisma.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblCharisma = new GridBagConstraints();
		gbc_lblCharisma.anchor = GridBagConstraints.EAST;
		gbc_lblCharisma.insets = new Insets(0, 0, 0, 5);
		gbc_lblCharisma.gridx = 1;
		gbc_lblCharisma.gridy = 4;
		add(lblCharisma, gbc_lblCharisma);
		
		JLabel lblKoerperkraft = new JLabel("Körperkraft");
		lblKoerperkraft.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblKoerperkraft = new GridBagConstraints();
		gbc_lblKoerperkraft.anchor = GridBagConstraints.EAST;
		gbc_lblKoerperkraft.insets = new Insets(0, 0, 0, 5);
		gbc_lblKoerperkraft.gridx = 3;
		gbc_lblKoerperkraft.gridy = 4;
		add(lblKoerperkraft, gbc_lblKoerperkraft);
		
		JLabel lblAusdauer = new JLabel("Ausdauer");
		lblAusdauer.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_lblAusdauer = new GridBagConstraints();
		gbc_lblAusdauer.anchor = GridBagConstraints.EAST;
		gbc_lblAusdauer.insets = new Insets(0, 0, 0, 5);
		gbc_lblAusdauer.gridx = 5;
		gbc_lblAusdauer.gridy = 4;
		add(lblAusdauer, gbc_lblAusdauer);
	}
}
