/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.idatasource;

import gza.transportPublic.idatasource.exceptions.PersistenceException;

/**
 *
 * @author ZEED
 */
public interface IMappeCmd {

    Object execute(IMapperManager mapperManager) throws PersistenceException;
}
