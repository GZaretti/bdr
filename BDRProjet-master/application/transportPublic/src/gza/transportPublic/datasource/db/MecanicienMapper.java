/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import static gza.transportPublic.datasource.db.PersonnelMapper.SQL_INSERT;
import gza.transportPublic.domain.Mecanicien;
import gza.transportPublic.domain.Categorie;
import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IMecanicienMapper;
import gza.transportPublic.idatasource.IMecanicienMapper;
import gza.transportPublic.idatasource.IMecanicienMapper;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.EntiteInconnuePersistenceException;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IMecanicien;
import gza.transportPublic.idomain.IMecanicien;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IModel;
import gza.transportPublic.idomain.IPersonnel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Connection;

/**
 *
 * @author ZEED
 */
public class MecanicienMapper extends EntiteMapper<IMecanicien> implements IMecanicienMapper {

    public static String CREATE_TABLE = "CREATE TABLE mecanicien(\n"
            + "	pk_fk_mecanicien SMALLINT UNSIGNED NOT NULL,\n"
            + "	noCertification varchar(255) NOT NULL,\n"
            + " CONSTRAINT ct_pk_pk_fk_mecanicien PRIMARY KEY(pk_fk_mecanicien),\n"
            + " CONSTRAINT ct_fk_pk_fk_mecanicien FOREIGN KEY (pk_fk_mecanicien) REFERENCES personnel(pk_personnel) ON DELETE CASCADE ON UPDATE CASCADE\n"
            + ");";

    public static String DROP_TABLE = "DROP TABLE mecanicien ";

    public static final String SQL_INSERT = "INSERT INTO mecanicien "
            + "(pk_fk_mecanicien, noCertification) "
            + " VALUES (?, ?)";

    public static final String SQL_UPDATE_BY_ID = "UPDATE mecanicien "
            + "SET "
            + "noCertification = ? "
            + "WHERE pk_fk_mecanicien = ? ";

    public static final String SQL_DELETE_BY_ID = "DELETE FROM mecanicien "
            + "WHERE pk_fk_mecanicien = ?";

    public static final String SQL_SELECT_ALL = "SELECT pk_fk_mecanicien, nom, prenom, dateEmbauche, pourcentage, noCertification "
            + "FROM mecanicien "
            + "INNER JOIN personnel ON pk_fk_mecanicien = pk_personnel ";

    public static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL
            + " WHERE pk_fk_mecanicien = ? ";

    public static final String SQL_SELECT_BY_ID_FOR_UPDATE = SQL_SELECT_BY_ID
            + " FOR UPDATE";

    public static final String SQL_SELECT = SQL_SELECT_ALL
            + " WHERE pk_fk_mecanicien = ? OR UPPER(nom) LIKE UPPER(?) OR "
            + " UPPER(prenom) LIKE UPPER(?) OR "
            //+ " OR dateEmbauche = ? "
            //+ " OR pourcentage = ? "
            + " UPPER(noCertification) LIKE UPPER(?) "
            + " ORDER BY pk_fk_mecanicien ";
    public static final String SQL_SELECT_MAX = "SELECT MAX(pk_fk_mecanicien) FROM mecanicien ";

    public static final String TABLE_ATTRIBUT_CLE = "pk_fk_mecanicien";
    public static final String TABLE_ATTRIBUT_NOCERTIFICATION = "noCertification";

    public MecanicienMapper(DbMapperManager manager) {
        super(manager);
        this.sqlSelectMaxStr = SQL_SELECT_MAX;
        this.sqlSelectByIdStr = SQL_SELECT_BY_ID;
        this.sqlSelectByIdForUpdateStr = SQL_SELECT_BY_ID_FOR_UPDATE;
        this.sqlDeleteStr = SQL_DELETE_BY_ID;
    }

