<?php
// Banco de dados em memória contendo a lista padrão com links funcionais
$itens_padrao = [
    ["nome" => "Carne Bovina (Picanha)", "tipo" => "solido", "taxa" => 0.250, "img" => "https://images.unsplash.com/photo-1544025162-d76694265947?w=150"], 
    ["nome" => "Coração de Frango",      "tipo" => "solido", "taxa" => 0.050, "img" => "https://images.unsplash.com/photo-1516685018646-549198525c1b?w=150"], 
    ["nome" => "Coxinha da Asa (Frango)","tipo" => "solido", "taxa" => 0.100, "img" => "https://images.unsplash.com/photo-1608039829572-78524f79c4c7?w=150"], 
    ["nome" => "Linguiça Toscana",       "tipo" => "solido", "taxa" => 0.100, "img" => "https://images.unsplash.com/photo-1541518763669-27fef04b14ea?w=150"],
    ["nome" => "Queijo Coalho",          "tipo" => "solido", "taxa" => 0.050, "img" => "https://images.unsplash.com/photo-1486297678162-eb2a19b0a32d?w=150"],
    ["nome" => "Pão de Alho",            "tipo" => "solido", "taxa" => 0.050, "img" => "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c5/Garlic_bread_slice.jpg/180px-Garlic_bread_slice.jpg"],
    ["nome" => "Farofa Pronta",          "tipo" => "solido", "taxa" => 0.030, "img" => "https://images.unsplash.com/photo-1589301760014-d929f3979dbc?w=150"],
    ["nome" => "Vinagrete",              "tipo" => "solido", "taxa" => 0.040, "img" => "https://images.unsplash.com/photo-1512621776951-a57141f2eefd?w=150"],
    ["nome" => "Sal Grosso",             "tipo" => "solido", "taxa" => 0.010, "img" => "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Coarse_salt_in_spoon.jpg/180px-Coarse_salt_in_spoon.jpg"],
    ["nome" => "Cerveja (Lata)",         "tipo" => "liquido", "taxa" => 1.500, "img" => "https://images.unsplash.com/photo-1608270586620-248524c67de9?w=150"], 
    ["nome" => "Refrigerante",           "tipo" => "liquido", "taxa" => 0.600, "img" => "https://images.unsplash.com/photo-1622483767028-3f66f32aef97?w=150"], 
    ["nome" => "Suco de Caixinha",       "tipo" => "liquido", "taxa" => 0.400, "img" => "https://images.unsplash.com/photo-1621506289937-a8e4df240d0b?w=150"],
    ["nome" => "Água Mineral",           "tipo" => "liquido", "taxa" => 0.300, "img" => "https://images.unsplash.com/photo-1548839140-29a749e1cf4d?w=150"],
    ["nome" => "Gelo em Cubos",          "tipo" => "liquido", "taxa" => 0.500, "img" => "https://images.unsplash.com/photo-1551782450-17144efb9c50?w=150"]
];

$convidados_post = [];
$total_convidados_calculado = 0;
$itens_post = [];

// Identifica qual ação de navegação foi disparada (Padrão: ir para a página de convidados)
$tela_destino = isset($_POST['tela_proxima']) ? $_POST['tela_proxima'] : 'convidados';

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // 1. Processa e armazena os convidados atuais
    if (isset($_POST['convidado_nome']) && isset($_POST['convidado_qtd'])) {
        for ($i = 0; $i < count($_POST['convidado_nome']); $i++) {
            $nome = trim($_POST['convidado_nome'][$i]);
            $qtd = intval($_POST['convidado_qtd'][$i]);
            if (!empty($nome) && $qtd > 0) {
                $convidados_post[] = ["nome" => $nome, "qtd" => $qtd];
                $total_convidados_calculado += $qtd;
            }
        }
    }

    // 2. Processa e armazena os itens configurados
    if (isset($_POST['item_nome'])) {
        for ($index = 0; $index < count($_POST['item_nome']); $index++) {
            $nome_item = trim($_POST['item_nome'][$index]);
            
            // Verifica se o item veio marcado ou se estamos apenas carregando dados persistidos
            $id_mapeado = $_POST['item_id'][$index];
            $marcado = isset($_POST['item_incluir'][$id_mapeado]) || isset($_POST['item_marcado_persistido'][$index]);

            if (!empty($nome_item)) {
                $itens_post[] = [
                    "nome" => $nome_item,
                    "tipo" => $_POST['item_tipo'][$index],
                    "taxa" => floatval($_POST['item_taxa'][$index]),
                    "img"  => $_POST['item_img'][$index],
                    "marcado" => $marcado
                ];
            }
        }
    }
    
    // Tratamento para adição/remoção de linhas de convidados em PHP
    if (isset($_POST['fluxo_acao'])) {
        if ($_POST['fluxo_acao'] == 'add_convidado') {
            $convidados_post[] = ["nome" => "", "qtd" => ""];
            $tela_destino = 'convidados';
        }
        if (strpos($_POST['fluxo_acao'], 'remove_convidado_') === 0) {
            $index_remover = intval(str_replace('remove_convidado_', '', $_POST['fluxo_acao']));
            if (isset($convidados_post[$index_remover])) {
                $total_convidados_calculado -= $convidados_post[$index_remover]['qtd'];
                unset($convidados_post[$index_remover]);
                $convidados_post = array_values($convidados_post); // Reindexa o array
            }
            $tela_destino = 'convidados';
        }
        if ($_POST['fluxo_acao'] == 'add_item') {
            $itens_post[] = [
                "nome" => "", 
                "tipo" => "solido", 
                "taxa" => 0.100, 
                "img" => "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=150", 
                "marcado" => true
            ];
            $tela_destino = 'itens';
        }
    }
} else {
    // Estado inicial padrão
    $convidados_post = [["nome" => "Paula e dois filhos", "qtd" => 3]];
    $total_convidados_calculado = 3;

    foreach ($itens_padrao as $item) {
        $item['marcado'] = true; // Todos começam marcados por padrão
        $itens_post[] = $item;
    }
}
?>