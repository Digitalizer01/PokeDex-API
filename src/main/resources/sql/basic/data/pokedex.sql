SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS pokemon;
DROP TABLE IF EXISTS stats;
DROP TABLE IF EXISTS pokemon_type_relation;
DROP TABLE IF EXISTS pokemon_type;
DROP TABLE IF EXISTS generation;

SET FOREIGN_KEY_CHECKS = 1;


CREATE TABLE stats
(
	id int NOT NULL AUTO_INCREMENT,
	hp int NOT NULL,
	attack int NOT NULL,
	defense int NOT NULL,
	special_attack int NOT NULL,
	special_defense int NOT NULL,
	speed int NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE generation
(
	id int NOT NULL AUTO_INCREMENT,
	number int NOT NULL,
	region varchar(255) NOT NULL,
	year int NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE pokemon_type
(
	id int NOT NULL AUTO_INCREMENT,
	name varchar(255) NOT NULL UNIQUE,
	PRIMARY KEY (id)
);

CREATE TABLE pokemon_type_relation
(
	id int NOT NULL AUTO_INCREMENT,
	id_pokemon_type int NOT NULL,
	id_pokemon_related_type int NOT NULL,
    effectiveness_percentage INT NOT NULL,
    PRIMARY KEY (id),
	FOREIGN KEY (id_pokemon_type) REFERENCES pokemon_type(id),
	FOREIGN KEY (id_pokemon_related_type) REFERENCES pokemon_type(id)
);

CREATE TABLE pokemon
(
	id int NOT NULL AUTO_INCREMENT,
	id_pokedex int NOT NULL UNIQUE,
	name varchar(255) NOT NULL UNIQUE,
	id_pokemon_type1 int NOT NULL,
	id_pokemon_type2 int,
	description varchar(1000) NOT NULL,
	id_stats int NOT NULL,
	id_generation int NOT NULL,
	legendary bool NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY (id_pokemon_type1) REFERENCES pokemon_type(id),
	FOREIGN KEY (id_pokemon_type2) REFERENCES pokemon_type(id),
	FOREIGN KEY (id_stats) REFERENCES stats(id),
	FOREIGN KEY (id_generation) REFERENCES generation(id)
);

INSERT INTO pokedex.generation (number, region, year)
VALUES (1, 'Kanto', 1996);

INSERT INTO pokedex.generation (number, region, year)
VALUES (2, 'Johto', 1999);

INSERT INTO pokedex.generation (number, region, year)
VALUES (3, 'Hoenn', 2002),
       (3, 'Kanto', 2004);

INSERT INTO pokedex.generation (number, region, year)
VALUES (4, 'Sinnoh', 2006),
       (4, 'Johto', 2009);

INSERT INTO pokedex.generation (number, region, year)
VALUES (5, 'Unova', 2010),
       (5, 'Hoenn', 2014);

INSERT INTO pokedex.generation (number, region, year)
VALUES (6, 'Kalos', 2013),
       (6, 'Kanto', 2018); 

INSERT INTO pokedex.generation (number, region, year)
VALUES (7, 'Alola', 2016);

INSERT INTO pokedex.generation (number, region, year)
VALUES (8, 'Galar', 2019),
       (8, 'Sinnoh', 2022);


INSERT INTO pokedex.pokemon_type
(name)
VALUES
('Normal'),
('Fire'),
('Water'),
('Electric'),
('Grass'),
('Ice'),
('Fighting'),
('Poison'),
('Ground'),
('Flying'),
('Phychic'),
('Bug'),
('Rock'),
('Ghost'),
('Dragon'),
('Dark'),
('Steel'),
('Fairy');

INSERT INTO pokedex.pokemon_type_relation 
(id_pokemon_type, id_pokemon_related_type, effectiveness_percentage)
VALUES
-- NORMAL
(1, 1, 100), -- Normal
(1, 2, 100), -- Fire
(1, 3, 100), -- Water
(1, 4, 100), -- Electric
(1, 5, 100), -- Grass
(1, 6, 100), -- Ice
(1, 7, 100), -- Fighting
(1, 8, 100), -- Poison
(1, 9, 100), -- Ground
(1, 10, 100), -- Flying
(1, 11, 100), -- Phychic
(1, 12, 100), -- Bug
(1, 13, 50), -- Rock
(1, 14, 0), -- Ghost
(1, 15, 100), -- Dragon
(1, 16, 100), -- Dark
(1, 17, 50), -- Steel
(1, 18, 100), -- Fairy

-- FIRE
(2, 1, 100), -- Normal
(2, 2, 50), -- Fire
(2, 3, 50), -- Water
(2, 4, 100), -- Electric
(2, 5, 200), -- Grass
(2, 6, 200), -- Ice
(2, 7, 100), -- Fighting
(2, 8, 100), -- Poison
(2, 9, 100), -- Ground
(2, 10, 100), -- Flying
(2, 11, 100), -- Phychic
(2, 12, 200), -- Bug
(2, 13, 50), -- Rock
(2, 14, 100), -- Ghost
(2, 15, 50), -- Dragon
(2, 16, 100), -- Dark
(2, 17, 200), -- Steel
(2, 18, 100), -- Fairy

-- WATER
(3, 1, 100), -- Normal
(3, 2, 200), -- Fire
(3, 3, 50), -- Water
(3, 4, 100), -- Electric
(3, 5, 200), -- Grass
(3, 6, 100), -- Ice
(3, 7, 100), -- Fighting
(3, 8, 100), -- Poison
(3, 9, 200), -- Ground
(3, 10, 100), -- Flying
(3, 11, 100), -- Phychic
(3, 12, 100), -- Bug
(3, 13, 200), -- Rock
(3, 14, 100), -- Ghost
(3, 15, 50), -- Dragon
(3, 16, 100), -- Dark
(3, 17, 100), -- Steel
(3, 18, 100), -- Fairy

-- ELECTRIC
(4, 1, 100), -- Normal
(4, 2, 100), -- Fire
(4, 3, 200), -- Water
(4, 4, 50), -- Electric
(4, 5, 50), -- Grass
(4, 6, 100), -- Ice
(4, 7, 100), -- Fighting
(4, 8, 100), -- Poison
(4, 9, 0), -- Ground
(4, 10, 200), -- Flying
(4, 11, 100), -- Phychic
(4, 12, 100), -- Bug
(4, 13, 100), -- Rock
(4, 14, 100), -- Ghost
(4, 15, 50), -- Dragon
(4, 16, 100), -- Dark
(4, 17, 100), -- Steel
(4, 18, 100), -- Fairy

-- GRASS
(5, 1, 100), -- Normal
(5, 2, 50), -- Fire
(5, 3, 200), -- Water
(5, 4, 100), -- Electric
(5, 5, 50), -- Grass
(5, 6, 100), -- Ice
(5, 7, 100), -- Fighting
(5, 8, 50), -- Poison
(5, 9, 200), -- Ground
(5, 10, 50), -- Flying
(5, 11, 100), -- Phychic
(5, 12, 50), -- Bug
(5, 13, 200), -- Rock
(5, 14, 100), -- Ghost
(5, 15, 50), -- Dragon
(5, 16, 100), -- Dark
(5, 17, 50), -- Steel
(5, 18, 100), -- Fairy

-- ICE
(6, 1, 100), -- Normal
(6, 2, 50), -- Fire
(6, 3, 50), -- Water
(6, 4, 100), -- Electric
(6, 5, 200), -- Grass
(6, 6, 50), -- Ice
(6, 7, 100), -- Fighting
(6, 8, 100), -- Poison
(6, 9, 200), -- Ground
(6, 10, 200), -- Flying
(6, 11, 100), -- Phychic
(6, 12, 100), -- Bug
(6, 13, 100), -- Rock
(6, 14, 100), -- Ghost
(6, 15, 200), -- Dragon
(6, 16, 100), -- Dark
(6, 17, 50), -- Steel
(6, 18, 100), -- Fairy

-- FIGHTING
(7, 1, 200), -- Normal
(7, 2, 100), -- Fire
(7, 3, 100), -- Water
(7, 4, 100), -- Electric
(7, 5, 100), -- Grass
(7, 6, 200), -- Ice
(7, 7, 100), -- Fighting
(7, 8, 50), -- Poison
(7, 9, 100), -- Ground
(7, 10, 50), -- Flying
(7, 11, 50), -- Phychic
(7, 12, 50), -- Bug
(7, 13, 200), -- Rock
(7, 14, 0), -- Ghost
(7, 15, 100), -- Dragon
(7, 16, 200), -- Dark
(7, 17, 200), -- Steel
(7, 18, 50), -- Fairy

-- POISON
(8, 1, 100), -- Normal
(8, 2, 100), -- Fire
(8, 3, 100), -- Water
(8, 4, 100), -- Electric
(8, 5, 200), -- Grass
(8, 6, 100), -- Ice
(8, 7, 100), -- Fighting
(8, 8, 50), -- Poison
(8, 9, 50), -- Ground
(8, 10, 100), -- Flying
(8, 11, 100), -- Phychic
(8, 12, 100), -- Bug
(8, 13, 50), -- Rock
(8, 14, 50), -- Ghost
(8, 15, 100), -- Dragon
(8, 16, 100), -- Dark
(8, 17, 0), -- Steel
(8, 18, 200), -- Fairy

-- GROUND
(9, 1, 100), -- Normal
(9, 2, 200), -- Fire
(9, 3, 100), -- Water
(9, 4, 200), -- Electric
(9, 5, 50), -- Grass
(9, 6, 100), -- Ice
(9, 7, 100), -- Fighting
(9, 8, 200), -- Poison
(9, 9, 100), -- Ground
(9, 10, 0), -- Flying
(9, 11, 100), -- Phychic
(9, 12, 50), -- Bug
(9, 13, 200), -- Rock
(9, 14, 100), -- Ghost
(9, 15, 100), -- Dragon
(9, 16, 100), -- Dark
(9, 17, 200), -- Steel
(9, 18, 100), -- Fairy

-- FLYING
(10, 1, 100), -- Normal
(10, 2, 100), -- Fire
(10, 3, 100), -- Water
(10, 4, 50), -- Electric
(10, 5, 200), -- Grass
(10, 6, 100), -- Ice
(10, 7, 200), -- Fighting
(10, 8, 100), -- Poison
(10, 9, 100), -- Ground
(10, 10, 100), -- Flying
(10, 11, 100), -- Phychic
(10, 12, 200), -- Bug
(10, 13, 50), -- Rock
(10, 14, 100), -- Ghost
(10, 15, 100), -- Dragon
(10, 16, 100), -- Dark
(10, 17, 50), -- Steel
(10, 18, 100), -- Fairy

-- PHYSIC
(11, 1, 100), -- Normal
(11, 2, 100), -- Fire
(11, 3, 100), -- Water
(11, 4, 100), -- Electric
(11, 5, 100), -- Grass
(11, 6, 100), -- Ice
(11, 7, 200), -- Fighting
(11, 8, 200), -- Poison
(11, 9, 100), -- Ground
(11, 10, 100), -- Flying
(11, 11, 50), -- Phychic
(11, 12, 100), -- Bug
(11, 13, 100), -- Rock
(11, 14, 100), -- Ghost
(11, 15, 100), -- Dragon
(11, 16, 0), -- Dark
(11, 17, 50), -- Steel
(11, 18, 100), -- Fairy

-- BUG
(12, 1, 100), -- Normal
(12, 2, 50), -- Fire
(12, 3, 100), -- Water
(12, 4, 100), -- Electric
(12, 5, 200), -- Grass
(12, 6, 100), -- Ice
(12, 7, 50), -- Fighting
(12, 8, 50), -- Poison
(12, 9, 100), -- Ground
(12, 10, 50), -- Flying
(12, 11, 200), -- Phychic
(12, 12, 100), -- Bug
(12, 13, 100), -- Rock
(12, 14, 50), -- Ghost
(12, 15, 100), -- Dragon
(12, 16, 200), -- Dark
(12, 17, 50), -- Steel
(12, 18, 50), -- Fairy

-- ROCK
(13, 1, 100), -- Normal
(13, 2, 200), -- Fire
(13, 3, 100), -- Water
(13, 4, 100), -- Electric
(13, 5, 100), -- Grass
(13, 6, 200), -- Ice
(13, 7, 50), -- Fighting
(13, 8, 100), -- Poison
(13, 9, 50), -- Ground
(13, 10, 200), -- Flying
(13, 11, 100), -- Phychic
(13, 12, 200), -- Bug
(13, 13, 100), -- Rock
(13, 14, 100), -- Ghost
(13, 15, 100), -- Dragon
(13, 16, 100), -- Dark
(13, 17, 50), -- Steel
(13, 18, 100), -- Fairy

-- GHOST
(14, 1, 0), -- Normal
(14, 2, 100), -- Fire
(14, 3, 100), -- Water
(14, 4, 100), -- Electric
(14, 5, 100), -- Grass
(14, 6, 100), -- Ice
(14, 7, 100), -- Fighting
(14, 8, 100), -- Poison
(14, 9, 100), -- Ground
(14, 10, 100), -- Flying
(14, 11, 200), -- Phychic
(14, 12, 100), -- Bug
(14, 13, 100), -- Rock
(14, 14, 200), -- Ghost
(14, 15, 100), -- Dragon
(14, 16, 50), -- Dark
(14, 17, 100), -- Steel
(14, 18, 100), -- Fairy

-- DRAGON
(15, 1, 100), -- Normal
(15, 2, 100), -- Fire
(15, 3, 100), -- Water
(15, 4, 100), -- Electric
(15, 5, 100), -- Grass
(15, 6, 100), -- Ice
(15, 7, 100), -- Fighting
(15, 8, 100), -- Poison
(15, 9, 100), -- Ground
(15, 10, 100), -- Flying
(15, 11, 100), -- Phychic
(15, 12, 100), -- Bug
(15, 13, 100), -- Rock
(15, 14, 100), -- Ghost
(15, 15, 200), -- Dragon
(15, 16, 100), -- Dark
(15, 17, 50), -- Steel
(15, 18, 0), -- Fairy

-- DARK
(16, 1, 100), -- Normal
(16, 2, 100), -- Fire
(16, 3, 100), -- Water
(16, 4, 100), -- Electric
(16, 5, 100), -- Grass
(16, 6, 100), -- Ice
(16, 7, 50), -- Fighting
(16, 8, 100), -- Poison
(16, 9, 100), -- Ground
(16, 10, 100), -- Flying
(16, 11, 200), -- Phychic
(16, 12, 100), -- Bug
(16, 13, 100), -- Rock
(16, 14, 200), -- Ghost
(16, 15, 100), -- Dragon
(16, 16, 50), -- Dark
(16, 17, 100), -- Steel
(16, 18, 50), -- Fairy

-- STEEL
(17, 1, 100), -- Normal
(17, 2, 50), -- Fire
(17, 3, 50), -- Water
(17, 4, 50), -- Electric
(17, 5, 100), -- Grass
(17, 6, 200), -- Ice
(17, 7, 100), -- Fighting
(17, 8, 100), -- Poison
(17, 9, 100), -- Ground
(17, 10, 100), -- Flying
(17, 11, 100), -- Phychic
(17, 12, 100), -- Bug
(17, 13, 200), -- Rock
(17, 14, 100), -- Ghost
(17, 15, 100), -- Dragon
(17, 16, 100), -- Dark
(17, 17, 50), -- Steel
(17, 18, 200), -- Fairy

-- FAIRY
(18, 1, 100), -- Normal
(18, 2, 50), -- Fire
(18, 3, 100), -- Water
(18, 4, 100), -- Electric
(18, 5, 100), -- Grass
(18, 6, 100), -- Ice
(18, 7, 200), -- Fighting
(18, 8, 50), -- Poison
(18, 9, 100), -- Ground
(18, 10, 100), -- Flying
(18, 11, 100), -- Phychic
(18, 12, 100), -- Bug
(18, 13, 100), -- Rock
(18, 14, 100), -- Ghost
(18, 15, 200), -- Dragon
(18, 16, 200), -- Dark
(18, 17, 50), -- Steel
(18, 18, 100) -- Fairy

;