package com.neodriver.scanProcess.gui;

//Imports
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import com.neodriver.scanProcess.calc;

//Extend JFrame so we can use its methods
public class MainFrame extends JFrame {

	// Convention
	private static final long serialVersionUID = 1L;

	// String to change title based on current calc
	String curApp = "Main";
	int winX = 300;
	int winY = 220;
	int test = 1;
	

	// Method to call the GUI
	public MainFrame() {

		initUI();
		repaint();

	}

	// Method to init the UI
	public void initUI() {

		// Set window properties
		this.setTitle("Image Process v0.0 - " + curApp);
		this.setSize(winX, winY);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		final String folderText = "test";

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
		panelInputs.setLayout(new BoxLayout(panelInputs, BoxLayout.PAGE_AXIS));
		panelButtons.setLayout(new BorderLayout());
		panelDirectory.setLayout(new BorderLayout());
		getContentPane().add(BorderLayout.SOUTH, panelButtons);
		getContentPane().add(BorderLayout.NORTH, panelInputs);

		JTextField imgsToUse = new JTextField(3);
		JTextField umPerPix = new JTextField(5);
		JLabel imgsLabel = new JLabel("How many images shall we use?");
		JLabel umLabel = new JLabel("How many micrometers per pixel?");
		final JTextField selFolder = new JTextField(folderText);
		System.out.println(selFolder.getText());
		JLabel folderPath = new JLabel("Please enter the path to the images folder");

		// Create the quit button and name it etc.
		JButton quitButton = new JButton("Quit");
		quitButton.setToolTipText("Quits the program");
		JButton runButton = new JButton("Run");
		quitButton.setToolTipText("Runs the program");
		JButton browseButton = new JButton("Browse...");
		browseButton.setToolTipText("Browse for the images folder");

		// Add the inputs to the input panel

		panelInputs.add(folderPath);

		//selFolder.setText(folderText);
		panelInputs.add(selFolder);
		panelInputs.add(browseButton);
		panelInputs.add(imgsLabel);
		panelInputs.add(imgsToUse);
		panelInputs.add(umLabel);
		panelInputs.add(umPerPix);

		// Add the button to the Button panel
		panelButtons.add(runButton, BorderLayout.WEST);
		panelButtons.add(quitButton, BorderLayout.EAST);

		//getContentPane().add(BorderLayout.NORTH, panelDirectory);

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
		final JFileChooser imagesFolder = new JFileChooser();

		// Try and create the browse for directory button
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				imagesFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				imagesFolder.showOpenDialog(new Frame());
				selFolder.setText(imagesFolder.getSelectedFile().toString());
				return;
			}
		});

		// Create the input boxes

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
