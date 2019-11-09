package fr.breakerland.breakerlandxp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.java.JavaPlugin;

public class BreakerlandXp extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void playerExpChangeEvent(PlayerExpChangeEvent e) {
		if (e.getAmount() > 0) {
			e.getPlayer();
			//int base = e.getAmount();
			Double multiplier = 1D;
			for (PermissionAttachmentInfo perm : e.getPlayer().getEffectivePermissions()) {
				String permission = perm.getPermission();
				if (permission.startsWith("royalxp.")) {
					double permMulti = Integer.parseInt(permission.replace("royalxp.", ""));
					if (permMulti > multiplier)
						multiplier = 1D + permMulti * 0.01;
				}
			}

			int newAmount = (int) Math.round(e.getAmount() * multiplier);
			e.setAmount(newAmount);
		}
	}

	@EventHandler(ignoreCancelled = true)
	public void entityDeathEvent(EntityDeathEvent e) {
		if (e.getEntity() instanceof Player)
			e.setDroppedExp(0);
	}
}