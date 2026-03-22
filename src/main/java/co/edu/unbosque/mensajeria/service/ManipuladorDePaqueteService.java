package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.edu.unbosque.mensajeria.dto.ManipuladorDePaqueteDTO;
import co.edu.unbosque.mensajeria.entity.ManipuladorDePaquete;
import co.edu.unbosque.mensajeria.repository.ManipuladorDePaqueteRepository;
import co.edu.unbosque.mensajeria.util.LanzadorDeException;

@Service
public class ManipuladorDePaqueteService implements CRUDOperation<ManipuladorDePaqueteDTO> {

    @Autowired
    private ManipuladorDePaqueteRepository manipuladorRep;

    @Autowired
    private ModelMapper mapper;

    public ManipuladorDePaqueteService() {}

    @Override
    public int create(ManipuladorDePaqueteDTO data) {
    	
    	LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarTurno(data.getTurno());
        LanzadorDeException.verificarTipoManipulador(data.getTipoManipulador());
        
        ManipuladorDePaquete entity = mapper.map(data, ManipuladorDePaquete.class);
        manipuladorRep.save(entity);
        return 0;
    }

    @Override
    public List<ManipuladorDePaqueteDTO> getAll() {
        List<ManipuladorDePaquete> entityList = (List<ManipuladorDePaquete>) manipuladorRep.findAll();
        List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
        entityList.forEach((entity) -> {
            ManipuladorDePaqueteDTO dto = mapper.map(entity, ManipuladorDePaqueteDTO.class);
            dtoList.add(dto);
        });
        return dtoList;
    }

    @Override
    public int deleteById(Long id) {
    	
    	LanzadorDeException.verificarId(id);
        Optional<ManipuladorDePaquete> encontrado = manipuladorRep.findById(id);
        if (encontrado.isPresent()) {
            manipuladorRep.delete(encontrado.get());
            return 0;
        }
        return 1;
    }

    @Override
    public int updateById(Long id, ManipuladorDePaqueteDTO data) {
    	
    	LanzadorDeException.verificarId(id);
		LanzadorDeException.verificarNombre(data.getNombre());
        LanzadorDeException.verificarCedula(data.getCedula());
        LanzadorDeException.verificarCorreoElectronico(data.getCorreo());
        LanzadorDeException.verificarTelefono(data.getTelefono());
        LanzadorDeException.verificarTipoAlimento(data.getTipoManipulador());
        
        Optional<ManipuladorDePaquete> encontrado = manipuladorRep.findById(id);
        if (encontrado.isPresent()) {
            ManipuladorDePaquete temp = encontrado.get();
            temp.setNombre(data.getNombre());
            temp.setCedula(data.getCedula());
            temp.setCorreo(data.getCorreo());
            temp.setTelefono(data.getTelefono());
            temp.setTurno(data.getTurno());
            temp.setTipoManipulador(data.getTipoManipulador());
            manipuladorRep.save(temp);
            return 0;
        }
        return 1;
    }

    @Override
    public long count() {
        return manipuladorRep.count();
    }

    @Override
    public boolean exist(Long id) {
    	LanzadorDeException.verificarId(id);
        return manipuladorRep.existsById(id) ? true : false;
    }
    
    
    
    public List<ManipuladorDePaqueteDTO> findByNombre(String nombre) {
    	LanzadorDeException.verificarNombre(nombre);
        Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByNombre(nombre);
        List<ManipuladorDePaquete> entityList = encontrados.get();
        List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
        
        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            entityList.forEach((entity) -> {
                ManipuladorDePaqueteDTO dto = mapper.map(entity, ManipuladorDePaqueteDTO.class);
                dtoList.add(dto);
            });
            return dtoList;
        } else {
            return new ArrayList<ManipuladorDePaqueteDTO>(); 
        }
    }

    public List<ManipuladorDePaqueteDTO> findByCedula(String cedula) {
    	LanzadorDeException.verificarCedula(cedula);
        Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByCedula(cedula);
        List<ManipuladorDePaquete> entityList = encontrados.get();
        List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
        
        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            entityList.forEach((entity) -> {
                ManipuladorDePaqueteDTO dto = mapper.map(entity, ManipuladorDePaqueteDTO.class);
                dtoList.add(dto);
            });
            return dtoList;
        } else {
            return new ArrayList<ManipuladorDePaqueteDTO>(); 
        }
    }

    public List<ManipuladorDePaqueteDTO> findByCorreo(String correo) {
    	LanzadorDeException.verificarCorreoElectronico(correo);
        Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByCorreo(correo);
        List<ManipuladorDePaquete> entityList = encontrados.get();
        List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
        
        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            entityList.forEach((entity) -> {
                ManipuladorDePaqueteDTO dto = mapper.map(entity, ManipuladorDePaqueteDTO.class);
                dtoList.add(dto);
            });
            return dtoList;
        } else {
            return new ArrayList<ManipuladorDePaqueteDTO>(); 
        }
    }

    public List<ManipuladorDePaqueteDTO> findByTelefono(String telefono) {
    	LanzadorDeException.verificarTelefono(telefono);
        Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByTelefono(telefono);
        List<ManipuladorDePaquete> entityList = encontrados.get();
        List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
        
        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            entityList.forEach((entity) -> {
                ManipuladorDePaqueteDTO dto = mapper.map(entity, ManipuladorDePaqueteDTO.class);
                dtoList.add(dto);
            });
            return dtoList;
        } else {
            return new ArrayList<ManipuladorDePaqueteDTO>(); 
        }
    }

    public List<ManipuladorDePaqueteDTO> findByTipoManipulador(String tipoManipulador) {
    	LanzadorDeException.verificarTipoManipulador(tipoManipulador);
        Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByTipoManipulador(tipoManipulador);
        List<ManipuladorDePaquete> entityList = encontrados.get();
        List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
        
        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            entityList.forEach((entity) -> {
                ManipuladorDePaqueteDTO dto = mapper.map(entity, ManipuladorDePaqueteDTO.class);
                dtoList.add(dto);
            });
            return dtoList;
        } else {
            return new ArrayList<ManipuladorDePaqueteDTO>(); 
        }
    }

    public List<ManipuladorDePaqueteDTO> findByNombreAndCedula(String nombre, String cedula) {
    	LanzadorDeException.verificarNombre(nombre);
    	LanzadorDeException.verificarCedula(cedula);
        Optional<List<ManipuladorDePaquete>> encontrados = manipuladorRep.findByNombreAndCedula(nombre, cedula);
        List<ManipuladorDePaquete> entityList = encontrados.get();
        List<ManipuladorDePaqueteDTO> dtoList = new ArrayList<>();
        
        if (encontrados.isPresent() && !encontrados.get().isEmpty()) {
            entityList.forEach((entity) -> {
                ManipuladorDePaqueteDTO dto = mapper.map(entity, ManipuladorDePaqueteDTO.class);
                dtoList.add(dto);
            });
            return dtoList;
        } else {
            return new ArrayList<ManipuladorDePaqueteDTO>(); 
        }
    }
    
}