package dick.storage;

import dick.task.Deadline;
import dick.task.Event;
import dick.task.Task;
import dick.task.Todo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Path.of(filePath);
    }

    public List<Task> load() {
        ensureParentDirExists();

        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException ignored) {
                // if somehow unable to create, it will behave like an empty storage
            }
            return new ArrayList<>();
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            List<Task> tasks = new ArrayList<>();

            for (String line : lines) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                Task t = parseLine(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
            return tasks;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public void save(List<Task> tasks) {
        ensureParentDirExists();

        List<String> lines = new ArrayList<>();
        for (Task t : tasks) {
            lines.add(serializeTask(t));
        }

        try {
            Files.write(filePath, lines);
        } catch (IOException ignored) {
            // ignore save failures (or throw if you prefer)
        }
    }

    private void ensureParentDirExists() {
        Path parent = filePath.getParent();
        if (parent == null) {
            return;
        }
        try {
            Files.createDirectories(parent);
        } catch (IOException ignored) {
            // ignore
        }
    }

    private static String serializeTask(Task t) {
        int done = t.isDone() ? 1 : 0;

        // NOTE: this requires your Task/Todo/Deadline/Event to have these getters.
        if (t instanceof Todo todo) {
            return "TODO|" + done + "|" + escape(todo.getDescription());
        }
        if (t instanceof Deadline deadline) {
            return "DEADLINE|" + done + "|" + escape(deadline.getDescription())
                    + "|" + escape(deadline.getBy());
        }
        if (t instanceof Event event) {
            return "EVENT|" + done + "|" + escape(event.getDescription())
                    + "|" + escape(event.getFrom()) + "|" + escape(event.getTo());
        }
        return "TASK|" + done + "|" + escape(t.getDescription());
    }

    private static Task parseLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean isDone = "1".equals(parts[1]);
        String desc = unescape(parts[2]);

        Task task;
        switch (type) {
            case "TODO":
                task = new Todo(desc);
                break;
            case "DEADLINE":
                if (parts.length < 4) return null;
                task = new Deadline(desc, unescape(parts[3]));
                break;
            case "EVENT":
                if (parts.length < 5) return null;
                task = new Event(desc, unescape(parts[3]), unescape(parts[4]));
                break;
            case "TASK":
                task = new Task(desc);
                break;
            default:
                return null;
        }

        if (isDone) {
            task.mark();
        }
        return task;
    }

    private static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("|", "\\p");
    }

    private static String unescape(String s) {
        return s.replace("\\p", "|")
                .replace("\\n", "\n")
                .replace("\\\\", "\\");
    }
}