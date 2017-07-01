/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nlp.platform;

import java.util.ArrayList;

/**
 *
 * @author elazab
 */
public class NlpPlatform {

    static ArrayList <String> Finalner1=new ArrayList<>();
    static ArrayList <String> n=new ArrayList<>();
     
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       NER ob =new NER();
       Finalner1.add("hello");
       //Finalner1.add("medical");
       n.add("hospital");
      // ob.initialize(Finalner1, 0, n);
    }
    
}
