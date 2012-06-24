/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.buffer;

import cz.mgn.collabdesktop.gui.desk.executor.CommandExecutor;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.utils.ImageProcessor;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *   @author indy
 */
public class Buffer implements Runnable {

    protected static final long editsTimeOut = 2000l;
    protected CommandExecutor executor = null;
    protected ArrayList<Edit> edits = new ArrayList<Edit>();
    protected BufferedImage imageAdd = null;
    protected BufferedImage imageRemove = null;
    protected final int BLOCK_SIZE = 80;
    protected boolean[][] changesAdd = null;
    protected boolean[][] changesRemove = null;
    protected int layerID = -1;
    protected volatile boolean running = false;

    public Buffer(CommandExecutor executor) {
        this.executor = executor;
        Thread t = new Thread(this);
        t.start();
    }

    protected void controlTimeOutEdits() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < edits.size(); i++) {
            Edit edit = edits.get(i);
            if ((time - edit.getTime()) > editsTimeOut) {
                edits.remove(i);
                i--;
            }
        }
    }

    protected void makeEdits() {
        if (imageAdd != null) {
            long time = System.currentTimeMillis();
            BufferedImage adds = new BufferedImage(imageAdd.getWidth(), imageAdd.getHeight(), imageAdd.getType());
            imageAdd.copyData(adds.getRaster());
            BufferedImage removes = new BufferedImage(imageRemove.getWidth(), imageRemove.getHeight(), imageRemove.getType());
            imageRemove.copyData(removes.getRaster());

            imageRemove = new BufferedImage(imageRemove.getWidth(), imageRemove.getHeight(), imageRemove.getType());
            Graphics2D g = (Graphics2D) imageRemove.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, imageRemove.getWidth(), imageRemove.getHeight());
            g.dispose();
            imageAdd = new BufferedImage(imageAdd.getWidth(), imageAdd.getHeight(), imageAdd.getType());

            ArrayList<Edit> editsl = new ArrayList<Edit>();
            for (int x = 0; x < changesAdd.length; x++) {
                for (int y = 0; y < changesAdd[0].length; y++) {
                    if (changesAdd[x][y]) {
                        int xr = x * BLOCK_SIZE;
                        int yr = y * BLOCK_SIZE;
                        BufferedImage editBox = createEditBox(xr, yr, BLOCK_SIZE, BLOCK_SIZE, adds);
                        editsl.add(new Edit(false, executor.generateNextID(), editBox, xr, yr, time));
                        changesAdd[x][y] = false;
                    }
                }
            }

            for (int x = 0; x < changesRemove.length; x++) {
                for (int y = 0; y < changesRemove[0].length; y++) {
                    if (changesRemove[x][y]) {
                        int xr = x * BLOCK_SIZE;
                        int yr = y * BLOCK_SIZE;
                        BufferedImage editBox = createEditBox(xr, yr, BLOCK_SIZE, BLOCK_SIZE, removes);
                        editsl.add(new Edit(true, executor.generateNextID(), editBox, xr, yr, time));
                        changesRemove[x][y] = false;
                    }
                }
            }

            edits.addAll(editsl);
            executor.sendPaintChanges(editsl, layerID);
        }
    }

    protected BufferedImage createEditBox(int x, int y, int width, int height, BufferedImage source) {
        int x2 = Math.min(x + width, source.getWidth());
        int y2 = Math.min(y + height, source.getHeight());
        width = Math.max(x2 - x, 1);
        height = Math.max(y2 - y, 1);

        BufferedImage result = new BufferedImage(width, height, source.getType());
        for (int xx = x; xx < x2; xx++) {
            for (int yy = y; yy < y2; yy++) {
                int pixel = source.getRGB(xx, yy);
                result.setRGB(xx - x, yy - y, pixel);
            }
        }
        return result;
    }

    protected void addToImage(BufferedImage edit, int x, int y, int width, int height) {
        ImageProcessor.addToImage(edit, imageAdd, 0, 0, x, y, width, height);
    }

    protected void removeFromImage(BufferedImage edit, int x, int y, int width, int height) {
        ImageProcessor.removeFromImage(edit, imageRemove, 0, 0, x, y, width, height);
    }

    protected void editsToImage(BufferedImage editImage, int x, int y, int width, int height) {
        for (int i = 0; i < edits.size(); i++) {
            Edit edit = edits.get(i);
            boolean a = (edit.getX() + edit.getImage().getWidth()) > x;
            boolean b = edit.getX() < (x + width);
            boolean c = (edit.getY() + edit.getImage().getHeight()) > y;
            boolean d = edit.getY() < (y + height);
            if (a && b && c && d) {
                if (!edit.isRemove()) {
                    ImageProcessor.addToImage(editImage, edit.getImage(), edit.getX(), edit.getY(), x, y, width, height);
                } else {
                    ImageProcessor.removeFromImage(editImage, edit.getImage(), edit.getX(), edit.getY(), x, y, width, height);
                }
            }
        }

    }

    protected void changesArrayUpdate(boolean[][] array, int x, int y, int width, int height) {
        int x2 = Math.min(width + x, array.length * BLOCK_SIZE);
        int y2 = Math.min(height + y, array[0].length * BLOCK_SIZE);

        x = Math.max(x / BLOCK_SIZE, 0);
        y = Math.max(y / BLOCK_SIZE, 0);
        x2 = (x2 - 1) / BLOCK_SIZE;
        y2 = (y2 - 1) / BLOCK_SIZE;
        for (int xx = x; xx <= x2; xx++) {
            for (int yy = y; yy <= y2; yy++) {
                array[xx][yy] = true;
            }
        }
    }

    protected void removeEdit(int identification) {
        for (int i = 0; i < edits.size(); i++) {
            if (edits.get(i).getIdentification() == identification) {
                edits.remove(i);
                return;
            }
        }
    }

    public void noLayer() {
        synchronized (this) {
            if (imageAdd != null) {
                layerID = -1;
                imageAdd = null;
                imageRemove = null;
                changesAdd = null;
                changesRemove = null;
            }
        }
    }

    public void setLayer(int layerID, int width, int height) {
        synchronized (this) {
            makeEdits();
            noLayer();
            this.layerID = layerID;
            imageRemove = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

            Graphics2D g = (Graphics2D) imageRemove.getGraphics();
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);
            g.dispose();

            changesRemove = new boolean[width / BLOCK_SIZE + 1][height / BLOCK_SIZE + 1];
            for (int x = 0; x < changesRemove.length; x++) {
                for (int y = 0; y < changesRemove[0].length; y++) {
                    changesRemove[x][y] = false;
                }

            }
            changesAdd = new boolean[width / BLOCK_SIZE + 1][height / BLOCK_SIZE + 1];
            for (int x = 0; x < changesAdd.length; x++) {
                for (int y = 0; y < changesAdd[0].length; y++) {
                    changesAdd[x][y] = false;
                }

            }
            imageAdd = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        }
    }

    public void paintRemove(BufferedImage remove, int x, int y) {
        synchronized (this) {
            ImageProcessor.paintRemove(imageRemove, remove, x, y);
            changesArrayUpdate(changesRemove, x, y, remove.getWidth(), remove.getHeight());
        }
    }

    public void paintAdd(BufferedImage add, int x, int y) {
        synchronized (this) {
            ImageProcessor.paintAdd(imageAdd, add, x, y);
            changesArrayUpdate(changesAdd, x, y, add.getWidth(), add.getHeight());
        }
    }

    public BufferedImage addLocalChanges(BufferedImage source, int x, int y, int width, int height) {
        synchronized (this) {
            if (imageRemove == null) {
                return source;
            }
            BufferedImage result = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            source.copyData(result.getRaster());
            editsToImage(result, x, y, width, height);
            addToImage(result, x, y, width, height);
            removeFromImage(result, x, y, width, height);
            return result;
        }
    }

    public void addReceived(int identificator) {
        synchronized (this) {
            removeEdit(identificator);
        }
    }

    public void removeReceived(int identificator) {
        synchronized (this) {
            removeEdit(identificator);
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            synchronized (this) {
                controlTimeOutEdits();
                makeEdits();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void dismiss() {
        running = false;
    }

    public class Edit {

        protected int x = 0;
        protected int y = 0;
        protected int identification = 0;
        boolean remove = false;
        protected BufferedImage image = null;
        protected long time = 0l;

        public Edit(boolean remove, int identification, BufferedImage image, int x, int y, long time) {
            this.remove = remove;
            this.identification = identification;
            this.image = image;
            this.x = x;
            this.y = y;
            this.time = time;
        }

        public int getIdentification() {
            return identification;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public long getTime() {
            return time;
        }

        public BufferedImage getImage() {
            return image;
        }

        public boolean isRemove() {
            return remove;
        }
    }
}
