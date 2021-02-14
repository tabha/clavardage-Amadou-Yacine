/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author amadou
 */
public class LocalData {
    
    private static String basePath = new File("").getAbsolutePath();
    public static String DATADIR="/src/chatData/";
    public File getImage(String name){
        
        String basePath = new File("").getAbsolutePath();
        String path =basePath+"/src/chatData/"+name;
        return new File(path);
    }
    private static void copyFile(File sourceFile, File destFile)
        throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    public static void saveSentFile(File sourceFile,String fileName) throws IOException{
        String basePath = new File("").getAbsolutePath();
        String path =basePath+"/src/chatData/sent/"+fileName;
        File destinationFile = new File(path);
        copyFile(sourceFile,destinationFile);
    }
    
    public static String getSentFile(String fileName) {
        File file =  new File(basePath+DATADIR+"sent/"+fileName);
        if(file.exists() && file.isFile()){
            return file.getAbsolutePath();
        }
        return null;
    }

    public static String getReceivedFile(String fileName) {
        File file =  new File(basePath+DATADIR+"received/"+fileName);
        if(file.exists() && file.isFile()){
            return file.getAbsolutePath();
        }
        return null;
    }
    
    /*
    public static void main(String...args){
        String basePath = new File("").getAbsolutePath();
        String path =basePath+"/src/chatData/image2-1613048602074.png";
        File source = new File(path);
        try {
            saveSentFile(source,"image2-1613048602074.png");
        } catch (IOException ex) {
            Logger.getLogger(LocalData.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(path);
        
    }*/
}
