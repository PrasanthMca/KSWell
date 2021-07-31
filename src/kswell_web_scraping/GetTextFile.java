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
import javax.swing.JOptionPane;
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
     public static int NumberOfFiles = 0;
    
    GetTextFile(String CSV_path, String OutPut_path)
    {
        CSVFILEPATH = CSV_path;
        OUTPUTFOLDERPATH = OutPut_path;
        NumberOfFiles = 0;
         ReadCSVFile();
       
            JOptionPane.showMessageDialog(null, "Completed "+NumberOfFiles+ " Files");
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
                        NumberOfFiles++;
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
        try{
          Document doc = Jsoup.parse(new URL(url), 10000);

    Elements resultLinks = doc.select("a");
    System.out.println("number of links: " + resultLinks.size());
    for (Element link : resultLinks) {
        
        String href = link.attr("href");
       
        if(link.text().contains(".txt"))
        {
             System.out.println("Title: " + link.text());
             System.out.println("Url: " + href);
            downloadFile(new URL(href),OUTPUTFOLDERPATH+link.text().toString());
        }
    }
    }
        catch(Exception Exception)
        {
            System.out.println("Exception on URL name : "+url);
        }
    }
       
        public static void downloadFile(URL url, String fileName) throws Exception {
        try (InputStream in = url.openStream()) {
            Files.copy(in, Paths.get(fileName));
        }
    }
     
    
}
