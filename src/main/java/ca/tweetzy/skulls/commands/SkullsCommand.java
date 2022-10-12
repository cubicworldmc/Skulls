/*
 * Skulls
 * Copyright 2022 Kiran Hart
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ca.tweetzy.skulls.commands;

import ca.tweetzy.skulls.feather.command.AllowedExecutor;
import ca.tweetzy.skulls.feather.command.Command;
import ca.tweetzy.skulls.feather.command.ReturnType;
import ca.tweetzy.skulls.Skulls;
import ca.tweetzy.skulls.guis.MainGUI;
import ca.tweetzy.skulls.settings.Settings;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Date Created: April 20 2022
 * Time Created: 9:42 p.m.
 *
 * @author Kiran Hart
 */
public final class SkullsCommand extends Command {

	public SkullsCommand() {
		super(AllowedExecutor.BOTH, "skulls");
	}

	@Override
	protected ReturnType execute(CommandSender commandSender, String... strings) {
		if (commandSender instanceof Player) {
			final Player player = (Player) commandSender;

			if (player.hasPermission("skulls.command.main") || Settings.MAIN_MENU_REQUIRES_NO_PERM.getBoolean())
				Skulls.getGuiManager().showGUI(player, new MainGUI(player));
		}

		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender commandSender, String... strings) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return null;
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return "The main command for the plugin";
	}

}
