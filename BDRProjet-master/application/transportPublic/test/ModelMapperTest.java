/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IModel;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ZEED
 */
public class ModelMapperTest {    
    private final IMapperManager mapperManager;
    public ModelMapperTest() {
        mapperManager = TestMapperFactory.createMapperManager();
    }
    
 @Before
    public void before() throws PersistenceException {
        IMapperManager mapperManager = TestMapperFactory.createMapperManager();

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
    public void after() throws PersistenceException {
        DbMapperManager mapperManager = TestMapperFactory.createMapperManager();

        mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                ((DbMapperManager) mapperManager).getDatabaseSetup().dropTables();
                return null;
            }
        });
    }
    
    @Test
    public void testCreate() throws PersistenceException {
        IModel nouvelleEntite = (IModel) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("ModelOne").build());
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());
    }
    
    @Test
    public void testCreateEntite() throws PersistenceException {
        final IModel entite = Model.builder()
                .nom("Nouvelle article")
                .build();

        IModel nouvelleEntite = (Model) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(entite);
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());

        Assert.assertEquals(entite.getNom(), nouvelleEntite.getNom());
    }
    
    @Test
    public void testRetreave() throws PersistenceException {
        final IModel entite = Model.builder()
                .nom("ModelTest")
                .build();

        final IModel nouvelleEntite = (IModel) mapperManager.transaction(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(entite);
            }
        });

        List<IModel> entiteTrouveeList = (List<IModel>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(nouvelleEntite.getNom());
            }
        });

        Assert.assertTrue(entiteTrouveeList.size() > 0);
        Assert.assertTrue(entiteTrouveeList.contains(nouvelleEntite));
    }
    
    @Test
    public void testRetreaveById() throws PersistenceException {
        final IModel entite = Model.builder()
                .nom("model retreave test")
                .build();

        final IModel nouvelleEntite = (IModel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(entite);
            }
        });

        IModel entiteTrouvee = (IModel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(nouvelleEntite.getId());
            }
        });
        
        Assert.assertEquals(nouvelleEntite, entiteTrouvee);
        
    }
    
    @Test
    public void testRetreaveByIdNotExist() throws PersistenceException {
        final Long id = 10L;

        IModel entiteTrouvee = (IModel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(id);
            }
        });

        Assert.assertNull(entiteTrouvee);
    }
    
    @Test
    public void testUpdate() throws PersistenceException {
        final IModel entite = Model.builder()
                .nom("model update test")
                .build();

        final IModel nouvelleEntite = (IModel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(entite);
            }
        });

        IModel entiteTrouvee = (IModel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                IModel entiteTrouvee = mapperManager.getModelMapper().retreave(nouvelleEntite.getId());
                entiteTrouvee.setNom(entiteTrouvee.getNom() + "++++");

                mapperManager.getModelMapper().update(entiteTrouvee);

                return entiteTrouvee;
            }
        });

        IModel entiteModifie = (IModel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(entiteTrouvee, entiteModifie);
    }

    @Test
    public void testDelete() throws PersistenceException {
        
        
        final IModel nouvelleEntite = (IModel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("nouvelle").build());
            }
        });

        final IModel entiteTrouvee = (IModel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(nouvelleEntite.getId());
            }
        });

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getModelMapper().delete(entiteTrouvee);
                return null;
            }
        });

        IModel entiteTrouvee2 = (IModel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().retreave(entiteTrouvee.getId());

            }
        });

        Assert.assertNull(entiteTrouvee2);
    }

    @Test
    public void testDeleteUnknownArticle() throws PersistenceException {
        final IModel entite = Model.builder()
                .id(-111L)
                .nom("model update test")
                .build();

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getModelMapper().delete(entite);
                return null;
            }
        });

    }
    
}
