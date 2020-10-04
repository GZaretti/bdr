/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.domain.Personnel;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IPersonnel;
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
public class PersonnelMapperTest {

    private final IMapperManager mapperManager;

    public PersonnelMapperTest() {
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
        IPersonnel nouvelleEntite = (IPersonnel) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().create(Personnel.builder()
                        .nom("PersonnelOne")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6, 9, 2001))
                        .pourcentage(80).build());
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());
    }

    @Test
    public void testCreateEntite() throws PersistenceException {
        final IPersonnel entite = Personnel.builder()
                .nom("PersonnelOne")
                .prenom("PersonneOne")
                .dateEmbauche(new Date(6, 9, 2001))
                .pourcentage(80).build();

        IPersonnel nouvelleEntite = (Personnel) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().create(entite);
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());

        Assert.assertEquals(entite.getNom(), nouvelleEntite.getNom());
    }
    
    @Test
    public void testRetreave() throws PersistenceException {
        final IPersonnel entite = Personnel.builder()
                .nom("PersonnelOne")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6,9,2001))
                        .pourcentage(80).build();

        final IPersonnel nouvelleEntite = (IPersonnel) mapperManager.transaction(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().create(entite);
            }
        });

        List<IPersonnel> entiteTrouveeList = (List<IPersonnel>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().retreave(nouvelleEntite.getNom());
            }
        });

        Assert.assertTrue(entiteTrouveeList.size() > 0);
        Assert.assertTrue(entiteTrouveeList.contains(nouvelleEntite));
    }
    
    @Test
    public void testRetreaveById() throws PersistenceException {
        final IPersonnel entite = Personnel.builder()
                .nom("PersonnelOne")
                        .prenom("PersonneOne")
                        .dateEmbauche(new Date(6,9,2001))
                        .pourcentage(80).build();

        final IPersonnel nouvelleEntite = (IPersonnel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().create(entite);
            }
        });

        IPersonnel entiteTrouvee = (IPersonnel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(nouvelleEntite, entiteTrouvee);
    }
    
    @Test
    public void testRetreaveByIdNotExist() throws PersistenceException {
        final Long id = 10L;

        IPersonnel entiteTrouvee = (IPersonnel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().retreave(id);
            }
        });

        Assert.assertNull(entiteTrouvee);
    }
    
    @Test
    public void testUpdate() throws PersistenceException {
        final IPersonnel entite = Personnel.builder()
                .nom("Psone")
                        .prenom("Perse")
                        .dateEmbauche(new Date(6,9,2001))
                        .pourcentage(80).build();

        final IPersonnel nouvelleEntite = (IPersonnel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().create(entite);
            }
        });

        IPersonnel entiteTrouvee = (IPersonnel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                IPersonnel e = mapperManager.getPersonnelMapper().retreave(nouvelleEntite.getId());
                e.setNom(e.getNom() + "ad");

                mapperManager.getPersonnelMapper().update(e);

                return e;
            }
        });

        IPersonnel entiteModifie = (IPersonnel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().retreave(entiteTrouvee.getId());
            }
        });

        Assert.assertEquals(entiteTrouvee, entiteModifie);
    }

    @Test
    public void testDelete() throws PersistenceException {
        
        
        final IPersonnel nouvelleEntite = (IPersonnel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().create(Personnel.builder() .nom("Psone")
                        .prenom("Perse")
                        .dateEmbauche(new Date(6,9,2001))
                        .pourcentage(80).build());
            }
        });

        final IPersonnel entiteTrouvee = (IPersonnel) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().retreave(nouvelleEntite.getId());
            }
        });

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getPersonnelMapper().delete(entiteTrouvee);
                return null;
            }
        });

        IPersonnel entiteTrouvee2 = (IPersonnel) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getPersonnelMapper().retreave(entiteTrouvee.getId());

            }
        });

        Assert.assertNull(entiteTrouvee2);
    }

}
