/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insoftar.core.mapper;

import com.insoftar.core.entities.Usuarios;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Sebastian
 */
public class UsuarioRowMapper implements RowMapper<Usuarios> {

    @Override
    public Usuarios mapRow(ResultSet rs, int arg1) throws SQLException {
        Usuarios usuario = new Usuarios();
        usuario.setId(rs.getLong("id"));
        usuario.setNombres(rs.getString("nombres"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setCedula(rs.getString("cedula"));
        usuario.setCorreo(rs.getString("correo"));
        usuario.setTelefono(rs.getString("telefono"));
        return usuario;
    }

}

