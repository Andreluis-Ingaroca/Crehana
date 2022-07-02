package pe.edu.upc.services;


import pe.edu.upc.entities.Multimedia;

import java.util.List;

public interface MultimediaService extends CrudService<Multimedia,Long> {

    Multimedia createMultimedia(Long courseId, Multimedia multimedia);

    List<Multimedia> getAllMultimediaByCourseId(Long courseId);
}
