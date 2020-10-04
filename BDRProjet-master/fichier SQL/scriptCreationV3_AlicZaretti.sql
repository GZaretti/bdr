-- transportPublic - script de création des tables et contraintes d'intégrité
-- Version 1 - MAJ 24.11.2017
-- 16.11.2017
-- Guillaume Zaretti et Nair Alic


DROP SCHEMA if exists transportPublic;
CREATE SCHEMA transportPublic;
use transportPublic;

--
-- Table clientt
--
CREATE TABLE clientt (
	pk_client SMALLINT UNSIGNED AUTO_INCREMENT,
    nom varchar(255)  NOT NULL,
    prenom varchar(255) NOT NULL,
    titre varchar(255) NOT NULL,
    adresseMail varchar(255),
    noTelephone varchar(255),
    nomSociete varchar(255),
    CONSTRAINT ct_pk_client PRIMARY KEY(pk_client)
);

--
-- Table reservation
--
CREATE TABLE reservation(
	pk_reservation SMALLINT UNSIGNED AUTO_INCREMENT,
    fk_zone SMALLINT UNSIGNED NOT NULL,
    fk_client SMALLINT UNSIGNED NOT NULL,
    dateDebut date NOT NULL,
    dateFin date NOT NULL,
    prixLocationJour float NOT NULL CHECK( prixLocationJour >= 0),
    CONSTRAINT ct_pk_reservation PRIMARY KEY(pk_reservation)
);

--
-- Table personnel
--
CREATE TABLE personnel(
	pk_personnel SMALLINT UNSIGNED AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    prenom varchar(255) NOT NULL,
    dateEmbauche varchar(255) NOT NULL,
    pourcentage int(3) NOT NULL CHECK( pourcentage >= 0 AND pourcentage <= 100),
    CONSTRAINT ct_pk_personnel PRIMARY KEY (pk_personnel)
);

--
-- Table depot
--
CREATE TABLE depot(
	pk_depot SMALLINT UNSIGNED AUTO_INCREMENT,
	nom varchar(255) NOT NULL,
    adresse_rue varchar(255) NOT NULL,
    adresse_no varchar(255)NOT NULL,
    adresse_NPA varchar(255)NOT NULL,
    adresse_ville varchar(255)NOT NULL,
    CONSTRAINT ct_pk_depot PRIMARY KEY(pk_depot)
);

--
-- Table places
--
CREATE TABLE place(
    pk_place SMALLINT UNSIGNED AUTO_INCREMENT,
	no_place varchar(255) NOT NULL,
    fk_depot SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT ct_pk_place PRIMARY KEY(pk_place)
);

--
-- Table modèle
--
CREATE TABLE modele(
	pk_modele SMALLINT UNSIGNED AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    CONSTRAINT ct_pk_modele PRIMARY KEY(pk_modele)
);

--
-- Table zone
--
CREATE TABLE zone(
	pk_zone SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    nom varchar(255) NOT NULL,
    CONSTRAINT ct_pk_zone PRIMARY KEY(pk_zone)
);

--
-- Table nettoyeur
--
CREATE TABLE nettoyeur(
	pk_fk_nettoyeur SMALLINT UNSIGNED NOT NULL,
	prixNettoyageParBus float NOT NULL CHECK(prixNettoyageParBus >0),
    heureNettoyageParBus float NOT NULL CHECK(prixNettoyageParBus >0),
    CONSTRAINT ct_pk_pk_fk_nettoyeur PRIMARY KEY(pk_fk_nettoyeur),
    CONSTRAINT ct_fk_pk_fk_nettoyeur FOREIGN KEY (pk_fk_nettoyeur) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE

);

--
-- Table chauffeur
--
CREATE TABLE chauffeur(
	pk_fk_chauffeur SMALLINT UNSIGNED NOT NULL,
	noPermis varchar(255) NOT NULL,
    estFormateur boolean NOT NULL,
    CONSTRAINT ct_pk_pk_fk_chauffeur PRIMARY KEY(pk_fk_chauffeur),
    CONSTRAINT ct_fk_pk_fk_chauffeur FOREIGN KEY (pk_fk_chauffeur) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE
);

