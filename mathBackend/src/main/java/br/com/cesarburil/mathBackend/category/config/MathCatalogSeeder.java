package br.com.cesarburil.mathBackend.category.config;

import br.com.cesarburil.mathBackend.category.model.Category;
import br.com.cesarburil.mathBackend.category.repository.CategoryRepository;
import br.com.cesarburil.mathBackend.lesson.model.Lesson;
import br.com.cesarburil.mathBackend.lesson.repository.LessonRepository;
import br.com.cesarburil.mathBackend.question.model.Alternative;
import br.com.cesarburil.mathBackend.question.model.Difficulty;
import br.com.cesarburil.mathBackend.question.model.Question;
import br.com.cesarburil.mathBackend.question.repository.QuestionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class MathCatalogSeeder implements CommandLineRunner {

    private static final String VIDEO = "https://www.youtube.com/watch?v=wt-cMyLvMfc";

    private final CategoryRepository categoryRepository;
    private final LessonRepository lessonRepository;
    private final QuestionRepository questionRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public MathCatalogSeeder(
            CategoryRepository categoryRepository,
            LessonRepository lessonRepository,
            QuestionRepository questionRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.lessonRepository = lessonRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (!catalogReady()) {
            entityManager.createNativeQuery(
                    "TRUNCATE TABLE alternatives, questions, lessons, categories RESTART IDENTITY CASCADE"
            ).executeUpdate();
            entityManager.clear();
            seedCatalog();
        }
        if (questionRepository.count() == 0) {
            seedQuestions();
        }
    }

    private boolean catalogReady() {
        return categoryRepository.count() == 10
                && lessonRepository.count() >= 60
                && categoryRepository.findAll().stream()
                .anyMatch(category -> "Matemática financeira".equals(category.getTitle()));
    }

    private void seedCatalog() {
        catalog().forEach((categoryTitle, lessons) -> {
            Category category = categoryRepository.save(Category.builder().title(categoryTitle).build());
            for (String[] lesson : lessons) {
                lessonRepository.save(Lesson.builder()
                        .title(lesson[0])
                        .description(lesson[1])
                        .video(VIDEO)
                        .category(category)
                        .build());
            }
        });
    }

    private void seedQuestions() {
        Map<String, Category> byTitle = categoryRepository.findAll().stream()
                .collect(Collectors.toMap(Category::getTitle, Function.identity(), (a, b) -> a, LinkedHashMap::new));
        for (QuestionSeed seed : questionBank()) {
            Category category = byTitle.get(seed.category());
            if (category == null) {
                throw new IllegalStateException("Categoria não encontrada para o exercício: " + seed.category());
            }
            Question question = new Question();
            question.setTitle(seed.title());
            question.setCategory(category);
            question.setDifficulty(seed.difficulty());
            question.setVideo(VIDEO);
            question.setAlternatives(new ArrayList<>());
            for (int i = 0; i < seed.alternatives().length; i++) {
                question.getAlternatives().add(Alternative.builder()
                        .title(seed.alternatives()[i])
                        .correct(i == seed.correctIndex())
                        .question(question)
                        .build());
            }
            questionRepository.save(question);
        }
    }

    private Map<String, List<String[]>> catalog() {
        Map<String, List<String[]>> data = new LinkedHashMap<>();
        data.put("Aritmética e proporcionalidade", List.of(
                lesson("Números naturais e inteiros", "O que são, como se operam e onde aparecem nas questões."),
                lesson("Frações e números decimais", "Equivalência, comparação e operações com partes de um inteiro."),
                lesson("Potenciação e radiciação", "Propriedades de potências e raízes no cálculo numérico."),
                lesson("Razão e proporção", "Grandezas proporcionais e regra de três simples."),
                lesson("Porcentagem", "Aumentos, descontos e variação percentual."),
                lesson("Grandezas direta e inversamente proporcionais", "Como reconhecer o tipo de proporção no enunciado."),
                lesson("Média aritmética simples", "Cálculo de médias em listas e situações do Enem.")
        ));
        data.put("Álgebra", List.of(
                lesson("Expressões algébricas", "Simplificar, reduzir termos semelhantes e substituir valores."),
                lesson("Produtos notáveis", "Quadrado da soma, da diferença e produto da soma pela diferença."),
                lesson("Fatoração", "Fator comum, agrupamento e trinômio quadrado perfeito."),
                lesson("Equações do 1º grau", "Isolar a incógnita e interpretar a solução."),
                lesson("Inequações do 1º grau", "Intervalos e representação na reta."),
                lesson("Sistemas lineares 2x2", "Substituição, adição e leitura gráfica."),
                lesson("Equações do 2º grau", "Fórmula de Bhaskara, discriminante e raízes."),
                lesson("Equações irracionais e biquadradas", "Substituição e domínio das raízes.")
        ));
        data.put("Funções", List.of(
                lesson("O que é uma função", "Domínio, contradomínio, imagem e lei de formação."),
                lesson("Função afim", "Coeficiente angular, raiz e gráfico da reta."),
                lesson("Função quadrática", "Concavidade, vértice e pontos de máximo ou mínimo."),
                lesson("Função exponencial", "Crescimento, decrescimento e equações exponenciais."),
                lesson("Função logarítmica", "Definição de logaritmo e mudança de base."),
                lesson("Função definida por partes", "Leitura de gráficos com mais de uma lei."),
                lesson("Composição e inversa", "Quando existe inversa e como encontrá-la."),
                lesson("Função modular", "Gráfico em V e equações com módulo."),
                lesson("Sequências e lei de recorrência", "Ler o próximo termo a partir da lei.")
        ));
        data.put("Geometria plana", List.of(
                lesson("Ângulos e paralelismo", "Correspondentes, alternos e soma dos internos."),
                lesson("Triângulos", "Classificação, soma dos ângulos e casos de congruência."),
                lesson("Teorema de Pitágoras", "Triângulo retângulo e aplicações em medidas."),
                lesson("Semelhança de triângulos", "Razão de semelhança e Tales."),
                lesson("Quadriláteros", "Paralelogramo, retângulo, losango e trapézio."),
                lesson("Áreas de figuras planas", "Triângulo, círculo, polígonos e figuras compostas.")
        ));
        data.put("Geometria espacial", List.of(
                lesson("Prismas e pirâmides", "Elementos, área e volume."),
                lesson("Cilindro e cone", "Geratriz, área lateral e volume."),
                lesson("Esfera", "Área da superfície e volume."),
                lesson("Troncos", "Tronco de pirâmide e de cone."),
                lesson("Vistas e planificações", "Ler sólidos a partir de vistas no plano.")
        ));
        data.put("Trigonometria", List.of(
                lesson("Razões no triângulo retângulo", "Seno, cosseno e tangente dos ângulos notáveis."),
                lesson("Ciclo trigonométrico", "Arcos, quadrantes e redução ao primeiro."),
                lesson("Seno e cosseno de qualquer arco", "Sinal e valor no ciclo."),
                lesson("Lei dos senos", "Relação entre lados e ângulos em qualquer triângulo."),
                lesson("Lei dos cossenos", "Quando usar no lugar de Pitágoras."),
                lesson("Identidades fundamentais", "Relação fundamental e arco duplo na prática."),
                lesson("Equações trigonométricas simples", "Resolver seno e cosseno iguais a um número."),
                lesson("Funções seno e cosseno", "Amplitude, período e deslocamento.")
        ));
        data.put("Análise combinatória", List.of(
                lesson("Princípio fundamental da contagem", "Árvore de possibilidades e produto de escolhas."),
                lesson("Permutação simples", "Arrumar n objetos distintos."),
                lesson("Permutação com repetição", "Quando há elementos iguais."),
                lesson("Arranjo", "Ordem importa: filas, senhas e pódios."),
                lesson("Combinação", "Ordem não importa: comissões e times."),
                lesson("Combinação complementar", "Contar o que falta para simplificar.")
        ));
        data.put("Probabilidade", List.of(
                lesson("Espaço amostral e eventos", "Eventos certos, impossíveis e complementares."),
                lesson("Probabilidade clássica", "Casos favoráveis sobre casos possíveis."),
                lesson("União de eventos", "Com e sem interseção."),
                lesson("Probabilidade condicional", "O que muda quando uma informação chega."),
                lesson("Eventos independentes", "Produto das probabilidades."),
                lesson("Ensaios sucessivos", "Com reposição e sem reposição."),
                lesson("Binomial na prática", "Sucesso ou fracasso em n tentativas.")
        ));
        data.put("Estatística", List.of(
                lesson("Tabelas e gráficos", "Barras, linhas, setores e histogramas."),
                lesson("Média, moda e mediana", "Quando cada medida representa melhor o conjunto."),
                lesson("Amplitude e desvio", "Dispersão sem fórmula pesada demais."),
                lesson("Frequência e classes", "Distribuição de dados agrupados."),
                lesson("Leitura crítica de dados", "Eixos, amostras e o que o gráfico esconde.")
        ));
        data.put("Matemática financeira", List.of(
                lesson("Juros simples", "Capital, taxa, tempo e montante."),
                lesson("Juros compostos", "Por que o juro rende sobre juro."),
                lesson("Taxas equivalentes", "Ao mês, ao ano e conversão."),
                lesson("Descontos", "Comercial e racional na prática de prova."),
                lesson("Sequências uniformes", "Prestações iguais e valor presente."),
                lesson("Sistemas de amortização", "Ideia de SAC e Price, sem planilha."),
                lesson("Inflação e poder de compra", "Correção e perda real."),
                lesson("Investimentos simples", "Comparar duas aplicações no papel."),
                lesson("Financiamento de um bem", "Entrada, parcelas e custo efetivo."),
                lesson("Revisão com problemas do Enem", "Enunciados longos e extração dos dados.")
        ));
        return data;
    }

    private static String[] lesson(String title, String description) {
        return new String[]{title, description};
    }

    private List<QuestionSeed> questionBank() {
        return List.of(
                question("Aritmética e proporcionalidade",
                        "Uma camiseta custa R$ 80,00 e a loja oferece 20% de desconto. Qual é o preço pago?",
                        Difficulty.EASY, 1,
                        "R$ 60,00", "R$ 64,00", "R$ 16,00", "R$ 96,00", "R$ 70,00"),
                question("Aritmética e proporcionalidade",
                        "Um produto de R$ 200,00 sofre aumento de 20% e, em seguida, desconto de 20%. Qual é o preço final?",
                        Difficulty.HARD, 0,
                        "R$ 192,00", "R$ 200,00", "R$ 160,00", "R$ 240,00", "R$ 180,00"),
                question("Álgebra",
                        "Qual é a solução da equação 3x - 7 = 8?",
                        Difficulty.EASY, 2,
                        "x = 1", "x = 3", "x = 5", "x = 15", "x = -5"),
                question("Álgebra",
                        "Quais são as raízes da equação x² - 5x + 6 = 0?",
                        Difficulty.MEDIUM, 0,
                        "2 e 3", "1 e 6", "-2 e -3", "0 e 5", "1 e 5"),
                question("Funções",
                        "Se f(x) = 2x + 1, quanto vale f(3)?",
                        Difficulty.EASY, 3,
                        "5", "6", "8", "7", "9"),
                question("Funções",
                        "A abscissa do vértice da parábola f(x) = x² - 4x + 3 é:",
                        Difficulty.MEDIUM, 1,
                        "x = 1", "x = 2", "x = 3", "x = -2", "x = 4"),
                question("Geometria plana",
                        "Os catetos de um triângulo retângulo medem 6 cm e 8 cm. Qual é a medida da hipotenusa?",
                        Difficulty.EASY, 0,
                        "10 cm", "14 cm", "7 cm", "12 cm", "9 cm"),
                question("Geometria plana",
                        "Qual é a área de um círculo de raio 3 cm?",
                        Difficulty.MEDIUM, 2,
                        "6π cm²", "3π cm²", "9π cm²", "12π cm²", "9 cm²"),
                question("Geometria espacial",
                        "Qual é o volume de um cubo de aresta 4 cm?",
                        Difficulty.EASY, 4,
                        "16 cm³", "48 cm³", "12 cm³", "32 cm³", "64 cm³"),
                question("Geometria espacial",
                        "Um cilindro circular reto tem raio 3 cm e altura 4 cm. Qual é o seu volume?",
                        Difficulty.MEDIUM, 1,
                        "12π cm³", "36π cm³", "24π cm³", "9π cm³", "48π cm³"),
                question("Trigonometria",
                        "Qual é o valor de sen 30°?",
                        Difficulty.EASY, 0,
                        "1/2", "√3/2", "√2/2", "1", "0"),
                question("Trigonometria",
                        "Em um triângulo retângulo, o cateto oposto a um ângulo mede 3 e a hipotenusa mede 5. O seno desse ângulo é:",
                        Difficulty.MEDIUM, 2,
                        "4/5", "3/4", "3/5", "5/3", "4/3"),
                question("Análise combinatória",
                        "De quantas maneiras 4 livros distintos podem ser alinhados em uma prateleira?",
                        Difficulty.EASY, 3,
                        "4", "8", "16", "24", "12"),
                question("Análise combinatória",
                        "De um grupo de 5 pessoas, quantas comissões de 2 pessoas podem ser formadas?",
                        Difficulty.MEDIUM, 1,
                        "20", "10", "5", "15", "25"),
                question("Probabilidade",
                        "Ao lançar um dado honesto de 6 faces, a probabilidade de sair uma face par é:",
                        Difficulty.EASY, 0,
                        "1/2", "1/6", "1/3", "2/3", "1/4"),
                question("Probabilidade",
                        "Uma urna tem 3 bolas vermelhas e 2 azuis. Retirando uma bola ao acaso, a probabilidade de ser vermelha é:",
                        Difficulty.MEDIUM, 4,
                        "2/5", "1/2", "1/5", "3/2", "3/5"),
                question("Estatística",
                        "A média aritmética das notas 4, 6 e 8 é:",
                        Difficulty.EASY, 1,
                        "4", "6", "8", "5", "7"),
                question("Estatística",
                        "No conjunto 2, 2, 3, 5, 8, a mediana é:",
                        Difficulty.MEDIUM, 2,
                        "2", "5", "3", "4", "8"),
                question("Matemática financeira",
                        "Um capital de R$ 1.000,00 é aplicado a juros simples de 2% ao mês durante 3 meses. Os juros desse período são:",
                        Difficulty.EASY, 0,
                        "R$ 60,00", "R$ 1.060,00", "R$ 20,00", "R$ 120,00", "R$ 30,00"),
                question("Matemática financeira",
                        "Um capital de R$ 1.000,00 é aplicado a juros compostos de 10% ao ano durante 2 anos. O montante ao final é:",
                        Difficulty.MEDIUM, 3,
                        "R$ 1.200,00", "R$ 1.100,00", "R$ 2.000,00", "R$ 1.210,00", "R$ 1.020,00")
        );
    }

    private static QuestionSeed question(
            String category,
            String title,
            Difficulty difficulty,
            int correctIndex,
            String... alternatives
    ) {
        return new QuestionSeed(category, title, difficulty, correctIndex, alternatives);
    }

    private record QuestionSeed(
            String category,
            String title,
            Difficulty difficulty,
            int correctIndex,
            String[] alternatives
    ) {
    }
}
