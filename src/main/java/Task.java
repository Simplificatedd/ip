public class Task {
    protected final String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getStatus() {
        return isDone ? "[X] " : "[ ] ";
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return "[?]";
    }

    @Override
    public String toString() {
        return getType() + getStatus() + description;
    }
}
