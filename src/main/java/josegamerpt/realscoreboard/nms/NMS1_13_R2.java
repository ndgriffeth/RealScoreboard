package josegamerpt.realscoreboard.nms;

import net.minecraft.server.v1_13_R2.EntityPlayer;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS1_13_R2 implements NMS {
    public int getPing(Player p) {
        CraftPlayer playerping = (CraftPlayer) p;

        EntityPlayer pegarPingInterno = playerping.getHandle();

        return pegarPingInterno.ping;
    }
}
