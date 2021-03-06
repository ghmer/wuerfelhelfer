package link.parzival.dsa.ui;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import link.parzival.dsa.Konstanten;
import link.parzival.dsa.VersionCheck;
import link.parzival.dsa.object.HeldenObjekt;
import link.parzival.dsa.object.enumeration.LizenzTypEnum;
import link.parzival.dsa.object.enumeration.PatzerTypEnum;
import link.parzival.dsa.parser.HeldenDokumentParser;
import link.parzival.dsa.ui.dialog.AboutDialog;
import link.parzival.dsa.ui.dialog.KampfbedingungenDialog;
import link.parzival.dsa.ui.dialog.LizenzDialog;
import link.parzival.dsa.ui.dialog.UpdateHinweisDialog;
import link.parzival.dsa.ui.dialog.PatzerTabellenDialog;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SplashScreen;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.swing.JCheckBoxMenuItem;

public class WuerfelHelferGUI extends JFrame {
    private static final long serialVersionUID      = 6428768807868759732L;
    public static final Logger _LOG                 = Logger.getLogger(WuerfelHelferGUI.class.getName());
    private JPanel contentPane                      = null;
    private Font customHeroNameFont                 = null;
    private HeldenObjekt hero                       = null;
    private JMenuItem menuItemHelleDarstellung      = null;
    private JMenuItem menuItemDunkleDarstellung     = null;
    private JMenu menuAnsicht                       = null;
    private HeldenPanel heldenPanel                 = null;
    private TalentPanel talentPanel                 = null;
    private KampfPanel kampfPanel                   = null;
    
    private SplashScreen splash                     = null;
    
    private boolean zeigeHeldenPanel                = true;
    private boolean zeigeTalentPanel                = true;
    private boolean zeigeKampfPanel                 = true;
    
