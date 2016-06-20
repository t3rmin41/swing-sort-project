package unique.digital.student;

public class StudentStats {

    private String studentSurname;
    private Double score;
    
    public StudentStats(String surname, Double score) {
        this.studentSurname = surname;
        this.score = score;
    }
    
    public String getStudentSurname() {
        return studentSurname;
    }
    public void setStudentSurname(String studentSurname) {
        this.studentSurname = studentSurname;
    }
    public Double getScore() {
        return score;
    }
    public void setScore(Double score) {
        this.score = score;
    }

}
