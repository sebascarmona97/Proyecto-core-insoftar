/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.insoftar.core.controller;

import com.insoftar.core.entities.Usuarios;
import com.insoftar.core.logic.InterfaceUsuariosLogic;
import com.insoftar.core.visualobject.ObjectMessage;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Sebastian
 */
@RestController
@RequestMapping("/usuarios")
public class CrudUsuario {

    @Resource
    InterfaceUsuariosLogic usuarioLogic;

    @GetMapping(value = "/usuariosList")
    public List<Usuarios> getUsuarios() {
        return usuarioLogic.findAll();
    }

    @PostMapping(value = "/createUsuario")
    public ObjectMessage createUsuario(@RequestBody Usuarios usuario) {
        ObjectMessage objReturn = new ObjectMessage();
        String mensajeValidaciones = validaciones(usuario);

        if (mensajeValidaciones == null) {
            usuarioLogic.insertUsuario(usuario);
            objReturn.setMessage(null);
            return objReturn;
        } else {
            objReturn.setMessage(mensajeValidaciones);
            return objReturn;
        }
    }

    @PutMapping(value = "/updateUsuario")
    public ObjectMessage updateUsuario(@RequestBody Usuarios usuario) {
        ObjectMessage objReturn = new ObjectMessage();
        String mensajeValidaciones = validaciones(usuario);
        
        if (mensajeValidaciones == null) {
            usuarioLogic.updateUsuario(usuario);
            objReturn.setMessage(null);
            return objReturn;
        } else {
            objReturn.setMessage(mensajeValidaciones);
            return objReturn;
        }
    }

    @PutMapping(value = "/executeUpdateUsuario")
    public void executeUpdateUsuario(@RequestBody Usuarios usuario) {
        usuarioLogic.executeUpdateUsuario(usuario);
    }

    @DeleteMapping(value = "/deleteUsuarioById")
    public void deleteUsuario(@RequestBody Usuarios usuario) {
        usuarioLogic.deleteUsuario(usuario);
    }

    public String validaciones(Usuarios usuario) {
        //Validacion nombres
        if (usuario.getNombres() == null || usuario.getNombres().equals("")) {
            return "EL campo nombres no puede ir vacio.";
        }

        //Validacion apellidos
        if (usuario.getApellidos() == null || usuario.getApellidos().equals("")) {
            return "EL campo apellidos no puede ir vacio.";
        }

        //Validacion telelfono
        if (usuario.getCedula() == null || usuario.getCedula().equals("")) {
            return "EL campo cedula no puede ir vacio.";
        }
        
        boolean cedulaExt = usuarioLogic.consultarCedulaExiste(usuario.getCedula(),usuario.getId());
        if(cedulaExt){
            return "La cedula ya existe..";
        }

        if (!isNumeric(usuario.getCedula())) {
            return "La cedula solo debe contener numeros.";
        }

        //Validacion correo
        if (usuario.getCorreo() == null || usuario.getCorreo().equals("")) {
            return "EL campo correo no puede ir vacio.";
        }
        
        boolean correoaExt = usuarioLogic.consultarCorreoExiste(usuario.getCorreo(),usuario.getId());
        if(correoaExt){
            return "El correo ya existe..";
        }

        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matc = pattern.matcher(usuario.getCorreo());
        if (!matc.matches()) {
            return "Estructura de correo no valida.";
        }

        //Validacion telelfono
        if (usuario.getTelefono() == null || usuario.getTelefono().equals("")) {
            return "EL campo telelfono no puede ir vacio.";
        }

        if (!isNumeric(usuario.getTelefono())) {
            return "El telefono solo debe contener numeros.";
        }

        return null;
    }

    private static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            try {
                Long.parseLong(cadena);
                return true;
            } catch (NumberFormatException nfe2) {
                return false;
            }
        }
    }
}
