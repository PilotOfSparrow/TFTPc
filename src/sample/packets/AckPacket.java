package sample.packets;

import sample.basepackets.DataAckBasePacket;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class AckPacket extends DataAckBasePacket {

    AckPacket(byte[] numOfDataBlock) {

        this.blockNum = numOfDataBlock;
        this.opcode = "4".getBytes();
    }
}
