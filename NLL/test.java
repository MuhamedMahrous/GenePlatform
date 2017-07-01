/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLL;

import java.util.LinkedList;
import nlp.platform.NER;
import nlp.platform.Relations;

/**
 *
 * @author Eslam
 */
public class test {

     
    public static void main(String[] args) {
/*
        Compound_list input1 = new Compound_list();
        //input1.add_String("yes");
        input1.add_file("1.txt");
        //input1.add_Relation("0");
        //input1.add_String("hello");
       // input1.add_file("1.txt");
       // input1.add_Relation("2");
       // input1.add_String("hello");
        Compound_list ner1 = new Compound_list();
        ner1.add_String("name");
        //ner1.add_file("1.txt");
       // System.out.println(ner1.get_index_type(2));
        //System.out.println(ner1.get_index_info(2));
        //String [] list =ner1.get_index_filelist(2);
       // System.out.println(list[1]);
        NER n1=new NER();
        n1.initialize(input1, "2", ner1);
        //n.initialize(input, "2", ner);
        */
        
        Compound_list input = new Compound_list();
        //input.add_file("1.txt");
        input.add_String("hello");
        //input.add_Generic("NNP");
        input.add_Relation("0");
        input.add_String("hello");
        //input.add_Generic("NNP");
        //input.add_file("1.txt");
        Compound_list ner = new Compound_list();
        ner.add_file("1.txt");
        //ner.add_String("hello");
        //ner.add_Generic("NN");
        ner.add_Relation("3");
        ner.add_file("1.txt");
        //ner.add_String("hello");
        //ner.add_Generic("NN");
        
         Relations n=new Relations();
         n.initialize(input, ner);
                
    }

}
