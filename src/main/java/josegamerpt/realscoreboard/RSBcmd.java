package josegamerpt.realscoreboard;

import josegamerpt.realscoreboard.config.Config;
import josegamerpt.realscoreboard.config.Data;
import josegamerpt.realscoreboard.managers.PlayerManager;
import josegamerpt.realscoreboard.player.SBPlayer;
import josegamerpt.realscoreboard.utils.Text;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class RSBcmd implements CommandExecutor {

    String nop = Text.addColor(RealScoreboard.getPrefix() + "&cYou don't have permission to use this command.");
    String notfound = Text.addColor(RealScoreboard.getPrefix() + "&cNo command has been found with that syntax.");

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            if ((cmd.getName().equalsIgnoreCase("realscoreboard"))) {
                if (args.length == 0) {
                    if (p.hasPermission("RealScoreboard.Help")) {
                        printHelp(p);
                    } else {
                        p.sendMessage(nop);
                    }
                } else if (args.length == 1) {
                    switch (args[0]) {
                        case "reload":
                            if (p.hasPermission("RealScoreboard.Reload")) {
                                processReload(p);
                                return false;
                            } else {
                                p.sendMessage(nop);
                            }
                            break;
                        case "config":
                            if (p.hasPermission("RealScoreboard.Config")) {
                                printConfigHelp(p);
                            } else {
                                p.sendMessage(nop);
                            }
                            return false;
                        case "toggle":
                            SBPlayer s = PlayerManager.getPlayer(p);
                            s.toggle();
                            break;
                        default:
                            p.sendMessage(notfound);
                            break;
                    }
                } else if (args.length == 2) {
                    if (args[0].equals("config")) {
                        if (args[1].equals("show")) {
                            if (p.hasPermission("RealScoreboard.Config")) {
                                processConfigShow1(p);
                            } else {
                                p.sendMessage(nop);
                            }
                        }
                    }
                    } else if (args.length == 3) {
                        if ((args[0].equals("config")) && (args[1].equals("show"))) {
                            if (p.hasPermission("RealScoreboard.Config")) {
                                if (args[2].equals("1")) {
                                    processConfigShow1(p);
                                }
                            } else {
                                p.sendMessage(nop);
                            }
                        }
                    } else {
                        p.sendMessage(notfound);
                    }
                } else {
                    if (p.hasPermission("RealScoreboard.Help")) {
                        printHelp(p);
                    } else {
                        p.sendMessage(nop);
                    }

            }
        } else {
            RealScoreboard.log("Only players can execute this command.");
        }
        return false;
    }

    protected void printHelp(Player p) {
        List<String> a = Arrays.asList("&7", RealScoreboard.getPrefix() + "&bHelp",
                "&f/realscoreboard toggle", "&f/realscoreboard reload", "&f/realscoreboard config",
                "&7");
        for (String s : a) {
            p.sendMessage(Text.addColor(s));
        }
    }

    protected void printConfigHelp(Player p) {
        List<String> a = Arrays.asList("&7", RealScoreboard.getPrefix() + "&bConfig",
                "&f/realscoreboard config show", "&7");
        for (String s : a) {
            p.sendMessage(Text.addColor(s));
        }
    }

    protected void processReload(Player p) {
        Config.reload();
        p.sendMessage(Text.addColor(RealScoreboard.getPrefix() + Config.file().getString("Config.Reloaded")));
    }

    protected void processConfigShow1(Player p) {
        p.sendMessage(Text.addColor("&7&m----------&r &aConfig &7&m----------"));
        p.sendMessage(Text.addColor("&9Disabled Worlds:"));
        List<String> l = Data.getList(Enum.DataList.CONFIG_DISABLED_WORLDS);
        for (String s : l) {
            p.sendMessage(Text.addColor(" &8- &b" + s));
        }
        p.sendMessage("");
        p.sendMessage(Text.addColor("&9Animations:"));
        p.sendMessage(Text.addColor(" &bRainbow Delay (In Ticks): ")
                + Text.addColor("&f" + Data.getInt(Enum.DataInt.ANIMATIONS_RAINBOW_DELAY)));
        p.sendMessage(Text.addColor(" &bTitle Delay (In Ticks): ")
                + Text.addColor("&f" + Data.getInt(Enum.DataInt.ANIMATIONS_TITLE_DELAY)));
        p.sendMessage("");
        p.sendMessage(Text.addColor("&9Scoreboard:"));
        p.sendMessage(Text.addColor(" &bRefresh Delay (In Ticks): ")
                + Text.addColor("&f" + Data.getInt(Enum.DataInt.ANIMATIONS_REFRESH_DELAY)));
        p.sendMessage("");
        p.sendMessage(
                Text.addColor("&9There are &b" + Data.getRegisteredWorlds().size() + " &9scoreboards registered."));
        p.sendMessage(Text.addColor(" &f" + String.join(", ", Data.getRegisteredWorlds())));
    }
}
