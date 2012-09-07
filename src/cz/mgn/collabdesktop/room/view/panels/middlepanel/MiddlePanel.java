/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.middlepanel;

import cz.mgn.collabcanvas.canvas.CollabCanvas;
import cz.mgn.collabcanvas.interfaces.networkable.NetworkIDGenerator;
import cz.mgn.collabdesktop.room.view.DeskInterface;
import cz.mgn.collabdesktop.room.model.executor.CommandExecutor;
import cz.mgn.collabdesktop.room.view.panels.middlepanel.infopanel.InfoPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class MiddlePanel extends JPanel {

    protected CollabCanvas collabCanvas = null;

    public MiddlePanel(CommandExecutor executor, DeskInterface desk) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());

        initCanvas();
        //FIXME: next code will be on another place!
        executor.setCanvas(collabCanvas);
        collabCanvas.getNetworkable().addListener(executor);

        add(collabCanvas.getCanvasComponent(), BorderLayout.CENTER);

        InfoPanel infoPanel = new InfoPanel();
        collabCanvas.addInfoListener(infoPanel);
        add(infoPanel, BorderLayout.SOUTH);
    }

    protected void initCanvas() {
        //FIXME: generate ids on other place
        collabCanvas = new CollabCanvas(true, new NetworkIDGenerator() {

            protected int last = 0;

            @Override
            public int generateNextID() {
                return ++last;
            }
        }, 0);
    }

    public CollabCanvas getCanvas() {
        return collabCanvas;
    }

    public void dismiss() {
        collabCanvas.destroy();
    }
}
