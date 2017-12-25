package com.kutash.test.query;

public class ListOfQueries {
    public static final String CREATE_DB = "CREATE DATABASE  IF NOT EXISTS `taxi_buber_test`";
    public static final String USE_DB = "USE `taxi_buber_test`";
    public static final String CREATE_USER = "CREATE TABLE `user` (\n" +
            "  `id_user` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `role` enum('ADMIN','CLIENT','DRIVER') NOT NULL,\n" +
            "  `email` varchar(100) NOT NULL,\n" +
            "  `password` varchar(255) NOT NULL,\n" +
            "  `rating` float NOT NULL,\n" +
            "  `name` varchar(45) NOT NULL,\n" +
            "  `surname` varchar(45) NOT NULL,\n" +
            "  `patronymic` varchar(45) DEFAULT NULL,\n" +
            "  `birthday` date DEFAULT NULL,\n" +
            "  `photo_path` varchar(255) DEFAULT NULL,\n" +
            "  `phone` varchar(20) DEFAULT NULL,\n" +
            "  PRIMARY KEY (`id_user`),\n" +
            "  UNIQUE KEY `email_UNIQUE` (`email`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
    public static final String FILL_USER = "INSERT INTO `user` VALUES (1,'ADMIN','petrov123@mail.ru','2222',0,'Петр','Петров','Петрович','1989-11-05',NULL, NULL),(2,'DRIVER','borisov123@mail.ru','3333',4.2,'Борис','Борискин','Борисович','2003-05-11',NULL, '8(029)2851148'),(3,'DRIVER','tolik123@mail.ru','5555',4,'Анатолий','Моржов','Петрович','1995-09-08',NULL,'8(029)3351148'),(4,'CLIENT','abram123@mail.ru','6666',5,'Аркадий','Абрамович','Иванович','1985-08-10',NULL,'8(029)3366668'),(5,'CLIENT','vasya123@mail.ru','7777',3.2,'Василий','Васин','Васильевич','1969-07-15',NULL,'8(029)2356489')";
    public static final String CREATE_COUNTRY = "CREATE TABLE `country` (\n" +
            "  `id_country` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(100) NOT NULL,\n" +
            "  PRIMARY KEY (`id_country`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
    public static final String FILL_COUNTRY = "INSERT INTO `country` VALUES (1,'Belarus'),(2,'Russia'),(3,'Italy')";
    public static final String CREATE_ADDRESS = "CREATE TABLE `address` (\n" +
            "  `id_address` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `city` varchar(45) NOT NULL,\n" +
            "  `street` varchar(45) NOT NULL,\n" +
            "  `house` varchar(45) DEFAULT NULL,\n" +
            "  `flat` varchar(45) DEFAULT NULL,\n" +
            "  `type` enum('HOME','WORK','OTHER') DEFAULT NULL,\n" +
            "  `latitude` char(10) NOT NULL,\n" +
            "  `longitude` char(10) NOT NULL,\n" +
            "  `id_user` int(10) unsigned NOT NULL,\n" +
            "  `id_country` int(10) unsigned NOT NULL,\n" +
            "  PRIMARY KEY (`id_address`),\n" +
            "  KEY `fk_address_user1_idx` (`id_user`),\n" +
            "  KEY `fk_address_country1_idx` (`id_country`),\n" +
            "  CONSTRAINT `fk_address_country1` FOREIGN KEY (`id_country`) REFERENCES `country` (`id_country`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `fk_address_user1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
    public static final String FILL_ADDRESS = "INSERT INTO `address` VALUES (1,'Минск','Казинца','38','176','HOME','236548971','125879463',4,1),(2,'Минск','пр. Машерова','32','567','WORK','236848971','175879463',4,1),(3,'Минск','Ульяновская','123','4','HOME','236548971','125879463',5,1),(4,'Минск','Асаналиева','7','223','WORK','236548971','125879463',5,1),(5,'Минск','пл. Свободы','12','20','OTHER','236548971','125879463',1,1),(6,'Минск','пр. Победителей','32','176','HOME','236548971','125879463',1,1)";
    public static final String CREATE_CAR_BRAND = "CREATE TABLE `car_brand` (\n" +
            "  `id_brand` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(45) NOT NULL,\n" +
            "  PRIMARY KEY (`id_brand`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
    public static final String FILL_CAR_BRAND = "INSERT INTO `car_brand` VALUES (1,'ACURA'),(2,'SKODA'),(3,'BMW'),(4,'AUDI')";
    public static final String CREATE_CAR = "CREATE TABLE `car` (\n" +
            "  `id_car` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `registration_number` char(8) NOT NULL,\n" +
            "  `body_type` enum('SEDAN','HATCHBACK','COUPE','SUV','WAGON','CABRIOLET','MINIVAN','MINIBUS') NOT NULL,\n" +
            "  `model` varchar(45) NOT NULL,\n" +
            "  `photo_path` varchar(255) DEFAULT NULL,\n" +
            "  `is_available` bit(1) NOT NULL,\n" +
            "  `latitude` char(10) NOT NULL,\n" +
            "  `longitude` char(10) NOT NULL,\n" +
            "  `id_brand` int(10) unsigned NOT NULL,\n" +
            "  `id_user` int(10) unsigned NOT NULL,\n" +
            "  PRIMARY KEY (`id_car`,`id_user`,`id_brand`),\n" +
            "  UNIQUE KEY `state_number_UNIQUE` (`registration_number`),\n" +
            "  KEY `fk_car_user_idx` (`id_user`),\n" +
            "  KEY `fk_car_car_brand1_idx` (`id_brand`),\n" +
            "  CONSTRAINT `fk_car_car_brand1` FOREIGN KEY (`id_brand`) REFERENCES `car_brand` (`id_brand`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `fk_car_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
    public static final String FILL_CAR = "INSERT INTO `car` VALUES (1,'3214MA-5','SEDAN','Fabia',NULL,1,'147895623','235987415',2,2),(2,'3315HJ-7','COUPE','E46',NULL,'\u0001','147895623','235987415',3,3)";
    public static final String CREATE_TRIP = "CREATE TABLE `trip` (\n" +
            "  `id_trip` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `price` decimal(8,2) unsigned NOT NULL,\n" +
            "  `date` datetime NOT NULL,\n" +
            "  `distance` float unsigned NOT NULL,\n" +
            "  `status` enum('ORDERED','ACCEPTED','COMPLETED') NOT NULL,\n" +
            "  `id_car` int(10) unsigned NOT NULL,\n" +
            "  `departure_address` int(10) unsigned NOT NULL,\n" +
            "  `destination_address` int(10) unsigned NOT NULL,\n" +
            "  PRIMARY KEY (`id_trip`),\n" +
            "  KEY `fk_trip_car1_idx` (`id_car`),\n" +
            "  KEY `fk_trip_address1_idx` (`departure_address`),\n" +
            "  KEY `fk_trip_address2_idx` (`destination_address`),\n" +
            "  CONSTRAINT `fk_trip_address1` FOREIGN KEY (`departure_address`) REFERENCES `address` (`id_address`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `fk_trip_address2` FOREIGN KEY (`destination_address`) REFERENCES `address` (`id_address`) ON DELETE NO ACTION ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `fk_trip_car1` FOREIGN KEY (`id_car`) REFERENCES `car` (`id_car`) ON DELETE NO ACTION ON UPDATE NO ACTION\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8";
    public static final String FILL_TRIP = "INSERT INTO `trip` VALUES (1,15.30,'2017-01-05 23:59:59',23.2,'COMPLETED',1,1,2),(2,22.25,'2017-02-04 01:02:03',28.5,'COMPLETED',2,3,4)";
    public static final String DROP_DB = "DROP DATABASE `taxi_buber_test`";
}
