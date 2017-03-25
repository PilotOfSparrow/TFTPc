package sample.gui;

import com.sun.istack.internal.NotNull;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import sample.files.FileWorker;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Sir Lskyp on 25-Mar-17.
 */
public class MyController implements Initializable {

    @FXML @NotNull private ListView<String> mFilesList;

    @FXML private Button mReadFileButton;

    @FXML private Button mWriteFileButton;

    @Override
    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        assert mReadFileButton != null : "fx:id=\"mReadFileButton\" was not injected: check FXML: 'sample.fxml'";
        assert mWriteFileButton != null : "fx:id=\"mWriteFileButton\" was not injected: check FXML: 'sample.fxml'";
        assert mFilesList != null : "fx:id=\"mFilesList\" was not injected: check FXML: 'sample.fxml'";

        ObservableList<String> items = FXCollections.observableArrayList (new FileWorker().getFilesNames());

        mFilesList.setItems(items);
    }

    @FXML
    public void handleReadFileAction(ActionEvent actionEvent) {

        System.out.println("Love");
    }

    @FXML
    public void handleWriteFileAction(ActionEvent actionEvent) {

        System.out.println("You");
    }


}
