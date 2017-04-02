package sample.basepackets;


public abstract class DataAckBasePacket extends TftpBasePacket {

    public static final int OFFSET_PACKETNUM = 3;

    public byte[] blockNum = new byte[2];

    public abstract byte[] createPackage(byte[] someData);

    private void addToArrayBlockNum() {

        System.arraycopy(this.blockNum, 0, this.packageByteArray,
                OFFSET_PACKETNUM - OFFSET_REQUEST, this.blockNum.length);
        this.currentActualPackageSize += this.blockNum.length;
    }

    public void addToArrayHeader() {

        this.addToArrayOpcode();
        this.addToArrayBlockNum();
    }
}
