package algorithm.selectedtopics.leetcode.LowestCommonAncestorOfABinaryTree;

public class Solution {

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        // 根节点为null 直接返回
        if (root == null) {
            return null;
        }

        // p 或者 q 如果是根节点，则可以直接返回
        if (root.getVal() == p.getVal() || root.getVal() == q.getVal()) {
            return root;
        }

        // 比较好情况是 p 和 q 在root的子树中，且分列root的异侧（即分别在左、右子树中）
        // 尝试在左子树寻找p和q的最近公共祖先
		TreeNode leftCommonAncestor = lowestCommonAncestor(root.getLeft(), p, q);

        // 尝试在右子树寻找q和q的最近公共祖先
		TreeNode rightCommonAncestor = lowestCommonAncestor(root.getRight(), p, q);

        // 如果 p 和 q 分别位于他们最近公共结点的两侧，则根节点为最近公共祖先
        if (null != leftCommonAncestor && null != rightCommonAncestor) {
            return root;
        }

        // 如果左子树有值，则最近公共祖先在左子树，否则，在右子树
        // p或q中有一个为空，返回非空的那个
        if (leftCommonAncestor == null) {
            return rightCommonAncestor;
        }

        // 如果左子树有值，则最近公共祖先在左子树，否则，在右子树
        //p或q中有一个为空，返回非空的那个
        if (rightCommonAncestor == null) {
            return leftCommonAncestor;
        }

        return null;
    }
}
