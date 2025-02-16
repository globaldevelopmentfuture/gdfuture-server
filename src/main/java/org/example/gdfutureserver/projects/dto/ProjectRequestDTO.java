package org.example.gdfutureserver.projects.dto;

import lombok.Data;
import org.example.gdfutureserver.projects.enums.ProjectType;
import org.example.gdfutureserver.projects.model.Technology;

import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectRequestDTO {
    private String name;
    private String description;
    private Long imageId;
    private String client;
    private Double price;
    private String link;
    private LocalDate deadline;
    private Integer teamSize;
    private ProjectType type;
    private List<Technology> technologies;
}

