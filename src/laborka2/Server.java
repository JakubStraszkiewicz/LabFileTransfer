/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laborka2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import laborka2.ReceiveFileTask;

/**
 *
 * @author Jakub
 */
public class Server implements Runnable{
    private ExecutorService executor = Executors.newFixedThreadPool(2);

    public Server()  {
       
    }

    /**
     *
     * @throws IOException
     */
    @Override
    public void run(){
        try (ServerSocket serverSocket = new ServerSocket(laborka2.Laborka2.PORT)) {
            while (true) {
                final Socket socket = serverSocket.accept();
                    executor.submit(new ReceiveFileTask(socket));
                }
        
            }catch (IOException ex) {
            ex.printStackTrace();
        }    
    }
    
}
