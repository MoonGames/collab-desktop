/*
 * Collab desktop - Software for shared drawing via internet in real-time
 * Copyright (C) 2012 Martin Indra <aktive@seznam.cz>
 *
 * This file is part of Collab desktop.
 *
 * Collab desktop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collab desktop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.
 */

package cz.mgn.collabdesktop.room.view.panels.middlepanel;

import cz.mgn.collabcanvas.canvas.CollabCanvas;
import cz.mgn.collabcanvas.interfaces.networkable.NetworkIDGenerator;
import cz.mgn.collabdesktop.room.model.executor.CommandExecutor;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineListener;
import cz.mgn.collabdesktop.room.model.paintengine.ToolInfoInterface;
import cz.mgn.collabdesktop.room.view.DeskInterface;
import cz.mgn.collabdesktop.room.view.panels.middlepanel.infopanel.InfoInterface;
import cz.mgn.collabdesktop.room.view.panels.middlepanel.infopanel.InfoPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class MiddlePanel extends JPanel implements PaintEngineListener {

    protected CollabCanvas collabCanvas = null;
    protected InfoInterface infoInterface = null;

    public MiddlePanel(CommandExecutor executor, DeskInterface desk) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new BorderLayout());

        initCanvas();
        //FIXME: next code will be on another place!
        executor.setCanvas(collabCanvas);
        collabCanvas.getNetworkable().addListener(executor);

        add(collabCanvas.getCanvasComponent(), BorderLayout.CENTER);

        InfoPanel infoPanel = new InfoPanel();
        this.infoInterface = (InfoInterface) infoPanel;
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

    @Override
    public void selectedToolChanged(ToolInfoInterface selectedTool) {
        infoInterface.showInfoString(selectedTool.getToolName() + ": " + selectedTool.getToolDescription());
    }

    @Override
    public void colorChanged(int newColor) {
    }
}
