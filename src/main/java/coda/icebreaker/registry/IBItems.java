package coda.icebreaker.registry;

import coda.icebreaker.Icebreaker;
import coda.icebreaker.common.item.IcebreakerBoatItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IBItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Icebreaker.MOD_ID);

    public static final RegistryObject<Item> ICEBREAKER_BOAT = ITEMS.register("icebreaker", () -> new IcebreakerBoatItem(new Item.Properties().tab(CreativeModeTab.TAB_TRANSPORTATION).stacksTo(1).rarity(Rarity.UNCOMMON)));
}
