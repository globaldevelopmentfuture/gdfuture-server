package org.example.gdfutureserver.projects.mapper;


import org.example.gdfutureserver.projects.dto.ProjectRequestDTO;
import org.example.gdfutureserver.projects.dto.ProjectResponseDTO;
import org.example.gdfutureserver.projects.model.Project;

public interface ProjectMapper {
    Project toEntity(ProjectRequestDTO dto);
    ProjectResponseDTO toDto(Project project);
}

