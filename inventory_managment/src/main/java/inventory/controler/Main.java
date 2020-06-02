package inventory.controler;

public class Main {
    public static void main(String[] args) {
        ConnectDB connDB = new ConnectDB();
        connDB.open();
        String queryString = "SELECT * FROM USERS WHERE USER_NAME = 'ngoccong'";

        connDB.printInfo(queryString);
        connDB.close();
    }
}
