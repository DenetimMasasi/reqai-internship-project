package com.company.reqai.entity;

import com.company.reqai.entity.enums.Complexity;
import com.company.reqai.entity.enums.Priority;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "business_requirement",
    indexes = {
        @Index(
            name = "idx_business_requirement_document_id",
            columnList = "document_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class BusinessRequirement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "document_id",
        nullable = false
    )
    private AnalysisDocument document;

    @Column(
        name = "title",
        nullable = false,
        length = 255
    )
    private String title;

    @Column(
        name = "description",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "priority",
        nullable = false,
        length = 20
    )
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(
        name = "complexity",
        nullable = false,
        length = 20
    )
    private Complexity complexity;

    @OneToMany(
        mappedBy = "requirement",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("id ASC")
    private List<DevelopmentTask> tasks = new ArrayList<>();

    public void addTask(DevelopmentTask task) {
        tasks.add(task);
        task.setRequirement(this);
    }

    public void removeTask(DevelopmentTask task) {
        tasks.remove(task);
        task.setRequirement(null);
    }

    public void clearTasks() {
        for (DevelopmentTask task : tasks) {
            task.setRequirement(null);
        }

        tasks.clear();
    }
}