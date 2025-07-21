package org.example.validatedprojecttracker.Model;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Project {
    @NotEmpty(message = "ID cannot be empty")
    @Size(min = 2, message = "Id length Should be at least 2")
    private String ID;


    @NotEmpty(message = "Title cannot be empty")
    @Size(min = 8, message = "Title should be at least 8 characters or more")
    private String title;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 15, message = "Description Should be at least 15 characters or more")
    private String description;

    @NotEmpty(message = "Status cannot be empty")
    @Pattern(regexp = "^(Not Started|in Progress|Completed$)", message = "Status must be Not Started or in Progress or Completed only")
    private String status;

    @NotEmpty(message = "Company's Name cannot be Empty")
    @Size(min = 6,message = "Company's Name Should be at least 6 Characters")
    private String companyName;
}
