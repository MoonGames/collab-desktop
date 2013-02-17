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

package cz.mgn.collabdesktop.room.view.panels.leftpanel.layerspanel;

import cz.mgn.collabcanvas.interfaces.paintable.Paintable;
import cz.mgn.collabdesktop.room.model.executor.CommandExecutor;
import cz.mgn.collabdesktop.room.model.executor.interfaces.Layers;
import cz.mgn.collabdesktop.room.view.DeskInterface;
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
 * @author Martin Indra <aktive@seznam.cz>
 */
public class LayersPanel extends JPanel implements Layers, ActionListener {

    protected Paintable paintable;
    protected CommandExecutor executor = null;
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
    protected int toSelect = -1;

    public LayersPanel(CommandExecutor executor, DeskInterface desk, Paintable paintable) {
        this.executor = executor;
        this.desk = desk;
        this.paintable = paintable;
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
                    paintable.selectLayer(selected);
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
        synchronized (this) {
            if (sucesfull && toSelect >= 0 && toSelect == identificator) {
                int[] order = layersList.getLayersOrder();
                int[] newOrder = new int[order.length + 1];
                System.arraycopy(order, 0, newOrder, 0, order.length);
                newOrder[newOrder.length - 1] = layerID;
                layersList.setLayersOrder(newOrder);
                selected = layerID;
                layersList.setSelectedLayer(layerID);
                toSelect = -1;
            } else if (!sucesfull) {
                toSelect = -1;
            }
        }
    }

    @Override
    public void setLayersOrder(int[] layersOrderIDs) {
        synchronized (this) {
            layersList.setLayersOrder(layersOrderIDs);
            for (int i = 0; i < layersOrderIDs.length; i++) {
                if (layersOrderIDs[i] == selected) {
                    paintable.selectLayer(selected);
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
            paintable.selectLayer(selected);
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
                    executor.sendAddLayer(layersList.getLayersCount(),
                            toSelect = executor.generateNextID(), name);
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

        public int[] getLayersOrder() {
            int[] order = new int[layers.size()];
            for (int i = 0; i < layers.size(); i++) {
                order[i] = layers.get(i).getLayerID();
            }
            return order;
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
