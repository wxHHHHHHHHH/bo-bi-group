package com.boss.bobi.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageCompressor {

    public static void compressImage(String inputImagePath, String outputImagePath, int newWidth, int newHeight) throws IOException {
        // Read the original image
        File inputFile = new File(inputImagePath);
        BufferedImage originalImage = ImageIO.read(inputFile);

        // Create a new buffered image with the new dimensions
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        // Create a buffered image to hold the scaled image
        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // Write the compressed image to a file
        File outputFile = new File(outputImagePath);
        ImageIO.write(outputImage, "jpg", outputFile);
    }

    public static void main(String[] args) {
        String inputImagePath = "D:\\08d660fd4911e2ea893f468c55eccd9c.jpg";
        String outputImagePath = "D:\\压缩图片.jpg";
        int newWidth = 100;  // Desired width
        int newHeight = 100; // Desired height

        try {
            compressImage(inputImagePath, outputImagePath, newWidth, newHeight);
            System.out.println("Image compressed successfully.");
        } catch (IOException e) {
            System.out.println("Error compressing image: " + e.getMessage());
        }
    }
}
