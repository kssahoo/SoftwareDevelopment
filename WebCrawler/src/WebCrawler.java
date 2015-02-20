import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebCrawler {
	private ThreadSafeInvertedIndex index;
	private HashSet<String> uniqueLinks = new HashSet<String>();
	private static final Logger logger = LogManager.getLogger();
	private WorkQueue minions;
	private int pending;
	private String baseURL = null;
	private URL tempURL = null;

	public WebCrawler(String seedURL, WorkQueue minions,
			ThreadSafeInvertedIndex index) {
		baseURL = seedURL;
		this.index = index;
		this.minions = minions;
		pending = 0;
	}

	/*
	 * addLink method adds unique links to the HashSet by converting them to
	 * absolute links, when needed(i.e for relative links). It creates a new
	 * Worker thread for individual link.
	 */
	public synchronized void addLink(String link) {
		try {

			if (link.startsWith("http") || link.startsWith("HTTP")) {
				tempURL = new URL(link);
			} else {
				String relativeURL = link;
				URL base = new URL(baseURL);
				tempURL = new URL(base, relativeURL);
			}
			String absoluteURL = tempURL.getProtocol() + "://"
					+ tempURL.getHost() + tempURL.getFile();
			if (!uniqueLinks.contains(absoluteURL) && uniqueLinks.size() < 50) {
				uniqueLinks.add(absoluteURL);
				minions.execute(new Worker(absoluteURL));
			}
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
	}

	/*
	 * crawl() gets the seed URL from the Driver as argument and passes it to
	 * addLink() to start the process of crawling through web-pages.
	 */
	public void crawl(String url) {
		addLink(url);
	}

	/*
	 * One Worker runnable is created for each link that needs to be parsed. For
	 * the individual link the the web content is crawled to parse links, which
	 * are then added to an array list and the addLink() is called for each of
	 * them. Then after the particular web page is cleaned to remove HTML tags
	 * the plain text is parsed into words, which are added into the localIndex
	 * which is combined with the shared InvertedIndex class in the end of run
	 * method.
	 */
	private class Worker implements Runnable {
		private String link;

		public Worker(String link) {
			this.link = link;
			incrementPending();
		}

		@Override
		public void run() {
			try {
				HTMLFetcher fetcher = new HTMLFetcher();
				String fetcherOutput = fetcher.fetchHTML(link);
				ArrayList<String> listOfLinks = HTMLLinkParser
						.listLinks(fetcherOutput);
				for (String singleLink : listOfLinks) {
					addLink(singleLink);
				}

				InvertedIndex localIndex = new InvertedIndex();
				HTMLCleaner cleaner = new HTMLCleaner();
				String cleanerWords;
				cleanerWords = cleaner.cleanHTML(fetcherOutput);
				ArrayList<String> words = cleaner.parseWords(cleanerWords);
				int position = 1;
				for (String word : words) {
					localIndex.addToMap(word, link, position);
					position++;
				}
				index.addAll(localIndex);
				decrementPending();
			} catch (MalformedURLException e1) {
				System.out.println(e1.getMessage());
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public synchronized void finish() {
		try {
			while (pending > 0) {
				logger.debug("Waiting until finished");
				this.wait();
			}
		} catch (InterruptedException e) {
			logger.debug("Finish interrupted");
		}
	}

	public synchronized void shutdown() {
		finish();
		minions.shutdown();
	}

	private synchronized void incrementPending() {
		pending++;
	}

	private synchronized void decrementPending() {
		pending--;
		if (pending <= 0) {
			this.notifyAll();
		}
	}
}
