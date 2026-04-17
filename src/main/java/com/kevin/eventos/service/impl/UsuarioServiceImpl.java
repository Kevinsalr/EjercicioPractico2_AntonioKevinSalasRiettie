package com.kevin.eventos.service.impl;

import com.kevin.eventos.domain.Rol;
import com.kevin.eventos.domain.Usuario;
import com.kevin.eventos.repository.RolRepository;
import com.kevin.eventos.repository.UsuarioRepository;
import com.kevin.eventos.service.EmailService;
import com.kevin.eventos.service.UsuarioService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              RolRepository rolRepository,
                              BCryptPasswordEncoder passwordEncoder,
                              EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Rol rol = rolRepository.findByNombre("CLIENTE")
                .orElseThrow(() -> new RuntimeException("Rol CLIENTE no encontrado"));

        usuario.setRol(rol);
        usuario.setActivo(true);

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        // ========================
        // ENVÍO DE CORREO SEGURO
        // ========================
        try {
            emailService.enviarCorreo(
                    usuario.getEmail(),
                    "Registro exitoso",
                    "Hola " + usuario.getNombre() + ", tu cuenta fue creada correctamente."
            );
        } catch (Exception e) {
            System.out.println("Error enviando correo: " + e.getMessage());
        }

        return usuarioGuardado;
    }

    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                usuario.getEmail(),
                usuario.getPassword(),
                usuario.isActivo(),
                true,
                true,
                true,
                Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + usuario.getRol().getNombre())
                )
        );
    }
}