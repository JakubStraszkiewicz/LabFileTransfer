/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laborka2;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javax.swing.JFileChooser;
import laborka2.SendFileTask;

/**
 *
 * @author Jakub
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    public ToggleButton chooseButton;
    @FXML
    public ToggleButton clientButton;
    @FXML
    public ToggleButton serverButton;
    @FXML
    public Label statusLabel;
    @FXML
    private ProgressBar progressBar;
            
    private static final int PORT = 1337;

    private ExecutorService executor = Executors.newFixedThreadPool(4);
    
    private File file;
        
    private StringProperty statusMsg = new SimpleStringProperty(STATUS_CONNECT);
    private static String STATUS_CONNECT = "Uruchom serwer lub połącz jako klient";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statusLabel.textProperty().bind(statusMsg);
        //tylko jeden z przycisków klient/serwer może być włączony
        clientButton.disableProperty().bind(serverButton.selectedProperty());
        serverButton.disableProperty().bind(clientButton.selectedProperty());
    }    
    
    public void serverToggle(MouseEvent event) throws IOException, ClassNotFoundException {
       Thread thread = new Thread(new Server());
       thread.start();
    }
    
    public void clientToggle(MouseEvent event) throws IOException, ClassNotFoundException {
       chooseButton.setDisable(false);
    }
    
    public void chooseToggle(MouseEvent event) throws IOException{
        JFileChooser fileChooser = new JFileChooser();
        progressBar.setDisable(false);
        fileChooser.showOpenDialog(null);
        file = fileChooser.getSelectedFile();
        Socket socket = new Socket("127.0.0.1",PORT);
        Task<Void> sendFileTask = new SendFileTask(file,socket);
        statusLabel.textProperty().bind(sendFileTask.messageProperty());
        progressBar.progressProperty().bind(sendFileTask.progressProperty());
        executor.submit(sendFileTask);
    }
    
   
}
