USE transportPublic;

DROP PROCEDURE IF EXISTS controleDate;
DELIMITER $$
CREATE PROCEDURE controleDate(dateDebut DATE, dateFin DATE)
BEGIN
	DECLARE err_msg VARCHAR(255) DEFAULT 'ERROR dateDebut est superieur a dateFin\n';
    IF (dateDebut > dateFin) THEN
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = err_msg;
	END IF;
END
$$
DELIMITER ;


DROP TRIGGER IF EXISTS date_bus_mecanicien;
DELIMITER $$
CREATE TRIGGER date_bus_mecanicien 
BEFORE INSERT ON bus_mecanicien
FOR EACH ROW 
BEGIN
	CALL controleDate(NEW.dateDebut, NEW.dateFin);
END
$$
DELIMITER ;