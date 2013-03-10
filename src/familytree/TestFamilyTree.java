package familytree;

import java.util.Scanner;
import java.io.*;

/**
 * TestFamilyTree provides a text-based interface for a family tree
 * The interface consists of a top-level menu with options to:
 * <dl>
 * <dt>Load Data
 * <dd> - loads a set of test data. This, together with any
 * other necessary data, will used when you run your code during the
 * demonstration.</dd>
 * <dt>Input Data
 * <dd> - provides options to add a person, make a link to mother or father
 * or record a wedding, divorce or adoption </dd>
 * <dt>Make a Query
 * <dd> - provides options to list details of an individual and their
 * ancestors or descendants.</dd>
 * </dl>
 * <p>
 * Choosing an option from the input data or query menus results in a
 * call to an empty FamilyTree method. You need to implement the methods in
 * FamilyTree.
 *
 * @author      David Coward
 * @author      Jane Berry
 * @version     2
 */
public class TestFamilyTree {

    FamilyTree fTree1;

    /**
     * Instantiates a FamilyTree
     *
     * @see FamilyTree
     */
    TestFamilyTree() {
        fTree1 = new FamilyTree();
    }

    /**
     * Returns the String entered at the console.
     *
     * @return    the string that has been input.
     */
    public String getPersonName() {
        Scanner scan = new Scanner(System.in);
        String personName;
        System.out.println("Enter - name: ");
        personName = scan.nextLine();
        return personName;
    }

    public String getDateOfBirth() {
        Scanner scan = new Scanner(System.in);
        String dateOfBirth;
        System.out.println("Enter - date of birth: ");
        dateOfBirth = scan.nextLine();
        return dateOfBirth;
    }

    /**
     * Processes the response to the Input Data request, prompting for
     * additional input as required. Once the required input data has
     * been received, the relevant FamilyTree method is called.
     *
     * @see       FamilyTree
     */
    private void processInput() {
        Scanner scan = new Scanner(System.in);
        String selection, personName, place, dOB, fDOB, mDOB, gDOB,
                mothersName, fathersName, bridesName, groomsName;
        char iChoice;

        do {
        selection = scan.nextLine().toUpperCase();
        } while(selection.isEmpty());
        iChoice = selection.charAt(0);
        while (iChoice != 'X') {
            switch (iChoice) {
                case 'A':
                    System.out.println("Enter - name, DOB and place of birth ");
                    personName = scan.nextLine();
                    dOB = scan.nextLine();
                    place = scan.nextLine();
                    Person aPerson = new Person(personName, dOB, place);
                    fTree1.addPerson(aPerson);
                    break;
                case 'B':
                    System.out.println("Enter - name, DOB, Mother's name and DOB: ");
                    personName = scan.nextLine();
                    dOB = scan.nextLine();
                    mothersName = scan.nextLine();
                    mDOB = scan.nextLine();
                    fTree1.makeLinkToMother(personName, dOB, mothersName, mDOB);
                    break;
                case 'C':
                    System.out.println("Enter - name, DOB, Father's name and DOB: ");
                    personName = scan.nextLine();
                    dOB = scan.nextLine();
                    fathersName = scan.nextLine();
                    fDOB = scan.nextLine();
                    fTree1.makeLinkToFather(personName, dOB, fathersName, fDOB);
                    break;
                case 'D':
                    System.out.println("Enter - Bride's name and DOB "
                            + "and Groom's name and DOB : ");
                    bridesName = scan.nextLine();
                    dOB = scan.nextLine();
                    groomsName = scan.nextLine();
                    gDOB = scan.nextLine();
                    fTree1.recordWedding(bridesName, dOB, groomsName, gDOB);
                    break;
                case 'E':
                    System.out.println("Enter - Wife's name and DOB "
                            + "and Husband's name and DOB : ");
                    bridesName = scan.nextLine();
                    dOB = scan.nextLine();
                    groomsName = scan.nextLine();
                    gDOB = scan.nextLine();
                    fTree1.recordDivorce(bridesName, dOB, groomsName, gDOB);
                    break;
                case 'F':
                    System.out.println("Enter - name and DOB of person adopted : ");
                    personName = scan.nextLine();
                    dOB = scan.nextLine();
                    fTree1.recordAdoption(personName, dOB);
                    break;
                default:
                    System.out.println("\nInvalid input choice. Try again\n"); //do nothing
            }
            inputMenu();
            selection = scan.nextLine().toUpperCase();
            iChoice = selection.charAt(0);
        }
    }

