package Tree;

import java.util.ArrayList;
import java.util.List;





//红黑树是每个节点都带有颜色属性的二叉查找树，颜色为红色或黑色。在二叉查找树强制一般要求以外，对于任何有效的红黑树我们增加了如下的额外要求：
//节点是红色或黑色。
//根是黑色。
//所有叶子都是黑色（叶子是NIL节点）。
//每个红色节点必须有两个黑色的子节点。（从每个叶子到根的所有路径上不能有两个连续的红色节点。）
//从任一节点到其每个叶子的所有简单路径都包含相同数目的黑色节点。

public class RBTree<T extends Comparable<T>>{
	private RBTNode<T> mRoot;
	
	private static final boolean RED =false;
	private static final boolean BLACK = true;
	public class RBTNode<T extends Comparable<T>>{
		boolean color;
		T key;
		RBTNode<T> left;
		RBTNode<T> right;
		RBTNode<T> parent;
		public RBTNode(T key, boolean color, RBTNode<T> parent, RBTNode<T> left, RBTNode<T> right){
			this.key = key;
			this.color= color;
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
		public String toString(){
			String col = color==BLACK?"B":"R";
			return String.valueOf(key)+"("+col+")";
		}
	}
	

	private void leftRotate(RBTNode<T> x){
		/* 
		 * 对红黑树的节点(x)进行左旋转
		 *
		 * 左旋示意图(对节点x进行左旋)：
		 *      px                            px
		 *     /                             /
		 *    x                             y                
		 *   /  \      --(左旋)-.           / \                #
		 *  lx   y                        x  ry     
		 *     /   \                     /    \
		 *    ly   ry                   lx     ly  
		 *
		 *
		 */
		   // 设置x的右孩子为y
	    RBTNode<T> y = x.right;

	    // 将 “y的左孩子” 设为 “x的右孩子”；
	    // 如果y的左孩子非空，将 “x” 设为 “y的左孩子的父亲”
	    x.right = y.left;
	    if (y.left != null)
	        y.left.parent = x;

	    // 将 “x的父亲” 设为 “y的父亲”
	    y.parent = x.parent;

	    if (x.parent == null) {
	        this.mRoot = y;            // 如果 “x的父亲” 是空节点，则将y设为根节点
	    } else {
	        if (x.parent.left == x)
	            x.parent.left = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
	        else
	            x.parent.right = y;    // 如果 x是它父节点的左孩子，则将y设为“x的父节点的左孩子”
	    }
	    
	    // 将 “x” 设为 “y的左孩子”
	    y.left = x;
	    // 将 “x的父节点” 设为 “y”
	    x.parent = y;
	}

	private void rightRotate(RBTNode<T> y){
		/* 
		 * 对红黑树的节点(y)进行右旋转
		 *
		 * 右旋示意图(对节点y进行左旋)：
		 *            py                               py
		 *           /                                /
		 *          y                                x                  
		 *         /  \      --(右旋)-.              /  \                     #
		 *        x   ry                           lx   y  
		 *       / \                                   / \                   #
		 *      lx  rx                                rx  ry
		 * 
		 */
	    // 设置x是当前节点的左孩子。
	    RBTNode<T> x = y.left;

	    // 将 “x的右孩子” 设为 “y的左孩子”；
	    // 如果"x的右孩子"不为空的话，将 “y” 设为 “x的右孩子的父亲”
	    y.left = x.right;
	    if (x.right != null)
	        x.right.parent = y;

	    // 将 “y的父亲” 设为 “x的父亲”
	    x.parent = y.parent;

	    if (y.parent == null) {
	        this.mRoot = x;            // 如果 “y的父亲” 是空节点，则将x设为根节点
	    } else {
	        if (y == y.parent.right)
	            y.parent.right = x;    // 如果 y是它父节点的右孩子，则将x设为“y的父节点的右孩子”
	        else
	            y.parent.left = x;    // (y是它父节点的左孩子) 将x设为“x的父节点的左孩子”
	    }

	    // 将 “y” 设为 “x的右孩子”
	    x.right = y;

	    // 将 “y的父节点” 设为 “x”
	    y.parent = x;
		
	}


