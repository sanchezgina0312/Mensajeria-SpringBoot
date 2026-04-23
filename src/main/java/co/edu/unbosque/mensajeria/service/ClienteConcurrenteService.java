package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ClienteConcurrenteDTO;
import co.edu.unbosque.mensajeria.entity.ClienteConcurrente;
import co.edu.unbosque.mensajeria.repository.ClienteConcurrenteRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Servicio encargado de gestionar las operaciones CRUD de la entidad ClienteConcurrente.
 * <p>
 * Permite crear, consultar, actualizar y eliminar clientes concurrentes, así como
 * realizar búsquedas por diferentes atributos como nombre, cédula, correo, teléfono,
 * método de pago y tipo de pedido.
 * </p>
 * 
 * <p>
 * Utiliza ClienteConcurrenteRepository para la persistencia, ModelMapper para la conversión
 * entre entidades y DTOs, y LanzadorDeException para la validación de datos.
 * </p>
 * 
 */
@Service
public class ClienteConcurrenteService implements CRUDOperation<ClienteConcurrenteDTO> {

    @Autowired
    private ClienteConcurrenteRepository clienteConcurrenteRep;

    @Autowired
    private ModelMapper mapper;

    /**
     * Constructor vacío.
     */
    public ClienteConcurrenteService() {
    }

    /**
     * Crea un nuevo cliente concurrente.
     * 
     * @param data datos del cliente
     * @return 0 si se creó correctamente, 1 si ya existe
     */
    @Override
    public int create(ClienteConcurrenteDTO data) {
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
        LanzadorDeException.verificarTipoPedido(data.getTipoPedido());
        LanzadorDeException.verificarContrasena(data.getContrasenia());

        LanzadorDeException.verificarDuplicado(
                clienteConcurrenteRep.existsByCedula(data.getCedula()),
                "La cédula " + data.getCedula() + " ya se encuentra registrada para un cliente concurrente.");

        if (clienteConcurrenteRep.existsByCedula(data.getCedula())) {
            return 1;
        } else {
            ClienteConcurrente entity = mapper.map(data, ClienteConcurrente.class);
            clienteConcurrenteRep.save(entity);
            return 0;
        }
    }

    /**
     * Obtiene todos los clientes concurrentes.
     * 
     * @return lista de clientes
     */
    @Override
    public List<ClienteConcurrenteDTO> getAll() {
        List<ClienteConcurrente> entityList = (List<ClienteConcurrente>) clienteConcurrenteRep.findAll();
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity ->
                dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));

        return dtoList;
    }

    /**
     * Elimina un cliente por ID.
     * 
     * @param id identificador
     * @return 0 si se eliminó, 1 si no existe
     */
    @Override
    public int deleteById(Long id) {
        LanzadorDeException.verificarId(id);
        Optional<ClienteConcurrente> encontrado = clienteConcurrenteRep.findById(id);

        if (encontrado.isPresent()) {
            clienteConcurrenteRep.delete(encontrado.get());
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Actualiza un cliente existente.
     * 
     * @param id identificador
     * @param data nuevos datos
     * @return 0 si se actualizó, 1 si hay error
     */
    @Override
    public int updateById(Long id, ClienteConcurrenteDTO data) {
        LanzadorDeException.verificarId(id);
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
        LanzadorDeException.verificarTipoPedido(data.getTipoPedido());
        LanzadorDeException.verificarContrasena(data.getContrasenia());

        Optional<ClienteConcurrente> encontrado = clienteConcurrenteRep.findById(id);

        if (encontrado.isPresent()) {
            ClienteConcurrente temp = encontrado.get();

            if (!temp.getCedula().equals(data.getCedula())) {
                LanzadorDeException.verificarDuplicado(
                        clienteConcurrenteRep.existsByCedula(data.getCedula()),
                        "No se puede actualizar: la cédula " + data.getCedula() + " ya pertenece a otro cliente.");

                if (clienteConcurrenteRep.existsByCedula(data.getCedula())) {
                    return 1;
                }
            }

            temp.setNombre(data.getNombre());
            temp.setCedula(data.getCedula());
            temp.setCorreo(data.getCorreo());
            temp.setTelefono(data.getTelefono());
            temp.setMetodoPago(data.getMetodoPago());
            temp.setTipoPedido(data.getTipoPedido());
            temp.setContrasenia(data.getContrasenia());

            clienteConcurrenteRep.save(temp);
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Cuenta los clientes registrados.
     * 
     * @return cantidad total
     */
    @Override
    public long count() {
        return clienteConcurrenteRep.count();
    }

    /**
     * Verifica si existe un cliente por ID.
     * 
     * @param id identificador
     * @return true si existe, false si no
     */
    @Override
    public boolean exist(Long id) {
        LanzadorDeException.verificarId(id);
        return clienteConcurrenteRep.existsById(id);
    }

    /**
     * Busca por nombre.
     */
    public List<ClienteConcurrenteDTO> findByNombre(String nombre) {
        LanzadorDeException.verificarNombre(nombre);
        Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByNombre(nombre);
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por cédula.
     */
    public List<ClienteConcurrenteDTO> findByCedula(String cedula) {
        LanzadorDeException.verificarCedula(cedula);
        Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByCedula(cedula);
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por correo.
     */
    public List<ClienteConcurrenteDTO> findByCorreo(String correo) {
        LanzadorDeException.verificarCorreoElectronico(correo);
        Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByCorreo(correo);
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por teléfono.
     */
    public List<ClienteConcurrenteDTO> findByTelefono(String telefono) {
        LanzadorDeException.verificarTelefono(telefono);
        Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByTelefono(telefono);
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por método de pago.
     */
    public List<ClienteConcurrenteDTO> findByMetodoPago(String metodoPago) {
        LanzadorDeException.verificarMetodoPago(metodoPago);
        Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByMetodoPago(metodoPago);
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por tipo de pedido.
     */
    public List<ClienteConcurrenteDTO> findByTipoPedido(String tipoPedido) {
        LanzadorDeException.verificarTipoPedido(tipoPedido);
        Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByTipoPedido(tipoPedido);
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por nombre y cédula.
     */
    public List<ClienteConcurrenteDTO> findByNombreAndCedula(String nombre, String cedula) {
        LanzadorDeException.verificarNombre(nombre);
        LanzadorDeException.verificarCedula(cedula);
        Optional<List<ClienteConcurrente>> encontrados = clienteConcurrenteRep.findByNombreAndCedula(nombre, cedula);
        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por tipo de pedido y método de pago.
     */
    public List<ClienteConcurrenteDTO> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago) {
        LanzadorDeException.verificarTipoPedido(tipoPedido);
        LanzadorDeException.verificarMetodoPago(metodoPago);
        Optional<List<ClienteConcurrente>> encontrados =
                clienteConcurrenteRep.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);

        List<ClienteConcurrenteDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteConcurrenteDTO.class)));
        }

        return dtoList;
    }

    /**
     * Permite inyectar el repositorio manualmente (pruebas).
     */
    public void setClienteConcurrenteRep(ClienteConcurrenteRepository repo) {
        this.clienteConcurrenteRep = repo;
    }

    /**
     * Permite inyectar el mapper manualmente (pruebas).
     */
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}