/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel;

/**
 *
 * @author indy
 */
public class UpdateRect {

    protected int x = 0;
    protected int y = 0;
    protected int width = 0;
    protected int height = 0;

    public UpdateRect() {
        reset();
    }

    public void reset() {
        x = -1;
        y = -1;
        width = -1;
        height = -1;
    }

    public void add(UpdateRect rect) {
        int[] rect1 = rect.getRect();
        if (rect1 != null) {
            add(rect1[0], rect1[1], rect1[2], rect1[3]);
        }
    }

    public void add(int x, int y, int width, int height) {
        int x2 = Math.max(this.x + this.width, x + width);
        int y2 = Math.max(this.y + this.height, y + height);

        if (this.x == -1) {
            this.x = x;
        } else {
            this.x = Math.min(x, this.x);
        }
        if (this.y == -1) {
            this.y = y;
        } else {
            this.y = Math.min(y, this.y);
        }
        this.width = x2 - this.x;
        this.height = y2 - this.y;
    }

    public int[] getRect() {
        if (x == -1) {
            return null;
        }
        return new int[]{Math.max(x, 0), Math.max(y, 0), Math.max(width, 1), Math.max(height, 1)};
    }
}
