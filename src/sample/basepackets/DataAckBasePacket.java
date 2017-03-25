package sample.basepackets;


/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class DataAckBasePacket extends TftpBasePacket {

    protected byte[] blockNum = new byte[2];
}
