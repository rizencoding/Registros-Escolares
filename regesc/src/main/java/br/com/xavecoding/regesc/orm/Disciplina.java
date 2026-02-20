package br.com.xavecoding.regesc.orm;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "disciplinas")
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nome;
    private Integer sem;

    public Disciplina(String nome,Integer sem, Professor professor) {
        this.nome = nome;
        this.sem = sem;
        this.professor = professor;
    }
    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = true)
    private Professor professor;

    @ManyToMany(fetch = FetchType.EAGER)
            @JoinTable(name = "disciplina_alunos",
                    joinColumns = @JoinColumn(name = "disciplina_fk"),
                    inverseJoinColumns = @JoinColumn(name = "alunos_fk"))
    private Set<Aluno> alunos;

    public Disciplina(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public Integer getSem() {
        return sem;
    }

    public void setSem(Integer sem) {
        this.sem = sem;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Set<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(Set<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", sem=" + sem +
                ", professor=" + professor +
                ", alunos=" + alunos +
                '}';
    }
}
