package geneplatform;

import NLL.Compound_list;

/**
 * Created by muhammedmahrous on 01/07/17.
 */
public class NER_Model extends BlockModel {
    public Compound_list first_list;
    public String unique_relation = "";
    public Compound_list ner;

    public NER_Model(Compound_list first_list, String unique_relation, Compound_list ner) {
        this.first_list = first_list;
        this.unique_relation = unique_relation;
        this.ner = ner;
    }
}