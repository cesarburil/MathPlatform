-- Recria o conteúdo de estudo. Questões ligadas a categorias antigas também saem por causa da FK.
TRUNCATE TABLE alternatives, questions, lessons, categories RESTART IDENTITY CASCADE;

INSERT INTO categories (title) VALUES
  ('Aritmética e proporcionalidade'),
  ('Álgebra'),
  ('Funções'),
  ('Geometria plana'),
  ('Geometria espacial'),
  ('Trigonometria'),
  ('Análise combinatória'),
  ('Probabilidade'),
  ('Estatística'),
  ('Matemática financeira');

INSERT INTO lessons (title, description, video, category_id)
SELECT seed.title, seed.description, 'https://www.youtube.com/watch?v=wt-cMyLvMfc', c.id
FROM (VALUES
  ('Aritmética e proporcionalidade', 'Números naturais e inteiros', 'O que são, como se operam e onde aparecem nas questões.'),
  ('Aritmética e proporcionalidade', 'Frações e números decimais', 'Equivalência, comparação e operações com partes de um inteiro.'),
  ('Aritmética e proporcionalidade', 'Potenciação e radiciação', 'Propriedades de potências e raízes no cálculo numérico.'),
  ('Aritmética e proporcionalidade', 'Razão e proporção', 'Grandezas proporcionais e regra de três simples.'),
  ('Aritmética e proporcionalidade', 'Porcentagem', 'Aumentos, descontos e variação percentual.'),
  ('Aritmética e proporcionalidade', 'Grandezas direta e inversamente proporcionais', 'Como reconhecer o tipo de proporção no enunciado.'),
  ('Aritmética e proporcionalidade', 'Média aritmética simples', 'Cálculo de médias em listas e situações do Enem.'),

  ('Álgebra', 'Expressões algébricas', 'Simplificar, reduzir termos semelhantes e substituir valores.'),
  ('Álgebra', 'Produtos notáveis', 'Quadrado da soma, da diferença e produto da soma pela diferença.'),
  ('Álgebra', 'Fatoração', 'Fator comum, agrupamento e trinômio quadrado perfeito.'),
  ('Álgebra', 'Equações do 1º grau', 'Isolar a incógnita e interpretar a solução.'),
  ('Álgebra', 'Inequações do 1º grau', 'Intervalos e representação na reta.'),
  ('Álgebra', 'Sistemas lineares 2x2', 'Substituição, adição e leitura gráfica.'),
  ('Álgebra', 'Equações do 2º grau', 'Fórmula de Bhaskara, discriminante e raízes.'),
  ('Álgebra', 'Equações irracionais e biquadradas', 'Substituição e domínio das raízes.'),

  ('Funções', 'O que é uma função', 'Domínio, contradomínio, imagem e lei de formação.'),
  ('Funções', 'Função afim', 'Coeficiente angular, raiz e gráfico da reta.'),
  ('Funções', 'Função quadrática', 'Concavidade, vértice e pontos de máximo ou mínimo.'),
  ('Funções', 'Função exponencial', 'Crescimento, decrescimento e equações exponenciais.'),
  ('Funções', 'Função logarítmica', 'Definição de logaritmo e mudança de base.'),
  ('Funções', 'Função definida por partes', 'Leitura de gráficos com mais de uma lei.'),
  ('Funções', 'Composição e inversa', 'Quando existe inversa e como encontrá-la.'),
  ('Funções', 'Função modular', 'Gráfico em V e equações com módulo.'),
  ('Funções', 'Sequências e lei de recorrência', 'Ler o próximo termo a partir da lei.'),

  ('Geometria plana', 'Ângulos e paralelismo', 'Correspondentes, alternos e soma dos internos.'),
  ('Geometria plana', 'Triângulos', 'Classificação, soma dos ângulos e casos de congruência.'),
  ('Geometria plana', 'Teorema de Pitágoras', 'Triângulo retângulo e aplicações em medidas.'),
  ('Geometria plana', 'Semelhança de triângulos', 'Razão de semelhança e Tales.'),
  ('Geometria plana', 'Quadriláteros', 'Paralelogramo, retângulo, losango e trapézio.'),
  ('Geometria plana', 'Áreas de figuras planas', 'Triângulo, círculo, polígonos e figuras compostas.'),

  ('Geometria espacial', 'Prismas e pirâmides', 'Elementos, área e volume.'),
  ('Geometria espacial', 'Cilindro e cone', 'Geratriz, área lateral e volume.'),
  ('Geometria espacial', 'Esfera', 'Área da superfície e volume.'),
  ('Geometria espacial', 'Troncos', 'Tronco de pirâmide e de cone.'),
  ('Geometria espacial', 'Vistas e planificações', 'Ler sólidos a partir de vistas no plano.'),

  ('Trigonometria', 'Razões no triângulo retângulo', 'Seno, cosseno e tangente dos ângulos notáveis.'),
  ('Trigonometria', 'Ciclo trigonométrico', 'Arcos, quadrantes e redução ao primeiro.'),
  ('Trigonometria', 'Seno e cosseno de qualquer arco', 'Sinal e valor no ciclo.'),
  ('Trigonometria', 'Lei dos senos', 'Relação entre lados e ângulos em qualquer triângulo.'),
  ('Trigonometria', 'Lei dos cossenos', 'Quando usar no lugar de Pitágoras.'),
  ('Trigonometria', 'Identidades fundamentais', 'Relação fundamental e arco duplo na prática.'),
  ('Trigonometria', 'Equações trigonométricas simples', 'Resolver seno e cosseno iguais a um número.'),
  ('Trigonometria', 'Funções seno e cosseno', 'Amplitude, período e deslocamento.'),

  ('Análise combinatória', 'Princípio fundamental da contagem', 'Árvore de possibilidades e produto de escolhas.'),
  ('Análise combinatória', 'Permutação simples', 'Arrumar n objetos distintos.'),
  ('Análise combinatória', 'Permutação com repetição', 'Quando há elementos iguais.'),
  ('Análise combinatória', 'Arranjo', 'Ordem importa: filas, senhas e pódios.'),
  ('Análise combinatória', 'Combinação', 'Ordem não importa: comissões e times.'),
  ('Análise combinatória', 'Combinação complementar', 'Contar o que falta para simplificar.'),

  ('Probabilidade', 'Espaço amostral e eventos', 'Eventos certos, impossíveis e complementares.'),
  ('Probabilidade', 'Probabilidade clássica', 'Casos favoráveis sobre casos possíveis.'),
  ('Probabilidade', 'União de eventos', 'Com e sem interseção.'),
  ('Probabilidade', 'Probabilidade condicional', 'O que muda quando uma informação chega.'),
  ('Probabilidade', 'Eventos independentes', 'Produto das probabilidades.'),
  ('Probabilidade', 'Ensaios sucessivos', 'Com reposição e sem reposição.'),
  ('Probabilidade', 'Binomial na prática', 'Sucesso ou fracasso em n tentativas.'),

  ('Estatística', 'Tabelas e gráficos', 'Barras, linhas, setores e histogramas.'),
  ('Estatística', 'Média, moda e mediana', 'Quando cada medida representa melhor o conjunto.'),
  ('Estatística', 'Amplitude e desvio', 'Dispersão sem fórmula pesada demais.'),
  ('Estatística', 'Frequência e classes', 'Distribuição de dados agrupados.'),
  ('Estatística', 'Leitura crítica de dados', 'Eixos, amostras e o que o gráfico esconde.'),

  ('Matemática financeira', 'Juros simples', 'Capital, taxa, tempo e montante.'),
  ('Matemática financeira', 'Juros compostos', 'Por que o juro rende sobre juro.'),
  ('Matemática financeira', 'Taxas equivalentes', 'Ao mês, ao ano e conversão.'),
  ('Matemática financeira', 'Descontos', 'Comercial e racional na prática de prova.'),
  ('Matemática financeira', 'Sequências uniformes', 'Prestações iguais e valor presente.'),
  ('Matemática financeira', 'Sistemas de amortização', 'Ideia de SAC e Price, sem planilha.'),
  ('Matemática financeira', 'Inflação e poder de compra', 'Correção e perda real.'),
  ('Matemática financeira', 'Investimentos simples', 'Comparar duas aplicações no papel.'),
  ('Matemática financeira', 'Financiamento de um bem', 'Entrada, parcelas e custo efetivo.'),
  ('Matemática financeira', 'Revisão com problemas do Enem', 'Enunciados longos e extração dos dados.')
) AS seed(category_title, title, description)
JOIN categories c ON c.title = seed.category_title;

