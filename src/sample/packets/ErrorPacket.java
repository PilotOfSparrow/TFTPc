package sample.packets;

import sample.basepackets.TftpBasePacket;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class ErrorPacket extends TftpBasePacket {

    char errorCode;
    String errMsg;

    ErrorPacket() {

        this.opcode = "5".getBytes();
    }
}
