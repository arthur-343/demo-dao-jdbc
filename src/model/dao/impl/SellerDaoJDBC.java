package model.dao.impl;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName  \n" +
                    "FROM seller INNER JOIN department  \n" +
                    "ON seller.DepartmentId = department.Id  \n" +
                    "WHERE seller.Id = ? ");
            st.setInt(1, id);

            rs = st.executeQuery();
            if (rs.next()) {
                Departament dep = new Departament();
                    dep.setId(rs.getInt("id"));
                    dep.setName(rs.getString("Name"));


                Seller sl = new Seller();
                sl.setId(rs.getInt("Id"));
                sl.setName(rs.getString("Name"));
                sl.setEmail(rs.getString("Email"));
                sl.setBirthDate(rs.getDate("BirthDate").toLocalDate());
                sl.setBaseSalary(rs.getDouble("BaseSalary"));
                sl.setDepartament(dep);
                return sl;

            }
            return null;
        } catch (SQLException e) {
            throw new DbException("the erro is relationate with an SQLException  - " + e.getMessage());
        }
        finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                throw new DbException("Error closing resources - " + e.getMessage());
            }

        }

    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}
