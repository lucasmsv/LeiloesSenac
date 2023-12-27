import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    private conectaDAO conexao;
    private Connection conn;

    public ProdutosDAO() {
        this.conexao = new conectaDAO();
        this.conn = (Connection) this.conexao.conectaDAO();
    }

    public void cadastrarProduto(ProdutosDTO produtosDTO) {
        String sql = "INSERT INTO dados(nome, valor, status) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, produtosDTO.getNome());
            stmt.setInt(2, produtosDTO.getValor());
            stmt.setString(3, produtosDTO.getStatus());

            stmt.execute();

        } 
        catch (SQLException e) {
            System.out.println("Erro ao inserir empresa: " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        return null;
    }

}
