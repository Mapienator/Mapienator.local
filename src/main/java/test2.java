import java.sql.*;

public class test2 {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/java?","root","jhyeurcghwe78trh3j478hf67gr7hfjguytddf64v5");
        Statement select = connection.createStatement();
        ResultSet rs = select.executeQuery("SELECT * FROM java.cabbages LIMIT 1");
        String debugString = null;
        while (rs.next()) {
            System.out.print(rs.getString("id") + " ");
            System.out.print(rs.getString("Server") + " ");
            System.out.print(rs.getString("User") + " ");
            System.out.print(rs.getString("Value1") + " ");
            System.out.print(rs.getString("Value2") + " ");
            System.out.println(rs.getString("Value3") + " ");
            System.out.println("NEXT" + " ");
            debugString = rs.getString("id");
        }
        System.out.println(debugString);






    }

    static int wordCounter(String str, String word) {

        String a[] = str.split(" ");
        int count = 0;
        for (
                int i = 0;
                i < a.length; i++) {

            if (word.equals(a[i]))
                count++;
        }
        System.out.println(count);
        return count;
    }
}