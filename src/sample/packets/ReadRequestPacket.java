package sample.packets;

import sample.basepackets.ReadWriteBasePacket;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class ReadRequestPacket extends ReadWriteBasePacket {


  ReadRequestPacket() {

      this.opcode = "1".getBytes();
  }
}
