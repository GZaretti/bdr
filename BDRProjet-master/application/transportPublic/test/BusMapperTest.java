/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.domain.Bus;
import gza.transportPublic.domain.Categorie;
import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IBus;
import gza.transportPublic.idomain.ICategorie;
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
public class BusMapperTest {

    private final IMapperManager mapperManager;

    public BusMapperTest() {
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

        ICategorie nouvelleCategorie = (Categorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        IBus nouvelleEntite = (IBus) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().create(Bus.builder()
                        .categorie(nouvelleCategorie)
                        .model(nouveauModel)
                        .nbPassagerMax(10)
                        .noMatricule("75DEA")
                        .handicap(true)
                        .build());
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());
    }

    @Test
    public void testCreateEntite() throws PersistenceException {

        ICategorie nouvelleCategorie = (Categorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        final IBus entite = Bus.builder()
                .categorie(nouvelleCategorie)
                .model(nouveauModel)
                .nbPassagerMax(10)
                .noMatricule("75DEA")
                .handicap(true)
                .build();

        IBus nouvelleEntite = (Bus) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().create(entite);
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());

        Assert.assertEquals(entite.getNoMatricule(), nouvelleEntite.getNoMatricule());
    }

    @Test
    public void testRetreave() throws PersistenceException {
        ICategorie nouvelleCategorie = (Categorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        final IBus entite = Bus.builder()
                .categorie(nouvelleCategorie)
                .model(nouveauModel)
                .nbPassagerMax(10)
                .noMatricule("75DEA")
                .handicap(true)
                .build();

        final IBus nouvelleEntite = (IBus) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().create(entite);
            }
        });

        List<IBus> entiteTrouveeList = (List<IBus>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(nouvelleEntite.getNoMatricule());
            }
        });

        Assert.assertTrue(entiteTrouveeList.size() > 0);
    }

    @Test
    public void testRetreaveById() throws PersistenceException {
        ICategorie nouvelleCategorie = (Categorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        final IBus entite = Bus.builder()
                .categorie(nouvelleCategorie)
                .model(nouveauModel)
                .nbPassagerMax(10)
                .noMatricule("75DEA")
                .handicap(true)
                .build();

        final IBus nouvelleEntite = (IBus) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().create(entite);
            }
        });

        IBus entiteTrouvee = (IBus) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(
                        nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(nouvelleEntite, entiteTrouvee);
    }

    @Test
    public void testRetreaveByIdNotExist() throws PersistenceException {
        final Long id = 10L;

        IBus entiteTrouvee = (IBus) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(id);
            }
        });

        Assert.assertNull(entiteTrouvee);
    }

    @Test
    public void testUpdate() throws PersistenceException {
        ICategorie nouvelleCategorie = (Categorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        final IBus entite = Bus.builder()
                .categorie(nouvelleCategorie)
                .model(nouveauModel)
                .nbPassagerMax(10)
                .noMatricule("75DEA")
                .handicap(true)
                .build();

        final IBus nouvelleEntite = (IBus) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().create(entite);
            }
        });

        IBus entiteTrouvee = (IBus) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                IBus entiteTrouvee = mapperManager.getBusMapper().retreave(nouvelleEntite.getId());
                entiteTrouvee.setNoMatricule(entiteTrouvee.getNoMatricule() + "++++");

                mapperManager.getBusMapper().update(entiteTrouvee);

                return entiteTrouvee;
            }
        });

        IBus entiteModifie = (IBus) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(entiteTrouvee, entiteModifie);
    }

    @Test
    public void testDelete() throws PersistenceException {

        ICategorie nouvelleCategorie = (Categorie) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        final IBus entite = Bus.builder()
                .categorie(nouvelleCategorie)
                .model(nouveauModel)
                .nbPassagerMax(10)
                .noMatricule("75DEA")
                .handicap(true)
                .build();

        final IBus nouvelleEntite = (IBus) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().create(entite);
            }
        });

        final IBus entiteTrouvee = (IBus) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(nouvelleEntite.getId());
            }
        });

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getBusMapper().delete(entiteTrouvee);
                return null;
            }
        });

        IBus entiteTrouvee2 = (IBus) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getBusMapper().retreave(entiteTrouvee.getId());

            }
        });

        Assert.assertNull(entiteTrouvee2);
    }
}
