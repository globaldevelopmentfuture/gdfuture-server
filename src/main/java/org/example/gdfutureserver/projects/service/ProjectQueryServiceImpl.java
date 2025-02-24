package org.example.gdfutureserver.projects.service;


import org.example.gdfutureserver.projects.dto.ProjectResponseDTO;
import org.example.gdfutureserver.projects.mapper.ProjectMapper;
import org.example.gdfutureserver.projects.model.Project;
import org.example.gdfutureserver.projects.repo.ProjectRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepo projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectQueryServiceImpl(ProjectRepo projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectResponseDTO getProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        return projectMapper.toDto(project);
    }

    @Override
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toDto)
                .collect(Collectors.toList());
    }
}
