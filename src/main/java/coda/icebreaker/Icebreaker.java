package coda.icebreaker;

import coda.icebreaker.registry.IBEntities;
import coda.icebreaker.registry.IBItems;
import coda.icebreaker.registry.IBMenus;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Icebreaker.MOD_ID)
public class Icebreaker {
    public static final String MOD_ID = "icebreaker";

    public Icebreaker() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        IBItems.ITEMS.register(bus);
        IBMenus.MENUS.register(bus);
        IBEntities.ENTITIES.register(bus);
    }

}