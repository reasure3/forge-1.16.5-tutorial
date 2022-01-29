package com.reasure.tutorial.entity.custom;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class PigeonEntity extends ParrotEntity {
    public PigeonEntity(EntityType<? extends ParrotEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new PigeonFlyingMovementController(this, 10, false);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.FLYING_SPEED, 1.3D)
                .add(Attributes.MOVEMENT_SPEED, 1.6D);
    }

    @Override
    protected int getExperienceReward(PlayerEntity pPlayer) {
        return 1 + this.level.random.nextInt(4);
    }

    @Override
    public SoundEvent getAmbientSound() {
        this.playSound(SoundEvents.CHICKEN_AMBIENT, 0.2f, 1.0f);
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        this.playSound(SoundEvents.RABBIT_HURT, 1.0F, 1.7F);
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        this.playSound(SoundEvents.PARROT_DEATH, 0.7F, 2.0F);
        return null;
    }

    @Override
    public Vector3d getLeashOffset() {
        return new Vector3d(5.0D, 5.5f * this.getEyeHeight(), this.getBbWidth() * 5.4f);
    }
}
