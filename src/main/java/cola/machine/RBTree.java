package cola.machine;

import cola.machine.RBTreeNode;

public class RBTree<E> {
    //红黑树的根结点
    private RBTreeNode<E> rootNode;
    //构造方法
    public RBTree(){
        this.rootNode = null;
    }
    //构造方法结束
    //get set方法
    public RBTreeNode<E> getRootNode(){
        return this.rootNode;
    }
    public void setRootNode(RBTreeNode<E>rootNode){
        this.rootNode = rootNode;
    }
    //get set方法结束
    public RBTreeNode<E> parentOf(RBTreeNode<E>node){
        return node == null?null:node.getParentNode();
    }
    public RBTreeNode<E> leftOf(RBTreeNode<E>node){
        return node == null?null:node.getLeftNode();
    }
    public RBTreeNode<E> rightOf(RBTreeNode<E>node){
        return node == null?null:node.getRightNode();
    }
    public boolean colorOf(RBTreeNode<E>node){
        return node == null?RBTreeNode.BLACK:node.getColor();
    }
    public void setColor(RBTreeNode<E>node,boolean color){
        if(node == null) return;
        node.setColor(color);
    }
    /**
     * 这个方法的作用是啥？<br/>
     * 三种情况：<br/>
     * 1.t为空，返回空<br/>
     *
     * 2.t的右子结点不为空<br/>
     *
     * 返回t的右子树的最小结点(左下方的结点)<br/>
     *
     * 3.t的右子结点为空<br/>
     *
     * p指向t的父结点
     * ch指向t
     * 然后跑个while循环
     * 当p不为空并且ch是p的右子结点时<br/>
     *
     * ch指向p
     * p指向p的父结点
     * while循环结束
     * 最后return p
     * @param t
     * @return
     */
    //static <K,V> TreeMap.Entry<K,V> successor(Entry<K,V> t) {
    private RBTreeNode<E> successor(RBTreeNode<E> t) {
        if (t == null)
            return null;
        /**
         * t的右子结点不为空
         */
            //else if (t.right != null) {
        else if (t.getRightNode() != null) {
            /**
             * p指向t的右子结点
             */
            //Entry<K,V> p = t.right;
            RBTreeNode<E> p = t.getRightNode();
            /**
             * 一直到p的子树的最左下结点
             * 然后将此结点返回
             */
            //while (p.left != null)
            while (p.getLeftNode() != null)
                //p = p.left;
                p = p.getLeftNode();
            return p;
        } else {//t的右子结点为空
            /**
             * p指向t的父结点
             */
            //Entry<K,V> p = t.parent;
            RBTreeNode<E> p = t.getParentNode();
            /**
             * ch指向t
             */
            //Entry<K,V> ch = t;
            RBTreeNode<E> ch = t;
            /**
             * 循环条件：
             * p非空
             * ch是p的右子结点
             */
            //while (p != null && ch == p.right) {
            while (p != null && ch == p.getRightNode()) {
                ch = p;
                //p = p.parent;
                p = p.getParentNode();
            }
            return p;
        }
    }
    //static <K,V> Entry<K,V> predecessor(Entry<K,V> t) {
    private RBTreeNode<E> predecessor(RBTreeNode<E> t) {
        if (t == null)
            return null;
            //else if (t.left != null) {
        else if (t.getLeftNode() != null) {
            //Entry<K,V> p = t.left;
            RBTreeNode<E> p = t.getLeftNode();
            //while (p.right != null)
            while (p.getRightNode() != null)
                //p = p.right;
                p = p.getRightNode();
            return p;
        } else {
            //Entry<K,V> p = t.parent;
            RBTreeNode<E> p = t.getParentNode();
            //Entry<K,V> ch = t;
            RBTreeNode<E> ch = t;
            //while (p != null && ch == p.left) {
            while (p != null && ch == p.getLeftNode()) {
                ch = p;
                //p = p.parent;
                p = p.getParentNode();
            }
            return p;
        }
    }
    /**
     * 围绕某个结点左旋转
     * @param node
     */
    private void rotateLeft(RBTreeNode<E>node){
        if (node != null) {
            /**
             * 得到node的右子结点
             */
            //Entry<K,V> r = p.right;
            if(node!=rootNode && node!=node.getParentNode().getLeftNode()){
                System.out.println(123);
            }
            RBTreeNode<E> rightNode = node.getRightNode();
            /**
             * 把node的右子结点设为rightNode的左子结点
             */
            //p.right = r.left;
            node.setRightNode(rightNode.getLeftNode());
            /**
             * 如果rightNode的左子结点非空
             * 就把rightNode的左子结点的父结点设为node
             */
            //if (r.left != null)
            if(rightNode.getLeftNode()!=null)
                //r.left.parent = p;
                rightNode.getLeftNode().setParentNode(node);
            //r.parent = p.parent;
            /**
             * 把rightNode的父结点设为node的父结点
             */
            rightNode.setParentNode(node.getParentNode());
            /**
             * node的父结点为空
             * 表明node是红黑树的根结点
             */
            //if (p.parent == null)
            if(node.getParentNode() == null)
                //root = r;
                this.setRootNode(rightNode);
            /**
             * node父结点的左子结点存储的值与node相同
             * 表明node是父结点的左子结点
             */
                //else if (p.parent.left == p)
            else if(node.getParentNode().getLeftNode() == node)
                //p.parent.left = r;
                node.getParentNode().setLeftNode(rightNode);
            /**
             * node是父结点的右子结点
             */
            else
                //p.parent.right = r;
                node.getParentNode().setRightNode(rightNode);
            /**
             * rightNode的左子结点设为node
             */
            //r.left = p;
            rightNode.setLeftNode(node);
            /**
             * node的父结点设为rightNode
             */
            //p.parent = r;
            node.setParentNode(rightNode);
        }

    }
    /**
     * 围绕某个结点右旋转
     * 原理同左旋转相同
     * 只不过方向换了下
     * @param node
     */
    private void rotateRight(RBTreeNode<E>node){
        //if (p != null) {
        if(node != null){
            /**
             * 得到node的左子结点leftNode
             */
            //Entry<K,V> l = p.left;
            RBTreeNode<E>leftNode = node.getLeftNode();
            /**
             * 把node的左子结点设为leftNode的右子结点
             */
            //p.left = l.right;
            node.setLeftNode(leftNode.getRightNode());
            /**
             * 如果leftNode的右子结点非空
             * 那么把leftNode的右子结点的父结点设为node
             */
            //if (l.right != null) l.right.parent = p;
            if(leftNode.getRightNode() != null) leftNode.getRightNode().setParentNode(node);
            /**
             * leftNode的父结点设为node的父结点
             */
            //l.parent = p.parent;
            leftNode.setParentNode(node.getParentNode());
            /**
             * node为根结点
             */
            //if (p.parent == null)
            if(node.getParentNode() == null)
                //root = l;
                this.rootNode = leftNode;
                //else if (p.parent.right == p)
            else if(node.getParentNode().getRightNode() == node)
                //p.parent.right = l;
                node.getParentNode().setRightNode(leftNode);
                //else p.parent.left = l;
            else node.getParentNode().setLeftNode(leftNode);
            //l.right = p;
            leftNode.setRightNode(node);
            //p.parent = l;
            node.setParentNode(leftNode);
        }

    }
    public void insertNode(RBTreeNode<E> node) {
        if (node == null) return;
        if (node.getValue() == null) return;
        /**
         * rootnode为空，
         * 空的红黑树
         */
        if (this.getRootNode() == null) {
            rootNode = new RBTreeNode<E>(node.getValue());
            return;
        }
        /**
         * rootnode非空
         */
        RBTreeNode<E> tempNode = this.rootNode;
        while (true) {
            /**
             * 红黑树中已经存在此结点
             * 无须插入
             * 直接返回
             */
            if (tempNode.compareTo(node) == 0) {
                return;
            }else if (tempNode.compareTo(node) > 0) {
                if (tempNode.getLeftNode() != null) {
                    tempNode = tempNode.getLeftNode();
                }else {
                    tempNode.setLeftNode(node);
                    node.setParentNode(tempNode);
                    fixAfterInsertion(node);
                    return;
                }
            }else {
                if (tempNode.getRightNode() != null) {
                    tempNode = tempNode.getRightNode();
                }else {
                    tempNode.setRightNode(node);
                    node.setParentNode(tempNode);
                    fixAfterInsertion(node);
                    return;
                }
            }
        }
    }
    private void fixAfterInsertion(RBTreeNode<E>node){
        if(node == null) return;
        /**
         * node标记为红结点
         */
        //x.color = RED;
        node.setColor(RBTreeNode.RED);
        /**
         * while循环条件
         * node非空 并且
         * node非根结点 并且
         * node的父结点的颜色为红色
         */
        //while (x != null && x != root && x.parent.color == RED) {
        while(node != null && ( node != rootNode) && node.getParentNode().getColor() == RBTreeNode.RED){
            /**
             * node的父结点是node祖父结点的左子结点
             */
            //if (parentOf(x) == leftOf(parentOf(parentOf(x)))) {
            if(parentOf(node) == leftOf(parentOf(parentOf(node)))){
                /**
                 * y是node祖父结点的右子结点
                 */
                //Entry<K,V> y = rightOf(parentOf(parentOf(x)));
                RBTreeNode<E> y = rightOf(parentOf(parentOf(node)));
                /**
                 * 如果y的颜色是红色
                 */
                //if (colorOf(y) == RED) {
                if(colorOf(y) == RBTreeNode.RED){
                    /**
                     * 把node父结点的颜色设为黑色
                     */
                    //setColor(parentOf(x), BLACK);
                    setColor(parentOf(node),RBTreeNode.BLACK);
                    /**
                     * 把y的颜色设为黑色
                     */
                    //setColor(y, BLACK);
                    setColor(y,RBTreeNode.BLACK);
                    /**
                     * 把node祖父结点的颜色设为红色
                     */
                    //setColor(parentOf(parentOf(x)), RED);
                    setColor(parentOf(parentOf(node)),RBTreeNode.RED);
                    /**
                     * node指向node的祖父结点
                     */
                    //x = parentOf(parentOf(x));
                    node = parentOf(parentOf(node));
                } else {
                    /**
                     * 这块y的颜色是黑色
                     */
                    /**
                     * 如果node是父结点的右子结点
                     */
                    //if (x == rightOf(parentOf(x))) {
                    if(node == rightOf(parentOf(node))){
                        /**
                         * node指向node的父结点
                         */
                        //x = parentOf(x);
                        node = parentOf(node);
                        /**
                         * 围绕node进行左旋转
                         */
                        //rotateLeft(x);
                        rotateLeft(node);
                    }
                    /**
                     * 把node的父结点设置为黑色
                     */
                    //setColor(parentOf(x), BLACK);
                    setColor(parentOf(node),RBTreeNode.BLACK);
                    /**
                     * 把node的祖父结点设置为红色
                     */
                    //setColor(parentOf(parentOf(x)), RED);
                    setColor(parentOf(parentOf(node)),RBTreeNode.RED);
                    /**
                     * 围绕node的祖父结点进行右旋转
                     */
                    //rotateRight(parentOf(parentOf(x)));
                    rotateRight(parentOf(parentOf(node)));
                }
                /**
                 * node的父结点是node祖父结点的右子结点
                 */
            } else {
                /**
                 * y指向node祖父结点的左子结点
                 */
                //Entry<K,V> y = leftOf(parentOf(parentOf(x)));
                RBTreeNode<E> y = leftOf(parentOf(parentOf(node)));
                /**
                 * 如果y的颜色是红色
                 */
                //if (colorOf(y) == RED) {
                if(colorOf(y) == RBTreeNode.RED){
                    /**
                     * 把node的父结点设为黑色
                     */
                    //setColor(parentOf(x), BLACK);
                    setColor(parentOf(node),RBTreeNode.BLACK);
                    /**
                     * 把y的颜色设为黑色
                     */
                    //setColor(y, BLACK);
                    setColor(y,RBTreeNode.BLACK);
                    /**
                     * 把node的祖父结点设为红色
                     */
                    //setColor(parentOf(parentOf(x)), RED);
                    setColor(parentOf(parentOf(node)),RBTreeNode.RED);
                    /**
                     * node结点指向其祖父结点
                     * 然后向上逐层调整
                     */
                    //x = parentOf(parentOf(x));
                    node = parentOf(parentOf(node));
                } else {
                    /**
                     * node是node父结点的左子结点
                     */
                    //if (x == leftOf(parentOf(x))) {
                    if(node == leftOf(parentOf(node))){
                        /**
                         * node指向node的父结点
                         */
                        //x = parentOf(x);
                        node = parentOf(node);
                        /**
                         * 围绕node进行右旋转
                         * 其实node已经是老node的父结点了
                         */
                        //rotateRight(x);
                        rotateRight(node);
                    }
                    /**
                     * 把node的父结点设为黑色
                     */
                    //setColor(parentOf(x), BLACK);
                    setColor(parentOf(node),RBTreeNode.BLACK);
                    /**
                     * 把node的祖父结点设为红色
                     */
                    //setColor(parentOf(parentOf(x)), RED);
                    setColor(parentOf(parentOf(node)),RBTreeNode.RED);
                    /**
                     * 围绕node的祖父结点进行左旋转
                     */
                    //rotateLeft(parentOf(parentOf(x)));
                    rotateLeft(parentOf(parentOf(node)));
                }
            }
        }
        /**
         * 把根结点设成黑色
         */
        //root.color = BLACK;
        setColor(rootNode,RBTreeNode.BLACK);

    }
    public void deleteNode(RBTreeNode<E> p) {
        //modCount++;
        //size--;

        // If strictly internal, copy successor's element to p and then make p
        // point to successor.
        /**
         * p的两个子结点都非空
         */
        //if (p.left != null && p.right != null) {
        if (p.getLeftNode() != null && p.getRightNode() != null) {
            //Entry<K,V> s = successor (p);
            RBTreeNode<E> s = successor(p);
            //p.key = s.key;
            p.setValue(s.getValue());
            //p.value = s.value;
            p = s;
        } // p has 2 children

        // Start fixup at replacement node, if it exists.
        //Entry<K,V> replacement = (p.left != null ? p.left : p.right);
        RBTreeNode<E> replacement = (p.getLeftNode() != null ? p.getLeftNode() : p.getRightNode());

        if (replacement != null) {
            // Link replacement to parent
            //replacement.parent = p.parent;
            replacement.setParentNode(p.getParentNode());
            /**
             * p的父结点为空
             * p是根结点
             */
            //if (p.parent == null)
            if (p.getParentNode() == null)
                //root = replacement;
                rootNode = replacement;
            /**
             * p是父结点的左子结点
             */
                //else if (p == p.parent.left)
            else if (p == p.getParentNode().getLeftNode())
                //p.parent.left  = replacement;
                p.getParentNode().setLeftNode(replacement);
            /**
             * p是父结点的右子结点
             */
            else
                //p.parent.right = replacement;
                p.getParentNode().setRightNode(replacement);
            /**
             * ok
             * 把p的左右子结点，父结点指针全部置空
             * p被删除了
             */

            // Null out links so they are OK to use by fixAfterDeletion.
            //p.left = p.right = p.parent = null;
            p.setLeftNode(null);
            p.setRightNode(null);
            p.setParentNode(null);

            // Fix replacement
            //if (p.color == BLACK)
            if (p.getColor() == RBTreeNode.BLACK)
                fixAfterDeletion(replacement);
            //} else if (p.parent == null) { // return if we are the only node.
        } else if (p.getParentNode() == null) {
            //root = null;
            rootNode = null;
        } else { //  No children. Use self as phantom replacement and unlink.
            /**
             * 在这个else里面
             * replacement是null
             * 表明p的左右两个子结点都为空
             */
            //if (p.color == BLACK)
            if (p.getColor() == RBTreeNode.BLACK)
                fixAfterDeletion(p);

            //if (p.parent != null) {
            if (p.getParentNode() != null) {
                /**
                 * p是p的父结点的左子结点
                 */
                //if (p == p.parent.left)
                if (p == p.getParentNode().getLeftNode())
                    //p.parent.left = null;
                    p.getParentNode().setLeftNode(null);
                /**
                 * p是p的父结点的右子结点
                 */
                    //else if (p == p.parent.right)
                else if (p == p.getParentNode().getRightNode())
                    //p.parent.right = null;
                    p.getParentNode().setRightNode(null);
                /**
                 * 把p的父结点设为空
                 * 删除ＯＫ
                 */
                //p.parent = null;
                p.setParentNode(null);
            }
        }
    }

