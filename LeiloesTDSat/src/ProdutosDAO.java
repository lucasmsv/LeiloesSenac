import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<ProdutosDTO> getProdutos() {
        String sql = "SELECT * FROM dados";

        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            List<ProdutosDTO> listaProdutos = new ArrayList<>();

            while (rs.next()) {
                ProdutosDTO produtosDTO = new ProdutosDTO();

                produtosDTO.setId(rs.getInt("id"));
                produtosDTO.setNome(rs.getString("nome"));
                produtosDTO.setValor(rs.getInt("valor"));
                produtosDTO.setStatus(rs.getString("status"));

                listaProdutos.add(produtosDTO);
            }
            return listaProdutos;
        } 
        catch (Exception e) {
            return null;
        }
    }

}
