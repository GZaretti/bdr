/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import static gza.transportPublic.datasource.db.PersonnelMapper.TABLE_ATTRIBUT_DATEEMBAUCHE;
import gza.transportPublic.domain.Maintenance;
import gza.transportPublic.domain.Categorie;
import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IMaintenanceMapper;
import gza.transportPublic.idatasource.IMaintenanceMapper;
import gza.transportPublic.idatasource.IMaintenanceMapper;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.EntiteInconnuePersistenceException;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IBus;
import gza.transportPublic.idomain.IMaintenance;
import gza.transportPublic.idomain.IMaintenance;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IMecanicien;
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
public class MaintenanceMapper extends EntiteMapper<IMaintenance> implements IMaintenanceMapper {

    public static String CREATE_TABLE = "CREATE TABLE maintenance(\n"
            + "	pk_maintenance SMALLINT UNSIGNED AUTO_INCREMENT,\n"
            + "	fk_bus SMALLINT UNSIGNED NOT NULL,\n"
            + " fk_mecanicien SMALLINT UNSIGNED NOT NULL,\n"
            + " dateDebut DATE NOT NULL,\n"
            + " dateFin DATE NOT NULL,\n"
            + " commentaires TEXT,\n"
            + " CONSTRAINT maintenance PRIMARY KEY(pk_maintenance)\n"
            + "); \n";

    public static String DROP_TABLE = "DROP TABLE maintenance ";

    public static final String SQL_INSERT = "INSERT INTO maintenance "
            + "(pk_maintenance,fk_bus, fk_mecanicien, dateDebut, dateFin, commentaires)"
            + " VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SQL_UPDATE_BY_ID = "UPDATE maintenance "
            + "SET fk_bus = ?,"
            + "fk_mecanicien = ?, "
            + "dateDebut = ?, "
            + "dateFin = ?, "
            + "commentaires = ? "
            + "WHERE pk_maintenance = ? ";

    public static final String SQL_DELETE_BY_ID = "DELETE FROM maintenance "
            + "WHERE pk_maintenance = ?";

    public static final String SQL_SELECT_ALL = "SELECT pk_maintenance,fk_bus,"
            + " fk_mecanicien, dateDebut, dateFin, commentaires " 
            + "FROM maintenance ";

    public static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE pk_maintenance = ? ";

    public static final String SQL_SELECT_BY_ID_FOR_UPDATE = SQL_SELECT_BY_ID
            + " FOR UPDATE";

    public static final String SQL_SELECT = SQL_SELECT_ALL
            + " WHERE pk_maintenance = ? OR UPPER(commentaires) LIKE UPPER(?) "
            + " ORDER BY pk_maintenance ";
    public static final String SQL_SELECT_MAX = "SELECT MAX(pk_maintenance) FROM maintenance ";

    public static final String TABLE_ATTRIBUT_CLE = "pk_maintenance";
    public static final String TABLE_ATTRIBUT_FK_BUS = "fk_bus";
    public static final String TABLE_ATTRIBUT_FK_MECANICIEN = "fk_mecanicien";
    public static final String TABLE_ATTRIBUT_DATEDEBUT = "dateDebut";
    public static final String TABLE_ATTRIBUT_DATEFIN = "dateFin";
    public static final String TABLE_ATTRIBUT_COMMENTAIRES = "commentaires";

    public MaintenanceMapper(DbMapperManager manager) {
        super(manager);
        this.sqlSelectMaxStr = SQL_SELECT_MAX;
        this.sqlSelectByIdStr = SQL_SELECT_BY_ID;
        this.sqlSelectByIdForUpdateStr = SQL_SELECT_BY_ID_FOR_UPDATE;
        this.sqlDeleteStr = SQL_DELETE_BY_ID;
    }

    @Override
    protected IMaintenance createEntite(ResultSet result) throws PersistenceException {
        IMaintenance maintenance;
        try {

            long id = result.getLong(TABLE_ATTRIBUT_CLE);
            long busId = result.getLong(TABLE_ATTRIBUT_FK_BUS);
            long mecanicienId = result.getLong(TABLE_ATTRIBUT_FK_MECANICIEN);

            IBus bus = mapperManager.getBusMapper().retreave(busId);
            IMecanicien mecanicien = mapperManager.getMecanicienMapper().retreave(mecanicienId);
            
            Date dateDebut = result.getDate(TABLE_ATTRIBUT_DATEDEBUT);
            Date dateFin = result.getDate(TABLE_ATTRIBUT_DATEFIN);
            String commentaires = result.getString(TABLE_ATTRIBUT_COMMENTAIRES);

            maintenance = Maintenance.builder().id(id)
                    .bus(bus)
                    .mecanicien(mecanicien)
                    .dateDebut(dateDebut)
                    .dateFin(dateFin)
                    .commentaires(commentaires)
                    .build();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return maintenance;
    }

    @Override
    protected void verifierEntiteUtilisee(long id) throws PersistenceException {

    }

    @Override
    public IMaintenance create(IMaintenance e) throws PersistenceException {
        IMaintenance entite;
        try {
            long id = getNewId();
            entite = Maintenance.builder().id(id)
                    .bus(e.getBus())
                    .mecanicien(e.getMecanicien())
                    .dateDebut(e.getDateDebut())
                    .dateFin(e.getDateFin())
                    .commentaires(e.getCommentaires())
                    .build();

            PreparedStatement statement = this.mapperManager.getConnection()
                    .prepareStatement(SQL_INSERT);
            statement.setLong(1, entite.getId());
            statement.setLong(2, entite.getBus().getId());
            statement.setLong(3, entite.getMecanicien().getId());
            statement.setDate(4, new java.sql.Date(entite.getDateDebut().getTime()));
            statement.setDate(5, new java.sql.Date(entite.getDateFin().getTime()));
            statement.setString(6, entite.getCommentaires());

            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return entite;
    }

    /*  @Override
    public IMaintenance create() throws PersistenceException {
        return this.create(Maintenance.builder().build());
    }*/
    @Override
    public List<IMaintenance> retreave(String recherche) throws PersistenceException {
        List<IMaintenance> list = new ArrayList<IMaintenance>();
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
                    IMaintenance entite = this.createEntite(result);
                    list.add(entite);
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return list;
    }

    @Override
    public void update(IMaintenance e) throws PersistenceException {

        IMaintenance maintenance = this.retreaveForUpdate(e.getId());
        if (maintenance == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("ERREUR_Maintenance_INCONNU %s", e));
        }

        try {
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_UPDATE_BY_ID);
            statement.setLong(1, e.getBus().getId());
            statement.setLong(2, e.getMecanicien().getId());
            statement.setDate(3,  new java.sql.Date(e.getDateDebut().getTime()));
            statement.setDate(4,  new java.sql.Date(e.getDateFin().getTime()));
            statement.setString(5, e.getCommentaires());
            statement.setLong(6, e.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {

            throw new PersistenceException(ex);
        }
    }

}
