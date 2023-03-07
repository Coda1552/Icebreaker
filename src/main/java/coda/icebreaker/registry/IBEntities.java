package coda.icebreaker.registry;

import coda.icebreaker.Icebreaker;
import coda.icebreaker.common.entity.IcebreakerBoat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class IBEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Icebreaker.MOD_ID);

    public static final RegistryObject<EntityType<IcebreakerBoat>> ICEBREAKER_BOAT = ENTITIES.register("icebreaker", () -> EntityType.Builder.<IcebreakerBoat>of(IcebreakerBoat::new, MobCategory.MISC).sized(1.5F, 0.65F).clientTrackingRange(10).build("icebreaker"));

}
