package familytree;

/**
 * Holds the details of an individual. You need to complete this class
 * @author David
 */
public class Person {

    public enum Gender {
        MALE, FEMALE
    }
    String name, dateOfBirth, birthPlace;
    int age;
    Gender gender;

    /** Creates a new instance of Person */
    public Person() {
    }

    public Person(String aName, String aDOB, String aBirthPlace) {
        this.name = aName;
        this.dateOfBirth = aDOB;
        this.birthPlace = aBirthPlace;
    }

    public Person(String aName, String aDOB, String aBirthPlace, Gender gender) {
        this.name = aName;
        this.dateOfBirth = aDOB;
        this.birthPlace = aBirthPlace;
        this.gender = gender;
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

    @Override
    public String toString() {
        String desc = "Name: " + this.getName() + ", Date of Birth: " + this.getDateOfBirth()
                + ", BirthPlace: " + this.getBirthPlace();
        return desc;
    }
}
