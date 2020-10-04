/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.idatasource.IBusMapper;
import gza.transportPublic.idatasource.ICategorieMapper;
import gza.transportPublic.idatasource.IEquipementMapper;
import gza.transportPublic.idatasource.IMaintenanceMapper;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.IMecanicienMapper;
import gza.transportPublic.idatasource.IModelMapper;
import gza.transportPublic.idatasource.IPersonnelMapper;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.ICategorie;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author ZEED
 */
public class DbMapperManager implements IMapperManager {

    private final DataSource dataSource;
    private Connection connection;
    private IMecanicienMapper mecanicienMapper;
    private IBusMapper busMapper;
    private IModelMapper modelMapper;
    private ICategorieMapper categorieMapper;
    private IEquipementMapper equipementMapper;
    private IPersonnelMapper personnelMapper;
    private IMaintenanceMapper maintenanceMapper;
    private DatabaseSetUp databaseSetUp;

    public DbMapperManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public IMaintenanceMapper getMaintenanceMapper() throws PersistenceException {
        if (this.maintenanceMapper == null) {
            this.maintenanceMapper = new MaintenanceMapper(this);
        }
        return this.maintenanceMapper;
    }

    @Override
    public IBusMapper getBusMapper() throws PersistenceException {
        if (this.busMapper == null) {
            this.busMapper = new BusMapper(this);
        }
        return this.busMapper;
    }

    @Override
    public IPersonnelMapper getPersonnelMapper() throws PersistenceException {
        if (this.personnelMapper == null) {
            this.personnelMapper = new PersonnelMapper(this);
        }
        return this.personnelMapper;
    }

    @Override
    public IMecanicienMapper getMecanicienMapper() throws PersistenceException {
        if (this.mecanicienMapper == null) {
            this.mecanicienMapper = new MecanicienMapper(this);
        }
        return this.mecanicienMapper;
    }

    @Override
    public IModelMapper getModelMapper() throws PersistenceException {
        if (this.modelMapper == null) {
            this.modelMapper = new ModelMapper(this);
        }
        return this.modelMapper;
    }

    @Override
    public ICategorieMapper getCategorieMapper() throws PersistenceException {
        if (this.categorieMapper == null) {
            this.categorieMapper = new CategorieMapper(this);
        }
        return this.categorieMapper;
    }

    @Override
    public IEquipementMapper getEquipementMapper() throws PersistenceException {
        if (this.equipementMapper == null) {
            this.equipementMapper = new EquipementMapper(this);
        }
        return this.equipementMapper;
    }

    public DatabaseSetUp getDatabaseSetup() throws PersistenceException {
        if (this.databaseSetUp == null) {
            this.databaseSetUp = new DatabaseSetUp(this);
        }
        return this.databaseSetUp;

    }

    public Connection getConnection() throws PersistenceException {
        try {
            if (this.connection == null) {
                this.connection = dataSource.getConnection();
            }
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        return this.connection;
    }

    protected void closeConnection() throws PersistenceException {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
            this.connection = null;
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

    }

    @Override
    public Object executeAndClose(IMappeCmd command) throws PersistenceException {
        Object result = null;
        try {
            result = command.execute(this);
        } finally {
            closeConnection();

        }
        return result;
    }

    @Override
    public Object transaction(IMappeCmd command) throws PersistenceException {
        Object returnValue = null;
        try {
            if (this.getConnection().getAutoCommit()) {
                this.getConnection().setAutoCommit(false);
            }

            returnValue = command.execute(this);
            this.getConnection().commit();
        } catch (SQLException ex) {
            try {
                this.getConnection().rollback();
            } catch (SQLException e) {
            }
            throw new PersistenceException(ex);
        } finally {

            try {
                this.getConnection().setAutoCommit(true);
            } catch (SQLException ex) {
            }

        }
        return returnValue;
    }

    @Override
    public Object transactionAndClose(IMappeCmd command) throws PersistenceException {
        return executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager manager) throws PersistenceException {
                return manager.transaction(command);
            }
        });
    }

}
