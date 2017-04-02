package sample.basepackets;

/**
 * Base packet class
 */
public abstract class TftpBasePacket {

    /**
     * opcode indicate package type, field length 2 bytes
     * packageByteArray contains whole tftp package, it's fixed sized because this implementation of rfc1350 without extensions
     */

    public static final int DEFAULT_PACKAGE_SIZE = 516;
    public static final int OFFSET_REQUEST = 1;

    protected final byte zeroByte = 0;

    protected byte[] opcode = new byte[2];

    public byte[] packageByteArray = new byte[DEFAULT_PACKAGE_SIZE];
    protected int currentActualPackageSize = 0;

//    abstract public byte[] createPackage();

    public void addToArrayOpcode() {

        System.arraycopy(this.opcode, 0, this.packageByteArray, 0, this.opcode.length);
        this.currentActualPackageSize += this.opcode.length;
    }

    public void addToArray(byte[] data) {

        System.arraycopy(data, 0, this.packageByteArray, this.currentActualPackageSize, data.length);
        this.currentActualPackageSize += data.length;
    }

    public void addToArray(byte singleByte) {

        this.packageByteArray[this.currentActualPackageSize] = singleByte;
        ++this.currentActualPackageSize;
    }
}
