/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.latex;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class LatexToolImage implements ToolImage {

    protected float lastScale = 1f;
    protected BufferedImage source;
    protected BufferedImage toolImage;
    protected BufferedImage toolImageScaled;

    public LatexToolImage(BufferedImage source) {
        this.source = source;
        generateImages();
    }

    protected void generateImages() {
        generateScaledImage();
        toolImage = toolImageScaled;
    }

    protected void generateScaledImage() {
        int w = (int) Math.max(lastScale * source.getWidth(), 1);
        int h = (int) Math.max(lastScale * source.getHeight(), 1);
        toolImageScaled = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) toolImageScaled.getGraphics();
        g.drawImage(source, 0, 0, w, h, null);
        g.dispose();
        toolImageScaled = OutlineUtil.generateOutline(toolImageScaled, Color.GRAY, true);
    }

    @Override
    public Point getRelativeLocatoin() {
        return new Point(-toolImage.getWidth() / 2, -toolImage.getHeight() / 2);
    }

    @Override
    public BufferedImage getToolImage() {
        return toolImage;
    }

    @Override
    public boolean isScalingSupported() {
        return true;
    }

    @Override
    public BufferedImage getScaledToolImage(float f) {
        if (lastScale != f) {
            lastScale = f;
            generateScaledImage();
        }
        return toolImageScaled;
    }
}
