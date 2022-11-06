package gay.efrei.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gay.efrei.account.Account;
import gay.efrei.managers.tpa.TPAQuery;
import gay.efrei.managers.tpa.TPARequest;

public class CommandTpa implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Account sender_account = Account.get(p.getUniqueId());
			if (args.length == 0) {
				p.sendMessage("�cLe format de la commande est /tpa [pseudo]");
				return false;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null || !target.isOnline()) {
				p.sendMessage("�cImpossible de trouver un compte avec ce pseudo connect�.");
				return false;
			}
			if (p == target) {
				p.sendMessage("�cVous ne pouvez pas vous t�l�porter � vous m�me !");
				return false;
			}
			if (TPAQuery.get(p).isOnRequestCooldown(target)) {
				p.sendMessage("�cUne requ�te a d�j� �t� envoy�e � cette personne il y a moins de 2 minutes.");
				return false;
			}
			if (!sender_account.canTeleport()) {
				p.sendMessage("�cVotre derni�re t�l�portation date d'il y a moins de 5 minutes.");
				return false;
			}
			TPARequest.make().sender(p).target(target).execute();

			p.sendMessage("�6Requ�te de t�l�portation envoy�e � �e" + target.getName() + " �6!");
			p.sendMessage("�6Pour annuler la requ�te, �e/tpcancel");
			target.sendMessage("�e" + p.getName() + " �6veux ce t�l�porter � toi.");
			target.sendMessage("�e> �a/tpaccept "+p.getName()+" �6pour accepter");
			target.sendMessage("�e> �c/tpdeny "+p.getName()+" �6pour refuser");
			target.sendMessage("�6La requ�te expirera dans �e120 secondes�6.");
			return true;
		}
		return false;
	}

}
