/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdrlabo04;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZEED
 */
public class BDRLabo04 extends Thread {

    /**
     * @param args the command line arguments
     */
    private static final Logger mLog = Logger.getLogger(BDRLabo04.class.getName());
    private Connection mConnection;

    private void queryTest() throws SQLException {
        try (Statement statement = mConnection.createStatement()) {
            //Retrieve by column name       
            String sql = "SELECT C.customer_id AS numero, C.first_name AS prenom, C.last_name AS nom "
                    + "FROM customer AS C INNER JOIN address AS A ON C.address_id = A.address_id "
                    + "WHERE C.store_id = 2 AND A.city_id = 321 AND C.active ORDER BY C.last_name;";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("numero");
                String first = rs.getString("prenom");
                String name = rs.getString("nom");

                //Display values
                System.out.print("Numero: " + id + ", Prenom: " + first + ", Nom: " + name);
            }
            rs.close();
        }
    }

    private void init() throws SQLException, ClassNotFoundException {
        // Example URL, update accordingly !
        String url = "jdbc:mysql://localhost:3306/sakila?user=root&password=Google2018$";
        mConnection = DriverManager.getConnection(url);
    }

    private void close() throws SQLException {
        if (mConnection != null) {
            mConnection.close();
        }
    }

    @Override
    public void run() {
        try {
            init();
            //queryTest();
            queryOne();
            queryTwo();
        } catch (SQLException e) {
            mLog.log(Level.SEVERE, e.getMessage());
        } catch (ClassNotFoundException e) {
            mLog.log(Level.SEVERE, e.getMessage());
        } finally {
            try {
                close();
            } catch (SQLException e) {
                mLog.log(Level.SEVERE, e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        new BDRLabo04().run();
    }

    public void queryOne() throws SQLException {
        try (Statement statement = mConnection.createStatement()) {
            //Retrieve by column name       
            String sql = "SELECT * FROM sakila.film LIMIT 10;";
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("film_id");
                String title = rs.getString("title");
                String description = rs.getString("description");

                //Display values
                System.out.print("film_id: " + id
                        + " title : " + title
                        + " description : " + description + "\n");
            }
            rs.close();
        }
    }

    public void queryTwo() throws SQLException {

        for (int i = 0; i < 10; i++) {
            final String SQL_INSERT = "INSERT INTO `sakila`.`actor`\n"
                    + "(`first_name`,\n"
                    + "`last_name`)\n"
                    + "VALUES(\n"
                    + "?,\n"
                    + "?\n); ";
            try (PreparedStatement preparedStatement = mConnection.prepareStatement(SQL_INSERT)) {
                preparedStatement.setString(1, ZonedDateTime.now().toString()+i);
                preparedStatement.setString(2, ZonedDateTime.now().toString()+i);

                preparedStatement.executeUpdate();
                //Display values
            }
        }
    }

}
