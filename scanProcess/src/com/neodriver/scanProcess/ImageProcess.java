package com.neodriver.scanProcess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.neodriver.scanProcess.gui.MainFrame;

public class ImageProcess {

	public ImageProcess(int arg0, String arg1, String arg2, String arg3) {

		// arg0 = position of the line
		// arg1 = directory path
		// arg2 = number of files to use
		// arg3 = number of um per pixel
		
		int total = 0;
		int avg = 0;
		int lines = 0;
		float pixelSize = 0;
		int maxFiles = 0;
		Scanner input = new Scanner(System.in);
		int fNum = 0;
		int finalPos = arg0;
		File imgDir = new File(arg1);
		int dirLength = (int) imgDir.length();
		int[] finalWidths = new int[dirLength];
		for (File child : imgDir.listFiles()) {

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

		// How many images are we going to use?
		if (arg1 == null) {
			while (true) {
				System.out.println("How many shall we use?");
				lines = input.nextInt();
				if (lines <= maxFiles) {
					System.out.println("Ok, we will use " + lines + " images.");
					break;
				}
				System.out.println("That is too many. Please enter another number");
			}
		} else {
			lines = Integer.valueOf(arg1);
		}
		
		for (int i = (bigWidthFileNum - lines); i <= (bigWidthFileNum + lines); i++) {
			total += finalWidths[i];
			avg = total / finalWidths.length;
		}

		if (arg2 == null) {
			System.out.println("How many micrometers per pixel? (to 3 dp)");
			pixelSize = input.nextFloat();

		} else {
			pixelSize = Float.valueOf(arg2);
		}

		System.out.println("The final average is: " + (avg * 9.792));
	}

}
