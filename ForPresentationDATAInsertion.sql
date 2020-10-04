use transportPublic_VLIGHT;

INSERT INTO categorie(pk_categorie, nom) VALUES
(1, 'D'),
(2, 'D1'),
(3, 'D1E'),
(4, 'DE');

INSERT INTO client_(pk_client, nom, prenom, titre, adresseMail, noTelephone, nomSociete) VALUES
(1, 'Irish', 'Krystin', 'Sir', 'rqzvi9410@nowhere.com', '(227) 988-9243', 'Pacific Semiconductor Inc.'),
(2, 'Sales', 'Jesusita', 'Lady', 'HeleneStine@example.com', '(301) 211-6174', 'United S-Mobile Group'),
(3, 'Tejada', 'Karleen', 'Sir', 'JoanConroy@nowhere.com', '(484) 408-8046', 'Australian 3G Wireless Corporation'),
(4, 'Barrow', 'Barabara', 'Dame', 'olet3081@nowhere.com', '(214) 069-8626', 'General Fossil Fuel Energy Co.'),
(5, 'Nunn', 'Lucius', 'Madam', 'Dove@example.com', '(985) 536-8541', 'Future Nuclear Resources Corporation'),
(6, 'Robison', 'Lorrine', 'Dame', 'JazmineLivingston786@nowhere.com', '(234) 718-0163', 'Pacific Engineering Group'),
(7, 'Lutz', 'Kristin', 'Lord', 'Anabel_LVenegas199@example.com', '(188) 525-4221', 'First Space Research Group'),
(8, 'Salgado', 'Mistie', 'Sir', 'Lamb569@example.com', '(406) 800-3452', 'Advanced Nuclear Energy Inc.'),
(9, 'Tejeda', 'Sammy', 'Dame', 'Rosendo.Morris@example.com', '(737) 888-1938', 'National Oil Corporation'),
(10, 'Carlin', 'Veta', 'Madam', 'ButcherO@example.com', '(695) 830-4717', 'East Protection Inc.');

INSERT INTO depot(pk_depot, nom, adresse_rue, adresse_no, adresse_NPA, adresse_ville) VALUES
(1, 'Depot 1', 'SW Chapel Hill Road', '2681', '22121', 'Zuni'),
(2, 'Depot 2', 'Rushwood Hwy', '1218', '77463', 'Smoot'),
(3, 'Depot 3', 'NW Edgewood Road', '3482', '46793', 'Atwood'),
(4, 'Depot 4', 'Farmview Ct', '5047', '71628', 'Eden'),
(5, 'Depot 5', 'West Woodside Way', '3187', '72778', 'Indian Rocks Beach');

INSERT INTO equipement(pk_equipement, nom, description) VALUES
(1, 'Camera', 'Camera de surveillance'),
(2, 'Camera Nocture', 'Camera de surveillance nocture'),
(3, 'Publicite', 'Affiche publicitaire'),
(4, 'TV', 'Ecran pour affichage'),
(5, 'Remorque ski', 'Remorque pour transport de ski');

INSERT INTO modele(pk_modele, nom) VALUES
(1, 'Simple'),
(2, 'Accordéon'),
(3, 'Minibus'),
(4, 'Bus scolaire'),
(5, 'Simple avec remorque');

INSERT INTO personnel(pk_personnel, nom, prenom, dateEmbauche, pourcentage) VALUES
(1, 'Hurtado', 'Mamie', '3', 80),
(2, 'Ulrich', 'Paula', '1', 90),
(3, 'Partridge', 'Kenda', '2', 90),
(4, 'Mcdonough', 'Jacklyn', '3', 100),
(5, 'Enos', 'Ayanna', '3', 100),
(6, 'Loving', 'Karyl', '3', 50),
(7, 'Hairston', 'Tomasa', '2', 60),
(8, 'Passmore', 'Arlena', '2', 70),
(9, 'Shafer', 'Sarai', '1', 80),
(10, 'Huskey', 'Douglass', '1', 90);

INSERT INTO zone(pk_zone, nom) VALUES
(1, 'Suisse Romande'),
(2, 'Suisse Allemande'),
(3, 'Europe');

INSERT INTO apprenti(pk_fk_apprenti) VALUES
(2),
(1),
(3),
(5);

INSERT INTO chauffeur(pk_fk_chauffeur, noPermis, estFormateur) VALUES
(1, '9777', 1),
(2, '4526', 1),
(3, '579', 0),
(4, '129', 0),
(5, '237753', 1);

INSERT INTO mecanicien(pk_fk_mecanicien, nom) VALUES
(6, 'Sizemore'),
(7, 'Loera'),
(8, 'Waite'),
(9, 'Register'),
(10, 'Archer');

INSERT INTO place(pk_place, fk_depot) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 3),
(6, 4),
(7, 1),
(8, 2);


INSERT INTO reservation(pk_reservation, fk_zone, fk_client, dateDebut, dateFin, prixLocationJour) VALUES
(1, 1, 1, '1979-10-25', '1991-09-23', 3584),
(2, 1, 2, '1998-10-07', '1992-04-27', 1050),
(3, 2, 3, '1971-11-23', '1977-06-07', 691),
(4, 2, 4, '1975-03-16', '1999-05-14', 1900),
(5, 3, 5, '1976-04-27', '1975-08-22', 2308);


INSERT INTO bus(pk_bus, fk_place, fk_categorie, fk_modele, noMatricule, nbPassagerMax, pourHandicap) VALUES
(1, 1, 1, 1, '742183', 69, 0),
(2, 2, 1, 1, '71147', 131, 1),
(3, 3, 2, 2, '711396191', 96, 1),
(4, 4, 2, 3, '4877', 45, 0),
(5, 5, 3, 3, '1339146161', 38, 1),
(6, 6, 3, 4, '41385', 7, 1),
(7, 7, 4, 4, '899', 44, 0),
(8, 8, 4, 3, '5696', 31, 1);

INSERT INTO bus_equipement(pk_fk_bus, pk_fk_equipement) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 3);

INSERT INTO bus_mecanicien(pk_fk_bus, pk_fk_mecanicien, dateDebut, dateFin) VALUES
(1, 6, '1970-03-29', '2012-04-28'),
(2, 6, '1984-04-23', '1983-07-15'),
(3, 7, '1970-03-09', '1983-06-06'),
(4, 8, '1984-02-24', '1996-06-25'),
(5, 9, '1995-07-12', '2000-09-28'),
(6place, 10, '1973-01-17', '1977-02-18');

INSERT INTO chauffeur_apprenti(pk_fk_chauffeur, pk_fk_apprenti) VALUES
(1, 2),
(1, 1),
(2, 1),
(3, 2),
(4, 5),
(5, 1);

INSERT INTO chauffeur_categorie(pk_fk_chauffeur, pk_fk_categorie) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 1),
(1, 2),
(2, 3),
(3, 4),
(4, 2);

INSERT INTO nettoyeur(pk_fk_nettoyeur, prixNettoyageParBus, heureNettoyageParBus) VALUES
(1, 37, 3),
(2, 10, 5),
(3, 17, 1),
(4, 19, 4),
(5, 21, 9);

INSERT INTO reservation_bus(pk_fk_reservation, pk_fk_bus) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

INSERT INTO reservation_personnel(pk_fk_personnel, pk_fk_reservation) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);


