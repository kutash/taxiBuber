package com.kutash.taxibuber.command;

import com.kutash.taxibuber.service.*;

/**
 * The enum Command enum.
 */
public enum CommandEnum {
    /**
     * The Login.
     */
    LOGIN(new LoginCommand(new LoginService())),
    /**
     * The Show users.
     */
    SHOW_USERS(new ShowUsersCommand(new UserService())),
    /**
     * The Edit.
     */
    EDIT(new EditUserCommand(new UserService(),new CarService(),new AddressService())),
    /**
     * The Logout.
     */
    LOGOUT(new LogoutCommand(new LoginService())),
    /**
     * The Photo.
     */
    PHOTO(new PhotoCommand()),
    /**
     * The Main.
     */
    MAIN(new MainCommand(new AddressService(),new CarService(),new TripService())),
    /**
     * The Make order.
     */
    MAKE_ORDER(new MakeOrderCommand(new TripService(),new CarService())),
    /**
     * The Free cars.
     */
    FREE_CARS(new FreeCarsCommand(new CarService())),
    /**
     * The Price.
     */
    PRICE(new PriceCommand(new CarService())),
    /**
     * The User info.
     */
    USER_INFO(new UserInfoCommand(new UserService())),
    /**
     * The Start trip.
     */
    START_TRIP(new StartTripCommand(new TripService())),
    /**
     * The Complete trip.
     */
    COMPLETE_TRIP(new CompleteTripCommand(new TripService())),
    /**
     * The Save user.
     */
    SAVE_USER(new SaveUserCommand(new UserService())),
    /**
     * The Set coordinates.
     */
    SET_COORDINATES(new SetCoordinatesCommand(new CarService())),
    /**
     * The Save car.
     */
    SAVE_CAR(new SaveCarCommand(new CarService())),
    /**
     * The Update user.
     */
    UPDATE_USER(new UpdateUserCommand(new UserService())),
    /**
     * The Change password.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand(new UserService())),
    /**
     * The Ban.
     */
    BAN(new BanCommand(new UserService())),
    /**
     * The Delete.
     */
    DELETE(new DeleteCommand(new UserService())),
    /**
     * The Delete address.
     */
    DELETE_ADDRESS(new DeleteAddressCommand(new AddressService())),
    /**
     * The Trips.
     */
    TRIPS(new ShowTripsCommand(new TripService())),
    /**
     * The Comment.
     */
    COMMENT(new CommentCommand(new UserService())),
    /**
     * The Delete car.
     */
    DELETE_CAR(new DeleteCarCommand(new CarService())),
    /**
     * The Cancel.
     */
    CANCEL(new CancelCommand()),
    /**
     * The Set available.
     */
    SET_AVAILABLE(new CarAvailableCommand(new CarService())),
    /**
     * The Forgot.
     */
    FORGOT(new ForgotPasswordCommand(new LoginService()));

    /**
     * The Command.
     */
    Command command;

    CommandEnum(Command command){this.command=command;}

    /**
     * Gets current command.
     *
     * @return the current command
     */
    public Command getCurrentCommand() {
        return command;
    }
}
