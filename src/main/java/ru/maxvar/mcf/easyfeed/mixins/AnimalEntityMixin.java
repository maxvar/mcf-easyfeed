package ru.maxvar.mcf.easyfeed.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.maxvar.mcf.easyfeed.EatTreatsGoal;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity {

    @SuppressWarnings("FieldCanBeLocal")
    private EatTreatsGoal eatTreatsGoal;

    @SuppressWarnings("unused")
    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("unused")
    @Inject(method = "<init>*", at = @At("RETURN"))
    public void onConstructed(CallbackInfo ci) {
        this.eatTreatsGoal = new EatTreatsGoal(this);
        int weight = EatTreatsGoal.getWeight(this);
        this.goalSelector.add(weight, this.eatTreatsGoal);
    }
}