	private void insert(RBTNode<T> node){
		//		添加
		//		将一个节点插入到红黑树中，需要执行哪些步骤呢？首先，将红黑树当作一颗二叉查找树，将节点插入；然后，将节点着色为红色；最后，通过"旋转和重新着色"等一系列操作来修正该树，使之重新成为一颗红黑树。详细描述如下：
		//		第一步: 将红黑树当作一颗二叉查找树，将节点插入。
		//		       红黑树本身就是一颗二叉查找树，将节点插入后，该树仍然是一颗二叉查找树。也就意味着，树的键值仍然是有序的。此外，无论是左旋还是右旋，若旋转之前这棵树是二叉查找树，旋转之后它一定还是二叉查找树。这也就意味着，任何的旋转和重新着色操作，都不会改变它仍然是一颗二叉查找树的事实。
		//		好吧？那接下来，我们就来想方设法的旋转以及重新着色，使这颗树重新成为红黑树！
		//
		//		第二步：将插入的节点着色为"红色"。
		//		       为什么着色成红色，而不是黑色呢？为什么呢？在回答之前，我们需要重新温习一下红黑树的特性：
		//		(1) 每个节点或者是黑色，或者是红色。
		//		(2) 根节点是黑色。
		//		(3) 每个叶子节点是黑色。 [注意：这里叶子节点，是指为空的叶子节点！]
		//		(4) 如果一个节点是红色的，则它的子节点必须是黑色的。
		//		(5) 从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。
		//		      将插入的节点着色为红色，不会违背"特性(5)"！少违背一条特性，就意味着我们需要处理的情况越少。接下来，就要努力的让这棵树满足其它性质即可；满足了的话，它就又是一颗红黑树了。o(∩∩)o...哈哈
		//
		//		第三步: 通过一系列的旋转或着色等操作，使之重新成为一颗红黑树。
		//		       第二步中，将插入节点着色为"红色"之后，不会违背"特性(5)"。那它到底会违背哪些特性呢？
		//		       对于"特性(1)"，显然不会违背了。因为我们已经将它涂成红色了。
		//		       对于"特性(2)"，显然也不会违背。在第一步中，我们是将红黑树当作二叉查找树，然后执行的插入操作。而根据二叉查找数的特点，插入操作不会改变根节点。所以，根节点仍然是黑色。
		//		       对于"特性(3)"，显然不会违背了。这里的叶子节点是指的空叶子节点，插入非空节点并不会对它们造成影响。
		//		       对于"特性(4)"，是有可能违背的！
		//		       那接下来，想办法使之"满足特性(4)"，就可以将树重新构造成红黑树了。
		int cmp;
		RBTNode<T> y = null;
		RBTNode<T> x = this.mRoot;
		while(x!=null){
			y = x;
			cmp = node.key.compareTo(x.key);
			if(cmp<0) x = x.left;
			else x=x.right;
		}
		node.parent = y;
		if(y!=null){
			cmp = node.key.compareTo(y.key);
			if(cmp<0) y.left = node;
			else y.right = node;
		}
		else this.mRoot = node;
		node.color = RED;
		insertFixUp(node);
	}
	public void insert(T key){
		RBTNode<T> node=new RBTNode<T>(key,BLACK,null,null,null);
		if(node!=null) insert(node);
	}

