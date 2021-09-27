package com.magicliang.code.LowestCommonAncestorOfABinaryTree;

public class TreeNode {
	private TreeNode left;
	private TreeNode right;
	private int val;

	public TreeNode(TreeNode left, TreeNode right, int val) {
		this.left = left;
		this.right = right;
		this.val = val;
	}

	public TreeNode getLeft() {
		return left;
	}

	public void setLeft(TreeNode left) {
		this.left = left;
	}

	public TreeNode getRight() {
		return right;
	}

	public void setRight(TreeNode right) {
		this.right = right;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
}
