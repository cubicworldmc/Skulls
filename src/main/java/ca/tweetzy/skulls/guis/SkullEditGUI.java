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

package ca.tweetzy.skulls.guis;

import ca.tweetzy.skulls.feather.comp.enums.CompMaterial;
import ca.tweetzy.skulls.feather.gui.Gui;
import ca.tweetzy.skulls.feather.gui.template.BaseGUI;
import ca.tweetzy.skulls.feather.utils.QuickItem;
import ca.tweetzy.skulls.feather.utils.input.TitleInput;
import ca.tweetzy.skulls.api.interfaces.Skull;
import ca.tweetzy.skulls.settings.Locale;
import ca.tweetzy.skulls.settings.Translation;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Date Created: April 29 2022
 * Time Created: 10:59 a.m.
 *
 * @author Kiran Hart
 */
public final class SkullEditGUI extends BaseGUI {

	private final Gui parent;
	private final Skull skull;
	private final int page;

	public SkullEditGUI(final Gui parent, Skull skull, int page) {
		super(parent, Translation.GUI_EDIT_TITLE.getString("skull_id", skull.getId()), 6);
		this.parent = parent;
		this.skull = skull;
		this.page = page;
		draw();
	}

	@Override
	protected void draw() {
		setItem(1, 4, this.skull.getItemStack());

		setButton(3, 1, QuickItem.of(CompMaterial.NAME_TAG)
				.name(Translation.GUI_EDIT_ITEMS_NAME_NAME.getString())
				.lore(Translation.GUI_EDIT_ITEMS_NAME_LORE.getList())
				.make(), click -> new TitleInput(click.player, Translation.INPUT_SKULL_EDIT_TITLE.getString(), Translation.INPUT_SKULL_EDIT_NAME.getString()) {

			@Override
			public boolean onResult(String string) {
				if (string.isEmpty()) return false;
				SkullEditGUI.this.skull.setName(string);
				SkullEditGUI.this.skull.sync();
				click.manager.showGUI(click.player, new SkullEditGUI(SkullEditGUI.this.parent, SkullEditGUI.this.skull, SkullEditGUI.this.page));
				return true;
			}
		});

		setButton(3, 3, QuickItem.of(CompMaterial.GOLD_INGOT)
				.name(Translation.GUI_EDIT_ITEMS_PRICE_NAME.getString())
				.lore(Translation.GUI_EDIT_ITEMS_PRICE_LORE.getList())
				.make(), click -> new TitleInput(click.player, Translation.INPUT_SKULL_EDIT_TITLE.getString(), Translation.INPUT_SKULL_EDIT_PRICE.getString()) {

			@Override
			public boolean onResult(String string) {
				if (string.isEmpty()) return false;
				if (!NumberUtils.isNumber(string.trim())) {
					Locale.tell(click.player, Translation.NOT_A_NUMBER.getKey());
					return false;
				}

				SkullEditGUI.this.skull.setPrice(Double.parseDouble(string.trim()));
				SkullEditGUI.this.skull.sync();
				click.manager.showGUI(click.player, new SkullEditGUI(SkullEditGUI.this.parent, SkullEditGUI.this.skull, SkullEditGUI.this.page));
				return true;
			}
		});

		setButton(3, 5, QuickItem.of(CompMaterial.BOOKSHELF)
				.name(Translation.GUI_EDIT_ITEMS_ADD_CATEGORY_NAME.getString())
				.lore(Translation.GUI_EDIT_ITEMS_ADD_CATEGORY_LORE.getList())
				.make(), click -> click.manager.showGUI(click.player, new CategorySelectorGUI(selected -> {

			if (!selected.getSkulls().contains(skull.getId())) {
				selected.getSkulls().add(skull.getId());
				selected.sync();
			}

			click.manager.showGUI(click.player, this);
		})));

		setButton(3, 7, QuickItem.of(this.skull.isBlocked() ? CompMaterial.RED_STAINED_GLASS_PANE : CompMaterial.LIME_STAINED_GLASS_PANE)
				.name(Translation.GUI_EDIT_ITEMS_BLOCKED_NAME.getString())
				.lore(Translation.GUI_EDIT_ITEMS_BLOCKED_LORE.getList("is_true", (this.skull.isBlocked() ? Translation.MISC_IS_TRUE.getString() : Translation.MISC_IS_FALSE.getString())))
				.make(), click -> {


			this.skull.setBlocked(!SkullEditGUI.this.skull.isBlocked());
			this.skull.sync();
			click.manager.showGUI(click.player, new SkullEditGUI(this.parent, this.skull, this.page));
		});

		applyBackExit();
	}
}
