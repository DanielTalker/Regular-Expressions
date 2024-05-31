// 322624206 Daniel Talker

/**
 * The class SuchAs represents the "such as" pattern used in Hearst patterns
 * extraction.
 * It implements the HearstPatterns interface.
 */
public class SuchAs implements HearstPatterns {
    private static final String NP = "<np>([^<]+)</np>";

    @Override
    public String getStringRegex() {
        return NP + "\\s?(,?\\s?)" + this.getRelation() + "\\s" + NP + "(\\s?(,\\s?)" + NP + ")*"
                + "(\\s?,?\\s?(and|or)\\s" + NP + ")?";
    }

    @Override
    public String getRelation() {
        return "such\\sas";
    }
}
