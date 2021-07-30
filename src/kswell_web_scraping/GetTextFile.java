/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kswell_web_scraping;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Prasanth
 */
public class GetTextFile {
      public static String CSVFILEPATH = "";
    public static String OUTPUTFOLDERPATH = "";
    
    GetTextFile(String CSV_path, String OutPut_path)
    {
        CSVFILEPATH = CSV_path;
        OUTPUTFOLDERPATH = OutPut_path;
         ReadCSVFile();
    }
      public static void ReadCSVFile()
     {
          CSVReader reader = null;
        try
        {
            //Get the CSVReader instance with specifying the delimiter to be used
            reader = new CSVReader(new FileReader(CSVFILEPATH), ',');
 
            String [] nextLine;
 
            //Read one line at a time
            while ((nextLine = reader.readNext()) != null) 
            {
                for(String token : nextLine)
                {
                    //Print all tokens
                    System.out.println(token);
                    if(token.contains("http"))
                    {
                        findLinks(token);
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     }
      
       private static void findLinks(String url) throws IOException, Exception 
    {
        
          Document doc = Jsoup.parse(new URL(url), 5000);

    Elements resultLinks = doc.select("a");
    System.out.println("number of links: " + resultLinks.size());
    for (Element link : resultLinks) {
        System.out.println();
        String href = link.attr("href");
       
        if(link.text().contains(".txt"))
        {
             System.out.println("Title: " + link.text());
             System.out.println("Url: " + href);
            downloadFile(new URL(href),OUTPUTFOLDERPATH+link.text().toString());
        }
    }

    }
       
        public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }
     
    
}
