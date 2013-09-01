package com.neodriver.scanProcess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class ImageCount {
	
	public int bigWidthFileNum = 0;
	public int[] finalWidths = null;
	public int finalPos = 0;

	public ImageCount(String arg1, String arg2, String arg3) throws IOException {
		// args 0 = folder path
		// args 1 = number of images
		// args 2 = number of um per pixel

		String images;
		String bigWidthFile = "";
		Scanner input = new Scanner(System.in);

		int fNum = 0;

		if (arg1 == null) {
			System.out.println("Please provide the folder path with the images:-");
			images = input.nextLine();
			System.out.println("You provided: " + images);
		} else {
			images = arg1;
		}

		File dir = new File(images);
		int dirLength = new File(images).list().length;
		int[] filePos = new int[dirLength];
		int[] fileLen = new int[dirLength];
		String[] fileNames = new String[dirLength];

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
				widths[y] = widest;

			}
			int bigWidth = 0;
			for (int i = 0; i < widths.length; i++) {
				if (widths[i] > bigWidth) {
					bigWidth = widths[i];
					bigWidthPos = i;
					bigWidthFile = child.toString();
				}
			}
			System.out.println("The widest line was: " + bigWidth + ". It was at position: " + bigWidthPos + ". In the file: " + bigWidthFile);
			filePos[fNum] = bigWidthPos;
			fileLen[fNum] = bigWidth;
			fileNames[fNum] = child.toString();
			fNum++;
		}

		// Find the single widest line across
		// all of the files we checked

		int bigWidth = 0;
		for (int i = 0; i < fileLen.length; i++) {
			if (fileLen[i] > bigWidth) {
				bigWidth = fileLen[i];
				bigWidthFileNum = i;
			}
		}
		finalPos = filePos[bigWidthFileNum];
		System.out.println("The final position we are going to use is: " + finalPos + ". This is from the file : " + fileNames[bigWidthFileNum]);

		// Reset a few vars and cycle through
		// the folder again, this time only
		// checking at the widest position
		fNum = 0;
		finalWidths = new int[dirLength];
		for (File child : dir.listFiles()) {

			BufferedImage curImg = ImageIO.read(child);
			int width = curImg.getWidth();
			int widest = 0;
			int bsf = 1; //Black so far on this line
			int firstWhite = 0;
			int lastWhite = 0;
			for (int x = 0; x < width; x++) {
				int rgb = curImg.getRGB(x, finalPos);
				if (rgb == -1 & bsf == 1) {
					firstWhite = x;
					bsf = 0;
				}
				if (rgb == -1) {
					lastWhite = x;
				}

			}

			widest = (lastWhite - firstWhite + 1);
			finalWidths[fNum] = widest;
			fNum++;
		}

		// Final calculations to work
		// out the average - taking in
		// a few user inputs
		int maxFiles = 0;

		System.out.println("We have " + dirLength + " files. The file with the largest line was " + (bigWidthFileNum + 1));
		if (bigWidthFileNum <= (dirLength - bigWidthFileNum - 1)) {
			System.out.println("We can use a max of " + bigWidthFileNum + " files either side.");
			maxFiles = bigWidthFileNum;
		} else {
			System.out.println("We can use a max of " + (dirLength - bigWidthFileNum - 1) + " files either side.");
		}

	}

}
