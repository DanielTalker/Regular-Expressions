// 322624206 Daniel Talker

/**
 * The class Including represents the "including NP" pattern used in Hearst
 * patterns extraction.
 * It implements the HearstPatterns interface, providing the regular expression
 * representing the pattern.
 */
public class Including implements HearstPatterns {
    private static final String NP = "<np>([^<]+)</np>";

    @Override
    public String getStringRegex() {
        return "<np>([^<]+)</np>\\s(,?\\s?)?" + this.getRelation() + "\\s<np>([^<]+)</np>(\\s?(,\\s?\\s)?<np>"
                + "([^<]+)</np>)*(\\s?,?\\s?(and|or) *<np>([^<]+)</np>)?";
    }
    @Override
    public String getRelation() {
        return "including";
    }
}
