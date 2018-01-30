package com.kutash.taxibuber.command;

import com.kutash.taxibuber.service.*;

public enum CommandEnum {
    LOGIN(new LoginCommand(new LoginService(),new CarService())),
    SHOW_USERS(new ShowUsersCommand(new UserService())),
    EDIT(new EditUserCommand(new UserService(),new CarService(),new AddressService())),
    LOGOUT(new LogoutCommand(new CarService())),
    PHOTO(new PhotoCommand()),
    MAIN(new MainCommand(new AddressService(),new CarService())),
    MAKE_ORDER(new MakeOrderCommand(new TripService(),new CarService(),new AddressService())),
    FREE_CARS(new FreeCarsCommand(new CarService())),
    PRICE(new PriceCommand(new CarService())),
    USER_INFO(new UserInfoCommand(new UserService())),
    NEW_ORDER(new NewOrderCommand(new TripService(),new AddressService())),
    START_TRIP(new StartTripCommand(new TripService(),new CarService())),
    COMPLETE_TRIP(new CompleteTripCommand(new TripService(),new CarService())),
    SAVE_USER(new SaveUserCommand(new UserService())),
    SET_COORDINATES(new SetCoordinatesCommand(new CarService())),
    SAVE_CAR(new SaveCarCommand(new CarService(),new UserService())),
    UPDATE_USER(new UpdateUserCommand(new UserService())),
    CHANGE_PASSWORD(new ChangePasswordCommand(new UserService())),
    BAN(new BanCommand(new UserService(),new CarService())),
    DELETE(new DeleteCommand(new UserService(),new CarService())),
    DELETE_ADDRESS(new DeleteAddressCommand(new AddressService())),
    TRIPS(new ShowTripsCommand(new TripService())),
    COMMENT(new CommentCommand(new UserService())),
    DELETE_CAR(new DeleteCarCommand(new CarService(),new UserService())),
    CANCEL(new CancelCommand()),
    SET_AVAILABLE(new CarAvailableCommand(new CarService()));

    Command command;

    CommandEnum(Command command){this.command=command;}

    public Command getCurrentCommand() {
        return command;
    }
}
