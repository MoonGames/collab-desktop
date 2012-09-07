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
