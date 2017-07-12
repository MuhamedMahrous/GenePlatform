/*
Author:				Muhamed Mahrous
Date:				1/07/2017
Version:			1.0
Project ID:			I.E. Framework
CS Class:			N/A
Programming Language:		JAVA
OS/Hardware dependencies:	None

Problem Description:
The compound relation data Model.


Overall Design:     None.
System structure:    None.
Data representation: 1.first_list: holds the elements of the left pattern.
                     2.second_list: holds the elements of the right pattern.
Algorithms:	        None.


Program Assumptions and Restrictions :
None.

*/
package geneplatform;

import NLL.Compound_list;

public class Relation_Model extends BlockModel{
    public Compound_list first_list;
    public Compound_list second_list ;

    public Relation_Model(Compound_list first_list, Compound_list second_list) {
        this.first_list = first_list;
        this.second_list = second_list;
    }
}
