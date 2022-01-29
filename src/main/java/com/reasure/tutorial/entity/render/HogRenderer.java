package com.reasure.tutorial.entity.render;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.entity.custom.HogEntity;
import com.reasure.tutorial.entity.model.HogModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class HogRenderer extends MobRenderer<HogEntity, HogModel<HogEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID, "textures/entity/hog.png");

    public HogRenderer(EntityRendererManager rendererManager) {
        // 숫자 : 그림자 크기
        super(rendererManager, new HogModel<>(), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(HogEntity hog) {
        return TEXTURE;
    }
}
