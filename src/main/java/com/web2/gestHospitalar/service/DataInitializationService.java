package com.web2.gestHospitalar.service;

import com.web2.gestHospitalar.model.Usuario;
import com.web2.gestHospitalar.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DataInitializationService implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializationService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Criar usuário admin padrão se não existir
        if (!usuarioRepository.existsByUsername("admin")) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@hospital.com");
            admin.setAtivo(true);
            admin.addRole("ADMIN");
            admin.addRole("USER");
            usuarioRepository.save(admin);
            System.out.println("Usuário admin criado com sucesso!");
        }

        // Criar usuário comum padrão se não existir
        if (!usuarioRepository.existsByUsername("usuario")) {
            Usuario usuario = new Usuario();
            usuario.setUsername("usuario");
            usuario.setPassword(passwordEncoder.encode("usuario123"));
            usuario.setEmail("usuario@hospital.com");
            usuario.setAtivo(true);
            usuario.addRole("USER");
            usuarioRepository.save(usuario);
            System.out.println("Usuário comum criado com sucesso!");
        }

        // Criar usuário médico padrão se não existir
        if (!usuarioRepository.existsByUsername("medico")) {
            Usuario medico = new Usuario();
            medico.setUsername("medico");
            medico.setPassword(passwordEncoder.encode("medico123"));
            medico.setEmail("medico@hospital.com");
            medico.setAtivo(true);
            medico.addRole("MEDICO");
            medico.addRole("USER");
            usuarioRepository.save(medico);
            System.out.println("Usuário médico criado com sucesso!");
        }

        // Criar usuário recepcionista padrão se não existir
        if (!usuarioRepository.existsByUsername("recepcionista")) {
            Usuario recepcionista = new Usuario();
            recepcionista.setUsername("recepcionista");
            recepcionista.setPassword(passwordEncoder.encode("recepcionista123"));
            recepcionista.setEmail("recepcionista@hospital.com");
            recepcionista.setAtivo(true);
            recepcionista.addRole("RECEPCIONISTA");
            recepcionista.addRole("USER");
            usuarioRepository.save(recepcionista);
            System.out.println("Usuário recepcionista criado com sucesso!");
        }
    }
}
