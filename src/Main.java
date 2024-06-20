import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>(Arrays.asList(
                new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
                new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
                new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
                new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
                new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
                new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
                new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
                new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
                new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
                new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 Remover o funcionário “João” da lista.
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 Imprimir todos os funcionários com todas suas informações.
        System.out.println("3.3 - Lista de todos os funcionários:");
        imprimirFuncionarios(funcionarios);

        // 3.4 Aumento de 10% no salário.
        System.out.println("\n3.4 - Funcionários após aumento de 10% no salário:");
        funcionarios.forEach(funcionario -> {
            BigDecimal novoSalario = funcionario.getSalario().multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
        });
        imprimirFuncionarios(funcionarios);

        // 3.5 Agrupar os funcionários por função em um MAP.
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 Imprimir os funcionários, agrupados por função.
        System.out.println("\n3.6 - Funcionários agrupados por função:");
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        // 3.8 Imprimir os funcionários que fazem aniversário no mês 10 e 12.
        System.out.println("\n3.8 - Funcionários que fazem aniversário nos meses 10 e 12:");
        List<Funcionario> aniversariantes = funcionarios.stream()
                .filter(funcionario -> {
                    int mes = funcionario.getDataNascimento().getMonthValue();
                    return mes == 10 || mes == 12;
                }).collect(Collectors.toList());
        imprimirFuncionarios(aniversariantes);

        // 3.9 Imprimir o funcionário com a maior idade.
        System.out.println("\n3.9 - Funcionário com a maior idade:");
        Funcionario funcionarioMaisVelho = Collections.max(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        int idade = LocalDate.now().getYear() - funcionarioMaisVelho.getDataNascimento().getYear();
        System.out.println("Nome: " + funcionarioMaisVelho.getNome() + ", Idade: " + idade);

        // 3.10 Imprimir a lista de funcionários por ordem alfabética.
        System.out.println("\n3.10 - Lista de funcionários por ordem alfabética:");
        List<Funcionario> funcionariosOrdenados = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
        imprimirFuncionarios(funcionariosOrdenados);

        // 3.11 Imprimir o total dos salários dos funcionários.
        System.out.println("\n3.11 - Total dos salários dos funcionários:");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: " + formatarSalario(totalSalarios));

        // 3.12 Imprimir quantos salários mínimos ganha cada funcionário.
        System.out.println("\n3.12 - Quantidade de salários mínimos que cada funcionário ganha:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + " ganha " + quantidadeSalariosMinimos + " salários mínimos.");
        });
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00");
        for (Funcionario funcionario : funcionarios) {
            String dataNascimento = funcionario.getDataNascimento().format(dateFormatter);
            String salarioFormatado = decimalFormatter.format(funcionario.getSalario());
            System.out.println("Nome: " + funcionario.getNome() + ", Data de Nascimento: " + dataNascimento +
                    ", Salário: " + salarioFormatado + ", Função: " + funcionario.getFuncao());
        }
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        funcionariosPorFuncao.forEach((funcao, listaFuncionarios) -> {
            System.out.println("Função: " + funcao);
            imprimirFuncionarios(listaFuncionarios);
        });
    }

    private static String formatarSalario(BigDecimal salario) {
        DecimalFormat decimalFormatter = new DecimalFormat("#,##0.00");
        return decimalFormatter.format(salario);
    }
}