package familytree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew Brown
 * 
 * Node for my tree implementation. Since the tree should have three 'link points'
 * which can have multiple links, I use the Java Collection's ArrayList to hold
 * the linked Nodes. Note that sideLinks is also a list; although the family
 * tree only needs one side link  (for life partners), it makes sense for a 
 * generic implementation of this tree/node pair to have the array. We might
 * need to make a application that requires multiple sideLinks.
 * 
 */
public class TreeNode<LI> implements Comparable<LI> {

    private LI item;
    private List<TreeNode> parentLinks;
    private List<TreeNode> childLinks;
    private List<TreeNode> sideLinks;

    //create an empty node. 
    public TreeNode() {
        parentLinks = new ArrayList<TreeNode>();
        childLinks = new ArrayList<TreeNode>();
        sideLinks = new ArrayList<TreeNode>();
    }

    //create a node that just contains an item.
    public TreeNode(LI item) {
        this.item = item;
        parentLinks = new ArrayList<TreeNode>();
        childLinks = new ArrayList<TreeNode>();
        sideLinks = new ArrayList<TreeNode>();
    }

    //a node which contains an item, and also a parent node.
    public TreeNode(LI item, TreeNode<LI> parentNode) {
        this.item = item;
        parentLinks = new ArrayList<TreeNode>();
        childLinks = new ArrayList<TreeNode>();
        sideLinks = new ArrayList<TreeNode>();

        //make a two way link between parentNode and this node.
        parentLinks.add(parentNode);
        parentNode.addChildLink(this);
    }

    //if the items to be compared can be represented by a string, compare them
    //based on the 'String' object's 'compare' method. Case is ignored.
    //Returns -1 if this Node's item is less than the given one
    //returns 0 if the items are the same.
    //Returns 1 if this Node's item is greater than the given one.
    public int compareTo(LI item) {
        int returnVal = 999;
        String string1, string2;
        if (item instanceof Integer) {
            returnVal = compareInts(item);
        } else {
            string1 = this.item.toString();
            string2 = item.toString();
            returnVal = string1.compareToIgnoreCase(string2);
        }
        return returnVal;
    }

    //If the items in question are integers, compare them numerically.
    public int compareInts(LI item) {
        int retVal = 999;
        Integer item1, item2;
        if ((this.item instanceof Integer)
                && (item instanceof Integer)) {
            item1 = (Integer) this.item;
            item2 = (Integer) item;
            retVal = item1.compareTo(item2);
        }
        return retVal;
    }

    //Sometimes, you just want to see if two items are equal.
    public boolean itemEquals(LI item) {
        return this.item.equals(item);
    }

    public LI getItem() {
        return item;
    }

    public void setItem(LI item) {
        this.item = item;
    }

    /**
     * 
     * @param parent
     * @return 
     * 
     * Adds a two-way link between a child and a parent, with the child
     * node being the node that this method is called on. Note that both
     * addParentLink and addChildLink both have the same result (a link
     * between parent and child), so the method you chose to use is really 
     * a matter of situational convenience. 
     */
    public int addParentLink(TreeNode<LI> parent) {
        
        if (!parent.containsChildLink(this)) this.parentLinks.add(parent);
        parent.addChildLink(this);
        return 1;
    }


    /**
     * 
     * @param child
     * @return 
     * 
     * Adds a two-way link between a parent and a child, with the parent
     * node being the node that this method is called on. Note that both
     * addParentLink and addChildLink both have the same result (a link
     * between parent and child), so the method you chose to use is really 
     * a matter of situational convenience. 
     */
    public int addChildLink(TreeNode<LI> child) {
        
        if(!child.containsParentLink(this)) this.childLinks.add(child);

        child.addParentLink(this);
        return 1;
    }

    /**
     * 
     * @param side
     * @return
     * 
     * Adds a two-way side link between two nodes. You might want to use this
     * to connect two nodes you consider to be equal in precedence.
     */
    public int addSideLink(TreeNode<LI> side) {
        return 1;
    }

    public boolean containsParentLink(TreeNode<LI> parent) {
        boolean contains = false;
        
        if(this.parentLinks.contains(parent)) contains = true;

        return contains;
    }

    public boolean containsChildLink(TreeNode<LI> child) {
        boolean contains = false;

        if(this.childLinks.contains(child)) contains = true;
        
        return contains;
    }

    public boolean containsSideLink(TreeNode<LI> side) {
        boolean contains = false;
        
        if(this.sideLinks.contains(side)) contains = true;

        return contains;
    }
} //end of 'Node'