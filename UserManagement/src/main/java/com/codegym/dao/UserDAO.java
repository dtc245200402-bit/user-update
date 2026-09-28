package com.codegym.dao;

import com.codegym.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "password";

    private static final String INSERT = "INSERT INTO users(name,email,country) VALUES(?,?,?)";
    private static final String SELECT = "SELECT id,name,email,country FROM users WHERE id=?";
    private static final String ALL = "SELECT id,name,email,country FROM users ORDER BY id";
    private static final String SEARCH_COUNTRY = "SELECT id,name,email,country FROM users WHERE country LIKE ? ORDER BY id";
    private static final String SORT_NAME = "SELECT id,name,email,country FROM users ORDER BY name ASC";
    private static final String DELETE = "DELETE FROM users WHERE id=?";
    private static final String UPDATE = "UPDATE users SET name=?,email=?,country=? WHERE id=?";

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    @Override
    public void insertUser(User u) throws Exception {
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(INSERT)) {
            p.setString(1, u.getName());
            p.setString(2, u.getEmail());
            p.setString(3, u.getCountry());
            p.executeUpdate();
        }
    }

    @Override
    public User selectUser(int id) {
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(SELECT)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return new User(r.getInt("id"), r.getString("name"),
                            r.getString("email"), r.getString("country"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<User> selectAllUsers() {
        return executeListQuery(ALL, null);
    }

    @Override
    public List<User> searchByCountry(String country) {
        return executeListQuery(SEARCH_COUNTRY, country == null ? "" : "%" + country.trim() + "%");
    }

    @Override
    public List<User> sortByName() {
        return executeListQuery(SORT_NAME, null);
    }

    private List<User> executeListQuery(String sql, String parameter) {
        List<User> list = new ArrayList<>();
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(sql)) {
            if (parameter != null) {
                p.setString(1, parameter);
            }
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    list.add(new User(r.getInt("id"), r.getString("name"),
                            r.getString("email"), r.getString("country")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public boolean deleteUser(int id) throws Exception {
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(DELETE)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        }
    }

    @Override
    public boolean updateUser(User u) throws Exception {
        try (Connection c = getConnection(); PreparedStatement p = c.prepareStatement(UPDATE)) {
            p.setString(1, u.getName());
            p.setString(2, u.getEmail());
            p.setString(3, u.getCountry());
            p.setInt(4, u.getId());
            return p.executeUpdate() > 0;
        }
    }
}
