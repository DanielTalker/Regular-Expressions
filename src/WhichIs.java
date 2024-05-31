// 322624206 Daniel Talker

/**
 * The class WhichIs represents the "which is NP" pattern used in Hearst
 * patterns extraction.
 * It implements the HearstPatterns interface, providing the regular expression
 * representing the pattern.
 */
public class WhichIs implements HearstPatterns {
    private static final String NP = "<np>([^<]+)</np>";

    @Override
    public String getStringRegex() {
          return NP + "\\s(,?\\s?)?" + this.getRelation() + "\\s"
                + "((an\\sexample|a\\skind|a\\sclass)\\sof\\s)?" + NP;
    }

    @Override
    public String getRelation() {
        return "which\\sis";
    }

}
