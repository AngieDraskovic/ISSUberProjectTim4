INSERT INTO USERS(role, active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES  ('PASSENGER', true, 'NS, 21000', false, 'alex@gmail.com', 'Alex', 'alex123', 'alex.jpg', 'Mishutkin', '+7 977 977 27 20'),
        ('PASSENGER', true, 'NS, 21000', false, 'ana@gmail.com', 'Ana', 'ana123', 'ana.jpg', 'Draskovic', '+7 977 977 27 22'),
        ('PASSENGER', true, 'NS, 21000', false, 'mirko@gmail.com', 'Mirko', 'mirko123', 'mirko.jpg', 'Ivanic', '+7 977 977 27 23'),
        ('PASSENGER', true, 'NS, 21000', false, 'nemanja@gmail.com', 'Nemanja', 'nemanja123', 'nemanja.jpg', 'Stojcevic', '+7 977 977 27 24'),
        ('PASSENGER', true, 'NS, 21000', false, 'sale@gmail.com', 'Sale', 'sale123', 'sale.jpg', 'Katai', '+7 977 977 27 25');

INSERT INTO USERS(id, role, active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES (123, 'PASSENGER', true, 'NS, 21000', false, 'strahinja@gmail.com', 'Strahinja', 'strahinja123', 'strahinja.jpg', 'Erakovic', '+7 977 977 27 21');

INSERT INTO LOCATION (ADDRESS, GEO_WIDTH, GEO_LENGTH)
VALUES ('dr Svetislava Kasapinovica 33, Novi Sad', 45.259728, 19.813830),
       ('dr Svetislava Kasapinovica 55, Novi Sad', 45.264812, 19.849088),
       ('Narodnog Fronta 12, Novi Sad', 45.241902, 19.841965),
       ('Futoska 12, Novi Sad', 45.248779, 19.816070),
       ('Strazilovska 24, Novi Sad', 45.248601, 19.848709),
       ('Hadzi Ruvimova 22, Novi Sad', 45.257372, 19.816070),
       ('Kisacka 56, Novi Sad', 45.265093, 19.836606),
       ('Aleksandra Tisme 8, Novi Sad', 45.258883, 19.849630),
       ('Patrijarha Rajacica 16a, Novi Sad', 45.254684, 19.873344);

INSERT INTO VEHICLE_TYPE
    (PRICE, VEHICLE_NAME)
VALUES (200.00, 0),
       (300.00, 1),
       (400.00, 2);

INSERT INTO VEHICLE
(BABY_PROOF, MODEL, NUM_SEATS, PETS_ALLOWED, REG_PLATES, VEHICLE_NAME, LOCATION_ID, AVAILABLE)
VALUES (true, 'Tesla', 4, true, '1FK-3DS', 1, 1, true),
       (false, 'Audi', 4, true, '2FK-3DS', 0, 2,  true),
       (true, 'Reno', 4, true, '3FK-3DS', 0, 3, true),
       (true, 'Malinovaja Lada', 2, true, '4FK-3DS', 0, 4, false),
       (false, 'Belarus', 1, true, '5FK-3DS',0, 5, true),
       (false, 'Citroen C4', 1, true, '5FK-3DS',0, 6, true),
       (false, 'BMW X1', 1, true, '5FK-3DS',0, 7, true),
       (false, 'BMW X2', 1, true, '5FK-3DS',0, 8, true);

INSERT INTO USERS
(ROLE, ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
VALUES ('DRIVER', true, 'NS, 21000', false, 'dejan@gmail.com', 'Dejan', 'dejan123', 'dejan.jpg', 'Stankovic', '+8 977 977 27 20', 1),
       ('DRIVER', true, 'NS, 21000 2', false, 'vladan@gmail.com', 'Vladan', 'vladan123', 'vladan.jpg', 'Milojevic', '+8 977 977 27 21', 2),
       ('DRIVER', true, 'NS, 21000 3', true, 'milos@gmail.com', 'Milos', 'milos123', 'milos.jpg', 'Milojevic', '+8 977 977 27 22', 3),
       ('DRIVER', true, 'NS, 21000 4', false, 'aleksandar@gmail.com', 'Aleksandar', 'aleksandar123', 'aleksandar.jpg', 'Jakovljevic', '+8 977 977 27 23', 4),
       ('DRIVER', true, 'NS, 21000 5', false, 'jelena@gmail.com', 'Jelena', 'jelena123', 'jelena.jpg', 'Jakovljevic', '+8 977 977 27 5', 5);

-- NOT INSERT DRIVER WITH ID 123 BACAUSE WE ALREADY HAVE PASENGER WITH ID 123, ALL USERS ARE IN THE SAME TABLE USERS

--
--INSERT INTO USERS
--(ID, ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
--VALUES (123, true, 'NS, 21000', false, 'f@y.ru', 'Flex', 'kekekekekek', '4.png', 'Petrovicсcсс', '+7 977 977 27 25', 5);
--

INSERT INTO DRIVER_DOCUMENT (DOCUMENT_IMAGE, NAME, DRIVER_ID)
VALUES  ('1.jpg', 'DRIVER licence', 6),
        ('2.jpg', 'DRIVER licence', 7),
        ('3.jpg', 'DRIVER licence', 8),
        ('4.jpg', 'DRIVER licence', 9);


INSERT INTO WORKING_HOURS(START_TIME, END_TIME, DRIVER_ID)
VALUES  ('2023-01-17 00:21:20','2023-01-17 01:24:20', 6),
        ('2022-10-10 09:21:20','2022-10-10 16:21:20', 7),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 8),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 9),
        ('2023-01-17 02:21:20', '2023-01-17 03:21:20', 6);
--
--INSERT INTO USERS
--(active, address, blocked, email, name, password, img_path, surname, phone)
--VALUES (true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20');
--
--
INSERT INTO REJECTION (REASON, REJECTION_TIME, USER_ID)
VALUES ('Headache', '2022-11-11 10:32:01', 1);

INSERT INTO PANIC (REASON, PANIC_TIME, USER_ID)
VALUES  ('Danger!', '2022-10-10 10:21:20', 1),
        ('Danger2!', '2022-10-10 10:21:20', 6);

INSERT INTO FAVOURITE_ROUTE(BABIES, FAVOURITE_NAME, PETS, SCHEDULED_TIME, VEHICLE_TYPE, KILOMETERS, ESTIMATED_TIME_IN_MINUTES)
VALUES (false, 'kuca-poso', false, '2022-12-29 15:21:20', 1, 1.5, 30);

INSERT INTO RIDE
(BABIES, END_TIME, ESTIMATED_TIME_IN_MINUTES, KILOMETERS, PETS, START_TIME, STATUS, TOTAL_COST, DRIVER_ID, REJECTION_ID,
 VEHICLE_TYPE, PANIC_ID)
VALUES  (true, '2023-01-22 04:33:20', 102.2, 0.9, false, '2023-01-24 16:53:20', 4, 500, 6, 1, 2, null),
        (false, '2023-01-22 11:21:20', 142.2, 1.2, false, '2023-01-22 12:53:20', 4, 600, 6, null, 2, null),
        (true, '2023-01-23 15:21:20', 32.2, 3.1, true, '2023-01-23 10:53:20', 4, 550, 6, null, 2, null),
        (true, '2023-01-27 15:21:20', 32.2, 1.1, false, '2023-01-27 11:53:20', 4, 700, 6, null, 2, null),
        (true, '2023-01-27 15:21:20', 168.2, 2.3, false, '2023-01-27 12:53:20', 4, 600, 6, null, 2, null);



INSERT INTO ROUTE (KILOMETERS, END_LOCATION_ID, START_LOCATION_ID, RIDE_ID)
values  (2.5, 1, 2, 1),
        (2.5, 1, 3, 2),
        (2.5, 2, 4, 3),
        (2.5, 1, 5, 4),
        (2.5, 2, 1, 5);


INSERT INTO PARTICIPATION(RIDE_ID, PASSENGER_ID)
VALUES  (1, 3),
        (2, 3),
        (3, 3),
        (4, 4),
        (5, 5),
        (3, 2),
        (4, 3);




INSERT INTO FAVOURITE_ROUTE_ROUTE(ROUTE_ID, FAVOURITE_ROUTE_ID)
VALUES (1, 1);


INSERT INTO FAVOURITE_ROUTE_PASSENGER(PASSENGER_ID, FAVOURITE_ROUTE_ID)
VALUES (3, 1);


INSERT INTO REVIEW (COMMENT, DRIVER_GRADE, VEHICLE_GRADE, PASSENGER_ID, RIDE_ID, VEHICLE_ID)
VALUES  ('dobar', 3, 3, 1, 1, 1),
        ('odlican', 5, 5, 2, 2, 2),
        ('odlican', 5, 5, 3, 3, 1),
        ('odlican', 5, 5, 4, 4, 2),
        ('odlican', 5, 5, 5, 5, 3),
        ('odlican', 5, 5, 3, 2, 4);


INSERT INTO USERS (ROLE, PROFILE_PICTURE, NAME, PASSWORD, SURNAME, EMAIL, ACTIVE, BLOCKED, telephone_number)
VALUES  ('ADMIN', 'novak.jpg', 'Novak', 'novak123', 'Djokovic', 'novak@gmail.com', TRUE, FALSE, '+7 1');


INSERT INTO DRIVER_REQUEST(NEW_ADDRESS, NEW_BABY_PROOF, NEW_EMAIL, NEW_MODEL, NEW_NAME, NEW_NUM_SEATS, NEW_PETS_ALLOWED, NEW_PROFILE_PICTURE, NEW_REG_PLATES, NEW_SURNAME, NEW_TELEPHONE_NUMBER, NEW_VEHICLE_NAME, STATUS, DRIVER_ID, VEHICLE_ID)
VALUES ('nova adresa', true, 'mejl@gmail.com', 'bmw', 'Marko', 4, true, 'slika.jpg', 'SFD-4S', 'Markovic', '034952398', 2, 1, 9, 4);

INSERT INTO MESSAGE (RIDE_ID, TEXT, SEND_TIME, TYPE, RECEIVER_ID, SENDER_ID)
VALUES  (1, 'Hoćeš li doći već jednom?!?', '2022-10-10 10:21:20', 1, 9, 2),
        (1, 'Vidiš li međeda?', '2022-10-10 10:21:20', 1, 2, 3),
        (1, 'A kako si ga dao Ludogorecu ne vjerujem', '2022-10-10 10:21:20', 1, 3, 4),
        (2, 'Posmatram sve vrijeme, ali ne vidim ga.', '2022-10-10 10:21:20', 1, 3, 2),
        (3, 'Prijavio sam te za lazne dojave o međedu', '2022-10-10 10:21:20', 1, 1, 6),
        (3, 'Eno ga!', '2022-10-10 10:21:20', 1, 2, 3),
        (3, 'Kolega da li je sve u redu?', '2022-10-10 10:21:20', 1, 7, 8),
        (4, 'Patriče majstoree', '2022-10-10 10:21:20', 1, 8, 9),
        (4, 'Gdje?', '2022-10-10 10:21:20', 1, 3, 2),
        (4, 'Aa vidim. Šta ćemo sad?', '2022-10-10 10:21:20', 1, 3, 2),
        (4, 'Pravi se mrtva.', '2022-10-10 10:21:20', 1, 2, 3);

INSERT INTO REMARK (MESSAGE, USER_ID)
VALUES ('BAD TIMING',7);
