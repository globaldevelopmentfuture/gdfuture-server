package org.example.gdfutureserver.projects.service;


import org.example.gdfutureserver.projects.dto.ProjectRequestDTO;
import org.example.gdfutureserver.projects.dto.ProjectResponseDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectCommandService {
    ProjectResponseDTO createProject(ProjectRequestDTO projectRequest, MultipartFile imageFile) throws Exception;
    ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO projectRequest, MultipartFile imageFile) throws Exception;
    void deleteProject(Long projectId);
}
