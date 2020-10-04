/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gza.transportPublic.datasource.db;

import gza.transportPublic.idatasource.exceptions.PersistenceException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ZEED
 */
public class DatabaseSetUp {

    private final DbMapperManager mapperManager;

    public DatabaseSetUp(DbMapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public void createTables() throws PersistenceException {
        try {
            Statement statement = mapperManager.getConnection().createStatement();
            statement.addBatch(DiffNotHandled.CREATE_TABLE_DEPOT);
            statement.addBatch(DiffNotHandled.CREATE_TABLE_PLACE);
            statement.addBatch(DiffNotHandled.SQL_INSERTS_SDATAS_DEPOT);
            statement.addBatch(DiffNotHandled.SQL_INSERTS_SDATAS_PLACE);
            statement.addBatch(ModelMapper.CREATE_TABLE);
            statement.addBatch(CategorieMapper.CREATE_TABLE);
            statement.addBatch(EquipementMapper.CREATE_TABLE);
            statement.addBatch(BusMapper.CREATE_TABLE);
            statement.addBatch(PersonnelMapper.CREATE_TABLE);
            statement.addBatch(MecanicienMapper.CREATE_TABLE);
            statement.addBatch(MaintenanceMapper.CREATE_TABLE);

            statement.addBatch(DiffNotHandled.CREATE_TABLE_CLIENT);
            statement.addBatch(DiffNotHandled.CREATE_TABLE_RESERVATION);
            statement.addBatch(DiffNotHandled.CREATE_TABLE_NETOYEUR);
            statement.addBatch(DiffNotHandled.CREATE_TABLE_CHAUFFEUR);
            statement.addBatch(DiffNotHandled.CREATE_TABLE_APPRENTI);

            for (String s : DiffNotHandled.CREATE_TABLES_ASSOCIATIVES.split(";")) {
                statement.addBatch(s + ";");
            }


            statement.executeBatch();
        } catch (SQLException ex) {
            throw new PersistenceException(ex);
        }

    }

    public void dropTables() throws PersistenceException {
        try {
            Statement statement = mapperManager.getConnection().createStatement();

            for (String s : DiffNotHandled.DROP_TABLES_ASSOCIATIVES.split(";")) {
                statement.addBatch(s + ";");
            }

            statement.addBatch(DiffNotHandled.DROP_TABLE_CLIENT);
            statement.addBatch(DiffNotHandled.DROP_TABLE_RESERVATION);
            statement.addBatch(DiffNotHandled.DROP_TABLE_NETOYEUR);
            statement.addBatch(DiffNotHandled.DROP_TABLE_CHAUFFEUR);
            statement.addBatch(DiffNotHandled.DROP_TABLE_APPRENTI);

            statement.addBatch(MaintenanceMapper.DROP_TABLE);
            statement.addBatch(BusMapper.DROP_TABLE);
            statement.addBatch(DiffNotHandled.DROP_TABLE_PLACE);
            statement.addBatch(DiffNotHandled.DROP_TABLE_DEPOT);
            statement.addBatch(ModelMapper.DROP_TABLE);
            statement.addBatch(CategorieMapper.DROP_TABLE);
            statement.addBatch(EquipementMapper.DROP_TABLE);
            statement.addBatch(MecanicienMapper.DROP_TABLE);
            statement.addBatch(PersonnelMapper.DROP_TABLE);

            statement.executeBatch();
        } catch (SQLException ex) {
           throw new PersistenceException(ex);
        }
    }

    public void insertDatas() {

    }
}
