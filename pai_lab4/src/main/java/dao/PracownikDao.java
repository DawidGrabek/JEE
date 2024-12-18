package dao;

import beans.Pracownik;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PracownikDao {

    JdbcTemplate template;

    public void setTemplate(JdbcTemplate template) {
        this.template = template;
    }

    public int save(Pracownik p) {
        String sql = "insert into pracownik (nazwisko,pensja,firma) " + "values('" + p.getNazwisko() + "'," + p.getPensja()+ ",'" + p.getFirma() + "')";
        return template.update(sql);
    }

    public List<Pracownik> getAll() {
        return template.query("select * from pracownik",
                new RowMapper<Pracownik>() {
            @Override
            public Pracownik mapRow(ResultSet rs, int row)
                    throws SQLException {
                Pracownik e = new Pracownik();
                e.setId(rs.getInt(1));
                e.setNazwisko(rs.getString(2));
                e.setPensja(rs.getInt(3));
                e.setFirma(rs.getString(4));
                return e;
            }
        });
    }
    
    public int delete(int id) {
        String sql = "DELETE FROM pracownik WHERE id = ?";
        return template.update(sql, id);
    }
    
    public int update(Pracownik p) {
        String sql = "UPDATE pracownik SET nazwisko = ?, pensja = ?, firma = ? WHERE id = ?";
        return template.update(sql, p.getNazwisko(), p.getPensja(), p.getFirma(), p.getId());
    }

    public Pracownik getPracownikById(int id) {
        String sql = "SELECT * FROM pracownik WHERE id = ?";
        return template.queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper<>(Pracownik.class));
    }
}