    @Override
    protected IMecanicien createEntite(ResultSet result) throws PersistenceException {
        IMecanicien mecanicien;
        try {

            long id = result.getLong(TABLE_ATTRIBUT_CLE);
            String noCertification = result.getString(TABLE_ATTRIBUT_NOCERTIFICATION);
            IPersonnel personnel = mapperManager.getPersonnelMapper().retreave(id);

            mecanicien = Mecanicien.builder()
                    .id(id)
                    .nom(personnel.getNom())
                    .prenom(personnel.getPrenom())
                    .dateEmbauche(personnel.getDateEmbauche())
                    .pourcentage(personnel.getPourcentage())
                    .noCertification(noCertification)
                    .build();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return mecanicien;
    }

    @Override
    protected void verifierEntiteUtilisee(long id) throws PersistenceException {

    }

    @Override
    public IMecanicien create(IMecanicien e) throws PersistenceException {
        IMecanicien entite;
        try {
            long id = getNewId();
            entite = Mecanicien.builder().id(id)
                    .id(id)
                    .nom(e.getNom())
                    .prenom(e.getPrenom())
                    .dateEmbauche(e.getDateEmbauche())
                    .pourcentage(e.getPourcentage())
                    .noCertification(e.getNoCertification())
                    .build();

            Connection con = DbMapperFactory.createMapperManager().getConnection();
            PreparedStatement statementPersonnel = con.prepareStatement(PersonnelMapper.SQL_INSERT);

            PreparedStatement statementMecano = con.prepareStatement(SQL_INSERT);
            try {

                con.setAutoCommit(false);

                statementPersonnel.setLong(1, entite.getId());
                statementPersonnel.setString(2, entite.getNom());
                statementPersonnel.setString(3, entite.getPrenom());
                statementPersonnel.setDate(4, new java.sql.Date(entite.getDateEmbauche().getTime()));
                statementPersonnel.setInt(5, entite.getPourcentage());

                statementMecano.setLong(1, entite.getId());
                statementMecano.setString(2, entite.getNoCertification());

                statementPersonnel.executeUpdate();
                statementMecano.executeUpdate();
                con.commit();

            } catch (SQLException expp) {
                if (con != null) {
                    try {
                        con.rollback();
                        throw new PersistenceException(expp);
                    } catch (SQLException excep) {
                        throw new PersistenceException(excep);
                    }
                }
            } finally {
                if (statementPersonnel != null) {
                    statementPersonnel.close();
                }
                if (statementMecano != null) {
                    statementMecano.close();
                }
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return entite;
    }

    /*  @Override
    public IMecanicien create() throws PersistenceException {
        return this.create(Mecanicien.builder().build());
    }*/
    @Override
    public List<IMecanicien> retreave(String recherche) throws PersistenceException {
        List<IMecanicien> list = new ArrayList<IMecanicien>();
        try {
            PreparedStatement statement = mapperManager.getConnection().prepareStatement(SQL_SELECT);
            try {
                statement.setLong(1, new Long(recherche));
            } catch (NumberFormatException ex) {
                //ne fait rien
                statement.setNull(1, Types.NUMERIC);
            }
            statement.setString(2, "%" + recherche + "%");
            statement.setString(3, recherche);
            //statement.setDate(4, new java.sql.Date(recherche));
            //statement.setInt(5, recherche);
            statement.setString(4, recherche);

            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {

                    IMecanicien entite = this.createEntite(result);

                    // IPersonnel pers =  mapperManager.getPersonnelMapper().retreave(entite.getId());
                    /*entite.setNom(pers.getNom());
                    entite.setPrenom(pers.getPrenom());
                    entite.setPourcentage(pers.getPourcentage());
                    entite.setDateEmbauche(pers.getDateEmbauche());*/
                    list.add(entite);

                }

            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return list;
    }

    @Override
    public void update(IMecanicien e) throws PersistenceException {

        IMecanicien mecanicien = this.retreaveForUpdate(e.getId());
        if (mecanicien == null) {
            throw new EntiteInconnuePersistenceException(
                    String.format("ERREUR_Mecanicien_INCONNU %s", e));
        }

        // ------------------------------
        try {
            Connection con = DbMapperFactory.createMapperManager().getConnection();
            PreparedStatement statementPersonnel = con.prepareStatement(PersonnelMapper.SQL_UPDATE_BY_ID);

            PreparedStatement statementMecano = con.prepareStatement(SQL_UPDATE_BY_ID);
            try {

                con.setAutoCommit(false);

                statementPersonnel.setString(1, e.getNom());
                statementPersonnel.setString(2, e.getPrenom());
                statementPersonnel.setDate(3, new java.sql.Date(e.getDateEmbauche().getTime()));
                statementPersonnel.setInt(4, e.getPourcentage());
                statementPersonnel.setLong(5, e.getId());
                statementPersonnel.executeUpdate();

                statementMecano.setString(1, e.getNoCertification());
                statementMecano.setLong(2, e.getId());

                statementPersonnel.executeUpdate();
                statementMecano.executeUpdate();
                con.commit();

            } catch (SQLException expp) {
                if (con != null) {
                    try {
                        con.rollback();
                        throw new PersistenceException(expp);
                    } catch (SQLException excep) {
                        throw new PersistenceException(excep);
                    }
                }
            } finally {
                if (statementPersonnel != null) {
                    statementPersonnel.close();
                }
                if (statementMecano != null) {
                    statementMecano.close();
                }
                con.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        // -------------------------------
    }

    @Override
    public void delete(long id) throws PersistenceException {

        IMecanicien entite = this.retreaveForUpdate(id);
        if (entite != null) {
            verifierEntiteUtilisee(id);

            try {//-------------

                Connection con = DbMapperFactory.createMapperManager().getConnection();
                PreparedStatement statementPersonnel = con.prepareStatement(PersonnelMapper.SQL_DELETE_BY_ID);

                PreparedStatement statementMecano = con.prepareStatement(SQL_DELETE_BY_ID);
                try {

                    con.setAutoCommit(false);

                    statementPersonnel.setLong(1, entite.getId());
                    statementPersonnel.executeUpdate();

                    statementMecano.setLong(1, entite.getId());

                    statementPersonnel.executeUpdate();
                    statementMecano.executeUpdate();
                    con.commit();

                } catch (SQLException expp) {
                    if (con != null) {
                        try {
                            con.rollback();
                            throw new PersistenceException(expp);
                        } catch (SQLException excep) {
                            throw new PersistenceException(excep);
                        }
                    }
                } finally {
                    if (statementPersonnel != null) {
                        statementPersonnel.close();
                    }
                    if (statementMecano != null) {
                        statementMecano.close();
                    }
                    con.setAutoCommit(true);
                }

            } catch (SQLException ex) {
                throw new PersistenceException(ex);
            }

            /*PreparedStatement statement = this.mapperManager.getConnection().prepareStatement(sqlDeleteStr);
                statement.setLong(1, entite.getId());
                statement.executeUpdate();*/
            //---------
        }
    }

}
