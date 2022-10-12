package ca.tweetzy.skulls.feather.gui.methods;


import ca.tweetzy.skulls.feather.gui.events.GuiOpenEvent;

public interface Openable {

    /**
     * This function is called when the GUI is opened.
     *
     * @param event The event that was fired.
     */
    void onOpen(GuiOpenEvent event);
}
