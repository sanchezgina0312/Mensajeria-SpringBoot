package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.mensajeria.dto.AdministradorDTO;
import co.edu.unbosque.mensajeria.entity.Administrador;
import co.edu.unbosque.mensajeria.repository.AdministradorRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Servicio encargado de gestionar las operaciones CRUD relacionadas con la entidad Administrador.
 * <p>
 * Implementa la interfaz CRUDOperation para proporcionar funcionalidades de creación,
 * consulta, actualización y eliminación. Además, incluye métodos de búsqueda por
 * diferentes atributos como nombre, cédula, correo, teléfono y usuario.
 * </p>
 * 
 * <p>
 * Utiliza AdministradorRepository para la persistencia de datos, ModelMapper para
 * convertir entre entidades y DTOs, y LanzadorDeException para validar los datos.
 * </p>
 * 
 * @author Angie Villarreal
 */
@Service
public class AdministradorService implements CRUDOperation<AdministradorDTO> {

    @Autowired
    private AdministradorRepository administradorRep;

    @Autowired
    private ModelMapper mapper;

    /**
     * Constructor vacío.
     */
    public AdministradorService() {
    }

    /**
     * Crea un nuevo administrador.
     * 
     * @param data DTO con la información del administrador
     * @return 0 si se creó correctamente, 1 si ya existe
     */
    @Override
    public int create(AdministradorDTO data) {
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarTurno(data.getTurno());

        LanzadorDeException.verificarDuplicado(
                administradorRep.existsByCedula(data.getCedula()),
                "La cédula " + data.getCedula() + " ya se encuentra registrada.");

        if (administradorRep.existsByCedula(data.getCedula())) {
            return 1;
        } else {
            Administrador entity = mapper.map(data, Administrador.class);
            administradorRep.save(entity);
            return 0;
        }
    }

    /**
     * Obtiene todos los administradores.
     * 
     * @return lista de administradores en formato DTO
     */
    @Override
    public List<AdministradorDTO> getAll() {
        List<Administrador> entityList = (List<Administrador>) administradorRep.findAll();
        List<AdministradorDTO> dtoList = new ArrayList<>();

        entityList.forEach((entity) -> {
            dtoList.add(mapper.map(entity, AdministradorDTO.class));
        });

        return dtoList;
    }

    /**
     * Elimina un administrador por ID.
     * 
     * @param id identificador
     * @return 0 si se eliminó, 1 si no existe
     */
    @Override
    public int deleteById(Long id) {
        LanzadorDeException.verificarId(id);
        Optional<Administrador> encontrado = administradorRep.findById(id);

        if (encontrado.isPresent()) {
            administradorRep.delete(encontrado.get());
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Actualiza un administrador existente.
     * 
     * @param id identificador
     * @param data nuevos datos
     * @return 0 si se actualizó, 1 si hay error
     */
    @Override
    public int updateById(Long id, AdministradorDTO data) {
        LanzadorDeException.verificarId(id);
        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarTurno(data.getTurno());

        Optional<Administrador> encontrado = administradorRep.findById(id);

        if (encontrado.isPresent()) {
            Administrador adminActual = encontrado.get();

            if (!adminActual.getCedula().equals(data.getCedula())) {
                LanzadorDeException.verificarDuplicado(
                        administradorRep.existsByCedula(data.getCedula()),
                        "No se puede actualizar: la cédula " + data.getCedula() + " ya pertenece a otro administrador.");

                if (administradorRep.existsByCedula(data.getCedula())) {
                    return 1;
                }
            }

            adminActual.setNombre(data.getNombre());
            adminActual.setCedula(data.getCedula());
            adminActual.setCorreo(data.getCorreo());
            adminActual.setTelefono(data.getTelefono());
            adminActual.setTurno(data.getTurno());
            adminActual.setUsuario(data.getUsuario());
            adminActual.setContrasenia(data.getContrasenia());

            administradorRep.save(adminActual);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Cuenta los administradores registrados.
     * 
     * @return cantidad total
     */
    @Override
    public long count() {
        return administradorRep.count();
    }

    /**
     * Verifica si existe un administrador por ID.
     * 
     * @param id identificador
     * @return true si existe, false si no
     */
    @Override
    public boolean exist(Long id) {
        LanzadorDeException.verificarId(id);
        return administradorRep.existsById(id);
    }

    /**
     * Busca administradores por nombre.
     */
    public List<AdministradorDTO> findByNombre(String nombre) {
        LanzadorDeException.verificarNombre(nombre);
        Optional<List<Administrador>> encontrados = administradorRep.findByNombre(nombre);
        List<AdministradorDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, AdministradorDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca administradores por cédula.
     */
    public List<AdministradorDTO> findByCedula(String cedula) {
        LanzadorDeException.verificarCedula(cedula);
        Optional<List<Administrador>> encontrados = administradorRep.findByCedula(cedula);
        List<AdministradorDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, AdministradorDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca administradores por correo.
     */
    public List<AdministradorDTO> findByCorreo(String correo) {
        LanzadorDeException.verificarCorreoElectronico(correo);
        Optional<List<Administrador>> encontrados = administradorRep.findByCorreo(correo);
        List<AdministradorDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, AdministradorDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca administradores por teléfono.
     */
    public List<AdministradorDTO> findByTelefono(String telefono) {
        LanzadorDeException.verificarTelefono(telefono);
        Optional<List<Administrador>> encontrados = administradorRep.findByTelefono(telefono);
        List<AdministradorDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, AdministradorDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca administradores por usuario.
     */
    public List<AdministradorDTO> findByUsuario(String usuario) {
        Optional<List<Administrador>> encontrados = administradorRep.findByUsuario(usuario);
        List<AdministradorDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, AdministradorDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca administradores por nombre y cédula.
     */
    public List<AdministradorDTO> findByNombreAndCedula(String nombre, String cedula) {
        LanzadorDeException.verificarNombre(nombre);
        LanzadorDeException.verificarCedula(cedula);
        Optional<List<Administrador>> encontrados = administradorRep.findByNombreAndCedula(nombre, cedula);
        List<AdministradorDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, AdministradorDTO.class)));
        }

        return dtoList;
    }

    /**
     * Permite establecer el repositorio manualmente (útil en pruebas).
     */
    public void setAdministradorRep(AdministradorRepository repo) {
        this.administradorRep = repo;
    }

    /**
     * Permite establecer el mapper manualmente (útil en pruebas).
     */
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}