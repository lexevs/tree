package org.lexevs.tree.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.lexevs.tree.model.LexEvsTree;
import org.lexevs.tree.test.LexEvsTreeTestBase;

public class MultipleNamspaceErrorLEXEVS_598_Test extends LexEvsTreeTestBase {


	@Test
	public void testMultipleNamespace() {
	
				LexEvsTree tree = 	pathToRootTreeServiceImpl.getTree(
						"Automobiles", 
						null, 
						"DifferentNamespaceConcept", "TestForSameCodeNamespace");
				assertNotNull(tree);
	}

}
