/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eslam
 */
public class file {

      String[] get_file (String path2){
   String[] linesArray = null;
           try {
            BufferedReader in = new BufferedReader(new FileReader(path2));
            String str = null;
            ArrayList<String> lines = new ArrayList<String>();
            while ((str = in.readLine()) != null) {
                lines.add(str);
            }
            linesArray = lines.toArray(new String[lines.size()]);

           
        }catch(IOException e){
            System.err.println(e);
        }
         
       return linesArray;
    }
}
