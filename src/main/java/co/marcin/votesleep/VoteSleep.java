package co.marcin.votesleep;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VoteSleep extends JavaPlugin implements Listener {
	protected static double odds;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		odds = getConfig().getDouble("odds");
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("[VoteSleep] v" + getDescription().getVersion() + " Enabled");
	}

	@Override
	public void onDisable() {
		getLogger().info("[VoteSleep] v" + getDescription().getVersion() + " Disabled");
	}

	@EventHandler
	public void onPlayerEnterBed(final PlayerBedEnterEvent event) {
		double playersInBed = 0;

		for(Player player : getServer().getOnlinePlayers()) {
			if(player.getWorld().equals(event.getPlayer().getWorld()) && player.isSleeping() || event.getPlayer().equals(player)) {
				playersInBed++;
			}
		}

		if(playersInBed / getServer().getOnlinePlayers().size() >= odds) {
			getServer().getScheduler().runTaskLater(this, new Runnable() {
						public void run() {
							event.getPlayer().getWorld().setTime(0);
						}
					}, 80);
		}
	}
}
