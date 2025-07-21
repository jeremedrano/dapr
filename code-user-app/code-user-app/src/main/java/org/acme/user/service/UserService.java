package org.acme.user.service;

import org.acme.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> getUserById(int id);
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de todos los usuarios
     */
    List<User> getAllUsers();
    
    /**
     * Crea un nuevo usuario
     * @param user Usuario a crear
     * @return Usuario creado
     */
    User createUser(User user);
    
    /**
     * Actualiza un usuario existente
     * @param user Usuario a actualizar
     * @return Usuario actualizado
     */
    User updateUser(User user);
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deleteUser(int id);
} 