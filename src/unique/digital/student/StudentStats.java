package unique.digital.student;

public class StudentStats implements Comparable<Object> {

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

    @Override
    public int compareTo(Object o) {
        
        StudentStats studentStats = (StudentStats) o;
        if (studentStats.getScore() > this.getScore()) {
            return -1;
        } else if (studentStats.getScore() < this.getScore()) {
            return 1;
        } else {
            return 0;
        }
    }

}
