package com.company.reqai.entity;

import com.company.reqai.entity.enums.DocumentStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "analysis_document",
    indexes = {
        @Index(
            name = "idx_analysis_document_status",
            columnList = "status"
        ),
        @Index(
            name = "idx_analysis_document_created_at",
            columnList = "created_at"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class AnalysisDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        name = "file_name",
        nullable = false,
        length = 255
    )
    private String fileName;

    @Column(
        name = "original_content",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String originalContent;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "status",
        nullable = false,
        length = 30
    )
    private DocumentStatus status;

    @Column(
        name = "created_at",
        nullable = false,
        updatable = false
    )
    private Instant createdAt;

    @Column(name = "analyzed_at")
    private Instant analyzedAt;

    @OneToMany(
        mappedBy = "document",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("id ASC")
    private List<BusinessRequirement> requirements = new ArrayList<>();

    @PrePersist
    public void beforeInsert() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }

        if (status == null) {
            status = DocumentStatus.UPLOADED;
        }
    }

    public void addRequirement(BusinessRequirement requirement) {
        requirements.add(requirement);
        requirement.setDocument(this);
    }

    public void removeRequirement(BusinessRequirement requirement) {
        requirements.remove(requirement);
        requirement.setDocument(null);
    }

    public void clearRequirements() {
        for (BusinessRequirement requirement : requirements) {
            requirement.setDocument(null);
        }

        requirements.clear();
    }
}