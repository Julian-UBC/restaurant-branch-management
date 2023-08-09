-- Drop tables with CASCADE CONSTRAINTS
DROP TABLE Menu CASCADE CONSTRAINTS;
DROP TABLE Ingredients CASCADE CONSTRAINTS;
DROP TABLE IngredientsRequired CASCADE CONSTRAINTS;
DROP TABLE Branches CASCADE CONSTRAINTS;
DROP TABLE MenuServed CASCADE CONSTRAINTS;
DROP TABLE Customers CASCADE CONSTRAINTS;
DROP TABLE MembershipSubscribes CASCADE CONSTRAINTS;
DROP TABLE WaitersTableSection CASCADE CONSTRAINTS;
DROP TABLE WaitersInfo CASCADE CONSTRAINTS;
DROP TABLE ChefSpecialty CASCADE CONSTRAINTS;
DROP TABLE ChefInfo CASCADE CONSTRAINTS;
DROP TABLE Hosts CASCADE CONSTRAINTS;
DROP TABLE Managers CASCADE CONSTRAINTS;
DROP TABLE EquipmentsName CASCADE CONSTRAINTS;
DROP TABLE EquipmentsMain CASCADE CONSTRAINTS;
DROP TABLE EquipmentContained CASCADE CONSTRAINTS;
DROP TABLE Reservations CASCADE CONSTRAINTS;

--
-- 	Create Database Table
--

-- Menu
CREATE TABLE Menu (
    name        VARCHAR2(50) PRIMARY KEY,
    cost        FLOAT,
    category    VARCHAR2(50) NOT NULL
);

-- Ingredients
CREATE TABLE Ingredients (
    name            VARCHAR2(50) PRIMARY KEY,
    storageMethod   VARCHAR2(50)
);

-- IngredientsRequired
CREATE TABLE IngredientsRequired (
    menuName            VARCHAR2(50),
    ingredientsName     VARCHAR2(50),
    ingredientsAmount   INTEGER,
    PRIMARY KEY (menuName, ingredientsName),
    FOREIGN KEY (menuName)
        REFERENCES Menu (name)
        ON DELETE CASCADE,
    FOREIGN KEY (ingredientsName)
        REFERENCES Ingredients (name)
        ON DELETE CASCADE
);

CREATE TABLE Branches (
    locID			INTEGER	PRIMARY KEY,
    streetAddress	VARCHAR2(50),
    city			VARCHAR2(50),
    province		VARCHAR2(50)
);

-- MenuServed
CREATE TABLE MenuServed (
    menuName    VARCHAR2(50),
    locID       INTEGER,
    PRIMARY KEY (menuName, locID),
    FOREIGN KEY (menuName)
        REFERENCES Menu (name)
        ON DELETE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE
);

-- Customers
CREATE TABLE Customers (
    cID         INTEGER         PRIMARY KEY,
    email       VARCHAR2(50)    NOT NULL,
    phoneNum    VARCHAR2(10)    NOT NULL,
    points      INTEGER         NOT NULL,
    firstName   VARCHAR2(50),
    lastName    VARCHAR2(50),
    dob         DATE            NOT NULL,
    UNIQUE(email, phoneNum)
);

-- MembershipSubscribes
CREATE TABLE MembershipSubscribes (
    cID     INTEGER,
    tiers   VARCHAR2(50),
    PRIMARY KEY (cID, tiers),
    FOREIGN KEY (cID)
        REFERENCES Customers (cID)
        ON DELETE CASCADE
);

-- WaitersTableSection
CREATE TABLE WaitersTableSection (
    tableSection	VARCHAR2(50)	PRIMARY KEY,
    wages		    FLOAT
);

-- WaitersInfo
CREATE TABLE WaitersInfo (
    wID		        INTEGER	        PRIMARY KEY,
    firstName	    VARCHAR2(50),
    lastName	    VARCHAR2(50),
    SIN		        CHAR(9)	        NOT NULL,
    tableSection	VARCHAR2(50),
    locID		    INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (tableSection)
        REFERENCES WaitersTableSection (tableSection)
        ON DELETE SET NULL,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
);

-- ChefSpecialty
CREATE TABLE ChefSpecialty (
    specialty	VARCHAR2(50),
    wages		FLOAT,
    PRIMARY KEY (specialty)
);

-- ChefInfo
CREATE TABLE ChefInfo(
    wID		    INTEGER	        PRIMARY KEY,
    firstName	VARCHAR2(50),
    lastName    VARCHAR2(50),
    SIN         CHAR(9)         NOT NULL,
    specialty   VARCHAR2(50),
    locID       INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (specialty)
        REFERENCES ChefSpecialty (specialty)
        ON DELETE SET NULL,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
);

