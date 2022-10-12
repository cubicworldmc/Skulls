package ca.tweetzy.skulls.feather.gui.events;

import ca.tweetzy.skulls.feather.gui.Gui;
import ca.tweetzy.skulls.feather.gui.GuiManager;
import org.bukkit.entity.Player;

public class GuiCloseEvent extends GuiEvent {
    public GuiCloseEvent(GuiManager manager, Gui gui, Player player) {
        super(manager, gui, player);
    }
}
