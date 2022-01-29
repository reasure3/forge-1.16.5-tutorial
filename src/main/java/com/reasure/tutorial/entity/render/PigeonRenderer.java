package com.reasure.tutorial.entity.render;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.entity.custom.PigeonEntity;
import com.reasure.tutorial.entity.model.PigeonModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class PigeonRenderer extends MobRenderer<PigeonEntity, PigeonModel<PigeonEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID, "textures/entity/pigeon.png");

    public PigeonRenderer(EntityRendererManager rendererManager) {
        // 숫자 : 그림자 크기
        super(rendererManager, new PigeonModel<>(), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(PigeonEntity pEntity) {
        return TEXTURE;
    }
}
