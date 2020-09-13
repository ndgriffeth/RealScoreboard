package josegamerpt.realscoreboard.managers;

import josegamerpt.realscoreboard.Enum;
import josegamerpt.realscoreboard.config.Data;
import josegamerpt.realscoreboard.RealScoreboard;
import josegamerpt.realscoreboard.utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AnimationManager {
    public static void startAnimations() {
        IniciarTitulo();
        IniciarRainbow();
    }

    public static void IniciarTitulo() {
        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    TitleManager.startAnimation(p);
                }
            }
        }.runTaskTimer(RealScoreboard.pl, 0L, (long) Data.getInt(Enum.DataInt.ANIMATIONS_TITLE_DELAY));
    }

    public static void IniciarRainbow() {
        new BukkitRunnable() {
            public void run() {
                Text.startAnimation();
            }
        }.runTaskTimer(RealScoreboard.pl, 0L, (long) Data.getInt(Enum.DataInt.ANIMATIONS_RAINBOW_DELAY));
    }
}
