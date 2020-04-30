package ru.maxvar.mcf.easyfeed.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.maxvar.mcf.easyfeed.EatTreatsGoal;

@Mixin(CatEntity.class)
public abstract class CatEntityMixin extends TameableEntity {

    @SuppressWarnings("unused")
    protected CatEntityMixin(EntityType<? extends TameableEntity> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("unused")
    @Inject(method = "initGoals", at = @At("TAIL"))
    public void addEatTreatsGoal(CallbackInfo ci) {
        this.goalSelector.add(2, new EatTreatsGoal(this, 10D, 0.6D));
    }
}