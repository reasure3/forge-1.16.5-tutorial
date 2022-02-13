package com.reasure.tutorial.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class TutorialEntity extends Entity {
    public TutorialEntity(EntityType<?> type, World level) {
        super(type, level);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compound) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {

    }
}
