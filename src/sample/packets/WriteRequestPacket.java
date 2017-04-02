package sample.packets;

import sample.basepackets.ReadWriteBasePacket;


public class WriteRequestPacket extends ReadWriteBasePacket {

    public static final byte OP_WRITE = 2;

    public WriteRequestPacket() {

        this.opcode[OFFSET_REQUEST] = OP_WRITE;
    }
}
