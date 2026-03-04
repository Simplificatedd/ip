package dick;

public class Parser {
    public static ParsedCommand parse(String input) {
        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return new ParsedCommand("", "");
        }

        String[] parts = trimmed.split("\\s+", 2);
        String commandWord = parts[0].toLowerCase();
        String args = (parts.length == 2) ? parts[1].trim() : "";
        return new ParsedCommand(commandWord, args);
    }

    public static class ParsedCommand {
        public final String commandWord;
        public final String args;

        public ParsedCommand(String commandWord, String args) {
            this.commandWord = commandWord;
            this.args = args;
        }
    }
}