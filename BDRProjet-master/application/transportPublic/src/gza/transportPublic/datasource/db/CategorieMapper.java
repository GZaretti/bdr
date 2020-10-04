/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.domain.Categorie;
import gza.transportPublic.idatasource.ICategorieMapper;
import gza.transportPublic.idatasource.ICategorieMapper;
import gza.transportPublic.idatasource.exceptions.EntiteInconnuePersistenceException;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.ICategorie;
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
public class CategorieMapper extends EntiteMapper<ICategorie> implements ICategorieMapper {

    public static String CREATE_TABLE = ""
            + "CREATE TABLE categorie(\n"
            + "	pk_categorie SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,\n"
            + " nom varchar(255) NOT NULL UNIQUE,\n"
            + " CONSTRAINT ct_pk_categorie PRIMARY KEY(pk_categorie)\n"
            + ");";
    


    public static String DROP_TABLE = "DROP TABLE categorie";

    public static final String SQL_INSERT = "INSERT INTO categorie "
            + "(pk_categorie,nom)"
            + " VALUES (?, ?)";

    public static final String SQL_UPDATE_BY_ID = "UPDATE categorie "
            + "SET nom = ?"
            + "WHERE pk_categorie = ?";

    public static final String SQL_DELETE_BY_ID = "DELETE FROM categorie "
            + "WHERE pk_categorie = ?";

    public static final String SQL_SELECT_ALL = "SELECT pk_categorie,nom"
            + " FROM categorie ";

    public static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE pk_categorie = ?";

    public static final String SQL_SELECT_BY_ID_FOR_UPDATE = SQL_SELECT_BY_ID
            + " FOR UPDATE";

    public static final String SQL_SELECT = SQL_SELECT_ALL
            + " WHERE pk_categorie = ? OR UPPER(nom) LIKE UPPER(?) "
            + " ORDER BY pk_categorie ";
    public static final String SQL_SELECT_MAX = "SELECT MAX(pk_categorie) FROM categorie";

    public static final String TABLE_ATTRIBUT_CLE = "pk_categorie";
    public static final String TABLE_ATTRIBUT_NOM = "nom";

    public CategorieMapper(DbMapperManager manager) {
        super(manager);
        this.sqlSelectMaxStr = SQL_SELECT_MAX;
        this.sqlSelectByIdStr = SQL_SELECT_BY_ID;
        this.sqlSelectByIdForUpdateStr = SQL_SELECT_BY_ID_FOR_UPDATE;
        this.sqlDeleteStr = SQL_DELETE_BY_ID;
    }

    @Override
    protected ICategorie createEntite(ResultSet result) throws PersistenceException {
        ICategorie categorie;
        try {
            long id = result.getLong(TABLE_ATTRIBUT_CLE);
            String nom = result.getString(TABLE_ATTRIBUT_NOM);

            categorie = Categorie.builder().id(id)
                    .nom(nom)
                    .build();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return categorie;
    }

    @Override
    protected void verifierEntiteUtilisee(long id) throws PersistenceException {

    }

    @Override
    public ICategorie create(ICategorie e) throws PersistenceException {
        ICategorie entite;
        try {
            long id = getNewId();
            entite = Categorie.builder().id(id)
                    .nom(e.getNom())
                    .build();

            PreparedStatement statement = this.mapperManager.getConnection().prepareStatement(SQL_INSERT);
            statement.setLong(1, entite.getId());
            statement.setString(2, entite.getNom());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return entite;
    }

  /*  @Override
    public ICategorie create() throws PersistenceException {
        return this.create(Categorie.builder().build());
    }*/

    @Override
    public List<ICategorie> retreave(String recherche) throws PersistenceException {
        List<ICategorie> list = new ArrayList<ICategorie>();
        try {
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_SELECT);

            try {
                statement.setLong(1, new Long(recherche));
            } catch (NumberFormatException ex) {
                //ne fait rien
                statement.setNull(1, Types.NUMERIC);
            }
            statement.setString(2, "%" + recherche + "%");

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    ICategorie entite = this.createEntite(result);
                    list.add(entite);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return list;
    }

    @Override
    public void update(ICategorie e) throws PersistenceException {

        ICategorie categorie = this.retreaveForUpdate(e.getId());
        if (categorie == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("ERREUR_Categorie_INCONNU %s", e));
        }

        try {
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_UPDATE_BY_ID);
            statement.setString(1, e.getNom());
            statement.setLong(2, e.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {

            throw new PersistenceException(ex);
        }
    }

}