    /**
     * Processes a family tree query by calling the relevant FamilyTree
     * method.
     *
     * @see       FamilyTree
     */
    private void processQuery() {
        Scanner scan = new Scanner(System.in);
        String selection, personName;
        char qChoice;
        int numOfGens;

        selection = scan.nextLine().toUpperCase();
        qChoice = selection.charAt(0);
        while (qChoice != 'X') {
            switch (qChoice) {
                case 'K':
                    System.out.println(fTree1.listPersonDetails(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'L':
                    System.out.println(fTree1.listParentDetails(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'M':
                    System.out.println(fTree1.listChildren(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'N':
                    System.out.println(fTree1.listSiblings(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'O':
                    System.out.println(fTree1.listPaternalLineage(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'P':
                    System.out.println(fTree1.listMaternalLineage(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'Q':
                    System.out.println(fTree1.listGrandParents(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'R':
                    System.out.println(fTree1.listGrandChildren(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'S':
                    System.out.println(fTree1.listCousins(this.getPersonName(), this.getDateOfBirth()));
                    break;
                case 'T':
                    personName = this.getPersonName();
                    System.out.println("Now enter - number of Generations required : ");
                    numOfGens = scan.nextInt();
                    scan.nextLine();
                    System.out.println(fTree1.listGreatNGrandParents(personName, this.getDateOfBirth(), numOfGens));
                    break;
                case 'U':
                    personName = this.getPersonName();
                    System.out.println("Now enter - number of Generations required : ");
                    numOfGens = scan.nextInt();
                    scan.nextLine();
                    System.out.println(fTree1.listGreatNGrandChildren(personName, this.getDateOfBirth(), numOfGens));
                    break;
            }
            queryMenu();
            selection = scan.nextLine().toUpperCase();
            qChoice = selection.charAt(0);
        }
    }

    /**
     * Displays the top-level TestFamilyTree menu
     *
     */
    private void menu() {
        System.out.println("\nFAMILY TREE MENU\n");
        System.out.println("L\tLoad Data");
        System.out.println("I\tInput Details");
        System.out.println("Q\tMake a Query\n");

        System.out.println("X\tEXIT\n");

        System.out.println("Enter menu choice L-I, X: ");
    }

    /**
     * Displays the options that make up the Input Details menu
     *
     */
    private void inputMenu() {
        System.out.println("\nFAMILY TREE INPUT MENU\n");
        System.out.println("A\tAdd a person to the family tree");
        System.out.println("B\tMake link to mother");
        System.out.println("C\tMake link to father");
        System.out.println("D\tRecord wedding");
        System.out.println("E\tRecord divorce");
        System.out.println("F\tRecord adoption\n");

        System.out.println("X\tEXIT INPUT\n");

        System.out.println("Enter menu choice A-F, X: ");
    }

    /**
     * Displays the query menu
     */
    private void queryMenu() {
        System.out.println("\nFAMILY TREE QUERY MENU\n");
        System.out.println("K\tList person details");
        System.out.println("L\tList parent details");
        System.out.println("M\tList children");
        System.out.println("N\tList siblings (noting any half-siblings)");
        System.out.println("O\tList paternal lineage (male line back to oldest man in the tree)");
        System.out.println("P\tList maternal lineage (female line back to oldest woman in the tree)");
        System.out.println("Q\tList all grandparents");
        System.out.println("R\tList all grandchildren");
        System.out.println("S\tList all cousins");
        System.out.println("T\tList all great great… (repeated N times) grandparents");
        System.out.println("U\tList all great great… (repeated N times) grandchildren\n");

        System.out.println("X\tEXIT QUERY\n");

        System.out.println("Enter menu choice K-U, X: ");
    }

    /**
     * Loads test data. The test data comes from 3 pre-named text files.
     * <ul>
     * <li> person.txt  - contains person details
     * <li> fathers.txt - links a person to their father
     * <li> mothers.txt - links a person to their mother
     *
     * @see       FamilyTree
     */
    private void loadData() throws IOException {

        Scanner pFile, fFile, mFile, lineScan;
        String entry, name, dOB, place, fName, fDOB, mName, mDOB;
        name = null;
        dOB = null;
        place = null;
        fName = null;
        fDOB = null;
        mName = null;
        mDOB = null;

        // read file person.txt and create new instance of person for each one 
        pFile = new Scanner(new File("person.txt"));
        while (pFile.hasNext()) {
            entry = pFile.nextLine();
            lineScan = new Scanner(entry);
            while (lineScan.hasNext()) {
                name = lineScan.next();
                dOB = lineScan.next();
                place = lineScan.next();
            }
            if ((name != null) && (dOB != null) && (place != null)) {
                Person aPerson = new Person(name, dOB, place);
                fTree1.addPerson(aPerson);
                System.out.println(name + " " + dOB + " ");
            }
        }
        // read file fathers.txt Foreach create father link
        fFile = new Scanner(new File("fathers.txt"));
        while (fFile.hasNext()) {
            entry = fFile.nextLine();
            lineScan = new Scanner(entry);
            while (lineScan.hasNext()) {
                name = lineScan.next();
                dOB = lineScan.next();
                fName = lineScan.next();
                fDOB = lineScan.next();
            }
            if ((name != null) && (dOB != null) && (fName != null) && (fDOB != null)) {
                fTree1.makeLinkToFather(name, dOB, fName, fDOB);
                System.out.println(name + " " + dOB + " " + fName + " " + fDOB + " Father Added");
            }
        }

        // read file mothers.txt Foreach create mother link        
        mFile = new Scanner(new File("mothers.txt"));
        while (mFile.hasNext()) {
            entry = mFile.nextLine();
            lineScan = new Scanner(entry);
            while (lineScan.hasNext()) {
                name = lineScan.next();
                dOB = lineScan.next();
                mName = lineScan.next();
                mDOB = lineScan.next();
            }
            if ((name != null) && (dOB != null) && (mName != null) && (mDOB != null)) {
                fTree1.makeLinkToMother(name, dOB, mName, mDOB);
                System.out.println(name + " " + dOB + " " + mName + " " + mDOB + " Mother Added");
            }
        }
    }

    /**
     * Displays the top and subsequent level menus.
     * @param args unused
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        TestFamilyTree tFT = new TestFamilyTree();
        Scanner scan = new Scanner(System.in);
        char mChoice;
        String selection;

        tFT.menu();
        selection = scan.nextLine().toUpperCase();
        mChoice = selection.charAt(0);
        while (mChoice != 'X') {
            switch (mChoice) {
                case 'L': {
                    tFT.loadData();
                    break;
                }
                case 'I': {
                    tFT.inputMenu();
                    tFT.processInput();
                    break;
                }
                case 'Q': {
                    tFT.queryMenu();
                    tFT.processQuery();
                    break;
                }
                default: {
                    System.out.println("\nInvalid choice. Try again\n");
                }
            }
            tFT.menu();
            selection = scan.nextLine().toUpperCase();
            mChoice = selection.charAt(0);
        }
    }
}