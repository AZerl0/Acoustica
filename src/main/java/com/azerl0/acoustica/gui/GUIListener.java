package com.azerl0.acoustica.gui;


import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class GUIListener implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (event.isCancelled()) return;
        if (event.getWhoClicked() instanceof Player clickPlayer) {
            if (clickPlayer.getGameMode() != GameMode.SPECTATOR && event.isCancelled()) return;
            if (event.getClickedInventory() != event.getView().getTopInventory()) return;
            if (event.getClickedInventory() == event.getView().getBottomInventory() && event.getClick() == ClickType.SHIFT_LEFT) {
                event.setCancelled(true);
                return;
            }

            InventoryHolder holder = event.getInventory().getHolder();
            if (holder instanceof AbstractGUI gui) {
                event.setCancelled(true);
                gui.click(event);
            }
        }
    }
}