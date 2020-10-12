package ua.com.alevel.dao;

import ua.com.alevel.dto.Office;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class OfficeDaoImpl implements OfficeDao {
    private static final ConnectionDao CONNECTOR = new ConnectionDaoImpl();

    @Override
    public void readFromDB() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = CONNECTOR.getConnection();
            statement = connection.prepareStatement("SELECT * FROM offices LIMIT ? OFFSET ?");
            loadData(statement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                connection.close();
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private void loadData(PreparedStatement statement) throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to load data?(1 - YES, Other number - NO)");
        int act = sc.nextInt();
        if (act == 1) {
            readInfo(statement, sc);
        } else {
            System.exit(1);
        }
    }

    private void readInfo(PreparedStatement statement, Scanner sc) throws SQLException {
        int act;
        int limit = 2, offset = -2;
        boolean flag = true;
        do {
            offset += limit;
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            statement.executeQuery();
            ResultSet res = statement.getResultSet();
            while (res.next()) {
                Office office = setOfficeIntoFromDB(res);
                printInfo(office);
            }
            System.out.println("Do you want to continue? (1 - YES, Other number - NO)");
            act = sc.nextInt();
            if (act != 1) {
                flag = false;
            }
        } while (flag);
    }

    private Office setOfficeIntoFromDB(ResultSet res) throws SQLException {
        Office office = new Office();
        office.setOfficeCode(res.getInt("officeCode"));
        office.setState(res.getString("state"));
        office.setCountry(res.getString("country"));
        office.setPhone(res.getString("phone"));
        office.setCity(res.getString("phone"));
        office.setAddsetsLine1(res.getString("addressLine1"));
        office.setAddsetsLine2(res.getString("addressLine2"));
        office.setPostalCode(res.getString("postalCode"));
        office.setTerritory(res.getString("territory"));
        return office;
    }

    private void printInfo(Office office) {
        System.out.println(office.toString());
    }
}
