/**
 * The class SuchNpAs represents the "such NP as" pattern used in Hearst
 * patterns extraction.
 * It implements the HearstPatterns interface, providing the regular expression
 * representing the pattern.
 */
public class SuchNpAs implements HearstPatterns {
    private static final String NP = "<np>([^<]+)</np>";
    @Override
    public String getStringRegex() {
        return "such\\s" + NP + "\\sas\\s" + NP + "(\\s?(,\\s?\\s)?" + NP + ")*"
                + "(\\s?,?\\s?(and|or) *" + NP + ")?";
    }

    @Override
    public String getRelation() {
        return null;
    }
}
