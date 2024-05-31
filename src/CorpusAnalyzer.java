// 322624206 Daniel Talker

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * The CorpusAnalyzer class represents a corpus analyzer that analyzes a given corpus
 * to identify the frequency of occurrences of a specific lemma within the corpus.
 * It utilizes a CorpusReader to read the corpus files and perform the analysis.
 */
public class CorpusAnalyzer {
    private CorpusReader reader;
    private String corpusPath;
    private String lemma;

    /**
     * Instantiates a new Corpus analyzer with the provided corpus path and lemma.
     *
     * @param corpusPath the path to the corpus directory
     * @param lemma the lemma to analyze
     */
    public CorpusAnalyzer(String corpusPath, String lemma) {
        this.corpusPath = corpusPath;
        this.lemma = lemma;
        this.reader = new CorpusReader(this.corpusPath, this.lemma);
    }

    /**
     * Analyzes the corpus to identify the frequency of occurrences of the lemma.
     *
     * @throws IOException if an I/O error occurs during corpus reading
     */
    public void analyzeCorpus() throws IOException {
        TreeMap<String, Integer> map = this.reader.readFolder();
        printResults(map);
    }

    /**
     * Prints the results of the lemma analysis in descending order of occurrence frequency.
     *
     * @param map the TreeMap containing the lemma occurrences and their frequencies
     */
    private void printResults(TreeMap<String, Integer> map) {
        if (map.isEmpty()) {
            System.out.println("The lemma doesn't appear in the corpus.");
            return;
        }

        // Sort the map entries by value in descending order and print the results
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println(entry.getKey() + ": (" + entry.getValue() + ")"));
    }
}
