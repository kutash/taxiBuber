package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.resource.PhotoManager;
import com.kutash.taxibuber.util.FileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PhotoCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PHOTO = "photo";
    private static final String USER_ID = "userId";

    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"getting photo for contact");
        String photoPath = request.getParameter(PHOTO);
        String userId = request.getParameter(USER_ID);
        String path;
        if (StringUtils.isEmpty(photoPath)) {
            String appPath = request.getServletContext().getRealPath("");
            path = appPath + PhotoManager.getInstance().getProperty("avatar");
        } else {
            path = PhotoManager.getInstance().getProperty("avatars_path")+userId+File.separator+photoPath;
        }
        File file = new File(path);
        int buffSize = Integer.parseInt(PhotoManager.getInstance().getProperty("buffer_size"));

        response.reset();
        response.setBufferSize(buffSize);
        response.setContentType(PhotoManager.getInstance().getProperty("content_type"));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "avatar; filename=\"" + file.getName() + "\"");
        unloadFile(file, response, buffSize);
        return null;
    }

    private void unloadFile(File file, HttpServletResponse response, int buffSize){
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
}
