import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calcularConsumo")
public class ConsumoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        double totalAnual = 0;
        double maiorConsumo = Double.MIN_VALUE;
        double menorConsumo = Double.MAX_VALUE;
        String mesMaior = "", mesMenor = "";
        
        String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", 
                          "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

        for (int i = 0; i < 12; i++) {
            double valor = Double.parseDouble(request.getParameter("mes" + i));
            totalAnual += valor;
            
            if (valor > maiorConsumo) {
                maiorConsumo = valor;
                mesMaior = meses[i];
            }
            if (valor < menorConsumo) {
                menorConsumo = valor;
                mesMenor = meses[i];
            }
        }

        request.setAttribute("nome", nome);
        request.setAttribute("total", totalAnual);
        request.setAttribute("maior", mesMaior);
        request.setAttribute("menor", mesMenor);
        request.getRequestDispatcher("resultado.jsp").forward(request, response);
    }
}