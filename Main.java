import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

// 1. Classe Principal obrigatória (Main) logo no começo do arquivo
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int totalItens = 2; // Quantidade de itens para teste
        
        Produto[] estoque = new Produto[totalItens];
        Pet[] petsClientes = new Pet[totalItens];
        
        System.out.println(" 🐾 -------------------------------------------------- 🐾 ");
        System.out.println("      ✨ BEM-VINDAS AO SISTEMA PETISCOS & CIA! ✨      ");
        System.out.println("        Módulo Backend & Lógica Integrada ao Sistema   ");
        System.out.println(" 🐾 -------------------------------------------------- 🐾 ");
        
        // Cadastro de Pets
        System.out.println("\n🐾 --- CADASTRO DOS PETS (CLIENTES) --- 🐾");
        for (int i = 0; i < totalItens; i++) {
            System.out.println("\n💖 Cadastro do " + (i + 1) + "º animalzinho:");
            System.out.print("🐶 Nome do Pet: ");
            String nomePet = scanner.nextLine();
            System.out.print("🐾 Espécie (ex: Cão, Gato): ");
            String especie = scanner.nextLine();
            System.out.print("🧬 Raça: ");
            String raca = scanner.nextLine();
            System.out.print("🎂 Idade do pet (anos): ");
            int idade = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer
            
            petsClientes[i] = new Pet(nomePet, especie, raca, idade);
        }
        
        // Cadastro de Produtos
        System.out.println("\n📦 --- CADASTRO DO ESTOQUE (PRODUTOS A KG) --- 📦");
        for (int i = 0; i < totalItens; i++) {
            System.out.println("\n💖 Cadastro do " + (i + 1) + "º produtinho:");
            System.out.print("🐾 Nome do produto (ex: Ração Especial): ");
            String nomeProd = scanner.nextLine();
            System.out.print("📅 Data de validade (DD/MM/AAAA): ");
            String dataVal = scanner.nextLine();
            System.out.print("💰 Preço por KG em R$: ");
            double precoKg = scanner.nextDouble();
            System.out.print("⚖️ Quantidade em KG no estoque: ");
            double pesoKg = scanner.nextDouble();
            scanner.nextLine(); // Limpa o buffer
            
            estoque[i] = new Produto(nomeProd, dataVal, precoKg, pesoKg);
        }
        
        // Menu de Opções Interativo com do-while e switch-case
        int opcao = 0;
        do {
            System.out.println("\n==================================================");
            System.out.println("              MENU DE RELATÓRIOS SISTEMA          ");
            System.out.println("==================================================");
            System.out.println("1️⃣ - Ver Lista de Pets Cadastrados");
            System.out.println("2️⃣ - Ver Relatório de Validade e Estoque (Produtos)");
            System.out.println("3️⃣ - Sair do Sistema");
            System.out.print("👉 Escolha uma opção: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
                case 1:
                    System.out.println("\n 🌸 --- LISTA DE CLIENTES DE 4 PATAS --- 🌸");
                    for (int i = 0; i < totalItens; i++) {
                        petsClientes[i].exibirFichaPet();
                        System.out.println(" ----------------------------------------------------- ");
                    }
                    break;
                    
                case 2:
                    System.out.println("\n 🌸 --- RELATÓRIO INTELIGENTE DE ESTOQUE --- 🌸");
                    for (int i = 0; i < totalItens; i++) {
                        estoque[i].analisarProduto();
                    }
                    break;
                    
                case 3:
                    System.out.println("\n✨ Agradecemos por usar o Petiscos & Cia! Até logo! ✨");
                    break;
                    
                default:
                    System.out.println("\n❌ Opção inválida! Escolha 1, 2 ou 3.");
            }
        } while (opcao != 3);
        
        scanner.close();
    }
}

// 2. Classe Produto com Encapsulamento, Construtor, 'this' e Métodos Próprios
class Produto {
    private String nome;
    private String dataValidadeStr;
    private double precoPorKg;
    private double pesoKg;
    
    public Produto(String nome, String dataValidadeStr, double precoPorKg, double pesoKg) {
        this.nome = nome;
        this.dataValidadeStr = dataValidadeStr;
        this.precoPorKg = precoPorKg;
        this.pesoKg = pesoKg;
    }
    
    public String getNome() { return this.nome; }
    public double getPesoKg() { return this.pesoKg; }
    public double getPrecoPorKg() { return this.precoPorKg; }
    
    public long calcularDiasRestantes() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataValidade = LocalDate.parse(this.dataValidadeStr, formato);
        LocalDate hoje = LocalDate.now();
        return ChronoUnit.DAYS.between(hoje, dataValidade);
    }
    
    public double calcularValorTotalEstoque() {
        return this.precoPorKg * this.pesoKg;
    }
    
    public void analisarProduto() {
        long dias = calcularDiasRestantes();
        double valorTotal = calcularValorTotalEstoque();
        
        if (dias < 0) {
            System.out.println("\n🚨 [ALERTA DE SEGURANÇA] Produto: " + this.nome);
            System.out.println("🐾 Quantidade: " + this.pesoKg + " kg | Prejuízo: R$ " + String.format("%.2f", valorTotal));
            System.out.println("💡 AÇÃO: Ops! Venceu há " + Math.abs(dias) + " dias. Retirar da prateleira!");
            
        } else if (dias <= 5) {
            double precoPromoKg = this.precoPorKg * 0.5;
            System.out.println("\n⚡ [QUEIMA DE ESTOQUE URGENTE!] Produto: " + this.nome);
            System.out.println("🐾 Validade: Vence em " + dias + " dias! (" + this.pesoKg + " kg)");
            System.out.println("💖 AÇÃO: Super Desconto de 50% off!");
            System.out.println("✨ Novo Preço por KG: R$ " + String.format("%.2f", precoPromoKg));
            
        } else if (dias <= 15) {
            double precoPromoKg = this.precoPorKg * 0.8;
            System.out.println("\n🎀 [ATENÇÃO CARINHOSA] Produto: " + this.nome);
            System.out.println("🐾 Validade: Faltam " + dias + " dias. (" + this.pesoKg + " kg)");
            System.out.println("💡 AÇÃO: Promoção leve de 20% off!");
            System.out.println("✨ Novo Preço por KG: R$ " + String.format("%.2f", precoPromoKg));
            
        } else {
            System.out.println("\n🌸 [TUDO PERFEITO] Produto: " + this.nome);
            System.out.println("🐾 Validade: Super segura! (" + dias + " dias tranquilos)");
            System.out.println("✨ Preço atual por KG: R$ " + String.format("%.2f", this.precoPorKg) + " (" + this.pesoKg + " kg)");
            System.out.println("💖 AÇÃO: Estoque feliz e seguro!");
        }
        System.out.println(" ----------------------------------------------------- ");
    }
}

// 3. Classe Pet logo abaixo
class Pet {
    private String nomePet;
    private String especie;
    private String raca;
    private int idade;
    
    public Pet(String nomePet, String especie, String raca, int idade) {
        this.nomePet = nomePet;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
    }
    
    public void exibirFichaPet() {
        System.out.println("🐶🐱 Nome do Pet: " + this.nomePet + " (" + this.especie + " - " + this.raca + ")");
        System.out.println("🎂 Idade: " + this.idade + " ano(s) de muita fofura!");
    }
}
