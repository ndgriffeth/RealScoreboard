package josegamerpt.realscoreboard;

import josegamerpt.realscoreboard.config.Config;
import josegamerpt.realscoreboard.config.ConfigUpdater;
import josegamerpt.realscoreboard.managers.AnimationManager;
import josegamerpt.realscoreboard.managers.PlayerManager;
import josegamerpt.realscoreboard.nms.*;
import josegamerpt.realscoreboard.player.SBPlayer;
import josegamerpt.realscoreboard.utils.Text;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class RealScoreboard extends JavaPlugin {
    public static NMS nms;
    public static Permission perms = null;
    public static Economy economia = null;
    public static Chat chat = null;
    public static int vault = 0;
    public static int placeholderapi = 0;
    public static Logger log = Bukkit.getLogger();
    public static Plugin pl;
    PluginDescriptionFile desc = getDescription();
    PluginManager pm = Bukkit.getPluginManager();
    static String name = "[ RealScoreboard ]";
    String sv;
    String header = "------------------- RealScoreboard -------------------";

    public static String getPrefix() {
        return Text.addColor(Config.file().getString("Config.Prefix"));
    }

    public static void log(String s) {
        System.out.print(s);
    }

    public static void logPrefix(String s) {
        System.out.print(name + " " + s);
    }

    public static String getServerVersion() {
        String s = Bukkit.getServer().getClass().getPackage().getName();
        return s.substring(s.lastIndexOf(".") + 1).trim();
    }

    public void onDisable() {
        PlayerManager.players.forEach(SBPlayer::stop);
        PlayerManager.players.clear();
    }

        public void onEnable() {
        pl = this;

        System.out.print(header);

        log("Checking the server version.");
        sv = getServerVersion();
        if (setupNMS()) {
            if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                vault = 1;
                setupEconomy();
                setupPermissions();
                setupChat();
            }
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                placeholderapi = 1;
            }

            log("Setting up the configuration.");
            saveDefaultConfig();
            Config.setup(this);
            log("Your config version is: " + ConfigUpdater.getConfigVersion());
            ConfigUpdater.updateConfig(ConfigUpdater.getConfigVersion());

            log("Registering Events.");
            pm.registerEvents(new PlayerManager(), this);

            log("Registering Commands.");
            getCommand("realscoreboard").setExecutor(new RSBcmd());

            log("Starting up the Scoreboard.");
            AnimationManager.startAnimations();

            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerManager.loadPlayer(p);
            }

            logList(Arrays.asList("Loaded sucessfully.", "Server version: " + sv,
                    "If you have any questions or need support, feel free to message JoseGamer_PT", "https://www.spigotmc.org/members/josegamer_pt.40267/"));

            System.out.print(header);
        } else {
            logList(Arrays.asList("Failed to load RealScoreboard.",
                    "Your server version (" + sv + ") is not compatible with RealScoreboard.",
                    "If you think this is a bug, please contact JoseGamer_PT.", "https://www.spigotmc.org/members/josegamer_pt.40267/"));
            System.out.print("");
            System.out.print(header);

            HandlerList.unregisterAll(this);

            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private boolean setupNMS() {
        String compatible = "Your server is compatible with RealScoreboard!";
        switch (sv) {
            case "v1_13_R2":
                log(compatible);
                nms = new NMS1_13_R2();
                break;
            case "v1_14_R1":
            case "v1_14_R2":
                log(compatible);
                nms = new NMS1_14_R1();
                break;
            case "v1_15_R1":
                log(compatible);
                nms = new NMS1_15_R1();
                break;
            case "v1_16_R1":
                log(compatible);
                nms = new NMS1_16_R1();
                break;
            case "v1_16_R2":
                log(compatible);
                nms = new NMS1_16_R2();
                break;
            default:
                //not compatible
                break;
        }

        return nms != null;
    }

    protected void logList(List<String> l) {
        for (String s : l) {
            log(s);
        }
    }

    // Vault
    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economia = rsp.getProvider();
        return economia != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager()
                .getRegistration(Permission.class);
        if (permissionProvider != null) {
            perms = permissionProvider.getProvider();
        }
        return perms != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return chat != null;
    }
}