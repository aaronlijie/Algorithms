package Tree;
import java.util.*;

public class AVLTree<T extends Comparable<T>>{
	private AVLTreeNode<T> mRoot;
	
	
	class AVLTreeNode<T extends Comparable<T>> {
		public T key;
		public int height;
		public AVLTreeNode<T> left,right;
		public AVLTreeNode(T key,AVLTreeNode<T> left,AVLTreeNode<T> right){
			this.key = key;
			this.height = 0;
			this.left = left;
			this.right= right;
		}

	}
	public AVLTree(){
		mRoot = null;
	}
	public void constructAVLTree(T[] array){
		for(T x:array) insert(x);
	}
	public void insert(T key){
		mRoot =insert(mRoot,key);
	}

	public void remove(T k){
		AVLTreeNode<T> z = search(mRoot, k);
		if(z!=null) mRoot = remove(mRoot, z);
	}
	public AVLTreeNode<T> search(T k){
		return search(mRoot, k);
	}
	
	
	private int height(AVLTreeNode<T> tree){
		if(tree!=null) return tree.height;
		return -1;
	}
	public int height() {
	    return height(mRoot);
	}
	private int max(int a,int b){
		return a>b?a:b;
	}
	private AVLTreeNode<T> leftLeftRotation(AVLTreeNode<T> k2){
		AVLTreeNode<T> k1;
		k1 = k2.left;
		k2.left = k1.right;
		k1.right=k2;
		k2.height = max(height(k2.left),height(k2.right))+1;
		k1.height = max(height(k1.left),height(k1.right))+1;
		return k1;
	}
	private AVLTreeNode<T> rightRightRotation(AVLTreeNode<T> k1){
		AVLTreeNode<T> k2;
		k2 = k1.right;
		k1.right = k2.left;
		k2.left=k1;
		k1.height = max(height(k1.left),height(k1.right))+1;
		k2.height = max(height(k2.right),height(k2.left))+1;
		return k2;
	}
	private AVLTreeNode<T> leftRightRotation(AVLTreeNode<T> k2){
		k2.left = rightRightRotation(k2.left);
		return leftLeftRotation(k2);
	}
	private AVLTreeNode<T> rightLeftRotation(AVLTreeNode<T> k2){
		k2.right = leftLeftRotation(k2.right);
		return rightRightRotation(k2);
	}
	AVLTreeNode<T> insert(AVLTreeNode<T> tree, T key){
		if(tree==null){
			tree = new AVLTreeNode<T>(key,null,null);
			return tree;
		}
		else{
			int cmp = key.compareTo(tree.key);
			if(cmp<0){
				tree.left = insert(tree.left,key);
				if(height(tree.left)-height(tree.right)==2){
					if(key.compareTo(tree.left.key)<0){
						tree = leftLeftRotation(tree);
					}
					else{
						tree = leftRightRotation(tree);
					}
				}
			}
			else if(cmp>0){
				tree.right = insert(tree.right,key);
				if(height(tree.right)-height(tree.left)==2){
					if(key.compareTo(tree.right.key)>0){
						tree = rightRightRotation(tree);
					}
					else{
						tree = rightLeftRotation(tree);
					}
				}
			}
		}
		tree.height = max(height(tree.left),height(tree.right))+1;
		return tree;
	}

	AVLTreeNode<T> remove(AVLTreeNode<T> tree, AVLTreeNode<T> z){
		if(tree ==null || z==null) return null;
		int cmp = z.key.compareTo(tree.key);
		if(cmp<0){
			tree.left = remove(tree.left,z);
//			int l = tree.left!=null?tree.left.height:0;
			
			if(height(tree.right)-height(tree.left)==2){
//				int rightHeight = tree.right.right!=null>t
				AVLTreeNode<T> r = tree.right;
				if(height(r.right)>=height(r.left)){
					
					tree = rightRightRotation(tree);
				}
				else{
					tree = rightLeftRotation(tree);
				}
			}
		}
		else if(cmp>0){
			tree.right = remove(tree.right,z);
			if(height(tree.left)-height(tree.right)>=2){
				AVLTreeNode<T> l = tree.left;
				if(height(l.left)>=height(l.right)){
					
					tree = leftLeftRotation(tree);
				}
				else{
					tree = leftRightRotation(tree);
				}
			}
		}
		else{
			if(tree.left!=null && tree.right!=null){
				if(height(tree.left)>height(tree.right)){
					AVLTreeNode<T> max = maximum(tree.left);
					tree.key = max.key;
					tree.left=remove(tree.left,max);
				}
				else{
					AVLTreeNode<T> min = minimum(tree.right);
					tree.key = min.key;
					tree.right = remove(tree.right,min);
				}
			}
			else{
//					AVLTreeNode<T> tmp = tree;
				tree = (tree.left!=null)? tree.left:tree.right;
//					tmp = null;
			}

		}
		if(tree!=null){
			tree.height = max(height(tree.left),height(tree.right))+1;
		}
		return tree;
	}

	AVLTreeNode<T> search(AVLTreeNode<T> tree,T k){
		if(tree==null) return null;
		int cmp =k.compareTo(tree.key);
		if(cmp<0) return search(tree.left,k);
		else if(cmp>0) return search(tree.right,k);
		else return tree;
	}
	private AVLTreeNode<T> minimum(AVLTreeNode<T> tree){
		if(tree==null) return null;
		while(tree.left!=null) tree = tree.left;
		return tree;
	}
	private AVLTreeNode<T> maximum(AVLTreeNode<T> tree){
		if(tree==null) return null;
		while(tree.right!=null) tree = tree.right;
		return tree;
	}
	
	public List<T> inOrder(){
		List<T> ret = new ArrayList<>();
		_inOrder(mRoot,ret);
		return ret;
	}
	private void _inOrder(AVLTreeNode<T> root,List<T> ret){
		if(root==null) return;
		_inOrder(root.left,ret);
		ret.add(root.key);
		_inOrder(root.right,ret);
	}
	public List<List<T>> levelTraverse(){
		List<List<T>> ret = new ArrayList<>();
		_levelTraverse(mRoot,ret,0);
		return ret;		
	}
	private void _levelTraverse(AVLTreeNode<T> root,List<List<T>> ret,int level){
		if(level==ret.size()) ret.add(new ArrayList<T>());
		if(root==null){
			ret.get(level).add(null);
			return;
		}
		ret.get(level).add(root.key);
		_levelTraverse(root.left,ret,level+1);
		_levelTraverse(root.right,ret,level+1);
	}

}



