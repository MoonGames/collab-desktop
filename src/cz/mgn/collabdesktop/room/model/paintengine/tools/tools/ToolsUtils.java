/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools;

import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class ToolsUtils {

    public static BufferedImage transformToolIcon(BufferedImage source, int width, int height) {
        BufferedImage result = source;
        int rw = result.getWidth();
        int rh = result.getHeight();
        if (rw != width || rh != height) {
            float scale = Math.min((float) (width / rw), (float) (height / rh));
            int nw = (int) (rw * scale);
            int nh = (int) (rh * scale);
            int x = 0, y = 0;
            if (nw < width) {
                x = (width - nw) / 2;
            }
            if (nh < height) {
                y = (height - nh) / 2;
            }
            result = new BufferedImage(width, height, source.getType());
            Graphics2D g = (Graphics2D) result.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(source, x, y, nw, nh, null);
            g.dispose();
        }
        return result;
    }
}
