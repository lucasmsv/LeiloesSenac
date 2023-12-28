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
    
    public void excluirProduto(int id) {
        String sql = "DELETE FROM dados WHERE id = ?";
    
        try {
            PreparedStatement stmt = this.conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Produto excluído com sucesso.");
            } 
            else {
                System.out.println("Produto não encontrado ou não pôde ser excluído.");
            }
        } 
        catch (SQLException e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage());
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
    
    public List<ProdutosDTO> getProdutosVendidos() {
        String sql = "SELECT * FROM vendidos";

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
    
    public ProdutosDTO getProdutoPorId(int id) {
        String sql = "SELECT * FROM dados WHERE id = ?";
        ProdutosDTO produto = null;

        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new ProdutosDTO();
                    produto.setId(rs.getInt("id"));
                    produto.setNome(rs.getString("nome"));
                    produto.setValor(rs.getInt("valor"));
                    produto.setStatus(rs.getString("status"));
                }
            }
        } 
        catch (SQLException e) {
            System.out.println("Erro ao obter produto por ID: " + e.getMessage());
        }
        return produto;
    }
    
    public void inserirProdutoVendido(ProdutosDTO produto) {
        if (!produtoJaVendido(produto.getId())) {
            String sql = "INSERT INTO vendidos (id, nome, valor, status) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
                stmt.setInt(1, produto.getId());
                stmt.setString(2, produto.getNome());
                stmt.setInt(3, produto.getValor());
                stmt.setString(4, "Vendido");

                stmt.executeUpdate();
            } 
            catch (SQLException e) {
                System.out.println("Erro ao inserir produto vendido: " + e.getMessage());
                try {
                    this.conn.rollback(); 
                } catch (SQLException ex) {
                    System.out.println("Erro ao realizar rollback: " + ex.getMessage());
                }
            }
        } 
        else {
            System.out.println("Produto já vendido.");
        }
    }
    
    private boolean produtoJaVendido(int id) {
        String sql = "SELECT id FROM vendidos WHERE id = ?";
        try (PreparedStatement stmt = this.conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } 
        catch (SQLException e) {
            System.out.println("Erro ao verificar se o produto já foi vendido: " + e.getMessage());
            return false;
        }
    }


}
