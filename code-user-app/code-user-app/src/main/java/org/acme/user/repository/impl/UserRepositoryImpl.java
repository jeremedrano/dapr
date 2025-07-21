package org.acme.user.repository.impl;

import org.acme.user.domain.User;
import org.acme.user.repository.UserRepository;
import org.jboss.logging.Logger;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@ApplicationScoped
public class UserRepositoryImpl implements UserRepository {
    
    private static final Logger LOG = Logger.getLogger(UserRepositoryImpl.class);
    
    // Datos mock - en una implementación real esto vendría de una base de datos
    private final CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    
    public UserRepositoryImpl() {
        // Inicializar con algunos usuarios de ejemplo
        users.add(new User(1, "John Doe", "john.doe@example.com"));
        users.add(new User(2, "Jane Smith", "jane.smith@example.com"));
        users.add(new User(3, "Bob Johnson", "bob.johnson@example.com"));
        LOG.info("UserRepositoryImpl inicializado con " + users.size() + " usuarios de ejemplo");
    }

    @Override
    public Optional<User> findById(int id) {
        LOG.info("=== REPOSITORY: findById(id=" + id + ") ===");
        
        Optional<User> user = users.stream()
                .filter(u -> u.getId() == id)
                .findFirst();
        
        if (user.isPresent()) {
            LOG.info("Usuario encontrado: " + user.get().getName());
        } else {
            LOG.warn("Usuario con ID " + id + " no encontrado");
        }
        
        LOG.info("=== FIN REPOSITORY: findById(id=" + id + ") ===");
        return user;
    }

    @Override
    public List<User> findAll() {
        LOG.info("=== REPOSITORY: findAll() ===");
        List<User> userList = List.copyOf(users);
        
        LOG.info("Lista de usuarios obtenida: " + userList.size() + " usuarios");
        LOG.info("Usuarios en la lista: " + userList.stream().map(User::getName).toList());
        LOG.info("=== FIN REPOSITORY: findAll() ===");
        
        return userList;
    }

    @Override
    public User save(User user) {
        LOG.info("=== REPOSITORY: save(user=" + user.getName() + ") ===");
        
        // Generar ID si no tiene uno
        if (user.getId() == 0) {
            int newId = users.stream()
                    .mapToInt(User::getId)
                    .max()
                    .orElse(0) + 1;
            user.setId(newId);
            LOG.info("ID generado para nuevo usuario: " + newId);
        }
        
        users.add(user);
        LOG.info("Usuario guardado: " + user.getName() + " (ID: " + user.getId() + ")");
        LOG.info("=== FIN REPOSITORY: save() ===");
        
        return user;
    }

    @Override
    public User update(User user) {
        LOG.info("=== REPOSITORY: update(user=" + user.getName() + ", ID=" + user.getId() + ") ===");
        
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == user.getId()) {
                users.set(i, user);
                LOG.info("Usuario actualizado: " + user.getName());
                LOG.info("=== FIN REPOSITORY: update() ===");
                return user;
            }
        }
        
        LOG.warn("Usuario con ID " + user.getId() + " no encontrado para actualizar");
        LOG.info("=== FIN REPOSITORY: update() ===");
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        LOG.info("=== REPOSITORY: deleteById(id=" + id + ") ===");
        
        boolean removed = users.removeIf(user -> user.getId() == id);
        
        if (removed) {
            LOG.info("Usuario con ID " + id + " eliminado correctamente");
        } else {
            LOG.warn("Usuario con ID " + id + " no encontrado para eliminar");
        }
        
        LOG.info("=== FIN REPOSITORY: deleteById() ===");
        return removed;
    }
} 