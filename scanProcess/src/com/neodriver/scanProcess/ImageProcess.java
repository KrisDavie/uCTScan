package com.neodriver.scanProcess;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageProcess {

	public ImageProcess(int arg0, String arg1, int arg2, int arg3, float arg4) throws IOException {

		// arg0 = position of the line (finalPos)
		// arg1 = directory path
		// arg2 = number of middle image (bigWidthFileNum)
		// arg3 = number of files to use
		// arg4 = number of um per pixel
		
		int total = 0;
		int avg = 0;
		int numFiles = arg3;
		int fNum = 0;
		int finalPos = arg0;
		File imgDir = new File(arg1);
		File[] dirList = imgDir.listFiles();
		Arrays.sort(dirList);
		int bigWidthFileNum = arg2;
		int dirLength = (int) imgDir.list().length;
		//System.out.println(dirLength);
		int[] finalWidths = new int[dirLength];
		
		for (File child : dirList) {
			if (child.getName().contains("spr.bmp") || child.getName().contains(".log") ) {
				continue;
			}
			
			if (child.getName().contains(".bmp")) {
			
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
		}

		for (int i = (bigWidthFileNum - numFiles); i <= (bigWidthFileNum + numFiles); i++) {
			total += finalWidths[i];
			avg = total / ((arg3 * 2) + 1); // arg 3 = files either side, for both sides, plus the middle
		}
		System.out.println("The final average is: " + (avg * arg4));
	}

}
