package com.azerl0.acoustica.gui;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class GUIListener implements Listener {


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Inventory top = event.getView().getTopInventory();
        Inventory clicked = event.getClickedInventory();

        InventoryHolder holder = top.getHolder();

        if (!(holder instanceof AbstractGUI gui)) return;

        if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            event.setCancelled(true);
            return;
        }

        if (clicked == event.getView().getBottomInventory()) {
            return;
        }

        if (clicked == top) {
            event.setCancelled(true);

            if (event.getWhoClicked() instanceof Player) {
                gui.click(event);
            }
        }
    }
}