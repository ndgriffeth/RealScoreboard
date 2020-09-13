package josegamerpt.realscoreboard.player;


import josegamerpt.realscoreboard.Enum;
import josegamerpt.realscoreboard.RealScoreboard;
import josegamerpt.realscoreboard.config.Config;
import josegamerpt.realscoreboard.config.Data;
import josegamerpt.realscoreboard.fastscoreboard.FastBoard;
import josegamerpt.realscoreboard.managers.TitleManager;
import josegamerpt.realscoreboard.utils.Placeholders;
import josegamerpt.realscoreboard.utils.Text;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SBPlayer {

    public Player p;
    public FastBoard scoreboard;
    public BukkitTask br;
    public Boolean toggle = false;

    public SBPlayer(Player p) {
        this.p = p;
        if (Config.file().getConfigurationSection("PlayerData." + p.getName()) == null) {
            RealScoreboard.log("Creating Player Data for " + p.getName());
            Config.file().set("PlayerData." + p.getName() + ".Scoreboard.Status.ON", true);
            Config.save();
        }
        if (Config.file().getBoolean("PlayerData." + p.getName() + ".Scoreboard.Status.ON")) {
            if (!Config.file().getList("Config.Disabled-Worlds").contains(p.getWorld().getName()))
                start();
        }

        br = new BukkitRunnable() {
            public void run() {
                if (scoreboard != null) {
                    if (!scoreboard.isDeleted()) {
                        List<String> lista = Config.file()
                                .getStringList("Config.Scoreboard." + Data.getCorrectPlace(p) + ".Lines");

                        List<String> send = new ArrayList<>();

                        for (String it : lista) {
                            String place = Placeholders.setPlaceHolders(p, it);

                            if (it.equalsIgnoreCase("%blank%")) {
                                send.add(Text.randomColor() + "§r" + Text.randomColor());
                            } else {
                                send.add(place);
                            }
                        }

                        scoreboard.updateTitle(TitleManager.getTitleAnimation(p));
                        scoreboard.updateLines(send);
                    }
                }
            }
        }.runTaskTimer(RealScoreboard.pl, 0L, Data.getInt(Enum.DataInt.ANIMATIONS_REFRESH_DELAY));
    }

    public void stop() {
        if (scoreboard == null) {
            return;
        }
        if (!scoreboard.isDeleted())
            scoreboard.delete();
    }

    public void start() {
        scoreboard = new FastBoard(p);
    }


    public void toggle() {
        if (!Config.file().getBoolean("PlayerData." + p.getName() + ".Scoreboard.Status.ON")) {
            Config.file().set("PlayerData." + p.getName() + ".Scoreboard.Status.ON", true);
            Config.save();
            if (!Config.file().getList("Config.Disabled-Worlds").contains(p.getWorld().getName())) {
                start();
            }
            p.sendMessage(Text.addColor(RealScoreboard.getPrefix() + Config.file().getString("Config.Messages.Scoreboard-Toggle.ON")));
        } else {
            Config.file().set("PlayerData." + p.getName() + ".Scoreboard.Status.ON", false);
            Config.save();
            stop();
            p.sendMessage(Text.addColor(RealScoreboard.getPrefix() + Config.file().getString("Config.Messages.Scoreboard-Toggle.OFF")));
        }
    }
}