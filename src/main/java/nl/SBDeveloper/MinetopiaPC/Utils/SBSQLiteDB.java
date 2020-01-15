package nl.SBDeveloper.MinetopiaPC.Utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.util.DriverDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SBSQLiteDB {

    private HikariDataSource source;

    public SBSQLiteDB(@Nonnull Plugin plugin, String name) {
        Bukkit.getLogger().info("[SBDBManager] Generating the " + name + ".db!");

        File dbFile = new File(plugin.getDataFolder(), name + ".db");

        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().severe("[SBDBManager] Couldn't generate the " + name + ".db!");
                e.printStackTrace();
                return;
            }
        }

        DataSource dataSource = new DriverDataSource("jdbc:sqlite:" + dbFile.getAbsolutePath(), "org.sqlite.JDBC", new Properties(), null, null);
        HikariConfig config = new HikariConfig();
        config.setPoolName("SQLiteConnectionPool");
        config.setDataSourceClassName("org.sqlite.SQLiteDataSource");
        config.setDataSource(dataSource);
        this.source = new HikariDataSource(config);
    }

    public void execute(String query) {
        try {
            Connection con = this.source.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            Bukkit.getLogger().severe("[SBDBManager] SQL exception! Please check the stacktrace below.");
            e.printStackTrace();
        }
    }

    public ResultSet executeWithResult(String query) {
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet set = null;

        try {
            con = this.source.getConnection();
            statement = con.prepareStatement(query);
            set = statement.executeQuery();
            return set;
        } catch (SQLException e) {
            Bukkit.getLogger().severe("[SBDBManager] SQL exception! Please check the stacktrace below.");
            e.printStackTrace();
        } finally {
            try {
                if (set != null) set.close();
                if (statement != null) statement.close();
                if (con != null) con.close();
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void closeSource() {
        Bukkit.getLogger().info("[SBDBManager] Closing the database connection!");
        this.source.close();
    }
}
