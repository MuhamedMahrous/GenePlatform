package geneplatform;

import NLL.Compound_list;

/**
 * Created by muhammedmahrous on 01/07/17.
 */
public class Relation_Model extends BlockModel{
    public Compound_list first_list;
    public Compound_list second_list ;

    public Relation_Model(Compound_list first_list, Compound_list second_list) {
        this.first_list = first_list;
        this.second_list = second_list;
    }
}
