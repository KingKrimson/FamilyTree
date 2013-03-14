package familytree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Trees in the Java collection API allow multiple children, but not multiple parents.
 * This custom built data-structure allows for that possibility, as well as
 * 'side links', in this case for partners. It also waits for the user to make
 * explicit links; the trees in the Java collection API make links when you
 * add them onto the tree, which isn't helpful when you have specific links
 * in mind.
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
     * Adds a new person to the family tree. Returns true if the operation was 
     * successful, or false if the person already exists.
     * 
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
     * mother need already to appear as a Person in the family tree. Returns true
     * if successful, false if not.
     * 
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
            //make sure that child doesn't already have a mother, and that there
            //isn't already a link.
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
            //make sure that child doesn't already have a father, and that there
            //isn't already a link.
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
        boolean success;
        FamilyTreeNode<Person> partner1 = getPerson(partner1Name, aDOB1);
        FamilyTreeNode<Person> partner2 = getPerson(partner2Name, aDOB2);

        if ((partner1 == null) || (partner2 == null)) {
            success = false;
        } else {
            //Make sure that neither person is already married.
            if (!partner1.sideLinksIsEmpty() || !partner2.sideLinksIsEmpty()) {
                success = false;
            } else {
                partner1.getItem().setIsMarried(true);
                partner2.getItem().setIsMarried(true);
                //adds two way link between the partners.
                partner1.addSideLink(partner2);
                success = true;
            }
        }
        return success;
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
        boolean success;
        FamilyTreeNode<Person> partner1 = getPerson(partner1Name, aDOB1);
        FamilyTreeNode<Person> partner2 = getPerson(partner2Name, aDOB2);

        if ((partner1 == null) || (partner2 == null)) {
            success = false;
        } else {
            //Make sure that the couple actually is married.
            if (!partner1.containsSideLink(partner2) || !partner2.containsSideLink(partner1)) {
                success = false;
            } else {
                partner1.getItem().setIsMarried(false);
                partner1.getItem().setIsDivorced(true);
                partner2.getItem().setIsMarried(false);
                partner2.getItem().setIsDivorced(true);
                //removes link between partners.
                partner1.removeSideLink(partner2);
                success = true;
            }
        }
        return success;
    }

    /**
     * This lists the person's name, date of birth and hometown. if present, 
     * it also prints their parents, siblings, partner, and children.
     * 
     * @param personName
     * @param aDOB
     */
    public String listPersonDetails(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            details += person.getItem().toString() + "\n";
            if (person.getItem().isAdopted()) {
                details += personName + " is adopted.\n";
            }
            if (person.getItem().isMarried()) {
                details += personName +  " is currently married to "
                        + person.getSideLinks().get(0).getItem().toString() + "\n";
            }
            if (person.getItem().isDivorced()) {
                details += "personName has had a divorce in the past.\n";
            }
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
     * List the details of the parents of the person whose name is given. Also
     * lists stepparents.
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
                //find blood parents.
                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    if (parent.getItem().isMother()) {
                        details += "Mother: ";
                        details += parent.getItem().toString() + "\n";
                    } else {
                        details += "Father: ";
                        details += parent.getItem().toString() + "\n";
                    }
                    //if the parent is married, and their partner is not also the 
                    //person's parent, then the partner is a stepparent.
                    //
                    if (!parent.sideLinksIsEmpty() && !person.containsParentLink(parent.getSideLinks().get(0))) {
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
     * Lists the details of the children of the person whose name is given, along
     * with their stepchildren, if any.
     * 
     * @param personName
     * @param aDOB
     */
    public String listChildren(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {

            if (!person.childLinksIsEmpty()) {
                details += personName + "'s children:\n";
                //find and list each child that the person has.
                for (FamilyTreeNode<Person> child : person.getChildLinks()) {
                    if (child.getItem().isAdopted()) {
                        details += "Adopted Child: ";
                    } else {
                        details += "Child: ";
                    }
                    details += child.getItem().toString() + "\n";
                }
            }
            //check to see if the person has any step-children.
            if (hasPartner(personName, aDOB)) {
                FamilyTreeNode<Person> partner = person.getSideLinks().get(0);

                for (FamilyTreeNode<Person> child : partner.getChildLinks()) {
                    //if this isn't the person's child, then it's a stepchild.
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
     * List the details of the siblings of the person whose name is given. Note
     * you need to do something about the possibility of duplicate names
     * appearing.
     * @param personName
     * @param aDOB
     */
    public String listSiblings(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;
        //Lists to hold various classes of siblings.
        List<FamilyTreeNode<Person>> halfSiblings = new ArrayList<FamilyTreeNode<Person>>();
        List<FamilyTreeNode<Person>> fullSiblings = new ArrayList<FamilyTreeNode<Person>>();
        List<FamilyTreeNode<Person>> stepSiblings = new ArrayList<FamilyTreeNode<Person>>();

        //make sure person exists first.
        if ((person = getPerson(personName, aDOB)) != null) {
            //check to see if person has parents.
            if (!person.parentLinksIsEmpty()) {
                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    for (FamilyTreeNode<Person> sibling : parent.getChildLinks()) {
                        //make sure that we're looking at a sibling, not our chosen person.
                        if (!sibling.equals(person)) {
                            //check to see if half sibling.
                            for (FamilyTreeNode<Person> siblingParent : sibling.getParentLinks()) {
                                //if the siblings only share one parent, then they are half siblings.
                                if (!person.containsParentLink(siblingParent)) {
                                    if (!halfSiblings.contains(sibling)) {
                                        halfSiblings.add(sibling);
                                    }
                                    break;
                                }
                            }
                            //another check to see if half sibling. Despite appearances,
                            //this is required, or else certain half-siblings will 
                            //be erroneously listed as full siblings.
                            for (FamilyTreeNode<Person> personParent : person.getParentLinks()) {
                                //if the siblings only share one parent, then they are half siblings.
                                if (!sibling.containsParentLink(personParent)) {
                                    if (!halfSiblings.contains(sibling)) {
                                        halfSiblings.add(sibling);
                                    }
                                    break;
                                }
                            }
                            //not a half-sibling, so must be full sibling.
                            if (!halfSiblings.contains(sibling) && !fullSiblings.contains(sibling)) {
                                fullSiblings.add(sibling);
                            }
                        }
                    }
                }
                //We've found full and half siblings, check for step-siblings.
                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    if (!parent.sideLinksIsEmpty()) {
                        FamilyTreeNode<Person> partner = parent.getSideLinks().get(0);
                        for (FamilyTreeNode<Person> sibling : partner.getChildLinks()) {
                            if (!sibling.equals(person)) {
                                if (!fullSiblings.contains(sibling)
                                        && !halfSiblings.contains(sibling)
                                        && !stepSiblings.contains(sibling)) {
                                    stepSiblings.add(sibling);
                                }
                            }
                        }
                    }
                }
            }
            //list sibling details.
            if (!fullSiblings.isEmpty() || !halfSiblings.isEmpty() || !stepSiblings.isEmpty()) {
                if (person.getItem().isAdopted()) {
                    details += personName + "'s adoptive siblings:\n";
                } else {
                    details += personName + "'s siblings:\n";
                }
                for (FamilyTreeNode<Person> sibling : fullSiblings) {
                    if (sibling.getItem().isAdopted()) {
                        details += "Adopted sibling: ";
                    } else {
                        details += "Sibling: ";
                    }
                    details += sibling.getItem().toString() + "\n";
                }
                for (FamilyTreeNode<Person> sibling : halfSiblings) {
                    if (sibling.getItem().isAdopted()) {
                        details += "Adopted half sibling: ";
                    } else {
                        details += "Half sibling: ";
                    }
                    details += sibling.getItem().toString() + "\n";
                }
                for (FamilyTreeNode<Person> sibling : stepSiblings) {
                    if (sibling.getItem().isAdopted()) {
                        details += "Adopted step sibling: ";
                    } else {
                        details += "Step sibling: ";
                    }
                    details += sibling.getItem().toString() + "\n";
                }
            } else {
                System.out.println(personName + " has no siblings listed.");
            }
        } else {
            System.out.println(personName + " isn't in the tree.");
        }

        return details;
    }

    /**
     * List the details of the ancestors along the paternal line of the person
     * whose name is given. Lists father, grandfather, great grandfather, etc,
     * until it reaches the oldest known man in the paternal side of the tree.
     * 
     * @param personName
     * @param aDOB
     */
    public String listPaternalLineage(String personName, String aDOB) {
        String details = "";
        int numGens = 0;
        FamilyTreeNode<Person> person;


        if ((person = getPerson(personName, aDOB)) != null) {
            //continue until we can't find any more men.
            while (!person.parentLinksIsEmpty()) {
                if (details.isEmpty()) {
                    if (person.getItem().isAdopted()) {
                        details += personName + "'s adoptive paternal lineage:\n";
                    } else {
                        details += personName + "'s paternal lineage:\n";
                    }
                }
                //find the current person's father, and increase the generation int.
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
     * List the details of the ancestors along the maternal line of the person
     * whose name is given. Lists mother, grandmother, great grandmother, etc,
     * until it reaches the oldest known woman in the maternal side of the tree.
     * 
     * @param personName
     * @param aDOB
     */
    public String listMaternalLineage(String personName, String aDOB) {
        String details = "";
        int numGens = 0;
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            //continue until we find ther oldest known woman in the tree.
            while (!person.parentLinksIsEmpty()) {
                if (details.isEmpty()) {
                    if (person.getItem().isAdopted()) {
                        details += personName + "'s adoptive maternal lineage:\n";
                    } else {
                        details += personName + "'s maternal lineage:\n";
                    }
                }
                //get the current person's mother, and increase the generation int.
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
     * whose name is given. 
     * 
     * Finds the person's parents, and then finds their parents, as those
     * are the person's grandparents.
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
     * Finds the person's children's children, as those are the person's 
     * grandchildren.
     * 
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
     * This looks pretty complex, but it's fairly simple once explained: We find
     * the desired person's grandparents, and then find the grandparent's children.
     * If the child in question isn't one of our person's parents, then it
     * stands to reason that their children are our person's cousins.
     *
     * @param personName
     * @param aDOB
     */
    public String listCousins(String personName, String aDOB) {
        String details = "";
        FamilyTreeNode<Person> person;

        if ((person = getPerson(personName, aDOB)) != null) {
            if (!person.parentLinksIsEmpty()) {
                //find parents.
                for (FamilyTreeNode<Person> parent : person.getParentLinks()) {
                    //find grandparents.
                    for (FamilyTreeNode<Person> grandparent : parent.getParentLinks()) {
                        //find aunts and uncles.
                        for (FamilyTreeNode<Person> parentSibling : grandparent.getChildLinks()) {
                            //find cousins, list them.
                            if (!parentSibling.equals(parent)) {
                                for (FamilyTreeNode<Person> cousin : parentSibling.getChildLinks()) {
                                    if (details.isEmpty()) {
                                        if (person.getItem().isAdopted()) {
                                            details += personName + "'s adoptive cousins:\n";
                                        } else {
                                            details += personName + "'s cousins:\n";
                                        }
                                    }
                                    if (!details.contains(cousin.getItem().toString())) {
                                        if (cousin.getItem().isAdopted()) {
                                            details += "Adopted cousin: ";
                                        } else {
                                            details += "Cousin: ";
                                        }
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
                //loop through the generations until they reach zero.
                for (int i = numberOfGenerations; i > 0; i--) {
                    for (FamilyTreeNode<Person> currentAncestor : currentAncestors) {
                        //fill up nextAncestors with the parents of the people in currentAncestors.
                        for (FamilyTreeNode<Person> nextAncestor : currentAncestor.getParentLinks()) {
                            nextAncestors.add(nextAncestor);
                        }
                    }
                    //copy nextAncestors to currentAncestors and clear nextAncestors
                    //for the next loop.
                    currentAncestors.clear();
                    currentAncestors.addAll(nextAncestors);
                    nextAncestors.clear();
                }
                //list ancestors.
                if (currentAncestors.isEmpty()) {
                    details += personName + " doesn't have any " + fixGenerations(numberOfGenerations).toLowerCase() + "parents.\n";
                } else {
                    if (person.getItem().isAdopted()) {
                        details += personName + "'s adopted " + fixGenerations(numberOfGenerations).toLowerCase() + "parents:\n";
                    } else {
                        details += personName + "'s " + fixGenerations(numberOfGenerations).toLowerCase() + "parents:\n";
                    }
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
                    if (currentChild.getItem().isAdopted()) {
                        details += "Adopted" + fixGenerations(numberOfGenerations).toLowerCase() + " child";
                    } else {
                        details += fixGenerations(numberOfGenerations) + "child: ";
                    }
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
     * Returns true if the person specified has a partner.
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