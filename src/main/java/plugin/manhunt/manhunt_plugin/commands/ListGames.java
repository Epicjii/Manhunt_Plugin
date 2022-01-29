package plugin.manhunt.manhunt_plugin.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import plugin.manhunt.manhunt_plugin.ManhuntGame;

import java.util.HashMap;
import java.util.List;

public class ListGames implements CommandExecutor {
    HashMap<ManhuntGame, Player> currentGames = Target.currentGames;

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args) {

        if (!currentGames.isEmpty()) {
            for (ManhuntGame manhuntGame : currentGames.keySet()) {
                sender.sendMessage(Component.text(manhuntGame.target.getName() + "'s Hunt").decorate(TextDecoration.UNDERLINED)
                        .hoverEvent(HoverEvent.showText(Component.text(playersToString(manhuntGame.hunters)))));
            }
            return true;
        }
        sender.sendMessage("There are currently no active games.");
        return true;
    }

    public String playersToString(List<Player> players) {
        StringBuilder hunterNames = new StringBuilder();

        for (Player player : players) {
            if (players.size() == 1) {
                hunterNames.append(player.getName());
                return hunterNames.toString();
            }
            hunterNames.append(player.getName()).append(", ");
        }

        return hunterNames.toString();
    }
}
