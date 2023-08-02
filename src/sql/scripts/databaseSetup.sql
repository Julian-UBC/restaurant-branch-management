CREATE TABLE Menu (
    name		VARCHAR(50)	PRIMARY KEY,
    cost		FLOAT,
    category	VARCHAR(50)	NOT NULL
)

CREATE TABLE Ingredients (
    name			VARCHAR(50)	PRIMARY KEY,
    storageMethod	VARCHAR(50)
)

CREATE TABLE IngredientsRequired (
    menuName		VARCHAR(50),
    ingredientsName	VARCHAR(50),
    ingredientsAmount	INTEGER,
    PRIMARY KEY (menuName, ingredientsName),
    FOREIGN KEY (menuName)
        REFERENCES Menu (name)
        ON DELETE CASCADE,
        --ON UPDATE CASCADE,
    FOREIGN KEY (ingredientsName)
        REFERENCES Ingredients (name)
        ON DELETE CASCADE
        --ON UPDATE CASCADE
)

CREATE TABLE Customers (
    cID		INTEGER	PRIMARY KEY,
    email		VARCHAR(50)	NOT NULL,
    phoneNum	VARCHAR(10)	NOT NULL,
    points		INTEGER	NOT NULL,
    firstName	VARCHAR(50),
    lastName	VARCHAR(50),
    dob		DATE,
    UNIQUE (email, phoneNum)
)

CREATE TABLE MembershipsSubscribes (
    cID	INTEGER,
    tiers	VARCHAR(50),
    PRIMARY KEY (cID, tiers),
    FOREIGN KEY (cID)
        REFERENCES Customers (cID)
        ON DELETE CASCADE
        --ON UPDATE CASCADE
)

CREATE TABLE Branches (
    locID			INTEGER	PRIMARY KEY,
    streetAddress		VARCHAR(50),
    city			VARCHAR(50),
    province		VARCHAR(50)
)

CREATE TABLE MenuServed (
    menuName	VARCHAR(50),
    locID		INTEGER,
    PRIMARY KEY (menuName, locID),
    FOREIGN KEY (menuName)
        REFERENCES Menu (name)
        ON DELETE CASCADE,
        --ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE
        --ON UPDATE CASCADE
)

CREATE TABLE WaitersTableSection (
    tableSection	VARCHAR(50)	PRIMARY KEY,
    wages		FLOAT
)

CREATE TABLE WaitersInfo (
    wID		INTEGER	PRIMARY KEY,
    firstName	VARCHAR(50),
    lastName	VARCHAR(50),
    SIN		CHAR(9)	NOT NULL,
    tableSection	VARCHAR(50),
    locID		INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (tableSection)
        REFERENCES WaitersTableSection (tableSection)
        ON DELETE SET NULL,
        --ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
        --ON UPDATE CASCADE
)

CREATE TABLE ChefSpecialty (
    specialty	VARCHAR(50),
    wages		FLOAT,
    PRIMARY KEY (specialty)
)

CREATE TABLE ChefInfo(
    wID		INTEGER	PRIMARY KEY,
    firstName	VARCHAR(50),
    lastName  VARCHAR(50),
    SIN       CHAR(9) NOT NULL,
    specialty VARCHAR(50),
    locID     INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (specialty)
        REFERENCES ChefSpecialty (specialty)
        ON DELETE SET NULL,
        --ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
        --ON UPDATE CASCADE
)

CREATE TABLE Hosts(
    wID       INTEGER PRIMARY KEY,
    firstName VARCHAR(50),
    lastName  VARCHAR(50),
    wages     FLOAT,
    SIN       CHAR(9) NOT NULL,
    locID     INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
        --ON UPDATE CASCADE
)

CREATE TABLE Manager(
    wID       INTEGER PRIMARY KEY,
    firstName VARCHAR(50),
    lastName  VARCHAR(50),
    SIN       CHAR(9) NOT NULL,
    rating    FLOAT,
    locID     INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
        --ON UPDATE CASCADE
)

CREATE TABLE EquipmentsName(
    name     VARCHAR(50) PRIMARY KEY,
    category VARCHAR(50)
)

CREATE TABLE EquipmentsMain(
    id 		INTEGER 	PRIMARY KEY,
    name 		VARCHAR(50),
    condition	VARCHAR(50),
    yearBought	INTEGER,
        FOREIGN KEY(name)
            REFERENCES EquipmentsName (name)
            ON DELETE CASCADE
            --ON UPDATE CASCADE
)