--
-- Table apprenti
--
CREATE TABLE apprenti(
	pk_fk_apprenti SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT ct_pk_pk_fk_apprenti PRIMARY KEY (pk_fk_apprenti),
	CONSTRAINT ct_fk_pk_fk_apprenti FOREIGN KEY (pk_fk_apprenti) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE
);

--
-- Table mecanicien
--
CREATE TABLE mecanicien(
	pk_fk_mecanicien SMALLINT UNSIGNED NOT NULL,
	noCertification varchar(255) NOT NULL,
    CONSTRAINT ct_pk_pk_fk_mecanicien PRIMARY KEY(pk_fk_mecanicien),
    CONSTRAINT ct_fk_pk_fk_mecanicien FOREIGN KEY (pk_fk_mecanicien) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE
);

--
-- Table categorie
--
CREATE TABLE categorie(
	pk_categorie SMALLINT UNSIGNED AUTO_INCREMENT,
	nom varchar(255) NOT NULL UNIQUE,
    CONSTRAINT ct_pk_categorie PRIMARY KEY(pk_categorie)
);

--
-- Table bus
--
CREATE TABLE bus(
	pk_bus SMALLINT UNSIGNED AUTO_INCREMENT,
    fk_place SMALLINT UNSIGNED NOT NULL,
    fk_categorie SMALLINT UNSIGNED NOT NULL,
    fk_modele SMALLINT UNSIGNED NOT NULL,
    noMatricule  varchar(255) NOT NULL UNIQUE,
    nbPassagerMax int(11) NOT NULL CHECK(nbPassagerMax > 0),
    pourHandicap boolean NOT NULL,
    CONSTRAINT ct_pk_bus PRIMARY KEY(pk_bus)
);

--
-- Table équipement
--
CREATE TABLE equipement(
	pk_equipement SMALLINT UNSIGNED AUTO_INCREMENT,
    nom  varchar(255) NOT NULL,
    description  TEXT,
    CONSTRAINT ct_pk_equipement PRIMARY KEY(pk_equipement)
);



CREATE TABLE maintenance(
            pk_maintenance SMALLINT UNSIGNED AUTO_INCREMENT,
            fk_bus SMALLINT UNSIGNED NOT NULL,
            fk_mecanicien SMALLINT UNSIGNED NOT NULL,
            dateDebut DATE NOT NULL,
            dateFin DATE NOT NULL,
            commentaires TEXT,
            CONSTRAINT maintenance PRIMARY KEY(pk_maintenance)
);
--
-- Table repare - A CHECKER car redondant avec bus_mecanicien
--
-- CREATE TABLE repare(
	-- pk_repare SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
    -- dateDebut date NOT NULL,
    -- dateFin date NOT NULL,
    -- CONSTRAINT ct_pk_repare PRIMARY KEY(pk_repare)
-- );


-- table associative
CREATE TABLE chauffeur_categorie(
	pk_fk_chauffeur SMALLINT UNSIGNED NOT NULL,
    pk_fk_categorie SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT ct_pk_fk_chauffeur_categorie PRIMARY KEY(pk_fk_chauffeur, pk_fk_categorie)
);

CREATE TABLE reservation_personnel(
	pk_fk_personnel SMALLINT UNSIGNED NOT NULL,
    pk_fk_reservation SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT ct_pk_fk_reservation_personnel PRIMARY KEY(pk_fk_personnel, pk_fk_reservation)
);

CREATE TABLE reservation_bus(
	pk_fk_reservation SMALLINT UNSIGNED NOT NULL,
    pk_fk_bus SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT ct_pk_fk_reservation_bus PRIMARY KEY(pk_fk_reservation, pk_fk_bus)
);

CREATE TABLE chauffeur_apprenti(
	pk_fk_chauffeur SMALLINT UNSIGNED NOT NULL,
    pk_fk_apprenti SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT ct_pk_fk_chauffeur_apprenti PRIMARY KEY(pk_fk_chauffeur, pk_fk_apprenti)
);

