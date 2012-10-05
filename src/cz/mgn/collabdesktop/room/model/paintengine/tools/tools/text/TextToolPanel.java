/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.text;

import cz.mgn.collabdesktop.utils.textgraphicsutil.TextGraphicsUtil;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author indy
 */
public class TextToolPanel extends JPanel {

    protected TextToolPanelListener til;
    //
    protected Color color = Color.BLACK;
    protected JComboBox font;
    protected JTextArea text;
    protected JSpinner fontSize;

    public TextToolPanel(TextToolPanelListener til) {
        this.til = til;
        initComponents();
    }

    protected void initComponents() {
        setLayout(new BorderLayout(0, 5));

        text = new JTextArea();
        text.setLineWrap(true);
        text.setWrapStyleWord(true);
        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                renderText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                renderText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                renderText();
            }
        });
        JScrollPane sp = new JScrollPane(text);
        add(sp, BorderLayout.CENTER);

        JPanel down = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(3, 3, 3, 3);

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0;
        down.add(new JLabel("Size:"), c);
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        fontSize = new JSpinner(new SpinnerNumberModel(16, 4, 64, 1));
        fontSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                renderText();
            }
        });
        down.add(fontSize, c);

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        down.add(new JLabel("Font:"), c);
        c.gridx = 1;
        c.gridy = 1;
        c.weightx = 1;
        font = new JComboBox(TextGraphicsUtil.getAvailibleFonts());
        font.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renderText();
            }
        });
        down.add(font, c);

        add(down, BorderLayout.SOUTH);
    }

    public void setColor(Color color) {
        this.color = color;
        renderText();
    }

    protected void renderText() {
        BufferedImage textImage = TextGraphicsUtil.renderText(text.getText(), color, (Integer) fontSize.getValue(), //
                (String) font.getSelectedItem());
        til.textRendered(textImage);
    }
}
