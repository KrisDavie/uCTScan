package com.neodriver.scanProcess.gui;

//Imports
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.neodriver.scanProcess.calc;

//Extend JFrame so we can use its methods
public class MainFrame extends JFrame {

	// Convention
	private static final long serialVersionUID = 1L;

	// String to change title based on current calc
	String curApp = "Main";
	int winX = 300;
	int winY = 200;
	int test = 1;

	// Method to call the GUI
	public MainFrame() {

		initUI();

	}

	// Method to init the UI
	public void initUI() {

		// Create the menu
		JMenuBar MainMenu = new JMenuBar();

		// Make the File menu and allow to open wth F
		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		
		// Set the open button props
		JMenuItem fileOpen = new JMenuItem("Open");
		fileOpen.setMnemonic(KeyEvent.VK_O);
		fileOpen.setToolTipText("Select the directory to open");
		fileOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				return;
			}
		});

		// Set the exit button props
		JMenuItem fileExit = new JMenuItem("Exit");
		fileExit.setMnemonic(KeyEvent.VK_E);
		fileExit.setToolTipText("Exit the application");
		fileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});

		// Add the menu and it hierarchy
		file.add(fileExit);
		MainMenu.add(file);
		this.setJMenuBar(MainMenu);

		// Create new JPanel and add it to the frame
		JPanel panelButtons = new JPanel();
		JPanel panelInputs = new JPanel();
		JPanel panelDirectory = new JPanel();
		panelInputs.setLayout(new BoxLayout(panelInputs, BoxLayout.Y_AXIS));
		panelButtons.setLayout(new BorderLayout());
		panelDirectory.setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.SOUTH, panelButtons);
		getContentPane().add(BorderLayout.CENTER, panelInputs);
		getContentPane().add(BorderLayout.NORTH, panelDirectory);

		// Create the quit button and name it etc.
		JButton quitButton = new JButton("Quit");
		quitButton.setToolTipText("Quits the program");
		JButton runButton = new JButton("Run");
		quitButton.setToolTipText("Runs the program");

		// Listen to the quit button for an event (pressed) and quit
		quitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);

			}
		});

		// Listen to the run button for an event (pressed) and quit
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					calc calc1 = new calc(null);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		// Create the directory selector
		JFileChooser imagesFolder = new JFileChooser();
		imagesFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		// Create the input boxes

		JTextField imgsToUse = new JTextField(3);
		JTextField umPerPix = new JTextField(5);

		// Add the inputs to the input panel

		panelInputs.add(imgsToUse);
		panelInputs.add(umPerPix);

		// Add the button to the Button panel
		panelButtons.add(runButton, BorderLayout.WEST);
		panelButtons.add(quitButton, BorderLayout.EAST);

		// Set window properties
		this.setTitle("Science Calc v0.0 - " + curApp);
		this.setSize(winX, winY);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public static void main(String[] args) {

		// Set the window to look and feel like the current System
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Run!
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame main1 = new MainFrame();
				main1.setVisible(true);

			}
		});
	}
}
