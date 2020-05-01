package link.parzival.dsa.ui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import link.parzival.dsa.HeldenObjekt;

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class HeroPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4145721306607452199L;
	private JTextField textFieldMut;
	private JTextField textFieldKlugheit;
	private JTextField textFieldIntuition;
	private JTextField textFieldCharisma;
	private JTextField textFieldFingerfertigkeit;
	private JTextField textFieldGewandheit;
	private JTextField textFieldKonstitution;
	private JTextField textFieldKoerperkraft;
	private JLabel lblHeroName;
	private JSpinner spinnerLebensenergie;
	private JSpinner spinnerAstralenergie;
	private JSpinner spinnerBehinderung;
	private JSpinner spinnerAusdauer;

	public void updatePanel(HeldenObjekt hero) {
		lblHeroName.setText(hero.getName());
		textFieldMut.setText(String.valueOf(hero.getMut()));
		textFieldKlugheit.setText(String.valueOf(hero.getKlugheit()));
		textFieldIntuition.setText(String.valueOf(hero.getIntuition()));
		textFieldCharisma.setText(String.valueOf(hero.getCharisma()));
		textFieldFingerfertigkeit.setText(String.valueOf(hero.getFingerfertigkeit()));
		textFieldGewandheit.setText(String.valueOf(hero.getGewandtheit()));
		textFieldKonstitution.setText(String.valueOf(hero.getKonstitution()));
		textFieldKoerperkraft.setText(String.valueOf(hero.getKoerperkraft()));
		spinnerLebensenergie.setValue(Integer.valueOf(hero.getLebensenergie()));
		spinnerAstralenergie.setValue(Integer.valueOf(hero.getAstralenergie()));
		spinnerBehinderung.setValue(Integer.valueOf(hero.getBehinderung()));
		spinnerAusdauer.setValue(Integer.valueOf(hero.getAusdauer()));
	}
	/**
	 * Create the panel.
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
		
		textFieldMut = new JTextField();
		textFieldMut.setEditable(false);
		textFieldMut.setBounds(138, 59, 60, 26);
		textFieldMut.setText(String.valueOf(hero.getMut()));
		textFieldMut.setColumns(10);
		add(textFieldMut);
		
		textFieldKlugheit = new JTextField();
		textFieldKlugheit.setEditable(false);
		textFieldKlugheit.setColumns(10);
		textFieldKlugheit.setBounds(138, 87, 60, 26);
		textFieldKlugheit.setText(String.valueOf(hero.getKlugheit()));
		add(textFieldKlugheit);
		
		textFieldIntuition = new JTextField();
		textFieldIntuition.setEditable(false);
		textFieldIntuition.setColumns(10);
		textFieldIntuition.setBounds(138, 115, 60, 26);
		textFieldIntuition.setText(String.valueOf(hero.getIntuition()));
		add(textFieldIntuition);
		
		textFieldCharisma = new JTextField();
		textFieldCharisma.setEditable(false);
		textFieldCharisma.setColumns(10);
		textFieldCharisma.setBounds(138, 143, 60, 26);
		textFieldCharisma.setText(String.valueOf(hero.getCharisma()));
		add(textFieldCharisma);
		
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
		
		textFieldFingerfertigkeit = new JTextField();
		textFieldFingerfertigkeit.setEditable(false);
		textFieldFingerfertigkeit.setColumns(10);
		textFieldFingerfertigkeit.setBounds(342, 59, 60, 26);
		textFieldFingerfertigkeit.setText(String.valueOf(hero.getFingerfertigkeit()));
		add(textFieldFingerfertigkeit);
		
		textFieldGewandheit = new JTextField();
		textFieldGewandheit.setEditable(false);
		textFieldGewandheit.setColumns(10);
		textFieldGewandheit.setBounds(342, 87, 60, 26);
		textFieldGewandheit.setText(String.valueOf(hero.getGewandtheit()));
		add(textFieldGewandheit);
		
		textFieldKonstitution = new JTextField();
		textFieldKonstitution.setEditable(false);
		textFieldKonstitution.setColumns(10);
		textFieldKonstitution.setBounds(342, 115, 60, 26);
		textFieldKonstitution.setText(String.valueOf(hero.getKonstitution()));
		add(textFieldKonstitution);
		
		textFieldKoerperkraft = new JTextField();
		textFieldKoerperkraft.setEditable(false);
		textFieldKoerperkraft.setColumns(10);
		textFieldKoerperkraft.setBounds(342, 143, 60, 26);
		textFieldKoerperkraft.setText(String.valueOf(hero.getKoerperkraft()));
		add(textFieldKoerperkraft);
		
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
		spinnerBehinderung.setEnabled(false);
		spinnerBehinderung.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				int newVal = (Integer)spinnerBehinderung.getValue();
				System.out.println("Neue Behinderung: " + newVal);
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
	}
}
