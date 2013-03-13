package familytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Trees in the Java collection allow multiple children, but not multiple parents.
 * This custom built data-structure allows for that possibility, as well as
 * 'side links', in this case for partners. 
 * 
 * It's important to note that while the actual node is generic, the FamilyTree
 * structure is geared towards, well, family trees. It should be fairly trivial
 * to implement a generic tree if the need calls for it, though.
 * 
 * @author Andrew Brown.
 */
public class FamilyTree {

    //contains the nodes of the people in the tree.
    List<FamilyTreeNode<Person>> people;

    public FamilyTree() {
        people = new LinkedList<FamilyTreeNode<Person>>();
    }

    /**
     * Adds a new person to the family tree; or rather, unlinkedPeople. Returns
     * true if the operation was successful, or false if the person already exists.
     * @param aPerson Person to be added
     * @return
     */
    public boolean addPerson(Person aPerson) {

        boolean success = false;

        if ((personInTree(aPerson.getName(), aPerson.getDateOfBirth())) == -1) {

            FamilyTreeNode<Person> newPerson = new FamilyTreeNode<Person>(aPerson);
            people.add(newPerson);
            success = true;
        }

        return success;
    }

    /**
     * Links an individual to their mother. Both the individual and the
     * mother need already to appear as a Person in the family tree.
     * @param aPerson String holding individual's name.
     * @param aDOB String holding individual's date of birth.
     * @param mName String holding mother's name.
     * @param mDOB String holding mother's date of birth.
     */
    public boolean makeLinkToMother(String aPerson, String aDOB,
            String mName, String mDOB) {
        FamilyTreeNode<Person> child = getPerson(aPerson, aDOB);
        FamilyTreeNode<Person> mother = getPerson(mName, mDOB);

        if (child != null && mother != null) {
            if (hasMother(child.getItem().getName(), child.getItem().getDateOfBirth()) || mother.containsChildLink(child)) {
                return false;
            } else {
                mother.getItem().setIsMother(true);
                //create two way link between mother and child.
                child.addParentLink(mother);
                return true;
            }
        }
        return false;
    }

    /**
     * Links an individual to their father. Both the individual and the
     * father need already to appear as a Person in the family tree.
     * @param aPerson String holding individual's name.
     * @param aDOB String holding individual's date of birth.
     * @param fName String holding father's name.
     * @param fDOB String holding father's date of birth.
     */
    public boolean makeLinkToFather(String aPerson, String aDOB,
            String fName, String fDOB) {
        FamilyTreeNode<Person> child = getPerson(aPerson, aDOB);
        FamilyTreeNode<Person> father = getPerson(fName, fDOB);

        if (child != null && father != null) {
            if (hasFather(child.getItem().getName(), child.getItem().getDateOfBirth()) || father.containsChildLink(child)) {
                return false;
            } else {
                father.getItem().setIsFather(true);
                //create two way link between father and child.
                child.addParentLink(father);
                return true;
            }
        }
        return false;
    }

    /**
     * Links a newly married couple. Each member of the couple
     * needs already to appear as a Person in the family tree.
     * @param partner1Name String holding first partner's name.
     * @param aDOB1 String holding first partner's date of birth.
     * @param partner2Name String holding second partner's name.
     * @param aDOB2 String holding second partner's date of birth.
     */
    public boolean recordWedding(String partner1Name, String aDOB1,
            String partner2Name, String aDOB2) {
        FamilyTreeNode<Person> partner1 = getPerson(partner1Name, aDOB1);
        FamilyTreeNode<Person> partner2 = getPerson(partner2Name, aDOB2);

        if ((partner1 != null) && (partner2 != null)) {

            if (!partner1.sideLinksIsEmpty() || !partner2.sideLinksIsEmpty()) {
                return false;
            } else {
                partner1.getItem().setIsMarried(true);
                partner2.getItem().setIsMarried(true);

                //adds two way link between the partners.
                partner1.addSideLink(partner2);
            }
        }
        return false;
    }

