/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IModelMapper;
import gza.transportPublic.idatasource.exceptions.EntiteInconnuePersistenceException;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IModel;
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
public class ModelMapper extends EntiteMapper<IModel> implements IModelMapper {

    public static String CREATE_TABLE = ""
            + "CREATE TABLE model(\n"
            + "	pk_model SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,\n"
            + "    nom varchar(255) NOT NULL,\n"
            + "    CONSTRAINT ct_pk_model PRIMARY KEY(pk_model)\n"
            + ");";

    public static String DROP_TABLE = "DROP TABLE model";

    public static final String SQL_INSERT = "INSERT INTO model "
            + "(pk_model,nom)"
            + " VALUES (?, ?)";

    public static final String SQL_UPDATE_BY_ID = "UPDATE model "
            + "SET NOM = ?"
            + "WHERE pk_model = ?";

    public static final String SQL_DELETE_BY_ID = "DELETE FROM model "
            + "WHERE pk_model = ?";

    public static final String SQL_SELECT_ALL = "SELECT pk_model,nom"
            + " FROM model ";

    public static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE pk_model = ?";

    public static final String SQL_SELECT_BY_ID_FOR_UPDATE = SQL_SELECT_BY_ID
            + " FOR UPDATE";

    public static final String SQL_SELECT = SQL_SELECT_ALL
            + " WHERE pk_model = ? OR UPPER(nom) LIKE UPPER(?) "
            + " ORDER BY pk_model ";
    public static final String SQL_SELECT_MAX = "SELECT MAX(pk_model) FROM model";

    public static final String TABLE_ATTRIBUT_CLE = "pk_model";
    public static final String TABLE_ATTRIBUT_NOM = "nom";

    public ModelMapper(DbMapperManager manager) {
        super(manager);
        this.sqlSelectMaxStr = SQL_SELECT_MAX;
        this.sqlSelectByIdStr = SQL_SELECT_BY_ID;
        this.sqlSelectByIdForUpdateStr = SQL_SELECT_BY_ID_FOR_UPDATE;
        this.sqlDeleteStr = SQL_DELETE_BY_ID;
    }

    @Override
    protected IModel createEntite(ResultSet result) throws PersistenceException {
        IModel model;
        try {
            long id = result.getLong(TABLE_ATTRIBUT_CLE);
            String nom = result.getString(TABLE_ATTRIBUT_NOM);

            model = Model.builder().id(id)
                    .nom(nom)
                    .build();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return model;
    }

    @Override
    protected void verifierEntiteUtilisee(long id) throws PersistenceException {

    }

    @Override
    public IModel create(IModel e) throws PersistenceException {
        IModel entite;
        try {
            long id = getNewId();
            entite = Model.builder().id(id)
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
    public IModel create() throws PersistenceException {
        return this.create(Model.builder().build());
    }*/

    @Override
    public List<IModel> retreave(String recherche) throws PersistenceException {
        List<IModel> list = new ArrayList<IModel>();
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
                    IModel entite = this.createEntite(result);
                    list.add(entite);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return list;
    }

    @Override
    public void update(IModel e) throws PersistenceException {

        IModel model = this.retreaveForUpdate(e.getId());
        if (model == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("ERREUR_Model_INCONNU %s", e));
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
