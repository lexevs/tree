package org.lexevs.tree.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.lexevs.tree.model.LexEvsTree;
import org.lexevs.tree.test.LexEvsTreeTestBase;
import org.lexevs.tree.utility.PrintUtility;

public class MultipleNamspaceErrorLEXEVS_598_Test extends LexEvsTreeTestBase {


	@Test
	public void testMultipleNamespace() {
	
				LexEvsTree tree = 	pathToRootTreeServiceImpl.getTree(
						"Automobiles", 
						null, 
						"DifferentNamespaceConcept", "TestForSameCodeNamespace");
				assertNotNull(tree);
				assertNotNull(tree.getCurrentFocus().getNamespace());
				try{
					PrintUtility.print(tree.getCurrentFocus());
				}
				catch(NullPointerException e){
					fail("Null value in tree node");
				}
	}

}
