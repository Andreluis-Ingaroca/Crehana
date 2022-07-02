package pe.edu.upc.services;


import pe.edu.upc.entities.Course;

import java.util.List;

public interface CourseService extends CrudService<Course,Long> {

    Course createCourse(Course course);
}

