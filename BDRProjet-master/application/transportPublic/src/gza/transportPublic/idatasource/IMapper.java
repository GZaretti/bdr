package gza.transportPublic.idatasource;

import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IEntite;
import java.util.List;

/**
 *
 * @author ZEED
 */
public interface IMapper<E extends IEntite> {

    E create(E e) throws PersistenceException;

   // E create() throws PersistenceException;

    List<E> retreave(String s) throws PersistenceException;

    E retreave(long id) throws PersistenceException;

    void update(E e) throws PersistenceException;

    void delete(E e) throws PersistenceException;

    void delete(long id) throws PersistenceException;
}
