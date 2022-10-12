package ca.tweetzy.skulls.feather.gui.template;

import ca.tweetzy.skulls.feather.comp.enums.CompMaterial;
import ca.tweetzy.skulls.feather.gui.Gui;
import ca.tweetzy.skulls.feather.utils.Common;
import ca.tweetzy.skulls.feather.utils.QuickItem;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Date Created: April 09 2022
 * Time Created: 9:22 p.m.
 *
 * @author Kiran Hart
 */
public abstract class BaseGUI extends Gui {

    private final Gui parent;

    public BaseGUI(final Gui parent, @NonNull final String title, final int rows) {
        this.parent = parent;
        setTitle(Common.colorize(title));
        setRows(rows);
        setDefaultSound(null);
        setDefaultItem(QuickItem.of(CompMaterial.BLACK_STAINED_GLASS_PANE).name(" ").make());
    }

    public BaseGUI(final Gui parent, @NonNull final String title) {
        this(parent, title, 6);
    }

    public BaseGUI(@NonNull final String title) {
        this(null, title, 6);
    }

    /**
     * Draw the gui.
     */
    protected abstract void draw();


    /**
     * It adds a back button to the bottom left of the GUI
     *
     * @param override The GUI to show when the back button is clicked.
     */
    protected void applyBackExit(Gui override) {
        setButton(this.rows - 1, 0, getBackButton(), click -> click.manager.showGUI(click.player, override));
    }

    /**
     * If the GUI has a parent, then the back button will be set to the back button, otherwise it will be set to the exit button
     */
    protected void applyBackExit() {
        if (this.parent == null)
            setButton(this.rows - 1, 0, getExitButton(), click -> click.gui.exit());
        else
            setButton(this.rows - 1, 0, getBackButton(), click -> click.manager.showGUI(click.player, this.parent));
    }

    protected List<Integer> fillSlots() {
        return IntStream.rangeClosed(0, 44).boxed().collect(Collectors.toList());
    }

    protected ItemStack getBackButton() {
        return QuickItem.of(CompMaterial.DARK_OAK_DOOR).name("&aBack").lore("&7Click to go back").make();
    }

    protected ItemStack getExitButton() {
        return QuickItem.of(CompMaterial.BARRIER).name("&cExit").lore("&7Click to close menu").make();
    }

    protected ItemStack getPreviousPageButton() {
        return QuickItem.of(CompMaterial.ARROW, "&ePrevious").make();
    }

    protected ItemStack getNextPageButton() {
        return QuickItem.of(CompMaterial.ARROW, "&eNext").make();
    }
}
