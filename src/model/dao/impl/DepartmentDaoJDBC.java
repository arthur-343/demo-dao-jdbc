package model.dao.impl;

import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {
    private Connection conn;
    public DepartmentDaoJDBC(Connection conn) {this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO department \n" +
                    "(Name)  \n" +
                    "VALUES  \n" +
                    "(?) ", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0){
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }}

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE Department\n" +
                    "        SET Name = ? \n" +
                    "        WHERE Id = ?");

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows affected. Check if the ID exists.");
            }

        } catch (SQLException e) {
            throw new DbException("Error in update operation " +e.getMessage());        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = conn.prepareCall("DELETE FROM Department WHERE Id = ?");

            st.setInt(1, id);

            st.executeUpdate();


        } catch (SQLException e) {
            throw new DbException("Error in delete operation " +e.getMessage());        }
        finally {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }}

        @Override
        public Department findById (Integer id){
            PreparedStatement st = null;
            ResultSet rs = null;
            try {
                st = conn.prepareCall("select * from Department where Id = ?");

                st.setInt(1, id);

                rs = st.executeQuery();
                if (rs.next()) {
                    Department dep = instantiateDepartment(rs);
                    return dep;
                }
                return null;

            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
        }

        @Override
        public List<Department> findAll () {
            List<Department> list;
            ResultSet rs = null;
            PreparedStatement st = null;
            try {
                st = conn.prepareStatement("SELECT department.*\n" +
                        "FROM department   \n" +
                        "ORDER BY Id");
                rs = st.executeQuery();
                list = new ArrayList<>();
                while (rs.next()) {

                    list.add(new Department(rs.getInt("Id"), rs.getString("Name")));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            return list;

        }
        private Department instantiateDepartment (ResultSet rs) throws SQLException {
            Department dep = new Department();
            dep.setId(rs.getInt("Id"));
            dep.setName(rs.getString("Name"));
            return dep;
        }


    }