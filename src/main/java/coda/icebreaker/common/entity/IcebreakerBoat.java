package coda.icebreaker.common.entity;

import coda.icebreaker.common.menu.IcebreakerBoatMenu;
import coda.icebreaker.registry.IBEntities;
import coda.icebreaker.registry.IBItems;
import coda.icebreaker.registry.IBMenus;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class IcebreakerBoat extends Boat implements HasCustomInventoryScreen, ContainerEntity {
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(1, ItemStack.EMPTY);
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;
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

    protected void addAdditionalSaveData(CompoundTag p_219908_) {
        super.addAdditionalSaveData(p_219908_);
        this.addChestVehicleSaveData(p_219908_);
    }

    protected void readAdditionalSaveData(CompoundTag p_219901_) {
        super.readAdditionalSaveData(p_219901_);
        this.readChestVehicleSaveData(p_219901_);
    }

    public void destroy(DamageSource p_219892_) {
        super.destroy(p_219892_);
        this.chestVehicleDestroyed(p_219892_, this.level, this);
    }

    public void remove(Entity.RemovalReason p_219894_) {
        if (!this.level.isClientSide && p_219894_.shouldDestroy()) {
            Containers.dropContents(this.level, this, this);
        }

        super.remove(p_219894_);
    }

    public InteractionResult interact(Player p_219898_, InteractionHand p_219899_) {
        return this.canAddPassenger(p_219898_) && !p_219898_.isSecondaryUseActive() ? super.interact(p_219898_, p_219899_) : this.interactWithChestVehicle(this::gameEvent, p_219898_);
    }

    public void openCustomInventoryScreen(Player p_219906_) {
        p_219906_.openMenu(this);

        if (!p_219906_.level.isClientSide) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, p_219906_);
            PiglinAi.angerNearbyPiglins(p_219906_, true);
        }
    }

    public void clearContent() {
        this.clearChestVehicleContent();
    }

    public int getContainerSize() {
        return 1;
    }

    public ItemStack getItem(int p_219880_) {
        return this.getChestVehicleItem(p_219880_);
    }

    public ItemStack removeItem(int p_219882_, int p_219883_) {
        return this.removeChestVehicleItem(p_219882_, p_219883_);
    }

    public ItemStack removeItemNoUpdate(int p_219904_) {
        return this.removeChestVehicleItemNoUpdate(p_219904_);
    }

    public void setItem(int p_219885_, ItemStack p_219886_) {
        this.setChestVehicleItem(p_219885_, p_219886_);
    }

    public SlotAccess getSlot(int p_219918_) {
        return this.getChestVehicleSlot(p_219918_);
    }

    public void setChanged() {
    }

    public boolean stillValid(Player p_219896_) {
        return this.isChestVehicleStillValid(p_219896_);
    }

    @Nullable
    public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
        if (this.lootTable != null && player.isSpectator()) {
            return null;
        }
        else {
            this.unpackLootTable(inv.player);
            return new IcebreakerBoatMenu(IBMenus.ICEBREAKER.get(), id, inv, new SimpleContainer(3), new SimpleContainerData(4));
        }
    }

    public void unpackLootTable(@Nullable Player p_219914_) {
        this.unpackChestVehicleLootTable(p_219914_);
    }

    @Nullable
    public ResourceLocation getLootTable() {
        return this.lootTable;
    }

    public void setLootTable(@Nullable ResourceLocation p_219890_) {
        this.lootTable = p_219890_;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    public void setLootTableSeed(long p_219888_) {
        this.lootTableSeed = p_219888_;
    }

    public NonNullList<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    public void clearItemStacks() {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    public boolean isFueled() {
        return !isEmpty();
    }

    public void setFueled(boolean fueled) {
        isFueled = fueled;
    }

    private LazyOptional<?> itemHandler = LazyOptional.of(() -> new InvWrapper(this));

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (this.isAlive() && capability == ForgeCapabilities.ITEM_HANDLER)
            return itemHandler.cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandler.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        itemHandler = LazyOptional.of(() -> new InvWrapper(this));
    }

    @Override
    public void setChestVehicleItem(int p_219941_, ItemStack p_219942_) {
        ContainerEntity.super.setChestVehicleItem(p_219941_, p_219942_);
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
