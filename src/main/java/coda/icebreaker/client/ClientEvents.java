package coda.icebreaker.client;

import coda.icebreaker.Icebreaker;
import coda.icebreaker.client.model.IcebreakerModel;
import coda.icebreaker.client.render.IcebreakerRenderer;
import coda.icebreaker.client.screen.IcebreakerScreen;
import coda.icebreaker.registry.IBEntities;
import coda.icebreaker.registry.IBMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Icebreaker.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEvents {
    public static final ModelLayerLocation ICEBREAKER = new ModelLayerLocation(new ResourceLocation(Icebreaker.MOD_ID, "icebreaker"), "main");

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent e) {
        EntityRenderers.register(IBEntities.ICEBREAKER_BOAT.get(), IcebreakerRenderer::new);

        MenuScreens.register(IBMenus.ICEBREAKER.get(), IcebreakerScreen::new);
    }

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions e) {
        e.registerLayerDefinition(ICEBREAKER, IcebreakerModel::createBodyLayer);
    }
}
