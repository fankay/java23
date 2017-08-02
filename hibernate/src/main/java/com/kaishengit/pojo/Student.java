package com.kaishengit.pojo;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "student_name")
    private String studentName;

    @ManyToMany
    @JoinTable(name = "teacher_student",
            joinColumns =@JoinColumn(name = "student_id"),
            inverseJoinColumns =@JoinColumn(name = "teacher_id")
    )
    private Set<Teacher> teacherSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Set<Teacher> getTeacherSet() {
        return teacherSet;
    }

    public void setTeacherSet(Set<Teacher> teacherSet) {
        this.teacherSet = teacherSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (id != student.id) return false;
        if (studentName != null ? !studentName.equals(student.studentName) : student.studentName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        return result;
    }
}
