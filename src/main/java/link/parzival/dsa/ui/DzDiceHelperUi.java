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

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;

public class DzDiceHelperUi extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6428768807868759732L;
	private JPanel contentPane;
	private Font customMainFont;
	private Font customHeroNameFont;
	private HeldenObjekt hero;
	
	private HeroPanel currentHero = null;
	private AbilityPanel currentAbility = null;
	private String selectedAbilityName;
	private JButton btnPruefungWaehlen;
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
		setBounds(100, 100, 670, 400);
		
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
					public String getDescription() {
						return "*.xml";
					}					
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
				            return true;
				        } else {
				            return f.getName().toLowerCase().endsWith(".xml");
				        }
					}
				});
				
				int result = fileChooser.showOpenDialog(contentPane);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    HeroXmlParser hxp = new HeroXmlParser();
					hero = hxp.parseFile(selectedFile);
					
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
					contentPane.repaint();
					contentPane.updateUI();
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
					public String getDescription() {
						return "*.html";
					}					
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
				            return true;
				        } else {
				            return f.getName().toLowerCase().endsWith(".html");
				        }
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
					contentPane.repaint();
					contentPane.updateUI();					
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
		
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 200, 658, 12);
		contentPane.add(separator_1);
		
		btnPruefungWaehlen = new JButton("Probe auswählen");
		btnPruefungWaehlen.setEnabled(false);
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
		btnPruefungWaehlen.setBounds(6, 210, 664, 29);
		contentPane.add(btnPruefungWaehlen);
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(6, 240, 658, 12);
		contentPane.add(separator_1_1);
	}
	
	protected void setSelectedAbilityName(String selectedAbility) {
		this.selectedAbilityName = selectedAbility;
		
	}
	
	protected String getSelectedAbilityName() {
		return this.selectedAbilityName;
	}
}
