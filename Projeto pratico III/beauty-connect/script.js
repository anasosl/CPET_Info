// Dados organizados por categorias (Projeto III)
const categorias = [
    {
        nome: "Perfumaria",
        produtos: [
            {n: "Kaiak", m: "Natura", c: 100}, {n: "Malbec", m: "Boticário", c: 140},
            {n: "Essencial", m: "Natura", c: 150}, {n: "Luiza Brunet", m: "Avon", c: 80}
        ]
    },
    {
        nome: "Cuidados com o Corpo",
        produtos: [
            {n: "Tododia", m: "Natura", c: 40}, {n: "Nativa SPA", m: "Boticário", c: 50},
            {n: "Sève", m: "Natura", c: 60}, {n: "Encanto", m: "Avon", c: 30}
        ]
    },
    {
        nome: "Maquiagem",
        produtos: [
            {n: "Batom Una", m: "Natura", c: 35}, {n: "Base Power Stay", m: "Avon", c: 45},
            {n: "Make B", m: "Boticário", c: 55}, {n: "Color Trend", m: "Avon", c: 25}
        ]
    }
];

function carregarVitrine() {
    const vitrine = document.getElementById('vitrine-categorias');
    vitrine.innerHTML = ""; // Limpa antes de renderizar

    categorias.forEach(cat => {
        let secao = `<div class="categoria-secao"><h2>${cat.nome}</h2><div class="grid">`;
        cat.produtos.forEach(p => {
            let precoRevenda = p.c * 1.30; // Margem de 30%
            secao += `
                <div class="card">
                    <small>${p.m}</small>
                    <h4>${p.n}</h4>
                    <b>R$ ${precoRevenda.toFixed(2)}</b>
                </div>`;
        });
        secao += `</div></div>`;
        vitrine.innerHTML += secao;
    });
}

function calcularComissao() {
    const custo = parseFloat(document.getElementById('custoInput').value);
    if(!custo) return alert("Informe o valor de custo.");
    
    const revenda = custo * 1.30;
    const lucro = revenda - custo;
    const parte = lucro / 2; // Divisão 50/50

    document.getElementById('resultado').innerHTML = `
        <div style="margin-top:15px; padding:10px; border-top:1px solid #d4af37;">
            <p>Venda: R$ ${revenda.toFixed(2)}</p>
            <p>Sua Parte (Proprietário): R$ ${parte.toFixed(2)}</p>
            <p>Parte do Vendedor: R$ ${parte.toFixed(2)}</p>
        </div>`;
}

function mudarAba(id) {
    document.querySelectorAll('.aba').forEach(a => a.classList.remove('ativa'));
    document.getElementById(id).classList.add('ativa');
}

function enviarMensagem() {
    const nome = document.getElementById('nome').value.trim();
    const email = document.getElementById('email').value.trim();
    const mensagem = document.getElementById('mensagem').value.trim();

    if (nome === "" || email === "" || mensagem === "") {
        alert("Por favor, preencha todos os campos.");
        return;
    }

    console.log("Enviando dados:", { nome, email, mensagem });

    Promise.resolve({ status: 200 })
        .then(res => {
            alert("Mensagem enviada com sucesso! O proprietário entrará em contato.");
            document.getElementById('contatoForm').reset();
        })
        .catch(err => {
            alert("Erro ao enviar mensagem. Tente novamente.");
            console.error(err);
        });
}

window.onload = carregarVitrine;