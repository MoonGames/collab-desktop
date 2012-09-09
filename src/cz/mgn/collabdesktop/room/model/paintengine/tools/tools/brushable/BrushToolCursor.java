/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import cz.mgn.collabcanvas.interfaces.visible.ToolCursor;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush.Brush;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class BrushToolCursor implements ToolCursor {

    protected Brush brush;
    protected BufferedImage brushCursorOriginal;
    protected BufferedImage brushCursorScaled;
    protected float lastScale = 1f;

    public BrushToolCursor(Brush brush) {
        this.brush = brush;
        generateBrushCursor();
    }

    protected void generateBrushCursor() {
        brushCursorOriginal = OutlineUtil.generateOutline(brush.getScaledImage(), Color.BLACK, false);
        generateBrushCursorScaled(lastScale);
    }

    protected void generateBrushCursorScaled(float scale) {
        BufferedImage outlineSource = brush.getScaledImage();
        int w = (int) (scale * outlineSource.getWidth());
        int h = (int) (scale * outlineSource.getHeight());
        BufferedImage outlineSourceScaled = new BufferedImage(w, h, outlineSource.getType());

        Graphics2D g = (Graphics2D) outlineSourceScaled.getGraphics();
        g.drawImage(outlineSource, 0, 0, w, h, null);
        g.dispose();
        lastScale = scale;
        brushCursorScaled = OutlineUtil.generateOutline(outlineSourceScaled, Color.BLACK, true);
    }

    @Override
    public Point getRelativeLocatoin() {
        return new Point();
    }

    @Override
    public int getLocationMode() {
        return ToolCursor.LOCATION_MODE_CENTER;
    }

    @Override
    public BufferedImage getCursorImage() {
        return brushCursorOriginal;
    }

    @Override
    public boolean isScalingSupported() {
        return true;
    }

    @Override
    public BufferedImage getScaledCursorImage(float scale) {
        if (scale != lastScale) {
            generateBrushCursorScaled(scale);
        }
        return brushCursorScaled;
    }
}
