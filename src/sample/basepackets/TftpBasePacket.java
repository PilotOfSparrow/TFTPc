package sample.basepackets;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */

/**
 * Base packet class
 */
public abstract class TftpBasePacket {

    /**
     * Opcode indicate package type
     */
    protected byte[] opcode;
    protected byte zeroByte = 0;
}
