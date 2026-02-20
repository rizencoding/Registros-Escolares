package br.com.xavecoding.regesc.service;

import br.com.xavecoding.regesc.orm.Disciplina;
import br.com.xavecoding.regesc.orm.Professor;
import br.com.xavecoding.regesc.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class CrudProfessorService {
    private ProfessorRepository professorRepository;

    public CrudProfessorService(ProfessorRepository professorRepository){
        this.professorRepository = professorRepository;
    }



    @Transactional
    public void menu(Scanner scanner){
        boolean isTrue = true;

        while(isTrue){
            System.out.println("\n Qual ação você quer executar? ");
            System.out.println("0 - Voltar ao menu anterior");
            System.out.println("1- Cadastrar novo professor");
            System.out.println("2- Atualizar um professor");
            System.out.println("3- Listar todos os professores");
            System.out.println("4- Deletar um professor");
            System.out.println("5- Deletar todos professores");
            System.out.println("6- Visualizar um professor");
            int opcao = scanner.nextInt();

            switch(opcao){
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
                    deletar(scanner);
                    break;
                case 5:
                    deletarTodos();
                    break;
                case 6:
                    visualizarProfessor(scanner);
                    break;
                default:
                    isTrue = false;
                break;
            }
        }

    }

    private void cadastrar(Scanner scanner){
        System.out.print("Digite o nome do professor: ");
        String nome = scanner.next();
        System.out.print("Digite o prontuário do professor: ");
        String prontuario = scanner.next();

        Professor p1 = new Professor(nome, prontuario);
        this.professorRepository.save(p1);
        System.out.println("Professor salvo com sucesso.\n");
    }

    private void atualizar(Scanner scanner){
        System.out.println("Digite o ID do professor a ser atualizado: ");
        Long id = scanner.nextLong();

        Optional<Professor>optional = this.professorRepository.findById(id);

        if (optional.isPresent()){
            Professor professor = optional.get();
            System.out.print("Digite o nome do professor: ");
            String nome = scanner.next();
            System.out.print("Digite o prontuário do professor: ");
            String prontuario = scanner.next();

            professor.setNome(nome);
            professor.setProntuario(prontuario);

            professorRepository.save(professor);
        }else{
            System.out.println("O ID informado é inválido.");
        }
    }

    private void listar(){
        Iterable<Professor> professores = this.professorRepository.findAll();
        professores.forEach(professor -> {
            System.out.println(professor);
        });
        System.out.println();
    }

    private void deletar(Scanner scanner){
        System.out.println("Informe o ID do professor a ser deletado: ");
        Long id = scanner.nextLong();

        Optional<Professor>del = this.professorRepository.findById(id);
        if (del.isPresent()){
            this.professorRepository.deleteById(id);
            System.out.println("Deletado com sucesso.");
        }else{
            System.out.println("Professor não encontrado.");
        }

    }
    private void deletarTodos(){
        this.professorRepository.deleteAll();
        System.out.println("Deletado tudo.");
    }
    @Transactional
    private void visualizarProfessor(Scanner scanner){
        System.out.println("ID do professor a ser visualizado: ");
        Long id = scanner.nextLong();
        Optional<Professor> optional = professorRepository.findById(id);
        if (optional.isPresent()){
            Professor professor = optional.get();

            System.out.println("Professor: {");
            System.out.println("ID: "+professor.getId());
            System.out.println("Nome: "+professor.getNome());
            System.out.println("Prontuário: "+professor.getProntuario());
            System.out.println("Disciplinas: [");
            for (Disciplina disciplina: professor.getDisciplinas()){
                System.out.println("Nome da disciplina: "+disciplina.getNome());
                System.out.println("ID da disciplina: "+disciplina.getId());
                System.out.println("Semestre da disciplina: "+disciplina.getSem());
                System.out.println();
            }
            System.out.println("]\n}");
        }
    }
}
