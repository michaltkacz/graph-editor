import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class GraphEditor extends JFrame implements ActionListener{

	private static final long serialVersionUID = 508317535368185508L;
	
	private static final String APP_TITLE = "Graph Editor";
	private static final String APP_INFO =
		    "Show example graph:\n"
		  + "Menu \"File\" => \"Show example\"\n"
		  + "(NOTE: it deletes current graph).\n\n"
		  + "Mouse:\n"
		  + "(action depends on mouse position):\n"
		  + "- LPM - move graph/node/edge\n"
		  + "- PPM - context menu general/node/edge\n\n"
		  + "Keyboard shortcuts:\n"
		  + "- \"q\" - show/hide grid helper\n"
		  + "- \"z\" - add node\n"
		  + "- \"x\" - add edge\n"
		  + "- Alt + \"LETTER\" - open related menu\n"
		  + "- Ctrl + \"s\" - save to file\n"
		  + "- Ctrl + \"o\" - load from file\n\n"
		  + "When cursor is over special node/edge\n"
		  + "- \"r\", \"g\", \"b\" - set color to red/green/blue\n"
		  + "- \"+\" - increase size\n"
		  + "- \"-\" - decrease size\n";
	
	
	private static final String AUTHOR_INFO =   
			  "Program:  " + APP_TITLE + "\n" 
			+ "Author:    Michal Tkacz \n"
			+ "Data:      november 2019";
	
	private static final String AUTOSAVE_FILE = "AUTOSAVE.bin";
	
	WindowAdapter windowListener = new WindowAdapter() {
		@Override
		public void windowClosed(WindowEvent e) {
			// metoda dispose()
			JOptionPane.showMessageDialog(null, "Program closed!");
		}

		@Override
		public void windowClosing(WindowEvent e) {
			// krzy¿yk
			windowClosed(e);
		}

	};
	

	public static void main(String[] args) {
		new GraphEditor();

	}

	GraphPanel graphPanel = new GraphPanel(null);
	
	JMenuBar menuBar = new JMenuBar();
	
	JMenu fileMenu = new JMenu("File");
	JMenuItem newGraphMenuItem = new JMenuItem("New graph", KeyEvent.VK_N);
	JMenuItem showExampleMenuItem = new JMenuItem("Show example", KeyEvent.VK_E);
	JMenuItem saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
	JMenuItem loadMenuItem = new JMenuItem("Load", KeyEvent.VK_L);
	
	JMenu graphMenu = new JMenu("Graph");
	JMenuItem nodesMenuItem = new JMenuItem("Show list of nodes", KeyEvent.VK_N);
	JMenuItem edgesMenuItem = new JMenuItem("Show list of edges", KeyEvent.VK_E);
	
	JMenu helpMenu = new JMenu("Help");
	JMenuItem drawGridCheckBoxMenuItem = new JCheckBoxMenuItem("Show grid");
	JMenuItem appMenuItem = new JMenuItem("About", KeyEvent.VK_A);
	JMenuItem authorMenuItem = new JMenuItem("Author", KeyEvent.VK_U);
	
	private GraphEditor() {
		super(APP_TITLE);
		setSize(800, 600);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setContentPane(graphPanel);
		UIManager.put("OptionPane.messageFont", new Font("Monospaced", Font.BOLD, 12));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent event) {
				graphPanel.serializeGraph(AUTOSAVE_FILE);
			}

			@Override
			public void windowClosing(WindowEvent event) {
				windowClosed(event);
			}
		});
		
		addActionListeners();
		createMenuBar();	
		
		setVisible(true);
		
		showInstruction();

		graphPanel.deserializeGraph(AUTOSAVE_FILE);
	
	}
	
	private void addActionListeners() {
		newGraphMenuItem.addActionListener(this);
		showExampleMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		loadMenuItem.addActionListener(this);
		nodesMenuItem.addActionListener(this);
		edgesMenuItem.addActionListener(this);
		drawGridCheckBoxMenuItem.addActionListener(this);
		appMenuItem.addActionListener(this);
		authorMenuItem.addActionListener(this);
	}
	
	private void createMenuBar() {
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(newGraphMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(showExampleMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(loadMenuItem);
		menuBar.add(fileMenu);
		
		graphMenu.setMnemonic(KeyEvent.VK_G);
		graphMenu.add(nodesMenuItem);
		graphMenu.add(edgesMenuItem);
		menuBar.add(graphMenu);
		
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.add(drawGridCheckBoxMenuItem);
		helpMenu.addSeparator();
		helpMenu.add(appMenuItem);
		helpMenu.add(authorMenuItem);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object eSource = e.getSource();
		
		if(eSource == newGraphMenuItem) {
			graphPanel.createNewGraph();
		}
		
		if(eSource == showExampleMenuItem) {
			graphPanel.showExampleGraph();
		}
		
		if(eSource == saveMenuItem) {
			graphPanel.serializeGraph();
		}
		
		if(eSource == loadMenuItem) {
			graphPanel.deserializeGraph();
		}
		
		if(eSource == nodesMenuItem) {
			graphPanel.showNodesList();
		}
		
		if(eSource == edgesMenuItem) {
			graphPanel.showEdgesList();
		}
		
		if(eSource == drawGridCheckBoxMenuItem) {
			graphPanel.enableGrid(drawGridCheckBoxMenuItem.isSelected());
		}

		if(eSource == appMenuItem) {
			showInstruction();
		}
		
		if(eSource == authorMenuItem) {
			showAuthorInfo();
		}
	}
	
	private void showInstruction() {
		JOptionPane.showMessageDialog(this, APP_INFO, "About", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showAuthorInfo() {
		JOptionPane.showMessageDialog(this, AUTHOR_INFO, "Author", JOptionPane.INFORMATION_MESSAGE);
	}

}