-- Hosts
CREATE TABLE Hosts(
    wID       INTEGER       PRIMARY KEY,
    firstName VARCHAR2(50),
    lastName  VARCHAR2(50),
    wages     FLOAT,
    SIN       CHAR(9)       NOT NULL,
    locID     INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
);

-- Managers
CREATE TABLE Managers(
    wID       INTEGER       PRIMARY KEY,
    firstName VARCHAR2(50),
    lastName  VARCHAR2(50),
    SIN       CHAR(9)       NOT NULL,
    rating    FLOAT,
    locID     INTEGER,
    UNIQUE (SIN),
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
);

-- EquipmentsName
CREATE TABLE EquipmentsName(
    name     VARCHAR2(50) PRIMARY KEY,
    category VARCHAR2(50)
);

-- EquipmentsMain
CREATE TABLE EquipmentsMain(
    id 		    INTEGER 	    PRIMARY KEY,
    name 		VARCHAR2(50),
    condition	VARCHAR2(50),
    yearBought	INTEGER,
    FOREIGN KEY(name)
        REFERENCES EquipmentsName (name)
        ON DELETE CASCADE
);

-- EquipmentContained
CREATE TABLE EquipmentContained(
    locID 		INTEGER,
    equipID 	INTEGER,
    PRIMARY KEY (locID, equipID),
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE,
    FOREIGN KEY (equipID)
        REFERENCES EquipmentsMain(id)
        ON DELETE CASCADE
);

-- Reservations
CREATE TABLE Reservations(
    rID             INTEGER,
    cID             INTEGER     NOT NULL,
    locID           INTEGER     NOT NULL,
    wID             INTEGER     NOT NULL,
    rDate           DATE,
    rTime           DATE,
    numOfPeople     INTEGER,
    reservationName CHAR(50),
    PRIMARY KEY (rID),
    FOREIGN KEY (cID)
        REFERENCES Customers (cID)
        ON DELETE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE,
    FOREIGN KEY (wID)
        REFERENCES Hosts (wID)
        ON DELETE CASCADE
);

--
-- ON UPDATE CASCADE --> Trigger
--

CREATE TRIGGER MenuUpdate
AFTER UPDATE OF name ON Menu
FOR EACH ROW
BEGIN
	UPDATE IngredientsRequired SET menuName = :new.name WHERE menuName = :old.name;
	UPDATE MenuServed SET menuName = :new.name WHERE menuName = :old.name;
END;

CREATE TRIGGER IngredientsUpdate
AFTER UPDATE OF name ON Ingredients
FOR EACH ROW
BEGIN
	UPDATE IngredientsRequired SET ingredientsName = :new.name WHERE ingredientsName = :old.name;
END;

CREATE TRIGGER BranchUpdate
AFTER UPDATE OF locID ON Branches
FOR EACH ROW
BEGIN
	UPDATE MenuServed SET locID = :new.locID WHERE locID = :old.locID;
	UPDATE WaitersInfo SET locID = :new.locID WHERE locID = :old.locID;
	UPDATE ChefInfo SET locID = :new.locID WHERE locID = :old.locID;
	UPDATE Hosts SET locID = :new.locID WHERE locID = :old.locID;
	UPDATE Managers SET locID = :new.locID WHERE locID = :old.locID;
	UPDATE EquipmentContained SET locID = :new.locID WHERE locID = :old.locID;
	UPDATE Reservations SET locID = :new.locID WHERE locID = :old.locID;
END;

CREATE TRIGGER CustomerUpdate
AFTER UPDATE OF cID ON Customers
FOR EACH ROW
BEGIN
	UPDATE MembershipSubscribes SET cID = :new.cID WHERE cID = :old.cID;
	UPDATE Reservations SET cID = :new.cID WHERE cID = :old.cID;
END;

CREATE TRIGGER TableUpdate
AFTER UPDATE OF tableSection ON WaitersTableSection
FOR EACH ROW
BEGIN
	UPDATE WaitersInfo SET tableSection = :new.tableSection WHERE tableSection = :old.tableSection;
END;

CREATE TRIGGER SpecialityUpdate
AFTER UPDATE OF specialty ON ChefSpecialty
FOR EACH ROW
BEGIN
	UPDATE ChefInfo SET specialty = :new.specialty WHERE specialty = :old.specialty;
END;