    /**
     * Records a divorce. Each member of the couple
     * needs already to appear as a Person in the family tree.
     * @param partner1Name String first partner's name.
     * @param aDOB1 String holding first partner's date of birth.
     * @param partner2Name String holding second partner's name.
     * @param aDOB2 String holding second partner's date of birth.
     */
    public boolean recordDivorce(String partner1Name, String aDOB1,
            String partner2Name, String aDOB2) {
        FamilyTreeNode<Person> partner1 = getPerson(partner1Name, aDOB1);
        FamilyTreeNode<Person> partner2 = getPerson(partner2Name, aDOB2);

        if ((partner1 != null) && (partner2 != null)) {
            if (!partner1.containsSideLink(partner2) || !partner2.containsSideLink(partner1)) {
                return false;
            } else {
                partner1.getItem().setIsMarried(false);
                partner1.getItem().setIsDivorced(true);
                partner2.getItem().setIsMarried(false);
                partner2.getItem().setIsDivorced(true);

                partner1.removeSideLink(partner2);
            }
        }
        return false;
    }

    /**
     * List the details of the person whose name is given. Note you need to do
     * something about the possibility of duplicate names appearing.
     * 
     * This lists the person's details, and, if present, their parents, siblings,
     * and children.
     * 
     * possibly list parents, children, etc?
     * @param personName
     * @param aDOB
     */
    public String listPersonDetails(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            details += person.getItem().toString() + "\n";
            details += this.listParentDetails(personName, aDOB);
            details += this.listSiblings(personName, aDOB);
            if (!person.sideLinksIsEmpty() && person.getItem().isMarried()) {
                details += personName + "'s partner:\n";
                details += "Partner: " + person.getSideLinks().get(0).getItem().toString() + "\n";
            }
            details += this.listChildren(personName, aDOB);
        } else {
            details = personName + " isn't in the tree.\n";
        }
        return details;
    }

    /**
     * TODO: step-parents.
     * List the details of the parents of the person whose name is given. Note
     * you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public String listParentDetails(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            if (!person.parentLinksIsEmpty()) {
                if (person.getItem().isAdopted()) {
                    details += personName + "'s adoptive parents:\n";
                } else {
                    details += personName + "'s parents:\n";
                }

                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    if (parent.getItem().isMother()) {
                        details += "Mother: ";
                        details += parent.getItem().toString() + "\n";
                    } else {
                        details += "Father: ";
                        details += parent.getItem().toString() + "\n";
                    }
                    if (hasPartner(parent.getItem().getName(), parent.getItem().getDateOfBirth())) {
                        details += "Stepparent: ";
                        details += parent.getSideLinks().get(0).getItem().toString() + "\n";
                    }
                }
            } else {
                details += personName + " doesn't have any parents listed.\n";
            }
        } else {
            details = personName + " isn't in the tree.\n";
        }
        return details;
    }

    /**
     * List the details of the children of the person whose name is given, along
     * with their stepchildren, if any.
     * @param personName
     * @param aDOB
     */
    public String listChildren(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {

            if (!person.childLinksIsEmpty()) {

                details += personName + "'s children:\n";

                for (FamilyTreeNode<Person> child : person.getChildLinks()) {
                    if (child.getItem().isAdopted()) {
                        details += "Adopted Child: ";
                    } else {
                        details += "Child: ";
                    }
                    details += child.getItem().toString() + "\n";
                }
            }
            if (hasPartner(personName, aDOB)) {
                FamilyTreeNode<Person> partner = person.getSideLinks().get(0);

                for (FamilyTreeNode<Person> child : partner.getChildLinks()) {
                    if (!person.containsChildLink(child)) {
                        details += "Stepchild: ";
                        details += child.getItem().toString() + "\n";
                    }
                }
            }
            if (details.isEmpty()) {
                details += personName + " has no children on record.\n";
            }
        } else {
            details = personName + " isn't in the tree.\n";
        }
        return details;
    }

    /**
     * TODO: step-siblings, half-siblings.
     * List the details of the siblings of the person whose name is given. Note
     * you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public String listSiblings(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            if (!person.parentLinksIsEmpty()) {
                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    for (FamilyTreeNode<Person> sibling : parent.getChildLinks()) {
                        if (!sibling.equals(person)) {
                            if (details.isEmpty()) {
                                details += personName + "'s siblings:\n";
                            }
                            if (!details.contains(sibling.getItem().toString())) {
                                if (sibling.getItem().isAdopted()) {
                                    details += "Adopted Sibling: ";
                                } else {
                                    details += "Sibling: ";
                                }
                                details += sibling.getItem().toString() + "\n";
                            }
                        }
                    }
                }
                if (details.isEmpty()) {
                    details += personName + " has no siblings listed.\n";
                }
            } else {
                details += personName + " has no parents listed. Thus, we cannnot find any siblings.\n";
            }

        } else {
            details += personName + " is not in the tree.\n";
        }
        return details;
    }

    /**
     * List the details of the ancestors along the paternal line  of the person
     * whose name is given. Note you need to do something about the possibility
     * of duplicate names appearing.
     * @param personName
     * @param aDOB
     */
    public String listPaternalLineage(String personName, String aDOB) {
        String details = "";
        int numGens = 0;
        FamilyTreeNode<Person> person;


        if ((person = getPerson(personName, aDOB)) != null) {
            while (!person.parentLinksIsEmpty()) {
                if (details.isEmpty()) {
                    if (person.getItem().isAdopted()) {
                        details += personName + "'s adoptive paternal lineage:\n";
                    } else {
                        details += personName + "'s paternal lineage:\n";
                    }
                }
                for (FamilyTreeNode<Person> father : person.getParentLinks()) {
                    if (father.getItem().isFather()) {
                        numGens += 1;
                        person = father;
                        break;
                    }
                }
                details += fixGenerations(numGens) + "father: " + person.getItem().toString() + "\n";
            }
            if (details.isEmpty()) {
                details += personName + " does not have a father listed. Therefore, we could not find a paternal lineage.\n";
            }
        } else {
            details += personName + " is not in the tree.\n";
        }
        return details;
    }

    /**
     * List the details of the ancestors along the maternal line  of the person
     * whose name is given. Note you need to do something about the possibility
     * of duplicate names appearing.
     * @param personName
     * @param aDOB
     */
    public String listMaternalLineage(String personName, String aDOB) {
        String details = "";
        int numGens = 0;
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            while (!person.parentLinksIsEmpty()) {
                if (details.isEmpty()) {
                    if (person.getItem().isAdopted()) {
                        details += personName + "'s adoptive maternal lineage (Order: Mother, Grandmother, Great Grandmother, etc):\n";
                    } else {
                        details += personName + "'s maternal lineage (Order: Father, Grandfather, Great Grandfather, etc):\n";
                    }
                }
                for (FamilyTreeNode<Person> mother : person.getParentLinks()) {
                    if (mother.getItem().isMother()) {
                        numGens += 1;
                        person = mother;
                        break;
                    }
                }
                details += fixGenerations(numGens) + "mother: " + person.getItem().toString() + "\n";
            }
            if (details.isEmpty()) {
                details += personName + " does not have a mother listed. Therefore, we could not find a maternal lineage.\n";
            }
        } else {
            details += personName + " is not in the tree.\n";
        }
        return details;
    }

    /**
     * List the details of the grandparents of the person
     * whose name is given. Note you need to do something about the possibility
     * of duplicate names appearing.
     * @param personName
     * @param aDOB
     */
    public String listGrandParents(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            if (!person.parentLinksIsEmpty()) {
                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    for (FamilyTreeNode<Person> grandparent : parent.getParentLinks()) {
                        if (details.isEmpty()) {
                            if (person.getItem().isAdopted()) {
                                details += personName + "'s Adoptive grandparents:\n";
                            } else {
                                details += personName + "'s Grandparents:\n";
                            }
                        }
                        if (grandparent.getItem().isMother()) {
                            details += "Grandmother: ";
                        } else {
                            details += "Grandfather: ";
                        }
                        details += grandparent.getItem().toString() + "\n";
                    }
                }
            }
            if (details.isEmpty()) {
                details += personName + " doesn't have any Grandparents listed.";
            }
        } else {
            details = personName + " isn't in the tree.\n";
        }
        return details;
    }

    /**
     * List the details of the grandchildren of the person whose name is given.
     * Note you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public String listGrandChildren(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            if (!person.childLinksIsEmpty()) {

                for (FamilyTreeNode<Person> child : person.getChildLinks()) {
                    for (FamilyTreeNode<Person> grandchild : child.getChildLinks()) {
                        if (details.isEmpty()) {
                            details += personName + "'s Grandchildren:\n";
                        }
                        if (grandchild.getItem().isAdopted()) {
                            details += "Adopted Grandchild: ";
                        } else {
                            details += "Grandchild: ";
                        }
                        details += grandchild.getItem().toString() + "\n";
                    }
                }
            }
            if (details.isEmpty()) {
                details += personName + " doesn't have any Grandchildren.\n";
            }
        } else {
            details = personName + " isn't in the tree.\n";
        }
        return details;


    }

    /**
     * List the details of the cousins of the person whose name is given.
     * 
     * @param personName
     * @param aDOB
     */
    public String listCousins(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            if (!person.parentLinksIsEmpty()) {
                /* This looks pretty complex, but it's fairly simple once explained: We find
                 * the desired person's grandparents, and then find the grandparent's children.
                 * If the child in question isn't one of our person's parents, then it
                 * stands to reason that their children are our person's cousins.
                 */
                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    for (FamilyTreeNode<Person> grandparent : parent.getParentLinks()) {
                        for (FamilyTreeNode<Person> parentSibling : grandparent.getChildLinks()) {
                            if (!parentSibling.equals(parent)) {
                                for (FamilyTreeNode<Person> cousin : parentSibling.getChildLinks()) {
                                    if (details.isEmpty()) {
                                        details += personName + "'s cousins:\n";
                                    }
                                    if (!details.contains(cousin.getItem().toString())) {
                                        details += "Cousin: ";
                                        details += cousin.getItem().toString() + "\n";
                                    }
                                }
                            }
                        }
                    }
                }
                if (details.isEmpty()) {
                    details += personName + " has no cousins listed.\n";
                }
            } else {
                details += personName + " has no parents listed. Thus, we cannnot find any cousins.\n";
            }
        } else {
            details += personName + " is not in the tree.\n";
        }
        return details;

    }

    /**
     * List the details of the N generations of ancestors of the person whose
     * name is given. 
     * 
     * @param personName
     * @param aDOB
     * @param numberOfGenerations 1=parents,2=grandparents,
     *                            3=great-grandparents etc.
     */
    public String listGreatNGrandParents(String personName, String aDOB, int numberOfGenerations) {
        String details = "";
        FamilyTreeNode<Person> person;
        ArrayList<FamilyTreeNode<Person>> currentAncestors = new ArrayList<FamilyTreeNode<Person>>();
        ArrayList<FamilyTreeNode<Person>> nextAncestors = new ArrayList<FamilyTreeNode<Person>>();

        if (numberOfGenerations > 0) {
            if ((person = getPerson(personName, aDOB)) != null) {
                currentAncestors.add(person);
                for (int i = numberOfGenerations; i > 0; i--) {
                    for (FamilyTreeNode<Person> currentAncestor : currentAncestors) {
                        for (FamilyTreeNode<Person> nextAncestor : currentAncestor.getParentLinks()) {
                            nextAncestors.add(nextAncestor);
                        }
                    }
                    currentAncestors.clear();
                    currentAncestors.addAll(nextAncestors);
                    nextAncestors.clear();
                }
                if (currentAncestors.isEmpty()) {
                    details += personName + " doesn't have any " + fixGenerations(numberOfGenerations).toLowerCase() + "parents.\n";
                } else {
                    details += personName + "'s " + fixGenerations(numberOfGenerations).toLowerCase() + "parents:\n";
                }
                for (FamilyTreeNode<Person> currentAncestor : currentAncestors) {
                    if (currentAncestor.getItem().isMother()) {
                        details += fixGenerations(numberOfGenerations) + "mother: ";
                    } else {
                        details += fixGenerations(numberOfGenerations) + "father: ";
                    }
                    details += currentAncestor.getItem().toString() + "\n";
                }
            } else {
                details += personName + " is not in tree.\n";
            }
        } else {
            details += "Please enter a positive, non-zero integer for generation.\n";
        }
        return details;
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
    public String listGreatNGrandChildren(String personName, String aDOB, int numberOfGenerations) {
        String details = "";
        FamilyTreeNode<Person> person;
        ArrayList<FamilyTreeNode<Person>> currentChildren = new ArrayList<FamilyTreeNode<Person>>();
        ArrayList<FamilyTreeNode<Person>> nextChildren = new ArrayList<FamilyTreeNode<Person>>();

        if (numberOfGenerations > 0) {
            if ((person = getPerson(personName, aDOB)) != null) {
                currentChildren.add(person);
                for (int i = numberOfGenerations; i > 0; i--) {
                    for (FamilyTreeNode<Person> currentChild : currentChildren) {
                        for (FamilyTreeNode<Person> nextChild : currentChild.getChildLinks()) {
                            nextChildren.add(nextChild);
                        }
                    }
                    currentChildren.clear();
                    currentChildren.addAll(nextChildren);
                    nextChildren.clear();
                }
                if (currentChildren.isEmpty()) {
                    details += personName + " doesn't have any " + fixGenerations(numberOfGenerations).toLowerCase() + "children.\n";
                } else {
                    details += personName + "'s " + fixGenerations(numberOfGenerations).toLowerCase() + "children:\n";
                }
                for (FamilyTreeNode<Person> currentChild : currentChildren) {
                    details += fixGenerations(numberOfGenerations) + "child: ";
                    details += currentChild.getItem().toString() + "\n";
                }
            } else {
                details += personName + " is not in tree.\n";
            }
        } else {
            details += "Please enter a positive, non-zero integer for generation.\n";
        }
        return details;
    }

    /**
     * Records an adoption. This method assumes that the child already has a link 
     * to their (adoptive) parents, and modifies the child so that they are marked
     * as adopted.
     * 
     * @param personName
     * @param aDOB
     */
    public boolean recordAdoption(String personName, String aDOB) {
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            person.getItem().setIsAdopted(true);
            return true;
        }
        return false;
    }

    /**
     * Returns true if the person specified has a mother.
     * 
     * @param aName
     * @param aDOB
     * @return 
     */
    public boolean hasMother(String aName, String aDOB) {
        FamilyTreeNode<Person> person;

        if ((person = getPerson(aName, aDOB)) != null) {

            for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                if (parent.getItem().isMother()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the person specified has a father.
     * 
     * @param aName
     * @param aDOB
     * @return 
     */
    public boolean hasFather(String aName, String aDOB) {
        FamilyTreeNode<Person> person;

        if ((person = getPerson(aName, aDOB)) != null) {

            for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                if (parent.getItem().isFather()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if the person specified has a father.
     * 
     * @param aName
     * @param aDOB
     * @return 
     */
    public boolean hasPartner(String aName, String aDOB) {
        FamilyTreeNode<Person> person;

        if ((person = getPerson(aName, aDOB)) != null) {

            if (person.getItem().isMarried()) {
                return true;
            }
        }
        return false;
    }

    /**
     * If the specified person is in the tree, returns their index. If not,
     * returns -1. Used in the 'getPerson' method to return the correct person.
     * 
     * @param aName
     * @param aDOB
     * @return 
     */
    private int personInTree(String aName, String aDOB) {

        Person comparedPerson = new Person(aName, aDOB, "");
        int index = -1;

        for (FamilyTreeNode<Person> node : people) {
            index += 1;
            if (node.getItem().equals(comparedPerson)) {
                return index;
            }

        }
        return -1;
    }

    /**
     * If the specified person exists, returns the Node containing the person.
     * otherwise returns null.
     * @param aName
     * @param aDOB
     * @return 
     */
    private FamilyTreeNode<Person> getPerson(String aName, String aDOB) {
        int index;
        FamilyTreeNode<Person> person = null;

        if ((index = personInTree(aName, aDOB)) != -1) {
            person = people.get(index);
        }

        return person;
    }

    /**
     * Given an int, fixGenerations() will return the appropriate generation
     * modifier, like Grand, Great grand, etc.
     * 
     * @param numGens
     * @return 
     */
    private String fixGenerations(int numGens) {
        String generations = "";
        if (numGens > 1) {
            if (numGens == 2) {
                generations = "Grand";
            } else {
                for (int grandGens = numGens - 2; grandGens > 0; grandGens--) {
                    if (generations.isEmpty()) {
                        generations += "Great ";
                    } else {
                        generations += "great ";
                    }
                }
                generations += "grand";
            }
        }
        return generations;
    }
}