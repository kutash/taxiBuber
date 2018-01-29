package com.kutash.taxibuber.command;

import com.google.gson.Gson;
import com.kutash.taxibuber.controller.Router;
import com.kutash.taxibuber.entity.Address;
import com.kutash.taxibuber.entity.Status;
import com.kutash.taxibuber.service.AddressService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAddressCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String ADDRESS_ID = "addressId";
    private AddressService addressService;

    DeleteAddressCommand(AddressService addressService){
        this.addressService = addressService;
    }

    @Override
    public Router execute(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.log(Level.INFO,"deleting user");
        Router router = new Router();
        int addressId = Integer.parseInt(request.getParameter(ADDRESS_ID));
        Address address = addressService.findAddressById(addressId);
        address.setStatus(Status.ARCHIVED);
        addressService.update(address);
        String json = new Gson().toJson("deleted");
        router.setPage(json);
        return router;
    }
}
