/*
Author:				Muhamed Mahrous
Date:				15/10/2016
Version:			1.0
Project ID:			I.E. Framework
CS Class:			N/A
Programming Language:		JAVA
OS/Hardware dependencies:	None

Problem Description:
The NER tagging data Model.


Overall Design:     None.
System structure:    None.
Data representation:
                        1.first_list : holds the first pattern.
                        2.unique_relation: holds the relation.
                        3.ner: holds the right node.
Algorithms:	        None.


Program Assumptions and Restrictions :
None.

*/

package geneplatform;

import NLL.Compound_list;


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