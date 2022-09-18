package ecs;

import java.util.List;
import java.util.ArrayList;

public class BinaryTree<T> {
	private TreeNode<T> root = new TreeNode<>(null, 0, null);
	private int lastKey;
	private T lastSearch;
	private List<TreeNode<T>> childs = new ArrayList<>();

	public void put(int key, T value) {
		recursivePut(root, key, value);
	}

	private void recursivePut(TreeNode node, int key, T value) {
		int direc = (key - node.key);
		if (direc > 0) {
			if (node.childL != null) {
				recursivePut(node.childL, key, value);
				return;
			}
			node.childL = new TreeNode<T>(node, key, value);
		} else if (direc < 0) {
			if (node.childR != null) {
				recursivePut(node.childR, key, value);
				return;
			}
			node.childR = new TreeNode<T>(node, key, value);
		} else {
			TreeNode newNode = new TreeNode(node.parent, key, value);
			newNode.childL = node.childL;
			newNode.childR = node.childR;
			if (newNode.parent.childL == node) {
				node.parent.childL = newNode;
				return;
			}
		    node.parent.childR = newNode;
		}
	}

	public T search(int key) {
		if (lastKey == key && lastSearch != null) return lastSearch;
		lastKey = key;
		lastSearch = recursiveSearch(root, lastKey);
		return lastSearch;
	}

	private T recursiveSearch(TreeNode<T> node, int key) {
		int direc = (key - node.key);
		if (direc > 0) {
			if (node.childL != null) {
				return recursiveSearch(node.childL, key);
			}
			return null; //a chave não existe na arvore
		} else if (direc < 0) {
			if (node.childR != null) {
				return recursiveSearch(node.childR, key);
			}
			return null; //a chave não existe na arvore
		} else {
			return node.value;
		}
	}

	public void remove(int key) {
		TreeNode<T>[] nodes = recursiveGetNodeAndParent(null, root, key);
		if (nodes == null) return;

		TreeNode parent = nodes[0];
		TreeNode removed = nodes[1];

		if (parent.childL == removed) {
			parent.childL = null;
		} else {
			parent.childR = null;
		}

		recursiveGetChilds(removed, childs);
		for (TreeNode<T> node : childs) {
			put(node.key, node.value);
		}
	}

	private void recursiveGetChilds(TreeNode<T> root, List<TreeNode<T>> output) {
		if (root.childL != null) {
			output.add(root.childL);
			recursiveGetChilds(root.childL, output);
		}
		if (root.childR != null) {
			output.add(root.childR);
			recursiveGetChilds(root.childR, output);
		}
	}

	private TreeNode<T>[] recursiveGetNodeAndParent(TreeNode<T> parent, TreeNode<T> node, int key) {
		int direc = (key - node.key);
		if (direc > 0) {
			if (node.childL != null) {
				return recursiveGetNodeAndParent(node, node.childL, key);
			}
			return null; //a chave não existe na arvore
		} else if (direc < 0) {
			if (node.childR != null) {
				return recursiveGetNodeAndParent(node, node.childR, key);
			}
			return null; //a chave não existe na arvore
		} else {
			return new TreeNode[]{parent, node};
		}
	}

	private static class TreeNode<T> {
		private TreeNode parent;
		private int key;
		private T value;
		private TreeNode<T> childL, childR;

		private TreeNode(TreeNode parent, int key, T value) {
			this.parent = parent;
			this.key = key;
			this.value = value;
		}

		@Override
		protected void finalize() throws Throwable {
			super.finalize();
		}
	}
}
