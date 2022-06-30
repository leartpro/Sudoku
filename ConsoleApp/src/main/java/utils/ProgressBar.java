package utils;

public class ProgressBar {
    private final Bar bar;
    private final boolean hasSteps;

    public ProgressBar(String name) {
        this.bar = new Bar(name);
        hasSteps = false;
    }

    public ProgressBar(String name, Integer totalSteps) {
        this.bar = new Bar(name, totalSteps);
        hasSteps = true;
    }

    public void addProgress() {
        if (hasSteps) bar.addStep();
        else bar.addProgress();
        System.out.print(bar);
    }

    public void addProgress(int steps) {
        if (hasSteps) bar.addSteps(steps);
        else bar.addProgress(steps);
        System.out.print(bar);
    }

    public boolean isCompleted() {
        return bar.isCompleted;
    }

    private static class Bar {

        private final String name;
        private final Integer totalSteps;
        private int progress;
        private int progressSteps = 0;
        private boolean isCompleted = false;

        public Bar(String name) {
            this.name = name;
            this.totalSteps = null;

        }

        public Bar(String name, Integer totalSteps) {
            this.name = name;
            this.totalSteps = totalSteps;
        }

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

        private String getUnits() {
            return progressSteps + " / " + totalSteps;
        }

        private String getProgress() {
            return "=".repeat(Math.max(0, progress / 2)) + (progress == 100 ? "" : ">");
        }

        private String getPercentage() {
            return progress + "%";
        }

        public void addSteps(int value) {
            assert totalSteps != null;
            if (progressSteps + value >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps += value;
            progress = (int) (float) (progressSteps * 100 / totalSteps);
        }

        public void addStep() {
            assert totalSteps != null;
            if (progressSteps + 1 >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps += 1;
            progress = (int) (float) (progressSteps * 100 / totalSteps);
        }

        public void addProgress() {
            if (progress + 1 <= 100) progress += 1;
        }

        public void addProgress(int value) {
            if (progress + value <= 100) progress += value;
        }
    }
}
