package pe.edu.upc.services.impls;


import pe.edu.upc.entities.Multimedia;
import pe.edu.upc.entities.Course;
import pe.edu.upc.repositories.MultimediaRepository;
import pe.edu.upc.repositories.CourseRepository;
import pe.edu.upc.services.MultimediaService;
import pe.edu.upc.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MultimediaServiceImpl implements MultimediaService {

    @Autowired
    private MultimediaRepository multimediaRepository;

    @Autowired
    private CourseRepository courseRepository;


    @Override
    public Multimedia createMultimedia(Long courseId, Multimedia multimedia) {
        Course recipe = courseRepository.findById(courseId)
                .orElseThrow(()->new ResourceNotFoundException("Course", "Id", courseId));
        recipe.addMultimedia(multimedia);
        return multimediaRepository.save(multimedia);
    }

    @Override
    public List<Multimedia> getAllMultimediaByCourseId(Long courseId) {
        return courseRepository.findById(courseId).map(
                recipe -> {
                    List<Multimedia> multimediaList = recipe.getMultimedia();
                    return multimediaList;
                }
        ).orElseThrow(()->new ResourceNotFoundException("Course","Id",courseId));
    }

    @Override
    public List<Multimedia> findAll() throws Exception {
        return multimediaRepository.findAll();
    }

    @Override
    public Multimedia findById(Long aLong) throws Exception {
        return multimediaRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
    }

    @Override
    public Multimedia update(Multimedia entity, Long aLong) throws Exception {
        Multimedia multimedia = multimediaRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        multimedia.setUrl(entity.getUrl());
        return multimediaRepository.save(multimedia);
    }

    @Override
    public void deleteById(Long aLong) throws Exception {
        Multimedia multimedia = multimediaRepository.findById(aLong)
                .orElseThrow(()->new ResourceNotFoundException(""));
        multimediaRepository.deleteById(aLong);
    }
}

