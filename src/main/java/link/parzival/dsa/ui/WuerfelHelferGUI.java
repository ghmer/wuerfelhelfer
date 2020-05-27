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
import link.parzival.dsa.ui.dialog.LizenzDialog;
import link.parzival.dsa.ui.dialog.NewVersionAvailableDialog;
import link.parzival.dsa.ui.panel.HeldenPanel;
import link.parzival.dsa.ui.panel.KampfPanel;
import link.parzival.dsa.ui.panel.TalentPanel;

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

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class WuerfelHelferGUI extends JFrame {
	private static final long serialVersionUID 	= 6428768807868759732L;
	private JPanel contentPane					= null;
	private Font customHeroNameFont				= null;
	private Font customMainFont					= null;
	private HeldenObjekt hero					= null;
	private JMenuItem menuItemHelleDarstellung;
	private JMenuItem menuItemDunkleDarstellung;
	private HeldenPanel heldenPanel;
	private TalentPanel talentPanel;
	private KampfPanel kampfPanel;
	
	
	/**
	 * Create the frame.
	 */
	public WuerfelHelferGUI() {
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
		setTitle(String.format("Würfelhelfer Version %s", Constants.VERSION_EXTERNAL));
		setBounds(100, 100, 300, 100);
		
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
					
					heldenPanel.setHero(hero);
					talentPanel.setHero(hero);
					kampfPanel.setHero(hero);
					setItemVisibility();
				}
			}			
		});
		menuFile.add(menuItemLoadHtml);
		
		JMenuItem menuItemUpdateCheck = new JMenuItem("auf Update prüfen");
		menuFile.add(menuItemUpdateCheck);
		menuItemUpdateCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(VersionCheck.checkForNewVersion(Constants.VERSION)) {
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
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		heldenPanel = new HeldenPanel();
		heldenPanel.setVisible(false);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		contentPane.add(heldenPanel, gbc_panel);
		
		talentPanel = new TalentPanel();
		talentPanel.setVisible(false);
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		contentPane.add(talentPanel, gbc_panel_2);
		
		kampfPanel = new KampfPanel();
		kampfPanel.setVisible(false);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		contentPane.add(kampfPanel, gbc_panel_1);
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
					
					WuerfelHelferGUI frame = new WuerfelHelferGUI();
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
	
	private void setItemVisibility() {
		heldenPanel.setVisible(true);
		talentPanel.setVisible(true);
		kampfPanel.setVisible(true);	
		pack();
	}
}
