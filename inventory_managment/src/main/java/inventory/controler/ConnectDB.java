package inventory.controler;

import java.sql.*;

public class ConnectDB {
    private Connection conn = null;

    public Connection getCon() {
        return conn;
    }

    public void setCon(Connection con) {
        this.conn = con;
    }

    public void open() {
        //Declare Connect Infomation
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "qlkho_admin";
        String pass = "oracle11";

        try {
            //Load the drive class
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //Create Connection object
            conn = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertEmp() throws SQLException {
        String sql = "INSERT INTO SKILL (SKILLNO, SKILLNAME) VALUES (?, ?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, 6);
        statement.setString(2, "HTML");

        int rowsInserted = statement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("A NEW SKILL WAS INSERTED SUCCESSFULLY!");
        }
    }

    public void updateEmp() throws SQLException {
        String sql = "UPDATE SKILL SET SKILLNAME=? WHERE SKILLNO=?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "JavaScript");
        statement.setInt(2, 1);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("A EXISTING SKILL WAS UPDATED SUCCESSFULLY!");
        }
    }

    public void deleteEmp() throws SQLException {
        String sql = "DELETE FROM SKILL WHERE SKILLNO=?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, 6);

        int rowsDeleted = statement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("A SKILL WAS DELETED SUCCESSFULLY!");
        }
    }

    public void printInfo(String queryString) {
        //Create Stetament object
        Statement sttm = null;
        try {
            sttm = conn.createStatement();
            //Execute query
            ResultSet rs = sttm.executeQuery(queryString);
            System.out.println(rs.getClass());
            while(rs.next()) {
                System.out.println(rs.getShort("user_Id") + "  "
                        + rs.getString("first_Name")
                        + rs.getTime("CREATE_DATE"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
