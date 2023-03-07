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

public class IcebreakerBoatMenu extends AbstractContainerMenu {
    private final ContainerData data;

    public IcebreakerBoatMenu(MenuType<?> type, int id, Inventory inventory) {
        this(type, id, inventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public IcebreakerBoatMenu(MenuType<?> type, int id, Inventory inventory, Container container, ContainerData data) {
        super(type, id);
        this.data = data;
        checkContainerSize(container, 1);
        checkContainerDataCount(data, 4);

        this.addSlot(new Slot(container, 1, 56, 53) {
            @Override
            public boolean mayPlace(ItemStack p_39526_) {
                return ForgeHooks.getBurnTime(p_39526_, RecipeType.SMELTING) > 0;
            }
        });

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public IcebreakerBoatMenu(int id, Inventory inventory) {
        this(IBMenus.ICEBREAKER.get(), id, inventory);
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

    @Override
    public ItemStack quickMoveStack(Player p_38941_, int p_38942_) {
        return null;
    }

    @Override
    public boolean stillValid(Player p_38874_) {
        return false;
    }
}
