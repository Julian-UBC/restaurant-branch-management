-- Drop tables with CASCADE CONSTRAINTS
DROP TABLE MembershipSubscribes CASCADE CONSTRAINTS;
DROP TABLE Customers CASCADE CONSTRAINTS;
DROP TABLE MenuServed CASCADE CONSTRAINTS;
DROP TABLE IngredientsRequired CASCADE CONSTRAINTS;
DROP TABLE Ingredients CASCADE CONSTRAINTS;
DROP TABLE Menu CASCADE CONSTRAINTS;
DROP TABLE Branches CASCADE CONSTRAINTS;

--
-- 	Create Database Table
--

-- Stub for branches
CREATE TABLE Branches (
    locID INTEGER PRIMARY KEY
);

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
--        ON UPDATE CASCADE,
    FOREIGN KEY (ingredientsName)
        REFERENCES Ingredients (name)
        ON DELETE CASCADE
--        ON UPDATE CASCADE
);

-- MenuServed
CREATE TABLE MenuServed (
    menuName    VARCHAR2(50),
    locID       INTEGER,
    PRIMARY KEY (menuName, locID),
    FOREIGN KEY (menuName)
        REFERENCES Menu (name)
        ON DELETE CASCADE,
--        ON UPDATE CASCADE,
    FOREIGN KEY (locID)
        REFERENCES Branches (locID)
        ON DELETE CASCADE
--        ON UPDATE CASCADE
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
--        ON UPDATE CASCADE
);


--
-- Populate Database Table
--

-- Stub for branches
INSERT INTO Branches VALUES (111);
INSERT INTO Branches VALUES (112);
INSERT INTO Branches VALUES (113);
INSERT INTO Branches VALUES (114);
INSERT INTO Branches VALUES (115);

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