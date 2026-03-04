package dick;

import dick.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks and operations that can be performed on them.
 */
public class TaskList {
    private final List<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list preloaded with tasks.
     *
     * @param tasks Initial tasks to include.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Task count.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task at the given index.
     *
     * @param index 0-based index.
     * @return Task at index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param index 0-based index.
     * @return Removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns an unmodifiable snapshot of the current task list.
     *
     * @return Unmodifiable list of tasks.
     */
    public List<Task> asUnmodifiableList() {
        return List.copyOf(tasks);
    }

    /**
     * Finds tasks whose descriptions contain the given keyword (case-insensitive).
     *
     * @param keyword Keyword to search for.
     * @return List of matching tasks.
     */
    public List<Task> find(String keyword) {
        List<Task> matches = new ArrayList<>();
        String needle = keyword.toLowerCase();

        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(needle)) {
                matches.add(t);
            }
        }
        return matches;
    }
}