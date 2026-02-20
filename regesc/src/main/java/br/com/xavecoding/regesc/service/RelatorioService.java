package br.com.xavecoding.regesc.service;

import br.com.xavecoding.regesc.orm.Aluno;
import br.com.xavecoding.regesc.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class RelatorioService {
    private AlunoRepository alunoRepository;

    public RelatorioService(AlunoRepository alunoRepository){
        this.alunoRepository = alunoRepository;
    }

    public void menu(Scanner scanner){
        Boolean isTrue = true;
        while(isTrue){
            System.out.println("\n Qual ação você deseja executar? ");
            System.out.println("0- Voltar ao menu anterior");
            System.out.println("1- Relatório de alunos por um dado nome");
            System.out.println("2- Relatório de alunos por um dado nome e idade (menor igual)");
            System.out.println("3- Relatório de alunos por um dado nome e idade (maior igual)");
            System.out.println("4- Relatório de alunos matriculados com um dado nome e idade (maior igual)");

            int opcao = scanner.nextInt();
            switch(opcao){
                case 1:
                    alunosPorNome(scanner);
                    break;
                case 2:
                    alunosPorNomeAndAge(scanner);
                    break;
                case 3:
                    alunosPorNameAndAge(scanner);
                    break;
                case 4:
                    alunosPorNomeMateria(scanner);
                    break;
                default:
                    isTrue = false;
                    break;
            }
        }
        System.out.println();
    }

    private void alunosPorNome(Scanner scanner){
        System.out.print("Nome: ");
        String nome = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWith(nome);
        alunos.forEach(System.out::println);
    }

    private void alunosPorNomeAndAge(Scanner scanner){
        System.out.println("Informe o nome: ");
        String nome = scanner.next();

        System.out.println("Informe a idade: ");
        int idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findByNomeStartingWithAndIdadeLessThanEqual(nome, idade);
        alunos.forEach(System.out::println);
    }

    private void alunosPorNameAndAge(Scanner scanner){
        System.out.println("Nome: ");
        String nome = scanner.next();
        System.out.println("Idade: ");
        Integer idade = scanner.nextInt();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaior(nome, idade);
        alunos.forEach(System.out::println);

    }

    private void alunosPorNomeMateria(Scanner scanner){
        System.out.println("Nome: ");
        String nome = scanner.next();
        System.out.println("Idade: ");
        Integer idade = scanner.nextInt();
        System.out.println("Disciplina: ");
        String nomeDisciplina = scanner.next();

        List<Aluno> alunos = this.alunoRepository.findNomeIdadeIgualOuMaiorMatriculado(nome,idade,nomeDisciplina);
        alunos.forEach(System.out::println);
    }


}
