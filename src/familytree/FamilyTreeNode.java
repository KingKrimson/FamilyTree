package familytree;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew Brown
 *
 * The node for my family tree implementation. Since each node in the  family tree 
 * should have three 'link points' which can have multiple links, I use the 
 * Java Collection's ArrayList to hold the linked Nodes. Note that sideLinks is 
 * also a list; although the family tree only needs one side link (for life partners), 
 * it makes sense for a generic implementation of this tree/node pair to have 
 * the array; in the future, we might need to make a application that requires 
 * multiple sideLinks.
 */
public class FamilyTreeNode<LI> {

    private LI item; //The actual item contained in the node.
    //Lists for each type of link. Allows for multiple parent links, which
    //the trees in the Java Collection API don't.
    private List<FamilyTreeNode<LI>> parentLinks;
    private List<FamilyTreeNode<LI>> childLinks;
    private List<FamilyTreeNode<LI>> sideLinks;

    /**
     * create an empty TreeNode.
     */
    public FamilyTreeNode() {

        parentLinks = new ArrayList<FamilyTreeNode<LI>>();
        childLinks = new ArrayList<FamilyTreeNode<LI>>();
        sideLinks = new ArrayList<FamilyTreeNode<LI>>();
    }

    /**
     * create a node that contains the item specified by the parameter.
     * 
     * @param item 
     */
    public FamilyTreeNode(LI item) {

        this.item = item;
        parentLinks = new ArrayList<FamilyTreeNode<LI>>();
        childLinks = new ArrayList<FamilyTreeNode<LI>>();
        sideLinks = new ArrayList<FamilyTreeNode<LI>>();
    }

    /**
     * Adds a two-way link between a child and a parent, with the child node
     * being the node that this method is called on. Note that both
     * addParentLink and addChildLink both have the same result (a link between
     * parent and child), so the method you chose to use is really a matter of
     * situational convenience.
     * @param parent
     * @return
     */
    public int addParentLink(FamilyTreeNode<LI> parent) {

        int status = 0;

        status += this.addOneWayParentLink(parent);
        status += parent.addOneWayChildLink(this);

        return status;
    }

    /**
     * Adds a one way link between a child and a parent, where the child is
     * the node that the link is made on. For example: child->parent. This method
     * is called by addChildLink in order to create a two-way connection.
     * You can use it too, if you want, but I don't see many uses for having a one way
     * link between child and parent.
     * 
     * @param child
     * @return 
     */
    public int addOneWayParentLink(FamilyTreeNode<LI> parent) {

        int status = 0;

        if (!this.containsParentLink(parent)) {
            this.parentLinks.add(parent);
        } else {
            status = 1;
        }

        return status;
    }

    /**
     * removes a two way parent/childLink between two linked nodes.
     * takes the node to be removed from this node as a parameter.
     * Uses the methods removeOneWayParentLink() and removeOneWayChildLink().
     * 
     * @param side
     * @return 
     */
    public int removeParentLink(FamilyTreeNode<LI> parent) {

        int status = 0;

        status += this.removeOneWayParentLink(parent);
        status += parent.removeOneWayChildLink(this);

        return status;
    }
    /**
     * removes a one way parentLink between two nodes.
     * removes the parent node specified in the parameters from
     * this node.
     * 
     * @param side
     * @return 
     */
    
    public int removeOneWayParentLink(FamilyTreeNode<LI> parent) {

        int status = 0;

        if (this.containsParentLink(parent)) {
            this.parentLinks.remove(parent);
        } else {
            status = 1;
        }
        return status;
    }

    /**
     * Adds a two-way link between a parent and a child, with the parent node
     * being the node that this method is called on. Note that both
     * addParentLink and addChildLink both have the same result (a link between
     * parent and child), so the method you chose to use is really a matter of
     * situational convenience.
     * @param child
     * @return
     */
    public int addChildLink(FamilyTreeNode<LI> child) {

        int status = 0;

        status += this.addOneWayChildLink(child);
        status += child.addOneWayParentLink(this);

        return status;
    }

    /**
     * Adds a one way link between a parent and a child, where the parent is
     * the node that the link is made on. For example: parent->child. This method
     * is called by addChildLink in order to create a two-way connection.
     * You can use it too, if you want.
     * 
     * @param child
     * @return 
     */
    public int addOneWayChildLink(FamilyTreeNode<LI> child) {

        int status = 0;

        if (!this.containsChildLink(child)) {
            this.childLinks.add(child);
        } else {
            status = 1;
        }

        return status;
    }

    /**
     * removes a two way child/parentLink between two linked nodes.
     * takes the node to be removed from this node as a parameter.
     * Uses the methods removeOneWayChildLink() and removeOneWayParentLink().
     * 
     * @param side
     * @return 
     */
    public int removeChildLink(FamilyTreeNode<LI> child) {

        int status = 0;

        status += this.removeOneWayChildLink(child);
        status += child.removeOneWayParentLink(this);

        return status;
    }

