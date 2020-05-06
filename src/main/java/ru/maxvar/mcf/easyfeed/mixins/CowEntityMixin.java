package ru.maxvar.mcf.easyfeed.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.maxvar.mcf.easyfeed.EatTreatsGoal;

@Mixin(CowEntity.class)
public abstract class CowEntityMixin extends AnimalEntity {

    @SuppressWarnings("unused")
    protected CowEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("unused")
    @Inject(method = "initGoals", at = @At("TAIL"))
    public void addEatTreatsGoal(CallbackInfo ci) {
        this.goalSelector.add(2, new EatTreatsGoal(this, 6D, 1.25D));
    }
}