package org.example.gdfutureserver.projects.service;


import org.example.gdfutureserver.projects.dto.ProjectResponseDTO;
import java.util.List;

public interface ProjectQueryService {
    ProjectResponseDTO getProject(Long projectId);
    List<ProjectResponseDTO> getAllProjects();
}

