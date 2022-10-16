package Extractors;

import Connectors.DBConnector;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBExtractors {
    @Autowired
    public DBConnector DBConnector;

    public ResultSet RDSExtractor() throws SQLException {
        Connection con = DBConnector.RdsConnector();
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("");
        return res;
    }
    public ResultSet RedshiftExtractor() throws SQLException {
        Connection con = DBConnector.RedshiftConnector();
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("");
        return res;
    }
}
