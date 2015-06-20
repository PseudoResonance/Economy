package io.github.wolfleader116.economy;

import io.github.wolfleader116.settings.Scoreboard;

import org.bukkit.Bukkit;

public class Econ {
	static Config c = new Config("data", EconomyPlugin.plugin);
	static Config db = new Config("uuiddatabase", EconomyPlugin.plugin);
	
	public static double getMoney(String playername) {
		if (Bukkit.getServer().getPlayer(playername) != null) {
			try {
				double balance = c.getConfig().getInt(Bukkit.getServer().getPlayer(playername).getUniqueId().toString());
				return balance;
			} catch (Exception e) {
				c.getConfig().set(Bukkit.getServer().getPlayer(playername).getUniqueId().toString(), 0);
				c.save();
				return 0;
			}
		} else {
			try {
				double balance = c.getConfig().getInt(db.getConfig().getString(playername));
				return balance;
			} catch (Exception e) {
				throw new NullPointerException();
			}
		}
	}
	
	public static void createAccount(String playername) {
		if (Bukkit.getServer().getPlayer(playername) != null) {
			c.getConfig().set(Bukkit.getServer().getPlayer(playername).getUniqueId().toString(), 0);
			c.save();
		} else {
			try {
				c.getConfig().set(db.getConfig().getString(playername), 0);
				c.save();
			} catch (Exception e) {
				throw new NullPointerException();
			}
		}
	}
	
	public static boolean set(String playername, double amount) {
		if (Bukkit.getServer().getPlayer(playername) != null) {
			if (amount < 0) {
				return false;
			} else {
				c.getConfig().set(Bukkit.getServer().getPlayer(playername).getUniqueId().toString(), amount);
				c.save();
				if (Bukkit.getServer().getPluginManager().getPlugin("Settings") != null) {
					Scoreboard.scoreboard(playername);
				}
				return true;
			}
		} else {
			try {
				if (amount < 0) {
					return false;
				} else {
					c.getConfig().set(db.getConfig().getString(playername), amount);
					c.save();
					return true;
				}
			} catch (Exception e) {
				throw new NullPointerException();
			}
		}
	}
	
	public static boolean withdraw(String playername, double amount) {
		if (Bukkit.getServer().getPlayer(playername) != null) {
			if (getMoney(playername) < amount) {
				return false;
			} else {
				c.getConfig().set(Bukkit.getServer().getPlayer(playername).getUniqueId().toString(), getMoney(playername) - amount);
				c.save();
				if (Bukkit.getServer().getPluginManager().getPlugin("Settings") != null) {
					Scoreboard.scoreboard(playername);
				}
				return true;
			}
		} else {
			try {
				if (getMoney(playername) < amount) {
					return false;
				} else {
					c.getConfig().set(db.getConfig().getString(playername), getMoney(playername) - amount);
					c.save();
					return true;
				}
			} catch (Exception e) {
				throw new NullPointerException();
			}
		}
	}
	
	public static boolean deposit(String playername, double amount) {
		if (Bukkit.getServer().getPlayer(playername) != null) {
			c.getConfig().set(Bukkit.getServer().getPlayer(playername).getUniqueId().toString(), getMoney(playername) + amount);
			c.save();
			if (Bukkit.getServer().getPluginManager().getPlugin("Settings") != null) {
				Scoreboard.scoreboard(playername);
			}
			return true;
		} else {
			try {
				c.getConfig().set(db.getConfig().getString(playername), getMoney(playername) + amount);
				c.save();
				return true;
			} catch (Exception e) {
				throw new NullPointerException();
			}
		}
	}
	
	public static boolean hasEnough(String playername, double amount) {
		if (Bukkit.getServer().getPlayer(playername) != null) {
			if (c.getConfig().getInt(Bukkit.getServer().getPlayer(playername).getUniqueId().toString()) >= amount) {
				return true;
			} else {
				return false;
			}
		} else {
			try {
				if (c.getConfig().getInt(db.getConfig().getString(playername)) >= amount) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				throw new NullPointerException();
			}
		}
	}
	
	@SuppressWarnings("unused")
	public static boolean hasAccount(String playername) {
		if (Bukkit.getServer().getPlayer(playername) != null) {
			try {
				double balance = c.getConfig().getInt(Bukkit.getServer().getPlayer(playername).getUniqueId().toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			try {
				String uuid = db.getConfig().getString(playername);
				try {
					double balance = c.getConfig().getInt(uuid);
					return true;
				} catch (Exception e) {
					return false;
				}
			} catch (Exception e) {
				throw new NullPointerException();
			}
		}
	}
}