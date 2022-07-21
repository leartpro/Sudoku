package controller;

/**
 *
 */
public interface ProgressMonitor {
    /**
     * @param name
     * @param totalSteps
     */
    void displayLoading(String name, int totalSteps);

    /**
     *
     */
    void increaseProgress();

    /**
     *
     */
    void completeProgress();
}
