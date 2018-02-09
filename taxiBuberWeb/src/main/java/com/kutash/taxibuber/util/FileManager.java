package com.kutash.taxibuber.util;

import com.kutash.taxibuber.resource.PhotoManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileManager {

    private static final Logger LOGGER = LogManager.getLogger();

    public String savePhoto(Part part,int id, boolean isCar){
        LOGGER.log(Level.INFO,"saving photo");
        String fileName = "";
        String photoPath = PhotoManager.getInstance().getProperty("avatars_path") + id;
        File fileSaveDir;
        if (part.getSize()>0){
            fileSaveDir = new File(photoPath);
            if (!fileSaveDir.exists()) {
                boolean created = fileSaveDir.mkdirs();
                if (!created){
                    LOGGER.log(Level.ERROR,"Directory wasn't created");
                }
            }
            String contentDisp = part.getHeader("Content-Disposition");
            String expansion = getExpansion(contentDisp);
            if (isCar){
                fileName = id + "car" + expansion;
            }else {
                fileName = id + expansion;
            }
            photoPath += File.separator + fileName;
            try {
                part.write(photoPath);
            } catch (IOException e) {
                LOGGER.log(Level.ERROR,"Exception while saving photo",e);
            }
        }
        return fileName;
    }

    private String getExpansion(String contentDisp){
        String expansion = "";
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                expansion = s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        expansion = expansion.substring(expansion.indexOf("."));
        return expansion;
    }

    public void deleteFile(String path,int id) {
        String photoPath = PhotoManager.getInstance().getProperty("avatars_path") + id + File.separator + path;
        File file = new File(photoPath);
        if (file.canWrite() && file.exists()) {
            boolean deleted = file.delete();
            if (!deleted){
                LOGGER.log(Level.ERROR, "File wasn't deleted");
            }
        }
    }

    public void deleteFolder(int id) {
        String path = PhotoManager.getInstance().getProperty("avatars_path") + id;
        File file = new File(path);
        if(file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    boolean deleted = f.delete();
                    if (!deleted){
                        LOGGER.log(Level.ERROR, "File wasn't deleted");
                    }
                }
            }
            boolean deleted = file.delete();
            if (!deleted){
                LOGGER.log(Level.ERROR, "File wasn't deleted");
            }
        }
    }
}
