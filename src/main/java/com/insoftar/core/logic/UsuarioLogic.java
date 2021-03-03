/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insoftar.core.logic;

import com.insoftar.core.dao.InterfaceUsuarioDao;
import com.insoftar.core.entities.Usuarios;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Sebastian
 */
@Component
public class UsuarioLogic implements InterfaceUsuariosLogic {
    
    @Resource
    InterfaceUsuarioDao usuarioDao;
    
    @Override
    public List<Usuarios> findAll() {
        return usuarioDao.findAll();
    }
    
    @Override
    public void insertUsuario(Usuarios usuario) {
        usuarioDao.insertUsuario(usuario);
    }

    @Override
    public void updateUsuario(Usuarios usuario) {
        usuarioDao.updateUsuario(usuario);
    }

    @Override
    public void executeUpdateUsuario(Usuarios usuario) {
        usuarioDao.executeUpdateUsuario(usuario);
    }

    @Override
    public void deleteUsuario(Usuarios usuario) {
        usuarioDao.deleteUsuario(usuario);
    }
    
    @Override
    public boolean consultarCedulaExiste(String cedula, Long id){
        return usuarioDao.consultarCedulaExiste(cedula,id);
    }
    
    @Override
    public boolean consultarCorreoExiste(String correo, Long id){
        return usuarioDao.consultarCorreoExiste(correo,id);
    }
}
