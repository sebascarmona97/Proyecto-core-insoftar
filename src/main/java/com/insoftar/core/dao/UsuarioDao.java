/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insoftar.core.dao;

import com.insoftar.core.entities.Usuarios;
import com.insoftar.core.mapper.UsuarioRowMapper;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sebastian
 */
@Repository
public class UsuarioDao implements InterfaceUsuarioDao {

    public UsuarioDao(NamedParameterJdbcTemplate template, JdbcTemplate jdbcTemplate) {
        this.template = template;
        this.jdbcTemplate = jdbcTemplate;
    }

    NamedParameterJdbcTemplate template;
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Usuarios> findAll() {
        return template.query("select * from usuarios order by nombres asc", new UsuarioRowMapper());
    }

    @Override
    public void insertUsuario(Usuarios usuario) {
        final String sql = "insert into usuarios(id,nombres,apellidos,cedula,correo,telefono) "
                + "values(NULL,:nombres,:apellidos,:cedula,:correo,:telefono)";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("nombres", usuario.getNombres())
                .addValue("apellidos", usuario.getApellidos())
                .addValue("cedula", usuario.getCedula())
                .addValue("correo", usuario.getCorreo())
                .addValue("telefono", usuario.getTelefono());
        template.update(sql, param, holder);

    }

    @Override
    public void updateUsuario(Usuarios usuario) {
        final String sql = "update usuarios set "
                + "nombres=:nombres, "
                + "apellidos=:apellidos, "
                + "cedula=:cedula, "
                + "correo=:correo, "
                + "telefono=:telefono "
                + "where id=:id";

        KeyHolder holder = new GeneratedKeyHolder();
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", usuario.getId())
                .addValue("nombres", usuario.getNombres())
                .addValue("apellidos", usuario.getApellidos())
                .addValue("cedula", usuario.getCedula())
                .addValue("correo", usuario.getCorreo())
                .addValue("telefono", usuario.getTelefono());
        template.update(sql, param, holder);

    }

    @Override
    public void executeUpdateUsuario(Usuarios usuario) {
        final String sql = "update usuarios set "
                + "nombres=:nombres, "
                + "apellidos=:apellidos, "
                + "cedula=:cedula, "
                + "correo=:correo, "
                + "telefono=:telefono "
                + "where id=:id";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", usuario.getId());
        map.put("nombres", usuario.getNombres());
        map.put("apellidos", usuario.getApellidos());
        map.put("cedula", usuario.getCedula());
        map.put("correo", usuario.getCorreo());
        map.put("telefono", usuario.getTelefono());

        template.execute(sql, map, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });

    }

    @Override
    public void deleteUsuario(Usuarios usuario) {
        final String sql = "delete from usuarios where id=:id";

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", usuario.getId());

        template.execute(sql, map, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps)
                    throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });
    }

    @Override
    public boolean consultarCedulaExiste(String cedula, Long id) {
        String sql = "select count(*) from usuarios where cedula = '" + cedula + "' ";
        if (id != null) {
            sql = sql + "and id <> " + id;
        }

        Integer restult = jdbcTemplate.queryForObject(sql, Integer.class);
        return restult != null && restult > 0;
    }

    @Override
    public boolean consultarCorreoExiste(String correo, Long id) {
        String sql = "select count(*) from usuarios where correo = '" + correo + "' ";
        if (id != null) {
            sql = sql + "and id <> " + id;
        }

        Integer restult = jdbcTemplate.queryForObject(sql, Integer.class);
        return restult != null && restult > 0;
    }

}
