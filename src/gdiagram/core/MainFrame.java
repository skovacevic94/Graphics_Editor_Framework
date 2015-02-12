/**
 * @author Stanko 26.04.2014.
 * MainFrame.java
 */
package gdiagram.core;

import gdiagram.actions.ActionManager;
import gdiagram.actions.MainFrameAdapter;
import gdiagram.gui.AboutDialog;
import gdiagram.gui.MenuBar;
import gdiagram.gui.Pallete;
import gdiagram.gui.StatusBar;
import gdiagram.gui.ToolBar;
import gdiagram.gui.WorkspaceTree;
import gdiagram.model.Project;
import gdiagram.model.WorkspaceModel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * @author Stanko
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ClipboardOwner{
		
	private static MainFrame instance = null;	//SINGLETON
	
	public static final int DEFAULT_FRAME_WIDTH		= 1024;
	public static final int DEFAULT_FRAME_HEIGHT	= 700;
	
	private JMenuBar 		menuBar;
	private JToolBar		toolBar;
	private Pallete			pallete;
	private StatusBar		statusBar;
	private JDesktopPane 	desktopPane;	
	private WorkspaceTree   workspaceTree;
	
	private WorkspaceModel workspaceModel;
	
	private ActionManager actionManager;
	
	private AboutDialog aboutDialog;
	
    private int pasteCounter = 0;
    private Clipboard clipboard = new Clipboard("GlowDiagram clipboard");
	
	private MainFrame() {
	}
	
	private void masterInit() {
		//INITIALIZE ALL APP'S ACTIONS
		actionManager = new ActionManager();
		
		//INIT MVC
		initMVCWorkspace();
		
		setTitle("GlowDiagram");
		
		//CENTER FRAME ON THE DESKTOP WIDH DIMENSION = SCR_WIDTH/2 X SCR_HEIGHT/2
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension scrDim = toolkit.getScreenSize();
		int x = ((int)scrDim.getWidth()  - DEFAULT_FRAME_WIDTH)	 / 2; 
		int y = ((int)scrDim.getHeight() - DEFAULT_FRAME_HEIGHT) / 2;
		setLocation(x, y);
		setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
		
		//SET APP ICON
		URL iconURL = getClass().getResource("/gdiagram/res/smallIco.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		addWindowListener(new MainFrameAdapter());
		
	}
	
	private void initMVCWorkspace() {
		workspaceTree 	= new WorkspaceTree();
		workspaceModel 	= new WorkspaceModel();
		
		workspaceTree.setModel(workspaceModel);
		ToolTipManager.sharedInstance().registerComponent(workspaceTree);
		
		workspaceTree.setRootVisible(false);
	}
	
	private void initGUI() {
		setLayout(new BorderLayout());
		
		menuBar = new MenuBar();
		setJMenuBar(menuBar);
		
		toolBar = new ToolBar();
		add(toolBar, BorderLayout.NORTH);
		
		pallete = new Pallete();
		add(pallete, BorderLayout.EAST);
		
		statusBar = new StatusBar();
		add(statusBar, BorderLayout.SOUTH);
		
		desktopPane = new JDesktopPane();
			
		JScrollPane scroll=new JScrollPane(workspaceTree);
		scroll.setMinimumSize(new Dimension(200,150));
		JSplitPane split=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scroll,desktopPane);
		add(split,BorderLayout.CENTER);
		split.setDividerLocation(250);
		
		aboutDialog = new AboutDialog(this);
		

		Action doNothing = new AbstractAction() {
		    public void actionPerformed(ActionEvent e) {
		    	if (workspaceTree.getSelectionPath().getLastPathComponent() instanceof Project){  
		    		workspaceTree.startEditingAtPath(workspaceTree.getSelectionPath());
		    	}
		    	
		    }
		};
		workspaceTree.getInputMap().put(KeyStroke.getKeyStroke("F2"),
		                            "doNothing");
		workspaceTree.getActionMap().put("doNothing",
		                             doNothing);
	}
	
	private void setLookNFeel() {
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
	}
	
	public static MainFrame getInstance() {
		if(instance == null) {
			instance = new MainFrame();
			instance.masterInit();
			instance.initGUI();
			instance.setLookNFeel();
			instance.setVisible(true);
		}
		return instance;
	}

	/**
	 * @return the desktopPane
	 */
	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	/**
	 * @return the workspaceTree
	 */
	public WorkspaceTree getWorkspaceTree() {
		return workspaceTree;
	}

	/**
	 * @return the workspaceModel
	 */
	public WorkspaceModel getWorkspaceModel() {
		return workspaceModel;
	}

	/**
	 * @return the actionManager
	 */
	public ActionManager getActionManager() {
		return actionManager;
	}

	/**
	 * @return the aboutDialog
	 */
	public AboutDialog getAboutDialog() {
		return aboutDialog;
	}

	/**
	 * @return the statusBar
	 */
	public StatusBar getStatusBar() {
		return statusBar;
	}

	/**
	 * @return the pallete
	 */
	public Pallete getPallete() {
		return pallete;
	}
	
	public int getPasteCounter() {
		return ++pasteCounter;
	}
	
	public void setPasteCounter(int pasteCounter) {
		this.pasteCounter = pasteCounter;
	}
	
	public Clipboard getClipboard() {
		return clipboard;
	}

	/* (non-Javadoc)
	 * @see java.awt.datatransfer.ClipboardOwner#lostOwnership(java.awt.datatransfer.Clipboard, java.awt.datatransfer.Transferable)
	 */
	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// TODO Auto-generated method stub
		
	}
}
