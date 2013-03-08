package familytree;

import java.util.LinkedList;
import java.util.List;

/**
 * Once complete this class manages a family tree.
 * You need to complete the methods whose headers are given here.
 * 
 * @author cjberry
 */
public class FamilyTree {
    //When added, people technically aren't part of a family tree,
    //so it doesn't make sense to have them hanging around on one;
    //we don't know where they fit in. Those people are held in this list.
    List unlinkedPeople;
    //Trees in the Java collection allow multiple children, but not multiple parents.
    //This custom built data-structure allows for that possibility, as well as
    //'side links', in this case for partners. 
    //FamilyTree familyTree;
    public FamilyTree() {
        unlinkedPeople = new LinkedList<Person>();
        //familyTree = new familyTree();
    }

    /**
     * Adds a person to the family tree
     * @param aPerson Person to be added
     */
    public void addPerson(Person aPerson) {
        unlinkedPeople.add(aPerson);
    }

    /**
     * Links an individual to their mother. Both the individual and the
     * mother need already to appear as a Person in the family tree.
     * @param aPerson String holding individual's name.
     * @param aDOB String holding individual's date of birth.
     * @param mName String holding mother's name.
     * @param mDOB String holding mother's date of birth.
     */
    public void makeLinkToMother(String aPerson, String aDOB,
            String mName, String mDOB) {
        //IF person not in tree, add to tree from list, and remove from list.
        //IF mother not in tree, add to tree from list, and remove from list.
        //ADD parent link to child and child link to parent.
    }

    /**
     * Links an individual to their father. Both the individual and the
     * father need already to appear as a Person in the family tree.
     * @param aPerson String holding individual's name.
     * @param aDOB String holding individual's date of birth.
     * @param fName String holding father's name.
     * @param fDOB String holding father's date of birth.
     */
    public void makeLinkToFather(String aPerson, String aDOB,
            String fName, String fDOB) {        
        //IF person not in tree, add to tree from list, and remove from list.
        //IF father not in tree, add to tree from list, and remove from list.
        //ADD parent link to child and child link to parent.
    }

    /**
     * Links a newly married couple. Each member of the couple
     * needs already to appear as a Person in the family tree.
     * @param partner1Name String holding first partner's name.
     * @param aDOB1 String holding first partner's date of birth.
     * @param partner2Name String holding second partner's name.
     * @param aDOB2 String holding second partner's date of birth.
     */
    public void recordWedding(String partner1Name, String aDOB1,
            String partner2Name, String aDOB2) {
    }

    /**
     * Records a divorce. Each member of the couple
     * needs already to appear as a Person in the family tree.
     * @param partner1Name String first partner's name.
     * @param aDOB1 String holding first partner's date of birth.
     * @param partner2Name String holding second partner's name.
     * @param aDOB2 String holding second partner's date of birth.
     */
    public void recordDivorce(String partner1Name, String aDOB1,
            String partner2Name, String aDOB2) {
    }

    /**
     * List the details of the person whose name is given. Note you need to do
     * something about the possibility of duplicate names appearing.
     * @param personName
     * @param aDOB
     */
    public void listPersonDetails(String personName, String aDOB) {
       //utelize person.toString. Can also list partner, parent, and kids.
    }

    /**
     * List the details of the parent of the person whose name is given. Note
     * you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public void listParentDetails(String personName, String aDOB) {
        //output more detailed info about their parents.
    }

    /**
     * List the details of the children of the person whose name is given. Note
     * you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public void listChildren(String personName, String aDOB) {
        //output details of the children.
    }

    /**
     * List the details of the siblings of the person whose name is given. Note
     * you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public void listSiblings(String personName, String aDOB) {
        //Follow parent links, then find parent's children. Special cases for
        //adoption and step-siblings?
    }

    /**
     * List the details of the ancestors along the paternal line  of the person
     * whose name is given. Note you need to do something about the possibility
     * of duplicate names appearing.
     * @param personName
     * @param aDOB
     */
    public void listPaternalLineage(String personName, String aDOB) {
        //all of them? I guess so.
    }

    /**
     * List the details of the ancestors along the maternal line  of the person
     * whose name is given. Note you need to do something about the possibility
     * of duplicate names appearing.
     * @param personName
     * @param aDOB
     */
    public void listMaternalLineage(String personName, String aDOB) {
    }

    /**
     * List the details of the grandparents of the person
     * whose name is given. Note you need to do something about the possibility
     * of duplicate names appearing.
     * @param personName
     * @param aDOB
     */
    public void listGrandParents(String personName, String aDOB) {
        //follow parents up, find their parents.
    }

    /**
     * List the details of the grandchildren of the person whose name is given.
     * Note you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public void listGrandChildren(String personName, String aDOB) {
    }

    /**
     * List the details of the cousins of the person whose name is given.
     * Note you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public void listCousins(String personName, String aDOB) {
    }

    /**
     * List the details of the N generations of ancestors of the person whose
     * name is given. Note you need to do something about the possibility of
     * duplicate names appearing.
     * @param personName
     * @param aDOB
     * @param numberOfGenerations 1=parents,2=grandparents,
     *                            3=great-grandparents etc.
     */
    public void listGreatNGrandParents(String personName, String aDOB, int numberOfGenerations) {
    }

    /**
     * List the details of the N generations of children of the person whose
     * name is given. Note you need to do something about the possibility of
     * duplicate names appearing.
     * @param personName
     * @param aDOB
     * @param numberOfGenerations 1=children,2=grandchildren,
     *                            3=great-grandchildren etc.
     */
    public void listGreatNGrandChildren(String personName, String aDOB, int numberOfGenerations) {
    }

    /**
     * Records an adoption.
     * @param personName
     * @param aDOB
     */
    public void recordAdoption(String personName, String aDOB) {
    }
}