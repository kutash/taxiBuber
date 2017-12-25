package com.kutash.taxibuber.command;

import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.main.FileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class PhotoCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PHOTO = "photo";

    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"getting photo for contact");
        Properties properties = new Properties();
        try {
            properties.load(PhotoCommand.class.getResourceAsStream("/photo.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.ERROR,"Exception while loading properties {}",e);
        }
        String photoPath = request.getParameter(PHOTO);
        String path = "";
        if (StringUtils.isEmpty(photoPath)) {
            String appPath = request.getServletContext().getRealPath("");
            path = appPath + properties.getProperty("AVATAR");
        } else {
            path = properties.getProperty("AVATARS_PATH")+photoPath;
        }
        File file = new File(path);
        int buffSize = Integer.parseInt(properties.getProperty("BUFFER_SIZE"));

        response.reset();
        response.setBufferSize(buffSize);
        response.setContentType(properties.getProperty("CONTENT_TYPE"));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "avatar; filename=\"" + file.getName() + "\"");
        new FileManager().unloadFile(file, response, buffSize);
        return null;
    }
}
