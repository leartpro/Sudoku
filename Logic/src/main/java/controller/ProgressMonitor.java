package controller;

public interface ProgressMonitor {
    void displayLoading(String name, int totalSteps);
    void increaseProgress();
    void completeProgress();
}
