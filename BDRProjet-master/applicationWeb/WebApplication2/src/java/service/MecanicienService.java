/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import gza.transportPublic.domain.Mecanicien;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IMecanicien;
import java.util.List;

/**
 *
 * @author ZEED
 */
public class MecanicienService {

    private IMapperManager mapperManager;

    public MecanicienService(IMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public List<IMecanicien> getMecaniciens() throws PersistenceException {

        List<IMecanicien> list = (List<IMecanicien>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave("");
            }
        });
        return list;

    }
    
        public List<IMecanicien> getMecaniciens(String string) throws PersistenceException {

        List<IMecanicien> list = (List<IMecanicien>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(string);
            }
        });
        return list;

    }
    
    
    
    

    public IMecanicien create(IMecanicien mecanicien) throws PersistenceException{
    
        return (IMecanicien) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().create(mecanicien);
            }
        });
    
    }
    
    public IMecanicien getById(long id) throws PersistenceException {
        return (IMecanicien) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(id);
            }
        });

    }

    public void update(IMecanicien mecanicien) throws PersistenceException {

        mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                mapperManager.getMecanicienMapper().update(mecanicien);
                return null;
            }
        });
    }

    public void deleteById(long id) throws PersistenceException {
        mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getMecanicienMapper().delete(id);
                return null;
            }
        });

    }

}
