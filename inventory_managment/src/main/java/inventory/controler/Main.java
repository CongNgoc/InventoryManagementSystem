package inventory.controler;

public class Main {

    static String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
//        ConnectDB connDB = new ConnectDB();
//        connDB.open();
//        String queryString = "SELECT * FROM USERS WHERE USER_NAME = 'ngoccong'";
//
//        connDB.printInfo(queryString);
//        connDB.close();
//        System.out.println("Random String: " + getAlphaNumericString(10));
        int i = 5;
        while (i != 5){
            System.out.println(i);
        };
    }
}
