package josegamerpt.realscoreboard.config;

import java.util.ArrayList;
import java.util.List;

import josegamerpt.realscoreboard.Enum;
import josegamerpt.realscoreboard.RealScoreboard;
import josegamerpt.realscoreboard.utils.Text;
import org.bukkit.entity.Player;

public class Data {

    public static int getInt(Enum.DataInt di) {
        switch (di) {
            case ANIMATIONS_TITLE_DELAY:
                return Config.file().getInt("Config.Animations.Title-Delay");
            case ANIMATIONS_RAINBOW_DELAY:
                return Config.file().getInt("Config.Animations.Rainbow-Delay");
            case ANIMATIONS_REFRESH_DELAY:
                return Config.file().getInt("Config.Scoreboard-Refresh");
            default:
                throw new IllegalArgumentException("DataInt isnt configured: " + di.name());
        }
    }

    public static String getString(Enum.DataString di, boolean prefix) {
        return prefix ? RealScoreboard.getPrefix() + getString(di) : getString(di);
    }

    private static String getString(Enum.DataString di) {
        switch (di) {
            case SCOREBOARD_TOGGLE_ON:
                return Text.addColor(Config.file().getString("Config.Messages.Scoreboard-Toggle.ON"));
            case SCOREBOARD_TOGGLE_OFF:
                return Text.addColor(Config.file().getString("Config.Messages.Scoreboard-Toggle.OFF"));
            default:
                throw new IllegalArgumentException("DataString isnt configured: " + di.name());
        }
    }

    public static List<String> getList(Enum.DataList ds) {
        if (ds == Enum.DataList.CONFIG_DISABLED_WORLDS) {
            return Config.file().getStringList("Config.Disabled-Worlds");
        }
        throw new IllegalArgumentException("DataList isnt configured: " + ds.name());
    }

    public static ArrayList<String> getRegisteredWorlds () {
        return new ArrayList<>(Config.file().getConfigurationSection("Config.Scoreboard").getKeys(false));
    }

    public static String getCorrectPlace (Player p) {
        return checkSystem(p) ? p.getLocation().getWorld().getName() : Data.getRegisteredWorlds().get(0);
    }


    public static boolean checkSystem(Player p) {
        String world = p.getLocation().getWorld().getName();
        ArrayList<String> worlds = Data.getRegisteredWorlds();
        for (String s : worlds) {
            if (s.equalsIgnoreCase(world)) {
                return true;
            }
        }
        return false;
    }
}
