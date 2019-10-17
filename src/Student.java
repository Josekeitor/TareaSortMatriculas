import java.io.Serializable;

public class Student implements Serializable, Comparable<Student> {
    private String name;
    private double grade;
    private String idNumber;

    public String getName() {

        return name;
    }

    public void setName(String name) throws EmptyFieldException {
        if(name == null || name.length()==0)
            throw new EmptyFieldException("The name field cannot be empty");
        this.name = name;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(String grade) throws EmptyFieldException {
        if(grade == null || grade.length()==0)
            throw new EmptyFieldException("The grade field cannot be empty (the minimum grade is 1/100)");
        this.grade = Double.parseDouble(grade);
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) throws EmptyFieldException {
        if(idNumber == null || idNumber.length()==0)
            throw new EmptyFieldException("The ID Number field cannot be empty");
        this.idNumber = idNumber;
    }

    public String toString(){
        return idNumber +"    "+grade;
    }

    @Override
    public int compareTo(Student s) {
        if(grade != s.grade)
            return (int)Math.round(s.grade -grade);
        else{
            return 0;
        }
    }

}
