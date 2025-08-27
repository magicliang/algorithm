package algorithm.LowestCommonAncestorOfABinaryTree;

public class TreeBuilder {

	public static TreeRet build() {
		TreeNode node7 = new TreeNode(null, null, 7);
		TreeNode node4 = new TreeNode(null, null, 4);

		TreeNode node6 = new TreeNode(null, null, 6);
		TreeNode node2 = new TreeNode(node7, node4, 2);
		TreeNode node0 = new TreeNode(null, null, 0);
		TreeNode node8 = new TreeNode(null, null, 8);

		TreeNode node5 = new TreeNode(node6, node2, 5);
		TreeNode node1 = new TreeNode(node0, node8, 1);

		TreeNode root = new TreeNode(node5, node1, 3);

		return new TreeRet(root, node5, node1);
	}

	public static class TreeRet {
		private TreeNode root;
		private TreeNode p;
		private TreeNode q;

		TreeRet(TreeNode root, TreeNode p, TreeNode q) {
			this.root = root;
			this.p = p;
			this.q = q;
		}

		public TreeNode getRoot() {
			return root;
		}

		public TreeNode getP() {
			return p;
		}

		public TreeNode getQ() {
			return q;
		}
	}
}
