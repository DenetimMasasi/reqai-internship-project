package com.company.reqai.entity;

import com.company.reqai.entity.enums.TaskStatus;
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
    name = "development_task",
    indexes = {
        @Index(
            name = "idx_development_task_requirement_id",
            columnList = "requirement_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class DevelopmentTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "requirement_id",
        nullable = false
    )
    private BusinessRequirement requirement;

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
        name = "status",
        nullable = false,
        length = 30
    )
    private TaskStatus status;

    @OneToMany(
        mappedBy = "task",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @OrderBy("id ASC")
    private List<TestScenario> testScenarios = new ArrayList<>();

    public void addTestScenario(TestScenario testScenario) {
        testScenarios.add(testScenario);
        testScenario.setTask(this);
    }

    public void removeTestScenario(TestScenario testScenario) {
        testScenarios.remove(testScenario);
        testScenario.setTask(null);
    }

    public void clearTestScenarios() {
        for (TestScenario testScenario : testScenarios) {
            testScenario.setTask(null);
        }

        testScenarios.clear();
    }
}