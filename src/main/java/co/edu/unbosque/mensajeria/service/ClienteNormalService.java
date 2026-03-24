package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ClienteNormalDTO;
import co.edu.unbosque.mensajeria.entity.ClienteNormal;
import co.edu.unbosque.mensajeria.repository.ClienteNormalRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

/**
 * Servicio encargado de gestionar las operaciones CRUD de la entidad ClienteNormal.
 * <p>
 * Permite crear, consultar, actualizar y eliminar clientes normales, así como
 * realizar búsquedas por diferentes atributos como nombre, cédula, correo, teléfono,
 * método de pago y tipo de pedido.
 * </p>
 * 
 * <p>
 * Utiliza ClienteNormalRepository para la persistencia de datos, ModelMapper para
 * la conversión entre entidades y DTOs, y LanzadorDeException para la validación
 * de la información.
 * </p>
 * 
 * @author Jairo Esteban
 */
@Service
public class ClienteNormalService implements CRUDOperation<ClienteNormalDTO> {

    @Autowired
    private ClienteNormalRepository clienteNormalRep;

    @Autowired
    private ModelMapper mapper;

    /**
     * Crea un nuevo cliente normal.
     * 
     * @param data datos del cliente
     * @return 0 si se creó correctamente, 1 si ya existe
     */
    @Override
    public int create(ClienteNormalDTO data) {
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
        LanzadorDeException.verificarTipoPedido(data.getTipoPedido());

        LanzadorDeException.verificarDuplicado(
                clienteNormalRep.existsByCedula(data.getCedula()),
                "La cédula " + data.getCedula() + " ya se encuentra registrada para un cliente normal.");

        if (clienteNormalRep.existsByCedula(data.getCedula())) {
            return 1;
        } else {
            ClienteNormal entity = mapper.map(data, ClienteNormal.class);
            clienteNormalRep.save(entity);
            return 0;
        }
    }

    /**
     * Obtiene todos los clientes normales.
     * 
     * @return lista de clientes
     */
    @Override
    public List<ClienteNormalDTO> getAll() {
        List<ClienteNormal> entityList = (List<ClienteNormal>) clienteNormalRep.findAll();
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        entityList.forEach(entity ->
                dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));

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
        Optional<ClienteNormal> encontrado = clienteNormalRep.findById(id);

        if (encontrado.isPresent()) {
            clienteNormalRep.delete(encontrado.get());
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
    public int updateById(Long id, ClienteNormalDTO data) {
        LanzadorDeException.verificarId(id);
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarMetodoPago(data.getMetodoPago());
        LanzadorDeException.verificarTipoPedido(data.getTipoPedido());

        Optional<ClienteNormal> encontrado = clienteNormalRep.findById(id);

        if (encontrado.isPresent()) {
            ClienteNormal temp = encontrado.get();

            if (!temp.getCedula().equals(data.getCedula())) {
                LanzadorDeException.verificarDuplicado(
                        clienteNormalRep.existsByCedula(data.getCedula()),
                        "No se puede actualizar: la cédula " + data.getCedula() + " ya pertenece a otro cliente.");

                if (clienteNormalRep.existsByCedula(data.getCedula())) {
                    return 1;
                }
            }

            temp.setNombre(data.getNombre());
            temp.setCedula(data.getCedula());
            temp.setCorreo(data.getCorreo());
            temp.setTelefono(data.getTelefono());
            temp.setMetodoPago(data.getMetodoPago());
            temp.setTipoPedido(data.getTipoPedido());

            clienteNormalRep.save(temp);
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
        return clienteNormalRep.count();
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
        return clienteNormalRep.existsById(id);
    }

    /**
     * Busca por nombre.
     */
    public List<ClienteNormalDTO> findByNombre(String nombre) {
        LanzadorDeException.verificarNombre(nombre);
        Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByNombre(nombre);
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por cédula.
     */
    public List<ClienteNormalDTO> findByCedula(String cedula) {
        LanzadorDeException.verificarCedula(cedula);
        Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByCedula(cedula);
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por correo.
     */
    public List<ClienteNormalDTO> findByCorreo(String correo) {
        LanzadorDeException.verificarCorreoElectronico(correo);
        Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByCorreo(correo);
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por teléfono.
     */
    public List<ClienteNormalDTO> findByTelefono(String telefono) {
        LanzadorDeException.verificarTelefono(telefono);
        Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByTelefono(telefono);
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por método de pago.
     */
    public List<ClienteNormalDTO> findByMetodoPago(String metodoPago) {
        LanzadorDeException.verificarMetodoPago(metodoPago);
        Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByMetodoPago(metodoPago);
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por tipo de pedido.
     */
    public List<ClienteNormalDTO> findByTipoPedido(String tipoPedido) {
        LanzadorDeException.verificarTipoPedido(tipoPedido);
        Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByTipoPedido(tipoPedido);
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por nombre y cédula.
     */
    public List<ClienteNormalDTO> findByNombreAndCedula(String nombre, String cedula) {
        LanzadorDeException.verificarNombre(nombre);
        LanzadorDeException.verificarCedula(cedula);
        Optional<List<ClienteNormal>> encontrados = clienteNormalRep.findByNombreAndCedula(nombre, cedula);
        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Busca por tipo de pedido y método de pago.
     */
    public List<ClienteNormalDTO> findByTipoPedidoAndMetodoPago(String tipoPedido, String metodoPago) {
        LanzadorDeException.verificarTipoPedido(tipoPedido);
        LanzadorDeException.verificarMetodoPago(metodoPago);
        Optional<List<ClienteNormal>> encontrados =
                clienteNormalRep.findByTipoPedidoAndMetodoPago(tipoPedido, metodoPago);

        List<ClienteNormalDTO> dtoList = new ArrayList<>();

        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            encontrados.get().forEach(entity ->
                    dtoList.add(mapper.map(entity, ClienteNormalDTO.class)));
        }

        return dtoList;
    }

    /**
     * Permite inyectar el repositorio manualmente (pruebas).
     */
    public void setClienteNormalRep(ClienteNormalRepository repo) {
        this.clienteNormalRep = repo;
    }

    /**
     * Permite inyectar el mapper manualmente (pruebas).
     */
    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }
}