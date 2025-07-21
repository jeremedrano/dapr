package org.acme.user.repository;

import org.acme.user.domain.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    
    /**
     * Obtiene un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<User> findById(int id);
    
    /**
     * Obtiene todos los usuarios
     * @return Lista de todos los usuarios
     */
    List<User> findAll();
    
    /**
     * Guarda un usuario
     * @param user Usuario a guardar
     * @return Usuario guardado
     */
    User save(User user);
    
    /**
     * Actualiza un usuario existente
     * @param user Usuario a actualizar
     * @return Usuario actualizado
     */
    User update(User user);
    
    /**
     * Elimina un usuario por su ID
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     */
    boolean deleteById(int id);
} 