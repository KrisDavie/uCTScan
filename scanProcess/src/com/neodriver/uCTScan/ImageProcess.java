package com.neodriver.uCTScan;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class ImageProcess {

	public ImageProcess(int arg0, String arg1, int arg2, int arg3, double arg4, int arg5, String arg6) throws IOException {

		// arg0 = position of the line (finalPos)
		// arg1 = directory path
		// arg2 = number of middle image (bigWidthFileNum)
		// arg3 = number of files to use
		// arg4 = number of um per pixel
		// arg5 = number of files in dir we are using
		// arg6 = output file
		
		int total = 0;
		int stdevTot = 0;
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
		Date date = new Date();
		DateFormat fd = new SimpleDateFormat("(dd-MM-yy HHmm)");
		String formattedDate = fd.format(date);
		File outputFile = null;
		//System.out.println("writing file");
		try {
			outputFile = new File(arg1 + File.separator + "uTScan output.csv");
			if (!outputFile.exists()) {
				outputFile.createNewFile();
				//System.out.println("writing file1");
			} else {
				outputFile = new File(arg1 + File.separator + "uCTScan output " + formattedDate + ".csv");
				if (!outputFile.exists()) {
					outputFile.createNewFile();
					//System.out.println("writing file2");
				}
			}		
			

		} catch (IOException e) {
			e.printStackTrace();
		}

		FileWriter outputWrite = new FileWriter(outputFile, true);
		BufferedWriter wb = new BufferedWriter(outputWrite);
		wb.write("uCTScan output - " + date);
		wb.newLine();
		wb.newLine();
		wb.write("Path to widest File:," + dirList[bigWidthFileNum]);
		wb.write("Widest point (pixels):," + finalPos);
		wb.newLine();
		wb.newLine();
		wb.write("File Location,Width (um)");
		wb.newLine();
		wb.newLine();
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
			wb.write(dirList[i] + "," + (finalWidths[i] * arg4) + ",um");
			wb.newLine();
			total += (finalWidths[i] * arg4);
			avg = total / ((arg3 * 2) + 1); // arg 3 = files either side, for both sides, plus the middle
		}
		wb.newLine();
		wb.newLine();
		wb.write("Average:," + avg);
		wb.newLine();
		
		for (int i = (bigWidthFileNum - numFiles); i <= (bigWidthFileNum + numFiles); i++) {
			stdevTot += Math.pow(((finalWidths[i] * arg4) - avg), 2);
		}
		wb.write("stDev:," + (Math.sqrt(stdevTot/((arg3 * 2) + 1))));
		wb.close();
		JOptionPane.showMessageDialog(null, "Analysis finished. Output file is located in the image folder", "Finished!", JOptionPane.INFORMATION_MESSAGE);
	}

}
