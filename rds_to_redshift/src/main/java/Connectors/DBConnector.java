package Connectors;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


@Log4j2
@Component
public class DBConnector {

    public static Connection RdsConnector() {
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/nive","root","root");
            log.info("connection established", con.getClientInfo());
            return con;
        }
        catch(Exception e)
        {
            log.info("error occured due to ", e);
        }
        return null;
    }

    public static Connection RedshiftConnector(){
        try{
            Class.forName("org.postgresql.Driver");
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5396/nive", "root", "root");
            log.info("connection established", con.getClientInfo());
            return con;
        }
        catch (Exception e){
            log.info("error occured due to", e);
        }
        return null;
    }
}