INSERT INTO questions (title, difficulty, video, category_id)
SELECT seed.title, seed.difficulty, 'https://www.youtube.com/watch?v=wt-cMyLvMfc', c.id
FROM (VALUES
  ('Aritmética e proporcionalidade', 'Uma camiseta custa R$ 80,00 e a loja oferece 20% de desconto. Qual é o preço pago?', 0),
  ('Aritmética e proporcionalidade', 'Um produto de R$ 200,00 sofre aumento de 20% e, em seguida, desconto de 20%. Qual é o preço final?', 2),
  ('Álgebra', 'Qual é a solução da equação 3x - 7 = 8?', 0),
  ('Álgebra', 'Quais são as raízes da equação x² - 5x + 6 = 0?', 1),
  ('Funções', 'Se f(x) = 2x + 1, quanto vale f(3)?', 0),
  ('Funções', 'A abscissa do vértice da parábola f(x) = x² - 4x + 3 é:', 1),
  ('Geometria plana', 'Os catetos de um triângulo retângulo medem 6 cm e 8 cm. Qual é a medida da hipotenusa?', 0),
  ('Geometria plana', 'Qual é a área de um círculo de raio 3 cm?', 1),
  ('Geometria espacial', 'Qual é o volume de um cubo de aresta 4 cm?', 0),
  ('Geometria espacial', 'Um cilindro circular reto tem raio 3 cm e altura 4 cm. Qual é o seu volume?', 1),
  ('Trigonometria', 'Qual é o valor de sen 30°?', 0),
  ('Trigonometria', 'Em um triângulo retângulo, o cateto oposto a um ângulo mede 3 e a hipotenusa mede 5. O seno desse ângulo é:', 1),
  ('Análise combinatória', 'De quantas maneiras 4 livros distintos podem ser alinhados em uma prateleira?', 0),
  ('Análise combinatória', 'De um grupo de 5 pessoas, quantas comissões de 2 pessoas podem ser formadas?', 1),
  ('Probabilidade', 'Ao lançar um dado honesto de 6 faces, a probabilidade de sair uma face par é:', 0),
  ('Probabilidade', 'Uma urna tem 3 bolas vermelhas e 2 azuis. Retirando uma bola ao acaso, a probabilidade de ser vermelha é:', 1),
  ('Estatística', 'A média aritmética das notas 4, 6 e 8 é:', 0),
  ('Estatística', 'No conjunto 2, 2, 3, 5, 8, a mediana é:', 1),
  ('Matemática financeira', 'Um capital de R$ 1.000,00 é aplicado a juros simples de 2% ao mês durante 3 meses. Os juros desse período são:', 0),
  ('Matemática financeira', 'Um capital de R$ 1.000,00 é aplicado a juros compostos de 10% ao ano durante 2 anos. O montante ao final é:', 1)
) AS seed(category_title, title, difficulty)
JOIN categories c ON c.title = seed.category_title;

