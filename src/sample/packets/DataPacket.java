package sample.packets;

import sample.basepackets.DataAckBasePacket;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class DataPacket extends DataAckBasePacket {

    byte[] data;


    DataPacket(int sizeOfDataBlock) {

        this.opcode = "3".getBytes();
        this.data = new byte[sizeOfDataBlock];
    }

}
