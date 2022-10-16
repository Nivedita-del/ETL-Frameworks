package Loaders;

import Connectors.DBConnector;
import Extractors.DBExtractors;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBLoaders {

    @Autowired
    public DBExtractors extractors;
    @Autowired
    public DBConnector connector;

    public ResultSet RDSLoader() throws SQLException {
        Connection con = connector.RdsConnector();
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("");
        return res;
    }
    public ResultSet RDSExtractor() throws SQLException {
        Connection con = connector.RdsConnector();
        Statement statement = con.createStatement();
        ResultSet res = statement.executeQuery("");
        return res;
    }
}
