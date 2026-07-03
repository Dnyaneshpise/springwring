package com.learn.demo.entity;

import com.learn.demo.validation.ValidRole;
import com.learn.demo.validation.ValidUserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ValidUserRole
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Role cannot be blank")
    @ValidRole                        // ← your custom annotation, works exactly like built-ins
    private String role;

    @Min(value = 0, message = "Experience cannot be negative")
    @Max(value = 50, message = "Experience seems unrealistic above 50")
    private int experience;
}