	private void insertFixUp(RBTNode<T> node){
		/*
		 * 红黑树插入修正函数
		 *
		 * 在向红黑树中插入节点之后(失去平衡)，再调用该函数；
		 * 目的是将它重新塑造成一颗红黑树。
		 *
		 * 参数说明：
		 *     node 插入的结点        // 对应《算法导论》中的z
		 */
		RBTNode<T> parent,gparent;
		while(((parent = parentOf(node))!=null) && isRed(parent)){
			gparent = parentOf(parent);
			if(parent==gparent.left){
				RBTNode<T> uncle =gparent.right;
				if((uncle!=null) && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					setRed(gparent);
					node = gparent;
					continue;
				}
				if(parent.right == node){
					RBTNode<T> tmp;
					leftRotate(parent);
					tmp = parent;
					parent = node;
					node = tmp;
				}
				setBlack(parent);
				setRed(gparent);
				rightRotate(gparent);
			}else{
				RBTNode<T> uncle = gparent.left;
				if(uncle!=null && isRed(uncle)){
					setBlack(uncle);
					setBlack(parent);
					setRed(gparent);
					node = gparent;
					continue;
				}
				if(parent.left==node){
					RBTNode<T> tmp;
					rightRotate(parent);
					tmp = parent;
					parent = node;
					node = tmp;					
				}
				setBlack(parent);
				setRed(gparent);
				leftRotate(gparent);
			}
		}
		setBlack(this.mRoot);
	}
	private void setBlack(RBTNode<T> node){if(node!=null) node.color = BLACK;}
	private void setRed(RBTNode<T> node){if(node!=null) node.color = RED;}
	private boolean colorOf(RBTNode<T> node){return node!=null?node.color:BLACK;}
	private boolean isRed(RBTNode<T> node){return ((node!=null)&&(node.color==RED)) ? true : false;}
	private boolean isBlack(RBTNode<T> node){return ((node!=null)&&(node.color==BLACK)) ? true : false;}
	private RBTNode<T> parentOf(RBTNode<T> node){return node==null?null:node.parent;}
	private void setParent(RBTNode<T> node, RBTNode<T> parent){
		if(node!=null) node.parent = parent;
	}
	private void setColor(RBTNode<T> node, boolean color) {
		if(node!=null) node.color = color;
	}
	private RBTNode<T> search(RBTNode<T> node,T key){
		if(node == null) return node;
		int cmp = key.compareTo(node.key);
		if(cmp<0) return search(node.left,key);
		else if(cmp>0) return search(node.right,key);
		return node;
	}
	public RBTNode<T> search(T key){
		return search(mRoot,key);
	}

	private void remove(RBTNode<T> node){
		//		删除操作
		//
		//			将红黑树内的某一个节点删除。需要执行的操作依次是：首先，将红黑树当作一颗二叉查找树，将该节点从二叉查找树中删除；然后，通过"旋转和重新着色"等一系列来修正该树，使之重新成为一棵红黑树。详细描述如下：
		//			第一步：将红黑树当作一颗二叉查找树，将节点删除。
		//			       这和"删除常规二叉查找树中删除节点的方法是一样的"。分3种情况：
		//			① 被删除节点没有儿子，即为叶节点。那么，直接将该节点删除就OK了。
		//			② 被删除节点只有一个儿子。那么，直接删除该节点，并用该节点的唯一子节点顶替它的位置。
		//			③ 被删除节点有两个儿子。那么，先找出它的后继节点；然后把“它的后继节点的内容”复制给“该节点的内容”；之后，删除“它的后继节点”。在这里，后继节点相当于替身，在将后继节点的内容复制给"被删除节点"之后，再将后继节点删除。这样就巧妙的将问题转换为"删除后继节点"的情况了，下面就考虑后继节点。 在"被删除节点"有两个非空子节点的情况下，它的后继节点不可能是双子非空。既然"的后继节点"不可能双子都非空，就意味着"该节点的后继节点"要么没有儿子，要么只有一个儿子。若没有儿子，则按"情况① "进行处理；若只有一个儿子，则按"情况② "进行处理。
		//
		//			第二步：通过"旋转和重新着色"等一系列来修正该树，使之重新成为一棵红黑树。
		//			        因为"第一步"中删除节点之后，可能会违背红黑树的特性。所以需要通过"旋转和重新着色"来修正该树，使之重新成为一棵红黑树。
		RBTNode<T> child,parent;
		boolean color;
		if((node.left!=null) && node.right!=null){
			RBTNode<T> replace = node;
			replace = replace.right;
			while(replace.left!=null) replace = replace.left;
			if(parentOf(node)!=null){
				if(parentOf(node).left==node) parentOf(node).left = replace;
				else parentOf(node).right = replace;
			}
			else this.mRoot = replace;
			child = replace.right;
			parent = parentOf(replace);
			color = colorOf(replace);
			if(parent == node){
				parent = replace;
			}
			else{
				if(child!=null) setParent(child,parent);
				parent.left = child;
				replace.right = node.right;
				setParent(node.right,replace);
			}
	        replace.parent = node.parent;
	        replace.color = node.color;
	        replace.left = node.left;
	        node.left.parent = replace;

	        if(color == BLACK) removeFixUp(child, parent);
	        node = null;
	        return ;
		}
	    if(node.left !=null){
	        child = node.left;
	    }else{
	        child = node.right;
	    }
	    parent = node.parent;
	    color = node.color;
	    if(child!=null) child.parent = parent;
	    if(parent!=null){
	    	if(parent.left==node) parent.left = child;
	    	else parent.right = child;
	    }
	    else this.mRoot = child;
	    if(color = BLACK) removeFixUp(child,parent);
	    node = null;
	}
	public void remove(T key){
		RBTNode<T> node;
		if((node=search(mRoot,key))!=null) remove(node);
	}
	

