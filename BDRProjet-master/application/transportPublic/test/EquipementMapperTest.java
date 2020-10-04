/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.domain.Equipement;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IEquipement;
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
public class EquipementMapperTest {    
    private final IMapperManager mapperManager;
    public EquipementMapperTest() {
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
        IEquipement nouvelleEntite = (IEquipement) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().create(Equipement.builder().nom("EquipementOne").build());
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());
    }
    
    @Test
    public void testCreateEntite() throws PersistenceException {
        final IEquipement entite = Equipement.builder()
                .nom("Nouvelle article")
                .build();

        IEquipement nouvelleEntite = (Equipement) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().create(entite);
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());

        Assert.assertEquals(entite.getNom(), nouvelleEntite.getNom());
    }
    
    @Test
    public void testRetreave() throws PersistenceException {
        final IEquipement entite = Equipement.builder()
                .nom("EquipementTest")
                .build();

        final IEquipement nouvelleEntite = (IEquipement) mapperManager.transaction(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().create(entite);
            }
        });

        List<IEquipement> entiteTrouveeList = (List<IEquipement>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(nouvelleEntite.getNom());
            }
        });

        Assert.assertTrue(entiteTrouveeList.size() > 0);
        Assert.assertTrue(entiteTrouveeList.contains(nouvelleEntite));
    }
    
    @Test
    public void testRetreaveById() throws PersistenceException {
        final IEquipement entite = Equipement.builder()
                .nom("equipement retreave test")
                .build();

        final IEquipement nouvelleEntite = (IEquipement) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().create(entite);
            }
        });

        IEquipement entiteTrouvee = (IEquipement) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(nouvelleEntite, entiteTrouvee);
    }
    
    @Test
    public void testRetreaveByIdNotExist() throws PersistenceException {
        final Long id = 10L;

        IEquipement entiteTrouvee = (IEquipement) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(id);
            }
        });

        Assert.assertNull(entiteTrouvee);
    }
    
    @Test
    public void testUpdate() throws PersistenceException {
        final IEquipement entite = Equipement.builder()
                .nom("equipement update test")
                .build();

        final IEquipement nouvelleEntite = (IEquipement) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().create(entite);
            }
        });

        IEquipement entiteTrouvee = (IEquipement) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                IEquipement entiteTrouvee = mapperManager.getEquipementMapper().retreave(nouvelleEntite.getId());
                entiteTrouvee.setNom(entiteTrouvee.getNom() + "++++");

                mapperManager.getEquipementMapper().update(entiteTrouvee);

                return entiteTrouvee;
            }
        });

        IEquipement entiteModifie = (IEquipement) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(entiteTrouvee, entiteModifie);
    }

    @Test
    public void testDelete() throws PersistenceException {
        
        
        final IEquipement nouvelleEntite = (IEquipement) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().create(Equipement.builder().nom("nouvelle").build());
            }
        });

        final IEquipement entiteTrouvee = (IEquipement) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(nouvelleEntite.getId());
            }
        });

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getEquipementMapper().delete(entiteTrouvee);
                return null;
            }
        });

        IEquipement entiteTrouvee2 = (IEquipement) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getEquipementMapper().retreave(entiteTrouvee.getId());

            }
        });

        Assert.assertNull(entiteTrouvee2);
    }

    @Test
    public void testDeleteUnknownArticle() throws PersistenceException {
        final IEquipement entite = Equipement.builder()
                .id(-111L)
                .nom("equipement update test")
                .build();

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getEquipementMapper().delete(entite);
                return null;
            }
        });

    }
    
}
