package ca.tweetzy.skulls.feather.gui.methods;


import ca.tweetzy.skulls.feather.gui.events.GuiClickEvent;

public interface Clickable {

    /**
     * When the user clicks on the GUI, do this.
     *
     * @param event The event that was fired.
     */
    void onClick(GuiClickEvent event);
}
