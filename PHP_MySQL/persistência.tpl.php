<?php
// Persiste os dados dos convidados entre os submits de abas
foreach ($convidados_post as $c) {
    echo '<input type="hidden" name="convidado_nome[]" value="' . htmlspecialchars($c['nome']) . '">';
    echo '<input type="hidden" name="convidado_qtd[]" value="' . $c['qtd'] . '">';
}

// Persiste os dados dos itens entre os submits de abas
foreach ($itens_post as $i => $item) {
    echo '<input type="hidden" name="item_id[]" value="' . $i . '">';
    echo '<input type="hidden" name="item_nome[]" value="' . htmlspecialchars($item['nome']) . '">';
    echo '<input type="hidden" name="item_tipo[]" value="' . $item['tipo'] . '">';
    echo '<input type="hidden" name="item_taxa[]" value="' . $item['taxa'] . '">';
    echo '<input type="hidden" name="item_img[]" value="' . htmlspecialchars($item['img']) . '">';
    if ($item['marcado']) {
        echo '<input type="hidden" name="item_marcado_persistido[' . $i . ']" value="1">';
    }
}
?>