package org.example.gdfutureserver.projects.dto;

import lombok.Data;
import org.example.gdfutureserver.projects.enums.ProjectType;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ProjectRequestDTO {
    private String name;
    private String description;
    private String client;
    private Double price;
    private String link;
    private String deadline;
    private Integer teamSize;
    private ProjectType type;
    private List<String> technologies;
}
