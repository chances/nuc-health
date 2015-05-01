package nucchallenge.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Patient {
    private int patientId;
    private String name;
    private int age;
    private Gender gender;
    private List<String> illnessCodes;

    public Patient() {
        this(-1, null, 0, new String[]{});
    }

    public Patient(int patientId, String name, int age, String[] illnessCodes) {
        this.patientId = patientId;
        this.name = name;
        this.age = age;
        this.gender = Gender.OTHER;
        this.illnessCodes = new ArrayList<>();
        this.illnessCodes.addAll(Arrays.asList(illnessCodes));
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge(int age)
    {
        this.age = age;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public List<String> getIllnessCodes() {
        return illnessCodes;
    }

    public void setIllnessCodes(List<String> illnessCodes) {
        this.illnessCodes = illnessCodes;
    }

}
