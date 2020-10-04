/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import gza.transportPublic.domain.Equipement;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IEquipement;
import java.util.List;

/**
 *
 * @author ZEED
 */
public class EquipementService {

    private IMapperManager mapperManager;

    public EquipementService(IMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public List<IEquipement> getEquipements() throws PersistenceException {

        List<IEquipement> list = (List<IEquipement>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave("");
            }
        });
        return list;

    }
    
        public List<IEquipement> getEquipements(String string) throws PersistenceException {

        List<IEquipement> list = (List<IEquipement>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(string);
            }
        });
        return list;

    }
    
    
    
    

    public IEquipement create(IEquipement equipement) throws PersistenceException{
    
        return (IEquipement) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().create(equipement);
            }
        });
    
    }
    
    public IEquipement getById(long id) throws PersistenceException {
        return (IEquipement) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(id);
            }
        });

    }

    public void update(IEquipement equipement) throws PersistenceException {

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                mapperManager.getEquipementMapper().update(equipement);
                return null;
            }
        });
    }

    public void deleteById(long id) throws PersistenceException {
        mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getEquipementMapper().delete(id);
                return null;
            }
        });

    }

}
