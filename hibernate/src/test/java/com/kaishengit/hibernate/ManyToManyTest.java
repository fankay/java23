package com.kaishengit.hibernate;

import com.kaishengit.pojo.Student;
import com.kaishengit.pojo.Teacher;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class ManyToManyTest extends BaseTestCase {

    @Test
    public void save() {
        //新增两个学生s1 s2 新增两个老师t1 t2
        Student s1 = new Student();
        s1.setStudentName("s1");

        Student s2 = new Student();
        s2.setStudentName("s2");

        Teacher t1 = new Teacher();
        t1.setTeacherName("t1");

        Teacher t2 = new Teacher();
        t2.setTeacherName("t2");


        Set<Teacher> teacherSet = new HashSet<Teacher>();
        teacherSet.add(t1);
        teacherSet.add(t2);

        s1.setTeacherSet(teacherSet);
        s2.setTeacherSet(teacherSet);

       /* Set<Student> studentSet = new HashSet<Student>();
        studentSet.add(s1);
        studentSet.add(s2);

        t1.setStudentSet(studentSet);
        t2.setStudentSet(studentSet);*/

        session.save(t1);
        session.save(t2);

        session.save(s1);
        session.save(s2);





    }

    @Test
    public void findByStudentId() {

        Student student = (Student) session.get(Student.class,5);
        System.out.println(student.getStudentName());

        //延迟加载
        Set<Teacher> teacherSet = student.getTeacherSet();
        for(Teacher teacher : teacherSet) {
            System.out.println(teacher.getId() + " -> " + teacher.getTeacherName());
        }
    }

}
