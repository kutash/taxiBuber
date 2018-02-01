package com.kutash.taxibuber.command;

import com.kutash.taxibuber.service.*;

public enum CommandEnum {
    LOGIN(new LoginCommand(new LoginService())),
    SHOW_USERS(new ShowUsersCommand(new UserService())),
    EDIT(new EditUserCommand(new UserService(),new CarService(),new AddressService())),
    LOGOUT(new LogoutCommand(new LoginService())),
    PHOTO(new PhotoCommand()),
    MAIN(new MainCommand(new AddressService(),new CarService(),new TripService())),
    MAKE_ORDER(new MakeOrderCommand(new TripService(),new CarService())),
    FREE_CARS(new FreeCarsCommand(new CarService())),
    PRICE(new PriceCommand(new CarService())),
    USER_INFO(new UserInfoCommand(new UserService())),
    NEW_ORDER(new NewOrderCommand(new TripService())),
    START_TRIP(new StartTripCommand(new TripService())),
    COMPLETE_TRIP(new CompleteTripCommand(new TripService())),
    SAVE_USER(new SaveUserCommand(new UserService())),
    SET_COORDINATES(new SetCoordinatesCommand(new CarService())),
    SAVE_CAR(new SaveCarCommand(new CarService(),new UserService())),
    UPDATE_USER(new UpdateUserCommand(new UserService())),
    CHANGE_PASSWORD(new ChangePasswordCommand(new UserService())),
    BAN(new BanCommand(new UserService())),
    DELETE(new DeleteCommand(new UserService())),
    DELETE_ADDRESS(new DeleteAddressCommand(new AddressService())),
    TRIPS(new ShowTripsCommand(new TripService())),
    COMMENT(new CommentCommand(new UserService())),
    DELETE_CAR(new DeleteCarCommand(new CarService())),
    CANCEL(new CancelCommand()),
    SET_AVAILABLE(new CarAvailableCommand(new CarService())),
    FORGOT(new ForgotPasswordCommand(new LoginService()));

    Command command;

    CommandEnum(Command command){this.command=command;}

    public Command getCurrentCommand() {
        return command;
    }
}
