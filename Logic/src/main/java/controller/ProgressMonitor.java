package controller;

/**
 * used to handle the progress in Game.java
 */
public interface ProgressMonitor {
    /**
     * @param name the name of the progress
     * @param totalSteps the count of steps to complete the progress
     */
    void displayLoading(String name, int totalSteps);

    /**
     * increases the progress by one
     */
    void increaseProgress();

    /**
     * completes the progress
     */
    void completeProgress();
}
