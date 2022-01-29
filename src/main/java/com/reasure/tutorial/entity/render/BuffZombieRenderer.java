package com.reasure.tutorial.entity.render;

import com.reasure.tutorial.TutorialMod;
import com.reasure.tutorial.entity.custom.BuffZombieEntity;
import com.reasure.tutorial.entity.model.BuffZombieModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class BuffZombieRenderer extends MobRenderer<BuffZombieEntity, BuffZombieModel<BuffZombieEntity>> {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(TutorialMod.MOD_ID, "textures/entity/buff_zombie.png");

    public BuffZombieRenderer(EntityRendererManager rendererManager) {
        // 숫자 : 그림자 크기
        super(rendererManager, new BuffZombieModel<>(), 0.7f);
    }

    @Override
    public ResourceLocation getTextureLocation(BuffZombieEntity pEntity) {
        return TEXTURE;
    }
}
