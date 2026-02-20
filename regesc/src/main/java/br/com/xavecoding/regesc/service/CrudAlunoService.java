package br.com.xavecoding.regesc.service;

import br.com.xavecoding.regesc.orm.Aluno;
import br.com.xavecoding.regesc.orm.Disciplina;
import br.com.xavecoding.regesc.orm.Professor;
import br.com.xavecoding.regesc.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
@Transactional
public class CrudAlunoService {
    private AlunoRepository alunoRepository;
    public CrudAlunoService(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;
    }

    public void menu(Scanner scanner){
        Boolean isTrue = true;
        while(isTrue){
            System.out.println("\n Qual ação você deseja executar? ");
            System.out.println("0- Voltar ao menu anterior");
            System.out.println("1- Cadastrar um novo aluno");
            System.out.println("2- Listar todos alunos");
            System.out.println("3- Atualizar um aluno");
            System.out.println("4- Deletar um aluno");
            System.out.println("5- Visualizar um aluno");

            int opcao = scanner.nextInt();
            switch(opcao){
                case 1:
                    cadastrar(scanner);
                break;
                case 2:
                    listar();
                break;
                case 3:
                    atualizar(scanner);
                break;
                case 4:
                    deletar(scanner);
                break;
                case 5:
                    visualizarAluno(scanner);
                break;
                default:
                    isTrue = false;
                break;
            }
        }
    }

    public void cadastrar(Scanner scanner){
        System.out.println("Informe o nome do aluno: ");
        String nome = scanner.next();
        System.out.println("Informe a idade do aluno: ");
        int idade = scanner.nextInt();
        Aluno aluno = new Aluno(nome, idade);
        this.alunoRepository.save(aluno);
    }

    public void listar(){
        Iterable<Aluno> alunos = this.alunoRepository.findAll();
        alunos.forEach(aluno -> {
            System.out.println(aluno);
        });
    }

    public void atualizar(Scanner scanner){
        System.out.println("Informe o ID do aluno a ser atualizado: ");
        Long id = scanner.nextLong();
        Optional<Aluno> optional = this.alunoRepository.findById(id);
        if(optional.isPresent()){
            Aluno aluno = optional.get();
            System.out.println("Informe o novo nome: ");
            String nome = scanner.next();
            System.out.println("Informe a nova idade: ");
            int idade = scanner.nextInt();
            aluno.setNome(nome);
            aluno.setIdade(idade);
            this.alunoRepository.save(aluno);
        }else{
            System.out.println("ID inválido.");
        }
    }

    public void deletar(Scanner scanner){
        System.out.println("Informe o ID do aluno a ser deletado: ");
        Long id = scanner.nextLong();
        Optional<Aluno> optional = this.alunoRepository.findById(id);
        if (optional.isPresent()){
            this.alunoRepository.deleteById(id);
            System.out.println("Deletado com sucesso.");
        }else{
            System.out.println("Impossível deletar.");
        }
    }


    public void visualizarAluno(Scanner scanner){
        System.out.println("ID do aluno a ser visualizado: ");
        Long id = scanner.nextLong();
        Optional<Aluno> optional = alunoRepository.findById(id);
        if (optional.isPresent()){
            Aluno aluno = optional.get();

            System.out.println("Aluno: {");
            System.out.println("ID: "+aluno.getId());
            System.out.println("Nome: "+aluno.getNome());
            System.out.println("Idade: "+aluno.getIdade());
            System.out.println("Disciplinas: [");

            if(aluno.getDisciplinas() != null) {
                for (Disciplina disciplina : aluno.getDisciplinas()) {
                    System.out.println("Nome da disciplina: " + disciplina.getNome());
                    System.out.println("ID da disciplina: " + disciplina.getId());
                    System.out.println("Semestre da disciplina: " + disciplina.getSem());
                    System.out.println();
                }
                System.out.println("]\n}");
            }
        }
    }




}
