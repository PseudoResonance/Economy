package io.github.wolfleader116.economy;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.economy.EconomyResponse.ResponseType;

public class VaultIntegration implements Economy {

	public EconomyResponse bankBalance(String arg0) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse bankDeposit(String arg0, double arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse bankHas(String arg0, double arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse bankWithdraw(String arg0, double arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse createBank(String arg0, String arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public boolean createPlayerAccount(String playername) {
		try {
			Econ.createAccount(playername);
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean createPlayerAccount(OfflinePlayer player) {
		return createPlayerAccount(player.getName());
	}

	public boolean createPlayerAccount(String playername, String worldname) {
		return createPlayerAccount(playername);
	}

	public boolean createPlayerAccount(OfflinePlayer player, String worldname) {
		return createPlayerAccount(player.getName());
	}

	public String currencyNamePlural() {
		return "";
	}

	public String currencyNameSingular() {
		return "";
	}

	public EconomyResponse deleteBank(String arg0) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse depositPlayer(String playername, double amount) {
		if (amount < 0) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot desposit negative funds");
		}
		double balance;
		EconomyResponse.ResponseType type;
		String errorMeconage = null;
		try {
			Econ.deposit(playername, amount);
			balance = Econ.getMoney(playername);
			type = EconomyResponse.ResponseType.SUCCESS;
		} catch (NullPointerException e) {
			amount = 0;
			balance = 0;
			type = EconomyResponse.ResponseType.FAILURE;
			errorMeconage = "User does not exist";
		}
		return new EconomyResponse(amount, balance, type, errorMeconage);
	}

	public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
		return withdrawPlayer(player.getName(), amount);
	}

	public EconomyResponse depositPlayer(String playername, String world, double amount) {
		return withdrawPlayer(playername, amount);
	}

	public EconomyResponse depositPlayer(OfflinePlayer player, String world, double amount) {
		return withdrawPlayer(player.getName(), amount);
	}

	public String format(double amount) {
		return "$" + String.valueOf(amount);
	}

	public int fractionalDigits() {
		return 2;
	}

	public double getBalance(String playername) {
		double balance;
		try {
			balance = Econ.getMoney(playername);
		} catch (NullPointerException e) {
			createPlayerAccount(playername);
			balance = 0;
		}
		return balance;
	}

	public double getBalance(OfflinePlayer player) {
		return getBalance(player.getName());
	}

	public double getBalance(String playername, String world) {
		return getBalance(playername);
	}

	public double getBalance(OfflinePlayer player, String world) {
		return getBalance(player.getName());
	}

	public List<String> getBanks() {
		return new ArrayList<String>();
	}

	public String getName() {
		return "Economy";
	}

	public boolean has(String playername, double amount) {
		try {
			return Econ.hasEnough(playername, amount);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public boolean has(OfflinePlayer player, double amount) {
		return has(player.getName(), amount);
	}

	public boolean has(String playername, String world, double amount) {
		return has(playername, amount);
	}

	public boolean has(OfflinePlayer player, String world, double amount) {
		return has(player.getName(), amount);
	}

	public boolean hasAccount(String playername) {
		return Econ.hasAccount(playername);
	}

	public boolean hasAccount(OfflinePlayer player) {
		return hasAccount(player.getName());
	}

	public boolean hasAccount(String playername, String world) {
		return hasAccount(playername);
	}

	public boolean hasAccount(OfflinePlayer player, String world) {
		return hasAccount(player.getName());
	}

	public boolean hasBankSupport() {
		return false;
	}

	public EconomyResponse isBankMember(String arg0, String arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse isBankOwner(String arg0, String arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
		return new EconomyResponse(0, 0, ResponseType.NOT_IMPLEMENTED, "Economy does not support bank accounts!");
	}

	public boolean isEnabled() {
		return Bukkit.getServer().getPluginManager().getPlugin("Economy").isEnabled();
	}

	public EconomyResponse withdrawPlayer(String playername, double amount) {
		if (amount < 0) {
			return new EconomyResponse(0, 0, ResponseType.FAILURE, "Cannot withdraw negative funds");
		}
		double balance;
		EconomyResponse.ResponseType type;
		String errorMeconage = null;
		try {
			Econ.withdraw(playername, amount);
			balance = Econ.getMoney(playername);
			type = EconomyResponse.ResponseType.SUCCESS;
		} catch (NullPointerException e) {
			amount = 0;
			balance = 0;
			type = EconomyResponse.ResponseType.FAILURE;
			errorMeconage = "User does not exist";
		}
		return new EconomyResponse(amount, balance, type, errorMeconage);
	}

	public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
		return withdrawPlayer(player.getName(), amount);
	}

	public EconomyResponse withdrawPlayer(String playername, String world, double amount) {
		return withdrawPlayer(playername, amount);
	}

	public EconomyResponse withdrawPlayer(OfflinePlayer player, String world, double amount) {
		return withdrawPlayer(player.getName(), amount);
	}
	
}
