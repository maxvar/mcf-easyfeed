package ru.maxvar.mcf.easyfeed;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.*;

import java.util.EnumSet;
import java.util.List;

public class EatTreatsGoal extends Goal {

    private final AnimalEntity animal;
    private ItemEntity targetFood;
    private Double range;
    private Double speed;

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

    /**
     * Somehow it Knows about the weight for Tempt Goal or Mate Goal of all animals and
     * recommends the weight for Eat Treats Goal (== tempt goal or mate goal +1)
     *
     * @param entity an [animal] entity
     * @return recommended weight
     */
    public static int getWeight(PassiveEntity entity) {
        if (entity instanceof BeeEntity)
            return 3;
        if (entity instanceof CatEntity)
            return 3;
        if (entity instanceof ChickenEntity)
            return 3;
        if (entity instanceof CowEntity)
            return 3;
        if (entity instanceof LlamaEntity)
            return 5;
        if (entity instanceof HorseBaseEntity)
            return 3;
        if (entity instanceof OcelotEntity)
            return 9;
        if (entity instanceof PigEntity)
            return 4;
        if (entity instanceof RabbitEntity)
            return 3;
        if (entity instanceof SheepEntity)
            return 3;
        if (entity instanceof TurtleEntity)
            return 2;
        if (entity instanceof WolfEntity)
            return 9;
        return 4; //better than nothing
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
        List<ItemEntity> entities = animal.world.getEntities(
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
                animal.getLookControl().lookAt(targetFood, animal.getLookYawSpeed() + 20, animal.getLookPitchSpeed());
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
