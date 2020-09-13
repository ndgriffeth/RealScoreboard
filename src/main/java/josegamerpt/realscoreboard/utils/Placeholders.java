package josegamerpt.realscoreboard.utils;

import josegamerpt.realscoreboard.RealScoreboard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Placeholders {
    private static int ping(Player player) {
        return RealScoreboard.nms.getPing(player);
    }

    private static String ram() {
        Runtime re = Runtime.getRuntime();
        int mbnumero = 1048576;
        return (re.totalMemory() - re.freeMemory()) / mbnumero + "MB";
    }

    private static int port() {
        return Bukkit.getPort();
    }

    private static String serverIP() {
        return Bukkit.getIp();
    }

    private static String time() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static String day() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private static String cords(Player player) {
        return "X: " + player.getLocation().getBlockX() + " Y: " + player.getLocation().getBlockY() + " Z: "
                + player.getLocation().getBlockZ();
    }

    private static int onlinePlayers() {
        return Bukkit.getOnlinePlayers().size();
    }

    private static int maxPlayers() {
        return Bukkit.getMaxPlayers();
    }

    private static String getVersion() {
        return Bukkit.getBukkitVersion();
    }

    private static String getWorldName(Player p) {
        return p.getLocation().getWorld().getName();
    }

    private static String getGroup(Player p) {
        if (RealScoreboard.vault == 0) {
            return "Missing Vault";
        }
        String w = RealScoreboard.perms.getPrimaryGroup(p);
        if (w == null) {
            return "None";
        }
        return Text.addColor(w);
    }

    private static String prefix(Player p) {
        if (RealScoreboard.vault == 0) {
            return "Missing Vault";
        }
        String grupo = RealScoreboard.chat.getPrimaryGroup(p);
        String prefix = RealScoreboard.chat.getGroupPrefix(p.getWorld(), grupo);
        if (grupo == null) {
            return "None";
        }
        if (prefix == null) {
            return "None";
        }
        return Text.addColor(prefix);
    }

    private static String sufix(Player p) {
        if (RealScoreboard.vault == 0) {
            return "Vault Not-Found";
        }
        String grupo = RealScoreboard.chat.getPrimaryGroup(p);
        String suffix = RealScoreboard.chat.getGroupSuffix(p.getWorld(), grupo);
        if (grupo == null) {
            return "None";
        }
        if (suffix == null) {
            return "None";
        }
        return Text.addColor(suffix);
    }

    private static double money(Player p) {
        if (RealScoreboard.vault == 0) {
            return 0D;
        }
        return RealScoreboard.economia.getBalance(p);
    }

    private static int stats(Player p, Statistic s) {
        return p.getStatistic(s);
    }

    private static String getKD(Player p) {
        double kills = p.getStatistic(Statistic.PLAYER_KILLS);
        double deaths = p.getStatistic(Statistic.DEATHS);
        String send = (kills / deaths + "");

        if (send.contains(".")) {
            return send.substring(0, send.indexOf("."));
        } else {
            return send;
        }
    }

    private static String placeholderAPI(Player p, String s) {
        if (RealScoreboard.placeholderapi == 0) {
            return s;
        }
        return PlaceholderAPI.setPlaceholders(p, s);
    }

    public static String setPlaceHolders(Player p, String s) {
        try {
            String placeholders = s.replaceAll("&", "§").replaceAll("%playername%", p.getName())
                    .replaceAll("%loc%", cords(p))
                    .replaceAll("%rainbow%", Text.getRainbow())
                    .replaceAll("%time%", time())
                    .replaceAll("%day%", day())
                    .replaceAll("%serverip%", serverIP())
                    .replaceAll("%version%", getVersion())
                    .replaceAll("%ping%", ping(p) + " ms")
                    .replaceAll("%ram%", ram())
                    .replaceAll("%jumps%", "" + stats(p, Statistic.JUMP))
                    .replaceAll("%mobkills%", "" + stats(p, Statistic.MOB_KILLS))
                    .replaceAll("%world%", getWorldName(p)).replaceAll("%port%", String.valueOf(port()))
                    .replaceAll("%maxplayers%", String.valueOf(maxPlayers()))
                    .replaceAll("%online%", String.valueOf(onlinePlayers()))
                    .replaceAll("%prefix%", prefix(p))
                    .replaceAll("%suffix%", sufix(p)).replaceAll("%yaw%", String.valueOf(p.getLocation().getYaw()))
                    .replaceAll("%kills%", String.valueOf(stats(p, Statistic.PLAYER_KILLS)))
                    .replaceAll("%deaths%", String.valueOf(stats(p, Statistic.DEATHS)))
                    .replaceAll("%kd%", getKD(p))
                    .replaceAll("%pitch%", String.valueOf(p.getLocation().getPitch()))
                    .replaceAll("%group%", getGroup(p))
                    .replaceAll("%money%", Text.formatMoney(money(p)))
                    .replaceAll("%displayname%", p.getDisplayName())
                    .replaceAll("%xp%", p.getTotalExperience() + "")
                    .replaceAll("%x%", p.getLocation().getBlockX() + "")
                    .replaceAll("%y%", p.getLocation().getBlockY() + "")
                    .replaceAll("%z%", p.getLocation().getBlockZ() + "")
                    .replaceAll("%playtime%", Text.formatTime(stats(p, Statistic.PLAY_ONE_MINUTE) / 20) + "");
            return placeholderAPI(p, placeholders);
        } catch (Exception ignored) {
        }
        return "RealScoreboard";
    }
}
