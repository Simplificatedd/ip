package dick;

/**
 * Parses raw user input into a structured command word and arguments.
 */
public class Parser {
    /**
     * Parses the given input into a {@link ParsedCommand}.
     *
     * @param input Full command string entered by the user.
     * @return Parsed command containing command word and argument string.
     */
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

    /**
     * Represents a parsed command consisting of a command word and the remaining arguments.
     */
    public static class ParsedCommand {
        /** The command word in lowercase (e.g., "todo", "deadline"). */
        public final String commandWord;
        /** The remaining part after the command word, trimmed. */
        public final String args;

        /**
         * Creates a parsed command.
         *
         * @param commandWord Command word.
         * @param args Remaining arguments.
         */
        public ParsedCommand(String commandWord, String args) {
            this.commandWord = commandWord;
            this.args = args;
        }
    }
}