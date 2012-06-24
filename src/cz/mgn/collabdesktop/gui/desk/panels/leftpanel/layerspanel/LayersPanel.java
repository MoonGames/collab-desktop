/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.leftpanel.layerspanel;

import cz.mgn.collabcanvas.interfaces.paintable.Paintable;
import cz.mgn.collabdesktop.gui.desk.DeskInterface;
import cz.mgn.collabdesktop.gui.desk.executor.CommandExecutor;
import cz.mgn.collabdesktop.gui.desk.executor.interfaces.Layers;
import cz.mgn.collabdesktop.gui.desk.paintengine.PaintEngine;
import cz.mgn.collabdesktop.utils.gui.iconComponent.IconButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author indy
 */
public class LayersPanel extends JPanel implements Layers, ActionListener {

    protected CommandExecutor executor = null;
    protected PaintEngine paintEngine = null;
    protected DeskInterface desk = null;
    //
    protected LayersList layersList = null;
    protected int selected = 0;
    //
    protected JButton add;
    protected JButton remove;
    protected JButton rename;
    protected JButton up;
    protected JButton down;
    //
    protected boolean layerNameOpened = false;

    public LayersPanel(CommandExecutor executor, PaintEngine paintEngine, DeskInterface desk) {
        this.paintEngine = paintEngine;
        this.executor = executor;
        this.desk = desk;
        executor.setLayers(this);
        initComponents();
    }

