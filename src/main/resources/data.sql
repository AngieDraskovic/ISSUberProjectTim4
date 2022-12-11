INSERT INTO PASSENGER(active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES (true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20');

INSERT INTO PASSENGER(id, active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES (123, true, 'NS, 21000', false, 'b@b.ru', 'Blex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 21');

INSERT INTO PASSENGER_ACTIVATION (CREATION_DATE, LIFE_LENGTH, PASSENGER_ID)
VALUES ('2022-10-10 10:21:20', 1, 1);

INSERT INTO LOCATION (ADDRESS, GEO_LENGTH, GEO_WIDTH)
VALUES ('NS 1', 54.6, 64.2),
       ('NS 2', 54.2, 64.3),
       ('NS 3', 54.4, 64.7),
       ('NS 4', 54.4, 64.4),
       ('NS 5', 54.8, 64.0);

INSERT INTO VEHICLE_TYPE
    (PRICE, VEHICLE_NAME)
VALUES (200.00, 1),
       (299.99, 2);

INSERT INTO VEHICLE
(BABY_PROOF, MODEL, NUM_SEATS, PETS_ALLOWED, REG_PLATES, VEHICLE_NAME, LOCATION_ID)
VALUES (true, 'Tesla', 4, true, 'Sta?', 1, 1),
       (false, 'Audi', 4, true, 'Sta??', 2, 2),
       (true, 'Reno', 4, true, 'Sta???', 2, 3),
       (true, 'Malinovaja Lada', 2, true, 'Sta????', 1, 4),
       (false, 'Belarus', 1, true, 'Sta?????', 2, 5);

INSERT INTO DRIVER
(ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
VALUES (true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20', 1),
       (true, 'NS, 21000 2', false, 'b@a.ru', 'Blex', 'kekekekekek', '2.png', 'Petrovicс', '+7 977 977 27 21', 2),
       (true, 'NS, 21000 3', true, 'c@a.ru', 'Clex', 'kekekekekek', '3.png', 'Petrovicсс', '+7 977 977 27 22', 3),
       (true, 'NS, 21000 4', false, 'd@a.ru', 'Dlex', 'kekekekekek', '3.png', 'Petroviccсс', '+7 977 977 27 23', 4),
       (true, 'NS, 21000', false, 'e@y.ru', 'Elex', 'kekekekekek', '4.png', 'Petrovicсcсс', '+7 977 977 27 24', 5);

INSERT INTO DRIVER
(ID, ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
VALUES (123, true, 'NS, 21000', false, 'f@y.ru', 'Flex', 'kekekekekek', '4.png', 'Petrovicсcсс', '+7 977 977 27 25', 5);

INSERT INTO USERS
(active, address, blocked, email, name, password, img_path, surname, phone)
VALUES (true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20');


INSERT INTO REJECTION (REASON, TIME, USER_ID)
VALUES ('Headache', '2022-11-11 10:32:01', 1);


INSERT INTO RIDE
(BABIES, END_TIME, ESTIMATED_TIME_IN_MINUTES, PANIC, PETS, START_TIME, STATUS, TOTAL_COST, DRIVER_ID, REJECTION_ID,
 VEHICLE_TYPE)
VALUES (true, '2022-10-10 10:21:20', 10.2, false, false, '2022-10-10 10:01:01', 0, 500, 1, 1, 2);

INSERT INTO PARTICIPATION(RIDE_ID, PASSENGER_ID)
VALUES (1, 1);

INSERT INTO ROUTE (KILOMETERS, END_LOCATION_ID, START_LOCATION_ID, RIDE_ID)
values (2.5, 1, 2, 1);