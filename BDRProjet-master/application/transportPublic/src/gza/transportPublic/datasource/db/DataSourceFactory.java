/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import javax.sql.ConnectionPoolDataSource;
import javax.sql.DataSource;
import org.hsqldb.jdbc.JDBCDataSource;

/**
 *
 * @author ZEED
 */
public class DataSourceFactory {
    public static DataSource getHsqldbDataSource(String url,
            String user, String password) {
        
        JDBCDataSource ds = new JDBCDataSource();
        ds.setUrl(url);
        ds.setUser(user);
        ds.setPassword(password);
        
        return ds;
    }
    public static DataSource getMysqlSQLDataSource(String serverName,
            int portNumber, String databaseName, String user, String password) {
        MysqlDataSource ds = new MysqlDataSource();
        //MysqlDataSource ds = new MysqlDataSource();
        //ConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
        ds.setServerName(serverName);
        ds.setPortNumber(portNumber);
        ds.setDatabaseName(databaseName);
        ds.setUser(user);
        ds.setPassword(password);        
        return ds;
    }

//    public static DataSource getPostgreSQLDataSource(String serverName,
//            int portNumber, String databaseName, String user, String password) {
//
//        PGPoolingDataSource ds = new PGPoolingDataSource();
//        ds.setServerName(serverName);
//        ds.setPortNumber(portNumber);
//        ds.setDatabaseName(databaseName);
//        ds.setUser(user);
//        ds.setPassword(password);
//        ds.setMaxConnections(100);
//        
//        return ds;
//    }
}
