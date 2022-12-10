-- noinspection SqlWithoutWhereForFile

DELETE FROM PARTICIPATION;
DELETE FROM ROUTE;
DELETE FROM RIDE;
DELETE FROM DRIVER;
DELETE FROM VEHICLE;
DELETE FROM VEHICLE_TYPE;
DELETE FROM LOCATION;
-- DELETE FROM PASSENGER_ACTIVATION;
DELETE FROM PASSENGER;
DELETE FROM REJECTION;
DELETE FROM USERS;
INSERT INTO PASSENGER
    (
        id,
        active,
        address,
        blocked,
        email,
        name,
        password,
        profile_picture,
        surname,
        telephone_number
    ) VALUES
        ( 5423, true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20' ),
        ( 5251, true, 'NS, 21000 2', false, 'b@a.ru', 'Blex', 'kekekekekek', '2.png', 'Petrovicс', '+7 977 977 27 21' ),
        ( 5342, true, 'NS, 21000 3', true, 'c@a.ru', 'Clex', 'kekekekekek', '3.png', 'Petrovicсс', '+7 977 977 27 22' ),
        ( 1555, true, 'NS, 21000 4', false, 'd@a.ru', 'Dlex', 'kekekekekek', '3.png', 'Petroviccсс', '+7 977 977 27 23' ),
        ( 9322, true, 'NS, 21000', false, 'e@y.ru', 'Elex', 'kekekekekek', '4.png', 'Petrovicсcсс', '+7 977 977 27 24' );

-- INSERT INTO PASSENGER_ACTIVATION(ACTIVATION_ID, CREATION_DATE, LIFE_LENGTH, PASSENGER_ID
--     ) VALUES
--        (1111, '2022-11-11 10:32:01', 2.1, 1555);

INSERT INTO LOCATION (ID, ADDRESS, GEO_LENGTH, GEO_WIDTH)
    VALUES ( 1, 'NS 1', 54.6, 64.2 ),
        ( 2, 'NS 2', 54.2, 64.3 ),
        ( 3, 'NS 3', 54.4, 64.7 ),
        ( 4, 'NS 4', 54.4, 64.4 ),
        ( 5, 'NS 5', 54.8, 64.0 );


INSERT INTO VEHICLE_TYPE
    (ID, PRICE, VEHICLE_NAME) VALUES ( 1, 200.00,  1), ( 2, 299.99,  2);


--INSERT INTO DRIVER_DOCUMENT (ID, DOCUMENT_IMAGE, NAME, DRIVER_ID)
--    VALUES ( 1, '1.png', 'driver license', 1 ),
--        ( 2, '2.png', 'driver license', 2 ),
--        ( 3, '3.png', 'driver license', 3 ),
--        ( 4, '3.png', 'driver license', 4 ),
--        ( 5, '4.png', 'driver license', 5 );


INSERT INTO VEHICLE
    (ID, BABY_PROOF, MODEL, NUM_SEATS, PETS_ALLOWED, REG_PLATES, VEHICLE_NAME, LOCATION_ID) VALUES
    ( 1, true, 'Tesla', 4, true,  'Sta?', 1, 1),
    ( 2, false, 'Audi', 4, true,  'Sta??', 2, 2),
    ( 3, true, 'Reno', 4, true,  'Sta???', 2, 3),
    ( 4, true, 'Malinovaja Lada', 2, true,  'Sta????', 1, 4),
    ( 5, false, 'Belarus', 1, true,  'Sta?????', 2, 5);


INSERT INTO DRIVER
    (
        ID,
        ACTIVE,
        ADDRESS,
        BLOCKED,
        EMAIL,
        NAME,
        PASSWORD,
        PROFILE_PICTURE,
        SURNAME,
        TELEPHONE_NUMBER,
        VEHICLE_ID
    ) VALUES
        ( 1, true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20', 1 ),
        ( 2, true, 'NS, 21000 2', false, 'b@a.ru', 'Blex', 'kekekekekek', '2.png', 'Petrovicс', '+7 977 977 27 21', 2 ),
        ( 3, true, 'NS, 21000 3', true, 'c@a.ru', 'Clex', 'kekekekekek', '3.png', 'Petrovicсс', '+7 977 977 27 22', 3 ),
        ( 4, true, 'NS, 21000 4', false, 'd@a.ru', 'Dlex', 'kekekekekek', '3.png', 'Petroviccсс', '+7 977 977 27 23', 4 ),
        ( 5, true, 'NS, 21000', false, 'e@y.ru', 'Elex', 'kekekekekek', '4.png', 'Petrovicсcсс', '+7 977 977 27 24', 5 );




INSERT INTO USERS
    (
        id,
        active,
        address,
        blocked,
        email,
        name,
        password,
        img_path,
        surname,
        phone
    ) VALUES
        ( 1, true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20' );


INSERT INTO REJECTION (ID, REASON, TIME, USER_ID
    ) VALUES
    (1, 'Headache', '2022-11-11 10:32:01', 1);

--
-- INSERT INTO RIDE
--     (ID, BABIES, END_TIME, ESTIMATED_TIME_IN_MINUTES, PANIC, PETS, START_TIME, STATUS, TOTAL_COST, DRIVER_ID, REJECTION_ID, VEHICLE_TYPE
--     ) VALUES
--         (7, true, '2022-10-10 10:21:20', 10.2, false, false, '2022-10-10 10:01:01', 0, 500, 3,1,2);
--
-- INSERT INTO PARTICIPATION(RIDE_ID, PASSENGER_ID) VALUES ( 7, 5423);
--
-- INSERT INTO ROUTE (ID, KILOMETERS, END_LOCATION_ID, START_LOCATION_ID, PASSENGER_ID, RIDE_ID) values (1, 2.5, 1, 2, 5423, 7);
--
