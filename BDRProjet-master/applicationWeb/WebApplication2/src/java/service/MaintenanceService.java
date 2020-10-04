/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import gza.transportPublic.domain.Maintenance;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IMaintenance;
import java.util.List;

/**
 *
 * @author ZEED
 */
public class MaintenanceService {

    private IMapperManager mapperManager;

    public MaintenanceService(IMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public List<IMaintenance> getMaintenances() throws PersistenceException {

        List<IMaintenance> list = (List<IMaintenance>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave("");
            }
        });
        return list;

    }
    
        public List<IMaintenance> getMaintenances(String string) throws PersistenceException {

        List<IMaintenance> list = (List<IMaintenance>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(string);
            }
        });
        return list;

    }
    
    
    
    

    public IMaintenance create(IMaintenance maintenance) throws PersistenceException{
    
        return (IMaintenance) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().create(maintenance);
            }
        });
    
    }
    
    public IMaintenance getById(long id) throws PersistenceException {
        return (IMaintenance) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(id);
            }
        });

    }

    public void update(IMaintenance maintenance) throws PersistenceException {

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                mapperManager.getMaintenanceMapper().update(maintenance);
                return null;
            }
        });
    }

    public void deleteById(long id) throws PersistenceException {
        mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getMaintenanceMapper().delete(id);
                return null;
            }
        });

    }

}
