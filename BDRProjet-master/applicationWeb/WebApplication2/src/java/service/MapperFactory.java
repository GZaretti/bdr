/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import gza.transportPublic.datasource.db.DataSourceFactory;
import gza.transportPublic.datasource.db.DbMapperManager;

/**
 *
 * @author ZEED
 */
public class MapperFactory {

    public static DbMapperManager createMapperManager() {
        return new gza.transportPublic.datasource.db.DbMapperManager(
                DataSourceFactory.getMysqlSQLDataSource("localhost",
                        3306, "testTransport", "root", "Google2018$"));
    }

}
