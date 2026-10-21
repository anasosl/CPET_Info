<?php
// Processamento do formulário de reservas e serviços
$mensagem_sucesso = "";
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $nome = htmlspecialchars($_POST['nome']);
    $data = htmlspecialchars($_POST['data']);
    $pessoas = htmlspecialchars($_POST['pessoas']);
    $servico = htmlspecialchars($_POST['servico']);

    $mensagem_sucesso = "Obrigado, $nome! Sua solicitação de '$servico' para $pessoas pessoa(s) no dia $data foi recebida.";
}

// Catálogo de produtos para venda
$produtos = [
    [
        "nome" => "Lasanha à Bolonhesa",
        "categoria" => "Massas",
        "descricao" => "Massa artesanal, molho de carne suculento, presunto e queijo.",
        "preco" => 48.90,
        "imagem" => "https://images.unsplash.com/photo-1574894709920-11b28e7367e3?w=400"
    ],
    [
        "nome" => "Fettuccine Alfredo com Camarão",
        "categoria" => "Massas",
        "descricao" => "Fettuccine com molho cremoso Alfredo e camarões salteados.",
        "preco" => 62.00,
        "imagem" => "https://images.unsplash.com/photo-1621996346565-e3def6166739?w=400"
    ],
    [
        "nome" => "Pizza Margherita",
        "categoria" => "Pizzas",
        "descricao" => "Molho de tomate italiano, muçarela de búfala e manjericão fresco.",
        "preco" => 55.00,
        "imagem" => "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?w=400"
    ],
    [
        "nome" => "Tiramisù Clássico",
        "categoria" => "Sobremesas",
        "descricao" => "Sobremesa tradicional com café, mascarpone e cacau em pó.",
        "preco" => 22.50,
        "imagem" => "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400"
    ]
];
?>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cantina Bella Italia</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .hero {
            background: linear-gradient(rgba(0,0,0,0.6), rgba(0,0,0,0.6)), url('https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=1200') center/cover;
            color: white;
            padding: 80px 0;
        }
        .card-img-top { height: 200px; object-fit: cover; }
    </style>
</head>
<body>

    <!-- Navegação -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-danger sticky-top">
        <div class="container">
            <a class="navbar-brand fw-bold fs-3" href="#inicio">Bella Italia</a>
            <div class="navbar-nav ms-auto">
                <a class="nav-link text-white" href="#inicio">Início</a>
                <a class="nav-link text-white" href="#produtos">Produtos</a>
                <a class="nav-link text-white" href="#servicos">Serviços & Reservas</a>
            </div>
        </div>
    </nav>

    <!-- Banner Principal -->
    <header id="inicio" class="hero text-center">
        <div class="container">
            <h1 class="display-4 fw-bold">Cantina Bella Italia</h1>
            <p class="lead">A autêntica culinária italiana com tradição e receitas artesanais.</p>
            <a href="#produtos" class="btn btn-warning fw-bold text-dark me-2">Ver Cardápio</a>
            <a href="#servicos" class="btn btn-outline-light">Agendar Serviço</a>
        </div>
    </header>

    <!-- Seção de Produtos (Vendas) -->
    <section id="produtos" class="container my-5">
        <h2 class="text-center text-danger fw-bold mb-4">Nossos Produtos</h2>
        <div class="row g-4">
            <?php foreach ($produtos as $p): ?>
                <div class="col-md-3">
                    <div class="card h-100 shadow-sm border-0">
                        <img src="<?php echo $p['imagem']; ?>" class="card-img-top" alt="<?php echo $p['nome']; ?>">
                        <div class="card-body d-flex flex-column">
                            <span class="badge bg-danger w-auto align-self-start mb-2"><?php echo $p['categoria']; ?></span>
                            <h5 class="card-title fw-bold"><?php echo $p['nome']; ?></h5>
                            <p class="card-text text-muted small flex-grow-1"><?php echo $p['descricao']; ?></p>
                            <div class="d-flex justify-content-between align-items-center mt-3">
                                <span class="fw-bold text-success fs-5">R$ <?php echo number_format($p['preco'], 2, ',', '.'); ?></span>
                                <button class="btn btn-sm btn-outline-danger" onclick="alert('Item adicionado ao carrinho!')">Comprar</button>
                            </div>
                        </div>
                    </div>
                </div>
            <?php endforeach; ?>
        </div>
    </section>

    <hr class="container">

    <!-- Seção de Serviços e Reservas -->
    <section id="servicos" class="container my-5">
        <h2 class="text-center text-danger fw-bold mb-4">Nossos Serviços</h2>
        
        <div class="row g-4 mb-5 text-center">
            <div class="col-md-4">
                <div class="p-4 border rounded bg-light">
                    <div class="fs-1">🍷</div>
                    <h5 class="fw-bold mt-2">Reserva de Mesas</h5>
                    <p class="text-muted">Garanta seu lugar para almoços e jantares especiais.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="p-4 border rounded bg-light">
                    <div class="fs-1">🎉</div>
                    <h5 class="fw-bold mt-2">Buffet & Eventos</h5>
                    <p class="text-muted">Leve a culinária italiana para festas e reuniões corporativas.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="p-4 border rounded bg-light">
                    <div class="fs-1">🛵</div>
                    <h5 class="fw-bold mt-2">Delivery Especial</h5>
                    <p class="text-muted">Entregas rápidas em embalagens térmicas exclusivas.</p>
                </div>
            </div>
        </div>

        <!-- Formulário de Agendamento -->
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card p-4 shadow-sm border-0">
                    <h3 class="text-center mb-3">Solicitar Serviço ou Reserva</h3>

                    <?php if (!empty($mensagem_sucesso)): ?>
                        <div class="alert alert-success"><?php echo $mensagem_sucesso; ?></div>
                    <?php endif; ?>

                    <form action="index.php#servicos" method="POST">
                        <div class="mb-3">
                            <label class="form-label">Nome Completo</label>
                            <input type="text" name="nome" class="form-control" required placeholder="Seu nome">
                        </div>
                        <div class="row">
                            <div class="col-md-6 mb-3">
                                <label class="form-label">Serviço</label>
                                <select name="servico" class="form-select">
                                    <option value="Reserva de Mesa">Reserva de Mesa</option>
                                    <option value="Buffet para Evento">Buffet para Evento</option>
                                    <option value="Encomenda Especial">Encomenda Especial</option>
                                </select>
                            </div>
                            <div class="col-md-3 mb-3">
                                <label class="form-label">Data</label>
                                <input type="date" name="data" class="form-control" required>
                            </div>
                            <div class="col-md-3 mb-3">
                                <label class="form-label">Pessoas</label>
                                <input type="number" name="pessoas" class="form-control" value="2" min="1" required>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-danger w-100">Enviar Solicitação</button>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <!-- Rodapé com o Nome do Aluno (Requisito da atividade) -->
    <footer class="bg-dark text-white text-center py-4 mt-5">
        <div class="container">
            <p class="mb-1">&copy; <?php echo date('Y'); ?> Cantina Bella Italia - Todos os direitos reservados.</p>
            <p class="mb-0 text-warning fw-bold">Desenvolvido por: Ana Sofia de Oliveira Silva Lima | 19/10/2026 </p>
        </div>
    </footer>

</body>
</html>