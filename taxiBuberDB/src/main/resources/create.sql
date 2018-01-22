-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema taxi_buber
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema taxi_buber
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `taxi_buber` DEFAULT CHARACTER SET utf8 ;
USE `taxi_buber` ;

-- -----------------------------------------------------
-- Table `taxi_buber`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxi_buber`.`user` (
  `id_user` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'user id',
  `role` ENUM('ADMIN', 'CLIENT', 'DRIVER') NOT NULL COMMENT 'user role, one of 3 values',
  `email` VARCHAR(100) NOT NULL COMMENT 'user email, field must be unique because it is used to authenticate the user',
  `password` VARCHAR(255) NOT NULL COMMENT 'user password',
  `rating` FLOAT NOT NULL COMMENT 'user rating from 1.0 to 5.0',
  `name` VARCHAR(45) NOT NULL COMMENT 'user name',
  `surname` VARCHAR(45) NOT NULL COMMENT 'user surname',
  `patronymic` VARCHAR(45) NULL DEFAULT NULL COMMENT 'user patronymic, optional field',
  `birthday` DATE NULL DEFAULT NULL COMMENT 'user birthday, optional field',
  `photo_path` VARCHAR(255) NULL DEFAULT NULL COMMENT 'path to the user\'s photo  if there is one',
  `phone` VARCHAR(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC))
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `taxi_buber`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxi_buber`.`address` (
  `id_address` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'address id',
  `address` VARCHAR(255) NOT NULL COMMENT 'name of the locality',
  `id_user` INT(10) UNSIGNED NOT NULL COMMENT 'each address must have a specific user',
  PRIMARY KEY (`id_address`),
  INDEX `fk_address_user1_idx` (`id_user` ASC),
  CONSTRAINT `fk_address_user1`
  FOREIGN KEY (`id_user`)
  REFERENCES `taxi_buber`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `taxi_buber`.`car_brand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxi_buber`.`car_brand` (
  `id_brand` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'brand id',
  `name` VARCHAR(45) NOT NULL COMMENT 'brand title',
  PRIMARY KEY (`id_brand`))
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `taxi_buber`.`car`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxi_buber`.`car` (
  `id_car` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'car id',
  `registration_number` CHAR(8) NOT NULL COMMENT 'car state registration number, 8 characters',
  `model` VARCHAR(45) NOT NULL COMMENT 'car model',
  `photo_path` VARCHAR(255) NULL DEFAULT NULL COMMENT 'path to the car\'s photo  if there is one',
  `is_available` BIT(1) NOT NULL COMMENT 'current status of the car, must be true or false',
  `latitude` CHAR(10) NULL DEFAULT NULL COMMENT 'geographical latitude of the car at time of order',
  `longitude` CHAR(10) NULL DEFAULT NULL COMMENT 'eographical longitude of the car at time of order',
  `id_brand` INT(10) UNSIGNED NOT NULL COMMENT 'car brand id',
  `capacity` ENUM('CAR', 'MINIBUS', 'MINIVAN') NULL DEFAULT NULL,
  `id_user` INT(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_car`, `id_brand`, `id_user`),
  UNIQUE INDEX `state_number_UNIQUE` (`registration_number` ASC),
  INDEX `fk_car_car_brand1_idx` (`id_brand` ASC),
  INDEX `fk_car_user1_idx` (`id_user` ASC),
  CONSTRAINT `fk_car_car_brand1`
  FOREIGN KEY (`id_brand`)
  REFERENCES `taxi_buber`.`car_brand` (`id_brand`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_car_user1`
  FOREIGN KEY (`id_user`)
  REFERENCES `taxi_buber`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `taxi_buber`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxi_buber`.`comment` (
  `id_comment` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `text` TEXT NOT NULL,
  `id_user` INT(10) UNSIGNED NOT NULL,
  `date` DATETIME NOT NULL,
  `id_reviewer` INT(10) UNSIGNED NOT NULL,
  `mark` TINYINT(1) UNSIGNED NOT NULL,
  PRIMARY KEY (`id_comment`),
  UNIQUE INDEX `id_comment_UNIQUE` (`id_comment` ASC),
  INDEX `fk_comment_user1_idx` (`id_user` ASC),
  INDEX `fk_comment_user2_idx` (`id_reviewer` ASC),
  CONSTRAINT `fk_comment_user1`
  FOREIGN KEY (`id_user`)
  REFERENCES `taxi_buber`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user2`
  FOREIGN KEY (`id_reviewer`)
  REFERENCES `taxi_buber`.`user` (`id_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `taxi_buber`.`trip`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `taxi_buber`.`trip` (
  `id_trip` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'trip id',
  `price` DECIMAL(8,2) UNSIGNED NOT NULL COMMENT 'trip price',
  `date` DATETIME NOT NULL COMMENT 'trip date and time',
  `distance` FLOAT UNSIGNED NOT NULL COMMENT 'total distance of the trip',
  `id_car` INT(10) UNSIGNED NOT NULL COMMENT 'id of the car that made the trip',
  `departure_address` INT(10) UNSIGNED NOT NULL COMMENT 'id departure address',
  `destination_address` INT(10) UNSIGNED NOT NULL COMMENT 'id destination address',
  `status` ENUM('ORDERED', 'COMPLETED', 'STARTED') NOT NULL,
  PRIMARY KEY (`id_trip`),
  INDEX `fk_trip_car1_idx` (`id_car` ASC),
  INDEX `fk_trip_address1_idx` (`departure_address` ASC),
  INDEX `fk_trip_address2_idx` (`destination_address` ASC),
  CONSTRAINT `fk_trip_address1`
  FOREIGN KEY (`departure_address`)
  REFERENCES `taxi_buber`.`address` (`id_address`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trip_address2`
  FOREIGN KEY (`destination_address`)
  REFERENCES `taxi_buber`.`address` (`id_address`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_trip_car1`
  FOREIGN KEY (`id_car`)
  REFERENCES `taxi_buber`.`car` (`id_car`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
  ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;