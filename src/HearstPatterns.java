// 322624206 Daniel Talker

/**
 * The interface Hearst patterns represents a pattern used in Hearst patterns
 * extraction.
 * It defines the contract for classes implementing specific patterns.
 */
public interface HearstPatterns {

    /**
     * Gets the string regular expression representing the Hearst pattern.
     *
     * @return the string regular expression representing the Hearst pattern.
     */
    String getStringRegex();

    /**
     * Gets the relation.
     *
     * @return The relation as a String.
     */
    String getRelation();
}
