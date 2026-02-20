package br.com.xavecoding.regesc.service;

import br.com.xavecoding.regesc.orm.Aluno;
import br.com.xavecoding.regesc.orm.Disciplina;
import br.com.xavecoding.regesc.orm.Professor;
import br.com.xavecoding.regesc.repository.AlunoRepository;
import br.com.xavecoding.regesc.repository.DisciplinasRepository;
import br.com.xavecoding.regesc.repository.ProfessorRepository;
import jakarta.persistence.PreUpdate;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Transactional
@Service
public class CrudDisciplinaService {
    private DisciplinasRepository disciplinasRepository;
    private ProfessorRepository professorRepository;
    private AlunoRepository alunoRepository;

    public CrudDisciplinaService(DisciplinasRepository disciplinasRepository,
                                 ProfessorRepository professorRepository,
                                 AlunoRepository alunoRepository) {
        this.disciplinasRepository = disciplinasRepository;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
    }

    public void menu(Scanner scanner) {
        Boolean isTrue = true;
        while (isTrue) {
            System.out.println("\n Qual ação você quer executar? ");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1- Cadastrar uma discplina");
            System.out.println("2- Atualizar uma disciplina");
            System.out.println("3- Listar todas disciplinas");
            System.out.println("4- Deletar uma disciplina");
            System.out.println("5- Deletar todas discplinas");
            System.out.println("6- Matricular um aluno");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrar(scanner);
                    break;
                case 2:
                    atualizar(scanner);
                    break;
                case 3:
                    listar();
                    break;
                case 4:
                    excluir(scanner);
                    break;
                case 5:
                    excluirtudo();
                    break;
                case 6:
                    matricularAluno(scanner);
                    break;
                default:
                    isTrue = false;
                    break;
            }


        }
    }

    private void cadastrar(Scanner scanner) {
        System.out.println("Nome da disciplina: ");
        String nome = scanner.next();

        System.out.println("Semestre: ");
        Integer sem = scanner.nextInt();

        System.out.println("ID do professor: ");
        Long professorId = scanner.nextLong();

        Optional<Professor> optional = professorRepository.findById(professorId);
        if (optional.isPresent()) {
            Professor professor = optional.get();
            Disciplina disciplina = new Disciplina(nome, sem, professor);
            disciplinasRepository.save(disciplina);
            System.out.println("Salvo com sucesso.\n");
        } else {
            System.out.println("ID inválido.");
        }
    }

    private void atualizar(Scanner scanner) {
        System.out.println("Insira o ID da disciplina a ser atualizada: ");
        Long id = scanner.nextLong();
        Optional<Disciplina> optional = disciplinasRepository.findById(id);
        if (optional.isPresent()) {
            Disciplina disciplina = optional.get();
            System.out.println("Digite o nome da disciplina: ");
            String nome = scanner.next();
            System.out.println("Digite o semestre da disciplina: ");
            Integer sem = scanner.nextInt();
            System.out.println("Digite o ID do professor: ");
            Long professorId = scanner.nextLong();
            Optional<Professor> prof = professorRepository.findById(professorId);
            if (prof.isPresent()) {
                Professor professor = prof.get();
                disciplina.setNome(nome);
                disciplina.setSem(sem);
                disciplina.setProfessor(professor);
                disciplinasRepository.save(disciplina);
            } else {
                System.out.println("Impossível atualizar professor.");
            }
        } else {
            System.out.println("Impossível atualizar disciplina.");
        }
    }

    private void listar() {
        Iterable<Disciplina> disciplinas = disciplinasRepository.findAll();
        disciplinas.forEach(disciplina -> {
            System.out.println(disciplina);
        });
        System.out.println();
    }

    private void excluir(Scanner scanner) {
        System.out.println("Informe o ID da disciplina a ser deletada: ");
        Long id = scanner.nextLong();
        Optional<Disciplina> del = disciplinasRepository.findById(id);
        if (del.isPresent()) {
            disciplinasRepository.deleteById(id);
            System.out.println("Deletado com sucesso.");
        } else {
            System.out.println("ID inválido.");
        }
    }

    private void excluirtudo() {
        this.disciplinasRepository.deleteAll();
        System.out.println("Todas as disciplinas foram excluídas.");
    }


    private Set<Aluno> matricular(Scanner scanner) {
        Boolean isTrue = true;
        Set<Aluno> alunos = new HashSet<Aluno>();

        while (isTrue) {
            System.out.println("ID do aluno a ser matriculado (digite 0 para sair): ");
            Long alunoId = scanner.nextLong();

            if (alunoId > 0) {
                System.out.println("alunoId: " + alunoId);
                Optional<Aluno> optional = this.alunoRepository.findById(alunoId);
                if (optional.isPresent()) {
                    alunos.add(optional.get());
                    System.out.println("Aluno cadastrado com sucesso!");
                } else {
                    System.out.println("Nenhum aluno possui id " + alunoId + "!");
                }
            } else {
                isTrue = false;
            }
        }
        return alunos;
    }

    private void matricularAluno(Scanner scanner){
        System.out.println("Digite o Id da disciplina para matricular alunos: ");
        Long id = scanner.nextLong();

        Optional<Disciplina> optionalDisciplina = this.disciplinasRepository.findById(id);
        if(optionalDisciplina.isPresent()){
            Disciplina disciplina = optionalDisciplina.get();
            Set<Aluno> novosAlunos = this.matricular(scanner);
            disciplina.getAlunos().addAll(novosAlunos);
            this.disciplinasRepository.save(disciplina);
        }else{
            System.out.println("Disciplina não existente.");
        }
    }
}
