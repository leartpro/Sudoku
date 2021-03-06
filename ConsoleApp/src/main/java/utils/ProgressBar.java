package utils;

/**
 *
 */
public class ProgressBar {
    private final Bar bar;
    private final boolean hasSteps;

    /**
     * @param name
     */
    public ProgressBar(String name) {
        this.bar = new Bar(name);
        hasSteps = false;
    }

    /**
     * @param name
     * @param totalSteps
     */
    public ProgressBar(String name, Integer totalSteps) {
        this.bar = new Bar(name, totalSteps);
        hasSteps = true;
    }

    /**
     *
     */
    public void addProgress() {
        if (hasSteps) bar.addStep();
        else bar.addProgress();
        System.out.print(bar);
    }

    /**
     * @param steps
     */
    public void addProgress(int steps) {
        if (hasSteps) bar.addSteps(steps);
        else bar.addProgress(steps);
        System.out.print(bar);
    }

    /**
     * @return
     */
    public boolean isCompleted() {
        return bar.isCompleted;
    }

    /**
     *
     */
    public void complete() {
        bar.progress = 100;
        if(bar.totalSteps != null) bar.progressSteps = bar.totalSteps;
        System.out.print(bar);
    }

    /**
     *
     */
    public void display() {
        System.out.print(bar);
    }

    /**
     *
     */
    private static class Bar {

        private final String name;
        private final Integer totalSteps;
        private int progress;
        private int progressSteps = 0;
        private boolean isCompleted = false;

        /**
         * @param name
         */
        public Bar(String name) {
            this.name = name;
            this.totalSteps = null;

        }

        /**
         * @param name
         * @param totalSteps
         */
        public Bar(String name, Integer totalSteps) {
            this.name = name;
            this.totalSteps = totalSteps;
        }

        /**
         * @return
         */
        @Override
        public String toString() {
            if(isCompleted) return "\r";
            if(progress == 100) isCompleted = true;
            return (name != null ? (name + " ") : "") +
                    getPercentage() + " [" +
                    getProgress() + " ] " +
                    (totalSteps != null ? (getUnits() + " ") : "") + "\r" +
                    (progress == 100 ? "\n" : "");
        }

        /**
         * @return
         */
        private String getUnits() {
            return progressSteps + " / " + totalSteps;
        }

        /**
         * @return
         */
        private String getProgress() {
            return "=".repeat(Math.max(0, progress / 2)) + (progress == 100 ? "" : ">");
        }

        /**
         * @return
         */
        private String getPercentage() {
            return progress + "%";
        }

        /**
         * @param value
         */
        public void addSteps(int value) {
            assert totalSteps != null;
            if (progressSteps + value >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps += value;
            progress = (int) (float) (progressSteps * 100 / totalSteps);
        }

        /**
         *
         */
        public void addStep() {
            assert totalSteps != null;
            if (progressSteps + 1 >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps += 1;
            progress = (int) (float) (progressSteps * 100 / totalSteps);
        }

        /**
         *
         */
        public void addProgress() {
            if (progress + 1 <= 100) progress += 1;
        }

        /**
         * @param value 
         */
        public void addProgress(int value) {
            if (progress + value <= 100) progress += value;
        }
    }
}
