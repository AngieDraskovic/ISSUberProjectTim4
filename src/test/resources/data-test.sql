INSERT INTO USERS(id, role, active, address, blocked, email, name, password, profile_picture, surname, telephone_number)
VALUES  (111, 'PASSENGER', true, 'NS, 21000', false, 'passenger-test@gmail.com', 'Alex', 'alex123', 'alex.jpg', 'Mishutkin', '+7 977 977 '),
        (400, 'PASSENGER', true, 'NS, 21000', false, 'p-test@gmail.com', 'Ana', 'ana123', 'ana.jpg', 'Draskovic', '+7 977 977 27');

INSERT INTO LOCATION (ID, ADDRESS, GEO_LENGTH, GEO_WIDTH)
VALUES (111, 'dr Svetislava Kasapinovica 33, Novi Sad', 54.6, 64.2),
       (222, 'Narodnog Fronta 12, Novi Sad', 74.2, 84.3),
       (454,'Narodnog Fronta 13, Novi Sad', 45.241902, 19.841965),
       (234, 'Futoska 12, Novi Sad', 45.248779, 19.816070);

INSERT INTO VEHICLE_TYPE
    (PRICE, VEHICLE_NAME)
VALUES (200.00, 0);

INSERT INTO VEHICLE
(ID, BABY_PROOF, MODEL, NUM_SEATS, PETS_ALLOWED, REG_PLATES, VEHICLE_NAME, LOCATION_ID, AVAILABLE)
VALUES (888, true, 'Tesla', 4, true, '1FK-3DS', 1, 111, true),
       (534, false, 'Audi', 4, true, '2FK-3DS', 0, 222,  true);


INSERT INTO USERS
(ID, ROLE, ACTIVE, ADDRESS, BLOCKED, EMAIL, NAME, PASSWORD, PROFILE_PICTURE, SURNAME, TELEPHONE_NUMBER, VEHICLE_ID)
VALUES (222, 'DRIVER', true, 'NS, 21000', false, 'driver-test@gmail.com', 'Dejan', 'dejan123', 'dejan.jpg', 'Stankovic', '+8 977 921', 888),
       (987, 'DRIVER', true, 'NS, 21000 2', false, 'd-test@gmail.com', 'Vladan', 'vladan123', 'vladan.jpg', 'Milojevic', '+8 977 977', 534);



INSERT INTO PANIC (ID, REASON, PANIC_TIME, USER_ID)
VALUES (6767, 'Reason for panicing',  '2023-01-24 04:33:20', 111);


INSERT INTO FAVOURITE_ROUTE(ID, BABIES, FAVOURITE_NAME, PETS, SCHEDULED_TIME, VEHICLE_TYPE, KILOMETERS, ESTIMATED_TIME_IN_MINUTES)
VALUES (453, false, 'kuca-posao', false, '2022-12-29 15:21:20', 1, 1.5, 3);


INSERT INTO RIDE
(ID, BABIES, END_TIME, ESTIMATED_TIME_IN_MINUTES, KILOMETERS, PETS, START_TIME, STATUS, TOTAL_COST, DRIVER_ID, REJECTION_ID,
 VEHICLE_TYPE, PANIC_ID)
VALUES  (333, true, '2023-01-24 04:33:20', 102.2, 0.9, false, '2023-01-24 16:53:20', 0, 500, 222, null, 1, null),
        (1234, false, '2023-01-10 11:21:20', 142.2, 1.2, false, '2023-01-07 12:53:20', 1, 600, 987, null, 1, null),
        (1235, true, '2023-01-10 15:21:20', 32.2, 3.1, true, '2023-01-07 10:53:20', 3, 550, 987, null, 1, 6767);



INSERT INTO ROUTE (ID, KILOMETERS, END_LOCATION_ID, START_LOCATION_ID, RIDE_ID)
values  (243, 2.5, 111, 222, 333),
        (213, 3.5, 222, 111, 1234),
        (244, 2.5, 222, 111, 1235);



INSERT INTO PARTICIPATION(RIDE_ID, PASSENGER_ID)
VALUES  (333, 111),
        (1234, 400),
        (1235, 400);



INSERT INTO FAVOURITE_ROUTE_ROUTE(ROUTE_ID, FAVOURITE_ROUTE_ID)
VALUES (243, 453);

INSERT INTO FAVOURITE_ROUTE_PASSENGER(PASSENGER_ID, FAVOURITE_ROUTE_ID)
VALUES (111, 453);