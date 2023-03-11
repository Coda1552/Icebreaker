package coda.icebreaker.common.menu;

import coda.icebreaker.registry.IBMenus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.ForgeHooks;

// todo - save items in the menu once the menu is closed
public class IcebreakerBoatMenu extends AbstractContainerMenu {
    private final ContainerData data;
    private final Container container;

    public IcebreakerBoatMenu(int id, Inventory inventory) {
        this(id, inventory, new SimpleContainer(1), new SimpleContainerData(1));
    }

    public IcebreakerBoatMenu(int id, Inventory inventory, Container container, ContainerData data) {
        super(IBMenus.ICEBREAKER.get(), id);
        this.data = data;
        this.container = container;
        checkContainerSize(container, 1);
        checkContainerDataCount(data, 1);

        container.startOpen(inventory.player);

        this.addSlot(new Slot(container, 0, 8, 36) {
            @Override
            public boolean mayPlace(ItemStack p_39526_) {
                return ForgeHooks.getBurnTime(p_39526_, RecipeType.SMELTING) > 0;
            }
        });

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public int getLitProgress() {
        int i = this.data.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.data.get(0) * 13 / i;
    }

    public boolean isLit() {
        return this.data.get(0) > 0;
    }

    public static boolean isFuel(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
    }

    @Override
    public ItemStack quickMoveStack(Player p_38986_, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index != 0) {
                if (isFuel(itemstack1)) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 1 && index < 28) {
                    if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 28 && index < 37 && !this.moveItemStackTo(itemstack1, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.moveItemStackTo(itemstack1, 1, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            }
            else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_38986_, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }

    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }
}
