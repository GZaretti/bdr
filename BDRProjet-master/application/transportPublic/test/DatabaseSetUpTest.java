/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ZEED
 */
/*public class DatabaseSetUpTest {

    private final DbMapperManager mapperManager;

    public DatabaseSetUpTest() {
        mapperManager = TestMapperFactory.createMapperManager();
    }

    @Before
    public void setUp() throws PersistenceException {
        DbMapperManager mapperManager = TestMapperFactory.createMapperManager();

        mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                try {
                    ((DbMapperManager) mapperManager).getDatabaseSetup().dropTables();
                } catch (Exception ex) {
                    //ne fait rien
                }
                ((DbMapperManager) mapperManager).getDatabaseSetup().createTables();
                ((DbMapperManager) mapperManager).getDatabaseSetup().insertDatas();
                return null;
            }
        });
    }
    
    @After
    public void tearDown() throws Exception {
        mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                ((DbMapperManager) mapperManager).getDatabaseSetup().dropTables();
                return null;
            }
        });

    }


   

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
*/