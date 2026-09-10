package com.course_blogging.user_service.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateProfileRequest {

    @NotBlank(message = "name is required" )
    @Size(max = 100,message = "name should be most 100 letters")
    private String name;
    @Size(max = 500,message = "name should be most 500 letters")
    private String bio;
}