INSERT INTO alternatives (title, correct, question_id)
SELECT seed.alt_title, seed.correct, q.id
FROM (VALUES
  ('Uma camiseta custa R$ 80,00 e a loja oferece 20% de desconto. Qual é o preço pago?', 'R$ 60,00', false),
  ('Uma camiseta custa R$ 80,00 e a loja oferece 20% de desconto. Qual é o preço pago?', 'R$ 64,00', true),
  ('Uma camiseta custa R$ 80,00 e a loja oferece 20% de desconto. Qual é o preço pago?', 'R$ 16,00', false),
  ('Uma camiseta custa R$ 80,00 e a loja oferece 20% de desconto. Qual é o preço pago?', 'R$ 96,00', false),
  ('Uma camiseta custa R$ 80,00 e a loja oferece 20% de desconto. Qual é o preço pago?', 'R$ 70,00', false),

  ('Um produto de R$ 200,00 sofre aumento de 20% e, em seguida, desconto de 20%. Qual é o preço final?', 'R$ 192,00', true),
  ('Um produto de R$ 200,00 sofre aumento de 20% e, em seguida, desconto de 20%. Qual é o preço final?', 'R$ 200,00', false),
  ('Um produto de R$ 200,00 sofre aumento de 20% e, em seguida, desconto de 20%. Qual é o preço final?', 'R$ 160,00', false),
  ('Um produto de R$ 200,00 sofre aumento de 20% e, em seguida, desconto de 20%. Qual é o preço final?', 'R$ 240,00', false),
  ('Um produto de R$ 200,00 sofre aumento de 20% e, em seguida, desconto de 20%. Qual é o preço final?', 'R$ 180,00', false),

  ('Qual é a solução da equação 3x - 7 = 8?', 'x = 1', false),
  ('Qual é a solução da equação 3x - 7 = 8?', 'x = 3', false),
  ('Qual é a solução da equação 3x - 7 = 8?', 'x = 5', true),
  ('Qual é a solução da equação 3x - 7 = 8?', 'x = 15', false),
  ('Qual é a solução da equação 3x - 7 = 8?', 'x = -5', false),

  ('Quais são as raízes da equação x² - 5x + 6 = 0?', '2 e 3', true),
  ('Quais são as raízes da equação x² - 5x + 6 = 0?', '1 e 6', false),
  ('Quais são as raízes da equação x² - 5x + 6 = 0?', '-2 e -3', false),
  ('Quais são as raízes da equação x² - 5x + 6 = 0?', '0 e 5', false),
  ('Quais são as raízes da equação x² - 5x + 6 = 0?', '1 e 5', false),

  ('Se f(x) = 2x + 1, quanto vale f(3)?', '5', false),
  ('Se f(x) = 2x + 1, quanto vale f(3)?', '6', false),
  ('Se f(x) = 2x + 1, quanto vale f(3)?', '8', false),
  ('Se f(x) = 2x + 1, quanto vale f(3)?', '7', true),
  ('Se f(x) = 2x + 1, quanto vale f(3)?', '9', false),

  ('A abscissa do vértice da parábola f(x) = x² - 4x + 3 é:', 'x = 1', false),
  ('A abscissa do vértice da parábola f(x) = x² - 4x + 3 é:', 'x = 2', true),
  ('A abscissa do vértice da parábola f(x) = x² - 4x + 3 é:', 'x = 3', false),
  ('A abscissa do vértice da parábola f(x) = x² - 4x + 3 é:', 'x = -2', false),
  ('A abscissa do vértice da parábola f(x) = x² - 4x + 3 é:', 'x = 4', false),

  ('Os catetos de um triângulo retângulo medem 6 cm e 8 cm. Qual é a medida da hipotenusa?', '10 cm', true),
  ('Os catetos de um triângulo retângulo medem 6 cm e 8 cm. Qual é a medida da hipotenusa?', '14 cm', false),
  ('Os catetos de um triângulo retângulo medem 6 cm e 8 cm. Qual é a medida da hipotenusa?', '7 cm', false),
  ('Os catetos de um triângulo retângulo medem 6 cm e 8 cm. Qual é a medida da hipotenusa?', '12 cm', false),
  ('Os catetos de um triângulo retângulo medem 6 cm e 8 cm. Qual é a medida da hipotenusa?', '9 cm', false),

  ('Qual é a área de um círculo de raio 3 cm?', '6π cm²', false),
  ('Qual é a área de um círculo de raio 3 cm?', '3π cm²', false),
  ('Qual é a área de um círculo de raio 3 cm?', '9π cm²', true),
  ('Qual é a área de um círculo de raio 3 cm?', '12π cm²', false),
  ('Qual é a área de um círculo de raio 3 cm?', '9 cm²', false),

  ('Qual é o volume de um cubo de aresta 4 cm?', '16 cm³', false),
  ('Qual é o volume de um cubo de aresta 4 cm?', '48 cm³', false),
  ('Qual é o volume de um cubo de aresta 4 cm?', '12 cm³', false),
  ('Qual é o volume de um cubo de aresta 4 cm?', '32 cm³', false),
  ('Qual é o volume de um cubo de aresta 4 cm?', '64 cm³', true),

  ('Um cilindro circular reto tem raio 3 cm e altura 4 cm. Qual é o seu volume?', '12π cm³', false),
  ('Um cilindro circular reto tem raio 3 cm e altura 4 cm. Qual é o seu volume?', '36π cm³', true),
  ('Um cilindro circular reto tem raio 3 cm e altura 4 cm. Qual é o seu volume?', '24π cm³', false),
  ('Um cilindro circular reto tem raio 3 cm e altura 4 cm. Qual é o seu volume?', '9π cm³', false),
  ('Um cilindro circular reto tem raio 3 cm e altura 4 cm. Qual é o seu volume?', '48π cm³', false),

  ('Qual é o valor de sen 30°?', '1/2', true),
  ('Qual é o valor de sen 30°?', '√3/2', false),
  ('Qual é o valor de sen 30°?', '√2/2', false),
  ('Qual é o valor de sen 30°?', '1', false),
  ('Qual é o valor de sen 30°?', '0', false),

  ('Em um triângulo retângulo, o cateto oposto a um ângulo mede 3 e a hipotenusa mede 5. O seno desse ângulo é:', '4/5', false),
  ('Em um triângulo retângulo, o cateto oposto a um ângulo mede 3 e a hipotenusa mede 5. O seno desse ângulo é:', '3/4', false),
  ('Em um triângulo retângulo, o cateto oposto a um ângulo mede 3 e a hipotenusa mede 5. O seno desse ângulo é:', '3/5', true),
  ('Em um triângulo retângulo, o cateto oposto a um ângulo mede 3 e a hipotenusa mede 5. O seno desse ângulo é:', '5/3', false),
  ('Em um triângulo retângulo, o cateto oposto a um ângulo mede 3 e a hipotenusa mede 5. O seno desse ângulo é:', '4/3', false),

  ('De quantas maneiras 4 livros distintos podem ser alinhados em uma prateleira?', '4', false),
  ('De quantas maneiras 4 livros distintos podem ser alinhados em uma prateleira?', '8', false),
  ('De quantas maneiras 4 livros distintos podem ser alinhados em uma prateleira?', '16', false),
  ('De quantas maneiras 4 livros distintos podem ser alinhados em uma prateleira?', '24', true),
  ('De quantas maneiras 4 livros distintos podem ser alinhados em uma prateleira?', '12', false),

  ('De um grupo de 5 pessoas, quantas comissões de 2 pessoas podem ser formadas?', '20', false),
  ('De um grupo de 5 pessoas, quantas comissões de 2 pessoas podem ser formadas?', '10', true),
  ('De um grupo de 5 pessoas, quantas comissões de 2 pessoas podem ser formadas?', '5', false),
  ('De um grupo de 5 pessoas, quantas comissões de 2 pessoas podem ser formadas?', '15', false),
  ('De um grupo de 5 pessoas, quantas comissões de 2 pessoas podem ser formadas?', '25', false),

  ('Ao lançar um dado honesto de 6 faces, a probabilidade de sair uma face par é:', '1/2', true),
  ('Ao lançar um dado honesto de 6 faces, a probabilidade de sair uma face par é:', '1/6', false),
  ('Ao lançar um dado honesto de 6 faces, a probabilidade de sair uma face par é:', '1/3', false),
  ('Ao lançar um dado honesto de 6 faces, a probabilidade de sair uma face par é:', '2/3', false),
  ('Ao lançar um dado honesto de 6 faces, a probabilidade de sair uma face par é:', '1/4', false),

  ('Uma urna tem 3 bolas vermelhas e 2 azuis. Retirando uma bola ao acaso, a probabilidade de ser vermelha é:', '2/5', false),
  ('Uma urna tem 3 bolas vermelhas e 2 azuis. Retirando uma bola ao acaso, a probabilidade de ser vermelha é:', '1/2', false),
  ('Uma urna tem 3 bolas vermelhas e 2 azuis. Retirando uma bola ao acaso, a probabilidade de ser vermelha é:', '1/5', false),
  ('Uma urna tem 3 bolas vermelhas e 2 azuis. Retirando uma bola ao acaso, a probabilidade de ser vermelha é:', '3/2', false),
  ('Uma urna tem 3 bolas vermelhas e 2 azuis. Retirando uma bola ao acaso, a probabilidade de ser vermelha é:', '3/5', true),

  ('A média aritmética das notas 4, 6 e 8 é:', '4', false),
  ('A média aritmética das notas 4, 6 e 8 é:', '6', true),
  ('A média aritmética das notas 4, 6 e 8 é:', '8', false),
  ('A média aritmética das notas 4, 6 e 8 é:', '5', false),
  ('A média aritmética das notas 4, 6 e 8 é:', '7', false),

  ('No conjunto 2, 2, 3, 5, 8, a mediana é:', '2', false),
  ('No conjunto 2, 2, 3, 5, 8, a mediana é:', '5', false),
  ('No conjunto 2, 2, 3, 5, 8, a mediana é:', '3', true),
  ('No conjunto 2, 2, 3, 5, 8, a mediana é:', '4', false),
  ('No conjunto 2, 2, 3, 5, 8, a mediana é:', '8', false),

  ('Um capital de R$ 1.000,00 é aplicado a juros simples de 2% ao mês durante 3 meses. Os juros desse período são:', 'R$ 60,00', true),
  ('Um capital de R$ 1.000,00 é aplicado a juros simples de 2% ao mês durante 3 meses. Os juros desse período são:', 'R$ 1.060,00', false),
  ('Um capital de R$ 1.000,00 é aplicado a juros simples de 2% ao mês durante 3 meses. Os juros desse período são:', 'R$ 20,00', false),
  ('Um capital de R$ 1.000,00 é aplicado a juros simples de 2% ao mês durante 3 meses. Os juros desse período são:', 'R$ 120,00', false),
  ('Um capital de R$ 1.000,00 é aplicado a juros simples de 2% ao mês durante 3 meses. Os juros desse período são:', 'R$ 30,00', false),

  ('Um capital de R$ 1.000,00 é aplicado a juros compostos de 10% ao ano durante 2 anos. O montante ao final é:', 'R$ 1.200,00', false),
  ('Um capital de R$ 1.000,00 é aplicado a juros compostos de 10% ao ano durante 2 anos. O montante ao final é:', 'R$ 1.100,00', false),
  ('Um capital de R$ 1.000,00 é aplicado a juros compostos de 10% ao ano durante 2 anos. O montante ao final é:', 'R$ 2.000,00', false),
  ('Um capital de R$ 1.000,00 é aplicado a juros compostos de 10% ao ano durante 2 anos. O montante ao final é:', 'R$ 1.210,00', true),
  ('Um capital de R$ 1.000,00 é aplicado a juros compostos de 10% ao ano durante 2 anos. O montante ao final é:', 'R$ 1.020,00', false)
) AS seed(question_title, alt_title, correct)
JOIN questions q ON q.title = seed.question_title;
