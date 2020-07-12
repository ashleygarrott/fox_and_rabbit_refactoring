package io.muic.ooc.fab;

import java.util.List;
import java.util.Random;

public abstract class Animal {
    private boolean alive = true;
    private Location location;
    protected Field field;
    private int age;
    protected static final Random RANDOM = new Random();


    public Animal(){

    }
    public void initialize(boolean randomAge, Field field, Location location) {
        age = 0;
        alive = true;
        this.field = field;
        setLocation(location);
        if (randomAge) {
            age = RANDOM.nextInt(getMaxAge());
        }
    }


    /**
     * Check whether the fox is alive or not.
     *
     * @return True if the fox is still alive.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Return the fox's location.
     *
     * @return The fox's location.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Place the fox at the new location in the given field.
     *
     * @param newLocation The fox's new location.
     */
    protected void setLocation(Location newLocation) {
        if (location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    public Field getField(){ return field;}

    protected void setField(Field field){
        this.field = field;
    }

    /**
     * Generate a number representing the number of births, if it can breed.
     *
     * @return The number of births (may be zero).
     */
    protected int breed() {
        int births = 0;
        if (canBreed() && RANDOM.nextDouble() <= getBreedingProbability()) {
            births = RANDOM.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }

    /**
     * A fox can breed if it has reached the breeding age.
     */
    private boolean canBreed() {
        return age >= getBreedingAge();
    }

    /**
     * Indicate that the fox is no longer alive. It is removed from the field.
     */
    protected void setDead() {
        alive = false;
        if (location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Increase the age. This could result in the fox's death.
     */
    protected void incrementAge() {
        age++;
        if (age > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Check whether or not this animal is to give birth at this step. New births
     * will be made into free adjacent locations.
     *
     * @param newAnimals A list to return newly born foxes.
     */

    protected void giveBirth(List<Animal> newAnimals) {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for (int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            //CHANGE THIS TO GENERAL ANIMAL
            Animal young = generateAnimal(false, field, loc);
            newAnimals.add(young);
        }
    }

    private  Animal generateAnimal(boolean randomAge, Field field, Location location){
        return AnimalFactory.createAnimal(getClass(), field, location);
    }


    //everything that needs to be done in each iteration
    public abstract void update(List<Animal> animals);

    protected abstract int getMaxAge();
    protected abstract int getBreedingAge();
    protected abstract double getBreedingProbability();
    protected abstract int getMaxLitterSize();
    protected abstract int getFoodValue(); //how much nutrient the animal provides





}
