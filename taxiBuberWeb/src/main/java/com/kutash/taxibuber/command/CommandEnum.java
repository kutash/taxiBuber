package com.kutash.taxibuber.command;

import com.kutash.taxibuber.service.*;

public enum CommandEnum {
    LOGIN(new LoginCommand(new LoginService())),
    SHOW_USERS(new ShowUsersCommand(new UserService())),
    EDIT(new EditUserCommand(new UserService())),
    LOGOUT(new LogoutCommand()),
    PHOTO(new PhotoCommand()),
    MAIN(new MainCommand(new AddressService(),new CarService())),
    MAKE_ORDER(new MakeOrderCommand(new TripService(),new CarService(),new AddressService())),
    SHOW_CARS(new ShowCarsCommand(new CarService())),
    FREE_CARS(new FreeCarsCommand(new CarService())),
    PRICE(new PriceCommand(new CarService())),
    CAR(new EditCarCommand(new CarService())),
    USER_INFO(new UserInfoCommand(new UserService())),
    NEW_ORDER(new NewOrderCommand(new TripService(),new AddressService())),
    START_TRIP(new StartTripCommand(new TripService())),
    COMPLETE_TRIP(new CompleteTripCommand(new TripService(),new CarService())),
    SAVE_USER(new SaveUserCommand(new UserService())),
    SET_COORDINATES(new SetCoordinatesCommand(new CarService())),
    SAVE_CAR(new SaveCarCommand(new CarService()));

    Command command;

    CommandEnum(Command command){this.command=command;}

    public Command getCurrentCommand() {
        return command;
    }
}
