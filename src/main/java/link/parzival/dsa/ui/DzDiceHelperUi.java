package link.parzival.dsa.ui;

import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import link.parzival.dsa.Constants;
import link.parzival.dsa.HeroHtmlParser;
import link.parzival.dsa.VersionCheck;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.TalentObjekt;
import link.parzival.dsa.ui.dialog.AbilityDialog;
import link.parzival.dsa.ui.dialog.LizenzDialog;
import link.parzival.dsa.ui.dialog.NewVersionAvailableDialog;
import link.parzival.dsa.ui.panel.AbilityPanel;
import link.parzival.dsa.ui.panel.CombatPanel;
import link.parzival.dsa.ui.panel.HeroPanel;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JButton;

public class DzDiceHelperUi extends JFrame {
	private static final long serialVersionUID 	= 6428768807868759732L;
	
	public  static final int VERSION 			= 10;
	private JPanel contentPane					= null;
	private AbilityPanel currentAbility 		= null;	
	private CombatPanel currentCombatPanel		= null;
	private HeroPanel currentHeroPanel 			= null;
	private Font customHeroNameFont				= null;
	private Font customMainFont					= null;
	private HeldenObjekt hero					= null;
	private String selectedAbilityName			= null;
	private JSeparator separatorTalentDown		= null;
	private JSeparator separatorTalentUp		= null;
	private JButton btnPruefungWaehlen			= null;
	private JMenuItem menuItemHelleDarstellung;
	private JMenuItem menuItemDunkleDarstellung;
	
	
	/**
	 * Create the frame.
	 */
	public DzDiceHelperUi() {
		try {
		    UIManager.setLookAndFeel( new FlatLightLaf() );
		} catch( Exception ex ) {
		    System.err.println( "Failed to initialize LaF" );
		}
		this.customMainFont 	= getFontFromResource("/UbuntuMono-R.ttf");
		this.customHeroNameFont = getFontFromResource("/Friedolin.ttf");
		GraphicsEnvironment ge 	= GraphicsEnvironment.getLocalGraphicsEnvironment();
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
					NewVersionAvailableDialog dialog = new NewVersionAvailableDialog();
					dialog.setLocationRelativeTo(getRootPane());
					dialog.setVisible(true);
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
		
		JMenu mntMenuDarstellung = new JMenu("Darstellung");
		menuBar.add(mntMenuDarstellung);
		
		menuItemHelleDarstellung = new JMenuItem("Helles UI");
		menuItemHelleDarstellung.setEnabled(false);
		menuItemHelleDarstellung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateDarstellungMenuItems(false);
					UIManager.setLookAndFeel(new FlatLightLaf());
					SwingUtilities.updateComponentTreeUI(getRootPane());
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntMenuDarstellung.add(menuItemHelleDarstellung);
		
		menuItemDunkleDarstellung = new JMenuItem("Dunkles UI");
		menuItemDunkleDarstellung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					updateDarstellungMenuItems(true);
					UIManager.setLookAndFeel(new FlatDarkLaf());
					SwingUtilities.updateComponentTreeUI(getRootPane());
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntMenuDarstellung.add(menuItemDunkleDarstellung);
		
		JMenu mnNewMenu = new JMenu("?");
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItemLizenzUbuntu = new JMenuItem("Lizenz: Ubuntu Font");
		menuItemLizenzUbuntu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LizenzDialog lizenzDialog = new LizenzDialog(LizenzDialog.License.Ubuntu);
				lizenzDialog.setFont(customMainFont);
				lizenzDialog.setVisible(true);
			}
		});
		
		JMenuItem menuItemLizenzGpl = new JMenuItem("Lizenz: Friedolin Font");
		menuItemLizenzGpl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LizenzDialog lizenzDialog = new LizenzDialog(LizenzDialog.License.GPL);
				lizenzDialog.setFont(customMainFont);
				lizenzDialog.setVisible(true);
			}
		});
		
		JMenuItem menuItemHilfe = new JMenuItem("Hilfe herunterladen");
		menuItemHilfe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URI(Constants.MANUAL_URL));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		mnNewMenu.add(menuItemHilfe);
		
		JMenuItem menuItemLizenzApache = new JMenuItem("Lizenz: Flatlaf L&F");
		menuItemLizenzApache.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LizenzDialog lizenzDialog = new LizenzDialog(LizenzDialog.License.Apache);
				lizenzDialog.setFont(customMainFont);
				lizenzDialog.setVisible(true);
			}
		});
		
		JSeparator separator_1 = new JSeparator();
		mnNewMenu.add(separator_1);
		mnNewMenu.add(menuItemLizenzApache);
		mnNewMenu.add(menuItemLizenzUbuntu);
		mnNewMenu.add(menuItemLizenzGpl);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		separatorTalentUp = new JSeparator();
		separatorTalentUp.setBounds(6, 200, 720, 12);
		separatorTalentUp.setVisible(false);
		contentPane.add(separatorTalentUp);
		
		btnPruefungWaehlen = new JButton("Probe auswählen");
		btnPruefungWaehlen.setEnabled(false);
		btnPruefungWaehlen.setVisible(false);
		btnPruefungWaehlen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AbilityDialog dialog = new AbilityDialog(hero);
				dialog.setFont(customMainFont);
				dialog.setLocationRelativeTo(contentPane);
				switch (dialog.showDialog()) {
			    case Constants.DIALOG_OK_STATE:
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
		
		separatorTalentDown = new JSeparator();
		separatorTalentDown.setVisible(false);
		separatorTalentDown.setBounds(6, 240, 720, 12);
		contentPane.add(separatorTalentDown);
	}

	protected void updateDarstellungMenuItems(boolean dark) {
		if(dark) {
			menuItemDunkleDarstellung.setEnabled(false);
			menuItemHelleDarstellung.setEnabled(true);
		} else {
			menuItemDunkleDarstellung.setEnabled(true);
			menuItemHelleDarstellung.setEnabled(false);
		}
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
			e1.printStackTrace();
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e1) {
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
		separatorTalentDown.setVisible(true);
		separatorTalentUp.setVisible(true);
		btnPruefungWaehlen.setVisible(true);	
	}

	protected void setSelectedAbilityName(String selectedAbility) {
		this.selectedAbilityName = selectedAbility;	
	}
}
