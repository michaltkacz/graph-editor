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
		    "Aby wyœwietliæ graf przyk³adowy:\n"
		  + "Menu \"Plik\" => \"Poka¿ przyk³ad\"\n"
		  + "(powoduje usuniêcie obecnego wyœwietlanego grafu).\n\n"
		  + "Obs³uga myszy:\n"
		  + "(akcja zale¿na od po³o¿enia kursora):\n"
		  + "- LPM - przesuwanie ca³ego grafu/wybranego wêz³a/wybranej krawêdzi\n"
		  + "- PPM - menu kontekstowe ogólne/wybranego wêz³a/wybranej krawêdzi\n\n"
		  + "Skróty klawiszowe:\n"
		  + "- \"q\" - w³¹cz/wy³¹cz siatkê pomocnicz¹\n"
		  + "- \"z\" - dodaj wêzê³ w miejscu kursora\n"
		  + "- \"x\" - dodaj krawêdŸ wychodz¹c¹ z wêz³a bêd¹cego pod kursorem\n"
		  + "- Alt + \"LITERA\" - otwiera menu odpowiadaj¹ce literze podkreœlonej\n"
		  + "                   na pasku menu\n"
		  + "- Ctrl + \"s\" - zapisz graf do pliku\n"
		  + "- Ctrl + \"o\" - wczytaj graf z pliku\n\n"
		  + "Gdy kursor znajduje siê nad wêz³em/krawêdzi¹ specjaln¹\n"
		  + "- \"r\", \"g\", \"b\" - ustaw kolor czerwony/zielony/niebieski\n"
		  + "- \"+\" - zwiêksz promieñ wêz³a/gruboœæ krawêdzi\n"
		  + "- \"-\" - zmiejsz promieñ wêz³a/gruboœæ krawêdzi\n"
		  + "\n\n Program posiada funkcjê autozapisu i autowczytania grafu.";
	
	
	private static final String AUTHOR_INFO =   
			  "Program do edycji grafów.\n"
			+ "Program:  " + APP_TITLE + "\n" 
			+ "Autor:    Micha³ Tkacz \n"
			+ "Data:     listopad 2019 r.";
	
	private static final String AUTOSAVE_FILE = "AUTOSAVE.bin";
	
	WindowAdapter windowListener = new WindowAdapter() {
		@Override
		public void windowClosed(WindowEvent e) {
			// metoda dispose()
			JOptionPane.showMessageDialog(null, "Program zakoñczy³ dzia³anie!");
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
	
	JMenu fileMenu = new JMenu("Plik");
	JMenuItem newGraphMenuItem = new JMenuItem("Nowy graf", KeyEvent.VK_N);
	JMenuItem showExampleMenuItem = new JMenuItem("Poka¿ przyk³ad", KeyEvent.VK_P);
	JMenuItem saveMenuItem = new JMenuItem("Zapisz graf", KeyEvent.VK_Z);
	JMenuItem loadMenuItem = new JMenuItem("Wczytaj graf", KeyEvent.VK_W);
	
	JMenu graphMenu = new JMenu("Graf");
	JMenuItem nodesMenuItem = new JMenuItem("Lista wêz³ów grafu", KeyEvent.VK_W);
	JMenuItem edgesMenuItem = new JMenuItem("Lista krawêdzi grafu", KeyEvent.VK_K);
	
	JMenu helpMenu = new JMenu("Pomoc");
	JMenuItem drawGridCheckBoxMenuItem = new JCheckBoxMenuItem("Rysuj siatkê");
	JMenuItem appMenuItem = new JMenuItem("O programie", KeyEvent.VK_P);
	JMenuItem authorMenuItem = new JMenuItem("O autorze", KeyEvent.VK_A);
	
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
		fileMenu.setMnemonic(KeyEvent.VK_P);
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
		
		helpMenu.setMnemonic(KeyEvent.VK_O);
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
		JOptionPane.showMessageDialog(this, APP_INFO, "Informacje o programie", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void showAuthorInfo() {
		JOptionPane.showMessageDialog(this, AUTHOR_INFO, "Informacje o autorze", JOptionPane.INFORMATION_MESSAGE);
	}

}
