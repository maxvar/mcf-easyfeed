package ru.maxvar.mcf.easyfeed;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.*;
import net.minecraft.world.World;

import java.util.List;

public class EatTreatsGoal extends Goal {

    private final AnimalEntity animal;
    private ItemEntity targetItemEntity;

    public EatTreatsGoal(PassiveEntity animal) {
        if (animal instanceof AnimalEntity)
            this.animal = (AnimalEntity) animal;
        else
            throw new IllegalArgumentException("argument got EatTreatsGoal is not an AnimalEntity");
    }

    /**
     * Somehow it Knows about the weight for Tempt Goal or Mate Goal of all animals and
     * recommends the weight for Eat Treats Goal (== tempt goal or mate goal +1)
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
        //if the animal can eat and it's standing exactly in pos with its breeding item
        if (!animal.canEat()) return false;
        World world = animal.world;
        List<ItemEntity> entities = world.getEntities(ItemEntity.class, animal.getBoundingBox(),
                itemEntity -> animal.isBreedingItem(itemEntity.getStack()));
        if (!entities.isEmpty()) {
            for (ItemEntity itemEntity : entities)
                targetItemEntity = itemEntity;
            System.out.println("canStart" + animal.getUuid());
            return true;
        }
        return false;
    }

    @Override
    public boolean shouldContinue() {
        return animal.canEat() && targetItemEntity != null;
    }

    @Override
    public void tick() {
        System.out.println("tick" + animal.getUuid());
        if (targetItemEntity != null && animal.canEat()) {
            feedAnimal(animal);
            targetItemEntity = null;
        }
    }

    private void feedAnimal(AnimalEntity mob) {
        if (!mob.world.isClient && mob.getBreedingAge() == 0 && mob.canEat()) {
            eat(targetItemEntity);
            mob.lovePlayer(null);
            return;
        }

        if (mob.isBaby()) {
            eat(targetItemEntity);
            mob.growUp((int) ((float) (-mob.getBreedingAge() / 20) * 0.1F), true);
            return;
        }
    }

    private void eat(ItemEntity targetItemEntity) {
        targetItemEntity.getStack().decrement(1);
    }

}
