package org.crawl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class SunilWebCrawler {

    private static final int maxAllowedDepth = 3; // we have to break the execution at certain depth else it might go  crazy
    private HashSet<String> linkSet;
    private HashSet<String> ignoredLinkSet;
    private HashSet<String> imageLinkSet;
    private static String mainURL = null;

    public SunilWebCrawler() {
        linkSet = new HashSet<String>();
        ignoredLinkSet = new HashSet<String>();
        imageLinkSet = new HashSet<String>();
    }

    //Find all URLs that start with given url and add them to the linkSet
    public void getPageLinks(String URL, int startDepth) {
        if (mainURL == null) {
            mainURL = URL;
        }
        //  System.out.println(" called in recursion");

        //This check will take care to not get into cyclic references , e.g. Page A has links for B and B has for C  and C has for D and then D has links for A..
        if ((!linkSet.contains(URL) && (startDepth < maxAllowedDepth))) {
            System.out.println(">> Depth: " + startDepth + " [" + URL + "]");
            try {
                linkSet.add(URL);

                Document document = Jsoup.connect(URL).get();
                Elements linksOnPage = document.select("a[href]");

                startDepth++;
                for (Element element : linksOnPage) {
                    // get relative path
                    String link = element.attr("href");

                    //if (link.startsWith("https") || link.startsWith("http") || link.startsWith("www")) {
                    if (link.contains(mainURL)) {
                        System.out.println("add link: " + link);
                        //get full path
                        getPageLinks(element.attr("abs:href"), startDepth);

                    } else if (link.startsWith("/") || link.startsWith("#")) {
                        link = URL + link.substring(1, link.length());
                        System.out.println("add relative path link: " + link);
                        getPageLinks(element.attr("abs:href"), startDepth); //get full path

                    } else {
                        //TODO: if external links needs to be looked as well,  then we should create ThreadPool
                        // and for each external link assign it to a worker Thread using FixedThreadPool
                        if (!"".equalsIgnoreCase(link)) {
                            ignoredLinkSet.add(link);
                            System.out.println("ignore link as it is external: " + link);
                        }
                    }
                    // }
                    // else{
                    //     System.out.println("unused link is" + link);
                    //  }
                }

            } catch (Exception e) {
                System.err.println("Error occured whule iterating links '" + URL + "': " + e.getMessage());
            }
        }


    }

    //Connect to each link of same url saved in the linkSet and find all the images in the page
    public void getAllImages() {
        linkSet.forEach(linkSetURL -> {
            Document document;
            try {
                document = Jsoup.connect(linkSetURL).get();

                Elements articleLinks = document.getElementsByTag("img");
                for (Element element : articleLinks) {
                    String link = element.attr("src");
                    imageLinkSet.add(link);
                }
            } catch (Exception e) {
                System.err.println("Error occured whule fetching images for URL '" + linkSetURL + "': " + e.getMessage());
            }
        });

    }

    public HashSet<String> getLinkSet() {
        return linkSet;
    }

    public HashSet<String> getIgnoredLinkSet() {
        return ignoredLinkSet;
    }

    public HashSet<String> getImageLinkSet() {
        return imageLinkSet;
    }


    public static void main(String[] args) {
        SunilWebCrawler sunilWebCrawler = new SunilWebCrawler();
        sunilWebCrawler.getPageLinks("https://wiprodigital.com", 0);
        sunilWebCrawler.getAllImages();

        //System.out.println("Final Consolidated Data is as below:");

        System.out.println
                (" \n\n\n------------------------------------- \n" +
                        "\tFinal Consolidated Data is as below:" +
                        "\n\t\n" +
                        " ------------------------------------- \n");
        System.out.println("Total Wipro Digital links count: " + sunilWebCrawler.getLinkSet().size());
        System.out.println("All Wipro Digital links: " + sunilWebCrawler.getLinkSet());
        System.out.println("");
        System.out.println("Total Non-Wipro Digital links count:  " + sunilWebCrawler.getIgnoredLinkSet().size());
        System.out.println("All Non-Wipro Digital links: " + sunilWebCrawler.getIgnoredLinkSet());
        System.out.println("");
        System.out.println("Total Wipro Digital image links count: " + sunilWebCrawler.getImageLinkSet().size());
        System.out.println("All Wipro Digital image links: " + sunilWebCrawler.getImageLinkSet());
    }
}
