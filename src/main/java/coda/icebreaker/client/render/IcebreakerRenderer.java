package coda.icebreaker.client.render;

import coda.icebreaker.Icebreaker;
import coda.icebreaker.client.ClientEvents;
import coda.icebreaker.client.model.IcebreakerModel;
import coda.icebreaker.common.entity.IcebreakerBoat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class IcebreakerRenderer extends EntityRenderer<IcebreakerBoat> {
    private static final ResourceLocation TEX = new ResourceLocation(Icebreaker.MOD_ID, "textures/entity/icebreaker.png");
    private final IcebreakerModel<IcebreakerBoat> model;

    public IcebreakerRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.8F;
        this.model = new IcebreakerModel<>(context.bakeLayer(ClientEvents.ICEBREAKER));
    }

    @Override
    public ResourceLocation getTextureLocation(IcebreakerBoat p_114482_) {
        return TEX;
    }

    @Override
    public void render(IcebreakerBoat p_113929_, float p_113930_, float p_113931_, PoseStack p_113932_, MultiBufferSource p_113933_, int p_113934_) {
        p_113932_.pushPose();
        p_113932_.translate(0.0D, 1.65D, 0.0D);
        p_113932_.mulPose(Vector3f.YP.rotationDegrees(-90.0F - p_113930_));
        float f = (float)p_113929_.getHurtTime() - p_113931_;
        float f1 = p_113929_.getDamage() - p_113931_;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            p_113932_.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)p_113929_.getHurtDir()));
        }

        float f2 = p_113929_.getBubbleAngle(p_113931_);
        if (!Mth.equal(f2, 0.0F)) {
            p_113932_.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), p_113929_.getBubbleAngle(p_113931_), true));
        }

        p_113932_.scale(-1.0F, -1.0F, 1.0F);
        p_113932_.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        model.setupAnim(p_113929_, p_113931_, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = p_113933_.getBuffer(model.renderType(getTextureLocation(p_113929_)));
        model.renderToBuffer(p_113932_, vertexconsumer, p_113934_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!p_113929_.isUnderWater()) {
            VertexConsumer vertexconsumer1 = p_113933_.getBuffer(RenderType.waterMask());
            model.waterPatch().render(p_113932_, vertexconsumer1, p_113934_, OverlayTexture.NO_OVERLAY);
        }

        p_113932_.popPose();
        super.render(p_113929_, p_113930_, p_113931_, p_113932_, p_113933_, p_113934_);
    }
}
