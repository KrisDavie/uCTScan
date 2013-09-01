package com.neodriver.scanProcess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageCount {

	public int bigWidthFileNum = 0;
	public int[] finalWidths = null;
	public int finalPos = 0;
	public int maxFiles = 0;

	public ImageCount(String arg1) throws IOException {
		// args 0 = folder path
		// args 1 = number of images
		// args 2 = number of um per pixel

		String images;
		int fNum = 0;
		images = arg1;

		File dir = new File(images);
		int dirLength = new File(images).list().length;
		int[] filePos = new int[dirLength];
		int[] fileLen = new int[dirLength];
		
		// For each file in the directory given
		// check every line for every image and 
		// find the widest white line
		for (File child : dir.listFiles()) {
			System.out.println("Working on: " + child);
			BufferedImage curImg = ImageIO.read(child);
			int height = curImg.getHeight();
			int width = curImg.getWidth();
			int bigWidthPos = 0;
			int[] widths = new int[height];

			for (int y = 0; y < height; y++) {
				int widest = 0;
				int bsf = 1; //Black so far on this line
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

		// Work out the max files we can use on either side of the widest

		if (bigWidthFileNum <= (dirLength - bigWidthFileNum)) {
			maxFiles = bigWidthFileNum;
		} else {
			maxFiles = (dirLength - bigWidthFileNum - 1);
		}

	}

}
