INSERT INTO user (surname, `name`, patronymic, email, `password`, birthday, role, rating,phone,photo_path)
VALUES ('Петров', 'Петр', 'Петрович', 'petrov123@mail.ru', '8c1wI+T4B4o=', '1989-11-05', 'ADMIN', 0,NULL,'1.jpg'),
  ('Борискин', 'Борис', 'Борисович', 'borisov123@mail.ru', '3333', '2000-05-12', 'DRIVER',4.2,'8(029)284-11-48','2.jpg'),
  ('Прекрасная', 'Елена', 'Васильевна', 'lenka123@mail.ru', '4444', '1988-10-03', 'DRIVER',3.5,'8(029)284-11-48','3.jpg'),
  ('Моржов', 'Анатолий', 'Петрович', 'tolik123@mail.ru', 'A4LrnjLlKLw=', '1995-09-08','DRIVER',4.0,'8(029)284-11-48','4.jpg'),
  ('Абрамович', 'Аркадий', 'Иванович', 'abram123@mail.ru', 'A4LrnjLlKLw=', '1984-08-10', 'CLIENT', 5.0,'8(029)284-11-45','5.jpg'),
  ('Васин', 'Василий', 'Васильевич', 'vasya123@mail.ru', '7777', '1969-07-15', 'CLIENT',3.2,'8(029)284-11-48','6.jpg'),
  ('Иванов', 'Александр', 'Федорович', 'sasha123@mail.ru', '8888', '1970-06-16', 'CLIENT',4.3,'8(029)284-11-48','7.jpg'),
  ('Симонов', 'Николай', 'Михайлович', 'kolay123@mail.ru', '9999', '1972-05-18', 'CLIENT',3.8,'8(029)284-11-48','8.jpg'),
  ('Плахотин', 'Иван', 'Борисович', 'ivan1234@mail.ru', '1010', '1986-05-12', 'DRIVER',4.4,'8(029)284-11-48','9.jpg'),
  ('Поршнева', 'Екатерина', 'Васильевна', 'katya1234@mail.ru', '2020', '1990-06-13', 'DRIVER',4.9,'8(029)284-11-48','10.jpg'),
  ('Малашевич', 'Петр', 'Семенович', 'petya1234@mail.ru', '3030', '1985-08-15', 'DRIVER',4.1,'8(029)284-11-48','11.jpg'),
  ('Куташ', 'Алла', 'Ивановна', 'ala1234@mail.ru', '4040', '1983-09-16', 'CLIENT',3.9,'8(029)284-11-48','12.jpg');


INSERT INTO address (address, id_user)
VALUES ('Минск Казинца 38', 5),
  ('Минск пр. Машерова 32', 5),
  ('Минск Ульяновская 123', 8),
  ('Минск Асаналиева 7',8),
  ('Минск пр. Кутузовский 79с', 12),
  ('Минск пл. Свободы 12', 12),
  ('Минск пр. Победителей 32', 7),
  ('Минск бул. Шевченко 12', 7),
  ('Минск пр. Партизанский 14', 1),
  ('Минск Лидская 8', 6),
  ('Минск Неманская 23', 6),
  ('Минск Кунцевщина 38', 2);


