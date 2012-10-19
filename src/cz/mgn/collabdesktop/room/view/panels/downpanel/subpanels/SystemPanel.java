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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.view.panels.downpanel.subpanels;

import cz.mgn.collabdesktop.room.view.panels.downpanel.interfaces.SystemInterface;
import cz.mgn.collabdesktop.utils.gui.iconComponent.IconButton;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class SystemPanel extends JPanel implements ActionListener {

    protected SystemInterface systemInterface = null;
    //
    protected SysButton leaveRoom = null;
    protected SysButton save = null;
    protected SysButton zoomTo100 = null;
    protected SysButton zoomIn = null;
    protected SysButton zoomOut = null;
    protected SysButton makeHTTPImage = null;
    protected SysButton print = null;
    protected SysButton tweet = null;

    public SystemPanel(SystemInterface systemInterface) {
        this.systemInterface = systemInterface;
        initCoponents();
    }

    protected void initCoponents() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/leave.png")));
        leaveRoom = new SysButton(icon, "leave room");
        addSysButton(leaveRoom);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/save.png")));
        save = zoomOut = new SysButton(icon, "save as image");
        addSysButton(save);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/http.png")));
        makeHTTPImage = zoomOut = new SysButton(icon, "make http image");
        addSysButton(makeHTTPImage);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/one_to_one.png")));
        zoomTo100 = zoomOut = new SysButton(icon, "zoom 1:1");
        addSysButton(zoomTo100);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/zoomin.png")));
        zoomIn = new SysButton(icon, "zoom in");
        addSysButton(zoomIn);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/zoomout.png")));
        zoomOut = new SysButton(icon, "zoom out");
        addSysButton(zoomOut);
//
//        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/print.png")));
//        print = new SysButton(icon, "print");
//        addSysButton(print);
//
//        icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(SystemPanel.class.getResource("/resources/images/system/twitter.png")));
//        tweet = new SysButton(icon, "tweet");
//        addSysButton(tweet);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == leaveRoom) {
            systemInterface.leaveRoom();
        } else if (e.getSource() == save) {
            systemInterface.saveAsImage();
        } else if (e.getSource() == zoomIn) {
            systemInterface.zoomIn();
        } else if (e.getSource() == zoomOut) {
            systemInterface.zoomOut();
        } else if (e.getSource() == zoomTo100) {
            systemInterface.zoomTo100();
        } else if (e.getSource() == makeHTTPImage) {
            systemInterface.makeHTTPImage();
        } else if (e.getSource() == print) {
            systemInterface.print();
        } else if (e.getSource() == tweet) {
            systemInterface.tweet();
        }
    }

    protected void addSysButton(SysButton button) {
        button.addActionListener(this);
        add(button);
    }

    protected class SysButton extends IconButton {

        public SysButton(ImageIcon icon, String toolTipText) {
            super("button_32", icon);
            setToolTipText(toolTipText);
        }
    }
}
