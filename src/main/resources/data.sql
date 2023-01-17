INSERT INTO USERS(role, active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES  ('PASSENGER', true, 'NS, 21000', false, 'a@a.ru', 'Alex', '1', '1.png', 'Petrovic', '+7 977 977 27 20'),
        ('PASSENGER', true, 'NS, 21000', false, 'b@a.ru', 'Blex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 22'),
        ('PASSENGER', true, 'NS, 21000', false, 'mirko@gmail.com', 'Mirko', 'mirko123', '1.png', 'Ivanic', '+7 977 977 27 23'),
        ('PASSENGER', true, 'NS, 21000', false, 'g@a.ru', 'Dlex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 24'),
        ('PASSENGER', true, 'NS, 21000', false, 'd@a.ru', 'Elex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 25');

INSERT INTO USERS(id, role, active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES (123, 'PASSENGER', true, 'NS, 21000', false, 'b@b.ru', 'Blex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 21');

INSERT INTO LOCATION (ADDRESS, GEO_LENGTH, GEO_WIDTH)
VALUES ('dr Svetislava Kasapinovica 33, Novi Sad', 54.6, 64.2),
       ('Narodnog Fronta 12, Novi Sad', 54.2, 64.3),
       ('Futoska 12, Novi Sad', 54.4, 64.7),
       ('Strazilovska 24, Novi Sad', 54.4, 64.4),
       ('Hadzi Ruvimova 22, Novi Sad', 54.8, 64.0),
       ('Kisacka 56, Novi Sad', 54.8, 64.0),
       ('Aleksandra Tisme 8 22, Novi Sad', 54.8, 64.0),
       ('Patrijarha Rajacica 16a, Novi Sad', 42.3, 21.1);

INSERT INTO VEHICLE_TYPE
    (PRICE, VEHICLE_NAME)
VALUES (200.00, 0),
       (300.00, 1),
       (400.00, 2);

INSERT INTO VEHICLE
(BABY_PROOF, MODEL, NUM_SEATS, PETS_ALLOWED, REG_PLATES, VEHICLE_NAME, LOCATION_ID, AVAILABLE)
VALUES (true, 'Tesla', 4, true, '1FK-3DS', 0, 1, true),
       (false, 'Audi', 4, true, '2FK-3DS', 2, 2,  true),
       (true, 'Reno', 4, true, '3FK-3DS', 2, 3, true),
       (true, 'Malinovaja Lada', 2, true, '4FK-3DS', 1, 4, true),
       (false, 'Belarus', 1, true, '5FK-3DS',2, 5, true),
       (false, 'Citroen C4', 1, true, '5FK-3DS',2, 6, true),
       (false, 'BMW X1', 1, true, '5FK-3DS',2, 7, true),
       (false, 'BMW X2', 1, true, '5FK-3DS',2, 8, true);

INSERT INTO USERS
(ROLE, ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
VALUES ('DRIVER', true, 'NS, 21000', false, 'dejan@gmail.com', 'Dejan', 'dejan123', '1.png', 'Stankovic', '+8 977 977 27 20', 1),
       ('DRIVER', true, 'NS, 21000 2', false, 'b@t.ru', 'Blex', 'kekekekekek', '2.png', 'Petrovicс', '+8 977 977 27 21', 2),
       ('DRIVER', true, 'NS, 21000 3', true, 'c@t.ru', 'Clex', 'kekekekekek', '3.png', 'Petrovicсс', '+8 977 977 27 22', 3),
       ('DRIVER', true, 'NS, 21000 4', false, 'd@t.ru', 'Dlex', 'kekekekekek', '3.png', 'Petroviccсс', '+8 977 977 27 23', 4),
       ('DRIVER', true, 'NS, 21000 5', false, 'fr@t.ru', 'Elex', 'kekekekekek', '3.png', 'Petroviccсс', '+8 977 977 27 5', 5);

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
VALUES  ('2022-10-10 10:21:20','2022-10-10 17:21:20', 6),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 7),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 8),
        ('2022-10-10 10:21:20','2022-10-10 17:21:20', 9),
        ('2022-11-10 10:21:20','2022-10-10 17:21:20', 6);
--
--INSERT INTO USERS
--(active, address, blocked, email, name, password, img_path, surname, phone)
--VALUES (true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20');
--
--
INSERT INTO REJECTION (REASON, REJECTION_TIME, USER_ID)
VALUES ('Headache', '2022-11-11 10:32:01', 1);


INSERT INTO RIDE
(BABIES, END_TIME, ESTIMATED_TIME_IN_MINUTES, PANIC, PETS, START_TIME, STATUS, TOTAL_COST, DRIVER_ID, REJECTION_ID,
 VEHICLE_TYPE)
VALUES  (true, '2023-01-04 04:33:20', 10.2, false, false, '2023-01-04 04:33:20', 4, 500, 6, 1, 2),
        (false, '2022-12-29 11:21:20', 14.2, false, false, '2022-12-28 10:15:01', 4, 600, 7, null, 2),
        (true, '2022-12-29 15:21:20', 20.2, false, false, '2022-12-29 08:01:01', 4, 700, 10, null, 2),
        (true, '2022-12-29 15:21:20', 12.2, false, true, '2022-12-29 08:11:01', 1, 550, 8, null, 2),
        (true, '2022-12-29 15:21:20', 15.2, false, false, '2022-12-29 08:06:01', 4, 600, 9, null, 2);


INSERT INTO ROUTE (KILOMETERS, END_LOCATION_ID, START_LOCATION_ID, RIDE_ID)
values  (2.5, 1, 2, 1),
        (2.5, 1, 3, 2),
        (2.5, 2, 4, 3),
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


INSERT INTO FAVOURITE_ROUTE(BABIES, FAVOURITE_NAME, PETS, SCHEDULED_TIME, VEHICLE_TYPE)
VALUES (false, 'kuca-poso', false, '2022-12-29 15:21:20', 1);


INSERT INTO FAVOURITE_ROUTE_ROUTE(ROUTE_ID, FAVOURITE_ROUTE_ID)
VALUES (1, 1);


INSERT INTO FAVOURITE_ROUTE_PASSENGER(PASSENGER_ID, FAVOURITE_ROUTE_ID)
VALUES (1, 1);


INSERT INTO REVIEW (COMMENT, GRADE, PASSENGER_ID, RIDE_ID, VEHICLE_ID)
VALUES  ('dobar', 3, 1, 1, 1),
        ('odlican', 5, 2, 2, 2),
        ('odlican', 5, 3, 3, 1),
        ('odlican', 5, 4, 4, 2),
        ('odlican', 5, 5, 5, 3),
        ('odlican', 5, 3, 2, 4);


INSERT INTO USERS (ROLE, PROFILE_PICTURE, NAME, PASSWORD, SURNAME, EMAIL, ACTIVE, BLOCKED, telephone_number)
VALUES  ('ADMIN', '1.jpg', 'Novak', 'novak123', 'Djokovic', 'nole', TRUE, FALSE, '+7 1'),
        ('ADMIN', '2.jpg', 'Zvezdan', 'zvezdan123', 'Terzic', 'terza', TRUE, FALSE, '+8 1');

INSERT INTO DRIVER_REQUEST(NEW_ADDRESS, NEW_BABY_PROOF, NEW_EMAIL, NEW_MODEL, NEW_NAME, NEW_NUM_SEATS, NEW_PETS_ALLOWED, NEW_PROFILE_PICTURE, NEW_REG_PLATES, NEW_SURNAME, NEW_TELEPHONE_NUMBER, NEW_VEHICLE_NAME, STATUS, DRIVER_ID, VEHICLE_ID)
VALUES ('nova adresa', true, 'mejl@gmail.com', 'bmw', 'Marko', 4, true, 'slika.jpg', 'SFD-4S', 'Markovic', '034952398', 2, 1, 9, 4);

INSERT INTO MESSAGE (RIDE_ID, TEXT, SEND_TIME, TYPE, RECEIVER_ID, SENDER_ID)
VALUES  (1, 'Privet', '2022-10-10 10:21:20', 1, 1, 2),
        (1, 'Privet', '2022-10-10 10:21:20', 1, 2, 3),
        (2, 'Privet', '2022-10-10 10:21:20', 1, 3, 4),
        (2, 'Privet', '2022-10-10 10:21:20', 1, 4, 5),
        (3, 'Privet', '2022-10-10 10:21:20', 1, 1, 6),
        (3, 'Privet', '2022-10-10 10:21:20', 1, 6, 7),
        (3, 'Privet', '2022-10-10 10:21:20', 1, 7, 8),
        (4, 'Privet', '2022-10-10 10:21:20', 1, 8, 9),
        (4, 'Privet', '2022-10-10 10:21:20', 1, 9, 1),
        (4, 'Privet', '2022-10-10 10:21:20', 1, 3, 2),
        (4, 'Privet', '2022-10-10 10:21:20', 1, 1, 3);

INSERT INTO PANIC (REASON, PANIC_TIME, RIDE_ID, USER_ID)
VALUES  ('Danger!', '2022-10-10 10:21:20', 1, 1),
        ('Danger2!', '2022-10-10 10:21:20', 2, 6);

INSERT INTO REMARK (MESSAGE, USER_ID)
VALUES ('BAD TIMING',7);

