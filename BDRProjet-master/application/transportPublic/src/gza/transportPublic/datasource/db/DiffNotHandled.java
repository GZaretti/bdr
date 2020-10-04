/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.domain.Equipement;
import gza.transportPublic.idatasource.IEquipementMapper;
import gza.transportPublic.idatasource.IEquipementMapper;
import gza.transportPublic.idatasource.IEquipementMapper;
import gza.transportPublic.idatasource.exceptions.EntiteInconnuePersistenceException;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IEquipement;
import gza.transportPublic.idomain.IEquipement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ZEED
 */
public abstract class DiffNotHandled {

    public static String DROP_TABLE_CLIENT = "DROP TABLE clientt;\n";

    public static String CREATE_TABLE_CLIENT = "CREATE TABLE clientt (\n"
            + "	pk_client SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + "    nom varchar(255)  NOT NULL,\n"
            + "    prenom varchar(255) NOT NULL,\n"
            + "    titre varchar(255) NOT NULL,\n"
            + "    adresseMail varchar(255),\n"
            + "    noTelephone varchar(255),\n"
            + "    nomSociete varchar(255),\n"
            + "    CONSTRAINT ct_pk_client PRIMARY KEY(pk_client)\n"
            + ");";

    public static String DROP_TABLE_RESERVATION = "DROP TABLE reservation;\n";

    public static String CREATE_TABLE_RESERVATION = "CREATE TABLE reservation(\n"
            + "	pk_reservation SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + "    fk_zone SMALLINT UNSIGNED NOT NULL,\n"
            + "    fk_client SMALLINT UNSIGNED NOT NULL,\n"
            + "    dateDebut date NOT NULL,\n"
            + "    dateFin date NOT NULL,\n"
            + "    prixLocationJour float NOT NULL CHECK( prixLocationJour >= 0),\n"
            + "    CONSTRAINT ct_pk_reservation PRIMARY KEY(pk_reservation)\n"
            + ");";

    public static String DROP_TABLE_DEPOT = "DROP TABLE depot;\n";

    public static String CREATE_TABLE_DEPOT = ""
            + "CREATE TABLE depot(\n"
            + "     pk_depot SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + "     nom varchar(255) NOT NULL,\n"
            + "     adresse_rue varchar(255) NOT NULL,\n"
            + "     adresse_no varchar(255)NOT NULL,\n"
            + "     adresse_NPA varchar(255)NOT NULL,\n"
            + "     adresse_ville varchar(255)NOT NULL,\n"
            + "     CONSTRAINT ct_pk_depot PRIMARY KEY(pk_depot)\n"
            + ");\n";

    public static String DROP_TABLE_PLACE = "DROP TABLE place;\n";
    public static String CREATE_TABLE_PLACE = "CREATE TABLE place(\n"
            + "    pk_place SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + "    no_place varchar(255) NOT NULL,\n"
            + "    fk_depot SMALLINT UNSIGNED NOT NULL,\n"
            + "    CONSTRAINT ct_pk_place PRIMARY KEY(pk_place)\n"
            + ");\n";

    public static String DROP_TABLE_ZONE = "DROP TABLE zone;\n";
    public static String CREATE_TAVLE_ZONE = "CREATE TABLE zone(\n"
            + "	pk_zone SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + "    nom varchar(255) NOT NULL,\n"
            + "    CONSTRAINT ct_pk_zone PRIMARY KEY(pk_zone)\n"
            + ");";

    public static String DROP_TABLE_NETOYEUR = "DROP TABLE nettoyeur;\n";
    public static String CREATE_TABLE_NETOYEUR = "CREATE TABLE nettoyeur(\n"
            + "	pk_fk_nettoyeur SMALLINT UNSIGNED NOT NULL,\n"
            + "	prixNettoyageParBus float NOT NULL CHECK(prixNettoyageParBus >0),\n"
            + "    heureNettoyageParBus float NOT NULL CHECK(prixNettoyageParBus >0),\n"
            + "    CONSTRAINT ct_pk_pk_fk_nettoyeur PRIMARY KEY(pk_fk_nettoyeur),\n"
            + "    CONSTRAINT ct_fk_pk_fk_nettoyeur FOREIGN KEY (pk_fk_nettoyeur) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + "\n"
            + ");";

