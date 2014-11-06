import java.util.List;

import org.LexGrid.LexBIG.DataModel.Core.CodingSchemeVersionOrTag;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.lexevs.tree.model.LexEvsTree;
import org.lexevs.tree.model.LexEvsTreeNode;
import org.lexevs.tree.service.TreeService;
import org.lexevs.tree.service.TreeServiceFactory;


public class GetTree {

    public static void main(String[] args) {
        LexBIGService lbsi = LexBIGServiceImpl.defaultInstance();
        TreeService service = TreeServiceFactory.getInstance().getTreeService(lbsi);
        LexEvsTree tree = null;
        CodingSchemeVersionOrTag csvt = new CodingSchemeVersionOrTag();
        csvt.setVersion("TestForMultiNamespace");
         tree = service.getTree("npo", csvt, "NPO_1607", "npo", "is_a");
            
            LexEvsTreeNode focusNode = tree.getCurrentFocus();
            focusNode.setNamespace("npo");
            List<LexEvsTreeNode> nodeList = service.getEvsTreeConverter().buildEvsTreePathFromRootTree(focusNode);
            assert(nodeList.size() > 0);
            for(LexEvsTreeNode tn: nodeList){
                System.out.println("code: " + tn.getCode());
                System.out.println("decription:" + tn.getEntityDescription());
                System.out.println("namespace: " + tn.getNamespace());

            }

    }

}