    //private void fixAfterDeletion(Entry<K,V> x) {
    private void fixAfterDeletion(RBTreeNode<E>node){
        /**
         * 循环条件：
         * node不是根结点
         * 而且
         * node结点的颜色为黑色
         */
        //while (x != root && colorOf(x) == BLACK) {
        while(this.getRootNode() != node && colorOf(node) == RBTreeNode.BLACK){
            /**
             * node是node父结点的左子结点
             */
            //if (x == leftOf(parentOf(x))) {
            if( node == leftOf(parentOf(node))){
                /**
                 * sib指向node父结点的右子结点
                 */
                //Entry<K,V> sib = rightOf(parentOf(x));
                RBTreeNode<E> sib = rightOf(parentOf(node));
                /**
                 * 参见colorOf(RBTreeNode<E>)方法实现
                 * 有对空值的判断
                 */
                //if (colorOf(sib) == RED) {
                if(colorOf(sib) == RBTreeNode.RED){
                    /**
                     * setColor()中也有对空值的判断
                     */
                    //setColor(sib, BLACK);
                    setColor(sib, RBTreeNode.BLACK);
                    /**
                     * 把node的父结点设为红色
                     */
                    //setColor(parentOf(x), RED);
                    setColor(parentOf(node), RBTreeNode.RED);
                    /**
                     * 围绕node的父结点进行左旋转
                     */
                    //rotateLeft(parentOf(x));
                    rotateLeft(parentOf(node));
                    /**
                     * sib指向node父结点的右子结点
                     */
                    //sib = rightOf(parentOf(x));
                    sib = rightOf(parentOf(node));
                }
                /**
                 * 如果sib的两个子结点都是黑色
                 */
                //if (colorOf(leftOf(sib))  == BLACK &&
                if(colorOf(leftOf(sib)) == RBTreeNode.BLACK &&
                        //colorOf(rightOf(sib)) == BLACK) {
                        colorOf(rightOf(sib)) == RBTreeNode.BLACK) {
                    /**
                     * 把sib设为红色
                     */
                    //setColor(sib, RED);
                    setColor(sib, RBTreeNode.RED);
                    /**
                     * node指向它的父结点
                     */
                    //x = parentOf(x);
                    node = parentOf(node);
                } else {//这个else表示sib的两个子结点并非都是黑色，一红一黑或都是红色
                    /**
                     * 如果sib的右子结点是黑色
                     * 那它的左子结点肯定是红色
                     */
                    //if (colorOf(rightOf(sib)) == BLACK) {
                    if(colorOf(rightOf(sib)) == RBTreeNode.BLACK) {
                        /**
                         * 把sib的左子结点设为黑色
                         */
                        //setColor(leftOf(sib), BLACK);
                        setColor(leftOf(sib), RBTreeNode.BLACK);
                        /**
                         * 把sib的颜色设为黑色
                         */
                        //setColor(sib, RED);
                        setColor(sib, RBTreeNode.RED);
                        /**
                         * 围绕sib进行右旋转
                         */
                        //rotateRight(sib);
                        rotateRight(sib);
                        /**
                         * sib指向node父结点的右子结点
                         */
                        //sib = rightOf(parentOf(x));
                        sib = rightOf(parentOf(node));
                    }
                    /**
                     * 把sib的颜色设为node父结点的颜色
                     */
                    //setColor(sib, colorOf(parentOf(x)));
                    setColor(sib, colorOf(parentOf(node)));
                    /**
                     * 把node父结点设为黑色
                     */
                    //setColor(parentOf(x), BLACK);
                    setColor(parentOf(node), RBTreeNode.BLACK);
                    /**
                     * 把sib右子结点的颜色设为黑色
                     */
                    //setColor(rightOf(sib), BLACK);
                    setColor(rightOf(sib), RBTreeNode.BLACK);
                    /**
                     * 围绕node的父结点进行左旋转
                     */
                    //rotateLeft(parentOf(x));
                    rotateLeft(parentOf(node));
                    /**
                     * node结点指向红黑树的根结点
                     */
                    //x = root;
                    node = rootNode;
                }
            } else { // symmetric
                /**
                 * sib指向node父结点的左子结点
                 */
                //Entry<K,V> sib = leftOf(parentOf(x));
                RBTreeNode<E> sib = leftOf(parentOf(node));
                /**
                 * 如果sib的颜色是红色
                 */
                //if (colorOf(sib) == RED) {
                if(colorOf(sib) == RBTreeNode.RED) {
                    /**
                     * 把sib的颜色设为黑色
                     */
                    //setColor(sib, BLACK);
                    setColor(sib, RBTreeNode.BLACK);
                    /**
                     * 把node父结点的颜色设为红色
                     */
                    //setColor(parentOf(x), RED);
                    setColor(parentOf(node), RBTreeNode.RED);
                    /**
                     * 围绕node的父结点进行右旋转
                     */
                    //rotateRight(parentOf(x));
                    rotateRight(parentOf(node));
                    /**
                     * sib指向node父结点的左子结点
                     */
                    //sib = leftOf(parentOf(x));
                    sib = leftOf(parentOf(node));
                }
                /**
                 * 如果sib的两个子结点都是黑色
                 */
                //if (colorOf(rightOf(sib)) == BLACK &&
                if (colorOf(rightOf(sib)) == RBTreeNode.BLACK &&
                        //colorOf(leftOf(sib)) == BLACK) {
                        colorOf(leftOf(sib)) == RBTreeNode.BLACK) {
                    /**
                     * 把sib的颜色设为红色
                     */
                    //setColor(sib, RED);
                    setColor(sib, RBTreeNode.RED);
                    /**
                     * node指向node的父结点
                     */
                    //x = parentOf(x);
                    node = parentOf(node);
                } else {
                    /**
                     * 如果sib的左子结点是黑色
                     */
                    //if (colorOf(leftOf(sib)) == BLACK) {
                    if (colorOf(leftOf(sib)) == RBTreeNode.BLACK) {
                        /**
                         * 把sib的右子结点设为黑色
                         */
                        //setColor(rightOf(sib), BLACK);
                        setColor(rightOf(sib), RBTreeNode.BLACK);
                        /**
                         * 把sib的颜色设为红色
                         */
                        //setColor(sib, RED);
                        setColor(sib, RBTreeNode.RED);
                        /**
                         * 围绕sib进行左旋转
                         */
                        rotateLeft(sib);
                        /**
                         * sib指向node父结点的左子结点
                         */
                        //sib = leftOf(parentOf(x));
                        sib = leftOf(parentOf(node));
                    }
                    /**
                     * 把sib的颜色设为node父结点的颜色
                     */
                    //setColor(sib, colorOf(parentOf(x)));
                    setColor(sib, colorOf(parentOf(node)));
                    /**
                     * 把node父结点的颜色设为黑色
                     */
                    //setColor(parentOf(x), BLACK);
                    setColor(parentOf(node), RBTreeNode.BLACK);
                    /**
                     * 把sib左子结点的颜色设为黑色
                     */
                    //setColor(leftOf(sib), BLACK);
                    setColor(leftOf(sib), RBTreeNode.BLACK);
                    /**
                     * 围绕node的父结点进行右旋转
                     */
                    //rotateRight(parentOf(x));
                    rotateRight(parentOf(node));
                    //x = root;
                    node = rootNode;
                }
            }
        }

        //setColor(x, BLACK);
        setColor(node, RBTreeNode.BLACK);
    }
    public static void main(String []args){
        RBTree<String> r = new RBTree<String>();
        r.insertNode(new RBTreeNode<String>("1"));
        System.out.println(r.getRootNode().getValue());
        r.insertNode(new RBTreeNode<String>("2"));
        System.out.println(r.getRootNode().getValue());
        r.insertNode(new RBTreeNode<String>("3"));
        System.out.println(r.getRootNode().getValue());
        r.insertNode(new RBTreeNode<String>("4"));
        System.out.println(r.getRootNode().getValue());
        r.insertNode(new RBTreeNode<String>("5"));
        System.out.println(r.getRootNode().getValue());

        r.printTree(r.getRootNode(),r);


        r.insertNode(new RBTreeNode<String>("6"));
        System.out.println(r.getRootNode().getValue());
        r.insertNode(new RBTreeNode<String>("7"));
        System.out.println(r.getRootNode().getValue());
        r.insertNode(new RBTreeNode<String>("8"));
        System.out.println(r.getRootNode().getValue());
        r.insertNode(new RBTreeNode<String>("9"));
        System.out.println(r.getRootNode().getValue());
    }
    public void printTree(RBTreeNode node,RBTree tree){
        if(node !=null){
            System.out.println(node.getValue()+" color:"+node.getColor());
        }
        if(node.getLeftNode()!=null){
            System.out.print(node.getValue()+" left:");
            printTree(node.getLeftNode(),tree);
        }
        if(node.getRightNode()!=null){
            System.out.print(node.getValue()+" right:");
            printTree(node.getRightNode(),tree);
        }
    }
}
