package coda.icebreaker.registry;

import coda.icebreaker.Icebreaker;
import coda.icebreaker.common.menu.IcebreakerBoatMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IBMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Icebreaker.MOD_ID);

    public static final RegistryObject<MenuType<IcebreakerBoatMenu>> ICEBREAKER = MENUS.register("icebreaker", () -> new MenuType<>(IcebreakerBoatMenu::new));
}
