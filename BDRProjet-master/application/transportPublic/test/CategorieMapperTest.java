/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.domain.Categorie;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.ICategorie;
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
public class CategorieMapperTest {    
    private final IMapperManager mapperManager;
    public CategorieMapperTest() {
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
        ICategorie nouvelleEntite = (ICategorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("CategorieOne").build());
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());
    }
    
    @Test
    public void testCreateEntite() throws PersistenceException {
        final ICategorie entite = Categorie.builder()
                .nom("Nouvelle article")
                .build();

        ICategorie nouvelleEntite = (Categorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(entite);
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());

        Assert.assertEquals(entite.getNom(), nouvelleEntite.getNom());
    }
    
    @Test
    public void testRetreave() throws PersistenceException {
        final ICategorie entite = Categorie.builder()
                .nom("CategorieTest")
                .build();

        final ICategorie nouvelleEntite = (ICategorie) mapperManager.transaction(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(entite);
            }
        });

        List<ICategorie> entiteTrouveeList = (List<ICategorie>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(nouvelleEntite.getNom());
            }
        });

        Assert.assertTrue(entiteTrouveeList.size() > 0);
        Assert.assertTrue(entiteTrouveeList.contains(nouvelleEntite));
    }
    
    @Test
    public void testRetreaveById() throws PersistenceException {
        final ICategorie entite = Categorie.builder()
                .nom("categorie retreave test")
                .build();

        final ICategorie nouvelleEntite = (ICategorie) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(entite);
            }
        });

        ICategorie entiteTrouvee = (ICategorie) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(nouvelleEntite, entiteTrouvee);
    }
    
    @Test
    public void testRetreaveByIdNotExist() throws PersistenceException {
        final Long id = 10L;

        ICategorie entiteTrouvee = (ICategorie) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(id);
            }
        });

        Assert.assertNull(entiteTrouvee);
    }
    
    @Test
    public void testUpdate() throws PersistenceException {
        final ICategorie entite = Categorie.builder()
                .nom("categorie update test")
                .build();

        final ICategorie nouvelleEntite = (ICategorie) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(entite);
            }
        });

        ICategorie entiteTrouvee = (ICategorie) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                ICategorie entiteTrouvee = mapperManager.getCategorieMapper().retreave(nouvelleEntite.getId());
                entiteTrouvee.setNom(entiteTrouvee.getNom() + "++++");

                mapperManager.getCategorieMapper().update(entiteTrouvee);

                return entiteTrouvee;
            }
        });

        ICategorie entiteModifie = (ICategorie) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(entiteTrouvee, entiteModifie);
    }

    @Test
    public void testDelete() throws PersistenceException {
        
        
        final ICategorie nouvelleEntite = (ICategorie) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("nouvelle").build());
            }
        });

        final ICategorie entiteTrouvee = (ICategorie) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(nouvelleEntite.getId());
            }
        });

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getCategorieMapper().delete(entiteTrouvee);
                return null;
            }
        });

        ICategorie entiteTrouvee2 = (ICategorie) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().retreave(entiteTrouvee.getId());

            }
        });

        Assert.assertNull(entiteTrouvee2);
    }

    @Test
    public void testDeleteUnknownArticle() throws PersistenceException {
        final ICategorie entite = Categorie.builder()
                .id(-111L)
                .nom("categorie update test")
                .build();

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getCategorieMapper().delete(entite);
                return null;
            }
        });

    }
    
}
