package utils;

public class ProgressBar {
    private Bar bar;
    private boolean hasSteps = false;

    public ProgressBar(String name) {
        this.bar = new Bar(name);
    }

    public ProgressBar(String name, Integer totalSteps) {
        this.bar = new Bar(name, totalSteps);
        hasSteps = true;
    }

    public void addProgress() {
        if(hasSteps) bar.addStep();
        else bar.addProgress();
        System.out.print(bar);

    }

    public void addProgress(int steps) {
        if(hasSteps) bar.addSteps(steps);
        else bar.addProgress(steps);
        System.out.print(bar);
    }

    public void clearProgress() {
        bar.clear();
        System.out.print(bar);
    }

    private class Bar {

        private final String name;
        private final Integer totalSteps;
        private int progress;
        private Integer progressSteps;

        public Bar(String name) {
            this.name = name;
            this.totalSteps = null;
        }

        public Bar(String name, Integer totalSteps) {
            this.name = name;
            this.totalSteps = totalSteps;
        }

        public void clear() {
            progress = 0;
        }

        @Override
        public String toString() {
            return
                    (name != null ? (name + " ") : "") +
                            getPercentage() + " [" +
                            getProgress() + " ] " +
                            (totalSteps != null ? (getUnits() + " ") : "") + "\r";
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
            if(progressSteps + value >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps+=value;
            progress = progressSteps / totalSteps * 100;
        }

        public void addStep() {
            assert totalSteps != null;
            if(progressSteps + 1 >= totalSteps.hashCode()) progressSteps = totalSteps;
            else progressSteps+=1;
            progress = progressSteps / totalSteps * 100;
        }

        public void addProgress() {
            if(progress+1 <= 100) progress+=1;
        }

        public void addProgress(int value) {
            if(progress+value <= 100) progress+=value;
        }
    }
}
