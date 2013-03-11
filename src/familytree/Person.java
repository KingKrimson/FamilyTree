package familytree;

/**
 * Holds the details of an individual. You need to complete this class
 * @author David
 */
public class Person {

    private String name, dateOfBirth, birthPlace;
    private int age;
    //basic information about family relationships. Relationships like
    //'stepfather' aren't set, as they can be inferred.
    private boolean isMother, isFather, isMarried, isDivorced, isAdopted;

    /** Creates a new instance of Person */
    public Person() {
    }

    public Person(String aName, String aDOB, String aBirthPlace) {
        this.name = aName;
        this.dateOfBirth = aDOB;
        this.birthPlace = aBirthPlace;
        
        this.isMother = this.isFather = this.isMarried = this.isDivorced = this.isAdopted = false;
    }
    
    //If two people's names are the same, and their date of birth's0 are the same, then
    //they are the same person.
    public boolean equals(Person comparedPerson) {
        boolean equals = false;
        if(comparedPerson.getName().equals(this.getName()) 
                && comparedPerson.getDateOfBirth().equals(this.getDateOfBirth())) {
            equals = true;
        }
        
        return equals;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDivorced() {
        return isDivorced;
    }

    public void setIsDivorced(boolean isDivorced) {
        this.isDivorced = isDivorced;
    }

    public boolean isFather() {
        return isFather;
    }

    public void setIsFather(boolean isFather) {
        this.isFather = isFather;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setIsMarried(boolean isMarried) {
        this.isMarried = isMarried;
    }

    public boolean isMother() {
        return isMother;
    }

    public void setIsMother(boolean isMother) {
        this.isMother = isMother;
    }
    
    public boolean isAdopted() {
        return isAdopted;
    }
    
    public void setIsAdopted(boolean isAdopted) {
        this.isAdopted = isAdopted;
    }

    @Override
    public String toString() {
        String desc = "Name: " + this.getName() + ", Date of Birth: " + this.getDateOfBirth()
                + ", Birth Place: " + this.getBirthPlace();
        return desc;
    }
}
