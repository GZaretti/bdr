package gza.transportPublic.idatasource;

import gza.transportPublic.idatasource.exceptions.PersistenceException;

/**
 *
 * @author ZEED
 */
public interface IMapperManager {
    
    public IMaintenanceMapper getMaintenanceMapper() throws PersistenceException;

    public IBusMapper getBusMapper() throws PersistenceException;
    
    public IPersonnelMapper getPersonnelMapper()throws PersistenceException;
    
    public IMecanicienMapper getMecanicienMapper()throws PersistenceException;

    public IModelMapper getModelMapper() throws PersistenceException;

    public ICategorieMapper getCategorieMapper() throws PersistenceException;

    public IEquipementMapper getEquipementMapper() throws PersistenceException;

    public Object executeAndClose(IMappeCmd command) throws PersistenceException;

    public Object transaction(IMappeCmd command) throws PersistenceException;

    public Object transactionAndClose(final IMappeCmd command) throws PersistenceException;

}
