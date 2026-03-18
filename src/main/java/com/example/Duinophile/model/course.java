package com.example.Duinophile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@AllArgsConstructor
@Data
@ToString
@Document(collection = "courses")
public class course {
    @Id
    private int id;
    private String name;
    private String description;
    private String material;
    private String level;

    public void printInfo() {
        System.out.println("\n--------------------\nCourse info");
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Material: " + material);
        System.out.println("Level: " + level + "\n--------------------\n");
    }
}
