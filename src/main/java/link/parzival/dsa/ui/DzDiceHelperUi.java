package link.parzival.dsa.ui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import link.parzival.dsa.HeldenObjekt;
import link.parzival.dsa.HeroHtmlParser;
import link.parzival.dsa.HeroXmlParser;
import link.parzival.dsa.TalentObjekt;
import link.parzival.dsa.WaffenObjekt;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class DzDiceHelperUi extends JFrame {

	public void copyToClipboard(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(text);
		clipboard.setContents(selection, null);
		
		JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6428768807868759732L;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {					
					
					DzDiceHelperUi frame = new DzDiceHelperUi();
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					frame.setResizable(false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JPanel contentPane;
	private Font customMainFont;
	private Font customHeroNameFont;
	
	private HeldenObjekt hero;
	private HeroPanel currentHero = null;
	private AbilityPanel currentAbility = null;
	private String selectedAbilityName;
	private JButton btnPruefungWaehlen;
	private JSeparator separatorTalentChoseButtonDown;
	private JSeparator separatorTalentChoseButtonUp;
	private JSeparator separatorFightUp;
	private JSeparator separatorFightDown;
	private JComboBox<String> comboBoxWaffe;
	private JButton btnInitiative;
	private JButton btnAusweichen;
	private JButton btnVerteidigen;
	private JButton btnAngriff;
	private JButton btnSchaden;
	
	/**
	 * Create the frame.
	 */
	public DzDiceHelperUi() {
		this.customMainFont = getFontFromResource("/UbuntuMono-R.ttf");
		this.customHeroNameFont = getFontFromResource("/Friedolin.ttf");
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(customHeroNameFont);
		ge.registerFont(customMainFont);
		
		setFont(customMainFont);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 475);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		JMenuItem menuItemLoadXml = new JMenuItem("load XML");
		menuItemLoadXml.setEnabled(false);
		menuItemLoadXml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
				            return true;
				        } else {
				            return f.getName().toLowerCase().endsWith(".xml");
				        }
					}					
					@Override
					public String getDescription() {
						return "*.xml";
					}
				});
				
				int result = fileChooser.showOpenDialog(contentPane);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    HeroXmlParser hxp = new HeroXmlParser();
					hero = hxp.parseFile(selectedFile);
					
					prepareView();
				}
			}			
		});
		menuFile.add(menuItemLoadXml);
		
		JMenuItem menuItemLoadHtml = new JMenuItem("load HTML");
		menuItemLoadHtml.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				fileChooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
				            return true;
				        } else {
				            return f.getName().toLowerCase().endsWith(".html");
				        }
					}					
					@Override
					public String getDescription() {
						return "*.html";
					}
				});
				
				int result = fileChooser.showOpenDialog(contentPane);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    HeroHtmlParser hxp = new HeroHtmlParser();
					try {
						hero = null;
						hero = hxp.parseFile(selectedFile);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					prepareView();
				}
			}			
		});
		menuFile.add(menuItemLoadHtml);
		
		JSeparator separator = new JSeparator();
		menuFile.add(separator);
		
		JMenuItem menuItemExit = new JMenuItem("exit");
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuFile.add(menuItemExit);
		
		JMenu mnNewMenu = new JMenu("?");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("3rd Party Lizenzen");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LizenzDialog lizenzDialog = new LizenzDialog();
				lizenzDialog.setFont(customMainFont);
				lizenzDialog.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		separatorTalentChoseButtonUp = new JSeparator();
		separatorTalentChoseButtonUp.setBounds(6, 200, 658, 12);
		separatorTalentChoseButtonUp.setVisible(false);
		contentPane.add(separatorTalentChoseButtonUp);
		
		btnPruefungWaehlen = new JButton("Probe auswählen");
		btnPruefungWaehlen.setEnabled(false);
		btnPruefungWaehlen.setVisible(false);
		btnPruefungWaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbilityDialogUi dialog = new AbilityDialogUi(hero);
				dialog.setFont(customMainFont);
				switch (dialog.showDialog()) {
			    case AbilityDialogUi.OK_STATE:
			    	setSelectedAbilityName(dialog.getSelectedAbility());
			        break;
				}
				
				TalentObjekt talentObjekt = null;
				if(getSelectedAbilityName() != null) {
					for(TalentObjekt objekt : hero.getTalente()) {
						if(objekt.getName().equalsIgnoreCase(getSelectedAbilityName())) {
							talentObjekt = objekt;
							break;
						}
					}
				}
				
				if(talentObjekt != null) {
					AbilityPanel ap = new AbilityPanel(talentObjekt, hero);
					ap.setBounds(6, 250, 658, 60);				
					if(currentAbility != null) {
						contentPane.remove(currentAbility);
					}
					currentAbility = ap;
					contentPane.add(ap);
					contentPane.updateUI();
				}
				
			}
		});
		btnPruefungWaehlen.setBounds(6, 210, 658, 29);
		contentPane.add(btnPruefungWaehlen);
		
		separatorTalentChoseButtonDown = new JSeparator();
		separatorTalentChoseButtonDown.setVisible(false);
		separatorTalentChoseButtonDown.setBounds(6, 240, 658, 12);
		contentPane.add(separatorTalentChoseButtonDown);
		
		separatorFightUp = new JSeparator();
		separatorFightUp.setVisible(false);
		separatorFightUp.setBounds(6, 338, 658, 12);
		contentPane.add(separatorFightUp);
		
		separatorFightDown = new JSeparator();
		separatorFightDown.setVisible(false);
		separatorFightDown.setBounds(6, 413, 658, 12);
		contentPane.add(separatorFightDown);
		
		comboBoxWaffe = new JComboBox<>();
		comboBoxWaffe.setVisible(false);
		comboBoxWaffe.setBounds(6, 349, 658, 27);
		contentPane.add(comboBoxWaffe);
		
		btnInitiative = new JButton("Initiative!");
		btnInitiative.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String waffenName 	= (String)comboBoxWaffe.getSelectedItem();
				WaffenObjekt waffe  = hero.getWaffeByName(waffenName);
				int waffenIni 		= waffe.getInitiative();
				int behinderung		= hero.getBehinderung();
				int berechneteIni 	= (hero.getBasisinitiative() + waffenIni) - behinderung;
				
				String kommando     = String.format("(1w6+%s) Initiative", berechneteIni);
				
				copyToClipboard(kommando);
			}
		});
		btnInitiative.setVisible(false);
		btnInitiative.setBounds(6, 388, 120, 29);
		contentPane.add(btnInitiative);
		
		btnAusweichen = new JButton("Ausweichen!");
		btnAusweichen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int basisVerteidigung = hero.getBasisparade();
				System.out.println("Verteidigung: " + basisVerteidigung);
				if(hero.isAusweichenI()) {
					System.out.println("Ausweichen I");
					basisVerteidigung += 3;
					System.out.println("Verteidigung: " + basisVerteidigung);
				}
				
				if(hero.isAusweichenII()) {
					System.out.println("Ausweichen II");
					basisVerteidigung += 3;
					System.out.println("Verteidigung: " + basisVerteidigung);
				}
				
				if(hero.isAusweichenIII()) {
					System.out.println("Ausweichen III");
					basisVerteidigung += 3;
					System.out.println("Verteidigung: " + basisVerteidigung);
				}
				
				basisVerteidigung -= hero.getBehinderung();
				System.out.println("Verteidigung: " + basisVerteidigung);
				
				String kommando     = String.format("!%s Ausweichen", basisVerteidigung);
				
				copyToClipboard(kommando);
			}
		});
		btnAusweichen.setVisible(false);
		btnAusweichen.setBounds(544, 388, 120, 29);
		contentPane.add(btnAusweichen);
		
		btnVerteidigen = new JButton("Verteidigen!");
		btnVerteidigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String waffenName 	= (String)comboBoxWaffe.getSelectedItem();
				WaffenObjekt waffe  = hero.getWaffeByName(waffenName);
				
				String kommando     = String.format("!%s Parade", waffe.getParade());
				
				copyToClipboard(kommando);
			}
		});
		btnVerteidigen.setVisible(false);
		btnVerteidigen.setBounds(412, 388, 120, 29);
		contentPane.add(btnVerteidigen);
		
		btnAngriff = new JButton("Angriff!");
		btnAngriff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String waffenName 	= (String)comboBoxWaffe.getSelectedItem();
				WaffenObjekt waffe  = hero.getWaffeByName(waffenName);
				
				String kommando     = String.format("!%s Attacke", waffe.getAttacke());
				
				copyToClipboard(kommando);
			}
		});
		btnAngriff.setVisible(false);
		btnAngriff.setBounds(138, 388, 120, 29);
		contentPane.add(btnAngriff);
		
		btnSchaden = new JButton("Schaden!");
		btnSchaden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String waffenName 	= (String)comboBoxWaffe.getSelectedItem();
				WaffenObjekt waffe  = hero.getWaffeByName(waffenName);
				
				String kommando     = formatWaffenSchaden(waffe.getTrefferpunkte());
				
				copyToClipboard(kommando);
			}

			public String formatWaffenSchaden(String trefferpunkte) {
				String result = null;
				
				if(trefferpunkte.contains("+")) {
					String[] parts = trefferpunkte.split("\\+");
					result = String.format("(%s6+%s)", parts[0].toLowerCase(), parts[1].toLowerCase());
				} else if(trefferpunkte.contains("-")) {
					String[] parts = trefferpunkte.split("\\-");
					result = String.format("(%s6-%s)", parts[0].toLowerCase(), parts[1].toLowerCase());
				} else {
					result = String.format("(%s6)", trefferpunkte.toLowerCase());
				}
				
				return result;
			}
		});
		btnSchaden.setVisible(false);
		btnSchaden.setBounds(270, 388, 130, 29);
		contentPane.add(btnSchaden);
	}

	private Font getFontFromResource(String pathToFont) {
		Font font = null;
		InputStream is = null;
		try {
			is = this.getClass().getResourceAsStream(pathToFont);
			font = Font.createFont(Font.TRUETYPE_FONT,is);
			
		} catch (IOException e) {
		     System.err.println(e.getMessage());
		} catch (FontFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		return font;
	}
	
	protected String getSelectedAbilityName() {
		return this.selectedAbilityName;
	}

	protected void populateFields(HeldenObjekt hero) {
		comboBoxWaffe.setModel(new DefaultComboBoxModel<String>(hero.getWaffenNamenAsArray()));
		
	}
	
	private void prepareView() {
		if(currentHero != null) {
			contentPane.remove(currentHero);
			contentPane.repaint();
			contentPane.updateUI();
		}
		
		btnPruefungWaehlen.setEnabled(true);
		
		HeroPanel hp = new HeroPanel(hero);
		hp.setBounds(6, 6, 658, 175);
		hp.setFont(customMainFont);
		currentHero = hp;
		contentPane.add(hp);
		setItemVisibility();
		populateFields(hero);
		contentPane.repaint();
		contentPane.updateUI();
	}
	
	private void setItemVisibility() {
		separatorTalentChoseButtonDown.setVisible(true);
		separatorTalentChoseButtonUp.setVisible(true);
		btnPruefungWaehlen.setVisible(true);
		separatorFightDown.setVisible(true);
		separatorFightDown.setVisible(true);
		comboBoxWaffe.setVisible(true);
		btnInitiative.setVisible(true);
		btnAngriff.setVisible(true);
		btnAusweichen.setVisible(true);
		btnVerteidigen.setVisible(true);
		btnSchaden.setVisible(true);
	}

	protected void setSelectedAbilityName(String selectedAbility) {
		this.selectedAbilityName = selectedAbility;
		
	}
}
