package ua.com.alevel;


import ua.com.alevel.dao.OfficeDao;
import ua.com.alevel.dao.OfficeDaoImpl;

public class Main {
    public static void main(String[] args) {
        OfficeDao dao = new OfficeDaoImpl();
        dao.readFromDB();
    }
}
