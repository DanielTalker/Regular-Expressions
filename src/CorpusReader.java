import java.io.IOException;
import java.io.File;
import java.util.TreeMap;

/**
 * The CorpusReader class represents a reader that reads and processes a corpus folder
 * to analyze the frequency of a specific lemma within the corpus files.
 */
public class CorpusReader {
    private File folder;
    private String lemma;

    /**
     * Instantiates a new Corpus reader with the provided folder path and lemma.
     *
     * @param folderPath the path to the corpus folder
     * @param lemma the lemma to analyze
     */
    public CorpusReader(String folderPath, String lemma) {
        this.folder = new File(folderPath);
        this.lemma = lemma;
    }

    /**
     * Reads the corpus folder and processes each file to identify the frequency of the lemma.
     *
     * @return the TreeMap containing the lemma occurrences and their frequencies
     * @throws IOException if an I/O error occurs during corpus reading
     */
    public TreeMap<String, Integer> readFolder() throws IOException {
        File[] folderFiles = this.folder.listFiles();
        HearstPatterns whichIsRelation = new WhichIs();
        HearstPatterns[] relations = new HearstPatterns[] {new SuchAs(), new SuchNpAs(),
                new Including(), new Especially()};
        CorpusProcessor processor = new CorpusProcessor(whichIsRelation, relations);

        // Read every file in the folder and Process each file with the CorpusProcessor
        for (File file : folderFiles) {
            processor.readFile(file, this.lemma);
        }
        // Get the result map from the CorpusProcessor
        TreeMap<String, Integer> resultMap = processor.getResultMap();
        // Return the resulting map containing lemma occurrences and their frequencies
        return resultMap;

    }
}

