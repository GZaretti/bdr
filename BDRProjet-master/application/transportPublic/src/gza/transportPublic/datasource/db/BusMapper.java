/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.domain.Bus;
import gza.transportPublic.domain.Categorie;
import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IBusMapper;
import gza.transportPublic.idatasource.IBusMapper;
import gza.transportPublic.idatasource.IBusMapper;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.EntiteInconnuePersistenceException;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IBus;
import gza.transportPublic.idomain.IBus;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZEED
 */
public class BusMapper extends EntiteMapper<IBus> implements IBusMapper {

    public static String CREATE_TABLE = "CREATE TABLE bus(\n"
            + "    pk_bus SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + "    fk_place SMALLINT UNSIGNED NOT NULL,\n"
            + "    fk_categorie SMALLINT UNSIGNED NOT NULL,\n"
            + "    fk_modele SMALLINT UNSIGNED NOT NULL,\n"
            + "    noMatricule  varchar(255) NOT NULL UNIQUE,\n"
            + "    nbPassagerMax int(11) NOT NULL CHECK(nbPassagerMax > 0),\n"
            + "    pourHandicap boolean NOT NULL,\n"
            + "    CONSTRAINT ct_pk_bus PRIMARY KEY(pk_bus)\n"
            + ");\n";

    public static String DROP_TABLE = "DROP TABLE bus ";
    
    public static long STRONG_VALUE_PLACE_NOT_HANDLED = 1;

    public static final String SQL_INSERT = "INSERT INTO bus "
            + "(pk_bus,fk_place, fk_categorie, fk_modele, noMatricule, nbPassagerMax,"
            + "pourHandicap)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String SQL_UPDATE_BY_ID = "UPDATE bus "
            + "SET fk_place = ?,"
            + "fk_categorie = ?, "
            + "fk_modele = ?, "
            + "noMatricule = ?, "
            + "nbPassagerMax = ?, "
            + "pourHandicap = ? "
            + "WHERE pk_bus = ? ";

    public static final String SQL_DELETE_BY_ID = "DELETE FROM bus "
            + "WHERE pk_bus = ?";

    public static final String SQL_SELECT_ALL = "SELECT pk_bus,fk_place, fk_categorie,"
            + "fk_modele, noMatricule, nbPassagerMax,"
            + "pourHandicap "
            + "FROM bus ";

    public static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE pk_bus = ? ";

    public static final String SQL_SELECT_BY_ID_FOR_UPDATE = SQL_SELECT_BY_ID
            + " FOR UPDATE";

    public static final String SQL_SELECT = SQL_SELECT_ALL
            + " WHERE pk_bus = ? OR UPPER(noMatricule) LIKE UPPER(?) "
            + " ORDER BY pk_bus ";
    public static final String SQL_SELECT_MAX = "SELECT MAX(pk_bus) FROM bus ";

    public static final String TABLE_ATTRIBUT_CLE = "pk_bus";
    public static final String TABLE_ATTRIBUT_FK_PLACE = "fk_place";
    public static final String TABLE_ATTRIBUT_FK_CATEGORIE = "fk_categorie";
    public static final String TABLE_ATTRIBUT_FK_MODEL = "fk_modele";
    public static final String TABLE_ATTRIBUT_NOMATRICULE = "noMatricule";
    public static final String TABLE_ATTRIBUT_NBPASSAGER = "nbPassagerMax";
    public static final String TABLE_ATTRIBUT_HANDICAP = "pourHandicap";

    public BusMapper(DbMapperManager manager) {
        super(manager);
        this.sqlSelectMaxStr = SQL_SELECT_MAX;
        this.sqlSelectByIdStr = SQL_SELECT_BY_ID;
        this.sqlSelectByIdForUpdateStr = SQL_SELECT_BY_ID_FOR_UPDATE;
        this.sqlDeleteStr = SQL_DELETE_BY_ID;
    }

    @Override
    protected IBus createEntite(ResultSet result) throws PersistenceException {
        IBus bus;
        try {
           
            
            long id = result.getLong(TABLE_ATTRIBUT_CLE);
            //long placeId = result.getLong(TABLE_ATTRIBUT_FK_PLACE);
            long categorieId = result.getLong(TABLE_ATTRIBUT_FK_CATEGORIE);
            long modelId = result.getLong(TABLE_ATTRIBUT_FK_MODEL);
            
            IModel model = mapperManager.getModelMapper().retreave(modelId);
            ICategorie categorie = mapperManager.getCategorieMapper().retreave(categorieId);
            
            String noMatricule = result.getString(TABLE_ATTRIBUT_NOMATRICULE);
            int nbPassager = result.getInt(TABLE_ATTRIBUT_NBPASSAGER);
            boolean handicap = result.getBoolean(TABLE_ATTRIBUT_HANDICAP);

            bus = Bus.builder().id(id)
                    .categorie(categorie)
                    .model(model)
                    .noMatricule(noMatricule)
                    .nbPassagerMax(nbPassager)
                    .handicap(handicap)
                    .build();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return bus;
    }

    @Override
    protected void verifierEntiteUtilisee(long id) throws PersistenceException {

    }

    @Override
    public IBus create(IBus e) throws PersistenceException {
        IBus entite;
        try {
            long id = getNewId();
            entite = Bus.builder().id(id)
                    .categorie(e.getCategorie())
                    .model(e.getModel())
                    .noMatricule(e.getNoMatricule())
                    .nbPassagerMax(e.getNbPassagerMax())
                    .handicap(e.getHandicap())
                    .build();
            
            PreparedStatement statement = this.mapperManager.getConnection().prepareStatement(SQL_INSERT);
            statement.setLong(1, entite.getId());
            statement.setLong(2, STRONG_VALUE_PLACE_NOT_HANDLED );
            statement.setLong(3, entite.getCategorie().getId());
            statement.setLong(4, entite.getModel().getId());
            statement.setString(5, entite.getNoMatricule());
            statement.setInt(6, entite.getNbPassagerMax());
            statement.setBoolean(7, entite.getHandicap());
                    
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return entite;
    }

    /*  @Override
    public IBus create() throws PersistenceException {
        return this.create(Bus.builder().build());
    }*/
    @Override
    public List<IBus> retreave(String recherche) throws PersistenceException {
        List<IBus> list = new ArrayList<IBus>();
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
                    IBus entite = this.createEntite(result);
                    list.add(entite);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return list;
    }

    @Override
    public void update(IBus e) throws PersistenceException {

        IBus bus = this.retreaveForUpdate(e.getId());
        if (bus == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("ERREUR_Bus_INCONNU %s", e));
        }

        try {      
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_UPDATE_BY_ID);
            statement.setLong(1, STRONG_VALUE_PLACE_NOT_HANDLED);
            statement.setLong(2, e.getCategorie().getId());
            statement.setLong(3, e.getModel().getId());
            statement.setString(4, e.getNoMatricule());
            statement.setInt(5, e.getNbPassagerMax());
            statement.setBoolean(6, e.getHandicap());
            statement.setLong(7, e.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {

            throw new PersistenceException(ex);
        }
    }

}
