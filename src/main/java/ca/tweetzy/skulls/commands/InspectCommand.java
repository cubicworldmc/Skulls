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

import ca.tweetzy.flight.command.AllowedExecutor;
import ca.tweetzy.flight.command.Command;
import ca.tweetzy.flight.command.ReturnType;
import ca.tweetzy.flight.comp.enums.CompMaterial;
import ca.tweetzy.flight.nbtapi.NBT;
import ca.tweetzy.flight.settings.TranslationManager;
import ca.tweetzy.flight.utils.Common;
import ca.tweetzy.flight.utils.PlayerUtil;
import ca.tweetzy.skulls.Skulls;
import ca.tweetzy.skulls.api.interfaces.PlacedSkull;
import ca.tweetzy.skulls.api.interfaces.Skull;
import ca.tweetzy.skulls.settings.Settings;
import ca.tweetzy.skulls.settings.Translations;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public final class InspectCommand extends Command {

	public InspectCommand() {
		super(AllowedExecutor.PLAYER, "inspect");
	}

	@Override
	protected ReturnType execute(CommandSender sender, String... args) {
		final Player player = (Player) sender;

		final ItemStack hand = PlayerUtil.getHand(player);
		int skullId = -1;

		if (hand.getType() == CompMaterial.AIR.parseMaterial()) {
			final Block targetBlock = player.getTargetBlock(null, 10);
			if (targetBlock.getType() != CompMaterial.PLAYER_HEAD.parseMaterial()) return ReturnType.FAIL;

			final PlacedSkull placedSkull = Skulls.getSkullManager().getPlacedSkulls().get(targetBlock.getLocation());
			if (placedSkull != null) {
				skullId = placedSkull.getSkullId();
			}
		} else {
			if (NBT.get(hand, nbt -> nbt.hasTag("Skulls:ID"))) {
				final String skullIdString = NBT.get(hand, nbt -> nbt.getString("Skulls:ID"));
				skullId = Integer.parseInt(skullIdString);
			}
		}

		final Skull skull = Skulls.getSkullManager().getSkull(skullId);
		if (skull == null) {
			Common.tell(sender, TranslationManager.string(Translations.NO_SKULL_INFO));
			return ReturnType.FAIL;
		}

		Common.tellNoPrefix(sender, TranslationManager.list(Translations.INSPECT_INFO, "skull_id", String.valueOf(skullId)).toArray(new String[0]));
		return ReturnType.SUCCESS;
	}

	@Override
	protected List<String> tab(CommandSender sender, String... args) {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return Settings.GENERAL_USAGE_REQUIRES_NO_PERM.getBoolean() ? null : "skulls.command.inspect";
	}

	@Override
	public String getSyntax() {
		return null;
	}

	@Override
	public String getDescription() {
		return "Inspects the skull being held or looked at";
	}
}
