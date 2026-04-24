/*
 * Decompiled with CFR 0_125.
 * 
 * Could not load the following classes:
 *  com.astryxion.chaospersists.entity.EntityCannonFodder
 *  com.astryxion.chaospersists.MyEntityAIAvoidEntity
 *  net.minecraft.command.IEntitySelector
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityCreature
 *  net.minecraft.entity.ai.EntityAIBase
 *  net.minecraft.entity.ai.RandomPositionGenerator
 *  net.minecraft.entity.monster.IMob
 *  net.minecraft.entity.passive.EntityTameable
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.pathfinding.Path
 *  net.minecraft.pathfinding.PathNavigate
 *  net.minecraft.util.math.AxisAlignedBB
 *  net.minecraft.util.math.Vec3d
 *  net.minecraft.world.World
 */
package com.astryxion.chaospersists.util;

import com.astryxion.chaospersists.entity.EntityCannonFodder;
import java.util.List;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MyEntityAIAvoidEntity
extends EntityAIBase {
    private EntityCreature theEntity;
    private double farSpeed;
    private double nearSpeed;
    private Entity closestLivingEntity;
    private float distanceFromEntity;
    private Path entityPath;
    private PathNavigate entityPathNavigate;
    private Class targetEntityClass;

    public MyEntityAIAvoidEntity(EntityCreature par1EntityCreature, Class par2Class, float par3, double par4, double par6) {
        this.theEntity = par1EntityCreature;
        this.targetEntityClass = par2Class;
        this.distanceFromEntity = par3;
        this.farSpeed = par4;
        this.nearSpeed = par6;
        this.entityPathNavigate = par1EntityCreature.getNavigator();
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        EntityCannonFodder cf;
        Vec3d Vec3d;
        if (this.theEntity != null && this.theEntity instanceof EntityCannonFodder && (cf = (EntityCannonFodder)this.theEntity).get_is_activated() != 0) {
            return false;
        }
        if (this.targetEntityClass == EntityPlayer.class) {
            if (this.theEntity instanceof EntityTameable && ((EntityTameable)this.theEntity).isTamed()) {
                return false;
            }
            this.closestLivingEntity = this.theEntity.world.getClosestPlayerToEntity((Entity)this.theEntity, (double)this.distanceFromEntity);
            if (this.closestLivingEntity == null) {
                return false;
            }
        } else {
            List list = this.theEntity.world.getEntitiesWithinAABB(this.targetEntityClass, this.theEntity.getEntityBoundingBox().expand((double)this.distanceFromEntity, 3.0, (double)this.distanceFromEntity), e -> e instanceof IMob);
            if (list.isEmpty()) {
                return false;
            }
            this.closestLivingEntity = (Entity)list.get(0);
        }
        if ((Vec3d = RandomPositionGenerator.findRandomTargetBlockAwayFrom((EntityCreature)this.theEntity, (int)16, (int)7, (Vec3d)new Vec3d((double)this.closestLivingEntity.posX, (double)this.closestLivingEntity.posY, (double)this.closestLivingEntity.posZ))) == null) {
            return false;
        }
        if (this.closestLivingEntity.getDistanceSq(Vec3d.x, Vec3d.y, Vec3d.z) < this.closestLivingEntity.getDistanceSq((Entity)this.theEntity)) {
            return false;
        }
        this.entityPath = this.entityPathNavigate.getPathToXYZ(Vec3d.x, Vec3d.y, Vec3d.z);
        if (this.entityPath == null) return false;
        PathPoint last = this.entityPath.getPathPointFromIndex(this.entityPath.getCurrentPathLength() - 1);
        return last != null && Math.abs(last.x + 0.5 - Vec3d.x) < 0.5 && Math.abs(last.y + 0.5 - Vec3d.y) < 0.5 && Math.abs(last.z + 0.5 - Vec3d.z) < 0.5;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !this.entityPathNavigate.noPath();
    }

    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPath, this.farSpeed);
    }

    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }

    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSq(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
        } else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }

    static EntityCreature func_98217_a(MyEntityAIAvoidEntity par0EntityAIAvoidEntity) {
        return par0EntityAIAvoidEntity.theEntity;
    }
}
