package ru.maxvar.mcf.easyfeed.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.maxvar.mcf.easyfeed.EatTreatsGoal;

@Mixin(LlamaEntity.class)
public abstract class LlamaEntityMixin extends AbstractDonkeyEntity {

    @SuppressWarnings("unused")
    protected LlamaEntityMixin(EntityType<? extends AbstractDonkeyEntity> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("unused")
    @Inject(method = "initGoals", at = @At("TAIL"))
    public void addEatTreatsGoal(CallbackInfo ci) {
        this.goalSelector.add(4, new EatTreatsGoal(this, 6D, 1.1D));
    }
}