<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Calculadora de IMC</title>
</head>
<body>
    <h2>Calculadora de Índice de Massa Corporal (IMC)</h2>
    <form method="post" action="">
        Nome: <input type="text" name="nome" required><br><br>
        Peso (kg): <input type="number" step="0.1" name="peso" required><br><br>
        Altura (m): <input type="number" step="0.01" name="altura" required><br><br>
        <input type="submit" name="enviar" value="Calcular">
        <input type="reset" value="Limpar">
    </form>

    <?php
    if (isset($_POST['enviar'])) {
        // Validação básica: verifica se os campos não estão vazios
        if (!empty($_POST['nome']) && !empty($_POST['peso']) && !empty($_POST['altura'])) {
            
            $nome = htmlspecialchars($_POST['nome']);
            $peso = (float)$_POST['peso'];
            $altura = (float)$_POST['altura'];

            if ($altura > 0) {
                $imc = $peso / ($altura * $altura);
                echo "<br><hr><h3>Resultado:</h3>";
                echo "<strong>Nome:</strong> $nome <br>";
                echo "<strong>Peso:</strong> $peso kg | <strong>Altura:</strong> $altura m<br>";
                echo "<strong>IMC:</strong> " . number_format($imc, 2);
            } else {
                echo "<p style='color:red;'>Erro: Altura deve ser maior que zero.</p>";
            }
        } else {
            // Se os campos estiverem vazios, exibimos um aviso
            echo "<p style='color:orange;'>Por favor, preencha todos os campos!</p>";
        }
    }
    ?>
</body>
</html>