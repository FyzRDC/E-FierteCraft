package gay.efrei;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import gay.efrei.account.Account;

public class CommandPronouns implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player)sender;
			Account account = Account.get(p.getUniqueId());
			if(args.length < 1) {
				p.sendMessage("�cLe format de la commande est /pronouns [pronoms]");
				return false;
			}
			if(!args[0].contains("/")) {
				p.sendMessage("�cErreur dans le format, exemple : /pronouns they/them");
				return false;
			}
			if(args[0].length() > 14) {
				p.sendMessage("�ceLa taille maximale est de 14 charact�res");
				return false;
			}
			p.sendMessage("�6Pronoms modifi�s !");
			account.setPronouns(args[0]);
			account.save();
		}
		return true;
	}

}
