package com.stalkermod.ai;

import com.stalkermod.entity.LurkerEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class StalkPlayerGoal extends Goal {
    private final LurkerEntity stalker;
    private PlayerEntity targetPlayer;
    private int teleportCooldown = 0;

    public StalkPlayerGoal(LurkerEntity stalker) {
        this.stalker = stalker;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    @Override
    public boolean canStart() {
        this.targetPlayer = this.stalker.getWorld().getClosestPlayer(this.stalker, 48.0D);
        return this.targetPlayer != null && !this.targetPlayer.isCreative();
    }

    @Override
    public void tick() {
        if (targetPlayer == null) return;

        this.stalker.getLookControl().lookAt(targetPlayer, 30.0F, 30.0F);
        double distance = this.stalker.squaredDistanceTo(targetPlayer);

        boolean isObserved = isPlayerLookingAt(targetPlayer, stalker);

        if (isObserved) {
            this.stalker.getNavigation().stop();
            teleportCooldown++;

            if (teleportCooldown > 40) {
                teleportBehindPlayer();
                teleportCooldown = 0;
            }
        } else {
            teleportCooldown = 0;
            if (distance > 16.0D) {
                this.stalker.getNavigation().startMovingTo(targetPlayer, 1.2D);
            } else {
                if (distance < 4.0D) {
                    stalker.getWorld().playSound(
                            null, stalker.getBlockPos(),
                            SoundEvents.ENTITY_ENDERMAN_SCREAM, SoundCategory.HOSTILE,
                            2.0F, 0.5F
                    );
                    stalker.tryAttack(targetPlayer);
                }
            }
        }
    }

    private boolean isPlayerLookingAt(PlayerEntity player, LurkerEntity entity) {
        Vec3d viewVec = player.getRotationVec(1.0F).normalize();
        Vec3d differenceVec = new Vec3d(
                entity.getX() - player.getX(),
                entity.getEyeY() - player.getEyeY(),
                entity.getZ() - player.getZ()
        ).normalize();

        double dotProduct = viewVec.dotProduct(differenceVec);
        return dotProduct > 0.5D;
    }

    private void teleportBehindPlayer() {
        Vec3d lookVec = targetPlayer.getRotationVec(1.0F);
        double targetX = targetPlayer.getX() - (lookVec.x * 10.0D);
        double targetZ = targetPlayer.getZ() - (lookVec.z * 10.0D);
        double targetY = targetPlayer.getY();

        this.stalker.teleport(targetX, targetY, targetZ);
        this.stalker.getWorld().playSound(
                null, this.stalker.getBlockPos(),
                SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.HOSTILE,
                1.0F, 0.2F
        );
    }
}
