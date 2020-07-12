package io.muic.ooc.fab;

import java.util.Iterator;
import java.util.List;

public class Tiger extends Animal{
    // Characteristics shared by all foxes (class variables).

    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.

    private int foodLevel;
    //how well the tiger absorbs food compared to energy expenditure
    private final double foodMultiplier = 0.5;

    /**
     * Create a fox. A fox can be created as a new born (age zero and not
     * hungry) or with a random age and food level.
     *
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */

    @Override
    public void initialize(boolean randomAge, Field field, Location location) {
        super.initialize(randomAge, field, location);
        if (randomAge) {
            foodLevel = RANDOM.nextInt(AnimalType.RABBIT.getFoodValue());
        } else {
            // leave age at 0
            foodLevel = RANDOM.nextInt(AnimalType.RABBIT.getFoodValue());
        }
    }



    @Override
    protected int getMaxAge() {
        return 150;
    }

    @Override
    protected int getBreedingAge() {
        return 15;
    }

    @Override
    protected double getBreedingProbability() {
        return 0.08;
    }

    @Override
    protected int getMaxLitterSize() {
        return 2;
    }

    @Override
    protected int getFoodValue() {
        return 20;
    }

    /**
     * This is what the fox does most of the time: it hunts for rabbits. In the
     * process, it might breed, die of hunger, or die of old age.
     *
     * @param newFoxes A list to return newly born foxes.
     */

    @Override
    public void update(List<Animal> newFoxes) {
        incrementAge();
        incrementHunger();
        if (isAlive()) {
            giveBirth(newFoxes);
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if (newLocation == null) {
                // No food found - try to move to a free location.
                newLocation = field.freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
            if (newLocation != null) {
                setLocation(newLocation);
            } else {
                // Overcrowding.
                setDead();
            }
        }
    }








    /**
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger() {
        foodLevel--;
        if (foodLevel <= 0) {
            setDead();
        }
    }

    /**
     * Look for rabbits adjacent to the current location. Only the first live
     * rabbit is eaten.
     *
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood() {
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while (it.hasNext()) {
            Location where = it.next();
            Object object = field.getObjectAt(where);
            if (object instanceof Rabbit) {
                Animal animal = (Animal) object;
                if (animal.isAlive()) {
                    animal.setDead();
                    foodLevel = (int) (foodMultiplier * AnimalType.RABBIT.getFoodValue());
                    return where;
                }
            }
            else if (object instanceof Fox){
                Animal animal = (Animal) object;
                if (animal.isAlive()) {
                    animal.setDead();
                    foodLevel = (int) (foodMultiplier * AnimalType.FOX.getFoodValue());
                    return where;
                }
            }
        }
        return null;
    }











}
