package link.parzival.dsa.ui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import link.parzival.dsa.HeroHtmlParser;
import link.parzival.dsa.VersionCheck;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.ui.dialog.AbilityDialog;
import link.parzival.dsa.ui.dialog.LizenzDialog;
import link.parzival.dsa.ui.panel.AbilityPanel;
import link.parzival.dsa.ui.panel.CombatPanel;
import link.parzival.dsa.ui.panel.HeroPanel;

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

public class DzDiceHelperUi extends JFrame {
	public static final String remoteUrlString = "https://parzival.link/dz-dice-helper-latest.jar";
	public  static final int VERSION = 2;
	private static final long serialVersionUID = 6428768807868759732L;
	private JButton btnPruefungWaehlen;
	private JPanel contentPane;
	private AbilityPanel currentAbility = null;
	
	private CombatPanel currentCombatPanel;
	private HeroPanel currentHeroPanel = null;
	private Font customHeroNameFont;
	private Font customMainFont;
	private HeldenObjekt hero;
	private String selectedAbilityName;
	private JSeparator separatorTalentChoseButtonDown;
	private JSeparator separatorTalentChoseButtonUp;
	
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
		setBounds(100, 100, 730, 650);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Datei");
		menuBar.add(menuFile);
		
		JMenuItem menuItemLoadHtml = new JMenuItem("lade HTML");
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
		
		JMenuItem menuItemUpdateCheck = new JMenuItem("auf Update prüfen");
		menuFile.add(menuItemUpdateCheck);
		menuItemUpdateCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(VersionCheck.checkForNewVersion(VERSION)) {
					JOptionPane.showMessageDialog(null, "Eine neue Version kann unter https://parzival.link heruntergeladen werden");
				} else {
					JOptionPane.showMessageDialog(null, "Du verwendest die aktuelle Version");
				}
			}
		});
		
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
		
		JMenuItem menuItemLizenzen = new JMenuItem("3rd Party Lizenzen");
		menuItemLizenzen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LizenzDialog lizenzDialog = new LizenzDialog();
				lizenzDialog.setFont(customMainFont);
				lizenzDialog.setVisible(true);
			}
		});
		mnNewMenu.add(menuItemLizenzen);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		separatorTalentChoseButtonUp = new JSeparator();
		separatorTalentChoseButtonUp.setBounds(6, 200, 720, 12);
		separatorTalentChoseButtonUp.setVisible(false);
		contentPane.add(separatorTalentChoseButtonUp);
		
		btnPruefungWaehlen = new JButton("Probe auswählen");
		btnPruefungWaehlen.setEnabled(false);
		btnPruefungWaehlen.setVisible(false);
		btnPruefungWaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbilityDialog dialog = new AbilityDialog(hero);
				dialog.setFont(customMainFont);
				dialog.setLocationRelativeTo(contentPane);
				switch (dialog.showDialog()) {
			    case AbilityDialog.OK_STATE:
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
					ap.setBounds(6, 250, 720, 60);
					if(currentAbility != null) {
						contentPane.remove(currentAbility);
					}
					currentAbility = ap;
					contentPane.add(ap);
					contentPane.updateUI();
				}
				
			}
		});
		btnPruefungWaehlen.setBounds(6, 210, 720, 29);
		contentPane.add(btnPruefungWaehlen);
		
		separatorTalentChoseButtonDown = new JSeparator();
		separatorTalentChoseButtonDown.setVisible(false);
		separatorTalentChoseButtonDown.setBounds(6, 240, 720, 12);
		contentPane.add(separatorTalentChoseButtonDown);
	}

	public static void copyToClipboard(String text) {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection selection = new StringSelection(text);
		clipboard.setContents(selection, null);
		
		JOptionPane.showMessageDialog( null, "Kommando wurde in die Zwischenablage kopiert" );
	}
	
	/**
	 * @param args the default arguments
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

	
	private void prepareView() {
		if(currentHeroPanel != null) {
			contentPane.remove(currentHeroPanel);
			contentPane.repaint();
			contentPane.updateUI();
		}
		
		if(currentCombatPanel != null) {
			contentPane.remove(currentCombatPanel);
			contentPane.repaint();
			contentPane.updateUI();
		}
		
		btnPruefungWaehlen.setEnabled(true);
		
		HeroPanel hp = new HeroPanel(hero);
		hp.setBounds(6, 6, 720, 175);
		hp.setFont(customMainFont);
		currentHeroPanel = hp;
		contentPane.add(hp);
		
		CombatPanel cp = new CombatPanel(hero);
		cp.setBounds(6,310,720,300);
		cp.setFont(customMainFont);
		currentCombatPanel = cp;
		contentPane.add(cp);
		
		setItemVisibility();
		contentPane.repaint();
		contentPane.updateUI();
	}
	
	private void setItemVisibility() {
		separatorTalentChoseButtonDown.setVisible(true);
		separatorTalentChoseButtonUp.setVisible(true);
		btnPruefungWaehlen.setVisible(true);	
	}

	protected void setSelectedAbilityName(String selectedAbility) {
		this.selectedAbilityName = selectedAbility;	
	}
}
