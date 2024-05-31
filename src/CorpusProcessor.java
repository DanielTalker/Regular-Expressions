// 322624206 Daniel Talker

import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

/**
 * The CorpusProcessor class processes a corpus file for lemma occurrences
 * using specified patterns.
 */
public class CorpusProcessor {
    private TreeMap<String, Integer> resultMap;
    private HearstPatterns whichIsRelation;
    private HearstPatterns[] relations;

    /**
     * Instantiates a new Corpus processor with default patterns.
     */
    public CorpusProcessor() {
        this.resultMap = new TreeMap<>();
        this.whichIsRelation = new WhichIs();
        this.relations = new HearstPatterns[] {new SuchAs(), new SuchNpAs(),
                new Including(), new Especially()};
    }

    /**
     * Instantiates a new Corpus processor with custom patterns.
     *
     * @param whichIsRelation the pattern for "which is" relation
     * @param relations       an array of Hearst patterns
     */
    public CorpusProcessor(HearstPatterns whichIsRelation, HearstPatterns[] relations) {
        this.resultMap = new TreeMap<>();
        this.whichIsRelation = whichIsRelation;
        this.relations = relations;
    }

    /**
     * Gets the result map containing lemma occurrences and frequencies.
     *
     * @return the result map
     */
    public TreeMap<String, Integer> getResultMap() {
        return this.resultMap;
    }

    /**
     * Reads a file and processes it for the given lemma.
     *
     * @param fromFile the file to be read
     * @param lemma    the lemma to be searched for
     * @throws IOException if an I/O error occurs
     */
    public void readFile(File fromFile, String lemma) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fromFile)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the lemma
                if (line.contains(lemma)) {
                    // Process the line for the lemma
                    processLine(lemma, line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Processes a line of text for lemma occurrences using the specified patterns.
     *
     * @param lemma the lemma to be searched for
     * @param line  the line of text to be processed
     */
    private void processLine(String lemma, String line) {
        // Process each Hearst pattern
        for (HearstPatterns pattern : this.relations) {
            Pattern relationPattern = Pattern.compile(pattern.getStringRegex());

            // Extract match parts based on the pattern
            List<String> matchParts = extractMatchParts(line, relationPattern);
            processMatchParts(lemma, matchParts, false);
        }

        // Process the "which is" relation pattern
        Pattern whichPattern = Pattern.compile(this.whichIsRelation.getStringRegex());
        List<String> matchParts2 = extractMatchParts(line, whichPattern);
        processMatchParts(lemma, matchParts2, true);
    }

    /**
     * Extracts match parts from a line using the specified pattern.
     *
     * @param line    the line of text to extract match parts from
     * @param pattern the pattern to be matched
     * @return the list of match parts
     */
    private List<String> extractMatchParts(String line, Pattern pattern) {
        List<String> matchParts = new ArrayList<>();
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String matchPortion = matcher.group();
            matchParts.add(matchPortion);
        }
        return matchParts;
    }

    /**
     * Processes the match parts and counts lemma occurrences.
     *
     * @param lemma the lemma to be searched for
     * @param matchParts the list of match parts
     * @param isWhichIsRelation specifies if the match parts correspond
     * to the "which is" relation pattern
     */
    private void processMatchParts(String lemma, List<String> matchParts,
                                   boolean isWhichIsRelation) {
        for (String match : matchParts) {
            List<String> hyponyms = searchHyponyms(match);
            String hypernym = hyponyms.get(isWhichIsRelation ? 1 : 0);
            // If it is the "which is" relation pattern
            if (isWhichIsRelation) {
                String hyponym = hyponyms.get(0);
                // Check if the hyponym matches the lemma
                if (hyponym.equalsIgnoreCase(lemma)) {
                    // Increment the count for the corresponding hypernym
                    incrementHypernymCount(hypernym);
                }
                // For other relation patterns
            } else {
                for (int i = 1; i < hyponyms.size(); i++) {
                    // Check if any of the hyponyms match the lemma
                    if (hyponyms.get(i).equalsIgnoreCase(lemma)) {
                        // Increment the count for the corresponding hypernym
                        incrementHypernymCount(hypernym);
                    }
                }
            }
        }
    }

    /**
     * Increments the count for the specified hypernym in the result map.
     *
     * @param hypernym the hypernym to increment the count for
     */
    private void incrementHypernymCount(String hypernym) {
        resultMap.put(hypernym, resultMap.getOrDefault(hypernym, 0) + 1);
    }

    /**
     * Finds hyponyms within a given string.
     *
     * @param st the input string
     * @return the list of hyponyms
     */
    public List<String> searchHyponyms(String st) {
        // Define the pattern to match "<np>...</np>" tags and capture the content in between
        Pattern pattern = Pattern.compile("<np>(.*?)</np>");
        // Create a matcher to apply the pattern on the input string
        Matcher matcher = pattern.matcher(st);

        // Create a list to store the extracted hyponyms
        List<String> hyponymsInString = new ArrayList<>();

        // Iterate through the matches found by the matcher
        while (matcher.find()) {
            // Extract the hyponym content and trim any leading or trailing spaces
            String hyponym = matcher.group(1).trim();
            // Add the hyponym to the list
            hyponymsInString.add(hyponym);
        }
        return hyponymsInString;
    }
}
