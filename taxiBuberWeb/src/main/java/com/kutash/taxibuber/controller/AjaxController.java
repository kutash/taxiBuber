package com.kutash.taxibuber.controller;

import com.google.gson.Gson;
import com.kutash.taxibuber.entity.Car;
import com.kutash.taxibuber.service.CarService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/buber")
public class AjaxController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.log(Level.INFO,"getting cars");
        String latitude = request.getParameter(LATITUDE);
        String longitude = request.getParameter(LONGITUDE);
        List<Car> cars = new CarService().findAllAvailable(latitude,longitude);
        String json = new Gson().toJson(cars);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
