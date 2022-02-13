package com.reasure.tutorial.entity.model;
// Made with Blockbench 4.1.3
// Exported for Minecraft version 1.15 - 1.16 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.reasure.tutorial.entity.custom.PaperPlaneEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

@SuppressWarnings("FieldCanBeLocal")
public class PaperPlaneModel<T extends PaperPlaneEntity> extends EntityModel<T> {
    private final ModelRenderer right;
    private final ModelRenderer left;
    private final ModelRenderer body;
    private final ModelRenderer cube_r1;
    private final ModelRenderer cube_r2;

    public PaperPlaneModel() {
        texWidth = 64;
        texHeight = 64;

        right = new ModelRenderer(this);
        right.setPos(0.0F, 24.0F, 0.0F);
        right.texOffs(0, 11).addBox(-1.5F, -3.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        right.texOffs(13, 15).addBox(-2.5F, -3.0F, -4.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
        right.texOffs(0, 24).addBox(-3.5F, -3.0F, -3.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        right.texOffs(18, 25).addBox(-4.5F, -3.0F, -2.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        right.texOffs(27, 21).addBox(-5.5F, -3.0F, -1.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        right.texOffs(0, 33).addBox(-6.5F, -3.0F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
        right.texOffs(0, 5).addBox(-7.5F, -3.0F, 1.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        left = new ModelRenderer(this);
        left.setPos(1.0F, 24.0F, 0.0F);
        setRotationAngle(left, -3.1416F, 0.0F, 3.1416F);
        left.texOffs(0, 0).addBox(-0.5F, -3.0F, -5.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);
        left.texOffs(12, 0).addBox(-1.5F, -3.0F, -5.0F, 1.0F, 1.0F, 9.0F, 0.0F, false);
        left.texOffs(23, 0).addBox(-2.5F, -3.0F, -5.0F, 1.0F, 1.0F, 8.0F, 0.0F, false);
        left.texOffs(24, 13).addBox(-3.5F, -3.0F, -5.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);
        left.texOffs(10, 25).addBox(-4.5F, -3.0F, -5.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        left.texOffs(29, 28).addBox(-5.5F, -3.0F, -5.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
        left.texOffs(0, 0).addBox(-6.5F, -3.0F, -5.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);


        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-0.7141F, -1.5861F, 1.0F);
        body.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, -0.3491F);
        cube_r1.texOffs(12, 1).addBox(0.2141F, -0.4139F, -6.0F, 0.0F, 2.0F, 10.0F, 0.0F, false);

        cube_r2 = new ModelRenderer(this);
        cube_r2.setPos(0.5F, -1.5F, 0.0F);
        body.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, 0.3491F);
        cube_r2.texOffs(0, 12).addBox(0.0F, -0.5F, -5.0F, 0.0F, 2.0F, 10.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        right.render(matrixStack, buffer, packedLight, packedOverlay);
        left.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}