CREATE TABLE EquipmentContained(
    locID 		INTEGER,
    equipID 	INTEGER,
    PRIMARY KEY (locID, equipID),
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE,
        --ON UPDATE CASCADE,
    FOREIGN KEY (equipID)
        REFERENCES EquipmentsMain(id)
        ON DELETE CASCADE
        --ON UPDATE CASCADE
)

create table Employee
(
    wID int not null
)

CREATE TABLE RESERVATIONS(
    rID             INTEGER,
    cID             INTEGER NOT NULL,
    locID           INTEGER NOT NULL,
    wID             INTEGER NOT NULL,
    date            DATE,
    time            TIME,
    numOfPeople     INTEGER,
    reservationName CHAR(50),
    PRIMARY KEY (rID),
    FOREIGN KEY (cID)
        REFERENCES Customers (cID)
        ON DELETE CASCADE,
        --ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE,
        --ON UPDATE CASCADE,
    FOREIGN KEY (wID)
        REFERENCES Employee (wID)
        ON DELETE CASCADE
        --ON UPDATE CASCADE
)

-- populate table for Branches
INSERT INTO Branches VALUES(111, '792 Montreal Road', 'Ottawa', 'Ontario');
INSERT INTO Branches VALUES(112, '1257 Beaver Creek', 'Thornhill', 'Ontario');
INSERT INTO Branches VALUES(113, '792 Montreal Road', 'Vancouver', 'British Columbia');
INSERT INTO Branches VALUES(114, '1221 Robson Street', 'Vancouver', 'British Columbia');
INSERT INTO Branches VALUES(115, '4795 Robson Street', 'Vancouver', 'British Columbia');
-- populate table for Hosts
INSERT INTO Hosts VALUES(11, 'Tom', 'John', 21.53, '203948576', 111);
INSERT INTO Hosts VALUES(12, 'Jim', 'Jones', 19.98, '564738291', 112);
INSERT INTO Hosts VALUES(13, 'Anna', 'Hills', 21.21, '543526169', 113);
INSERT INTO Hosts VALUES(14, 'Tim', 'Jones', 20.12, '123456787', 114);
INSERT INTO Hosts VALUES(15, 'Tina', 'John', 20.34, '123456765', 115);
-- populate table for WaitersInfo
INSERT INTO WaitersInfo VALUES(1, 'Joe', 'Smith', '123456789', 'VIP Second Floor', 111);
INSERT INTO WaitersInfo VALUES(2, 'Tom', 'Miller', '234567891', 'First Floor General', 112);
INSERT INTO WaitersInfo VALUES(3, 'Tina', 'Johnson', '345678901', 'First Floor General', 113);
INSERT INTO WaitersInfo VALUES(4, 'Alex', 'Wang', '456789012', 'Second Floor General', 114);
INSERT INTO WaitersInfo VALUES(5, 'Jenny', 'Kim', '567890123', 'VIP First Floor', 115);
-- populate table for WaitersTableSection
INSERT INTO WaitersTableSection VALUES('VIP Second Floor', 23.31);
INSERT INTO WaitersTableSection VALUES('First Floor General', 18.23);
INSERT INTO WaitersTableSection VALUES('First Floor Banquet', 20.31);
INSERT INTO WaitersTableSection VALUES('Second Floor General', 18.55);
INSERT INTO WaitersTableSection VALUES('VIP First Floor', 23.11);
-- populate table for Managers
INSERT INTO Manager VALUES(16, 'Jamilah', 'Gayle', 25.5, '112345678', 113);
INSERT INTO Manager VALUES(17, 'Kris', 'Lena', 26.12, '111234567', 115);
INSERT INTO Manager VALUES(18, 'Joost', 'Taavetti', 24.33, '111123456', 112);
INSERT INTO Manager VALUES(19, 'Helga', 'Aitor', 25.41, '111112345', 111);
INSERT INTO Manager VALUES(20, 'Davide', 'Artie', 24.61, '111111234', 114);
-- populate table for ChefInfo
INSERT INTO ChefInfo VALUES(200, 'Oliwer', 'Dennir', '987654321', 'Head', 111);
INSERT INTO ChefInfo VALUES(204, 'Janice', 'Patel', '998765432', 'Sous', 111);
INSERT INTO ChefInfo VALUES(206, 'Sion', 'Jordan', '999876543', 'Saucier', 111);
INSERT INTO ChefInfo VALUES(208, 'Kelly', 'Weis', '999987654', 'Pastry', 111);
INSERT INTO ChefInfo VALUES(210, 'Whitney', 'Dunn', '999998765', 'Prep', 111);
INSERT INTO ChefInfo VALUES(212, 'Jerry', 'Dunn', '999999876', 'Sous', 112);
INSERT INTO ChefInfo VALUES(214, 'Diana', 'Cherry', '999999987', 'Pastry', 112);
INSERT INTO ChefInfo VALUES(216, 'Eryn', 'Knapp', '999999998', 'Prep', 112);
INSERT INTO ChefInfo VALUES(218, 'Eryn', 'Le', '999999999', 'Sous', 113);
INSERT INTO ChefInfo VALUES(220, 'Jakob', 'Singh', '887654321', 'Prep', 113);
INSERT INTO ChefInfo VALUES(222, 'Shauna', 'Stark', '888765432', 'Pastry', 114);
INSERT INTO ChefInfo VALUES(224, 'Lauren', 'Gordon', '888876543', 'Prep', 114);
INSERT INTO ChefInfo VALUES(226, 'Edmun', 'Gordon', '888887654', 'Sous', 115);
INSERT INTO ChefInfo VALUES(228, 'Essa', 'Cantrell', '888888765', 'Prep', 115);

