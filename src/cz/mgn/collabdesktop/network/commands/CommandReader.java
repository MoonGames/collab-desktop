/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.network.commands;

import cz.mgn.collabdesktop.utils.Utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 *                   @author indy
 */
public class CommandReader {

    protected static ImageData readPaintRemoveCommand(byte[] data) {
        if (data.length > 16) {
            try {
                byte[] identificatorData = new byte[4];
                System.arraycopy(data, 0, identificatorData, 0, 4);
                byte[] layerIDData = new byte[4];
                System.arraycopy(data, 4, layerIDData, 0, 4);
                byte[] xData = new byte[4];
                System.arraycopy(data, 8, xData, 0, 4);
                byte[] yData = new byte[4];
                System.arraycopy(data, 12, yData, 0, 4);

                int identificator = Utils.byteArrayToInt(identificatorData);
                int layerID = Utils.byteArrayToInt(layerIDData);
                int x = Utils.byteArrayToInt(xData);
                int y = Utils.byteArrayToInt(yData);

                byte[] imageData = new byte[data.length - 16];
                System.arraycopy(data, 16, imageData, 0, imageData.length);
                ByteArrayInputStream stream = new ByteArrayInputStream(imageData);
                BufferedImage image = ImageIO.read(stream);
                return new ImageData(identificator, layerID, x, y, image);
            } catch (IOException ex) {
                Logger.getLogger(CommandReader.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    public static ImageData readPaintCommand(byte[] data) {
        return readPaintRemoveCommand(data);
    }

    public static ImageData readRemoveCommand(byte[] data) {
        return readPaintRemoveCommand(data);
    }

    /**
     *              read add layer command as three objects, first is integer with layer ID,
     * second is integer with command identificator and third is boolean with
     * sucessful
     */
    public static Object[] readAddLayerCommand(byte[] data) {
        if (data.length >= 9) {
            Object[] result = new Object[3];
            byte[] layerIDData = new byte[4];
            System.arraycopy(data, 0, layerIDData, 0, 4);
            byte[] commandIdentificatorData = new byte[4];
            System.arraycopy(data, 4, layerIDData, 0, 4);
            byte[] sucessfull = new byte[]{data[8]};

            result[0] = Utils.byteArrayToInt(layerIDData);
            result[1] = Utils.byteArrayToInt(commandIdentificatorData);
            result[2] = Utils.byteArrayToBoolean(sucessfull);

            return result;
        }
        return null;
    }

    public static int[] readLayersOrderCommand(byte[] data) {
        int count = data.length / 4;
        int[] layersOrder = new int[count];
        for (int i = 0; i < layersOrder.length; i++) {
            byte[] layerIDData = new byte[4];
            System.arraycopy(data, i * 4, layerIDData, 0, 4);
            layersOrder[i] = Utils.byteArrayToInt(layerIDData);
        }
        return layersOrder;
    }

    /**
     *             read set resolution as two integer, first is width second is height
     */
    public static int[] readSetResolutionCommand(byte[] data) {
        if (data.length >= 8) {
            int[] resolution = new int[2];

            byte[] widthData = new byte[4];
            System.arraycopy(data, 0, widthData, 0, 4);
            byte[] heightData = new byte[4];
            System.arraycopy(data, 4, heightData, 0, 4);

            resolution[0] = Utils.byteArrayToInt(widthData);
            resolution[1] = Utils.byteArrayToInt(heightData);

            return resolution;
        }
        return null;
    }

    /**
     *            read set layer name command as two Object, first is integer with layer
     * ID, second is String with name
     */
    public static Object[] readSetLayerNameCommand(byte[] data) {
        if (data.length >= 4) {
            Object[] result = new Object[2];
            byte[] layerIDData = new byte[4];
            System.arraycopy(data, 0, layerIDData, 0, 4);
            byte[] layerNameData = new byte[data.length - 4];
            System.arraycopy(data, 4, layerNameData, 0, layerNameData.length);

            result[0] = Utils.byteArrayToInt(layerIDData);
            result[1] = "-";
            try {
                result[1] = new String(layerNameData, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(CommandReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            return result;
        }
        return null;
    }

//rooms list: rooms list: [room id](4)[users count](4)[width](4)[height](4)[locked](1)[name length](4)[name](name length)
    public static ArrayList<RoomInfo> readRoomsListCommand(byte[] data) {
        ArrayList<RoomInfo> rooms = new ArrayList<RoomInfo>();
        int index = 0;
        while (index >= 0) {
            if (data.length >= (index + 4)) {
                byte[] roomIDData = new byte[4];
                System.arraycopy(data, index, roomIDData, 0, 4);
                index += 4;
                int roomID = Utils.byteArrayToInt(roomIDData);
                if (data.length >= (index + 4)) {
                    byte[] usersCountData = new byte[4];
                    System.arraycopy(data, index, usersCountData, 0, 4);
                    index += 4;
                    int usersCount = Utils.byteArrayToInt(usersCountData);
                    if (data.length >= (index + 4)) {
                        byte[] widthData = new byte[4];
                        System.arraycopy(data, index, widthData, 0, 4);
                        index += 4;
                        int width = Utils.byteArrayToInt(widthData);
                        if (data.length >= (index + 4)) {
                            byte[] heightData = new byte[4];
                            System.arraycopy(data, index, heightData, 0, 4);
                            index += 4;
                            int height = Utils.byteArrayToInt(heightData);
                            if (data.length >= (index + 1)) {
                                byte[] lockedData = new byte[1];
                                System.arraycopy(data, index, lockedData, 0, 1);
                                index += 1;
                                boolean locked = Utils.byteArrayToBoolean(lockedData);
                                if (data.length >= (index + 4)) {
                                    byte[] nameLengthData = new byte[4];
                                    System.arraycopy(data, index, nameLengthData, 0, 4);
                                    index += 4;
                                    int nameLength = Utils.byteArrayToInt(nameLengthData);

                                    if (data.length >= (index + nameLength)) {
                                        byte[] nameData = new byte[nameLength];
                                        System.arraycopy(data, index, nameData, 0, nameData.length);
                                        index += nameData.length;

                                        String name = "-";
                                        try {
                                            name = new String(nameData, "UTF-8");
                                        } catch (UnsupportedEncodingException ex) {
                                            Logger.getLogger(CommandReader.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        rooms.add(new RoomInfo(name, roomID, usersCount, width, height, locked));
                                    } else {
                                        index = -1;
                                    }
                                } else {
                                    index = -1;
                                }
                            } else {
                                index = -1;
                            }
                        } else {
                            index = -1;
                        }
                    } else {
                        index = -1;
                    }
                } else {
                    index = -1;
                }
            } else {
                index = -1;
            }
        }
        return rooms;
    }

    /**
     *             read join room command as two Objects, first is integer with your id, and
     * second is String with room name
     */
    public static Object[] readJoinRoomCommand(byte[] data) {
        if (data.length >= 4) {
            Object[] result = new Object[2];
            byte[] yourIDData = new byte[4];
            System.arraycopy(data, 0, yourIDData, 0, 4);
            byte[] roomNameData = new byte[data.length - 4];
            System.arraycopy(data, 4, roomNameData, 0, roomNameData.length);

            result[0] = Utils.byteArrayToInt(yourIDData);
            result[1] = "-";
            try {
                result[1] = new String(roomNameData, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(CommandReader.class.getName()).log(Level.SEVERE, null, ex);
            }

            return result;
        }
        return null;
    }

    /**
     *             read users list command as array with users nicks
     */
    public static String[] readUsersListCommand(byte[] data) {
        ArrayList<String> nicks = new ArrayList<String>();
        int index = 0;
        while (index >= 0) {
            if (data.length >= (index + 4)) {
                byte[] nickLengthData = new byte[4];
                System.arraycopy(data, index, nickLengthData, 0, 4);
                index += 4;
                int nickLength = Utils.byteArrayToInt(nickLengthData);
                if (data.length >= (index + nickLength)) {
                    byte[] nickData = new byte[nickLength];
                    System.arraycopy(data, index, nickData, 0, nickLength);
                    index += nickLength;
                    try {
                        nicks.add(new String(nickData, "UTF-8"));
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(CommandReader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    index = -1;
                }
            } else {
                index = -1;
            }
        }
        String[] nicksA = new String[nicks.size()];
        for (int i = 0; i < nicks.size(); i++) {
            nicksA[i] = nicks.get(i);
        }
        return nicksA;
    }

    /**
     *             read chat command as two Strings, first is nick and second is message
     */
    public static String[] readChatCommand(byte[] data) {
        if (data.length >= 4) {
            byte[] nickLengthData = new byte[4];
            System.arraycopy(data, 0, nickLengthData, 0, 4);
            int nickLength = Utils.byteArrayToInt(nickLengthData);
            if (data.length >= (nickLength + 4)) {
                byte[] nickData = new byte[nickLength];
                System.arraycopy(data, 4, nickData, 0, nickData.length);
                byte[] messageData = new byte[data.length - 4 - nickData.length];
                System.arraycopy(data, data.length - messageData.length, messageData, 0, messageData.length);
                try {
                    String nick = new String(nickData, "UTF-8");
                    String message = new String(messageData, "UTF-8");
                    return new String[]{nick, message};
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(CommandReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    public static class RoomInfo {

        protected String name = "";
        protected int id = 0;
        protected int usersCount = 0;
        protected int width = 0;
        protected int height = 0;
        protected boolean locked = false;

        public RoomInfo(String name, int id, int usersCount, int width, int height, boolean locked) {
            this.name = name;
            this.id = id;
            this.usersCount = usersCount;
            this.width = width;
            this.height = height;
            this.locked = locked;
        }

        public String getName() {
            return name;
        }

        public int getID() {
            return id;
        }

        public boolean isLocked() {
            return locked;
        }

        public int getUsersCount() {
            return usersCount;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }
}
