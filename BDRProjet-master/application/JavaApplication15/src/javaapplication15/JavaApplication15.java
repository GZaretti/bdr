/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication15;

import gza.transportPublic.datasource.db.DataSourceFactory;
import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.idatasource.IMapperManager;
import java.io.Console;

/**
 *
 * @author ZEED
 */
public class JavaApplication15 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
            IMapperManager mapperManager = new DbMapperManager(
                    DataSourceFactory.getMysqlSQLDataSource("localhost",
                            3306, "testTransport", "root", "Google2018$"));

            ((DbMapperManager) mapperManager).getDatabaseSetup().createTables();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

}
