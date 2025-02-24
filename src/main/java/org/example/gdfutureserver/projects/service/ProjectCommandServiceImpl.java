package org.example.gdfutureserver.projects.service;

import org.example.gdfutureserver.image.model.ImageFile;
import org.example.gdfutureserver.image.service.ImageCommandService;
import org.example.gdfutureserver.projects.dto.ProjectRequestDTO;
import org.example.gdfutureserver.projects.dto.ProjectResponseDTO;
import org.example.gdfutureserver.projects.mapper.ProjectMapper;
import org.example.gdfutureserver.projects.model.Project;
import org.example.gdfutureserver.projects.repo.ProjectRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@Transactional
public class ProjectCommandServiceImpl implements ProjectCommandService {

    private final ProjectRepo projectRepository;
    private final ImageCommandService imageCommandService;
    private final ProjectMapper projectMapper;

    public ProjectCommandServiceImpl(ProjectRepo projectRepository,
                                     ImageCommandService imageCommandService,
                                     ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.imageCommandService = imageCommandService;
        this.projectMapper = projectMapper;
    }

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequest, MultipartFile imageFile) throws Exception {
        Project project = projectMapper.toEntity(projectRequest);
        if (imageFile != null && !imageFile.isEmpty()) {
            ImageFile uploadedImage = imageCommandService.uploadImage(imageFile);
            project.setImage(uploadedImage);
        }
        Project savedProject = projectRepository.save(project);
        return projectMapper.toDto(savedProject);
    }

    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectRequestDTO projectRequest, MultipartFile imageFile) throws Exception {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));

        existingProject.setName(projectRequest.getName());
        existingProject.setDescription(projectRequest.getDescription());
        existingProject.setClient(projectRequest.getClient());
        existingProject.setPrice(projectRequest.getPrice());
        existingProject.setLink(projectRequest.getLink());
        existingProject.setDeadline(LocalDate.parse(projectRequest.getDeadline()));
        existingProject.setTeamSize(projectRequest.getTeamSize());
        existingProject.setType(projectRequest.getType());
        existingProject.setTechnologies(projectRequest.getTechnologies());

        if (imageFile != null && !imageFile.isEmpty()) {
            if (existingProject.getImage() != null) {
                ImageFile updatedImage = imageCommandService.updateImage(existingProject.getImage(), imageFile);
                existingProject.setImage(updatedImage);
            } else {
                ImageFile newImage = imageCommandService.uploadImage(imageFile);
                existingProject.setImage(newImage);
            }
        }
        Project updatedProject = projectRepository.save(existingProject);
        return projectMapper.toDto(updatedProject);
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
        if (project.getImage() != null) {
            imageCommandService.deleteImage(project.getImage());
        }
        projectRepository.delete(project);
    }
}
