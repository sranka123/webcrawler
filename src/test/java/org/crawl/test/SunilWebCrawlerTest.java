package org.crawl.test;

import org.crawl.SunilWebCrawler;
import org.junit.Test;

public class SunilWebCrawlerTest {
    private static String URL = "https://wiprodigital.com";

    @Test
    public void myTest (){
        SunilWebCrawler sunilWebCrawler = new SunilWebCrawler();
        sunilWebCrawler.getPageLinks(URL, 0);
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
