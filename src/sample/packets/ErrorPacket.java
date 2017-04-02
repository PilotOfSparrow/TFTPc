package sample.packets;

import sample.basepackets.TftpBasePacket;


public class ErrorPacket extends TftpBasePacket {

//    byte[] errorCode;
//    String errMsg;

    public static final byte OP_ERROR = 5;
    public static final byte OFFSET_ERROR_TYPE = 3;

    ErrorPacket() {

        this.opcode[OFFSET_REQUEST] = OP_ERROR;
    }


    public byte[] createPackage(byte[] errorCode, String errorMsg) {

        this.addToArrayOpcode();

//        System.arraycopy(errorCode, 0, this.packageByteArray, this.currentActualPackageSize, errorCode.length);
        this.addToArray(errorCode);
        this.addToArray(errorMsg.getBytes());
        this.addToArray(this.zeroByte);
//        System.arraycopy(errMsg, 0, this.packageByteArray, this.currentActualPackageSize, errMsg.length());

        return this.packageByteArray;
    }
}
