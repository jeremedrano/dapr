package org.acme.user.service.impl;

import org.acme.user.domain.User;
import org.acme.user.repository.UserRepository;
import org.acme.user.service.UserService;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserServiceImpl implements UserService {
    
    private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
    
    @Inject
    UserRepository userRepository;
    
    @Override
    public Optional<User> getUserById(int id) {
        LOG.info("=== SERVICE: getUserById(id=" + id + ") ===");
        LOG.info("Iniciando proceso para obtener usuario con ID: " + id);
        
        long startTime = System.currentTimeMillis();
        Optional<User> user = userRepository.findById(id);
        long endTime = System.currentTimeMillis();
        
        if (user.isPresent()) {
            LOG.info("Usuario encontrado: " + user.get().getName() + " (ID: " + id + ")");
        } else {
            LOG.warn("Usuario no encontrado con ID: " + id);
        }
        
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: getUserById(id=" + id + ") ===");
        
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        LOG.info("=== SERVICE: getAllUsers() ===");
        LOG.info("Iniciando proceso para obtener lista de todos los usuarios");
        
        long startTime = System.currentTimeMillis();
        List<User> users = userRepository.findAll();
        long endTime = System.currentTimeMillis();
        
        LOG.info("Lista de usuarios obtenida: " + users.size() + " usuarios");
        LOG.info("Usuarios en la lista: " + users.stream().map(User::getName).toList());
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: getAllUsers() ===");
        
        return users;
    }

    @Override
    public User createUser(User user) {
        LOG.info("=== SERVICE: createUser(user=" + user.getName() + ") ===");
        LOG.info("Iniciando proceso para crear nuevo usuario");
        
        // Validaciones de negocio
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            LOG.error("Error: El nombre del usuario no puede estar vacío");
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            LOG.error("Error: El email del usuario no puede estar vacío");
            throw new IllegalArgumentException("El email del usuario no puede estar vacío");
        }
        
        long startTime = System.currentTimeMillis();
        User createdUser = userRepository.save(user);
        long endTime = System.currentTimeMillis();
        
        LOG.info("Usuario creado: " + createdUser.getName() + " (ID: " + createdUser.getId() + ")");
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: createUser() ===");
        
        return createdUser;
    }

    @Override
    public User updateUser(User user) {
        LOG.info("=== SERVICE: updateUser(user=" + user.getName() + ", ID=" + user.getId() + ") ===");
        LOG.info("Iniciando proceso para actualizar usuario");
        
        // Validaciones de negocio
        if (user.getId() <= 0) {
            LOG.error("Error: ID de usuario inválido: " + user.getId());
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            LOG.error("Error: El nombre del usuario no puede estar vacío");
            throw new IllegalArgumentException("El nombre del usuario no puede estar vacío");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            LOG.error("Error: El email del usuario no puede estar vacío");
            throw new IllegalArgumentException("El email del usuario no puede estar vacío");
        }
        
        long startTime = System.currentTimeMillis();
        User updatedUser = userRepository.update(user);
        long endTime = System.currentTimeMillis();
        
        if (updatedUser != null) {
            LOG.info("Usuario actualizado: " + updatedUser.getName());
        } else {
            LOG.warn("Usuario con ID " + user.getId() + " no encontrado para actualizar");
        }
        
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: updateUser() ===");
        
        return updatedUser;
    }

    @Override
    public boolean deleteUser(int id) {
        LOG.info("=== SERVICE: deleteUser(id=" + id + ") ===");
        LOG.info("Iniciando proceso para eliminar usuario");
        
        // Validaciones de negocio
        if (id <= 0) {
            LOG.error("Error: ID de usuario inválido: " + id);
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        
        long startTime = System.currentTimeMillis();
        boolean deleted = userRepository.deleteById(id);
        long endTime = System.currentTimeMillis();
        
        if (deleted) {
            LOG.info("Usuario con ID " + id + " eliminado correctamente");
        } else {
            LOG.warn("Usuario con ID " + id + " no encontrado para eliminar");
        }
        
        LOG.info("Tiempo de respuesta: " + (endTime - startTime) + " ms");
        LOG.info("=== FIN SERVICE: deleteUser() ===");
        
        return deleted;
    }
} 