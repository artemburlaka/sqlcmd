import java.sql.*;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Artem on 07.01.2016.
 */
public class Main {
    public static void main(String[] argv) throws ClassNotFoundException, SQLException {
            Class.forName("org.postgresql.Driver");
        Connection connection =  DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/sqlcmd", "postgres",
                "9369");
        //insert
        Statement stmt = connection.createStatement();
        String insertS = "INSERT INTO public.user (NAME, PASSWORD)" +
                "VALUES ('Stiven', 'Pupkin')";
        stmt.executeUpdate(insertS);
        stmt.close();

        //select
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM public.user WHERE id > 10");
        while (rs.next())
        {
            System.out.println("id:" + rs.getString("id"));
            System.out.println("name:" + rs.getString("name"));
            System.out.println("password:" + rs.getString("password"));
            System.out.println("-----");
        } rs.close();
        stmt.close();

//        //select
//        stmt = connection.createStatement();
//        ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
//        while (rs.next())
//        {
//            System.out.println(rs.getString("table_name"));
//            System.out.println("-----");
//        } rs.close();
//        stmt.close();

        //select
        stmt = connection.createStatement();
        rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
        String[] tables = new String[100];
        int index = 0;
        while (rs.next())
        {
            tables[index++] = rs.getString("table_name");
        }
        tables = Arrays.copyOf(tables, index, String[].class);

        rs.close();
        stmt.close();

        //delete
        stmt = connection.createStatement();
        stmt.executeUpdate("DELETE FROM public.user " +
                "WHERE id > 10 AND id < 100");
        stmt.close();

        //update
        PreparedStatement ps = connection.prepareStatement("UPDATE PUBLIC.user SET password = ? WHERE id > 3");
        ps.setString(1, "password_" + new Random().nextInt());
        ps.executeUpdate();
        ps.close();

        connection.close();
    }
}
