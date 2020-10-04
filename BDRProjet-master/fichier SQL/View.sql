use transportpublic;
DROP VIEW IF EXISTS bus_en_reparation_view;
DELIMITER $$
CREATE VIEW bus_en_reparation_view AS 
	SELECT BU.noMatricule, CA.categorie, PE.nom, PE.prenom, ME.nom, BUME.dateDebut, BUME.dateFin 
	FROM bus BU
	INNER JOIN modele MO ON BU.fk_modele = MO.pk_modele	
    INNER JOIN categorie CA on CA.pk_categorie = BU.fk_categorie
    INNER JOIN bus_mecanicien BUME ON BUME.pk_fk_bus = B.pk_bus
    INNER JOIN mecanicien ME ON ME.pk_fk_mecanicien = BUME.pk_fk_mecanicien
    INNER JOIN personnel PE ON PE.pk_personnel = ME.pk_fk_mecanicien
    WHERE BUME.dateFin is null OR BUME.dateFin > now();
$$ 
DELIMITER ;

DROP VIEW IF EXISTS chauffeur_bus_view;
DELIMITER $$
CREATE VIEW chauffeur_bus_view AS 
	SELECT PE.nom, PE.prenom, CH.noPermis, BU.noMatricule, CA.nom, MO.nom
	FROM bus BU
	INNER JOIN modele MO ON  BU.fk_modele = MO.pk_modele	
    INNER JOIN categorie CA on CA.pk_categorie = BU.fk_categorie
    INNER JOIN chauffeur_categorie CHCA on CHCA.pk_fk_categorie = BU.fk_categorie
    INNER JOIN chauffeur CH ON CH.pk_fk_chauffeur = CHCA.pk_fk_chauffeur
    INNER JOIN personnel PE ON PE.pk_personnel = CH.pk_fk_chauffeur;
$$ 
DELIMITER ;

DROP VIEW IF EXISTS depot_view;
DELIMITER $$
CREATE VIEW depot_view AS 
	SELECT DE.nom, BU.noMatricule, BU.nbPassagerMax, MO.modele, CA.nom, EQ.nom, EQ.description
	FROM bus BU
	INNER JOIN modele MO ON  BU.fk_modele = MO.pk_modele	
    INNER JOIN categorie CA on CA.pk_categorie = BU.fk_categorie
    INNER JOIN place PL ON PL.pk_place = BU.fk_place
    INNER JOIN depot DE ON DE.pk_depot = PL.fk_depot
    INNER JOIN bus_equipement BUEQ ON BUEQ.pk_fk_bus = BU.pk_bus
    INNER JOIN equipement EQ ON EQ.pk_equipement = BUEQ.pk_fk_equipement;
$$ 
DELIMITER ;

DROP VIEW IF EXISTS formation_view;
DELIMITER $$
CREATE VIEW formation_view AS 
	SELECT PE.nom, PE.prenom, CH.noPermis,CH.estFormateur, PEE.nom, PEE.prenom
	FROM bus BU
    INNER JOIN chauffeur CH ON CH.pk_fk_chauffeur = CHCA.pk_fk_chauffeur
    INNER JOIN personnel PE ON PE.pk_personnel = CH.pk_fk_chauffeur
    INNER JOIN chauffeur_apprenti CHAP ON CHAP.pk_fk_chauffeur = CH.pk_fk_chauffeur
    INNER JOIN apprenti AP ON AP.pk_fk_apprenti = CHAP.pk_fk_apprenti
    INNER JOIN personnel PEE ON PEE.pk_personnel = AP.pk_fk_apprenti;
$$ 
DELIMITER ;
