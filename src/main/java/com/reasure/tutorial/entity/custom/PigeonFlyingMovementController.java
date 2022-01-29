package com.reasure.tutorial.entity.custom;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.util.math.MathHelper;

public class PigeonFlyingMovementController extends MovementController {
    private final int maxTurn;
    private final boolean hoversInPlace;

    public PigeonFlyingMovementController(MobEntity pigeonEntity, int maxTurn, boolean hoversInPlace) {
        super(pigeonEntity);
        this.maxTurn = maxTurn;
        this.hoversInPlace = hoversInPlace;
    }

    public void tick() {
        if (operation == MovementController.Action.MOVE_TO) {
            operation = MovementController.Action.WAIT;
            mob.setNoGravity(true);

            double dx = wantedX - mob.getX();
            double dy = wantedY - mob.getY();
            double dz = wantedZ - mob.getZ();
            double sqrDistance = dx * dx + dy * dy + dz * dz;

            if (sqrDistance < 2.5e-7) {
                mob.setYya(0.0f);
                mob.setZza(0.0f);
                return;
            }

            float toYRot = rad2deg(MathHelper.atan2(dz, dx)) - 90;
            // yRot : y축을 기준으로 회전한 정도 : 고개를 양옆으로
            mob.yRot = rotlerp(mob.yRot, toYRot, 90.0f);

            float speed;
            if (mob.isOnGround()) {
                speed = (float) (speedModifier * mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
            } else {
                speed = (float) (speedModifier * mob.getAttributeValue(Attributes.FLYING_SPEED));
            }
            mob.setSpeed(speed);

            double horizonDistance = MathHelper.sqrt(dx * dx + dz * dz);
            float toXRot = -1 * rad2deg(MathHelper.atan2(dy, horizonDistance));
            // xRot : x축을 기준으로 회전한 정도 : 고개를 위 아래로
            mob.xRot = rotlerp(mob.xRot, toXRot, maxTurn);

            mob.setYya(dy > 0.0D ? speed : -speed);
        } else {
            if (!hoversInPlace) {
                mob.setNoGravity(false);
            }

            mob.setYya(0.0f);
            mob.setZza(0.0f);
        }

    }

    float rad2deg(double rad) {
        return (float) (rad * (180 / Math.PI));
    }
}
