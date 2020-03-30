package com.conupods.pictureMapper;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

//import org.imgscalr.Scalr;

/**
 * This class and driver is used to transform Maps(images) to Boolean Arrays
 * You can use this driver to scale images using scalePNG("path-of-image", "where-to-save-with filename", "scale to size")
 */

//-2476490 == dark red (classroom)
//-534826 == light red (corridor)

public class PixelGridBoolean {

    private static final int walkable = -534826;

    /**
     * Get gradle import to work
     */
    /*
    public static void scalePNG(String path, String newPath, int targetSize) {
        try {
            BufferedImage scaledImage = Scalr.resize(ImageIO.read(new File(path)), targetSize);
            ImageIO.write(scaledImage, "png", new File(newPath));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
     */
    public static int[][] getRGBArray(BufferedImage image) {

        final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;

        int[][] result = new int[height][width];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel + 3 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel + 2 < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row][col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        return result;
    }

    public static String[] getArr(int[][] arr) {
        String[] sArr = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            String line = "";
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 0) {
                    line = line + '1';
                } else {
                    if (arr[i][j] == walkable) {
                        line = line + '0';
                    } else {
                        line = line + '1';
                    }
                }
            }
            sArr[i] = line;
        }
        return sArr;
    }

    public static char[][] getCharArr(int[][] arr) {
        char[][] boolArr = new char[arr.length][arr[arr.length - 1].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] == 0) {
                    boolArr[i][j] = '1';
                } else {
                    if (arr[i][j] == walkable) {
                        boolArr[i][j] = '0';
                    } else {
                        boolArr[i][j] = '1';
                    }
                }
            }
        }
        return boolArr;
    }

}
