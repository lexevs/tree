package org.lexevs.tree.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Ignore;
import org.junit.Test;
import org.lexevs.tree.service.PathToRootTreeServiceImpl;
import org.lexevs.tree.test.LexEvsTreeTestBase;

@Ignore
public class LexEvsTreeTest extends LexEvsTreeTestBase {
	
	@Resource
	private PathToRootTreeServiceImpl pathToRootTreeServiceImpl;
	
	private LexEvsTree tree;
	
	public void buildTree(){
		
		tree =
			pathToRootTreeServiceImpl.
				getTree(
						"Automobiles", 
						null, 
						"Chevy");
	}
	
	@Test
	public void testGetTreeCurrentFocus(){
		this.buildTree();
		
		assertNotNull(tree.getCurrentFocus());
		assertTrue(tree.getCurrentFocus().getCode().equals("Chevy"));
	}
	
	@Test
	public void testFindNodeNumber(){
		this.buildTree();
		
		assertEquals(8,tree.getCodeMap().size());
	}
}