-- populate table for ChefSpeciality

INSERT INTO ChefSpecialty VALUES ('Head' , 39.95);
INSERT INTO ChefSpecialty VALUES ('Sous' , 29.95);
INSERT INTO ChefSpecialty VALUES ('Saucier' , 20.55);
INSERT INTO ChefSpecialty VALUES ('Pastry' , 18.35);
INSERT INTO ChefSpecialty VALUES ('Prep' , 17.25);
INSERT INTO ChefSpecialty VALUES ('Line' , 16.75);

-- populate table for Reservations

INSERT INTO Reservations VALUES (1000, 1, 111, 11, '2023-08-01', '18:20:00', 4, 'John');
INSERT INTO Reservations VALUES (1001, 2, 113, 13, '2023-08-01', '19:00:00', 3, 'Bobs birthday');
INSERT INTO Reservations VALUES (1002, 1, 111, 11, '2023-08-03', '19:30:00', 8, 'Marketing team social');
INSERT INTO Reservations VALUES (1003, 4, 112, 12, '2023-08-04', '18:00:00', 2, 'Wangs anniversary');
INSERT INTO Reservations VALUES (1004, 3, 113, 13, '2023-08-04', '18:20:00', 3, 'Tracy');
INSERT INTO Reservations VALUES (1005, 2, 114, 14, '2023-08-05', '19:00:00', 6, 'Bobs birthday');

-- populate table for EquipmentsMain

INSERT INTO EquipmentsMain VALUES (1, 'Table', 'Good', 2020);
INSERT INTO EquipmentsMain VALUES (2, 'Stand Mixer', 'Fair', 2005);
INSERT INTO EquipmentsMain VALUES (3, 'Chair', 'Good', 2021);
INSERT INTO EquipmentsMain VALUES (4, 'Christmas Lights', 'Poor', 1999);
INSERT INTO EquipmentsMain VALUES (5, 'Christmas Tree', 'Very Good', 2022);

-- populate table for EquipmentsName

INSERT INTO EquipmentsName VALUES ('Table', 'Furniture');
INSERT INTO EquipmentsName VALUES ('Stand Mixer', 'Appliance');
INSERT INTO EquipmentsName VALUES ('Chair', 'Furniture');
INSERT INTO EquipmentsName VALUES ('Christmas Lights', 'Decoration');
INSERT INTO EquipmentsName VALUES ('Christmas Tree', 'Decoration');

-- populate table for EquipmentContained

INSERT INTO EquipmentContained VALUES (111, 1);
INSERT INTO EquipmentContained VALUES (111, 3);
INSERT INTO EquipmentContained VALUES (112, 1);
INSERT INTO EquipmentContained VALUES (112, 2);
INSERT INTO EquipmentContained VALUES (112, 3);
INSERT INTO EquipmentContained VALUES (113, 1);
INSERT INTO EquipmentContained VALUES (113, 3);
INSERT INTO EquipmentContained VALUES (114, 1);
INSERT INTO EquipmentContained VALUES (114, 3);
INSERT INTO EquipmentContained VALUES (115, 1);
INSERT INTO EquipmentContained VALUES (115, 2);
INSERT INTO EquipmentContained VALUES (115, 3);
INSERT INTO EquipmentContained VALUES (115, 4);
INSERT INTO EquipmentContained VALUES (115, 5);
