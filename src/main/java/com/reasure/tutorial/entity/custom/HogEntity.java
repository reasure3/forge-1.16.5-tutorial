package com.reasure.tutorial.entity.custom;

import com.reasure.tutorial.entity.ModEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.IPacket;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

// 아직 미완성 코드
public class HogEntity extends AnimalEntity {
    public static final float controlSpeedModifier = 1.2f;
    public static final float sprintSpeedModifier = 1.5f;
    private static final Ingredient FOOD_ITEMS = Ingredient.of(Items.CARROT, Items.POTATO, Items.BEETROOT);

    public HogEntity(EntityType<? extends HogEntity> entityType, World world) {
        super(entityType, world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 12.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        // p_75776_1 : priority - 작을수록 순위가 높음
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D)); // p_i1645_2 : speed
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D)); // p_i1619_2 : speed
        // p_i47823_2 : speed, p_i47822_5 : scared by player movement
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, Ingredient.of(Items.CARROT_ON_A_STICK), false));
        //  p_i47823_2 : speed, p_i47823_4 : scared by player movement
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, false, FOOD_ITEMS));
        // 숫자는 대부분 속도인듯
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F)); // p_i1631_3 : max distance
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    @Override
    protected int getExperienceReward(PlayerEntity playerEntity) {
        return 1 + this.level.random.nextInt(4);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PIG_AMBIENT;
    }

    @Override
    protected void playStepSound(BlockPos blockPos, BlockState blockState) {
        // 숫자는 순서대로 volume, pitch
        this.playSound(SoundEvents.PIG_STEP, 0.15f, 1.0f);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PIG_DEATH;
    }

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld world, AgeableEntity ageableEntity) {
        return ModEntityTypes.HOG.get().create(this.level);
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getControllingPassenger() instanceof LivingEntity;
    }

    @Nullable
    public Entity getControllingPassenger() {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        // 번식용 템이 아니면
        if (!this.isFood(player.getItemInHand(hand))) {
            // 누가 타고 있지 않고 플레이어가 Shift 키를 누르고 있지 않으면
            if (!this.isVehicle() && !player.isSecondaryUseActive()) {
                // 회전과 엔티티 탑승은 서버 사이드에서
                if (!this.level.isClientSide) {
                    player.yRot = this.yRot;
                    player.xRot = this.xRot;
                    player.startRiding(this);
                }
                return ActionResultType.sidedSuccess(this.level.isClientSide);
            }
        }
        return super.mobInteract(player, hand);
    }

    @OnlyIn(Dist.CLIENT)
    public Vector3d getLeashOffset() {
        return new Vector3d(0.0D, 0.6F * this.getEyeHeight(), this.getBbWidth() * 0.4F);
    }

    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        // TODO
    }

    // TODO
    @Override
    public void travel(Vector3d travelVector) {
        // 누가 타고 있다면
        if (this.isAlive() && this.isVehicle()) {
            Entity entity = this.getControllingPassenger();
            if (entity instanceof LivingEntity) {
                LivingEntity passenger = (LivingEntity) entity;

                // 회전 설정
                this.yRot = passenger.yRot;
                this.yRotO = this.yRot;
                this.xRot = passenger.xRot * 0.5F;
                this.setRot(this.yRot, this.xRot);
                this.yBodyRot = this.yRot;
                this.yHeadRot = this.yBodyRot;

                this.maxUpStep = 1.0f;

                // 기본적으로 엔티티 이동은 Server Side 에서 이루어진다.
                // 다만, 예외적으로 플레이어와 플레이어가 탑승중인 엔티티의 이동은 Client Side 에서 이루어진다.
                // 1. 탈 수 있는 엔티티
                // 2 - 1. 탑승 중인 엔티티가 플레이어 -> Client Player 인가?
                // 2 - 2. 탑승 중인 엔티티가 나머지 -> Server Side 인가?
                if (this.isControlledByLocalInstance()) {
                    this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    super.travel(getHogSpeed(passenger));
                    // 두 틱간 중간의 과정을 보간하기 위한 값을 초기화
                    this.lerpSteps = 0;
                } else {
                    this.calculateEntityAnimation(this, false);
                    this.setDeltaMovement(Vector3d.ZERO);
                }
            }
        } else {
            // 누가 타고 있지 않으면
            this.maxUpStep = 0.5f;
            super.travel(travelVector);
        }
    }

    private Vector3d getHogSpeed(LivingEntity passenger) {
        double movement_speed = this.getAttributeValue(Attributes.MOVEMENT_SPEED) * controlSpeedModifier;
        if (passenger.isSprinting()) movement_speed *= sprintSpeedModifier;
        return new Vector3d(movement_speed * getDirect(passenger.xxa), passenger.yya, movement_speed * getDirect(passenger.zza));
    }

    private int getDirect(double speed) {
        if (Math.abs(speed) < 1.0e-5) return 0;
        if (speed < 0.0) return -1;
        return 1;
    }
}
