package utils;

/**
 * Used to display a progress-bar
 */
public class ProgressBar {
    private final Bar bar;
    private final boolean hasSteps;

    /**
     * @param name the name of the progress
     */
    public ProgressBar(String name) {
        this.bar = new Bar(name);
        hasSteps = false;
    }

    /**
     * @param name the name of the progress
     * @param totalSteps the count of steps to complete the progress
     */
    public ProgressBar(String name, Integer totalSteps) {
        this.bar = new Bar(name, totalSteps);
        hasSteps = true;
    }

    /**
     * increases the progress
     */
    public void addProgress() {
        if (hasSteps) bar.addStep();
        else bar.addProgress();
        System.out.print(bar);
    }

    /**
     * @param steps the count of steps to increase
     */
    public void addProgress(int steps) {
        if (hasSteps) bar.addSteps(steps);
        else bar.addProgress(steps);
        System.out.print(bar);
    }

    /**
     * @return if the progress-bar is completed
     */
    public boolean isCompleted() {
        return bar.isCompleted;
    }

    /**
     * complete the progress
     */
    public void complete() {
        bar.progress = 100;
        if(bar.totalSteps != null) bar.progressSteps = bar.totalSteps;
        System.out.print(bar);
    }

    /**
     * displays the progress bar
     */
    public void display() {
        System.out.print(bar);
    }

    /**
     * represents the progress-bar
     */
    private static class Bar {

        private final String name;
        private final Integer totalSteps;
        private int progress;
        private int progressSteps = 0;
        private boolean isCompleted = false;

        /**
         * @param name the name of the progress
         */
        public Bar(String name) {
            this.name = name;
            this.totalSteps = null;

        }

        /**
         * @param name the name of the progress
         * @param totalSteps the count of steps to complete the progress
         */
        public Bar(String name, Integer totalSteps) {
            this.name = name;
            this.totalSteps = totalSteps;
        }

        /**
         * @return the progress-bar as string
         */
        @Override
        public String toString() {
            if(isCompleted) return "\r";
            if(progress == 100) isCompleted = true;
            return (name != null ? (name + " ") : "") +
                    progress + "%" + " [" +
                    getProgress() + " ] " +
                    (totalSteps != null ? (progressSteps + " / " + totalSteps + " ") : "") + "\r" +
                    (progress == 100 ? "\n" : "");
        }

        /**
         * @return the current progress
         */
        private String getProgress() {
            return "=".repeat(Math.max(0, progress / 2)) + (progress == 100 ? "" : ">");
        }


        /**
         * @param value the count of steps to increase
         */
        public void addSteps(int value) {
            assert totalSteps != null;
            if (progressSteps + value >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps += value;
            progress = (int) (float) (progressSteps * 100 / totalSteps);
        }

        /**
         * increases the progress by one step
         */
        public void addStep() {
            assert totalSteps != null;
            if (progressSteps + 1 >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps += 1;
            progress = (int) (float) (progressSteps * 100 / totalSteps);
        }

        /**
         * increases the progress by one percent
         */
        public void addProgress() {
            if (progress + 1 <= 100) progress += 1;
        }

        /**
         * @param value increases the progress
         */
        public void addProgress(int value) {
            if (progress + value <= 100) progress += value;
        }
    }
}
