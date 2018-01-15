package com.kutash.taxibuber.command;

import com.kutash.taxibuber.service.CarService;
import com.kutash.taxibuber.service.LoginService;
import com.kutash.taxibuber.service.OrderService;
import com.kutash.taxibuber.service.UserService;

public enum CommandEnum {
    LOGIN(new LoginCommand(new LoginService())),
    SHOW_USERS(new ShowUsersCommand(new UserService())),
    EDIT(new EditUserCommand(new UserService())),
    LOGOUT(new LogoutCommand()),
    PHOTO(new PhotoCommand()),
    ORDER(new OrderCommand(new OrderService())),
    MAKE_ORDER(new MakeOrderCommand(new OrderService())),
    SHOW_CARS(new ShowCarsCommand(new CarService())),
    FREE_CARS(new FreeCarsCommand(new CarService())),
    PRICE(new PriceCommand(new OrderService())),
    CAR(new EditCarCommand(new CarService())),
    USER_INFO(new UserInfoCommand(new UserService()));

    Command command;

    CommandEnum(Command command){this.command=command;}

    public Command getCurrentCommand() {
        return command;
    }
}
