package com.company.reqai.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "test_scenario",
    indexes = {
        @Index(
            name = "idx_test_scenario_task_id",
            columnList = "task_id"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class TestScenario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(
        fetch = FetchType.LAZY,
        optional = false
    )
    @JoinColumn(
        name = "task_id",
        nullable = false
    )
    private DevelopmentTask task;

    @Column(
        name = "title",
        nullable = false,
        length = 255
    )
    private String title;

    @Column(
        name = "expected_result",
        nullable = false,
        columnDefinition = "TEXT"
    )
    private String expectedResult;
}