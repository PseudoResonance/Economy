package io.github.wolfleader116.economy.commands;

import io.github.wolfleader116.economy.Config;
import io.github.wolfleader116.economy.Econ;
import io.github.wolfleader116.economy.EconomyPlugin;
import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconomyC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("economy")) {
			if (!(sender instanceof Player)) {
				if (args.length == 0) {
					log.info("Use /economy help for a list of commands.");
					log.info("Economy plugin created by WolfLeader116");
					log.info("===---Economy Info---===");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("help")) {
						log.info("/econ <pay/request/balance> <player> <amount> Pays a player, requests money or checks the player's balance.");
						log.info("/economy total Shows the total server balance.");
						log.info("/economy set <balance> <player> Set's a player's balance to a certain amount.");
						log.info("/economy remove <amount> <player> Removes a certain amount from a player's balance.");
						log.info("/economy add <amount> <player> Adds a certain amount to a player's balance.");
						log.info("/economy help Shows this message.");
						log.info("/economy Shows the info page.");
						log.info("===---Economy Help---===");
					} else if (args[0].equalsIgnoreCase("add")) {
						try {
							try {
								double amount = Double.parseDouble(args[1]);
								if (amount > 0) {
									Econ.deposit(args[2], amount);
									log.info("Added $" + String.valueOf(amount) + " to " + args[2] + "'s balance!");
								} else {
									log.info("Your number must be greater than 0!");
								}
							} catch (NumberFormatException e) {
								log.info("You did not enter a number!");
							}
						} catch (Exception e) {
							log.info("That player has never joined the server!");
						}
					} else if (args[0].equalsIgnoreCase("remove")) {
						try {
							try {
								double amount = Double.parseDouble(args[1]);
								if (amount > 0) {
									Econ.withdraw(args[2], amount);
									log.info("Removed $" + String.valueOf(amount) + " from " + args[2] + "'s balance!");
								} else {
									log.info("Your number must be greater than 0!");
								}
							} catch (NumberFormatException e) {
								log.info("You did not enter a number!");
							}
						} catch (Exception e) {
							log.info("That player has never joined the server!");
						}
					} else if (args[0].equalsIgnoreCase("set")) {
						try {
							try {
								double amount = Double.parseDouble(args[1]);
								if (amount >= 0) {
									Econ.set(args[2], amount);
									log.info("Set " + args[2] + "'s balance to " + amount + "!");
								} else {
									log.info("Your number must be greater than or equal to 0!");
								}
							} catch (NumberFormatException e) {
								log.info("You did not enter a number!");
							}
						} catch (Exception e) {
							log.info("That player has never joined the server!");
						}
					} else if (args[0].equalsIgnoreCase("total")) {
						Config c = new Config("data", EconomyPlugin.plugin);
						int total = 0;
						for (String key : c.getConfig().getKeys(false)) {
							total = total + c.getConfig().getInt(key);
						}
						log.info("Total server money: $" + total);
					}
				}
			} else {
				Player p = (Player) sender;
				if (args.length == 0) {
					sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Economy Info" + ChatColor.DARK_AQUA + "---===");
					sender.sendMessage(ChatColor.AQUA + "Economy plugin created by WolfLeader116");
					sender.sendMessage(ChatColor.AQUA + "Use " + ChatColor.RED + "/economy help " + ChatColor.AQUA + "for a list of commands.");
				} else if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.DARK_AQUA + "===---" + ChatColor.GOLD + "Economy Help" + ChatColor.DARK_AQUA + "---===");
						sender.sendMessage(ChatColor.RED + "/economy " + ChatColor.AQUA + "Shows the info page.");
						sender.sendMessage(ChatColor.RED + "/economy help " + ChatColor.AQUA + "Shows this message.");
						if (sender.hasPermission("economoy.set")) {
							sender.sendMessage(ChatColor.RED + "/economy set <amount> <player> " + ChatColor.AQUA + "Adds a certain amount to a player's balance.");
						}
						if (sender.hasPermission("economoy.remove")) {
							sender.sendMessage(ChatColor.RED + "/economy remove <amount> <player> " + ChatColor.AQUA + "Removes a certain amount from a player's balance.");
						}
						if (sender.hasPermission("economoy.set")) {
							sender.sendMessage(ChatColor.RED + "/economy set <balance> <player> " + ChatColor.AQUA + "Set's a player's balance to a certain amount.");
						}
						sender.sendMessage(ChatColor.RED + "/economy total " + ChatColor.AQUA + "Shows the total server balance.");
						if (sender.hasPermission("economy.balance.other")) {
							sender.sendMessage(ChatColor.RED + "/econ <pay/request/balance> <player> <amount> " + ChatColor.AQUA + "Pays a player, requests money from them or checks their balance.");
						} else {
							sender.sendMessage(ChatColor.RED + "/econ <pay/request> <player> <amount> " + ChatColor.AQUA + "Pays a player or requests money from them.");
						}
					} else if (args[0].equalsIgnoreCase("add")) {
						if (sender.hasPermission("economy.add")) {
							try {
								try {
									double amount = Double.parseDouble(args[1]);
									if (amount > 0) {
										Econ.deposit(args[2], amount);
										WolfAPI.message("Added $" + String.valueOf(amount) + " to " + args[2] + "'s balance!", p, "Economy");
									} else {
										WolfAPI.message("Your number must be greater than 0!", p, "Economy");
									}
								} catch (NumberFormatException e) {
									Errors.sendError(Errors.NOT_NUMBER, p, "Economy");
								}
							} catch (Exception e) {
								Errors.sendError(Errors.NEVER_JOINED, p, "Economy");
							}
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Economy");
						}
					} else if (args[0].equalsIgnoreCase("remove")) {
						if (sender.hasPermission("economy.remove")) {
							try {
								try {
									double amount = Double.parseDouble(args[1]);
									if (amount > 0) {
										Econ.withdraw(args[2], amount);
										WolfAPI.message("Removed $" + String.valueOf(amount) + " from " + args[2] + "'s balance!", p, "Economy");
									} else {
										WolfAPI.message("Your number must be greater than 0!", p, "Economy");
									}
								} catch (NumberFormatException e) {
									Errors.sendError(Errors.NOT_NUMBER, p, "Economy");
								}
							} catch (Exception e) {
								Errors.sendError(Errors.NEVER_JOINED, p, "Economy");
							}
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Economy");
						}
					} else if (args[0].equalsIgnoreCase("set")) {
						if (sender.hasPermission("economy.set")) {
							try {
								try {
									double amount = Double.parseDouble(args[1]);
									if (amount >= 0) {
										Econ.set(args[2], amount);
										WolfAPI.message("Set " + args[2] + "'s balance to " + amount + "!", p, "Economy");
									} else {
										WolfAPI.message("Your number must be greater than or equal to 0!", p, "Economy");
									}
								} catch (NumberFormatException e) {
									Errors.sendError(Errors.NOT_NUMBER, p, "Economy");
								}
							} catch (Exception e) {
								Errors.sendError(Errors.NEVER_JOINED, p, "Economy");
							}
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Economy");
						}
					} else if (args[0].equalsIgnoreCase("total")) {
						Config c = new Config("data", EconomyPlugin.plugin);
						int total = 0;
						for (String key : c.getConfig().getKeys(false)) {
							total = total + c.getConfig().getInt(key);
						}
						WolfAPI.message("Total server money: $" + total, p, "Economy");
					}
				}
			}
		}
		return false;
	}
}