CREATE TRIGGER EquipmentNameUpdate
AFTER UPDATE OF name ON EquipmentsName
FOR EACH ROW
BEGIN
	UPDATE EquipmentsMain SET name = :new.name WHERE name = :old.name;
END;

CREATE TRIGGER EquipmentMainUpdate
AFTER UPDATE OF id ON EquipmentsMain
FOR EACH ROW
BEGIN
	UPDATE EquipmentContained SET equipID = :new.id WHERE equipID = :old.id;
END;

CREATE TRIGGER HostUpdate
AFTER UPDATE OF wID ON Hosts
FOR EACH ROW
BEGIN
	UPDATE Reservations SET wID = :new.wID WHERE wID = :old.wID;
END;

--
-- Populate Database Table
--

-- Menu
INSERT INTO Menu VALUES ('Miso and mango soup', 7.89, 'Main Dish');
INSERT INTO Menu VALUES ('Chervil and prosciutto salad', 6.49, 'Appetizer');
INSERT INTO Menu VALUES ('Tangerine and ugli fruit salad', 6.99, 'Appetizer');
INSERT INTO Menu VALUES ('Haddock and pepper pie', 9.99, 'Main Dish');
INSERT INTO Menu VALUES ('Courgette and ginger cake', 6.49, 'Dessert');

-- Ingredients
INSERT INTO Ingredients VALUES ('Onion', 'Room Temperature');
INSERT INTO Ingredients VALUES ('Miso', 'Refrigerator');
INSERT INTO Ingredients VALUES ('Mango', 'Room Temperature');
INSERT INTO Ingredients VALUES ('Lettuce', 'Refrigerator');
INSERT INTO Ingredients VALUES ('Chevril', 'Refrigerator');
INSERT INTO Ingredients VALUES ('Prosciutto', 'Refrigerator');
INSERT INTO Ingredients VALUES ('Tangerine', 'Room Temperature');
INSERT INTO Ingredients VALUES ('Ugli Fruit', 'Room Temperature');
INSERT INTO Ingredients VALUES ('Flour', 'Room Temperature');
INSERT INTO Ingredients VALUES ('Butter', 'Refrigerator');
INSERT INTO Ingredients VALUES ('Haddock', 'Freezer');
INSERT INTO Ingredients VALUES ('Pepper', 'Room Temperature');
INSERT INTO Ingredients VALUES ('Egg', 'Refrigerator');
INSERT INTO Ingredients VALUES ('Sugar', 'Room Temperature');
INSERT INTO Ingredients VALUES ('Courgette', 'Refrigerator');
INSERT INTO Ingredients VALUES ('Ginger', 'Room Temperature');

-- IngredientsRequired
INSERT INTO IngredientsRequired VALUES ('Miso and mango soup', 'Onion', 2);
INSERT INTO IngredientsRequired VALUES ('Miso and mango soup', 'Miso', 1);
INSERT INTO IngredientsRequired VALUES ('Miso and mango soup', 'Mango', 1);
INSERT INTO IngredientsRequired VALUES ('Chervil and prosciutto salad', 'Lettuce', 3);
INSERT INTO IngredientsRequired VALUES ('Chervil and prosciutto salad', 'Chevril', 3);
INSERT INTO IngredientsRequired VALUES ('Chervil and prosciutto salad', 'Prosciutto', 2);
INSERT INTO IngredientsRequired VALUES ('Tangerine and ugli fruit salad', 'Lettuce', 2);
INSERT INTO IngredientsRequired VALUES ('Tangerine and ugli fruit salad', 'Tangerine', 1);
INSERT INTO IngredientsRequired VALUES ('Tangerine and ugli fruit salad', 'Ugli Fruit', 2);
INSERT INTO IngredientsRequired VALUES ('Haddock and pepper pie', 'Flour', 1);
INSERT INTO IngredientsRequired VALUES ('Haddock and pepper pie', 'Butter', 1);
INSERT INTO IngredientsRequired VALUES ('Haddock and pepper pie', 'Sugar', 1);
INSERT INTO IngredientsRequired VALUES ('Haddock and pepper pie', 'Haddock', 2);
INSERT INTO IngredientsRequired VALUES ('Haddock and pepper pie', 'Pepper', 1);
INSERT INTO IngredientsRequired VALUES ('Courgette and ginger cake', 'Flour', 1);
INSERT INTO IngredientsRequired VALUES ('Courgette and ginger cake', 'Butter', 1);
INSERT INTO IngredientsRequired VALUES ('Courgette and ginger cake', 'Egg', 2);
INSERT INTO IngredientsRequired VALUES ('Courgette and ginger cake', 'Sugar', 1);
INSERT INTO IngredientsRequired VALUES ('Courgette and ginger cake', 'Courgette', 2);
INSERT INTO IngredientsRequired VALUES ('Courgette and ginger cake', 'Ginger', 1);

