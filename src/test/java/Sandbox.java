import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lexevs.tree.evstree.EvsTreeConverterFactory;
import org.lexevs.tree.json.JsonConverterFactory;
import org.lexevs.tree.model.LexEvsTree;
import org.lexevs.tree.model.LexEvsTreeNode;
import org.lexevs.tree.model.LexEvsTreeNode.ExpandableStatus;
import org.lexevs.tree.service.PathToRootTreeServiceImpl;
import org.lexevs.tree.service.TreeService;

public class Sandbox {

	private Map<String, LexEvsTreeNode> fromRootNodes = new HashMap<String, LexEvsTreeNode>();
	private Map<String, LexEvsTreeNode> toRootNodes = new HashMap<String, LexEvsTreeNode>();

	public static void main(String[] args) throws Exception {
		TreeService service = // TreeServiceFactory.getInstance().getTreeService(LexBIGServiceImpl.defaultInstance());
		new PathToRootTreeServiceImpl().getSpringManagedBean();

		long start = System.currentTimeMillis();
		// LexEvsTree tree = service.getTree("MedDRA", null, "10040901");
		// CodingSchemeVersionOrTag csvt= Constructors
		// .createCodingSchemeVersionOrTagFromVersion("UNASSIGNED");
		// LexEvsTree tree = service.getTree("chebi_ontology", csvt,
		// "CHEBI:100246");
		
		
		//CodingSchemeVersionOrTag csvt = Constructors
		//		.createCodingSchemeVersionOrTagFromVersion("January2012");
		// LexEvsTree tree = service.getTree("ChEBI", csvt, "CHEBI:100246");
		//LexEvsTree tree = service.getTree("ChEBI", csvt, "CHEBI:33582");

		
		CodingSchemeVersionOrTag csvt = Constructors
				.createCodingSchemeVersionOrTagFromVersion("11.09d");
		
		LexEvsTree tree = service.getTree("NCI_Thesaurus", csvt, "C37927");
		System.out.println(System.currentTimeMillis() - start);

		System.out.println(tree.getCurrentFocus().getExpandableStatus());

		// Sandbox sandbox = new Sandbox();
		List<LexEvsTreeNode> listEvsTreeNode = EvsTreeConverterFactory
				.getEvsTreeConverter().buildEvsTreePathFromRootTree(
						tree.getCurrentFocus());
		System.out.println(listEvsTreeNode);
		System.out.println("Print Children");
		printMyTree(listEvsTreeNode, 0);
		System.out.println(JsonConverterFactory.getJsonConverter()
				.buildJsonPathFromRootTree(tree.getCurrentFocus()));

		// sandbox.walkTreeFromRoot(root, 0);
		// System.out.println("==========================");

		// System.out.println(JsonConverterFactory.getJsonConverter().buildChildrenNodes(tree.findNodeInTree("C4872")));

		// sandbox.walkTreeFromFocus(tree.getCurrentFocus(), 0);
		/*
		 * long start2 = System.currentTimeMillis(); tree =
		 * service.getTree("NCI Thesaurus", null, "C4872");
		 * System.out.println(System.currentTimeMillis() - start2);
		 */

		// walkTree(TreeUtility.getRoot(tree.getCurrentFocus()), 0);

		// printParents(tree.getCurrentFocus(), 0);

		// printChildren(tree.getCurrentFocus(), 0);

		// System.out.println(sandbox.fromRootNodes);
		// System.out.println(sandbox.toRootNodes);

	}

	private static void jsonWalk(JSONArray array, int depth) throws Exception {
		for (int i = 0; i < array.length(); i++) {
			JSONObject obj = (JSONObject) array.get(i);
			System.out.println(buildPrefix(depth) + obj.get("ontology_node_id")
					+ " " + obj.get("ontology_node_name") + " "
					+ obj.get("ontology_node_child_count"));
			if (obj.has("children_nodes")) {
				jsonWalk(obj.getJSONArray("children_nodes"), depth + 1);
			}
		}
	}

	// C4503
	private void walkTreeFromRoot(LexEvsTreeNode root, int depth) {
		printNode(root, depth);
		if (fromRootNodes.containsKey(root.getCode())) {
			LexEvsTreeNode node = fromRootNodes.get(root.getCode());
			if (node.hashCode() != root.hashCode()) {
				throw new RuntimeException(root.getCode() + " already added");
			}
		}
		fromRootNodes.put(root.getCode(), root);
		if (root.getPathToRootChildren() == null) {
			return;
		}
		for (LexEvsTreeNode node : root.getPathToRootChildren()) {
			walkTreeFromRoot(node, depth + 1);
		}
	}

	private void walkTreeFromFocus(LexEvsTreeNode focus, int depth) {
		printNode(focus, depth);
		if (toRootNodes.containsKey(focus.getCode())) {
			LexEvsTreeNode node = toRootNodes.get(focus.getCode());
			if (node.hashCode() != focus.hashCode()) {
				throw new RuntimeException(focus.getCode() + " already added");
			}
		}
		toRootNodes.put(focus.getCode(), focus);
		if (focus.getPathToRootParents() == null) {
			return;
		}
		for (LexEvsTreeNode node : focus.getPathToRootParents()) {
			walkTreeFromFocus(node, depth + 1);
		}
	}

	private static void printNode(LexEvsTreeNode node, int depth) {
		System.out.println(buildPrefix(depth) + "Code: " + node.getCode()
				+ ", Description: " + node.getEntityDescription() + " Hash: "
				+ node.hashCode());
	}

	private static void printMyTree(List<LexEvsTreeNode> nodes, int depth) {
		for (LexEvsTreeNode node : nodes) {
		   printNode(node, depth);
		   List<LexEvsTreeNode> list_children = node.getPathToRootChildren();
		   if (list_children != null) {
				printMyTree(list_children, depth + 1);
		   }
		}
	}	

	private static String buildPrefix(int depth) {
		String prefix = "";
		for (int i = 0; i < depth; i++) {
			prefix = prefix + " -> ";
		}
		return prefix;
	}
	
	private static void printTree(LexEvsTreeNode node, int depth) {
		printNode(node, depth);
		List<LexEvsTreeNode> list_children = node.getPathToRootChildren();
		if (list_children != null) {
			for (LexEvsTreeNode child : list_children) {

				
				if (child.getExpandableStatus().equals(
						ExpandableStatus.IS_EXPANDABLE)) {
					printTree(child, depth + 1);
				}
			}
		}
	}

	private static void printChildren(LexEvsTreeNode node, int depth) {
		Iterator<LexEvsTreeNode> itr = node.getChildIterator();
		while (itr.hasNext()) {
			LexEvsTreeNode child = itr.next();
			printNode(child, depth);
			if (child.getExpandableStatus().equals(
					ExpandableStatus.IS_EXPANDABLE)) {
				printChildren(child, depth + 1);
			}
		}
	}

	private static void printParents(LexEvsTreeNode node, int depth) {
		if (node.getPathToRootParents() == null) {
			return;
		}
		;
		for (LexEvsTreeNode parent : node.getPathToRootParents()) {
			printNode(parent, depth);
			printParents(parent, depth + 1);
		}
	}


}
