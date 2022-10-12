package ca.tweetzy.skulls.feather.gui.methods;


import ca.tweetzy.skulls.feather.gui.events.GuiCloseEvent;

public interface Closable {

    /**
     * This function is called when the GUI is closed
     *
     * @param event The event that was fired.
     */
    void onClose(GuiCloseEvent event);
}
