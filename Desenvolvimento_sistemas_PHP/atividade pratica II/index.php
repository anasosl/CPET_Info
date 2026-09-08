<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Calendário Anual</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .calendario { display: grid; grid-template-columns: repeat(auto-fit, minmax(250px, 1fr)); gap: 15px; margin-top: 20px; }
        .mes { border: 1px solid #ccc; padding: 10px; border-radius: 5px; background: #f9f9f9; }
        .mes-titulo { text-align: center; font-weight: bold; margin-bottom: 10px; background: #eee; padding: 5px; }
        .grid-dias { display: grid; grid-template-columns: repeat(7, 1fr); text-align: center; gap: 2px; }
        .cabecalho-dia { font-weight: bold; font-size: 0.8em; color: #555; }
        .dia { padding: 5px; font-size: 0.9em; background: #fff; border: 1px solid #ddd; }
        .vazio { background: transparent; border: none; }
        .feriado { background-color: #ffcccc; color: #d00; font-weight: bold; }
    </style>
</head>
<body>
    <h2>Calendário Anual com Feriados</h2>
    
    <?php
    // Se o formulário foi enviado, pega o ano digitado. Se não, pega o ano atual (ex: 2026)
    $ano = isset($_POST['ano']) && !empty($_POST['ano']) ? (int)$_POST['ano'] : (int)date('Y');
    ?>

    <form method="post" action="">
        Digite o Ano: <input type="number" name="ano" min="1900" max="2100" value="<?php echo $ano; ?>" required>
        <input type="submit" value="Gerar Calendário">
    </form>

    <hr>
    <h3>Calendário do Ano: <?php echo $ano; ?></h3>

    <?php
    function obterFeriados($ano) {
        $dias_pascoa = easter_days($ano);
        $data_pascoa = date('Y-m-d', strtotime("$ano-03-21 +$dias_pascoa days"));
        
        $feriados = [
            '01-01' => 'Confraternização Universal',
            '21-04' => 'Tiradentes',
            '01-05' => 'Dia do Trabalhador',
            '07-09' => 'Independência do Brasil',
            '12-10' => 'Nossa Sra. Aparecida',
            '02-11' => 'Finados',
            '15-11' => 'Proclamação da República',
            '25-12' => 'Natal',
        ];

        $feriados[date('d-m', strtotime("$data_pascoa -47 days"))] = 'Carnaval';
        $feriados[date('d-m', strtotime("$data_pascoa -2 days"))] = 'Sexta-feira Santa';
        $feriados[date('d-m', strtotime($data_pascoa))] = 'Páscoa';
        $feriados[date('d-m', strtotime("$data_pascoa +60 days"))] = 'Corpus Christi';

        return $feriados;
    }

    $feriados = obterFeriados($ano);
    $meses = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
    $diasDaSemana = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'];

    echo "<div class='calendario'>";
    
    for ($m = 1; $m <= 12; $m++) {
        echo "<div class='mes'>";
        echo "<div class='mes-titulo'>" . $meses[$m - 1] . "</div>";
        echo "<div class='grid-dias'>";

        foreach ($diasDaSemana as $dow) {
            echo "<div class='cabecalho-dia'>$dow</div>";
        }

        $primeiroDiaMes = mktime(0, 0, 0, $m, 1, $ano);
        $totalDias = date('t', $primeiroDiaMes);
        $diaSemanaInicial = date('w', $primeiroDiaMes);

        for ($i = 0; $i < $diaSemanaInicial; $i++) {
            echo "<div class='vazio'></div>";
        }

        for ($d = 1; $d <= $totalDias; $d++) {
            $dataAtualStr = sprintf("%02d-%02d", $d, $m);
            $classe = 'dia';
            $titulo = '';

            if (array_key_exists($dataAtualStr, $feriados)) {
                $classe .= ' feriado';
                $titulo = "title='{$feriados[$dataAtualStr]}'";
            }

            echo "<div class='$classe' $titulo>$d</div>";
        }

        echo "</div></div>"; 
    }
    echo "</div>"; 
    ?>
</body>
</html>