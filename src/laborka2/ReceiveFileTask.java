/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laborka2;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Jakub
 */
public class ReceiveFileTask implements Runnable{

    private Socket socket;
    public ReceiveFileTask(Socket socket){
        this.socket=socket;
    }

    /**
     *
     * @throws Exception
     */
    @Override
    public void run(){
       
       try(BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
           DataInputStream dis = new DataInputStream(in);)
           {
               String name = dis.readUTF();
               try( FileOutputStream out = new FileOutputStream(name))
               {
               byte[] buffer = new byte[4096];
               int readSize;
               while((readSize = in.read(buffer))!=-1)
                   out.write(buffer,0,readSize);
               }
           }catch(FileNotFoundException e){
               e.printStackTrace();  
    }catch(IOException ex){
        ex.printStackTrace();
    }
           
    
}
}
