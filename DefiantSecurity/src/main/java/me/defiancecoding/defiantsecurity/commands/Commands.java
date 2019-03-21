package me.defiancecoding.defiantsecurity.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.api.localchecks.YamlUtil;
import me.defiancecoding.defiantsecurity.api.localchecks.geoip.GeoTracking;
import me.defiancecoding.defiantsecurity.api.onlinechecks.OnlineChecks;
import me.defiancecoding.defiantsecurity.api.validation.IPValidation;
import me.defiancecoding.defiantsecurity.exceptions.InvalidIPException;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;
import me.defiancecoding.defiantsecurity.util.mysql.SQLUtil;

public class Commands implements CommandExecutor {

	private DefiantSecurity main;

	public Commands(DefiantSecurity main) {
		this.main = main;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		ConfigGetter cfg = new ConfigGetter(main);

		String help = cfg.getPermissions().getString("Permissions.Help");
		String WhitelistAdd = cfg.getPermissions().getString("Permissions.WhitelistAdd");
		String WhitelistRemove = cfg.getPermissions().getString("Permissions.WhitelistRemove");
		String BlacklistAdd = cfg.getPermissions().getString("Permissions.BlacklistAdd");
		String BlacklistRemove = cfg.getPermissions().getString("Permissions.BlacklistRemove");
		// private Permission NotifyAdmin = new
		// Permission(cfg.getPermissions().getString("Permissions.NotifyAdmin"));
		String CheckUser = cfg.getPermissions().getString("Permissions.CheckUser");
		String Reload = cfg.getPermissions().getString("Permissions.Reload");

		String Prefix = cfg.getLanguage().getString("PluginOptions.Prefix");
		// private String dateFormat =
		// cfg.getLanguage().getString("PluginOptions.DateFormat");
		String addedBlacklist = cfg.getLanguage().getString("Messages.AddedBlacklist");
		String removedBlacklist = cfg.getLanguage().getString("Messages.RemovedBlacklist");
		String addedWhitelist = cfg.getLanguage().getString("Messages.AddedWhitelist");
		String removedWhitelist = cfg.getLanguage().getString("Messages.RemovedWhitelist");
		String invalidArguments = cfg.getLanguage().getString("Messages.InvalidArguments");
		String reloadMessage = cfg.getLanguage().getString("Messages.Reloaded");
		// private String playerPerIPLimitReached =
		// cfg.getLanguage().getString("Messages.ToManyPlayersOnIP");
		String noPermission = cfg.getLanguage().getString("Messages.NoPermission");

		int prams = args.length;

		if (cmd.getName().equalsIgnoreCase("DefiantSecurity") || cmd.getName().equalsIgnoreCase("Ds")
				|| cmd.getName().equalsIgnoreCase("AntiProxy") || cmd.getName().equalsIgnoreCase("Ap")
				|| cmd.getName().equalsIgnoreCase("AntiBot") || cmd.getName().equalsIgnoreCase("Ab")) {
			if (prams == 1) {
				if (args[0].equalsIgnoreCase("Help")) {
					if (sender instanceof Player) {
						if (sender.hasPermission(help)) {
							Player player = (Player) sender;
							/*
							 * send the help message
							 */
							player.sendMessage(main.color(
									ChatColor.AQUA + "=============" + Prefix + ChatColor.AQUA + "============"));
							player.sendMessage(
									ChatColor.DARK_GREEN + "/DefiantSecurity, /Ds, /Ab, /AntiBot, /Ap, /AntiProxy >"
											+ ChatColor.AQUA + "main plugin commands.");
							player.sendMessage(ChatColor.DARK_GREEN + "/Ab check <username> >" + ChatColor.AQUA
									+ "Prints out general information on the IP.");
							player.sendMessage(ChatColor.DARK_GREEN + "/Ab whitelist <add/remove> <IP> >"
									+ ChatColor.AQUA + "Add or Remove an IP from the whitelist.");
							player.sendMessage(ChatColor.DARK_GREEN + "/Ab blacklist <add/remove> <IP> >"
									+ ChatColor.AQUA + "Add or Remove an IP from the blacklist.");
							player.sendMessage(ChatColor.DARK_GREEN + "/Ab reload >" + ChatColor.AQUA
									+ "Reloads the plugins config.");
							player.sendMessage(ChatColor.DARK_GREEN + "/Ab help >" + ChatColor.AQUA
									+ "Displays this help message.");
							player.sendMessage(main.color(ChatColor.AQUA + "=============" + ChatColor.BLACK + "["
									+ ChatColor.AQUA + "V." + main.getDescription().getVersion() + ChatColor.BLACK + "]"
									+ ChatColor.AQUA + "============"));
							return true;
						}

						else {
							// Player didn't have permission for this command,
							sender.sendMessage(main.color(Prefix + " " + noPermission));
							return false;
						}

					} else {
						// Not a player so must be console.
						ConsoleCommandSender console = (ConsoleCommandSender) sender;
						console.sendMessage(main
								.color(ChatColor.AQUA + "=============" + Prefix + ChatColor.AQUA + "============"));
						console.sendMessage(
								ChatColor.DARK_GREEN + "/DefiantSecurity, /Ds, /Ab, /AntiBot, /Ap, /AntiProxy >"
										+ ChatColor.AQUA + "main plugin commands.");
						console.sendMessage(ChatColor.DARK_GREEN + "/Ab check <username> >" + ChatColor.AQUA
								+ "Prints out general information on the IP.");
						console.sendMessage(ChatColor.DARK_GREEN + "/Ab whitelist <add/remove> <IP> >" + ChatColor.AQUA
								+ "Add or Remove an IP from the whitelist.");
						console.sendMessage(ChatColor.DARK_GREEN + "/Ab blacklist <add/remove> <IP> >" + ChatColor.AQUA
								+ "Add or Remove an IP from the blacklist.");
						console.sendMessage(
								ChatColor.DARK_GREEN + "/Ab reload >" + ChatColor.AQUA + "Reloads the plugins config.");
						console.sendMessage(
								ChatColor.DARK_GREEN + "/Ab help >" + ChatColor.AQUA + "Displays this help message.");
						console.sendMessage(main.color(ChatColor.AQUA + "==============" + ChatColor.BLACK + "["
								+ ChatColor.AQUA + "V." + main.getDescription().getVersion() + ChatColor.BLACK + "]"
								+ ChatColor.AQUA + "============="));
						return true;
					}

				} else if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission(Reload)) {
						cfg.reloadBlacklist();
						cfg.reloadConfig();
						cfg.reloadFirewall();
						cfg.reloadGeoIP();
						cfg.reloadLanguage();
						cfg.reloadOnlineChecks();
						cfg.reloadPermissions();
						cfg.reloadPunishmentCommands();
						cfg.reloadWhitelist();
						sender.sendMessage(main.color(Prefix + " " + reloadMessage));
						return true;
					}

					else {
						sender.sendMessage(main.color(Prefix + " " + noPermission));
						return false;
					}
				} else {
					sender.sendMessage(main.color(Prefix + " " + invalidArguments));
					return false;
				}

			} else if (prams == 2) {

				if (args[0].equalsIgnoreCase("check")) {
					if (sender.hasPermission(CheckUser)) {
						String IP = null;
						String information = null;
						Player player = main.getServer().getPlayerExact(args[1]);
						IPValidation validate = new IPValidation(args[1]);
						try {
							if (validate.check()) {
								information = args[1];
								IP = args[1];
							} else if (player != null) {
								information = player.getPlayerListName();
								IP = main.getServer().getPlayerExact(args[1]).getAddress().getHostName();
							} else {
								sender.sendMessage(main.color(Prefix + " " + invalidArguments));
								return false;
							}
						} catch (InvalidIPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						// LocalCacheCheck localCache = new LocalCacheCheck(main);
						OnlineChecks onlineCheck = new OnlineChecks(main);
						YamlUtil yaml = new YamlUtil(main);
						sender.sendMessage(ChatColor.AQUA + information + "'s information");

						sender.sendMessage(ChatColor.DARK_GREEN + "IP: " + IP);

						boolean isWhitelisted;

						if (cfg.getConfig().getBoolean("Modules.MySQL")) {
							SQLUtil sql = new SQLUtil(main);
							isWhitelisted = sql.valueExists("Whitelist", IP);
						} else {
							isWhitelisted = yaml.checkWhitelist(IP);
						}

						boolean isBlacklisted;
						if (cfg.getConfig().getBoolean("Modules.MySQL")) {
							SQLUtil sql = new SQLUtil(main);
							isBlacklisted = sql.valueExists("Blacklist", IP);
						} else {
							isBlacklisted = yaml.checkBlacklist(IP);
						}

						if (isWhitelisted) {
							sender.sendMessage(ChatColor.DARK_GREEN + "Whitelisted: True");
						} else {
							sender.sendMessage(ChatColor.DARK_GREEN + "Whitelisted: " + ChatColor.RED + "False");
						}
						if (isBlacklisted) {
							sender.sendMessage(ChatColor.DARK_GREEN + "Blacklisted: True");
						} else {
							sender.sendMessage(ChatColor.DARK_GREEN + "Blacklisted: " + ChatColor.RED + "False");
						}
						if (onlineCheck.checkIP(IP)) {
							sender.sendMessage(ChatColor.DARK_GREEN + "Proxied IP: " + ChatColor.GREEN + "True");
						} else {
							sender.sendMessage(ChatColor.DARK_GREEN + "Proxied IP: " + ChatColor.RED + "False");
						}
						GeoTracking geo = new GeoTracking(main);
						sender.sendMessage(ChatColor.DARK_GREEN + "IP Location: " + geo.getInfo(IP));
						sender.sendMessage(ChatColor.AQUA + "End of " + information + "'s lookup");
						return true;
					} else {
						sender.sendMessage(main.color(Prefix + " " + noPermission));
						return false;
					}
				} else {
					sender.sendMessage(main.color(Prefix + " " + invalidArguments));
					return false;
				}
			} else if (prams == 3) {

				if (args[0].equalsIgnoreCase("Whitelist")) {

					if (args[1].equalsIgnoreCase("add")) {
						if (sender.hasPermission(WhitelistAdd)) {
							String IP = null;
							IPValidation validate = new IPValidation(args[2]);
							try {
								if (validate.check()) {
									IP = args[2];
								} else {
									Player player = Bukkit.getServer().getPlayerExact(args[2]);
									if (player != null) {
										IP = player.getAddress().getHostName();
									} else {
										IP = null;
										sender.sendMessage(main.color(Prefix + " " + invalidArguments));
										return false;
									}
								}
							} catch (InvalidIPException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if (cfg.getConfig().getBoolean("Modules.MySQL")) {
								SQLUtil sql = new SQLUtil(main);
								sql.addValue("Whitelist", IP);
								sender.sendMessage(main.color(Prefix + " " + addedWhitelist));
								return true;
							} else {
								YamlUtil yaml = new YamlUtil(main);
								yaml.addWhitelist(IP);
								cfg.saveWhitelist();
								sender.sendMessage(main.color(Prefix + " " + addedWhitelist));
								return true;
							}
						}

						else {
							sender.sendMessage(main.color(Prefix + " " + noPermission));
							return false;
						}

					}

					else if (args[1].equalsIgnoreCase("remove")) {
						if (sender.hasPermission(WhitelistRemove)) {
							String IP = null;
							IPValidation validate = new IPValidation(args[2]);
							try {
								if (validate.check()) {
									IP = args[2];
								} else {
									Player player = Bukkit.getServer().getPlayerExact(args[2]);
									if (player != null) {
										IP = player.getAddress().getHostName();
									} else {
										IP = null;
										sender.sendMessage(main.color(Prefix + " " + invalidArguments));
										return false;
									}
								}
							} catch (InvalidIPException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if (cfg.getConfig().getBoolean("Modules.MySQL")) {
								SQLUtil sql = new SQLUtil(main);
								sql.removeValue("Whitelist", IP);
								sender.sendMessage(main.color(Prefix + " " + removedWhitelist));
								return true;
							} else {
								YamlUtil yaml = new YamlUtil(main);
								yaml.removeWhitelist(IP);
								sender.sendMessage(main.color(Prefix + " " + removedWhitelist));
								cfg.saveWhitelist();
								return true;
							}
						}

						else {
							sender.sendMessage(main.color(Prefix + " " + noPermission));
							return false;
						}

					}

				}

				else if (args[0].equalsIgnoreCase("Blacklist")) {

					if (args[1].equalsIgnoreCase("add")) {
						if (sender.hasPermission(BlacklistAdd)) {
							String IP = null;
							IPValidation validate = new IPValidation(args[2]);
							try {
								if (validate.check()) {
									IP = args[2];
								} else {
									Player player = Bukkit.getServer().getPlayerExact(args[2]);
									if (player != null) {
										IP = player.getAddress().getHostName();
									} else {
										IP = null;
										sender.sendMessage(main.color(Prefix + " " + invalidArguments));
										return false;
									}
								}
							} catch (InvalidIPException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if (cfg.getConfig().getBoolean("Modules.MySQL")) {
								SQLUtil sql = new SQLUtil(main);
								sql.addValue("Blacklist", IP);
								sender.sendMessage(main.color(Prefix + " " + addedBlacklist));
								return true;
							} else {
								YamlUtil yaml = new YamlUtil(main);
								yaml.addWhitelist(IP);
								sender.sendMessage(main.color(Prefix + " " + addedBlacklist));
								cfg.saveBlacklist();
								return true;
							}
						}

						else {
							sender.sendMessage(main.color(Prefix + " " + noPermission));
							return false;
						}

					}

					else if (args[1].equalsIgnoreCase("remove")) {
						if (sender.hasPermission(BlacklistRemove)) {
							String IP = null;
							IPValidation validate = new IPValidation(IP);
							try {
								if (validate.check()) {
									IP = args[2];
								} else {
									Player player = Bukkit.getServer().getPlayerExact(args[2]);
									if (player != null) {
										IP = player.getAddress().getHostName();
									} else {
										IP = null;
										sender.sendMessage(main.color(Prefix + " " + invalidArguments));
										return false;
									}
								}
							} catch (InvalidIPException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if (cfg.getConfig().getBoolean("Modules.MySQL")) {
								SQLUtil sql = new SQLUtil(main);
								sql.addValue("Blacklist", IP);
								sender.sendMessage(main.color(Prefix + " " + removedBlacklist));
								return true;
							} else {
								YamlUtil yaml = new YamlUtil(main);
								yaml.removeBlacklist(IP);
								sender.sendMessage(main.color(Prefix + " " + removedBlacklist));
								cfg.saveBlacklist();
								return true;
							}
						}

						else {
							sender.sendMessage(main.color(Prefix + " " + noPermission));
							return false;
						}

					} else {
						sender.sendMessage(main.color(Prefix + " " + invalidArguments));
						return false;
					}
				}
			}
		}
		return false;
	}
}
