/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import gza.transportPublic.domain.Categorie;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.ICategorie;
import java.util.List;

/**
 *
 * @author ZEED
 */
public class CategorieService {

    private IMapperManager mapperManager;

    public CategorieService(IMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public List<ICategorie> getCategories() throws PersistenceException {

        List<ICategorie> list = (List<ICategorie>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave("");
            }
        });
        return list;

    }
    
        public List<ICategorie> getCategories(String string) throws PersistenceException {

        List<ICategorie> list = (List<ICategorie>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(string);
            }
        });
        return list;

    }
    
    
    
    

    public ICategorie create(ICategorie categorie) throws PersistenceException{
    
        return (ICategorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(categorie);
            }
        });
    
    }
    
    public ICategorie getById(long id) throws PersistenceException {
        return (ICategorie) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(id);
            }
        });

    }

    public void update(ICategorie categorie) throws PersistenceException {

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                mapperManager.getCategorieMapper().update(categorie);
                return null;
            }
        });
    }

    public void deleteById(long id) throws PersistenceException {
        mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getCategorieMapper().delete(id);
                return null;
            }
        });

    }

}
