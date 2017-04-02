package sample.packets;

import sample.basepackets.DataAckBasePacket;


public class AckPacket extends DataAckBasePacket {

    public static final byte OP_ACK = 4;

    public AckPacket() {

        this.opcode[OFFSET_REQUEST] = OP_ACK;
    }

    @Override
    public byte[] createPackage(byte[] numOfDataBlock){

        this.blockNum = numOfDataBlock;

        this.addToArrayHeader();

        return this.packageByteArray;
    }
}
