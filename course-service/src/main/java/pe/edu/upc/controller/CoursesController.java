package pe.edu.upc.controller;


import pe.edu.upc.entities.*;
import pe.edu.upc.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@Slf4j
@RequestMapping("/courses")
public class CoursesController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private MultimediaService multimediaService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<List<Course>> fetchAll(){
        try{
            List<Course> courses= courseService.findAll();
            return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long courseId) {
        try {
            Course course = courseService.findById(courseId);
            if (course!=null) {
                return new ResponseEntity<Course>(course, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping(value = "{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable("courseId") Long courseId,@Valid @RequestBody Course course) throws Exception {
        log.info("Actualizando Course con Id {}", courseId);
        Course currentCourse = courseService.update(course, courseId);
        return ResponseEntity.ok(currentCourse);
    }


    @DeleteMapping(value = "{courseId}")
    public void deleteRecipe(@PathVariable("courseId") Long courseId) throws Exception {
        log.info("Eliminando Course con Id {}", courseId);
        courseService.deleteById(courseId);
    }

    @PostMapping
    public ResponseEntity<Course> createRecipe(@Valid @RequestBody Course course, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Course productDB= courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDB);
    }


    @PostMapping(path = "{courseId}/multimedia")
    public ResponseEntity<Multimedia> createMultimedia(@PathVariable("courseId") Long courseId, @Valid @RequestBody Multimedia resource, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Multimedia multimedia = multimediaService.createMultimedia(courseId,resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(multimedia);
    }


    private String formatMessage( BindingResult result){

        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
