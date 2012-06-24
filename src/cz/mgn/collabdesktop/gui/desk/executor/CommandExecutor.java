/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.executor;

import cz.mgn.collabdesktop.gui.desk.executor.interfaces.Layers;
import cz.mgn.collabdesktop.gui.desk.executor.interfaces.System;
import cz.mgn.collabdesktop.gui.desk.executor.interfaces.Users;
import cz.mgn.collabcanvas.canvas.CollabCanvas;
import cz.mgn.collabcanvas.interfaces.networkable.NetworkListener;
import cz.mgn.collabcanvas.interfaces.networkable.NetworkUpdate;
import cz.mgn.collabdesktop.network.Client;
import cz.mgn.collabdesktop.network.DataInterface;
import cz.mgn.collabdesktop.network.commands.CommandGenerator;
import cz.mgn.collabdesktop.network.commands.CommandReader;
import cz.mgn.collabdesktop.network.commands.ImageData;
import cz.mgn.collabdesktop.network.commands.InCommands;
import java.awt.Point;

/**
 *
 * @author indy
 */
public class CommandExecutor implements DataInterface, NetworkListener {

    protected Client client = null;
    protected int id = 0;
    protected int idGenerator = 0;
    protected CollabCanvas canvas = null;
    protected Layers layers = null;
    protected System system = null;
    protected Users users = null;

    public CommandExecutor(Client client, int id) {
        this.client = client;
        this.id = id;
        idGenerator = id;
        client.addDataInterface(this);
    }

    public int generateNextID() {
        idGenerator += 1024;
        return idGenerator;
    }

    public void setCanvas(CollabCanvas canvas) {
        this.canvas = canvas;
    }

    public void setLayers(Layers layers) {
        this.layers = layers;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public void setSystem(System system) {
        this.system = system;
    }

    public void dismiss() {
        client.removeDataInterface(this);
    }

    public Client getClient() {
        return client;
    }

    public void sendAddLayer(int layerLocation, int commandIdentificator, String layerName) {
        byte[] send = CommandGenerator.generateAddLayerCommand(layerLocation, commandIdentificator, layerName);
        client.send(send);
    }

    public void sendRemoveLayer(int layerID) {
        byte[] send = CommandGenerator.generateRemoveLayerCommand(layerID);
        client.send(send);
    }

    public void sendSetLayerLocation(int layerID, int newLocation) {
        byte[] send = CommandGenerator.generateSetLayerLocationCommand(layerID, newLocation);
        client.send(send);
    }

    public void sendSetLayerName(int layerID, String name) {
        byte[] send = CommandGenerator.generateSetLayerNameCommand(layerID, name);
        client.send(send);
    }

    public void sendChat(String message) {
        byte[] send = CommandGenerator.generateChatCommand(message);
        client.send(send);
    }

    public void sendDisconnectFromRoom() {
        byte[] send = CommandGenerator.generateDisconnectFromRoomCommand();
        client.send(send);
    }

    public void sendMakeHTTPImage() {
        byte[] send = CommandGenerator.generateMakeHTTPImageCommand();
        client.send(send);
    }

    @Override
    public void dataReaded(byte[] data, byte command) {
        switch (command) {
            case InCommands.IN_COMMAND_PAINT:
                commandPaint(data);
                break;
            case InCommands.IN_COMMAND_REMOVE:
                commandRemove(data);
                break;
            case InCommands.IN_COMMAND_ADD_LAYER:
                commandAddLayer(data);
                break;
            case InCommands.IN_COMMAND_LAYERS_ORDER:
                commandLayersOrder(data);
                break;
            case InCommands.IN_COMMAND_SET_RESOLUTION:
                commandSetResolution(data);
                break;
            case InCommands.IN_COMMAND_SET_LAYER_NAME:
                commandSetLayerName(data);
                break;
            case InCommands.IN_COMMAND_USERS_LIST:
                commandUsersList(data);
                break;
            case InCommands.IN_COMMAND_CHAT:
                commandChat(data);
                break;
            case InCommands.IN_COMMAND_DISCONNECT_FROM_ROOM:
                commandDisconnectFromRoom(data);
                break;
        }
    }

    protected void commandPaint(byte[] data) {
        if (canvas != null) {
            ImageData im = CommandReader.readPaintCommand(data);
            canvas.getNetworkable().updateReceived(new ExecutorNetworkUpdate(im.getIdentificator(), im.getLayerID(),
                    true, new Point(im.getX(), im.getY()), im.getImage()));
        }
    }

    protected void commandRemove(byte[] data) {
        if (canvas != null) {
            ImageData im = CommandReader.readRemoveCommand(data);
            canvas.getNetworkable().updateReceived(new ExecutorNetworkUpdate(im.getIdentificator(), im.getLayerID(),
                    false, new Point(im.getX(), im.getY()), im.getImage()));
        }
    }

    protected void commandAddLayer(byte[] data) {
        Object[] addLayer = CommandReader.readAddLayerCommand(data);
        if (addLayer != null) {
            int layerID = (Integer) addLayer[0];
            int commandIdentificator = (Integer) addLayer[1];
            boolean sucessfull = (Boolean) addLayer[2];
            if (layers != null) {
                layers.addLayer(layerID, commandIdentificator, sucessfull);
            }
        }
    }

    protected void commandLayersOrder(byte[] data) {
        int[] order = CommandReader.readLayersOrderCommand(data);
        if (order != null) {
            if (canvas != null) {
                canvas.getPaintable().setLayersOrder(order, true);
            }
            if (layers != null) {
                layers.setLayersOrder(order);
            }
        }
    }

    protected void commandSetResolution(byte[] data) {
        int[] resolution = CommandReader.readSetResolutionCommand(data);
        if (resolution != null) {
            if (canvas != null) {
                canvas.getPaintable().setResolution(resolution[0], resolution[1]);
            }
        }
    }

    protected void commandSetLayerName(byte[] data) {
        Object[] layerName = CommandReader.readSetLayerNameCommand(data);
        if (layerName != null) {
            int layerID = (Integer) layerName[0];
            String name = (String) layerName[1];
            if (layers != null) {
                layers.setLayerName(layerID, name);
            }
        }
    }

    protected void commandUsersList(byte[] data) {
        String[] nicks = CommandReader.readUsersListCommand(data);
        if (nicks != null) {
            if (users != null) {
                users.usersList(nicks);
            }
        }
    }

    protected void commandChat(byte[] data) {
        String[] chat = CommandReader.readChatCommand(data);
        if (chat != null) {
            if (users != null) {
                users.chat(chat[0], chat[1]);
            }
        }
    }

    protected void commandDisconnectFromRoom(byte[] data) {
        if (system != null) {
            system.disconnectFromRoom();
        }
    }

    @Override
    public void sendUpdate(NetworkUpdate update) {
        byte[] data = null;
        if (update.isAdd()) {
            data = CommandGenerator.generatePaintCommand(new ImageData(update.getUpdateID(), update.getUpdateLayerID(),
                    update.getUpdateCoordinates().x, update.getUpdateCoordinates().y, update.getUpdateImage()));
        } else {
            data = CommandGenerator.generateRemoveCommand(new ImageData(update.getUpdateID(), update.getUpdateLayerID(),
                    update.getUpdateCoordinates().x, update.getUpdateCoordinates().y, update.getUpdateImage()));
        }
        client.send(data);
    }

    @Override
    public void unreachedUpdateRemoved(NetworkUpdate update) {
    }
}
