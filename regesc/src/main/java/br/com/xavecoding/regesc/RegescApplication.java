package br.com.xavecoding.regesc;


import br.com.xavecoding.regesc.service.CrudAlunoService;
import br.com.xavecoding.regesc.service.CrudDisciplinaService;
import br.com.xavecoding.regesc.service.CrudProfessorService;
import br.com.xavecoding.regesc.service.RelatorioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RegescApplication implements CommandLineRunner {
	private CrudDisciplinaService disciplinaService;
	private CrudProfessorService professorService;
	private CrudAlunoService alunoService;
	private RelatorioService relatorioService;

	public RegescApplication(CrudDisciplinaService disciplinaService,
							 CrudProfessorService professorService,
							 CrudAlunoService alunoService,
							 RelatorioService relatorioService){
		this.disciplinaService = disciplinaService;
		this.professorService = professorService;
		this.alunoService = alunoService;
		this.relatorioService = relatorioService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RegescApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Boolean isTrue = true;
		Scanner scanner = new Scanner(System.in);

		while(isTrue){
			System.out.println("Qual entidade você deseja interagir? ");
			System.out.println("0 - sair");
			System.out.println("1 - Professor");
			System.out.println("2 - Disciplina");
			System.out.println("3 - Aluno");
			System.out.println("4 - Relatórios");

			int opcao = scanner.nextInt();

			switch(opcao){
				case 1:
					this.professorService.menu(scanner);
					break;
				case 2:
					this.disciplinaService.menu(scanner);
					break;
				case 3:
					this.alunoService.menu(scanner);
					break;
				case 4:
					this.relatorioService.menu(scanner);
				default:
					isTrue = false;
					break;

			}
		}

	}


}
