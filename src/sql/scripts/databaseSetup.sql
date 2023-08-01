CREATE TABLE Menu
(name		VARCHAR(50)	PRIMARY KEY,
 cost		FLOAT,
 category	VARCHAR(50)	NOT NULL
)

CREATE TABLE Ingredients
(name			VARCHAR(50)	PRIMARY KEY,
 storageMethod	VARCHAR(50)
)

CREATE TABLE IngredientsRequired
(menuName		VARCHAR(50),
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

CREATE TABLE Customers
(cID		INTEGER	PRIMARY KEY,
 email		VARCHAR(50)	NOT NULL,
 phoneNum	VARCHAR(10)	NOT NULL,
 points		INTEGER	NOT NULL,
 firstName	VARCHAR(50),
 lastName	VARCHAR(50),
 dob		DATE,
 UNIQUE (email, phoneNum)
)

CREATE TABLE MembershipsSubscribes
(cID	INTEGER,
 tiers	VARCHAR(50),
 PRIMARY KEY (cID, tiers),
 FOREIGN KEY (cID)
     REFERENCES Customers (cID)
     ON DELETE CASCADE
     ON UPDATE CASCADE
)

CREATE TABLE Branches
(locID			INTEGER	PRIMARY KEY,
 streetAddress		VARCHAR(50),
 city			VARCHAR(50),
 province		VARCHAR(50)
)

CREATE TABLE MenuServed
(menuName	VARCHAR(50),
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

CREATE TABLE WaitersTableSection
(tableSection	VARCHAR(50)	PRIMARY KEY,
 wages		FLOAT
)

CREATE TABLE WaitersInfo
(wID		INTEGER	PRIMARY KEY,
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

CREATE TABLE ChefSpecialty(
                              specialty	VARCHAR(50),
                              wages		FLOAT,
                              PRIMARY KEY (specialty)
)

CREATE TABLE ChefInfo(
                         wID		INTEGER	PRIMARY KEY,
                         firstName	VARCHAR(50),
                         lastName	VARCHAR(50),
                         SIN		CHAR(9)	NOT NULL,
                         specialty	VARCHAR(50),
                         locID		INTEGER,
                         UNIQUE (SIN),
                         FOREIGN KEY (specialty)
                             REFERENCES ChefSpecialty(specialty)
                             ON DELETE SET DEFAULT
                             ON UPDATE CASCADE,
                         FOREIGN KEY (locID)
                             REFERENCES Branches (locID)
                             ON DELETE SET NULL
                             ON UPDATE CASCADE
)

CREATE TABLE Hosts(
                      wID 		INTEGER 	PRIMARY KEY,
                      firstName 	VARCHAR(50),
                      lastName 	VARCHAR(50),
                      wages 		FLOAT,
                      SIN 		CHAR(9)	NOT NULL,
                      locID 		INTEGER,
                      UNIQUE (SIN),
                      FOREIGN KEY (locID)
                          REFERENCES Branches (locID)
                          ON DELETE SET NULL
                          ON UPDATE CASCADE
)

CREATE TABLE Manager(
                        wID		INTEGER	PRIMARY KEY,
                        firstName	VARCHAR(50),
                        lastName	VARCHAR(50),
                        SIN		CHAR(9)	NOT NULL,
                        rating		FLOAT,
                        locID		INTEGER,
                        UNIQUE (SIN),
                        FOREIGN KEY (locID)
                            REFERENCES Branches (locID)
                            ON DELETE SET NULL
                            ON UPDATE CASCADE
)

CREATE TABLE EquipmentsName(
                               name 		VARCHAR(50) 	PRIMARY KEY,
                               category 	VARCHAR(50),
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
                                   FOREIGN KEY (locID),
                                   REFERENCES Branches (locID)
                                       ON DELETE CASCADE
                                       ON UPDATE CASCADE,
                                   FOREIGN KEY (equipID)
                                       REFERENCES EquipmentMain(id)
                                       ON DELETE CASCADE
                                       ON UPDATE CASCADE
)

CREATE TABLE RESERVATIONS(
                             rID 			INTEGER,
                             cID			INTEGER	NOT NULL,
                             locID 			INTEGER	NOT NULL,
                             wID			INTEGER	NOT NULL,
                             date			DATE,
                             time			TIME,
                             numOfPeople		INTEGER,
                             reservationName	CHAR(50),
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

INSERT INTO Branches VALUES(111, "792 Montreal Road", "Ottawa", "Ontario")
INSERT INTO Branches VALUES(112, "1257 Beaver Creek", "Thornhill", "Ontario")
INSERT INTO Branches VALUES(113, "792 Montreal Road", "Vancouver", "British Columbia")
INSERT INTO Branches VALUES(114, "1221 Robson Street", "Vancouver", "British Columbia")
INSERT INTO Branches VALUES(115, "4795 Robson Street", "Vancouver", "British Columbia")