INSERT INTO PASSENGER(active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES  (true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20'),
        (true, 'NS, 21000', false, 'b@a.ru', 'Blex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 22'),
        (true, 'NS, 21000', false, 'mirko@gmail.com', 'Mirko', 'mirko123', '1.png', 'Ivanic', '+7 977 977 27 23'),
        (true, 'NS, 21000', false, 'g@a.ru', 'Dlex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 24'),
        (true, 'NS, 21000', false, 'd@a.ru', 'Elex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 25');

INSERT INTO PASSENGER(id, active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES (123, true, 'NS, 21000', false, 'b@b.ru', 'Blex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 21');

INSERT INTO PASSENGER_ACTIVATION (CREATION_DATE, LIFE_LENGTH, PASSENGER_ID)
VALUES  ('2022-10-10 10:21:20', 1, 1),
        ('2022-10-10 10:21:21', 1, 2),
        ('2022-10-10 10:21:22', 1, 3),
        ('2022-10-10 10:21:23', 1, 4),
        ('2022-10-10 10:21:24', 1, 5);

INSERT INTO LOCATION (ADDRESS, GEO_LENGTH, GEO_WIDTH)
VALUES ('NS 1', 54.6, 64.2),
       ('NS 2', 54.2, 64.3),
       ('NS 3', 54.4, 64.7),
       ('NS 4', 54.4, 64.4),
       ('NS 5', 54.8, 64.0);

INSERT INTO VEHICLE_TYPE
    (PRICE, VEHICLE_NAME)
VALUES (200.00, 1),
       (299.99, 2),
       (399.99, 3);

INSERT INTO VEHICLE
(BABY_PROOF, MODEL, NUM_SEATS, PETS_ALLOWED, REG_PLATES, VEHICLE_NAME, LOCATION_ID)
VALUES (true, 'Tesla', 4, true, '1FK-3DS', 1, 1),
       (false, 'Audi', 4, true, '2FK-3DS', 2, 2),
       (true, 'Reno', 4, true, '3FK-3DS', 2, 3),
       (true, 'Malinovaja Lada', 2, true, '4FK-3DS', 1, 4),
       (false, 'Belarus', 1, true, '5FK-3DS', 3, 5);

INSERT INTO DRIVER
(ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
VALUES (true, 'NS, 21000', false, 'dejan@gmail.com', 'Dejan', 'dejan123', '1.png', 'Stankovic', '+7 977 977 27 20', 1),
       (true, 'NS, 21000 2', false, 'b@a.ru', 'Blex', 'kekekekekek', '2.png', 'Petrovicс', '+7 977 977 27 21', 2),
       (true, 'NS, 21000 3', true, 'c@a.ru', 'Clex', 'kekekekekek', '3.png', 'Petrovicсс', '+7 977 977 27 22', 3),
       (true, 'NS, 21000 4', false, 'd@a.ru', 'Dlex', 'kekekekekek', '3.png', 'Petroviccсс', '+7 977 977 27 23', 4);

INSERT INTO DRIVER
(ID, ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
VALUES (123, true, 'NS, 21000', false, 'f@y.ru', 'Flex', 'kekekekekek', '4.png', 'Petrovicсcсс', '+7 977 977 27 25', 5);

INSERT INTO DRIVER_DOCUMENT (DOCUMENT_IMAGE, NAME, DRIVER_ID)
VALUES  ('1.jpg', 'driver licence', 1),
        ('2.jpg', 'driver licence', 2),
        ('3.jpg', 'driver licence', 3),
        ('4.jpg', 'driver licence', 4),
        ('5.jpg', 'driver licence', 123);

INSERT INTO WORKING_HOURS(START_TIME, END_TIME, DRIVER_ID)
VALUES  ('2022-10-10 10:21:20','2022-10-10 17:21:20', 1),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 2),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 3),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 4),
        ('2022-11-10 10:21:20','2022-10-10 17:21:20', 1);

INSERT INTO USERS
(active, address, blocked, email, name, password, img_path, surname, phone)
VALUES (true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20');


INSERT INTO REJECTION (REASON, TIME, USER_ID)
VALUES ('Headache', '2022-11-11 10:32:01', 1);


INSERT INTO RIDE
(BABIES, END_TIME, ESTIMATED_TIME_IN_MINUTES, PANIC, PETS, START_TIME, STATUS, TOTAL_COST, DRIVER_ID, REJECTION_ID,
 VEHICLE_TYPE)
VALUES  (true, '2022-10-10 10:21:20', 10.2, false, false, '2022-10-10 10:11:01', 0, 500, 1, 1, 2),
        (false, '2022-10-10 11:21:20', 14.2, false, false, '2022-10-10 10:15:01', 0, 600, 2, null, 2),
        (true, '2022-10-10 12:21:20', 20.2, false, false, '2022-10-10 10:01:01', 0, 700, 1, null, 2),
        (true, '2022-10-10 13:21:20', 12.2, false, true, '2022-10-10 10:11:01', 0, 550, 3, null, 2),
        (true, '2022-10-10 14:21:20', 15.2, false, false, '2022-10-10 10:06:01', 0, 600, 4, null, 2);

INSERT INTO ROUTE (KILOMETERS, END_LOCATION_ID, START_LOCATION_ID, RIDE_ID)
values  (2.5, 1, 2, 1),
        (2.5, 1, 3, 2),
        (2.5, 1, 4, 3),
        (2.5, 1, 5, 4),
        (2.5, 2, 1, 5);


INSERT INTO PARTICIPATION(RIDE_ID, PASSENGER_ID)
VALUES  (1, 1),
        (2, 2),
        (3, 3),
        (4, 4),
        (5, 5),
        (3, 2),
        (4, 3);


INSERT INTO REVIEW (COMMENT, GRADE, PASSENGER_ID, RIDE_ID, VEHICLE_ID)
VALUES  ('dobar', 3, 1, 1, 1),
        ('odlican', 5, 2, 2, 2),
        ('odlican', 5, 3, 3, 1),
        ('odlican', 5, 4, 4, 2),
        ('odlican', 5, 5, 5, 3),
        ('odlican', 5, 3, 2, 4);


INSERT INTO ADMIN (IMG_PATH, NAME, PASSWORD, SURNAME, USERNAME)
VALUES  ('1.jpg', 'Novak', 'novak123', 'Djokovic', 'nole'),
        ('2.jpg', 'Zvezdan', 'zvezdan123', 'Terzic', 'terza');

INSERT INTO DRIVER_REQUEST(NEW_ADDRESS, NEW_BABY_PROOF, NEW_EMAIL, NEW_MODEL, NEW_NAME, NEW_NUM_SEATS, NEW_PETS_ALLOWED, NEW_PROFILE_PICTURE, NEW_REG_PLATES, NEW_SURNAME, NEW_TELEPHONE_NUMBER, NEW_VEHICLE_NAME, STATUS, DRIVER_ID, VEHICLE_ID)
VALUES ('nova adresa', true, 'mejl@gmail.com', 'bmw', 'Marko', 4, true, 'slika.jpg', 'SFD-4S', 'Markovic', '034952398', 2, 1, 4, 4)
