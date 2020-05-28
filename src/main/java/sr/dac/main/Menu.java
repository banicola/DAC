package sr.dac.main;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import sr.dac.utils.PlayerMenuUtil;

public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;

    protected PlayerMenuUtil playerMenuUtil;

    public Menu(PlayerMenuUtil playerMenuUtil){
        this.playerMenuUtil = playerMenuUtil;
    }

    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void interactMenu(InventoryClickEvent e);

    public abstract void closeMenu(InventoryCloseEvent e);

    public abstract  void setMenuItems();

    public void open(){
        inventory = Bukkit.createInventory(this, getSlots(), getMenuName());
        this.setMenuItems();
        playerMenuUtil.getP().openInventory(inventory);
    }


    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
