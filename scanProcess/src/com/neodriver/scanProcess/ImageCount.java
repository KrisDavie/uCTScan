package com.neodriver.scanProcess;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ImageCount {

	public int bigWidthFileNum = 0;
	public int[] finalWidths = null;
	public int finalPos = 0;
	public int maxFiles = 0;
	public String images = null;
	public int dirLength = 0;
	public int curFile = 1;
	public double pixelSize = 0;
	public int pixelMultiplier = 1;
	public double origPixelSize = 0;

	// public final Timer progressTimer = null;

	public ImageCount(String arg1) throws IOException, InterruptedException {

		// arg1 = folder path

		images = arg1;
		int fNum = 0;
		File dir = new File(images);
		File[] dirList = dir.listFiles();
		Arrays.sort(dirList);

		for (File file : dirList) {
			if (!file.getName().contains("spr.bmp") && file.getName().contains(".bmp")) {
				System.out.println(file);
				dirLength++;
			}
		}

		int[] filePos = new int[dirLength]; // Check this
		int[] fileLen = new int[dirLength]; // and this

		JFrame progressFrame = new JFrame();
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressFrame.setVisible(true);
		progressFrame.setTitle("Progress");
		progressFrame.setSize(300, 100);
		progressFrame.setLocationRelativeTo(null);
		JPanel progressPanel = new JPanel();
		// final JLabel progressSoFar = new JLabel("Processing images " +
		// curFile + " of " + dirLength);
		final JLabel progressSoFar = new JLabel("Processing images");
		progressFrame.add(progressPanel);
		progressPanel.add(progressSoFar, BorderLayout.NORTH);
		progressPanel.add(progressBar, BorderLayout.SOUTH);

		// For each file in the directory given
		// check every line for every image and
		// find the widest white line
		for (File child : dirList) {
			if (child.getName().contains("spr.bmp")) {
				continue;
			}

			if (child.getName().contains(".log")) {
				BufferedReader lineReader = new BufferedReader(new FileReader(child));
				String line = null;
				while ((line = lineReader.readLine()) != null) {
					System.out.println(line);

					if (line.contains("2x2x2")) {
						pixelMultiplier = 2;
						System.out.println("Found 2x multiplier");
					}
					if (line.contains("3x3x3")) {
						pixelMultiplier = 3;
						System.out.println("Found 3x multiplier");
					}
					if (line.contains("4x4x4")) {
						pixelMultiplier = 4;
						System.out.println("Found 4x multiplier");
					}

					if (line.contains("Pixel Size") && !line.contains("Camera") && !line.contains("Image")) {						origPixelSize = Double.parseDouble(line.substring(line.lastIndexOf('.') - 1));
						System.out.println("Orig pixel size is" + origPixelSize);
					}
				}
				pixelSize = origPixelSize * pixelMultiplier;
			}

			if (child.getName().contains(".bmp")) {
				progressSoFar.setText("Processing image " + curFile + " of " + dirLength);
				BufferedImage curImg = ImageIO.read(child);
				int height = curImg.getHeight();
				int width = curImg.getWidth();
				int bigWidthPos = 0;
				int[] widths = new int[height];

				for (int y = 0; y < height; y++) {
					int widest = 0;
					int bsf = 1; // Black so far on this line
					int firstWhite = 0;
					int lastWhite = 0;
					for (int x = 0; x < width; x++) {
						int rgb = curImg.getRGB(x, y);
						if (rgb == -1 & bsf == 1) {
							firstWhite = x;
							bsf = 0;
						}
						if (rgb == -1) {
							lastWhite = x;
						}
					}

					widest = (lastWhite - firstWhite + 1);

					// Make array with widths for each Y
					widths[y] = widest;

				}

				// Find widest point for file
				int bigWidth = 0;
				for (int i = 0; i < widths.length; i++) {
					if (widths[i] > bigWidth) {
						bigWidth = widths[i];
						bigWidthPos = i;

					}
				}
				// Add the width and line number to the arrays for this file
				filePos[fNum] = bigWidthPos;
				fileLen[fNum] = bigWidth;
				fNum++;
				curFile++;

			}

		}

		// Find the single widest line across all of the files we checked

		int bigWidth = 0;
		for (int i = 0; i < fileLen.length; i++) {
			if (fileLen[i] > bigWidth) {
				bigWidth = fileLen[i];
				bigWidthFileNum = i;
			}
		}
		// This is the line number of the widest line
		finalPos = filePos[bigWidthFileNum];
		System.out.println(finalPos + "   " + dirList[bigWidthFileNum]);

		// Work out the max files we can use on either side of the widest

		if (bigWidthFileNum <= (dirLength - bigWidthFileNum)) {
			maxFiles = (bigWidthFileNum - 1);
		} else {
			maxFiles = (dirLength - bigWidthFileNum);
		}
		System.out.println(dirLength);
		progressFrame.setVisible(false);

	}

}
