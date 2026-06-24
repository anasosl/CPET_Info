<?php
// Filtra a lista final exibindo apenas os itens que foram marcados na página anterior
$itens_filtrados = [];
foreach ($itens_post as $item) {
    if ($item['marcado']) {
        $itens_filtrados[] = $item;
    }
}
?>
<div class="resultado-container">
    <h2>🛒 Lista Geral </h2>
    <p>Os cálculos foram processados no servidor PHP com base nos dados fornecidos nas páginas anteriores.</p>

    <div class="info-box">
        <span>Público Total do Evento:</span>
        <span><?php echo $total_convidados_calculado; ?> pessoas presentes</span>
    </div>

    <h3>👥 Composição de Presença</h3>
    <ul>
        <?php foreach ($convidados_post as $convidado): ?>
            <li><strong><?php echo htmlspecialchars($convidado['nome']); ?></strong> — (<?php echo $convidado['qtd']; ?> <?php echo $convidado['qtd'] == 1 ? 'pessoa' : 'pessoas'; ?>)</li>
        <?php endforeach; ?>
    </ul>

    <h3>📦 Lista de Compras Calculada (Apenas Itens Escolhidos)</h3>
    <table>
        <thead>
            <tr>
                <th width="10%">Foto</th>
                <th width="40%">Nome do Item</th>
                <th width="15%">Classificação</th>
                <th width="15%">Consumo Individual</th>
                <th width="20%">Quantidade Total</th>
            </tr>
        </thead>
        <tbody>
            <?php 
            $total_solido_kg = 0;
            $total_liquido_l = 0;

            foreach ($itens_filtrados as $item) {
                $total_item = $item['taxa'] * $total_convidados_calculado;
                $unidade = $item['tipo'] == 'solido' ? 'kg' : 'L';
                
                if ($item['tipo'] == 'solido') $total_solido_kg += $total_item;
                if ($item['tipo'] == 'liquido') $total_liquido_l += $total_item;

                echo "<tr>";
                echo "<td><img src='" . htmlspecialchars($item['img']) . "' style='width:50px; height:50px; object-fit:cover; border-radius:6px;' alt='Item'></td>";
                echo "<td><strong>" . htmlspecialchars($item['nome']) . "</strong></td>";
                echo "<td>" . ucfirst($item['tipo']) . "</td>";
                echo "<td>" . number_format($item['taxa'], 3, ',', '.') . " " . $unidade . "</td>";
                echo "<td><mark><strong>" . number_format($total_item, 2, ',', '.') . " " . $unidade . "</strong></mark></td>";
                echo "</tr>";
            }
            ?>
        </tbody>
    </table>

    <div class="info-box" style="background-color: #efebe9; border-left-color: #5d4037;">
        <span>Resumo volumétrico de Carga:</span>
        <span>Sólidos: <?php echo number_format($total_solido_kg, 2, ',', '.'); ?> kg | Líquidos: <?php echo number_format($total_liquido_l, 2, ',', '.'); ?> L</span>
    </div>
</div>