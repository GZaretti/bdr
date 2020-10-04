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
public class EquipementMapper extends EntiteMapper<IEquipement> implements IEquipementMapper {

    public static String CREATE_TABLE = ""
            + "CREATE TABLE equipement(\n"
            + "	pk_equipement SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + " nom  varchar(255) NOT NULL,\n"
            + " information  TEXT,\n"
            + " CONSTRAINT ct_pk_equipement PRIMARY KEY(pk_equipement)\n"
            + ");";

    public static String DROP_TABLE = "DROP TABLE equipement";

    public static final String SQL_INSERT = "INSERT INTO equipement "
            + "(pk_equipement,nom,information)"
            + " VALUES (?, ?, ?)";

    public static final String SQL_UPDATE_BY_ID = "UPDATE equipement "
            + "SET nom = ?,"
            + " information = ? "
            + "WHERE pk_equipement = ?";

    public static final String SQL_DELETE_BY_ID = "DELETE FROM equipement "
            + "WHERE pk_equipement = ?";

    public static final String SQL_SELECT_ALL = "SELECT pk_equipement,nom,information "
            + " FROM equipement ";

    public static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE pk_equipement = ?";

    public static final String SQL_SELECT_BY_ID_FOR_UPDATE = SQL_SELECT_BY_ID
            + " FOR UPDATE";

    public static final String SQL_SELECT = SQL_SELECT_ALL
            + " WHERE pk_equipement = ? OR UPPER(nom) LIKE UPPER(?) OR UPPER(information) LIKE UPPER(?) "
            + " ORDER BY pk_equipement ";
    public static final String SQL_SELECT_MAX = "SELECT MAX(pk_equipement) FROM equipement";

    public static final String TABLE_ATTRIBUT_CLE = "pk_equipement";
    public static final String TABLE_ATTRIBUT_NOM = "nom";
     public static final String TABLE_ATTRIBUT_INFORMATION = "information";

    public EquipementMapper(DbMapperManager manager) {
        super(manager);
        this.sqlSelectMaxStr = SQL_SELECT_MAX;
        this.sqlSelectByIdStr = SQL_SELECT_BY_ID;
        this.sqlSelectByIdForUpdateStr = SQL_SELECT_BY_ID_FOR_UPDATE;
        this.sqlDeleteStr = SQL_DELETE_BY_ID;
    }

    @Override
    protected IEquipement createEntite(ResultSet result) throws PersistenceException {
        IEquipement equipement;
        try {
            long id = result.getLong(TABLE_ATTRIBUT_CLE);
            String nom = result.getString(TABLE_ATTRIBUT_NOM);
             String information = result.getString(TABLE_ATTRIBUT_INFORMATION);

            equipement = Equipement.builder().id(id)
                    .nom(nom)
                    .information(information)
                    .build();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return equipement;
    }

    @Override
    protected void verifierEntiteUtilisee(long id) throws PersistenceException {

    }

    @Override
    public IEquipement create(IEquipement e) throws PersistenceException {
        IEquipement entite;
        try {
            long id = getNewId();
            entite = Equipement.builder().id(id)
                    .nom(e.getNom())
                    .information(e.getInformation())
                    .build();

            PreparedStatement statement = this.mapperManager.getConnection().prepareStatement(SQL_INSERT);
            statement.setLong(1, entite.getId());
            statement.setString(2, entite.getNom());
            statement.setString(3, entite.getInformation());
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return entite;
    }

  /*  @Override
    public IEquipement create() throws PersistenceException {
        return this.create(Equipement.builder().build());
    }*/

    @Override
    public List<IEquipement> retreave(String recherche) throws PersistenceException {
        List<IEquipement> list = new ArrayList<IEquipement>();
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
                    IEquipement entite = this.createEntite(result);
                    list.add(entite);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return list;
    }

    @Override
    public void update(IEquipement e) throws PersistenceException {

        IEquipement equipement = this.retreaveForUpdate(e.getId());
        if (equipement == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("ERREUR_Equipement_INCONNU %s", e));
        }

        try {
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_UPDATE_BY_ID);
            statement.setString(1, e.getNom());            
            statement.setString(2,e.getInformation());
            statement.setLong(3, e.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {

            throw new PersistenceException(ex);
        }
    }

}
