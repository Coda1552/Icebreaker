package coda.icebreaker.client.screen;

import coda.icebreaker.Icebreaker;
import coda.icebreaker.common.menu.IcebreakerBoatMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class IcebreakerScreen extends AbstractContainerScreen<IcebreakerBoatMenu> {
    private static final ResourceLocation INV = new ResourceLocation(Icebreaker.MOD_ID, "textures/gui/icebreaker.png");

    public IcebreakerScreen(IcebreakerBoatMenu boatMenu, Inventory inv, Component component) {
        super(boatMenu, inv, component);
    }

    @Override
    protected void renderBg(PoseStack stack, float partialTick, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, INV);

        int i = this.leftPos;
        int j = this.topPos;

        this.blit(stack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (this.menu.isLit()) {
            int k = this.menu.getLitProgress();
            this.blit(stack, i + 56, j + 36 + 12 - k, 176, 12 - k, 14, k + 1);
        }
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.playerInventoryTitle, (float) this.inventoryLabelX, (float) this.inventoryLabelY, 4210752);
        this.font.draw(matrixStack, this.title, (float) this.titleLabelY, (float) this.titleLabelX - 2, 4210752);
    }
}
