/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NLL;

/**
 *
 * @author Eslam
 */
public class NLL {

    private Compound_list list1;
    private Compound_list list2;
    String relation;

    void NLL() {
        list1 = new Compound_list();
        list2 = new Compound_list();

    }

    public Compound_list get_first_list() {

        return list1;
    }

    public Compound_list get_second_list() {

        return list2;

    }

    public String get_unique_relation() {
        return relation;
        
    }
    
    public void set_unique_relation(String rel){
    
    relation = rel;
    
    }
    
  

}
