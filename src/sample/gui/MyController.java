package sample.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.basepackets.TftpBasePacket;
import sample.files.FileWorker;
import sample.network.Client;
import sample.packets.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;


public class MyController implements Initializable {

    private FileWorker fileWorker;

    @FXML private ListView<String> filesList;

    @FXML private Button readFileButton;
    @FXML private Button writeFileButton;

    @FXML private TextField remoteFileName;
    @FXML private TextField serverIpAddr;
    @FXML private TextField serverPort;


    public MyController() {

        fileWorker = FileWorker.getFileWorker();
    }

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        assert readFileButton != null : "fx:id=\"mReadFileButton\" was not injected: check FXML: 'sample.fxml'";
        assert writeFileButton != null : "fx:id=\"mWriteFileButton\" was not injected: check FXML: 'sample.fxml'";
        assert filesList != null : "fx:id=\"mFilesList\" was not injected: check FXML: 'sample.fxml'";

        ObservableList<String> items = FXCollections.observableArrayList (fileWorker.getCurrentDirFilesNames());

        filesList.setItems(items);

        filesList.setOnMouseClicked(event -> remoteFileName.setText(filesList.getSelectionModel().getSelectedItem()));
    }

    @FXML
    public void handleReadFileAction() {

        String fileName = remoteFileName.getText();

        ReadRequestPacket readRequestPacket = new ReadRequestPacket();

        Client readClient = new Client(serverIpAddr.getText(), serverPort.getText());

        readClient.send(readRequestPacket.createPackage(remoteFileName.getText()), TftpBasePacket.DEFAULT_PACKAGE_SIZE);

        DatagramPacket tmpPocket;
        byte[] receiveBuff = new byte[TftpBasePacket.DEFAULT_PACKAGE_SIZE];
        do {

             tmpPocket = readClient.receive(receiveBuff);

             if (receiveBuff[TftpBasePacket.OFFSET_REQUEST] == ErrorPacket.OP_ERROR) {
                 System.err.println("Error! Type: "
                         + new String(receiveBuff, ErrorPacket.OFFSET_ERROR_TYPE, receiveBuff.length-2));

                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Error!");
                 alert.setHeaderText(null);
                 alert.setContentText("Type: "
                         + new String(receiveBuff, ErrorPacket.OFFSET_ERROR_TYPE, receiveBuff.length-2));

                 alert.showAndWait();
                 return;
             }

             byte[] tmpPackageNum = new byte[2];
             System.arraycopy(receiveBuff, AckPacket.OFFSET_REQUEST + 1, tmpPackageNum, 0, 2);

             byte[] tmpAckArr = new AckPacket().createPackage(tmpPackageNum);
             readClient.send(tmpAckArr, tmpAckArr.length);

             byte[] tmpToFileArr = new byte[TftpBasePacket.DEFAULT_PACKAGE_SIZE - 4];
             System.arraycopy(receiveBuff, AckPacket.OFFSET_PACKETNUM + 1, tmpToFileArr, 0,
                     tmpToFileArr.length);

             fileWorker.writeToFile(tmpToFileArr, fileName);

        } while (tmpPocket.getLength() == TftpBasePacket.DEFAULT_PACKAGE_SIZE);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("File transfer complete!");

        alert.showAndWait();
    }

    @FXML
    public void handleWriteFileAction() {

        String fileName = remoteFileName.getText();

        WriteRequestPacket writeRequestPacket = new WriteRequestPacket();

        Client client = new Client(serverIpAddr.getText(), serverPort.getText());

        client.send(writeRequestPacket.createPackage(remoteFileName.getText()), TftpBasePacket.DEFAULT_PACKAGE_SIZE);

        byte[] receiveBuff = new byte[TftpBasePacket.DEFAULT_PACKAGE_SIZE];
        byte[] sendBuff = new byte[TftpBasePacket.DEFAULT_PACKAGE_SIZE - 4];

        client.receive(receiveBuff);

        if (receiveBuff[TftpBasePacket.OFFSET_REQUEST] == ErrorPacket.OP_ERROR) {
            System.err.println("Error! Type: "
                    + new String(receiveBuff, ErrorPacket.OFFSET_ERROR_TYPE, receiveBuff.length-2));

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText(null);
            alert.setContentText("Type: "
                    + new String(receiveBuff, ErrorPacket.OFFSET_ERROR_TYPE, receiveBuff.length-2));

            alert.showAndWait();
            return;
        }

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileWorker.readFromFile(fileName));

        DataPacket dataPacket = new DataPacket();
        try{
            while (byteArrayInputStream.available() > sendBuff.length){

                byteArrayInputStream.read(sendBuff);

                client.send(dataPacket.createPackage(sendBuff), TftpBasePacket.DEFAULT_PACKAGE_SIZE);

                client.receive(receiveBuff);

                if (receiveBuff[TftpBasePacket.OFFSET_REQUEST] == ErrorPacket.OP_ERROR) {
                    System.err.println("Error! Type: "
                            + new String(receiveBuff, ErrorPacket.OFFSET_ERROR_TYPE, receiveBuff.length-2));

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText(null);
                    alert.setContentText("Type: "
                            + new String(receiveBuff, ErrorPacket.OFFSET_ERROR_TYPE, receiveBuff.length-2));

                    alert.showAndWait();
                    return;
                }

                byte[] tmpPackageNum = new byte[2];
                System.arraycopy(receiveBuff, AckPacket.OFFSET_REQUEST + 1, tmpPackageNum, 0, 2);

                if (receiveBuff[TftpBasePacket.OFFSET_REQUEST] == AckPacket.OP_ACK
                        && !(Arrays.equals(dataPacket.blockNum, tmpPackageNum))) {

                    System.err.println("Error! Wrong Ack num!");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error!");
                    alert.setHeaderText(null);
                    alert.setContentText("Error! Wrong Ack num!");

                    alert.showAndWait();
                    return;
                }
            }

            byte[] lastSendBuff = new byte[byteArrayInputStream.available()];
            byteArrayInputStream.read(lastSendBuff);

            client.send(dataPacket.createPackage(lastSendBuff), lastSendBuff.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("File transfer complete!");

        alert.showAndWait();
    }
}
