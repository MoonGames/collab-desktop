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
package cz.mgn.collabdesktop.room.view.panels.downpanel.frames.saveimage;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author indy
 */
public class SaveImage<O> extends EFrame implements ActionListener {

    protected JFileChooser fileChooser;
    protected SaveImageInterface fInterface = null;
    protected O data = null;

    public SaveImage(SaveImageInterface<O> fInterface, O data, String defaultFile) {
        super();
        this.fInterface = fInterface;
        this.data = data;
        if (defaultFile.isEmpty()) {
            defaultFile = "collab.png";
        }
        fileChooser.setSelectedFile(new File(defaultFile));
    }

    @Override
    protected String getSectionName() {
        return "save as image";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(500, 400));
        setSize(getPreferredSize());
        setLayout(new BorderLayout());

        fileChooser = new JFileChooser();
        fileChooser.addActionListener(this);
        add(fileChooser);


        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setFileFilter(new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().endsWith(".PNG") || f.getName().endsWith(".png");
            }

            @Override
            public String getDescription() {
                return "PNG image files";
            }
        });
    }

    protected void choosed() {
        dispose();
        File file = fileChooser.getSelectedFile();
        if (file != null) {
            fInterface.fileChoosed(file, data);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fileChooser) {
            if (JFileChooser.CANCEL_SELECTION.equals(e.getActionCommand())) {
                fInterface.saveImageClosed();
                dispose();
            } else if (JFileChooser.APPROVE_SELECTION.equals(e.getActionCommand())) {
                choosed();
            }
        }
    }

    @Override
    public void windowClosed() {
        fInterface.saveImageClosed();
    }
}
