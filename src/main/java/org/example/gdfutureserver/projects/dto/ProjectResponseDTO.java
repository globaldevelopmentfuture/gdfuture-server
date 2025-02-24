package org.example.gdfutureserver.projects.dto;

import lombok.Data;
import org.example.gdfutureserver.projects.enums.ProjectType;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String client;
    private Double price;
    private String link;
    private LocalDate deadline;
    private Integer teamSize;
    private ProjectType type;
    private List<String> technologies;
}