    public static String DROP_TABLE_CHAUFFEUR = "DROP TABLE chauffeur;\n";
    public static String CREATE_TABLE_CHAUFFEUR = "CREATE TABLE chauffeur(\n"
            + "	pk_fk_chauffeur SMALLINT UNSIGNED NOT NULL,\n"
            + "	noPermis varchar(255) NOT NULL,\n"
            + "    estFormateur boolean NOT NULL,\n"
            + "    CONSTRAINT ct_pk_pk_fk_chauffeur PRIMARY KEY(pk_fk_chauffeur),\n"
            + "    CONSTRAINT ct_fk_pk_fk_chauffeur FOREIGN KEY (pk_fk_chauffeur) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ");";

    public static String DROP_TABLE_APPRENTI = "DROP TABLE apprenti;\n";
    public static String CREATE_TABLE_APPRENTI = "CREATE TABLE apprenti(\n"
            + "	pk_fk_apprenti SMALLINT UNSIGNED NOT NULL,\n"
            + "    CONSTRAINT ct_pk_pk_fk_apprenti PRIMARY KEY (pk_fk_apprenti),\n"
            + "	CONSTRAINT ct_fk_pk_fk_apprenti FOREIGN KEY (pk_fk_apprenti) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ");";

    public static String DROP_TABLES_ASSOCIATIVES = "DROP TABLE chauffeur_categorie;"
            + "DROP TABLE reservation_personnel;"
            + "DROP TABLE reservation_bus;"
            + "DROP TABLE chauffeur_apprenti;"
            + "DROP TABLE bus_equipement;";
    
    public static String CREATE_TABLES_ASSOCIATIVES = "CREATE TABLE chauffeur_categorie(\n"
            + "	pk_fk_chauffeur SMALLINT UNSIGNED NOT NULL,\n"
            + "    pk_fk_categorie SMALLINT UNSIGNED NOT NULL,\n"
            + "    CONSTRAINT ct_pk_fk_chauffeur_categorie PRIMARY KEY(pk_fk_chauffeur, pk_fk_categorie)\n"
            + ");\n"
            + "\n"
            + "CREATE TABLE reservation_personnel(\n"
            + "	pk_fk_personnel SMALLINT UNSIGNED NOT NULL,\n"
            + "    pk_fk_reservation SMALLINT UNSIGNED NOT NULL,\n"
            + "    CONSTRAINT ct_pk_fk_reservation_personnel PRIMARY KEY(pk_fk_personnel, pk_fk_reservation)\n"
            + ");\n"
            + "\n"
            + "CREATE TABLE reservation_bus(\n"
            + "	pk_fk_reservation SMALLINT UNSIGNED NOT NULL,\n"
            + "    pk_fk_bus SMALLINT UNSIGNED NOT NULL,\n"
            + "    CONSTRAINT ct_pk_fk_reservation_bus PRIMARY KEY(pk_fk_reservation, pk_fk_bus)\n"
            + ");\n"
            + "\n"
            + "CREATE TABLE chauffeur_apprenti(\n"
            + "	pk_fk_chauffeur SMALLINT UNSIGNED NOT NULL,\n"
            + "    pk_fk_apprenti SMALLINT UNSIGNED NOT NULL,\n"
            + "    CONSTRAINT ct_pk_fk_chauffeur_apprenti PRIMARY KEY(pk_fk_chauffeur, pk_fk_apprenti)\n"
            + ");\n"
            + "\n"
            + "CREATE TABLE bus_equipement(\n"
            + "	pk_fk_bus SMALLINT UNSIGNED NOT NULL,\n"
            + "    pk_fk_equipement SMALLINT UNSIGNED NOT NULL,\n"
            + "    CONSTRAINT ct_pk_fk_bus_equipement PRIMARY KEY(pk_fk_bus, pk_fk_equipement)\n"
            + ");";

    public static final String SQL_INSERTS_SDATAS_DEPOT = " INSERT INTO depot(nom, adresse_rue, adresse_no, adresse_NPA, adresse_ville) VALUES\n"
            + "('Depot 1', 'SW Chap', '2800', '25', 'Delemont'),\n"
            + "('Depot 2', 'SW Chapel Hill Road', '2681', '22121', 'Zuni'),\n"
            + "('Depot 3', 'Rushwood Hwy', '1218', '77463', 'Smoot'),\n"
            + "('Depot 4', 'NW Edgewood Road', '3482', '46793', 'Atwood'),\n"
            + "('Depot 5', 'Farmview Ct', '5047', '71628', 'Eden'),\n"
            + "('Depot 6', 'West Woodside Way', '3187', '72778', 'Indian Rocks Beach'),\n"
            + "('Depot 7', 'Riverside Court', '2230', '68367', 'North Augusta');\n";

    public static final String SQL_INSERTS_SDATAS_PLACE = "INSERT INTO place(fk_depot, no_place) VALUES\n"
            + "(2,'PlaceUne'),\n"
            + "(2,'PlaceDeux'),\n"
            + "(2,'PlaceTrois'),\n"
            + "(1,'PlaceUne'),\n"
            + "(1,'PlaceDeux'),\n"
            + "(1,'PlaceTrois');\n";

}
