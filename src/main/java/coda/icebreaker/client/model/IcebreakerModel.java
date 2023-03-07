package coda.icebreaker.client.model;

import coda.icebreaker.common.entity.IcebreakerBoat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class IcebreakerModel<T extends IcebreakerBoat> extends EntityModel<T> {
	private final ModelPart bone;
	private final ModelPart water_patch;
	private final ModelPart bb_main;

	public IcebreakerModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.water_patch = root.getChild("water_patch");
		this.bb_main = root.getChild("bb_main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(67, 80).addBox(9.0F, -10.5F, -8.5F, 4.0F, 6.0F, 17.0F, new CubeDeformation(0.01F))
				.texOffs(67, 80).mirror().addBox(-9.0F, -10.5F, -8.5F, 4.0F, 6.0F, 17.0F, new CubeDeformation(0.01F)).mirror(false)
				.texOffs(93, 80).addBox(-9.0F, -10.5F, -11.5F, 22.0F, 6.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.0F, 18.0F, -12.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition water_patch = partdefinition.addOrReplaceChild("water_patch", CubeListBuilder.create().texOffs(155, 7).addBox(-7.0F, -12.0F, -13.0F, 14.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 8.0F));

		PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -6.0F, -5.0F, 14.0F, 6.0F, 28.0F, new CubeDeformation(0.0F))
				.texOffs(0, 80).addBox(-9.0F, -12.0F, -20.0F, 18.0F, 12.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 35).addBox(-7.0F, -30.0F, -8.0F, 14.0F, 24.0F, 16.0F, new CubeDeformation(-0.02F))
				.texOffs(58, 8).addBox(7.0F, -12.0F, -5.0F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F))
				.texOffs(61, 42).addBox(-11.0F, -12.0F, 22.0F, 22.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.0F, -36.0F, 3.0F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(52, 80).addBox(-4.0F, -39.0F, 2.0F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
				.texOffs(58, 8).mirror().addBox(-11.0F, -12.0F, -5.0F, 4.0F, 6.0F, 27.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 153).addBox(-9.0F, 0.0F, -20.0F, 18.0F, 4.0F, 15.0F, new CubeDeformation(0.0F))
				.texOffs(0, 113).addBox(-7.0F, 0.0F, -5.0F, 14.0F, 4.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(40, 55).addBox(-8.0F, -1.25F, -10.5F, 16.0F, 3.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -30.25F, 0.0F, -0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	public ModelPart waterPatch() {
		return this.water_patch;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		bb_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		water_patch.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}