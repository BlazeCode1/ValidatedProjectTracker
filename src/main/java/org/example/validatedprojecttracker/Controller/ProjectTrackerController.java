package org.example.validatedprojecttracker.Controller;


import jakarta.validation.Valid;
import org.example.validatedprojecttracker.Api.ApiResponse;
import org.example.validatedprojecttracker.Model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectTrackerController {

    ArrayList<Project> projects = new ArrayList<>();


    @GetMapping("/get")
    public ArrayList<Project> getProjects(){
        return projects;
    }


    @PostMapping("/add")
    public ResponseEntity<?> createProject(@Valid @RequestBody Project project, Errors err){
        if(err.hasErrors()){
            String error = err.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(error));
        }

        for(Project p : projects){
            if(p.getID().equals(project.getID()))
                return ResponseEntity.badRequest().body(new ApiResponse("Project With Given ID Already Exist."));
        }
        projects.add(project);
        return ResponseEntity.ok( new ApiResponse("Project added Successfully"));
    }


    @PutMapping("/update/{index}")
    public ResponseEntity<?> updateProject(@PathVariable int index,@RequestBody Project project ,Errors err){
        if(projects.isEmpty()){
            return ResponseEntity.badRequest().body(new ApiResponse("Project List Is Empty"));
        }

        if(index < 0 || index > projects.size()){
            return ResponseEntity.badRequest().body(new ApiResponse("Index Out Of Bound"));
        }

        if(err.hasErrors()){
            String message = err.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(new ApiResponse(message));
        }

        projects.set(index,project);


        return ResponseEntity.ok(new ApiResponse("Project Updated Successfully!"));
    }


    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<?> deleteProject(@PathVariable String ID){
        for(Project p : projects){
            if(p.getID().equals(ID)){
                projects.remove(p);
                return ResponseEntity.ok(new ApiResponse("Project Removed Successfully!"));
            }
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Project With given ID not found."));
    }




    @PutMapping("/status/{ID}/{status}")
    public ResponseEntity<?> updateStatus(@PathVariable String ID,@PathVariable String status){
        if(projects.isEmpty())
            return ResponseEntity.badRequest().body(new ApiResponse("Project List Is Empty"));

        for (Project p : projects){
            if(p.getID().equals(ID)){
                if(p.getStatus().equalsIgnoreCase("Completed"))
                    return ResponseEntity.badRequest().body(new ApiResponse("Project Already Completed."));
                if (status.equalsIgnoreCase("Completed")) {
                    p.setStatus("Completed");
                    return ResponseEntity.ok(new ApiResponse("Changed Status To 'Completed'"));
                }
                if (status.equalsIgnoreCase("in Progress")) {
                    p.setStatus("in Progress");
                    return ResponseEntity.ok(new ApiResponse("Changed Status To 'in Progress'"));
                }
                if(status.equalsIgnoreCase("Not Started")){
                    p.setStatus("Not Started");
                     return ResponseEntity.ok(new ApiResponse("Changed Status To 'Not Started'"));
                }
            }
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Project With Given ID not Found"));
    }



    //search project by title
    @GetMapping("/search/{title}")
    public ResponseEntity<?> searchProject(@PathVariable String title){
        for (Project p : projects){
            if(p.getTitle().equalsIgnoreCase(title)){
                return ResponseEntity.ok(p);
            }
        }
        return ResponseEntity.badRequest().body( new ApiResponse("Project Not Found."));
    }

    //display all projects from a given company
    @GetMapping("/filter/{companyName}")
    public ArrayList<Project> displayByCompany(@PathVariable String companyName){
        ArrayList<Project> filteredProjects = new ArrayList<>();
        for(Project p : projects){
            if(p.getCompanyName().equals(companyName)){
                filteredProjects.add(p);
            }
        }
        return filteredProjects;

    }




}
