/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IModel;
import java.util.List;

/**
 *
 * @author ZEED
 */
public class ModelService {

    private IMapperManager mapperManager;

    public ModelService(IMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public List<IModel> getModels() throws PersistenceException {

        List<IModel> list = (List<IModel>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave("");
            }
        });
        return list;

    }
    
        public List<IModel> getModels(String string) throws PersistenceException {

        List<IModel> list = (List<IModel>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(string);
            }
        });
        return list;

    }
    
    
    
    

    public IModel create(IModel model) throws PersistenceException{
    
        return (IModel) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(model);
            }
        });
    
    }
    
    public IModel getById(long id) throws PersistenceException {
        return (IModel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(id);
            }
        });

    }

    public void update(IModel model) throws PersistenceException {

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                mapperManager.getModelMapper().update(model);
                return null;
            }
        });
    }

    public void deleteById(long id) throws PersistenceException {
        mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getModelMapper().delete(id);
                return null;
            }
        });

    }

}
