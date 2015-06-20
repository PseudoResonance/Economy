package io.github.wolfleader116.economy.commands;

import io.github.wolfleader116.economy.Econ;
import io.github.wolfleader116.wolfapi.Errors;
import io.github.wolfleader116.wolfapi.WolfAPI;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EconC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			log.info("This command is only for players!");
		} else {
			Player p = (Player) sender;
			if (cmd.getName().equalsIgnoreCase("econ")) {
				if (args.length == 0) {
					WolfAPI.message("Please add an operation. Either pay, request or balance.", p, "Economy");
				} else if (args.length == 1) {
					if (args[0].equalsIgnoreCase("pay") || args[0].equalsIgnoreCase("request")) {
						WolfAPI.message("Please add a player name and amount.", p, "Economy");
					} else if (args[0].equalsIgnoreCase("balance")) {
						WolfAPI.message("Your balance is $" + Econ.getMoney(p.getName()), p, "Economy");
					}
				} else if (args.length == 2) {
					if (args[0].equalsIgnoreCase("pay") || args[0].equalsIgnoreCase("request")) {
						WolfAPI.message("Please add an amount.", p, "Economy");
					} else if (args[0].equalsIgnoreCase("balance")) {
						if (p.hasPermission("economy.balance.other")) {
							try {
								WolfAPI.message(Bukkit.getServer().getPlayer(args[1]).getName() + "'s balance is $" + Econ.getMoney(Bukkit.getServer().getPlayer(args[1]).getName()), p, "Economy");
							} catch (NullPointerException e) {
								Errors.sendError(Errors.NEVER_JOINED, p, "Economy");
							}
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Economy");
						}
					}
				} else if (args.length >= 3) {
					if (args[0].equalsIgnoreCase("pay")) {
						try {
							double pay = Double.parseDouble(args[2]);
							try {
								if (Bukkit.getServer().getPlayer(args[1]) == p) {
									WolfAPI.message("You can't pay yourself!", p, "Economy");
								} else {
									if (Econ.getMoney(p.getName()) < pay) {
										WolfAPI.message("You don't have enough money!", p, "Economy");
									} else {
										Econ.deposit(args[1], pay);
										Econ.withdraw(args[1], pay);
										if (Bukkit.getServer().getPlayer(args[1]) != null) {
											WolfAPI.message(p.getName() + " has paid you $" + String.valueOf(pay) + "!", Bukkit.getServer().getPlayer(args[1]), "Economy");
										}
										WolfAPI.message("You have paid " + args[1] + "$" + String.valueOf(pay) + "!", p, "Economy");
									}
								}
							} catch (NullPointerException e) {
								Errors.sendError(Errors.NEVER_JOINED, p, "Economy");
							}
						} catch (NumberFormatException e) {
							Errors.sendError(Errors.NOT_NUMBER, p, "Economy");
						}
					} else if (args[0].equalsIgnoreCase("request")) {
						if (Bukkit.getServer().getPlayer(args[1]) != null) {
							try {
								double request = Double.parseDouble(args[2]);
								if (Bukkit.getServer().getPlayer(args[1]) == p) {
									WolfAPI.message("You can't request money from yourself!", p, "Economy");
								} else {
									WolfAPI.message(p.getName() + " is requesting $" + String.valueOf(request) + " from you!", Bukkit.getServer().getPlayer(args[1]), "Economy");
									WolfAPI.message("You have requested $" + String.valueOf(request) + " from " + args[1] + "!", p, "Economy");
								}
							} catch (NumberFormatException e) {
								Errors.sendError(Errors.NOT_NUMBER, p, "Economy");
							}
						} else {
							Errors.sendError(Errors.NOT_ONLINE, p, "Economy");
						}
					} else if (args[0].equalsIgnoreCase("balance")) {
						if (p.hasPermission("economy.balance.other")) {
							try {
								WolfAPI.message(Bukkit.getServer().getPlayer(args[1]).getName() + "'s balance is $" + Econ.getMoney(Bukkit.getServer().getPlayer(args[1]).getName()), p, "Economy");
							} catch (NullPointerException e) {
								Errors.sendError(Errors.NEVER_JOINED, p, "Economy");
							}
						} else {
							Errors.sendError(Errors.NO_PERMISSION, p, "Economy");
						}
					}
				}
			}
		}
		return false;
	}
}