    protected void showSplashScreen() {
        splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            _LOG.severe("SplashScreen.getSplashScreen() returned null");
            return;
        }
        Graphics2D graphics = splash.createGraphics();
        if (graphics == null) {
            _LOG.severe("g is null");
            return;
        }
    }
    /**
     * Create the frame.
     */
    public WuerfelHelferGUI() {
        showSplashScreen();
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            _LOG.severe( "Failed to initialize LaF" );
        }
        
        UIManager.put( "Button.arc", 15 );
        UIManager.put( "Component.arc", 15 );
        UIManager.put( "ProgressBar.arc", 15 );
        UIManager.put( "TextComponent.arc", 15 );
        
        this.customHeroNameFont = UIHelfer.getFontFromResource("/Friedolin.ttf");
        GraphicsEnvironment ge  = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customHeroNameFont);
        
        Font font = Font.decode("Lucida Grande");
        setFont(font);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(String.format("Würfelhelfer Version %s", Konstanten.VERSION_EXTERNAL));
        setBounds(100, 100, 300, 100);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu menuFile = new JMenu(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuFile.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuBar.add(menuFile);
        
        JMenuItem menuItemLoadHtml = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemLoadHtml.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemLoadHtml.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HeldenDokumentParser hxp = new HeldenDokumentParser();
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                fileChooser.setFileFilter(UIHelfer.getHtmlFileFilter());
                
                int result = fileChooser.showOpenDialog(contentPane);
                if (result == JFileChooser.APPROVE_OPTION) {
                    SwingUtilities.invokeLater(new Runnable() {
                        
                        @Override
                        public void run() {
                            File selectedFile = fileChooser.getSelectedFile();
                            try {
                                hero = null;
                                Instant start = Instant.now();
                                hero = hxp.parseFile(selectedFile);
                                Instant end = Instant.now();
                                _LOG.fine("Dauer des parsens: " + Duration.between(start, end));
                            } catch (Exception e1) {
                                _LOG.severe(e1.getMessage());
                            }
                            
                            heldenPanel.setzeHeldenObjekt(hero);
                            talentPanel.setzeHeldenObjekt(hero);
                            kampfPanel.setzeHeldenObjekt(hero);
                            setItemVisibility();
                            
                        }
                    });
                }
            }            
        });
        menuFile.add(menuItemLoadHtml);
        
        JMenuItem menuItemUpdateCheck = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemUpdateCheck.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuFile.add(menuItemUpdateCheck);
        menuItemUpdateCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    
                    @Override
                    public void run() {
                        if(VersionCheck.checkForNewVersion(Konstanten.VERSION)) {
                            UpdateHinweisDialog dialog = new UpdateHinweisDialog();
                            dialog.setLocationRelativeTo(getRootPane());
                            dialog.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "Du verwendest die aktuelle Version");
                        }
                        
                    }
                });
            }
        });
        
        JSeparator separator = new JSeparator();
        menuFile.add(separator);
        
        JMenuItem menuItemExit = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemExit.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menuFile.add(menuItemExit);
        
        JMenu mntMenuDarstellung = new JMenu(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.mntMenuDarstellung.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuBar.add(mntMenuDarstellung);
        
        menuItemHelleDarstellung = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemHelleDarstellung.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemHelleDarstellung.setEnabled(false);
        menuItemHelleDarstellung.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    updateDarstellungMenuItems(false);
                    UIManager.setLookAndFeel(new FlatLightLaf());
                    SwingUtilities.updateComponentTreeUI(getRootPane());
                } catch (UnsupportedLookAndFeelException e1) {
                    _LOG.severe(e1.getMessage());
                }
            }
        });
        mntMenuDarstellung.add(menuItemHelleDarstellung);
        
        menuItemDunkleDarstellung = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemDunkleDarstellung.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemDunkleDarstellung.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    updateDarstellungMenuItems(true);
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                    SwingUtilities.updateComponentTreeUI(getRootPane());
                } catch (UnsupportedLookAndFeelException e1) {
                    _LOG.severe(e1.getMessage());
                }
            }
        });
        mntMenuDarstellung.add(menuItemDunkleDarstellung);
        
        JMenu menuTabellen = new JMenu(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuTabellen.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuBar.add(menuTabellen);
        
        JMenuItem menuItemPatzerTabelleNahkampf = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemPatzerTabelleNahkampf.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemPatzerTabelleNahkampf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PatzerTabellenDialog patzerTabellenDialog = new PatzerTabellenDialog(PatzerTypEnum.Nahkampf);
                patzerTabellenDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                patzerTabellenDialog.setFont(getFont());
                patzerTabellenDialog.setLocationRelativeTo(getRootPane());
                patzerTabellenDialog.setVisible(true);
            }
        });
        menuTabellen.add(menuItemPatzerTabelleNahkampf);
        
        JMenuItem menuItemPatzerTabelleFernkampf = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemPatzerTabelleFernkampf.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemPatzerTabelleFernkampf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PatzerTabellenDialog patzerTabellenDialog = new PatzerTabellenDialog(PatzerTypEnum.Fernkampf);
                patzerTabellenDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                patzerTabellenDialog.setFont(getFont());
                patzerTabellenDialog.setLocationRelativeTo(getRootPane());
                patzerTabellenDialog.setVisible(true);
            }
        });
        menuTabellen.add(menuItemPatzerTabelleFernkampf);
        
        JMenuItem menuItemKampfbedingungen = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemKampfbedingungenTabelle.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemKampfbedingungen.setEnabled(false);
        menuItemKampfbedingungen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                KampfbedingungenDialog kampfbedingungenDialog = new KampfbedingungenDialog();
                kampfbedingungenDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                kampfbedingungenDialog.setFont(getFont());
                kampfbedingungenDialog.setLocationRelativeTo(getRootPane());
                kampfbedingungenDialog.setVisible(true);
            }
        });
        menuTabellen.add(menuItemKampfbedingungen);
        
        menuAnsicht = new JMenu(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.mnNewMenu.text"));
        menuAnsicht.setVisible(false);
        menuBar.add(menuAnsicht);
        
        JCheckBoxMenuItem cbZeigeHeldenPanel = new JCheckBoxMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.cbZeigeHeldenPanel.text")); //$NON-NLS-1$ //$NON-NLS-2$
        cbZeigeHeldenPanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getSource();
                zeigeHeldenPanel = item.isSelected();
                setItemVisibility();
            }
        });
        cbZeigeHeldenPanel.setSelected(true);
        menuAnsicht.add(cbZeigeHeldenPanel);
        
        JCheckBoxMenuItem cbZeigeTalentPanel = new JCheckBoxMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.cbZeigeTalentPanel.text")); //$NON-NLS-1$ //$NON-NLS-2$
        cbZeigeTalentPanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getSource();
                zeigeTalentPanel = item.isSelected();
                setItemVisibility();
            }
        });
        cbZeigeTalentPanel.setSelected(true);
        menuAnsicht.add(cbZeigeTalentPanel);
        
        JCheckBoxMenuItem cbZeigeKampfPanel = new JCheckBoxMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.cbZeigeKampfPanel.text")); //$NON-NLS-1$ //$NON-NLS-2$
        cbZeigeKampfPanel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JCheckBoxMenuItem item = (JCheckBoxMenuItem)e.getSource();
                zeigeKampfPanel = item.isSelected();
                setItemVisibility();
            }
        });
        cbZeigeKampfPanel.setSelected(true);
        menuAnsicht.add(cbZeigeKampfPanel);
        
        JMenu menuHilfe = new JMenu(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuHilfe.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuBar.add(menuHilfe);
        
        JMenuItem menuItemLizenzGpl = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemLizenzGpl.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemLizenzGpl.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LizenzDialog lizenzDialog = new LizenzDialog(LizenzTypEnum.GPL);
                lizenzDialog.setLocationRelativeTo(getRootPane());
                lizenzDialog.setVisible(true);
            }
        });
        
        JMenuItem menuItemLizenzApache = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemLizenzApache.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemLizenzApache.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LizenzDialog lizenzDialog = new LizenzDialog(LizenzTypEnum.Apache);
                lizenzDialog.setLocationRelativeTo(getRootPane());
                lizenzDialog.setVisible(true);
            }
        });
        
        JMenuItem menuItemAbout = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemAbout.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AboutDialog aboutDialog = new AboutDialog();
                aboutDialog.setLocationRelativeTo(getRootPane());
                aboutDialog.setVisible(true);
            }
        });
        menuHilfe.add(menuItemAbout);
        
        JSeparator separator_1 = new JSeparator();
        menuHilfe.add(separator_1);
        
        JMenuItem menuItemHilfe = new JMenuItem(ResourceBundle.getBundle("link.parzival.dsa.ui.messages").getString("WuerfelHelferGUI.menuItemHilfe.text")); //$NON-NLS-1$ //$NON-NLS-2$
        menuItemHilfe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI(Konstanten.MANUAL_URL));
                } catch (IOException | URISyntaxException e1) {
                    _LOG.severe(e1.getMessage());
                }
            }
        });
        menuHilfe.add(menuItemHilfe);
        
        JSeparator separator_2 = new JSeparator();
        menuHilfe.add(separator_2);
        menuHilfe.add(menuItemLizenzApache);
        menuHilfe.add(menuItemLizenzGpl);
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
        kampfPanel.setFont(getFont());
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.anchor = GridBagConstraints.NORTH;
        gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 2;
        contentPane.add(kampfPanel, gbc_panel_1);
        
        Thread splashScreenThread = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(3000);
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            if(splash != null) {
                                try {
                                    splash.close();
                                }catch(Exception e) {
                                    
                                }
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    _LOG.severe(e.getMessage());
                }
            }
        });
        
        if(splash != null) {
            
            try {
                splashScreenThread.start();
                splashScreenThread.join();
            } catch (InterruptedException e1) {
                _LOG.severe(e1.getMessage());
            }
        }
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
        copyToClipboard(text, getFrames()[0]);
    }
    
    public static void copyToClipboard(String text, Component parentComponent) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, null);
        
        JOptionPane.showMessageDialog(parentComponent, "Kommando wurde in die Zwischenablage kopiert" );
    }
    
    /**
     * @param args the default arguments
     */
    public static void main(String[] args) {
        if(args != null && args.length == 1) {
            if(args[0].equalsIgnoreCase("-version")) {
                System.out.println(Konstanten.VERSION);
                return;
            }
            if(args[0].equalsIgnoreCase("-updatecheck")) {
                System.out.println(VersionCheck.checkForNewVersion(Konstanten.VERSION));
                return;
            }
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Instant start = Instant.now();
                    WuerfelHelferGUI frame = new WuerfelHelferGUI();
                    frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setVisible(true);
                    Instant end = Instant.now();
                    _LOG.fine("Startup Zeit: " + Duration.between(start, end));
                } catch (Exception e) {
                    _LOG.severe(e.getMessage());
                }
            }
        });
    }
        
    private void setItemVisibility() {
        heldenPanel.setVisible(zeigeHeldenPanel);
        talentPanel.setVisible(zeigeTalentPanel);
        kampfPanel.setVisible(zeigeKampfPanel);  
        menuAnsicht.setVisible(true);
        pack();
    }
}
