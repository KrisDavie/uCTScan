package com.neodriver.scanProcess.gui;

//Imports
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.neodriver.scanProcess.ImageCount;

//Extend JFrame so we can use its methods
public class MainFrame extends JFrame {

	// Convention
	private static final long serialVersionUID = 1L;

	protected static final Exception FileNotFoundException = null;

	// String to change title based on current calc
	String curApp = "Main";
	int winX = 300;
	int winY = 220;
	
	final JFileChooser imagesFolder = new JFileChooser();
	final JTextField imgsToUse = new JTextField(3);
	final JTextField umPerPix = new JTextField(5);
	JLabel imgsLabel = new JLabel("How many images shall we use?");
	JLabel umLabel = new JLabel("How many micrometers per pixel?");
	final JTextField selFolder = new JTextField(15);
	JLabel folderPath = new JLabel("Please enter the path to the images folder");
	public int[] finalVals = null;
	public int bigWidthFileNum = 0;
	public int finalPos = 0;


	// Method to call the GUI
	public MainFrame() {
		initUI();
	}
	


	// Method to init the UI
	public void initUI() {

		// Set window properties
		this.setTitle("Image Process v0.0 - " + curApp);
		this.setSize(winX, winY);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

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
				imagesFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				imagesFolder.showOpenDialog(new Frame());
				selFolder.setText(imagesFolder.getSelectedFile().toString());
				try {
					calcFiles();
				} catch (IOException e) {
					e.printStackTrace();
				}
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
		file.add(fileOpen);
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

		
		// Create the quit button and name it etc.
		JButton quitButton = new JButton("Quit");
		quitButton.setToolTipText("Quits the program");
		final JButton runButton = new JButton("Run");
		quitButton.setToolTipText("Runs the program");
		JButton browseButton = new JButton("Browse...");
		browseButton.setToolTipText("Browse for the images folder");
		JButton addButton = new JButton("Add");
		addButton.setToolTipText("Adds the current setting for batch processing");

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
		panelButtons.add(addButton, BorderLayout.CENTER);

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
					File checkDir = new File(selFolder.getText());
					if (checkDir.exists()) {
						if (checkDir.isFile()) {
							throw FileNotFoundException;
						}
					} else {
						throw FileNotFoundException;
					}

				} catch (Exception e) {
					JOptionPane.showMessageDialog(runButton, "The path provided is not a valid directory!", "Error!", JOptionPane.ERROR_MESSAGE);
				}

				// Add a try to check if we are using less than the max usable images AND it is a number
				// Add a try to check if we are using a valid number for ums per pixel - maybe make an error if is very large or small?
			}


		});
		
		
		// Try and create the browse for directory button
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				imagesFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				imagesFolder.showOpenDialog(new Frame());
				selFolder.setText(imagesFolder.getSelectedFile().toString());
				try {
					calcFiles();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return;
			}
		});

		// Create the input boxes

	}

	public void calcFiles() throws IOException {
		 ImageCount count1 = new ImageCount(selFolder.getText(), imgsToUse.getText(), umPerPix.getText());
		 finalVals = count1.finalWidths;
		 bigWidthFileNum = count1.bigWidthFileNum;
		 finalPos = count1.finalPos;				 
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
