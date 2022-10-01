package ecs;

import java.util.List;
import java.util.ArrayList;

public class BinaryTree<T> {
	private TreeNode<T> root = null;
	private int lastKey;
	private T lastSearch;
	private List<TreeNode<T>> childs = new ArrayList<>(); //empty list used to tore node childs to remove

	//Returns: 1 to sucess, 0 to fail (when you try to put 2 identic keys)
	public int put(int key, T value) {
		if (root == null) {
			root = new TreeNode<T>(key, value);
			return 1;
		}
		return recursivePut(root, key, value);
	}

	private int recursivePut(TreeNode node, int key, T value) {
		int direc = (key - node.key);
		if (direc > 0) {
			if (node.childL != null) {
				return recursivePut(node.childL, key, value);
			}
			node.childL = new TreeNode<T>(key, value);
			return 1;
		} else if (direc < 0) {
			if (node.childR != null) {
				return recursivePut(node.childR, key, value);
			}
			node.childR = new TreeNode<T>(key, value);
			return 1;
		} else {
			return 0;
		}
	}

	public T search(int key) {
		if (lastKey == key && lastSearch != null) return lastSearch;
		lastKey = key;
		
		if (root == null) return null; 	
		
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
		if (root == null) return; 	
		
		TreeNode<T>[] nodes = recursiveGetNodeAndParent(null, root, key);
		if (nodes == null) return;

		TreeNode<T> parent = nodes[0];
		TreeNode<T> removed = nodes[1];

		if (parent != null) {
			if (parent.childL == removed) {
				parent.childL = null;
			} else {
				parent.childR = null;
			}
		}

		recursiveGetChilds(removed, childs);
		for (TreeNode<T> node : childs) {
			put(node.key, node.value);
		}
		childs.clear();
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
	
	public List<T> toList(List<T> output) {
		if (root == null) return output;
		
		output.add(root.value); //the recursiveGet only gets the root childs, so i need to do it manually
		recursiveGet(root, output);
		return output;
	}

	public List<T> toList() {
		List<T> output = new ArrayList<>();
		return toList(output);
	}
	
	private void recursiveGet(TreeNode<T> root, List<T> output) {
		if (root.childL != null) {
			output.add(root.childL.value);
			recursiveGet(root.childL, output);
		}
		if (root.childR != null) {
			output.add(root.childR.value);
			recursiveGet(root.childR, output);
		}
	}

	private static class TreeNode<T> {
		private int key;
		private T value;
		private TreeNode<T> childL, childR;

		private TreeNode(int key, T value) {
			this.key = key;
			this.value = value;
		}
	}
}