-- Branches
INSERT INTO Branches VALUES(111, '792 Montreal Road', 'Ottawa', 'Ontario');
INSERT INTO Branches VALUES(112, '1257 Beaver Creek', 'Thornhill', 'Ontario');
INSERT INTO Branches VALUES(113, '792 Montreal Road', 'Vancouver', 'British Columbia');
INSERT INTO Branches VALUES(114, '1221 Robson Street', 'Vancouver', 'British Columbia');
INSERT INTO Branches VALUES(115, '4795 Robson Street', 'Vancouver', 'British Columbia');

-- MenuServed
INSERT INTO MenuServed VALUES ('Miso and mango soup', 111);
INSERT INTO MenuServed VALUES ('Haddock and pepper pie', 111);
INSERT INTO MenuServed VALUES ('Miso and mango soup', 112);
INSERT INTO MenuServed VALUES ('Chervil and prosciutto salad', 112);
INSERT INTO MenuServed VALUES ('Haddock and pepper pie', 112);
INSERT INTO MenuServed VALUES ('Tangerine and ugli fruit salad', 112);
INSERT INTO MenuServed VALUES ('Miso and mango soup', 113);
INSERT INTO MenuServed VALUES ('Haddock and pepper pie', 113);
INSERT INTO MenuServed VALUES ('Courgette and ginger cake', 113);
INSERT INTO MenuServed VALUES ('Miso and mango soup', 114);
INSERT INTO MenuServed VALUES ('Chervil and prosciutto salad', 114);
INSERT INTO MenuServed VALUES ('Haddock and pepper pie', 114);
INSERT INTO MenuServed VALUES ('Courgette and ginger cake', 114);
INSERT INTO MenuServed VALUES ('Miso and mango soup', 115);
INSERT INTO MenuServed VALUES ('Haddock and pepper pie', 115);
INSERT INTO MenuServed VALUES ('Courgette and ginger cake', 115);

-- Customers
INSERT INTO Customers VALUES (1, 'j_smith@hotmail.com', '6043324567', 0, 'John', 'Smith', to_date('1998-08-23', 'YYYY-MM-DD'));
INSERT INTO Customers VALUES (2, 'bob@gmail.com', '7781234912', 0, 'Bob', 'Smith', to_date('1998-08-01', 'YYYY-MM-DD'));
INSERT INTO Customers VALUES (3, 'tracyk@outlook.com', '1234567892', 100, 'Tracy', 'Kim', to_date('2001-06-13', 'YYYY-MM-DD'));
INSERT INTO Customers VALUES (4, 'sarawang@gmail.com', '2345678912', 50, 'Sara', 'Wang', to_date('1972-01-03', 'YYYY-MM-DD'));
INSERT INTO Customers VALUES (5, 'zoem@hotmail.com', '3456789012', 24, 'Zoe', 'Miller', to_date('1980-04-12', 'YYYY-MM-DD'));

-- MembershipSubscribes
INSERT INTO MembershipSubscribes VALUES (1, 'Gold');
INSERT INTO MembershipSubscribes VALUES (1, 'Silver');
INSERT INTO MembershipSubscribes VALUES (3, 'Gold');
INSERT INTO MembershipSubscribes VALUES (4, 'Bronze');
INSERT INTO MembershipSubscribes VALUES (5, 'Silver');

-- populate table for Hosts
INSERT INTO Hosts VALUES(11, 'Tom', 'John', 21.53, '203948576', 111);
INSERT INTO Hosts VALUES(12, 'Jim', 'Jones', 19.98, '564738291', 112);
INSERT INTO Hosts VALUES(13, 'Anna', 'Hills', 21.21, '543526169', 113);
INSERT INTO Hosts VALUES(14, 'Tim', 'Jones', 20.12, '123456787', 114);
INSERT INTO Hosts VALUES(15, 'Tina', 'John', 20.34, '123456765', 115);

-- populate table for WaitersTableSection
INSERT INTO WaitersTableSection VALUES('VIP Second Floor', 23.31);
INSERT INTO WaitersTableSection VALUES('First Floor General', 18.23);
INSERT INTO WaitersTableSection VALUES('First Floor Banquet', 20.31);
INSERT INTO WaitersTableSection VALUES('Second Floor General', 18.55);
INSERT INTO WaitersTableSection VALUES('VIP First Floor', 23.11);

