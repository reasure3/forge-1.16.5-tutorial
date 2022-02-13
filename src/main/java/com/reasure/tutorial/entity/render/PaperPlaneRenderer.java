package com.reasure.tutorial.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.entity.custom.PaperPlaneEntity;
import com.reasure.tutorial.entity.model.PaperPlaneModel;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class PaperPlaneRenderer extends EntityRenderer<PaperPlaneEntity> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID, "textures/entity/paper_plane.png");
    protected static final PaperPlaneModel<PaperPlaneEntity> model = new PaperPlaneModel<>();

    public PaperPlaneRenderer(EntityRendererManager rendererManager) {
        super(rendererManager);
        this.shadowRadius = 0.8f;
    }

    @Override
    public ResourceLocation getTextureLocation(PaperPlaneEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(PaperPlaneEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        matrixStack.pushPose();
        // 위치를 위로 올립니다.
        matrixStack.translate(0, 4.5, 0);
        // matrixStack.scale(1f, -1f, 1f) 를 통해 모델을 뒤집어줍니다.
        // 또한 여기서 각 숫자를 *3을 해서 크기도 키워봤습니다.
        matrixStack.scale(3.0f, -3.0f, 3.0f);
        // 시선에 따라 모델을 돌립니다.
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180f - entityYaw));

        model.setupAnim(entity, partialTicks, 0.0f, -0.1f, 0.0f, 0.0f);
        IVertexBuilder ivertexbuilder = buffer.getBuffer(model.renderType(getTextureLocation(entity)));
        model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

        matrixStack.popPose();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }
}
