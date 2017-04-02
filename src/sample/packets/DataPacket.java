package sample.packets;

import sample.basepackets.DataAckBasePacket;


public class DataPacket extends DataAckBasePacket {

    public static final byte OP_DATA = 3;

    public byte[] data;


    public DataPacket() {

        this.opcode[OFFSET_REQUEST] = OP_DATA;
    }

    @Override
    public byte[] createPackage(byte[] dataToTransfer) {

        this.packageByteArray = new byte[DEFAULT_PACKAGE_SIZE];
        this.currentActualPackageSize = 0;

        this.incrementBlockNum();

        this.addToArrayHeader();

        this.data = dataToTransfer;

//        System.arraycopy(this.data, 0, this.packageByteArray, this.currentActualPackageSize, this.data.length);
        this.addToArray(this.data);

        return this.packageByteArray;
    }

    private void incrementBlockNum() {

        if (this.blockNum[1] != (byte)0xFFFF) ++this.blockNum[1];
        else {

            ++this.blockNum[0];
            this.blockNum[1] = (byte) 0;
        }
    }
}
