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
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (ingredientsName)
        REFERENCES Ingredients (name)
        ON DELETE CASCADE
        ON UPDATE CASCADE
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
        ON UPDATE CASCADE
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
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
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
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
        ON UPDATE CASCADE
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
        ON DELETE SET DEFAULT
        ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE SET NULL
        ON UPDATE CASCADE
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
        ON UPDATE CASCADE
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
        ON UPDATE CASCADE
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
            ON UPDATE CASCADE
)

CREATE TABLE EquipmentContained(
    locID 		INTEGER,
    equipID 	INTEGER,
    PRIMARY KEY (locID, equipID),
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (equipID)
        REFERENCES EquipmentMain(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
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
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (wID)
        REFERENCES Employee (wID)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)

-- populate table for Branches
INSERT INTO Branches VALUES(111, "792 Montreal Road", "Ottawa", "Ontario")
INSERT INTO Branches VALUES(112, "1257 Beaver Creek", "Thornhill", "Ontario")
INSERT INTO Branches VALUES(113, "792 Montreal Road", "Vancouver", "British Columbia")
INSERT INTO Branches VALUES(114, "1221 Robson Street", "Vancouver", "British Columbia")
INSERT INTO Branches VALUES(115, "4795 Robson Street", "Vancouver", "British Columbia")
-- populate table for Hosts
INSERT INTO Hosts VALUES(11, "Tom", "John", 21.53, "203948576", 111)
INSERT INTO Hosts VALUES(12, "Jim", "Jones", 19.98, "564738291", 112)
INSERT INTO Hosts VALUES(13, "Anna", "Hills", 21.21, "543526169", 113)
INSERT INTO Hosts VALUES(14, "Tim", "Jones", 20.12, "123456787", 114)
INSERT INTO Hosts VALUES(15, "Tina", "John", 20.34, "123456765", 115)
-- populate table for WaitersInfo
INSERT INTO WaitersInfo VALUES(1, "Joe", "Smith", "123456789", "VIP Second Floor", 111)
INSERT INTO WaitersInfo VALUES(2, "Tom", "Miller", "234567891", "First Floor General", 112)
INSERT INTO WaitersInfo VALUES(3, "Tina", "Johnson", "345678901", "First Floor General", 113)
INSERT INTO WaitersInfo VALUES(4, "Alex", "Wang", "456789012", "Second Floor General", 114)
INSERT INTO WaitersInfo VALUES(5, "Jenny", "Kim", "567890123", "VIP First Floor", 115)
-- populate table for WaitersTableSection
INSERT INTO WaitersTableSection VALUES("VIP Second Floor", 23.31)
INSERT INTO WaitersTableSection VALUES("First Floor General", 18.23)
INSERT INTO WaitersTableSection VALUES("First Floor Banquet", 20.31)
INSERT INTO WaitersTableSection VALUES("Second Floor General", 18.55)
INSERT INTO WaitersTableSection VALUES("VIP First Floor", 23.11)
-- populate table for Managers
INSERT INTO Managers VALUES(16, "Jamilah", "Gayle", 25.5, "112345678", 113)
INSERT INTO Managers VALUES(17, "Kris", "Lena", 26.12, "111234567", 115)
INSERT INTO Managers VALUES(18, "Joost", "Taavetti", 24.33, "111123456", 112)
INSERT INTO Managers VALUES(19, "Helga", "Aitor", 25.41, "111112345", 111)
INSERT INTO Managers VALUES(20, "Davide", "Artie", 24.61, "111111234", 114)
-- populate table for ChefInfo
INSERT INTO ChefInfo VALUES(200, "Oliwer", "Dennir", "987654321", "Head", 111)
INSERT INTO ChefInfo VALUES(204, "Janice", "Patel", "998765432", "Sous", 111)
INSERT INTO ChefInfo VALUES(206, "Sion", "Jordan", "999876543", "Saucier", 111)
INSERT INTO ChefInfo VALUES(208, "Kelly", "Weis", "999987654", "Pastry", 111)
INSERT INTO ChefInfo VALUES(210, "Whitney", "Dunn", "999998765", "Prep", 111)
INSERT INTO ChefInfo VALUES(212, "Jerry", "Dunn", "999999876", "Sous", 112)
INSERT INTO ChefInfo VALUES(214, "Diana", "Cherry", "999999987", "Pastry", 112)
INSERT INTO ChefInfo VALUES(216, "Eryn", "Knapp", "999999998", "Prep", 112)
INSERT INTO ChefInfo VALUES(218, "Eryn", "Le", "999999999", "Sous", 113)
INSERT INTO ChefInfo VALUES(220, "Jakob", "Singh", "887654321", "Prep", 113)
INSERT INTO ChefInfo VALUES(222, "Shauna", "Stark", "888765432", "Pastry", 114)
INSERT INTO ChefInfo VALUES(224, "Lauren", "Gordon", "888876543", "Prep", 114)
INSERT INTO ChefInfo VALUES(226, "Edmun", "Gordon", "888887654", "Sous", 115)
INSERT INTO ChefInfo VALUES(228, "Essa", "Cantrell", "888888765", "Prep", 115)