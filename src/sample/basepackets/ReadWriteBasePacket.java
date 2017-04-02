package sample.basepackets;


public abstract class ReadWriteBasePacket extends TftpBasePacket {

    public static final int OFFSET_PACKETNUM = 3;

    private final String DEFAULT_MODE = "octet";

    public void addToArrayRequest(String fileName) {

        this.addToArrayOpcode();
        this.addToArray(fileName.getBytes());

        this.addToArray(this.zeroByte);

        this.addToArray(DEFAULT_MODE.getBytes());

        this.addToArray(this.zeroByte);
    }

    public byte[] createPackage(String fileName) {

        this.addToArrayRequest(fileName);

        return this.packageByteArray;
    }
}
