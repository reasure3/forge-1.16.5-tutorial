package com.reasure.tutorial.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;

public class PaperPlaneEntity extends Entity {

    private boolean isForward;
    private boolean isBackward;
    private boolean isLeft;
    private boolean isRight;

    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;

    public PaperPlaneEntity(EntityType<?> type, World level) {
        super(type, level);
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean isPickable() {
        return isAlive();
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        tickLerp();

        if (isControlledByLocalInstance()) {
            Entity controller = getControllingPassenger();
            if (controller != null) {
                floatPlane();
                yRotO = yRot;
                yRot = controller.yRot;

                if (level.isClientSide()) {
                    controlPlane(controller);
                }
            } else {
                applyGravity();
            }

            move(MoverType.SELF, getDeltaMovement());
        } else {
            setDeltaMovement(Vector3d.ZERO);
        }
    }

    // 렌더링 보간을 위한 함수. 조종중일 때는, 서버에 위치 보내기.
    private void tickLerp() {
        if (isControlledByLocalInstance()) {
            lerpSteps = 0;
            setPacketCoordinates(getX(), getY(), getZ());
        }

        if (lerpSteps > 0) {
            double d0 = getX() + (lerpX - getX()) / (double) lerpSteps;
            double d1 = getY() + (lerpY - getY()) / (double) lerpSteps;
            double d2 = getZ() + (lerpZ - getZ()) / (double) lerpSteps;
            double d3 = MathHelper.wrapDegrees(lerpYRot - (double) yRot);
            yRot = (float) ((double) yRot + d3 / (double) lerpSteps);
            xRot = (float) ((double) xRot + (lerpXRot - (double) xRot) / (double) lerpSteps);
            --lerpSteps;
            setPos(d0, d1, d2);
            setRot(yRot, xRot);
        }
    }

    private void floatPlane() {
        Vector3d deltaMove = getDeltaMovement();
        setDeltaMovement(deltaMove.x * 0.05f, 0.0, deltaMove.z * 0.05f);
    }

    private void applyGravity() {
        Vector3d deltaMove = getDeltaMovement();
        setDeltaMovement(deltaMove.x * 0.05f, deltaMove.y - 0.04f, deltaMove.z * 0.05f);
    }

    private void controlPlane(@Nullable Entity passenger) {
        if (isVehicle()) {
            float goZ = 0;
            float goX = 0;
            float goY = 0;

            boolean going = false;

            if (isForward) {
                goZ += 0.2f;
                going = true;
            }
            if (isBackward) {
                goZ -= 0.2f;
                going = true;
            }
            if (isLeft)
                goX += 0.1f;
            if (isRight)
                goX -= 0.1f;

            if (passenger != null && going)
                goY = -passenger.xRot * ((float) Math.PI / 180f) * 0.3f;

            float yRotToRad = yRot * ((float) Math.PI / 180f);

            // 정방향 (남쪽)을 본다면
            // getDeltaMovement().add(goX, goY, goZ) 와 같은 코드
            Vector3d deltaMove = getDeltaMovement()
                    .add(MathHelper.sin(-yRotToRad) * goZ, 0.0, MathHelper.cos(yRotToRad) * goZ)
                    .add(MathHelper.cos(yRotToRad) * goX, 0.0, MathHelper.sin(yRotToRad) * goX)
                    .add(0.0, goY, 0.0);

            setDeltaMovement(deltaMove);
        }
    }

    // 반드시 ClientSide에서 일어날 것
    public void updateInputs(boolean isForward, boolean isBackward, boolean isLeft, boolean isRight) {
        this.isForward = isForward;
        this.isBackward = isBackward;
        this.isLeft = isLeft;
        this.isRight = isRight;
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = getPassengers();
        // 보통 첫번째로 탄 탑승자를 엔티티 조종하는 사람으로 여김
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        if (player.isSecondaryUseActive()) {
            return ActionResultType.PASS;
        }

        if (!level.isClientSide()) {
            return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
        }

        return ActionResultType.SUCCESS;
    }

    @Override
    public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        lerpX = x;
        lerpY = y;
        lerpZ = z;
        lerpYRot = yaw;
        lerpXRot = pitch;
        lerpSteps = 10;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (isControlledByLocalInstance() && lerpSteps > 0) {
            lerpSteps = 0;
            absMoveTo(lerpX, lerpY, lerpZ, (float) lerpYRot, (float) lerpXRot);
        }
    }

    //    @Override
    //    protected boolean canAddPassenger(Entity passenger) {
    //        return super.canAddPassenger(passenger);
    //    }
    //
    //    @Override
    //    public void onPassengerTurned(Entity entityToUpdate) {
    //        super.onPassengerTurned(entityToUpdate);
    //    }
    //
    //    @Override
    //    public void move(MoverType type, Vector3d pos) {
    //        super.move(type, pos);
    //    }

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
