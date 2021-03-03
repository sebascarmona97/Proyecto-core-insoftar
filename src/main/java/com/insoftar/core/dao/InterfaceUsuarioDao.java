/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insoftar.core.dao;

import com.insoftar.core.entities.Usuarios;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public interface InterfaceUsuarioDao {
    
    List<Usuarios> findAll();
    
    void insertUsuario(Usuarios usuario);

    void updateUsuario(Usuarios usuario);

    void executeUpdateUsuario(Usuarios usuario);

    void deleteUsuario(Usuarios usuario);
    
    boolean consultarCedulaExiste(String cedula, Long id);
    
    boolean consultarCorreoExiste(String correo, Long id);
}
