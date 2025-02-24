package org.example.gdfutureserver.projects.web;


import org.example.gdfutureserver.projects.dto.ProjectRequestDTO;
import org.example.gdfutureserver.projects.dto.ProjectResponseDTO;
import org.example.gdfutureserver.projects.service.ProjectCommandService;
import org.example.gdfutureserver.projects.service.ProjectQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/gdfuture/server/api/projects")
public class ProjectController {

    private final ProjectCommandService projectCommandService;
    private final ProjectQueryService projectQueryService;

    public ProjectController(ProjectCommandService projectCommandService,
                             ProjectQueryService projectQueryService) {
        this.projectCommandService = projectCommandService;
        this.projectQueryService = projectQueryService;
    }



    @PostMapping(value="/create",consumes = {"multipart/form-data"})
    public ResponseEntity<ProjectResponseDTO> createProject(
            @RequestPart("project") ProjectRequestDTO projectRequest,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            ProjectResponseDTO response = projectCommandService.createProject(projectRequest, imageFile);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(value="/{projectId}", consumes = {"multipart/form-data"})
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable Long projectId,
            @RequestPart("project") ProjectRequestDTO projectRequest,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            ProjectResponseDTO response = projectCommandService.updateProject(projectId, projectRequest, imageFile);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        try {
            projectCommandService.deleteProject(projectId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Long projectId) {
        try {
            ProjectResponseDTO response = projectQueryService.getProject(projectId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        List<ProjectResponseDTO> responseList = projectQueryService.getAllProjects();
        return ResponseEntity.ok(responseList);
    }
}

