package josegamerpt.realscoreboard.nms;

import net.minecraft.server.v1_14_R1.EntityPlayer;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMS1_14_R1 implements NMS {
    public int getPing(Player p) {
        CraftPlayer playerping = (CraftPlayer) p;

        EntityPlayer pegarPingInterno = playerping.getHandle();

        return pegarPingInterno.ping;
    }
}
