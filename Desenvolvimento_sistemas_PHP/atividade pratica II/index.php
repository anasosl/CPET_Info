<?php
// Função para calcular feriados nacionais móveis e fixos do Brasil
function getFeriados($ano) {
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

    $carnaval = date('d-m', strtotime("$data_pascoa -47 days"));
    $paixao = date('d-m', strtotime("$data_pascoa -2 days"));
    $pascoa = date('d-m', strtotime($data_pascoa));
    $corpus = date('d-m', strtotime("$data_pascoa +60 days"));

    $feriados[$carnaval] = 'Carnaval';
    $feriados[$paixao] = 'Sexta-feira Santa';
    $feriados[$pascoa] = 'Páscoa';
    $feriados[$corpus] = 'Corpus Christi';

    return $feriados;
}

$ano = date('Y');
$feriados = getFeriados($ano);
$meses = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
$diasDaSemana = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'];
?>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Calendário <?php echo $ano; ?></title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f9; color: #333; margin: 0; padding: 20px; }
        h1 { text-align: center; color: #2c3e50; }
        .calendario { display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 20px; max-width: 1200px; margin: 0 auto; }
        .mes { background: #fff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); padding: 15px; }
        .mes-titulo { text-align: center; font-weight: bold; font-size: 1.2em; margin-bottom: 10px; color: #34495e; text-transform: uppercase; border-bottom: 2px solid #3498db; padding-bottom: 5px; }
        .grid-dias { display: grid; grid-template-columns: repeat(7, 1fr); text-align: center; gap: 5px; }
        .cabecalho-dia { font-weight: bold; color: #7f8c8d; font-size: 0.9em; padding: 5px 0; }
        .dia { padding: 8px 5px; border-radius: 4px; font-size: 0.95em; }
        .vazio { background: transparent; }
        .feriado { background-color: #e74c3c; color: white; font-weight: bold; cursor: help; position: relative; }
        .feriado:hover::after {
            content: attr(title); position: absolute; bottom: 120%; left: 50%; transform: translateX(-50%);
            background: #2c3e50; color: #fff; padding: 5px 10px; border-radius: 4px; font-size: 12px;
            white-space: nowrap; z-index: 10; box-shadow: 0 2px 4px rgba(0,0,0,0.2);
        }
        .hoje { border: 2px solid #3498db; font-weight: bold; }
    </style>
</head>
<body>

    <h1>Calendário Anual - <?php echo $ano; ?></h1>

    <div class="calendario">
        <?php
        $hoje = date('d-m-Y');

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

            // Preenche os espaços vazios antes do dia 1º
            for ($i = 0; $i < $diaSemanaInicial; $i++) {
                echo "<div class='vazio'></div>";
            }

            // Gera os dias do mês
            for ($d = 1; $d <= $totalDias; $d++) {
                $dataAtualStr = sprintf("%02d-%02d", $d, $m);
                $dataCompleta = sprintf("%02d-%02d-%04d", $d, $m, $ano);
                
                $classes = ['dia'];
                $title = '';

                // Verifica se é feriado
                if (array_key_exists($dataAtualStr, $feriados)) {
                    $classes[] = 'feriado';
                    $title = "title='{$feriados[$dataAtualStr]}'";
                }

                // Destaca o dia de hoje
                if ($dataCompleta === $hoje) {
                    $classes[] = 'hoje';
                }

                $classStr = implode(' ', $classes);
                echo "<div class='$classStr' $title>$d</div>";
            }

            echo "</div></div>";
        }
        ?>
    </div>

</body>
</html>