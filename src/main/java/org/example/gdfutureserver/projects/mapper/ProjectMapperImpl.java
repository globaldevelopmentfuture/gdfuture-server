package org.example.gdfutureserver.projects.mapper;

import org.example.gdfutureserver.projects.dto.ProjectRequestDTO;
import org.example.gdfutureserver.projects.dto.ProjectResponseDTO;
import org.example.gdfutureserver.projects.model.Project;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class ProjectMapperImpl implements ProjectMapper {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Project toEntity(ProjectRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        return Project.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .client(dto.getClient())
                .price(dto.getPrice())
                .link(dto.getLink())
                .deadline(dto.getDeadline() != null ? LocalDate.parse(dto.getDeadline(), formatter) : null)
                .teamSize(dto.getTeamSize())
                .type(dto.getType())
                .technologies(dto.getTechnologies())
                .build();
    }

    @Override
    public ProjectResponseDTO toDto(Project project) {
        if (project == null) {
            return null;
        }
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
        dto.setClient(project.getClient());
        dto.setPrice(project.getPrice());
        dto.setLink(project.getLink());
        dto.setDeadline(project.getDeadline() != null ? LocalDate.parse(project.getDeadline().format(formatter)) : null);
        dto.setTeamSize(project.getTeamSize());
        dto.setType(project.getType());
        dto.setTechnologies(project.getTechnologies());
        dto.setImageUrl(project.getImage() != null ? project.getImage().getUrl() : null);
        return dto;
    }
}
