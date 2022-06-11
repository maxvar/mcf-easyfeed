package ru.maxvar.mcf.easyfeed;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.*;

import java.util.EnumSet;
import java.util.List;

public class EatTreatsGoal extends Goal {

    private final AnimalEntity animal;
    private ItemEntity targetFood;
    private final Double range;
    private final Double speed;

    @SuppressWarnings("unused")
    public EatTreatsGoal(AnimalEntity animal, double range, double speed) {
        this.animal = animal;
        this.range = range;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    private boolean isEager(AnimalEntity animal) {
        return animal.getBreedingAge() == 0 && animal.canEat();
    }

    @Override
    public boolean canStart() {
        //if the animal can't eat then return immediately
        if (!isEager(animal)) return false;
        targetFood = findClosestFood();
        return targetFood != null;
    }

    private ItemEntity findClosestFood() {
        //find if any suitable food is around
        List<ItemEntity> entities = animal.world.getEntitiesByClass(
                ItemEntity.class, animal.getBoundingBox().expand(range),
                itemEntity -> animal.isBreedingItem(itemEntity.getStack()));
        ItemEntity food = null;
        double closedSquaredDistance = range * range;
        for (ItemEntity itemEntity : entities) {
            //choose the closest suitable stack as target
            double distance = animal.squaredDistanceTo(itemEntity);
            if (distance < closedSquaredDistance) {
                food = itemEntity;
                closedSquaredDistance = distance;
            }
        }
        return food;
    }

    @Override
    public boolean shouldContinue() {
        return targetFood != null && isEager(animal);
    }

    @Override
    public void tick() {
        if (targetFood != null && !animal.world.isClient && isEager(animal)) {
            if (!targetFood.getStack().isEmpty()) {
                //move animal
                animal.getLookControl().lookAt(targetFood, animal.getMaxLookYawChange(), animal.getMaxLookPitchChange());
                animal.getNavigation().startMovingTo(targetFood, speed);
                //feed!
                if (animal.squaredDistanceTo(targetFood) < 4.0D) {
                    animal.getNavigation().stop();
                    targetFood.getStack().decrement(1);
                    animal.lovePlayer(null);
                }
            }
            targetFood = null;
        }
    }
}
