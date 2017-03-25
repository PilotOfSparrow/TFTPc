package sample.packets;

import sample.basepackets.ReadWriteBasePacket;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class WriteRequestPacket extends ReadWriteBasePacket {


    WriteRequestPacket() {

        this.opcode = "2".getBytes();
    }
}
