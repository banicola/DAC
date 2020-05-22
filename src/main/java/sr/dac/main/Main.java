package sr.dac.main;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import sr.dac.commands.DACCommand;
import sr.dac.commands.DACTabCompletion;
import sr.dac.configs.ArenaManager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    private static Plugin plugin;
    private static Economy econ = null;
    private Connection connection;

    public static File lang;
    public static YamlConfiguration f;
    private static WorldEditPlugin we;

    @Override
    public void onEnable() {
        plugin = this;
        Config.init();
        f = YamlConfiguration.loadConfiguration(lang);
        try {
            openConnection(Main.getPlugin().getConfig().getString("database.type"));
            if (connection != null && !Main.getPlugin().getConfig().getString("database.type").equalsIgnoreCase("SQLITE"))
                getLogger().info(ChatColor.translateAlternateColorCodes('&', f.getString("debug.dbConnectionSuccess")));
        } catch (SQLException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(plugin);
            return;
        }
        ArenaManager.load();
        we = setupWorldEdit();
        econ = setupEconomy();
        try {
            if (we.isEnabled())
                getLogger().info(ChatColor.translateAlternateColorCodes('&', f.getString("debug.worldeditEnable")));
        } catch (NoClassDefFoundError e) {
        }
        try {
            if (econ != null)
                getLogger().info(ChatColor.translateAlternateColorCodes('&', f.getString("debug.vaultEnable")));
        } catch (NoClassDefFoundError e) {
        }

        getCommand("dac").setExecutor(new DACCommand());
        getCommand("dac").setTabCompleter(new DACTabCompletion());

        getLogger().info(ChatColor.translateAlternateColorCodes('&', f.getString("debug.onEnable")));
    }

    @Override
    public void onDisable() {
        try {
            connection.close();
        } catch (NullPointerException | SQLException e) {
        }
        getLogger().info(ChatColor.translateAlternateColorCodes('&', f.getString("debug.onDisable")));
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    private void openConnection(String type) throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        if (type.equalsIgnoreCase("mysql")) {
            synchronized (this) {
                if (connection != null && !connection.isClosed()) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + Main.getPlugin().getConfig().getString("database.host") + ":" + Main.getPlugin().getConfig().getString("database.port")
                        + "/" + Main.getPlugin().getConfig().getString("database.database"), Main.getPlugin().getConfig().getString("database.user"), Main.getPlugin().getConfig().getString("database.password"));
            }
        } else if (type.equalsIgnoreCase("sqlite")) {
            File file = new File(Main.getPlugin().getDataFolder(), "database.db");

            String URL = "jdbc:sqlite:" + file;
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
        }
    }

    private Economy setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        return rsp.getProvider();
    }

    private static WorldEditPlugin setupWorldEdit() {
        return (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static WorldEditPlugin getWorldEdit() {
        return we;
    }
}
