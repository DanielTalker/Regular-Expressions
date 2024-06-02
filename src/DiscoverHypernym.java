import java.io.IOException;

/**
 * The DiscoverHypernym class is the entry point of the application for
 * discovering hypernyms in a corpus.
 * It accepts the corpus path and a lemma as command-line arguments and
 * analyzes the corpus to find hypernyms
 * related to the specified lemma.
 */
public class DiscoverHypernym {
    private static final int MIN_NUM_ARGUMENTS = 2;

    /**
     * The main method is the entry point of the application.
     *
     * @param args the command-line arguments
     * @throws IOException if an I/O error occurs during corpus analysis
     */
    public static void main(String[] args) throws IOException {

        // Must get corpus path and a lemma
        if (args.length < MIN_NUM_ARGUMENTS) {
            System.out.println("Not enough arguments");
            return;
        }

        String corpusPath = args[0];
        String lemma = args[1];

        // In case the lemma is multiple words
        if (args.length > MIN_NUM_ARGUMENTS) {
            for (int i = 2; i < args.length; i++) {
                lemma += " " + args[i];
            }
        }

        // Create a CorpusAnalyzer instance with the corpus path and lemma
        CorpusAnalyzer analyzer = new CorpusAnalyzer(corpusPath, lemma);
        // Analyze the corpus to discover hypernyms
        analyzer.analyzeCorpus();
    }
}
