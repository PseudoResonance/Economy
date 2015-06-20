package io.github.wolfleader116.economy;
import java.util.logging.Logger;

import io.github.wolfleader116.economy.commands.EconC;
import io.github.wolfleader116.economy.commands.EconomyC;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyPlugin extends JavaPlugin implements Listener {
	
	private static final Logger log = Logger.getLogger("Minecraft");

	public static EconomyPlugin plugin;

	@Override
	public void onEnable() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			log.severe("Vault was not found on the server! Disabling Economy!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
		} else {
			Bukkit.getServer().getServicesManager().register(Economy.class, new VaultIntegration(), this, ServicePriority.Highest);
		}
		getCommand("economy").setExecutor(new EconomyC());
		getCommand("econ").setExecutor(new EconC());
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		plugin = this;
	}

	@Override
	public void onDisable() {
		plugin = null;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Config db = new Config("uuiddatabase", EconomyPlugin.plugin);
		if (db.getConfig().getString(e.getPlayer().getName()) != e.getPlayer().getUniqueId().toString()) {
			db.getConfig().set(e.getPlayer().getName(), e.getPlayer().getUniqueId().toString());
			db.save();
		}
	}
	
}
