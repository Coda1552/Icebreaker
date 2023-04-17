package coda.icebreaker.common.entity;

import coda.icebreaker.common.menu.IcebreakerBoatMenu;
import coda.icebreaker.registry.IBEntities;
import coda.icebreaker.registry.IBItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

// todo - save items in the menu once the menu is closed
public class IcebreakerBoat extends ChestBoat implements HasCustomInventoryScreen, ContainerEntity {
    public boolean isFueled;

    public IcebreakerBoat(EntityType<? extends Boat> p_38290_, Level p_38291_) {
        super(p_38290_, p_38291_);
    }

    public IcebreakerBoat(Level p_38293_, double p_38294_, double p_38295_, double p_38296_) {
        this(IBEntities.ICEBREAKER_BOAT.get(), p_38293_);
        this.setPos(p_38294_, p_38295_, p_38296_);
        this.xo = p_38294_;
        this.yo = p_38295_;
        this.zo = p_38296_;
    }

    @Override
    public @NotNull Item getDropItem() {
        return IBItems.ICEBREAKER_BOAT.get();
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public void positionRider(Entity rider) {
        super.positionRider(rider);
        rider.setPos(position().x, position().y + 0.0D, position().z);
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }

    public static Vec3 getYawVec(float yaw, double xOffset, double yOffset, double zOffset) {
        return new Vec3(xOffset, yOffset, zOffset).yRot(-yaw * (Mth.PI / 180f));
    }

    public void openCustomInventoryScreen(Player p_219906_) {
        p_219906_.openMenu(this);

        if (!p_219906_.level.isClientSide) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, p_219906_);
            PiglinAi.angerNearbyPiglins(p_219906_, true);
        }
    }

    public int getContainerSize() {
        return 27; // todo
    }

    @Nullable
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        if (this.lootTable != null && player.isSpectator()) {
            return null;
        }
        else {
            this.unpackLootTable(inv.player);
            return new IcebreakerBoatMenu(id, inv, new SimpleContainer(3), new SimpleContainerData(4));
            //return ChestMenu.threeRows(id, inv);
        }
    }

    public boolean isFueled() {
        return !isEmpty();
    }

    public void setFueled(boolean fueled) {
        isFueled = fueled;
    }

    @Override
    public void tick() {
        super.tick();

        if (getControllingPassenger() != null) {
            BlockPos blockPos = blockPosition();

            for (int x = -1; x < 2; x++) {
                for (int y = 0; y < 3; y++) {
                    for (int z = -1; z < 2; z++) {
                        if (level.getBlockState(blockPos.offset(x, y, z)).is(BlockTags.ICE)) {
                            level.setBlock(blockPos.offset(x, y, z), Blocks.AIR.defaultBlockState(), 2);
                            level.setBlock(blockPos.offset(x, 0, z), Blocks.WATER.defaultBlockState(), 2);
                            level.levelEvent(2001, blockPos.offset(x, y, z), Block.getId(Blocks.ICE.defaultBlockState()));
                        }
                    }
                }
            }

            Vec3 pos = getYawVec(getYRot(), 0.0F, 2.65F, -0.275F).add(position());

            if (tickCount % 2 == 0) {
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.x, pos.y, pos.z, 0.0D, 0.05D, 0.0D);
            }
        }

        if (getControllingPassenger() != null && getControllingPassenger() instanceof Player) {
            if (isFueled()) {
                setDeltaMovement(getDeltaMovement().scale(1.05D));
            }
            else {
                setDeltaMovement(getDeltaMovement().scale(0.95D));
            }

        }
    }
}
