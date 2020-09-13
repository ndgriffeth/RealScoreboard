package josegamerpt.realscoreboard.config;

import josegamerpt.realscoreboard.RealScoreboard;

public class ConfigUpdater {

    public static int getConfigVersion() {
        if (Config.file().getInt("Version") == 0) {
            return 1;
        } else {
            return Config.file().getInt("Version");
        }
    }

    public static void updateConfig(int i) {
        switch (i) {
            case 1:
                //update to 2
                Config.file().set("Version", 2);
                Config.file().set("Config.Messages.Scoreboard-Toggle.ON", "&fScoreboard turned &aON&f.");
                Config.file().set("Config.Messages.Scoreboard-Toggle.OFF", "&fScoreboard turned &cOFF&f.");
                Config.save();
                RealScoreboard.log("Config file updated to version 2.");
                break;
            case 2:
                //update to 3
                Config.file().set("Version", 3);
                Config.file().set("Config.Prefix", "&bReal&9Scoreboard &7| &r");
                Config.file().set("Config.Reloaded", "&fConfig &areloaded&f.");
                Config.save();
                RealScoreboard.log("Config file updated to version 3.");
                break;
            case 3:
                //update to 4
                RealScoreboard.log("Your config file is updated to the latest version.");
                break;
        }
    }
}
