package cola.machine;

public class RBTreeNode<E> implements Comparable<RBTreeNode<E>>{
    static boolean RED = true;
    static boolean BLACK = false;
    private E value;
    //父结点
    private RBTreeNode<E> parentNode;
    //左子结点
    private RBTreeNode<E> leftNode;
    //右子结点
    private RBTreeNode<E> rightNode;
    //结点的颜色，默认颜色为黑色
    private boolean color;
    //构造方法
    public RBTreeNode(){
        this.value = null;
        this.parentNode = null;
        this.leftNode = null;
        this.rightNode = null;
        this.color = RBTreeNode.BLACK;
    }
    public RBTreeNode(E value){
        this.value = value;
        this.parentNode = null;
        this.leftNode = null;
        this.rightNode = null;
        this.color = RBTreeNode.BLACK;
    }
    public RBTreeNode(E value,boolean color){
        this.value = value;
        this.parentNode = null;
        this.leftNode = null;
        this.rightNode = null;
        this.color = color;
    }
    public RBTreeNode(E value,RBTreeNode<E>parentNode){
        this.value = value;
        this.parentNode = parentNode;
        this.leftNode = null;
        this.rightNode = null;
        this.color = RBTreeNode.BLACK;
    }
    public RBTreeNode(E value,RBTreeNode<E>parentNode,boolean color){
        this.value = value;
        this.parentNode = parentNode;
        this.leftNode = null;
        this.rightNode = null;
        this.color = color;
    }
    //构造方法结束
    //get set方法
    public E getValue(){
        return this.value;
    }
    public void setValue(E value){
        this.value = value;
    }
    public RBTreeNode<E> getParentNode(){
        return this.parentNode;
    }
    public void setParentNode(RBTreeNode<E>parentNode){
        this.parentNode = parentNode;
    }
    public RBTreeNode<E> getLeftNode(){
        return this.leftNode;
    }
    public void setLeftNode(RBTreeNode<E>leftNode){
        this.leftNode = leftNode;
    }
    public RBTreeNode<E> getRightNode(){
        return this.rightNode;
    }
    public void setRightNode(RBTreeNode<E>rightNode){
        this.rightNode = rightNode;
    }
    public boolean getColor(){
        return this.color;
    }
    public void setColor(boolean color){
        this.color = color;
    }
    //get set方法结束
    public boolean equals(Object o){
        if(o instanceof RBTreeNode){
            RBTreeNode<E> node = (RBTreeNode<E>)o;
            if(this.compareTo(node) == 0)
                return true;
            return false;
        }else{
            return false;
        }
    }
    public int hashCode(){
        if(this.value == null) return 0;
        return String.valueOf(value).hashCode();
    }
    public int compareTo(RBTreeNode<E>node){
        E obj = node.getValue();
        if(value instanceof String){
            return ((String)value).compareTo((String)obj);
        }else if(value instanceof Character){
            return (String.valueOf(value)).compareTo(String.valueOf(obj));
        }else if((value instanceof Byte)){
            if(((Byte)value)>((Byte)obj)){
                return 1;
            }else if(((Byte)value) == ((Byte)obj)){
                return 0;
            }else{
                return -1;
            }
        }else if(value instanceof Short){
            if(((Short)value)>((Short)obj)){
                return 1;
            }else if(((Short)value) == ((Short)obj)){
                return 0;
            }else{
                return -1;
            }
        }else if(value instanceof Integer){
            if(((Integer)value)>((Integer)obj)){
                return 1;
            }else if(((Integer)value) == ((Integer)obj)){
                return 0;
            }else{
                return -1;
            }
        }else if(value instanceof Float){
            if(((Float)value)>((Float)obj)){
                return 1;
            }else if(((Float)value) == ((Float)obj)){
                return 0;
            }else{
                return -1;
            }
        }else if(value instanceof Long){
            if(((Long)value)>((Long)obj)){
                return 1;
            }else if(((Long)value) == ((Long)obj)){
                return 0;
            }else{
                return -1;
            }
        }else if(value instanceof Double){
            if(((Double)value)>((Double)obj)){
                return 1;
            }else if(((Double)value) == ((Double)obj)){
                return 0;
            }else{
                return -1;
            }
        }
        return 0;
    }

}