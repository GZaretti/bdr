/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.domain.Mecanicien;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IMecanicien;
import java.util.Date;
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
public class MechanicienMapperTest {

    private final IMapperManager mapperManager;

    public MechanicienMapperTest() {
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
        IMecanicien nouvelleEntite = (IMecanicien) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().create(Mecanicien.builder()
                        .nom("MecanicienOne")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6, 9, 2001))
                        .pourcentage(80)
                        .noCertification("LaMeilleure").build());
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());
    }

   @Test
    public void testCreateEntite() throws PersistenceException {
        final IMecanicien entite = Mecanicien.builder()
                 .nom("Mecanic")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6, 9, 2001))
                        .pourcentage(80)
                        .noCertification("LaMeilleure").build();

        IMecanicien nouvelleEntite = (Mecanicien) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().create(entite);
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());

        Assert.assertEquals(entite.getNom(), nouvelleEntite.getNom());
    }
    
    @Test
    public void testRetreave() throws PersistenceException {
        final IMecanicien entite = Mecanicien.builder()
                .nom("Mecanic")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6, 9, 2001))
                        .pourcentage(80)
                        .noCertification("LaMeilleure").build();

        final IMecanicien nouvelleEntite = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().create(entite);
            }
        });

        List<IMecanicien> entiteTrouveeList = (List<IMecanicien>) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(nouvelleEntite.getNom());
            }
        });

        Assert.assertTrue(entiteTrouveeList.size() > 0);
        Assert.assertTrue(entiteTrouveeList.contains(nouvelleEntite));
    }
    
    @Test
    public void testRetreaveById() throws PersistenceException {
        final IMecanicien entite = Mecanicien.builder()
                .nom("Mecanic")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6, 9, 2001))
                        .pourcentage(80)
                        .noCertification("LaMeilleure").build();

        final IMecanicien nouvelleEntite = (IMecanicien) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().create(entite);
            }
        });

        IMecanicien entiteTrouvee = (IMecanicien) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(nouvelleEntite, entiteTrouvee);
    }
    
    @Test
    public void testRetreaveByIdNotExist() throws PersistenceException {
        final Long id = 10L;

        IMecanicien entiteTrouvee = (IMecanicien) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(id);
            }
        });

        Assert.assertNull(entiteTrouvee);
    }
    
    @Test
    public void testUpdate() throws PersistenceException {
        final IMecanicien entite = Mecanicien.builder()
               .nom("Mecanic")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6, 9, 2001))
                        .pourcentage(80)
                        .noCertification("LaMeilleure").build();

        final IMecanicien nouvelleEntite = (IMecanicien) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().create(entite);
            }
        });

        IMecanicien entiteTrouvee = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                IMecanicien e = mapperManager.getMecanicienMapper().retreave(nouvelleEntite.getId());
                e.setNom(e.getNom() + "ad");

                mapperManager.getMecanicienMapper().update(e);

                return e;
            }
        });
    

        IMecanicien entiteModifie = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(entiteTrouvee.getId());
            }
        });

        Assert.assertEquals(entiteTrouvee, entiteModifie);
    }

    @Test
    public void testDelete() throws PersistenceException {
        
        
        final IMecanicien nouvelleEntite = (IMecanicien) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().create(Mecanicien.builder()
                        .nom("Mecanic")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6, 9, 2001))
                        .pourcentage(80)
                        .noCertification("LaMeilleure").build());
            }
        });

        final IMecanicien entiteTrouvee = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(nouvelleEntite.getId());
            }
        });

        mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getMecanicienMapper().delete(entiteTrouvee);
                return null;
            }
        });

        IMecanicien entiteTrouvee2 = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMecanicienMapper().retreave(entiteTrouvee.getId());

            }
        });

        Assert.assertNull(entiteTrouvee2);
    }

}
