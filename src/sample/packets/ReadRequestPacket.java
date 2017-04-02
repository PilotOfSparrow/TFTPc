package sample.packets;

import sample.basepackets.ReadWriteBasePacket;


public class ReadRequestPacket extends ReadWriteBasePacket {

    public static final byte OP_READ = 1;

  public ReadRequestPacket() {

      this.opcode[OFFSET_REQUEST] = OP_READ;
  }
}
