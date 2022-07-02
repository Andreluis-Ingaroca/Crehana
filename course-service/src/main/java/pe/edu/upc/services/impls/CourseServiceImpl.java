package pe.edu.upc.services.impls;

import pe.edu.upc.entities.Course;
import pe.edu.upc.repositories.*;
import pe.edu.upc.services.CourseService;
import pe.edu.upc.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    private MultimediaRepository multimediaRepository;

   @Override
   public List<Course> findAll() throws Exception {
       return courseRepository.findAll();
   }

    @Override
    public Course findById(Long aLong) throws Exception {
       Course course = courseRepository.findById(aLong).orElse(null);
       return course;
    }

    @Override
    public Course update(Course entity, Long aLong) throws Exception {
       Course course = courseRepository.findById(aLong)
               .orElseThrow(()->new ResourceNotFoundException("Course", "Id", aLong));
       course.setName(entity.getName())
               .setDescription(entity.getDescription())
               .setPremium(entity.isPremium());
       return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Course course = courseRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException("Course", "Id", aLong));
        courseRepository.deleteById(aLong);
    }


    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

}

