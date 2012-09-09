/*
 ** Copyright 2005 Huxtable.com. All rights reserved.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool.tools.filters.filters;

import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 *              A filter which applies Gaussian blur to an image. This is a subclass of
 * ConvolveFilter which simply creates a kernel with a Gaussian distribution for
 * blurring.
 *
 *              @author Jerry Huxtable
 */
public class GaussianFilter {

    protected float[][] matrix;

    public GaussianFilter(float radius) {
        matrix = generateMatrix(radius);
    }

    protected float[][] generateMatrix(float radius) {
        int size = Math.round(radius * 4) + 1;
        float[][] matrix = new float[size][size];
        int mid = size / 2;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                double distance = Math.sqrt(Math.pow((x - mid), 2.0) + Math.pow((y - mid), 2.0));
                matrix[x][y] = (float) Math.pow(Math.E, -(Math.pow(distance, 2.0) / (2 * radius)));
            }
        }
//        float scale = 1f / matrixSum(matrix);
//        for (int x = 0; x < size; x++) {
//            for (int y = 0; y < size; y++) {
//                matrix[x][y] *= scale;
//            }
//        }
        return matrix;
    }

    protected float matrixSum(float[][] matrix) {
        float sum = 0;
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[0].length; y++) {
                sum += matrix[x][y];
            }
        }
        return sum;
    }

   
    protected int[] getBluredPixel(int x, int y, BufferedImage source, int w, int h) {
        int mid = matrix.length / 2;
        float scale = 0;
        int[] rgb = new int[4];
        for (int xx = 0; xx < matrix.length; xx++) {
            for (int yy = 0; yy < matrix[0].length; yy++) {
                int xG = x + xx - mid;
                int yG = y + yy - mid;
                if (xG >= 0 && yG >= 0 && xG < w && yG < h) {
                    scale += matrix[xx][yy];
                    int[] rgbL = source.getRaster().getPixel(xG, yG, new int[4]);
                    for (int i = 0; i < rgbL.length; i++) {
                        rgb[i] += rgbL[i] * matrix[xx][yy];
                    }
                }
            }
        }
        for (int i = 0; i < rgb.length; i++) {
            rgb[i] /= scale;
        }
        return rgb;
    }
}
