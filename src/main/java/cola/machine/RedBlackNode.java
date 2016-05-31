package cola.machine;

/**
 * Created with IntelliJ IDEA.
 * User: qarkly
 * Date: 14-6-8
 * Time: 下午11:16
 * To change this template use File | Settings | File Templates.
 */
public class RedBlackNode {
    public static final byte RED = 0x00;
    public static final byte BLACK = 0x01;
    public static RedBlackNode NILL = new RedBlackNode(BLACK);

    RedBlackNode parent;
    RedBlackNode left;
    RedBlackNode right;
    Comparable element;
    byte color;

    public RedBlackNode(RedBlackNode left,RedBlackNode right,Comparable element){
        this.left = left;
        this.right = right;
        this.element = element;
        color = RED;
    }

    public RedBlackNode(byte color){
        this.color = color;
    }

    public RedBlackNode(Comparable element,byte color){
        this.color = color;
        this.element = element;
        this.left = NILL;
        this.right = NILL;
    }


}