CREATE TABLE bus_equipement(
	pk_fk_bus SMALLINT UNSIGNED NOT NULL,
    pk_fk_equipement SMALLINT UNSIGNED NOT NULL,
    CONSTRAINT ct_pk_fk_bus_equipement PRIMARY KEY(pk_fk_bus, pk_fk_equipement)
);

-- contraintes de clée étrangère

ALTER TABLE reservation 
ADD CONSTRAINT ct_fk_clienttreservation FOREIGN KEY (fk_client) REFERENCES clientt(pk_client);

ALTER TABLE reservation_personnel 
ADD CONSTRAINT ct_fk_reservation_reservation_personnel FOREIGN KEY (pk_fk_reservation) REFERENCES reservation(pk_reservation);
ALTER TABLE reservation_personnel 
ADD CONSTRAINT ct_fk_personnel_reservation_personnel FOREIGN KEY (pk_fk_personnel) REFERENCES personnel(pk_personnel);

ALTER TABLE reservation_bus
ADD CONSTRAINT ct_fk_reservation_reservation_bus FOREIGN KEY (pk_fk_reservation) REFERENCES reservation(pk_reservation);
ALTER TABLE reservation_bus 
ADD CONSTRAINT ct_fk_bus_reservation_bus FOREIGN KEY (pk_fk_bus) REFERENCES bus(pk_bus);

ALTER TABLE chauffeur_apprenti
ADD CONSTRAINT ct_pk_fk_chauffeur_chauffeur_apprenti FOREIGN KEY (pk_fk_chauffeur) REFERENCES chauffeur(pk_fk_chauffeur);
ALTER TABLE chauffeur_apprenti 
ADD CONSTRAINT ct_pk_fk_apprenti_chauffeur_apprenti FOREIGN KEY (pk_fk_apprenti) REFERENCES apprenti(pk_fk_apprenti);

ALTER TABLE chauffeur_categorie
ADD CONSTRAINT ct_pk_fk_chauffeur_chauffeur_categorie FOREIGN KEY (pk_fk_chauffeur) REFERENCES chauffeur(pk_fk_chauffeur);
ALTER TABLE chauffeur_categorie
ADD CONSTRAINT ct_pk_fk_categorie_chauffeur_categorie FOREIGN KEY (pk_fk_categorie) REFERENCES categorie(pk_categorie);

ALTER TABLE bus_equipement
ADD CONSTRAINT ct_pk_fk_bus_bus_equipement FOREIGN KEY (pk_fk_bus) REFERENCES bus(pk_bus);
ALTER TABLE bus_equipement
ADD CONSTRAINT ct_pk_fk_equipement_bus_equipement FOREIGN KEY (pk_fk_equipement) REFERENCES equipement(pk_equipement);

ALTER TABLE maintenance
ADD CONSTRAINT ct_fk_bus_maintenance FOREIGN KEY (fk_bus) REFERENCES bus(pk_bus);
ALTER TABLE maintenance
ADD CONSTRAINT ct_fk_mecanicien_maintenance FOREIGN KEY (fk_mecanicien) REFERENCES mecanicien(pk_fk_mecanicien);

ALTER TABLE place 
ADD CONSTRAINT ct_fk_depot_place FOREIGN KEY (fk_depot) REFERENCES depot(pk_depot);
ALTER TABLE bus 
ADD CONSTRAINT ct_fk_place_bus FOREIGN KEY (fk_place) REFERENCES place(pk_place);
ALTER TABLE reservation 
ADD CONSTRAINT ct_fk_zone_reservation FOREIGN KEY (fk_zone) REFERENCES zone(pk_zone);
ALTER TABLE bus 
ADD CONSTRAINT ct_fk_categorie_bus FOREIGN KEY (fk_categorie) REFERENCES categorie(pk_categorie);
ALTER TABLE bus 
ADD CONSTRAINT ct_fk_modele_bus FOREIGN KEY (fk_modele) REFERENCES modele(pk_modele);

