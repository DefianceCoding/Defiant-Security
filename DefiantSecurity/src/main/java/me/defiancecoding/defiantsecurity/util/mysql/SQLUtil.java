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
	private DefiantSecurity main;

	public SQLUtil(DefiantSecurity main) {
		this.main = main;
	}

	private ConfigGetter cfg = new ConfigGetter(main);

	private String user = cfg.getMySQL().getString("MySQL.User");
	private String pass = cfg.getMySQL().getString("MySQL.Password");
	private String hostname = cfg.getMySQL().getString("MySQL.Hostname");
	private String port = cfg.getMySQL().getString("MySQL.Port");
	private String database = cfg.getMySQL().getString("MySQL.Database");

	public void createTable() throws SQLException {
		try {
			MySQL sql = new MySQL(hostname, port, database, user, pass);
			c = sql.openConnection();

			Statement statement = c.createStatement();
			String upd1 = "CREATE TABLE IF NOT EXISTS `" + database + "` ( `UID` "
					+ "INT NULL DEFAULT NULL AUTO_INCREMENT COMMENT 'UID' , `IP` TEXT NOT NULL COMMENT 'PlayerIP' ,"
					+ " UNIQUE `UID` (`UID`)) ENGINE = InnoDB;";
			statement.executeUpdate(upd1);
			c.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteTable(String database) throws SQLException {
		try {
			MySQL sql = new MySQL(hostname, port, database, user, pass);
			c = sql.openConnection();
			Statement statement = c.createStatement();
			String upd1 = "DROP TABLE `" + database + "`;";
			statement.executeUpdate(upd1);
			c.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean valueExists(String table, String data) {
		try {
			MySQL sql = new MySQL(hostname, port, database, user, pass);
			c = sql.openConnection();
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
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public void addValue(String table, String IP) {
		PreparedStatement ps = null;
		try {
			MySQL sql = new MySQL(hostname, port, database, user, pass);
			c = sql.openConnection();
			ps = c.prepareStatement("INSERT INTO " + table + " (`UID`, `IP`) VALUES (?,?);");
			ps.setString(1, "NULL");
			ps.setString(2, "'" + IP + "'");
			ps.executeUpdate();
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void removeValue(String table, String IP) {
		String query = "DELETE FROM " + table + " WHERE IP=?;";
		try {
			MySQL sql = new MySQL(hostname, port, database, user, pass);
			c = sql.openConnection();
			PreparedStatement statement = c.prepareStatement(query);
			statement.setString(1, IP);
			statement.executeUpdate();
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
