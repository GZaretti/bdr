/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gza.transportPublic.datasource.db.DbMapperManager;
import gza.transportPublic.domain.Bus;
import gza.transportPublic.domain.Maintenance;
import gza.transportPublic.domain.Categorie;
import gza.transportPublic.domain.Mecanicien;
import gza.transportPublic.domain.Model;
import gza.transportPublic.idatasource.IMappeCmd;
import gza.transportPublic.idatasource.IMapperManager;
import gza.transportPublic.idatasource.exceptions.PersistenceException;
import gza.transportPublic.idomain.IBus;
import gza.transportPublic.idomain.IMaintenance;
import gza.transportPublic.idomain.ICategorie;
import gza.transportPublic.idomain.IMecanicien;
import gza.transportPublic.idomain.IModel;
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
public class maintenanceMapperTest {

    private final IMapperManager mapperManager;

    public maintenanceMapperTest() {
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

        ICategorie nouvelleCategorie = (Categorie) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        IBus nouveauBus = (IBus) mapperManager.executeAndClose(new IMappeCmd() {
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

        IMecanicien nouveauMecanicien = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {

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

        IMaintenance nouvelleEntite = (IMaintenance) mapperManager.executeAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().create(Maintenance.builder()
                        .bus(nouveauBus)
                        .mecanicien(nouveauMecanicien)
                        .dateDebut(new Date(6, 9, 2001))
                        .dateFin(new Date(6, 9, 2002))
                        .commentaires("bus a reperrer").build());
            }
        });
        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());
    }

    @Test
    public void testCreateEntite() throws PersistenceException {

        ICategorie nouvelleCategorie = (Categorie) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        IBus nouveauBus = (IBus) mapperManager.executeAndClose(new IMappeCmd() {
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

        IMecanicien nouveauMecanicien = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {

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

        final IMaintenance entite = Maintenance.builder()
                .bus(nouveauBus)
                .mecanicien(nouveauMecanicien)
                .dateDebut(new Date(6, 9, 2001))
                .dateFin(new Date(6, 9, 2002))
                .commentaires("bus a reperrer").build();

        IMaintenance nouvelleEntite = (Maintenance) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().create(entite);
            }
        });

        Assert.assertNotNull(nouvelleEntite);
        Assert.assertNotNull(nouvelleEntite.getId());

        Assert.assertEquals(entite.getCommentaires(), nouvelleEntite.getCommentaires());
    }

    @Test
    public void testRetreave() throws PersistenceException {

        ICategorie nouvelleCategorie = (Categorie) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        IBus nouveauBus = (IBus) mapperManager.executeAndClose(new IMappeCmd() {
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

        IMecanicien nouveauMecanicien = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {

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

        final IMaintenance entite = Maintenance.builder()
                .bus(nouveauBus)
                .mecanicien(nouveauMecanicien)
                .dateDebut(new Date(6, 9, 2001))
                .dateFin(new Date(6, 9, 2002))
                .commentaires("bus a reperrer").build();

        final IMaintenance nouvelleEntite = (IMaintenance) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().create(entite);
            }
        });

        List<IMaintenance> entiteTrouveeList = (List<IMaintenance>) mapperManager.transactionAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(nouvelleEntite.getCommentaires());
            }
        });

        Assert.assertTrue(entiteTrouveeList.size() > 0);
    }

    @Test
    public void testRetreaveById() throws PersistenceException {

        ICategorie nouvelleCategorie = (Categorie) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        IBus nouveauBus = (IBus) mapperManager.executeAndClose(new IMappeCmd() {
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

        IMecanicien nouveauMecanicien = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {

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

        final IMaintenance entite = Maintenance.builder()
                .bus(nouveauBus)
                .mecanicien(nouveauMecanicien)
                .dateDebut(new Date(6, 9, 2001))
                .dateFin(new Date(6, 9, 2002))
                .commentaires("bus a reperrer").build();

        final IMaintenance nouvelleEntite = (IMaintenance) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().create(entite);
            }
        });

        IMaintenance entiteTrouvee = (IMaintenance) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(
                        nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(nouvelleEntite, entiteTrouvee);
    }

    @Test
    public void testRetreaveByIdNotExist() throws PersistenceException {
        final Long id = 10L;

        IMaintenance entiteTrouvee = (IMaintenance) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(id);
            }
        });

        Assert.assertNull(entiteTrouvee);
    }

    @Test
    public void testUpdate() throws PersistenceException {

        ICategorie nouvelleCategorie = (Categorie) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        IBus nouveauBus = (IBus) mapperManager.executeAndClose(new IMappeCmd() {
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

        IMecanicien nouveauMecanicien = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {

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

        final IMaintenance entite = Maintenance.builder()
                .bus(nouveauBus)
                .mecanicien(nouveauMecanicien)
                .dateDebut(new Date(6, 9, 2001))
                .dateFin(new Date(6, 9, 2002))
                .commentaires("bus a reperrer").build();

        final IMaintenance nouvelleEntite = (IMaintenance) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().create(entite);
            }
        });

        IMaintenance entiteTrouvee = (IMaintenance) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {

                IMaintenance entiteTrouvee = mapperManager.getMaintenanceMapper().retreave(nouvelleEntite.getId());
                entiteTrouvee.setCommentaires(entiteTrouvee.getDescription() + "++++");

                mapperManager.getMaintenanceMapper().update(entiteTrouvee);

                return entiteTrouvee;
            }
        });

        IMaintenance entiteModifie = (IMaintenance) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(nouvelleEntite.getId());
            }
        });

        Assert.assertEquals(entiteTrouvee, entiteModifie);
    }

    @Test
    public void testDelete() throws PersistenceException {
        ICategorie nouvelleCategorie = (Categorie) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getCategorieMapper().create(Categorie.builder().nom("Categorie Test").build());
            }
        });
        IModel nouveauModel = (Model) mapperManager.executeAndClose(new IMappeCmd() {

            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getModelMapper().create(Model.builder().nom("Model Test").build());
            }
        });

        IBus nouveauBus = (IBus) mapperManager.executeAndClose(new IMappeCmd() {
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

        IMecanicien nouveauMecanicien = (IMecanicien) mapperManager.executeAndClose(new IMappeCmd() {

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

        final IMaintenance entite = Maintenance.builder()
                .bus(nouveauBus)
                .mecanicien(nouveauMecanicien)
                .dateDebut(new Date(6, 9, 2001))
                .dateFin(new Date(6, 9, 2002))
                .commentaires("bus a reperrer").build();

        final IMaintenance nouvelleEntite = (IMaintenance) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().create(entite);
            }
        });

        final IMaintenance entiteTrouvee = (IMaintenance) mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(nouvelleEntite.getId());
            }
        });

        mapperManager.transaction(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                mapperManager.getMaintenanceMapper().delete(entiteTrouvee);
                return null;
            }
        });

        IMaintenance entiteTrouvee2 = (IMaintenance) mapperManager.transactionAndClose(new IMappeCmd() {
            @Override
            public Object execute(IMapperManager mapperManager) throws PersistenceException {
                return mapperManager.getMaintenanceMapper().retreave(entiteTrouvee.getId());

            }
        });

        Assert.assertNull(entiteTrouvee2);
    }
}
