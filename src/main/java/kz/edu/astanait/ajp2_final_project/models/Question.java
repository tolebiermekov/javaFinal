package kz.edu.astanait.ajp2_final_project.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "QuestionEntity")
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question")
    private String question;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "answer_id")
    private Answer answer;
}
