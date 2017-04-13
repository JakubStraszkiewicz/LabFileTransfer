/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laborka2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.concurrent.Task;

/**
 *
 * @author Jakub
 */
public class SendFileTask extends Task<Void>{

    File file;
    Socket socket;
    
    public SendFileTask(File file,Socket socket){
        this.file=file;
        this.socket=socket;
    }
    
    @Override
    protected Void call() throws Exception {
        
        try(BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            DataOutputStream dos = new DataOutputStream(out))
            {
                byte[] buffer = new byte [4096];
                int readSize;
                int actuallyReadSize=0;
                updateMessage("Initializing...");
                dos.writeUTF(file.getName());
                while((readSize = in.read(buffer))!=-1)
                {
                    dos.write(buffer,0,readSize);
                    actuallyReadSize+=readSize;
                    updateProgress(actuallyReadSize,file.length());
                }
                updateMessage("Done");
            }
        return null;
    }
    
}
