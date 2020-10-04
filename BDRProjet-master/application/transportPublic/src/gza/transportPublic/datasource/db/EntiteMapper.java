/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.idatasource.IMapper;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IEntite;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 *
 * @author ZEED
 */
public abstract class EntiteMapper<E extends IEntite> implements IMapper<E> {

    protected final DbMapperManager mapperManager;

    protected String sqlSelectMaxStr;
    protected String sqlSelectByIdStr;
    protected String sqlSelectByIdForUpdateStr;
    protected String sqlDeleteStr;

    public EntiteMapper(DbMapperManager manager) {
        mapperManager = manager;
    }

    protected long getNewId() throws PersistenceException {
        Long id;
        try {
            Statement statement = this.mapperManager.getConnection().createStatement();
            try (final ResultSet result = statement.executeQuery(sqlSelectMaxStr)) {
                result.next();
                id = result.getLong(1) + 1;
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return id;
    }

    @Override
    public E retreave(long id) throws PersistenceException {
        E entite = retreaveEntite(sqlSelectByIdStr, id);
        return entite;
    }

    protected E retreaveEntite(String query, Long id) throws PersistenceException {

        E entite = null;
        try {
            if (id != null) {
                PreparedStatement statement = this.mapperManager.getConnection()
                        .prepareStatement(query);
                statement.setLong(1, id);

                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        entite = createEntite(result);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

        return entite;
    }

    protected abstract E createEntite(ResultSet result) throws PersistenceException;

    @Override
    public void delete(E e) throws PersistenceException {
        delete(e.getId());
    }

    @Override
    public void delete(long id) throws PersistenceException {

        E entite = this.retreaveForUpdate(id);
        if (entite != null) {
            verifierEntiteUtilisee(id);

            try {
                PreparedStatement statement = this.mapperManager.getConnection().prepareStatement(sqlDeleteStr);
                statement.setLong(1, entite.getId());
                statement.executeUpdate();
            } catch (SQLException ex) {
                throw new PersistenceException(ex);
            }
        }
    }

    protected E retreaveForUpdate(long id) throws PersistenceException {
        E entite = retreaveEntite(sqlSelectByIdForUpdateStr, id);
        return entite;
    }

    protected abstract void verifierEntiteUtilisee(long id) throws PersistenceException;

}
