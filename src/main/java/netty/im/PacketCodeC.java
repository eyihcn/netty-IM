package netty.im;

import io.netty.buffer.ByteBuf;
import netty.im.packet.*;

/**
 * 通信协议
 * ==================================================================================================
 * |魔数(0x12345678)|版本号   |序列化算法 |指令   |数据长度|数据
 * |4个byte         |1个byte |1个byte  |1个byte|4个byte|N个byte
 * ==================================================================================================
 */
public class PacketCodeC {


    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private PacketCodeC() {

    }

    public static final int MAGIC_NUMBER = 0x12345678;

    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 序列化包
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 魔法数 4个byte
        byteBuf.writeInt(MAGIC_NUMBER);
        // 版本号 1个byte
        byteBuf.writeByte(packet.getVersion());
        // 序列化算法 1个byte
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        // 指令  1个byte
        byteBuf.writeByte(packet.getCommand());
        // 数据包的长度 4个字节
        byteBuf.writeInt(bytes.length);
        // 数据包字节内容
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }


    public Packet decode(ByteBuf byteBuf) {

        // 跳过魔数
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法
        byte serializerAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        //数据包长度
        int packetLen = byteBuf.readInt();

        byte[] bytes = new byte[packetLen];
        byteBuf.readBytes(bytes);
        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;

    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        if (serializerAlgorithm == SerializerAlgorithm.JSON) {
            return Serializer.DEFAULT;
        }
        throw new IllegalStateException("Unexpected value: " + serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {


        switch (command) {
            case Command.LOGIN_REQUEST:
                return LoginRequestPacket.class;
            case Command.LOGIN_RESPONSE:
                return LoginResponsePacket.class;
            case Command.MESSAGE_REQUEST:
                return MessageRequestPacket.class;
            case Command.MESSAGE_RESPONSE:
                return MessageResponsePacket.class;
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }

    }
}
