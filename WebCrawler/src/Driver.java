import java.io.File;

public class Driver {
	/*
	 * main function takes the command line inputs and calls constructor of
	 * ArgumentParser class that parses the list of command line arguments into
	 * flag/value pairs,which is passed to traverseDirectory function for file
	 * traversal.It provides the output filename to printMap function to be
	 * created for storing the map data. In addition main function takes the
	 * query file as command line argument and carries out partial search
	 * operation on each query line and outputs the result to a text file. For
	 * multi-threading, main method takes a value which is treated as required
	 * number of threads for the project. main() takes the seed URL as argument 
	 * and calls the crawl() of WebCrawler class to start the web crawling process.
	 */
	public static void main(String[] args) {
		ArgumentParser ap = new ArgumentParser(args);
		if ((ap.hasFlag("-t")) && (ap.hasValue("-t"))) {
			int numThreads = 1;

			try {
				numThreads = Integer.parseInt(ap.getValue("-t"));
			} catch (NumberFormatException e) {
				System.out.println("Enter a proper value for -t ");
			}
			ThreadSafeInvertedIndex tSi = new ThreadSafeInvertedIndex();

			if (numThreads <= 0) {
				numThreads = 1;
			}
			if (numThreads > 0) {
				WorkQueue minions = new WorkQueue(numThreads);
				if (ap.hasFlag("-d")) {
					MultiThreadedDirectoryTraverser mDt = new MultiThreadedDirectoryTraverser(
							tSi, minions);
					if ((ap.hasFlag("-d")) && (ap.hasValue("-d"))) {
						String dir = ap.getValue("-d");
						File file = new File(dir);
						mDt.traverseDirectory(file);
						mDt.finish();
					} else {
						return;
					}
				}
				if (ap.hasFlag("-u"))
					if (ap.getValue("-u") != null) {
						String url = ap.getValue("-u");
						WebCrawler wc = new WebCrawler(url, minions, tSi);
						wc.crawl(url);
						wc.finish();
					}
				if (ap.hasFlag("-i"))
					if (ap.getValue("-i") != null) {
						String txtFile = ap.getValue("-i");
						tSi.printMap(txtFile);
					} else
						tSi.printMap("invertedindex.txt");
				if (ap.hasFlag("-q")) {
					MultiThreadedIndexSearcher mIs = new MultiThreadedIndexSearcher(
							tSi, minions);
					String inputQuery = ap.getValue("-q");
					mIs.parseQueries(inputQuery, tSi);
					mIs.finish();
					if (ap.hasFlag("-r"))
						if (ap.getValue("-r") != null) {
							String txtDoc = ap.getValue("-r");
							mIs.printSearchResults(txtDoc);
						} else {
							String txtDoc = "searchresults.txt";
							mIs.printSearchResults(txtDoc);
						}
				} else {
					if (!ap.hasFlag("-i"))
						tSi.printMap("invertedindex.txt");
				}
			}
		} else {
			InvertedIndex invertedMapObject = new InvertedIndex();
			DirectoryTraverser dt = new DirectoryTraverser(invertedMapObject);
			if ((ap.hasFlag("-d")) && (ap.hasValue("-d"))) {
				String dir = ap.getValue("-d");
				File file = new File(dir);
				dt.traverseDirectory(file);
			} else {
				return;
			}
			if (ap.hasFlag("-i"))
				if (ap.getValue("-i") != null) {
					String txtFile = ap.getValue("-i");
					invertedMapObject.printMap(txtFile);
				} else
					invertedMapObject.printMap("invertedindex.txt");
			if (ap.hasFlag("-q")) {
				IndexSearcher is = new IndexSearcher(invertedMapObject);
				String inputQuery = ap.getValue("-q");
				is.parseQueries(inputQuery);
				if (ap.hasFlag("-r"))
					if (ap.getValue("-r") != null) {
						String txtDoc = ap.getValue("-r");
						is.printSearchResults(txtDoc);
					} else {
						String txtDoc = "searchresults.txt";
						is.printSearchResults(txtDoc);
					}
			} else {
				if (!ap.hasFlag("-i"))
					invertedMapObject.printMap("invertedindex.txt");
			}
		}
	}
}