	private void removeFixUp(RBTNode<T> node, RBTNode<T> parent){
		/*
		 * 红黑树删除修正函数
		 *
		 * 在从红黑树中删除插入节点之后(红黑树失去平衡)，再调用该函数；
		 * 目的是将它重新塑造成一颗红黑树。
		 *
		 * 参数说明：
		 *     node 待修正的节点
		 */
		RBTNode<T> other;
	    while ((node==null || isBlack(node)) && (node != this.mRoot)) {
	        if (parent.left == node) {
	            other = parent.right;
	            if (isRed(other)) {
	                // Case 1: x的兄弟w是红色的  
	                setBlack(other);
	                setRed(parent);
	                leftRotate(parent);
	                other = parent.right;
	            }

	            if ((other.left==null || isBlack(other.left)) &&
	                (other.right==null || isBlack(other.right))) {
	                // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的  
	                setRed(other);
	                node = parent;
	                parent = parentOf(node);
	            } else {

	                if (other.right==null || isBlack(other.right)) {
	                    // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。  
	                    setBlack(other.left);
	                    setRed(other);
	                    rightRotate(other);
	                    other = parent.right;
	                }
	                // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
	                setColor(other, colorOf(parent));
	                setBlack(parent);
	                setBlack(other.right);
	                leftRotate(parent);
	                node = this.mRoot;
	                break;
	            }
	        } else {

	            other = parent.left;
	            if (isRed(other)) {
	                // Case 1: x的兄弟w是红色的  
	                setBlack(other);
	                setRed(parent);
	                rightRotate(parent);
	                other = parent.left;
	            }

	            if ((other.left==null || isBlack(other.left)) &&
	                (other.right==null || isBlack(other.right))) {
	                // Case 2: x的兄弟w是黑色，且w的俩个孩子也都是黑色的  
	                setRed(other);
	                node = parent;
	                parent = parentOf(node);
	            } else {

	                if (other.left==null || isBlack(other.left)) {
	                    // Case 3: x的兄弟w是黑色的，并且w的左孩子是红色，右孩子为黑色。  
	                    setBlack(other.right);
	                    setRed(other);
	                    leftRotate(other);
	                    other = parent.left;
	                }

	                // Case 4: x的兄弟w是黑色的；并且w的右孩子是红色的，左孩子任意颜色。
	                setColor(other, colorOf(parent));
	                setBlack(parent);
	                setBlack(other.left);
	                rightRotate(parent);
	                node = this.mRoot;
	                break;
	            }
	        }
	    }

	    if (node!=null)
	        setBlack(node);
	}

	private void _levelTraverse(RBTNode<T> root,List<List<String>> ret,int level){
		if(level==ret.size()) ret.add(new ArrayList<String>());
		if(root==null){
			ret.get(level).add(null);
			return;
		}
		ret.get(level).add(root.toString());
		_levelTraverse(root.left,ret,level+1);
		_levelTraverse(root.right,ret,level+1);
	}
	public List<List<String>> levelTraverse(){
		List<List<String>> ret = new ArrayList<>();
		_levelTraverse(mRoot,ret,0);
		return ret;		
	}
}
