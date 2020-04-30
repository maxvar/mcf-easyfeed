package ru.maxvar.mcf.easyfeed.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.maxvar.mcf.easyfeed.EatTreatsGoal;

@Mixin(HorseBaseEntity.class)
public abstract class HorseEntityMixin extends AnimalEntity {

    @SuppressWarnings("unused")
    protected HorseEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @SuppressWarnings("unused")
    @Inject(method = "initGoals", at = @At("TAIL"))
    public void addEatTreatsGoal(CallbackInfo ci) {
        this.goalSelector.add(2, new EatTreatsGoal(this, 6D, 1.2D));
    }

    private static Ingredient BREEDING_INGREDIENTS = Ingredient.ofItems(Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.GOLDEN_CARROT);

    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENTS.test(stack);
    }
}