    /**
     * removes a one way childLink between two nodes.
     * removes the child node specified in the parameters from
     * this node.
     * 
     * @param side
     * @return 
     */
    public int removeOneWayChildLink(FamilyTreeNode<LI> child) {

        int status = 0;

        if (this.containsChildLink(child)) {
            childLinks.remove(child);
        } else {
            status = 1;
        }

        return status;
    }

    /**
     * Adds a two-way side link between two nodes. You might want to use this to
     * connect two nodes you consider to be equal in precedence.
     * @param side
     * @return
     */
    public int addSideLink(FamilyTreeNode<LI> side) {
        int status = 0;

        this.addOneWaySideLink(side);
        side.addOneWaySideLink(this);

        return status;
    }

    /**
     * Adds a one way link between two side nodes, where the node the method is
     * called on is the node in which the link is made on. For example: 
     * side1->side2. This method is called by addSideLink in order to create a 
     * two-way connection. You can use it too, if you want.
     * @param child
     * @return 
     */
    public int addOneWaySideLink(FamilyTreeNode<LI> side) {
        int status = 0;

        if (!this.containsSideLink(side)) {
            this.sideLinks.add(side);
        } else {
            status = 1;
        }

        return status;
    }

    /**
     * removes a two way sideLink between two linked nodes.
     * takes the node to be removed from this node as a parameter.
     * Uses the method removeOneWaySideLink().
     * 
     * @param side
     * @return 
     */
    public int removeSideLink(FamilyTreeNode<LI> side) {

        int status = 0;

        status += this.removeOneWaySideLink(side);
        status += side.removeOneWaySideLink(this);

        return status;
    }

    /**
     * removes a one way sideLink between two nodes.
     * removes the node specified in the parameters from
     * this node.
     * 
     * @param side
     * @return 
     */
    public int removeOneWaySideLink(FamilyTreeNode<LI> side) {

        int status = 0;

        if (this.containsSideLink(side)) {
            sideLinks.remove(side);
        } else {
            status = 1;
        }
        return status;
    }

    /**
     * Returns true if the node already has 'parent' in parentLinks. False otherwise.
     * @param parent
     * @return 
     */
    public boolean containsParentLink(FamilyTreeNode<LI> parent) {
        boolean contains = false;

        if (this.parentLinks.contains(parent)) {
            contains = true;
        }

        return contains;
    }

    /**
     * Returns true if the node already has 'child' in childLinks. False otherwise.
     * @param child
     * @return 
     */
    public boolean containsChildLink(FamilyTreeNode<LI> child) {
        boolean contains = false;

        if (this.childLinks.contains(child)) {
            contains = true;
        }

        return contains;
    }

    /**
     * Returns true if the node already has 'side' in sideLinks. False otherwise.
     * @param side
     * @return 
     */
    public boolean containsSideLink(FamilyTreeNode<LI> side) {
        boolean contains = false;

        if (this.sideLinks.contains(side)) {
            contains = true;
        }

        return contains;
    }

    /**
     * Returns true if sideLinks is empty.
     * 
     * @return 
     */
    public boolean sideLinksIsEmpty() {
        if (sideLinks.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if childLinks is empty.
     * 
     * @return 
     */
    public boolean childLinksIsEmpty() {
        if (childLinks.isEmpty()) {
            return true;
        }
        return false;
    }
    
    /**
     * Returns true if parentLinks is empty.
     * 
     * @return 
     */
    public boolean parentLinksIsEmpty() {
        if (parentLinks.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * doesn't compare the nodes themselves; compares the actual items.
     * Returns true if the items are equal, and false if not.
     * @param item
     * @return 
     */
    public boolean equals(FamilyTreeNode comparedNode) {
        return this.item.equals(comparedNode.item);
    }

    /**
     * Gets the item in the node. Pretty standard!
     * @return 
     */
    public LI getItem() {
        return item;
    }

    /**
     * Set the item in the node.
     * @param item 
     */
    public void setItem(LI item) {
        this.item = item;
    }

    /**
     * Returns the childLinks list.
     * @return 
     */
    public List<FamilyTreeNode<LI>> getChildLinks() {
        return childLinks;
    }

    /**
     * Set the childLinks List. Takes another List as a parameter.
     * @param childLinks 
     */
    public void setChildLinks(List<FamilyTreeNode<LI>> childLinks) {
        this.childLinks = childLinks;
    }

    /**
     * Return the parentLinks list.
     * @return 
     */
    public List<FamilyTreeNode<LI>> getParentLinks() {
        return parentLinks;
    }

    /**
     * Set the parentLinks list. Takes another List as a parameter.
     * @param parentLinks 
     */
    public void setParentLinks(List<FamilyTreeNode<LI>> parentLinks) {
        this.parentLinks = parentLinks;
    }

    /**
     * Return the sideLinks list.
     * @return 
     */
    public List<FamilyTreeNode<LI>> getSideLinks() {
        return sideLinks;
    }

    /**
     * Set the sideLinks list. Takes another list as a parameter.
     * @param sideLinks 
     */
    public void setSideLinks(List<FamilyTreeNode<LI>> sideLinks) {
        this.sideLinks = sideLinks;
    }
} //end of 'Node'