    protected void initComponents() {
        setBorder(new TitledBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), "Layers"));
        setLayout(new BorderLayout(0, 5));
        setPreferredSize(new Dimension(getPreferredSize().width, 90));

        layersList = new LayersList();
        layersList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    int selectedL = layersList.getSelectedLayerID();
                    if (selectedL >= 0) {
                        selected = selectedL;
                    }
                    paintEngine.getCanvas().getPaintable().selectLayer(selected);
                }
            }
        });
        JScrollPane layersListScrollPane = new JScrollPane(layersList);
        add(layersListScrollPane, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        buttons.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));

        Icon icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/layer_add.png")));
        add = new IconButton("button_16", icon);
        add.setToolTipText("create new layer");
        add.addActionListener(this);
        buttons.add(add);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/layer_remove.png")));
        remove = new IconButton("button_16", icon);
        remove.setToolTipText("remove selected layer");
        remove.addActionListener(this);
        buttons.add(remove);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/layer_rename.png")));
        rename = new IconButton("button_16", icon);
        rename.setToolTipText("rename selected layer");
        rename.addActionListener(this);
        buttons.add(rename);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/layer_up.png")));
        up = new IconButton("button_16", icon);
        up.setToolTipText("mouve selected layer up");
        up.addActionListener(this);
        buttons.add(up);

        icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(LayersPanel.class.getResource("/resources/images/layer_down.png")));
        down = new IconButton("button_16", icon);
        down.setToolTipText("move selected layer down");
        down.addActionListener(this);
        buttons.add(down);

        add(buttons, BorderLayout.SOUTH);
    }

    @Override
    public void addLayer(int layerID, int identificator, boolean sucesfull) {
    }

    @Override
    public void setLayersOrder(int[] layersOrderIDs) {
        synchronized (this) {
            layersList.setLayersOrder(layersOrderIDs);
            for (int i = 0; i < layersOrderIDs.length; i++) {
                if (layersOrderIDs[i] == selected) {
                    paintEngine.getCanvas().getPaintable().selectLayer(selected);
                    layersList.setSelectedLayer(selected);
                    return;
                }
            }
            if (layersOrderIDs.length > 0) {
                selected = layersOrderIDs[layersOrderIDs.length - 1];
            } else {
                selected = -1;
            }
            layersList.setSelectedLayer(selected);
            paintEngine.getCanvas().getPaintable().selectLayer(selected);
        }
    }

    @Override
    public void setLayerName(int layerID, String name) {
        layersList.setLayerName(layerID, name);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == add) {
            add();
        } else if (e.getSource() == remove) {
            remove();
        } else if (e.getSource() == rename) {
            rename();
        } else if (e.getSource() == up) {
            up();
        } else if (e.getSource() == down) {
            down();
        }
    }

    protected void add() {
        if (!layerNameOpened) {
            layerNameOpened = true;
            LayerName ln = new LayerName(new LayerNameInterface() {

                @Override
                public void done(String name) {
                    executor.sendAddLayer(layersList.getLayersCount(), -1, name);
                    layerNameOpened = false;
                }

                @Override
                public void close() {
                    layerNameOpened = false;
                }
            });
            desk.showWindow(ln);
        }

    }

    protected void remove() {
        int layerID = layersList.getSelectedLayerID();
        if (layerID >= 0) {
            executor.sendRemoveLayer(layerID);
        }
    }

    protected void rename() {
        if (!layerNameOpened) {
            final int layerID = layersList.getSelectedLayerID();
            if (layerID >= 0) {
                String name = layersList.getSelectedLayerName();
                layerNameOpened = true;
                LayerName ln = new LayerName(new LayerNameInterface() {

                    @Override
                    public void done(String name) {
                        executor.sendSetLayerName(layerID, name);
                        layerNameOpened = false;
                    }

                    @Override
                    public void close() {
                        layerNameOpened = false;
                    }
                });
                ln.setDefaultName(name);
                desk.showWindow(ln);
            }
        }

    }

    protected void up() {
        int layerID = layersList.getSelectedLayerID();
        int layerLocation = layersList.getSelectedLayerLocation();
        if (layerID >= 0 && layerLocation < (layersList.getLayersCount() - 1)) {
            layerLocation++;
            executor.sendSetLayerLocation(layerID, layerLocation);
        }
    }

    protected void down() {
        int layerID = layersList.getSelectedLayerID();
        int layerLocation = layersList.getSelectedLayerLocation();
        if (layerID >= 0 && layerLocation > 0) {
            layerLocation--;
            executor.sendSetLayerLocation(layerID, layerLocation);
        }
    }

    public class LayersList extends JList {

        protected DefaultListModel model = null;
        protected ArrayList<LayersList.LayerInfo> layers = new ArrayList<LayersList.LayerInfo>();

        public LayersList() {
            super();
            model = new DefaultListModel();
            setCellRenderer(new LayersCellRenderer());
            setModel(model);
            setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        }

        protected LayersList.LayerInfo getLayerByID(ArrayList<LayersList.LayerInfo> layersS, int layerID) {
            for (int i = 0; i < layersS.size(); i++) {
                if (layersS.get(i).getLayerID() == layerID) {
                    return layersS.get(i);
                }
            }
            return null;
        }

        protected void refresh(boolean select) {
            synchronized (this) {
                int selected = getSelectedIndex();
                model.clear();
                for (int i = (layers.size() - 1); i >= 0; i--) {
                    model.addElement(layers.get(i));
                }
                if (select && selected < model.getSize()) {
                    setSelectedIndex(selected);
                }
            }
        }

        public void setSelectedLayer(int id) {
            for (int i = 0; i < layers.size(); i++) {
                if (layers.get(i).getLayerID() == id) {
                    int index = layers.size() - i - 1;
                    setSelectedIndex(index);
                }
            }
        }

        public int getSelectedLayerID() {
            int index = getSelectedIndex();
            if (index >= 0) {
                return layers.get(layers.size() - index - 1).getLayerID();
            }
            return -1;
        }

        public String getSelectedLayerName() {
            int index = getSelectedIndex();
            if (index >= 0) {
                return layers.get(layers.size() - index - 1).getName();
            }
            return "";
        }

        public int getLayersCount() {
            return layers.size();
        }

        public int getSelectedLayerLocation() {
            return layers.size() - getSelectedIndex() - 1;
        }

        public void setLayersOrder(int[] layersOrderIDs) {
            ArrayList<LayersList.LayerInfo> layersD = new ArrayList<LayersList.LayerInfo>();
            for (int i = 0; i < layersOrderIDs.length; i++) {
                LayersList.LayerInfo layer = getLayerByID(layers, layersOrderIDs[i]);
                if (layer == null) {
                    layer = new LayersList.LayerInfo("#id: " + layersOrderIDs[i], layersOrderIDs[i]);
                }
                layersD.add(layer);
            }
            layers = layersD;
            refresh(false);
        }

        public void setLayerName(int layerID, String name) {
            LayersList.LayerInfo layer = getLayerByID(layers, layerID);
            if (layer != null) {
                if (name.trim().isEmpty()) {
                    name = "#unamed#";
                }
                layer.setName(name);
            }
            refresh(true);
        }

        protected class LayersCellRenderer implements ListCellRenderer {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof LayerInfo) {
                    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
                    LayerInfo li = (LayerInfo) value;

                    panel.add(new JLabel(li.getName()));
                    panel.setBackground(isSelected ? Color.GRAY : Color.WHITE);

                    return panel;
                }
                return new JLabel("" + value);
            }
        }

        protected class LayerInfo {

            protected String name = "";
            protected int layerID = 0;

            public LayerInfo(String name, int layerID) {
                this.name = name;
                this.layerID = layerID;
            }

            public void resetID(int newID) {
                this.layerID = newID;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getName() {
                return name;
            }

            public int getLayerID() {
                return layerID;
            }
        }
    }
}
