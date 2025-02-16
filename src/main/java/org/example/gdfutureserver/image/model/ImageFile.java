package org.example.gdfutureserver.image.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageFile {

    @Id
    @SequenceGenerator(
            name = "file_sequence",
            sequenceName = "file_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "file_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String url;

    @Lob
    @Column(name = "data", nullable = false)
    private byte[] data;
}