INSERT INTO car_brand (`name`) VALUES ('﻿ACURA'),('ALFA_ROMEO'),('ARO'),('ASIA'),('ASTON_MARTIN'),('AUDI'),('AUSTIN'),('BAW'),
  ('BEIJING'),('BENTLEY'),('BMW'),('BRILLIANCE'),('BRISTOL'),('BUGATTI'),('BUICK'),('BYD'),('VECTOR'),('VENTURI'),('VOLKSWAGEN'),
  ('VOLVO'),('VORTEX'),('WARTBURG'),('WESTFIELD'),('WIESMANN'),('WULING'),('LADA'),('GEELY'),('GEO'),('GMC'),('GREAT_WALL'),('DACIA'),
  ('DADI'),('DAEWOO'),('DAIHATSU'),('DAIMLER'),('DALLAS'),('DERWAYS'),('DE TOMASO'),('DODGE'),('DONG FENG'),('DONINVEST'),('JEEP'),
  ('JIANGLING'),('JIANGNAN'),('EAGLE'),('ZASTAVA'),('ZX'),('INFINITI'),('INNOCENTI'),('INVICTA'),('IRAN_KHODRO'),('ISDERA'),('ISUZU'),
  ('IVECO'),('XIN KAI'),('CADILLAC'),('CALLAWAY'),('CARBODIES'),('CATERHAM'),('CITROEN'),('CIZETA'),('COGGIOLA'),('KIA'),('KOENIGSEGG'),
  ('QVALE'),('КАМАЗ'),('LAMBORGHINI'),('LANCIA'),('LAND ROVER'),('LANDWIND'),('LEXUS'),('LIFAN'),('LINCOLN'),('LOTUS'),('LTI'),('LUXGEN'),
  ('ЛУАЗ'),('MAHINDRA'),('MARCOS'),('MARLIN'),('MARUSSIA'),('MARUTI'),('MASERATI'),('MAYBACH'),('MAZDA'),('MCLAREN'),('MEGA'),('MERCEDES'),
  ('MERCURY'),('METROCAB'),('MG'),('MINELLI'),('MINI'),('MITSUBISHI'),('MITSUOKA'),('MONTE CARLO'),('MORGAN'),('МОСКВИЧ'),('NISSAN'),('NOBLE'),
  ('OLDSMOBILE'),('OPEL'),('PAGANI'),('PANOZ'),('PAYKAN'),('PERODUA'),('PEUGEOT'),('PLYMOUTH'),('PONTIAC'),('PORSCHE'),('PREMIER'),('PROTON'),
  ('PUMA'),('RELIANT'),('RENAULT'),('ROLLS_ROYCE'),('RONART'),('ROVER'),('SAAB'),('SALEEN'),('SAMSUNG'),('SATURN'),('SCION'),('SEAT'),('SKODA'),
  ('SMA'),('SMART'),('SOUEAST'),('SPECTRE'),('SSANGYONG'),('SUBARU'),('SUZUKI'),('TALBOT'),('TATA'),('TATRA'),('TIANMA'),('TOFAS'),('TOYOTA'),
  ('TRABANT'),('TVR'),('FAW'),('FERRARI'),('FIAT'),('FORD'),('FSO'),('FUQI'),('HAFEI'),('HAIMA'),('HINDUSTAN'),('HOLDEN'),('HONDA'),('HUANGHAI'),
  ('HUMMER'),('HYUNDAI'),('CHANA'),('CHANGFENG'),('CHANGHE'),('CHERY'),('CHEVROLET'),('CHRYSLER'),('SHIFENG'),('SHUANGHUAN'),('JAC');


INSERT INTO car (registration_number, capacity, model, latitude, longitude, is_available, id_brand, id_user,photo_path)
VALUES ('3214MA-5', 'CAR', 'Fabia', '147895623', '235987415', 1, 125, 2,'2car.jpg'),
  ('3315HJ-7', 'CAR', 'E46', '147895623', '235987415', 1, 11, 3,'3car.jpg'),
  ('3416YA-7', 'CAR', 'Corola', '147895623', '235987415', 1, 138, 4,'4car.jpg'),
  ('3517OU-7', 'MINIVAN', 'Polo', '147895623', '235987415', 1, 19, 9,'9car.jpg'),
  ('3618PO-7', 'MINIVAN', 'C200', '147895623', '235987415', 1, 88, 10,'10car.jpg'),
  ('3719MA-5', 'MINIBUS', 'Boxer', '147895623', '235987415', 1, 107, 11,'11car.jpg');


INSERT INTO trip (price, `date`, distance, `status`, id_car, departure_address, destination_address)
VALUES ('15.30','2017-01-05 23:59:59', 23.2,'COMPLETED',1, 1, 2),
  ('22.25','2017-02-04 01:02:03', 28.5,'ORDERED',2, 3, 4),
  ('20.82','2017-03-06 04:05:10', 17.9,'ORDERED',3, 5, 6),
  ('13.00','2017-04-07 06:15:00', 13.7,'ORDERED',4, 7, 8),
  ('30.70','2017-05-08 07:45:59', 22.3,'ORDERED',5, 10, 11),
  ('22.00','2017-06-09 08:00:00', 23.2,'ORDERED',6, 2, 1),
  ('25.45','2017-10-04 09:15:59', 28.5,'ORDERED',1, 4, 3),
  ('21.50','2017-10-05 18:58:59', 17.2, 'ORDERED',2, 6, 5),
  ('26.80','2017-10-05 19:00:00', 23.2,'STARTED',3, 11, 10),
  ('18.53','2017-10-05 19:03:59', 28.5, 'ORDERED', 4, 3, 4),
  ('19.49','2017-10-05 19:15:23', 13.5,'ORDERED',5, 7, 8);


INSERT INTO comment (text, id_user, date, id_reviewer,mark) VALUES
  ('Для меня лично важно, что удобно и быстро можно вызвать такси через смартфон, который всегда под рукой. Не нужно звонить диспетчеру, ждать, а потом оплачивать сумасшедшие счета за телефон т к звонил на короткий номер и долго висел на линии. Тут водители приятные и вежливые, все четко с оплатой, с обманом не встречался. Это очень радует! Спасибо вам. ',5,'2017-02-04 01:02:03',2,5),
  ('Пользуюсь этим такси уже год. Все супер, спасибо! Всегда на высоте и очень дешево. Пересадила всех своих знакомых на эту службу:)',6,'2017-02-04 01:02:03',3,4),
  ('Все супер!!!',2,'2017-02-04 01:02:03',6,5),
  ('Ужасный сервис, привязал карту, списали рубль и не вернули. Приложение тугое, не грузит карту.',6,'2017-02-04 01:02:03',2,1);