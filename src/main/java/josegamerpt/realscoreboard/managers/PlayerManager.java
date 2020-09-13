package josegamerpt.realscoreboard.managers;

import josegamerpt.realscoreboard.RealScoreboard;
import josegamerpt.realscoreboard.config.Config;
import josegamerpt.realscoreboard.player.SBPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class PlayerManager implements Listener {

    public static ArrayList<SBPlayer> players = new ArrayList<SBPlayer>();

    public static SBPlayer getPlayer(Player p) {
        for (SBPlayer sp : players) {
            if (sp.p == p) {
                return sp;
            }
        }
        return null;
    }

    public static void loadPlayer(Player p) {
        SBPlayer sb = new SBPlayer(p);
        players.add(sb);
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        loadPlayer(e.getPlayer());
    }


    private static void unloadPlayer(SBPlayer sb) {
        players.remove(sb);
    }

    @EventHandler
    public void leave(PlayerQuitEvent e) {
        SBPlayer sb = PlayerManager.getPlayer(e.getPlayer());
        if (sb != null) {
            PlayerManager.unloadPlayer(sb);
        }
    }

    @EventHandler
    public void changeWorld(PlayerTeleportEvent e) {
        new BukkitRunnable()
        {
            public void run()
            {
                if (Config.file().getList("Config.Disabled-Worlds").contains(e.getPlayer().getWorld().getName()))
                {
                    PlayerManager.getPlayer(e.getPlayer()).stop();
                } else {
                    if (Config.file().getBoolean("PlayerData." + e.getPlayer().getName() + ".Scoreboard.Status.ON")) {
                        if (PlayerManager.getPlayer(e.getPlayer()) != null) {
                            PlayerManager.getPlayer(e.getPlayer()).start();
                        }
                    }
                }
            }
        }.runTaskLater(RealScoreboard.pl, 5);
    }
}
