/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.domain.Personnel;
import gza.transportPublic.domain.Categorie;
import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.IPersonnelMapper;
import gza.transportPublic.idatasource.exceptions.EntiteInconnuePersistenceException;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IPersonnel;
import gza.transportPublic.idomain.IPersonnel;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZEED
 */
public class PersonnelMapper extends EntiteMapper<IPersonnel> implements IPersonnelMapper {

    public static String CREATE_TABLE = "CREATE TABLE personnel(\n"
            + "	pk_personnel SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,\n"
            + "    nom varchar(255) NOT NULL,\n"
            + "    prenom varchar(255) NOT NULL,\n"
            + "    dateEmbauche varchar(255) NOT NULL,\n"
            + "    pourcentage int(3) NOT NULL CHECK( pourcentage >= 0 AND pourcentage <= 100),\n"
            + "    CONSTRAINT ct_pk_personnel PRIMARY KEY (pk_personnel)\n"
            + ");";

    public static String DROP_TABLE = "DROP TABLE personnel ";

    public static final String SQL_INSERT = "INSERT INTO personnel "
            + "(pk_personnel,nom, prenom, dateEmbauche, pourcentage)"
            + " VALUES (?, ?, ?, ?, ?)";

    public static final String SQL_UPDATE_BY_ID = "UPDATE personnel "
            + "SET "
            + "nom = ?, "
            + "prenom = ?, "
            + "dateEmbauche = ?, "
            + "pourcentage = ? "
            + "WHERE pk_personnel = ? ";

    public static final String SQL_DELETE_BY_ID = "DELETE FROM personnel "
            + "WHERE pk_personnel = ?";

    public static final String SQL_SELECT_ALL = "SELECT pk_personnel,nom, prenom,dateEmbauche, pourcentage "
            + "FROM personnel ";

    public static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE pk_personnel = ? ";

    public static final String SQL_SELECT_BY_ID_FOR_UPDATE = SQL_SELECT_BY_ID
            + " FOR UPDATE";

    public static final String SQL_SELECT = SQL_SELECT_ALL
            + " WHERE pk_personnel = ? OR UPPER(nom) LIKE UPPER(?) OR UPPER(prenom) LIKE UPPER(?)"
            + " ORDER BY pk_personnel ";
    public static final String SQL_SELECT_MAX = "SELECT MAX(pk_personnel) FROM personnel ";

    public static final String TABLE_ATTRIBUT_CLE = "pk_personnel";
    public static final String TABLE_ATTRIBUT_NOM = "nom";
    public static final String TABLE_ATTRIBUT_PRENOM = "prenom";
    public static final String TABLE_ATTRIBUT_DATEEMBAUCHE = "dateEmbauche";
    public static final String TABLE_ATTRIBUT_POURCENTAGE = "pourcentage";

    public PersonnelMapper(DbMapperManager manager) {
        super(manager);
        this.sqlSelectMaxStr = SQL_SELECT_MAX;
        this.sqlSelectByIdStr = SQL_SELECT_BY_ID;
        this.sqlSelectByIdForUpdateStr = SQL_SELECT_BY_ID_FOR_UPDATE;
        this.sqlDeleteStr = SQL_DELETE_BY_ID;
    }

    @Override
    protected IPersonnel createEntite(ResultSet result) throws PersistenceException {
        IPersonnel personnel;
        try {

            long id = result.getLong(TABLE_ATTRIBUT_CLE);

            String nom = result.getString(TABLE_ATTRIBUT_NOM);
            String prenom = result.getString(TABLE_ATTRIBUT_PRENOM);
            Date dateEmbauche = result.getDate(TABLE_ATTRIBUT_DATEEMBAUCHE);
            int pourcentage = result.getInt(TABLE_ATTRIBUT_POURCENTAGE);
            
            personnel = Personnel.builder()
                    .id(id)
                    .nom(nom)
                    .prenom(prenom)
                    .dateEmbauche(dateEmbauche)
                    .pourcentage(pourcentage)
                    .build();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return personnel;
    }

    @Override
    protected void verifierEntiteUtilisee(long id) throws PersistenceException {

    }

    @Override
    public IPersonnel create(IPersonnel e) throws PersistenceException {
        IPersonnel entite;
        try {
            long id = getNewId();
            entite = Personnel.builder().id(id)
                    .nom(e.getNom())
                    .prenom(e.getPrenom())
                    .dateEmbauche(e.getDateEmbauche())
                    .pourcentage(e.getPourcentage())
                    .build();

 
            PreparedStatement statement = this.mapperManager.getConnection().prepareStatement(SQL_INSERT);
            statement.setLong(1, entite.getId());
            statement.setString(2, entite.getNom());
            statement.setString(3, entite.getPrenom());
            statement.setDate(4, new java.sql.Date(entite.getDateEmbauche().getTime()));
            statement.setInt(5, entite.getPourcentage());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return entite;
    }

    /*  @Override
    public IPersonnel create() throws PersistenceException {
        return this.create(Personnel.builder().build());
    }*/
    @Override
    public List<IPersonnel> retreave(String recherche) throws PersistenceException {
        List<IPersonnel> list = new ArrayList<IPersonnel>();
        try {
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_SELECT);
            try {
                statement.setLong(1, new Long(recherche));
            } catch (NumberFormatException ex) {
                //ne fait rien
                statement.setNull(1, Types.NUMERIC);
            }
            statement.setString(2, "%" + recherche + "%");
            statement.setString(3, "%" + recherche + "%");
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    IPersonnel entite = this.createEntite(result);
                    list.add(entite);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return list;
    }

    @Override
    public void update(IPersonnel e) throws PersistenceException {

        IPersonnel personnel = this.retreaveForUpdate(e.getId());
        if (personnel == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("ERREUR_Personnel_INCONNU %s", e));
        }
        try {
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_UPDATE_BY_ID);            
            
            statement.setString(1, e.getNom());
            statement.setString(2, e.getPrenom());
            statement.setDate(3, new java.sql.Date(e.getDateEmbauche().getTime()));
            statement.setInt(4, e.getPourcentage());
            statement.setLong(5, e.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {

            throw new PersistenceException(ex);
        }
    }

}
