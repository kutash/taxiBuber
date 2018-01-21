package com.kutash.taxibuber.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class FileManager {

    private static final Logger LOGGER = LogManager.getLogger();

    public String savePhoto(Part part,int id){
        LOGGER.log(Level.INFO,"saving photo");
        String fileName = null;
        Properties properties = new Properties();
        try {
            properties.load(FileManager.class.getResourceAsStream("/photo.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.ERROR,"Exception while loading photo properties",e);
        }
        String photoPath = properties.getProperty("AVATARS_PATH") + id;
        File fileSaveDir;
        if (part.getSize()>0){
            fileSaveDir = new File(photoPath);
            if (!fileSaveDir.exists()) {
                boolean created = fileSaveDir.mkdirs();
                if (!created){
                    LOGGER.log(Level.ERROR,"Directory wasn't created");
                }
            }
            deleteAllFilesFolder(photoPath);
            String contentDisp = part.getHeader("Content-Disposition");
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    fileName = s.substring(s.indexOf("=") + 2, s.length() - 1);
                }
            }
            fileName = id + fileName.substring(fileName.indexOf("."));
            photoPath += File.separator + fileName;
            try {
                part.write(photoPath);
            } catch (IOException e) {
                LOGGER.log(Level.ERROR,"Exception while saving photo",e);
            }
        }
        return fileName;
    }

    public void unloadFile(File file, HttpServletResponse response, int buffSize){
        OutputStream out = null;
        FileInputStream in = null;
        try{
            out = response.getOutputStream();
            in = new FileInputStream(file);
            byte[] buffer = new byte[buffSize];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Exception while unloading photo {}",e);
        }
        finally {
            try {
                if (out != null && in != null) {
                    out.close();
                    in.close();
                }
            } catch (IOException e) {
                LOGGER.log(Level.ERROR, "Error in closing streams");
            }
        }
    }

    public void deleteFile(String path) {
        File file = new File(path);
        if (file.canWrite() && file.exists()) {
            boolean deleted = file.delete();
            if (!deleted){
                LOGGER.log(Level.ERROR, "File wasn't deleted");
            }
        }
    }

    public void deleteEmptyFolder(File file){
        String[] tempFiles = file.list();
        if (tempFiles != null && file.isDirectory() && file.exists()) {
            int length = tempFiles.length;
            if (length == 0) {
                boolean deleted = file.delete();
                if (!deleted) {
                    LOGGER.log(Level.ERROR, "Empty directory wasn't deleted");
                }
            }
        }
    }

    private void deleteAllFilesFolder(String path) {
        File[] files = new File(path).listFiles();
        if (files != null) {
            for (File myFile : files) {
                if (myFile.isFile()) {
                    boolean deleted = myFile.delete();
                    if (!deleted){
                        LOGGER.log(Level.ERROR,"File wasn't deleted");
                    }
                }
            }
        }
    }
}
