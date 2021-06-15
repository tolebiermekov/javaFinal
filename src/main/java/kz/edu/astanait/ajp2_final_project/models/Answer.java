package kz.edu.astanait.ajp2_final_project.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "AnswerEntity")
@Table(name = "answers")
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long answerId;

    @Column
    private String answerOne;

    @Column
    private String answerTwo;

    @Column
    private String answerThree;

    @Column
    private String answerFour;
}
