package me.defiancecoding.defiantsecurity.util.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;
import me.defiancecoding.defiantsecurity.util.mysql.mysql.MySQL;

public class SQLUtil {

	private Connection c;
	private MySQL sql;
	private DefiantSecurity main;

	private ConfigGetter cfg;
	private String user;
	private String pass;
	private String hostname;
	private String port;
	private String database;

	public SQLUtil(DefiantSecurity main) {
		this.main = main;
		setupConfigValues();
	}

	private void setupConfigValues() {
		cfg = new ConfigGetter(main);
		this.user = cfg.getMySQL().getString("MySQL.User");
		this.pass = cfg.getMySQL().getString("MySQL.Password");
		this.hostname = cfg.getMySQL().getString("MySQL.Hostname");
		this.port = cfg.getMySQL().getString("MySQL.Port");
		this.database = cfg.getMySQL().getString("MySQL.Database");
		sql = new MySQL(this.hostname, this.port, this.database, this.user, this.pass);
		try {
			c = sql.openConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			c = null;
			e.printStackTrace();
		}
	}

	public void createTable(String tableName) throws SQLException {
		Statement statement = c.createStatement();
		String upd1 = "CREATE TABLE IF NOT EXISTS `" + tableName + "` ( `UID` "
				+ "INT NULL DEFAULT NULL AUTO_INCREMENT COMMENT 'UID' , `IP` TEXT NOT NULL COMMENT 'PlayerIP' ,"
				+ " UNIQUE `UID` (`UID`)) ENGINE = InnoDB;";
		statement.executeUpdate(upd1);
		c.close();
	}

	public void deleteTable(String tableName) throws SQLException {
		Statement statement = c.createStatement();
		String upd1 = "DROP TABLE `" + tableName + "`;";
		statement.executeUpdate(upd1);
		c.close();
	}

	public boolean valueExists(String table, String data) {
		try {
			String SQLQuery = ("SELECT * FROM `" + table + "` WHERE IP= '" + data + ";");
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(SQLQuery);
			Boolean hasNext = null;
			if (rs.next())
				hasNext = true;
			if (!(rs.next()))
				hasNext = false;
			c.close();
			return hasNext;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void addValue(String table, String IP) {
		PreparedStatement ps = null;
		try {
			ps = c.prepareStatement("INSERT INTO " + table + " (`UID`, `IP`) VALUES (?,?);");
			ps.setString(1, "NULL");
			ps.setString(2, "'" + IP + "'");
			ps.executeUpdate();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeValue(String table, String IP) {
		String query = "DELETE FROM " + table + " WHERE IP=?;";
		try {
			PreparedStatement statement = c.prepareStatement(query);
			statement.setString(1, IP);
			statement.executeUpdate();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
