DELETE FROM DRIVER WHERE ID < 6;
DELETE FROM VEHICLE WHERE ID < 6;
DELETE FROM VEHICLE_TYPE WHERE ID < 3;
DELETE FROM LOCATION WHERE ID < 6;
DELETE FROM PASSENGER WHERE ID < 6;


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
        ( 1, true, 'NS, 21000', false, 'a@a.ru', 'Alex', 'kekekekekek', '1.png', 'Petrovic', '+7 977 977 27 20' ),
        ( 2, true, 'NS, 21000 2', false, 'b@a.ru', 'Blex', 'kekekekekek', '2.png', 'Petrovicс', '+7 977 977 27 21' ),
        ( 3, true, 'NS, 21000 3', true, 'c@a.ru', 'Clex', 'kekekekekek', '3.png', 'Petrovicсс', '+7 977 977 27 22' ),
        ( 4, true, 'NS, 21000 4', false, 'd@a.ru', 'Dlex', 'kekekekekek', '3.png', 'Petroviccсс', '+7 977 977 27 23' ),
        ( 5, true, 'NS, 21000', false, 'e@y.ru', 'Elex', 'kekekekekek', '4.png', 'Petrovicсcсс', '+7 977 977 27 24' );

INSERT INTO LOCATION (ID, ADDRESS, GEO_LENGTH, GEO_WIDTH)
    VALUES ( 1, 'NS 1', 54.6, 64.2 ),
        ( 2, 'NS 2', 54.2, 64.3 ),
        ( 3, 'NS 3', 54.4, 64.7 ),
        ( 4, 'NS 4', 54.4, 64.4 ),
        ( 5, 'NS 5', 54.8, 64.0 );


INSERT INTO VEHICLE_TYPE
    (ID, PRICE, VEHICLE_NAME) VALUES ( 1, 200.00,  1), ( 2, 299.99,  2);


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