-- populate table for WaitersInfo
INSERT INTO WaitersInfo VALUES(1, 'Joe', 'Smith', '123456789', 'VIP Second Floor', 111);
INSERT INTO WaitersInfo VALUES(2, 'Tom', 'Miller', '234567891', 'First Floor General', 112);
INSERT INTO WaitersInfo VALUES(3, 'Tina', 'Johnson', '345678901', 'First Floor General', 113);
INSERT INTO WaitersInfo VALUES(4, 'Alex', 'Wang', '456789012', 'Second Floor General', 114);
INSERT INTO WaitersInfo VALUES(5, 'Jenny', 'Kim', '567890123', 'VIP First Floor', 115);

-- populate table for Managers
INSERT INTO Managers VALUES(16, 'Jamilah', 'Gayle', 25.5, '112345678', 113);
INSERT INTO Managers VALUES(17, 'Kris', 'Lena', 26.12, '111234567', 115);
INSERT INTO Managers VALUES(18, 'Joost', 'Taavetti', 24.33, '111123456', 112);
INSERT INTO Managers VALUES(19, 'Helga', 'Aitor', 25.41, '111112345', 111);
INSERT INTO Managers VALUES(20, 'Davide', 'Artie', 24.61, '111111234', 114);

-- populate table for ChefSpecialty
INSERT INTO ChefSpecialty VALUES ('Head' , 39.95);
INSERT INTO ChefSpecialty VALUES ('Sous' , 29.95);
INSERT INTO ChefSpecialty VALUES ('Saucier' , 20.55);
INSERT INTO ChefSpecialty VALUES ('Pastry' , 18.35);
INSERT INTO ChefSpecialty VALUES ('Prep' , 17.25);
INSERT INTO ChefSpecialty VALUES ('Line' , 16.75);
INSERT INTO ChefSpecialty VALUES ('Cook', 16.55);

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

-- populate table for Reservations
INSERT INTO Reservations VALUES (1000, 1, 111, 11, to_date('2023-08-20', 'YYYY-MM-DD'), to_date('1970-01-01 18:20:00', 'YYYY-MM-DDHH 24:MI:SS'), 4, 'John');
INSERT INTO Reservations VALUES (1001, 2, 113, 13, to_date('2023-08-10', 'YYYY-MM-DD'), to_date('1970-01-01 19:00:00', 'YYYY-MM-DDHH HH24:MI:SS'), 3, 'Bob''s birthday');
INSERT INTO Reservations VALUES (1002, 1, 111, 11, to_date('2023-08-11', 'YYYY-MM-DD'), to_date('1970-01-01 19:30:00', 'YYYY-MM-DDHH HH24:MI:SS'), 8, 'Marketing team social');
INSERT INTO Reservations VALUES (1003, 4, 112, 12, to_date('2023-08-04', 'YYYY-MM-DD'), to_date('1970-01-01 18:00:00', 'YYYY-MM-DDHH HH24:MI:SS'), 2, 'Wang''s anniversary');
INSERT INTO Reservations VALUES (1004, 3, 113, 13, to_date('2023-08-04', 'YYYY-MM-DD'), to_date('1970-01-01 18:20:00', 'YYYY-MM-DDHH HH24:MI:SS'), 3, 'Tracy');
INSERT INTO Reservations VALUES (1005, 2, 114, 14, to_date('2023-08-11', 'YYYY-MM-DD'), to_date('1970-01-01 19:00:00', 'YYYY-MM-DDHH HH24:MI:SS'), 6, 'Bob''s birthday');

-- populate table for EquipmentsName
INSERT INTO EquipmentsName VALUES ('Table', 'Furniture');
INSERT INTO EquipmentsName VALUES ('Stand Mixer', 'Appliance');
INSERT INTO EquipmentsName VALUES ('Chair', 'Furniture');
INSERT INTO EquipmentsName VALUES ('Christmas Lights', 'Decoration');
INSERT INTO EquipmentsName VALUES ('Christmas Tree', 'Decoration');

-- populate table for EquipmentsMain
INSERT INTO EquipmentsMain VALUES (1, 'Table', 'Good', 2020);
INSERT INTO EquipmentsMain VALUES (2, 'Stand Mixer', 'Fair', 2005);
INSERT INTO EquipmentsMain VALUES (3, 'Chair', 'Good', 2021);
INSERT INTO EquipmentsMain VALUES (4, 'Christmas Lights', 'Poor', 1999);
INSERT INTO EquipmentsMain VALUES (5, 'Christmas Tree', 'Very Good', 2022);

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