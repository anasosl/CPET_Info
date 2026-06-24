<?php 
include("calculos.php");
?>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>CHURRASCO Ana Sofia de Oliveira Silva Lima</title>
    <link rel="stylesheet" href="style.css">
    <style>
        /* Estilização dos botões de abas em PHP puro */
        .etapas-nav { display: flex; gap: 10px; margin-bottom: 25px; border-bottom: 2px solid #dee2e6; padding-bottom: 10px; }
        .tab-btn { 
            padding: 10px 20px; 
            background: #e0e0e0; 
            border-radius: 6px; 
            color: #495057; 
            font-size: 14px; 
            font-weight: bold; 
            cursor: pointer;
            border: none;
        }
        .tab-btn.ativa { background: #c2185b; color: white; }
        .tab-btn.resultado-btn { background: #1565c0; color: white; }
        .tab-btn.resultado-btn.ativa { background: #0d47a1; }

        .btn-remove { 
            background-color: #e53935; 
            color: white; 
            padding: 9px 12px; 
            border-radius: 6px;
            display: flex;
            align-items: center;
            border: none;
            cursor: pointer;
        }
        .btn-remove:hover { background-color: #b71c1c; }
        .product-img-mini { width: 45px; height: 45px; object-fit: cover; border-radius: 6px; display: block; }
        .checkbox-container { display: flex; align-items: center; justify-content: center; }
        input[type="checkbox"] { transform: scale(1.2); cursor: pointer; }
        td, th { vertical-align: middle; }
    </style>
</head>
<body>

<div class="container">
    <h1>CHURRASCO - Ana Sofia de Oliveira Silva Lima</h1>

    <div class="etapas-nav">
        <form method="POST" action="index.php">
            <?php include("persistência.tpl.php"); ?>
            <input type="hidden" name="tela_proxima" value="convidados">
            <button type="submit" class="tab-btn <?php echo $tela_destino == 'convidados' ? 'ativa' : ''; ?>">👥 Convidados</button>
        </form>

        <form method="POST" action="index.php">
            <?php include("persistência.tpl.php"); ?>
            <input type="hidden" name="tela_proxima" value="itens">
            <button type="submit" class="tab-btn <?php echo $tela_destino == 'itens' ? 'ativa' : ''; ?>">🥩 Itens de Desejo</button>
        </form>

        <form method="POST" action="index.php">
            <?php include("persistência.tpl.php"); ?>
            <input type="hidden" name="tela_proxima" value="resultado">
            <button type="submit" class="tab-btn resultado-btn <?php echo $tela_destino == 'resultado' ? 'ativa' : ''; ?>">📋 Lista Geral</button>
        </form>
    </div>

    <?php if ($tela_destino == 'convidados'): ?>
        <form method="POST" action="index.php">
            <h2>👥 Lista de Convidados</h2>
            <p>Gerencie os participantes do evento abaixo. Os botões realizam a atualização direta pelo servidor.</p>
            
            <div class="info-box">
                <span>Total de Convidados Acumulado:</span>
                <span><?php echo $total_convidados_calculado; ?> pessoas</span>
            </div>

            <div id="container-convidados">
                <?php foreach ($convidados_post as $index => $convidado): ?>
                    <div class="row-dinamica">
                        <input type="text" name="convidado_nome[]" value="<?php echo htmlspecialchars($convidado['nome']); ?>" placeholder="Ex: Maria e dois filhos" required>
                        <input type="number" name="convidado_qtd[]" value="<?php echo $convidado['qtd']; ?>" min="1" placeholder="Ex: 3" required>
                        
                        <button type="submit" class="btn btn-remove" name="fluxo_acao" value="remove_convidado_<?php echo $index; ?>" title="Remover">
                            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><polyline points="3 6 5 6 21 6"></polyline><path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"></path><line x1="10" y1="11" x2="10" y2="17"></line><line x1="14" y1="11" x2="14" y2="17"></line></svg>
                        </button>
                    </div>
                <?php endforeach; ?>
            </div>

            <?php foreach ($itens_post as $i => $item): ?>
                <input type="hidden" name="item_id[]" value="<?php echo $i; ?>">
                <input type="hidden" name="item_nome[]" value="<?php echo htmlspecialchars($item['nome']); ?>">
                <input type="hidden" name="item_tipo[]" value="<?php echo $item['tipo']; ?>">
                <input type="hidden" name="item_taxa[]" value="<?php echo $item['taxa']; ?>">
                <input type="hidden" name="item_img[]" value="<?php echo htmlspecialchars($item['img']); ?>">
                <?php if ($item['marcado']): ?>
                    <input type="hidden" name="item_marcado_persistido[<?php echo $i; ?>]" value="1">
                <?php endif; ?>
            <?php endforeach; ?>

            <div style="display:flex; gap:15px; margin-top:20px;">
                <button type="submit" class="btn btn-add" name="fluxo_acao" value="add_convidado" style="flex:1;">+ Adicionar Linha de Convidado</button>
                <input type="hidden" name="tela_proxima" value="itens">
                <button type="submit" class="btn btn-primary" style="flex:1; margin-top:0;">Ir para os Itens ➡</button>
            </div>
        </form>

    <?php elseif ($tela_destino == 'itens'): ?>
        <form method="POST" action="index.php">
            <h2>🥩 Lista de Desejos Detalhada e Editável</h2>
            <p>Marque os produtos e configure os fatores de consumo por pessoa.</p>

            <?php foreach ($convidados_post as $c): ?>
                <input type="hidden" name="convidado_nome[]" value="<?php echo htmlspecialchars($c['nome']); ?>">
                <input type="hidden" name="convidado_qtd[]" value="<?php echo $c['qtd']; ?>">
            <?php endforeach; ?>

            <table>
                <thead>
                    <tr>
                        <th width="8%" style="text-align:center;">Usar</th>
                        <th width="12%">Foto</th>
                        <th width="40%">Nome do Item</th>
                        <th width="20%">Tipo</th>
                        <th width="20%">Consumo/Pessoa (kg ou L)</th>
                    </tr>
                </thead>
                <tbody>
                    <?php foreach ($itens_post as $i => $item): ?>
                        <tr>
                            <td style="text-align:center;">
                                <div class="checkbox-container">
                                    <input type="hidden" name="item_id[]" value="<?php echo $i; ?>">
                                    <input type="hidden" name="item_img[]" value="<?php echo htmlspecialchars($item['img']); ?>">
                                    <input type="checkbox" name="item_incluir[<?php echo $i; ?>]" value="1" <?php echo $item['marcado'] ? 'checked' : ''; ?>>
                                </div>
                            </td>
                            <td><img src="<?php echo htmlspecialchars($item['img']); ?>" class="product-img-mini" alt="Foto"></td>
                            <td><input type="text" name="item_nome[]" value="<?php echo htmlspecialchars($item['nome']); ?>" style="width:100%;"></td>
                            <td>
                                <select name="item_tipo[]" style="width:100%;">
                                    <option value="solido" <?php echo $item['tipo'] == 'solido' ? 'selected' : ''; ?>>Sólido</option>
                                    <option value="liquido" <?php echo $item['tipo'] == 'liquido' ? 'selected' : ''; ?>>Líquido</option>
                                </select>
                            </td>
                            <td><input type="number" name="item_taxa[]" value="<?php echo $item['taxa']; ?>" step="0.001" min="0" style="width:100%;"></td>
                        </tr>
                    <?php endforeach; ?>
                </tbody>
            </table>

            <div style="display:flex; gap:15px;">
                <button type="submit" class="btn btn-add" name="fluxo_acao" value="add_item" style="flex:1;">+ Criar Novo Item Customizado</button>
                <input type="hidden" name="tela_proxima" value="resultado">
                <button type="submit" class="btn btn-primary" style="flex:1; margin-top:0; background-color:#1565c0;">Gerar Lista Geral de Compras 📋</button>
            </div>
        </form>

    <?php elseif ($tela_destino == 'resultado'): ?>
        <?php include("resultado.php"); ?>
    <?php endif; ?>
</div>

</body>