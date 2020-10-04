/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import gza.transportPublic.domain.Bus;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IBus;
import java.util.List;

/**
 *
 * @author ZEED
 */
public class BusService {

    private IMapperManager mapperManager;

    public BusService(IMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public List<IBus> getBuss() throws PersistenceException {

        List<IBus> list = (List<IBus>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave("");
            }
        });
        return list;

    }
    
        public List<IBus> getBuss(String string) throws PersistenceException {

        List<IBus> list = (List<IBus>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(string);
            }
        });
        return list;

    }
    
    
    
    

    public IBus create(IBus bus) throws PersistenceException{
    
        return (IBus) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().create(bus);
            }
        });
    
    }
    
    public IBus getById(long id) throws PersistenceException {
        return (IBus) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(id);
            }
        });

    }

    public void update(IBus bus) throws PersistenceException {

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                mapperManager.getBusMapper().update(bus);
                return null;
            }
        });
    }

    public void deleteById(long id) throws PersistenceException {
        mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getBusMapper().delete(id);
                return null;
            }
        });

    }

}
