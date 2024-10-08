package model.dao.impl;

import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(  "INSERT INTO seller \n" +
                    "(Name, Email, BirthDate, BaseSalary, DepartmentId)  \n" +
                    "VALUES  \n" +
                    "(?, ?, ?, ?, ?) ", Statement.RETURN_GENERATED_KEYS);
            Date sqlDate = Date.valueOf(obj.getBirthDate());
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, sqlDate);  // Use setDate para definir o BirthDate
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                try {
                   rs.close();
                } catch (SQLException e) {
                    throw new DbException("rs dont close correct");
                }
            }else {
                throw new DbException("rows not affected");}
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException("st dont close correct");
            }
        }

    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(  "UPDATE seller "
                    +"SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?"
            + " WHERE Id = ?" );
            Date sqlDate = Date.valueOf(obj.getBirthDate());
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, sqlDate);  // Use setDate para definir o BirthDate
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());
            st.setInt(6,obj.getId());

             st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException("st dont close correct");
            }
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller  \n" +
                    "WHERE Id = ? ");
            st.setInt(1,id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Error in delete operation " +e.getMessage());
        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException("st dont close correct");
            }
        }

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
                Department dep = instantiateDepartment(rs);

                Seller sl = instantiateSeller(rs, dep);

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

    private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sl = new Seller();
        sl.setId(rs.getInt("Id"));
        sl.setName(rs.getString("Name"));
        sl.setEmail(rs.getString("Email"));
        sl.setBirthDate(rs.getDate("BirthDate").toLocalDate());
        sl.setBaseSalary(rs.getDouble("BaseSalary"));
        sl.setDepartament(dep);
        return sl;

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
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
    public List<Seller> findByDep(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE DepartmentId = ? "
                            + "ORDER BY Name");

            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }
            return list;
        }
        catch (SQLException e) {
            throw new DbException(e.getMessage());
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
    }
