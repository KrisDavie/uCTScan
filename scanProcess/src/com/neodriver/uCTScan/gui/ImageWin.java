package com.neodriver.uCTScan.gui;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageWin {
	
	public JDialog imageWin = new JDialog();
	public JPanel mainPanel = new JPanel();
	public JPanel imagePanel = new JPanel();
	public JPanel radioPanel = new JPanel();
	
	public ImageWin(int arg0, String arg1, int arg2) throws IOException {
		
		// arg0 = position of the line (finalPos)
		// arg1 = directory path
		// arg2 = number of middle image (bigWidthFileNum)
		File imgDir = new File(arg1);
		File[] dirList = imgDir.listFiles();
		Arrays.sort(dirList);
		int bigWidthFileNum = arg2;
		File toDisplay = dirList[bigWidthFileNum];
		
		BufferedImage curImg = ImageIO.read(toDisplay);
		int height = curImg.getHeight();
				
		JRadioButton autoRadio = new JRadioButton("Auto", true);
		JRadioButton manRadio = new JRadioButton("Manual", false);
		ButtonGroup buttons = new ButtonGroup();
		buttons.add(manRadio);
		buttons.add(autoRadio);
		JSlider position = new JSlider(JSlider.VERTICAL, 0, curImg.getHeight(), (curImg.getHeight() - arg0));
		mainPanel.add(position, BorderLayout.EAST);

		mainPanel.add(radioPanel, BorderLayout.NORTH);
		radioPanel.add(autoRadio, BorderLayout.EAST);
		radioPanel.add(manRadio);
		imageWin.add(mainPanel, BorderLayout.CENTER);
		mainPanel.add(imagePanel, BorderLayout.SOUTH);
		ImageLabel label = new ImageLabel(new ImageIcon(curImg));
		imagePanel.add(label);

	}

}

class ImageLabel extends JLabel {

	  public ImageLabel(String img) {
	    this(new ImageIcon(img));
	  }

	  public ImageLabel(ImageIcon icon) {
	    setIcon(icon);
	    // setMargin(new Insets(0,0,0,0));
	    setIconTextGap(0);
	    // setBorderPainted(false);
	    setBorder(null);
	    setText(null);
	    setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
	  }

	}

