package co.edu.unbosque.mensajeria.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.unbosque.mensajeria.dto.ConductorDTO;
import co.edu.unbosque.mensajeria.entity.Conductor;
import co.edu.unbosque.mensajeria.repository.ConductorRepository;

@Service
public class ConductorService implements CRUDOperation<ConductorDTO> {

	@Autowired
	private ConductorRepository conductorRep;

	@Autowired
	private ModelMapper mapper;

	public ConductorService() {

	}

	@Override
	public int create(ConductorDTO data) {
		Conductor entity = mapper.map(data, Conductor.class);
		conductorRep.save(entity);
		return 0;
	}

	@Override
	public List<ConductorDTO> getAll() {
		List<Conductor> entityList = (List<Conductor>) conductorRep.findAll();
		List<ConductorDTO> dtoList = new ArrayList<>();
		entityList.forEach((entity) -> {
			ConductorDTO dto = mapper.map(entity, ConductorDTO.class);
			dtoList.add(dto);
		});

		return dtoList;
	}

	@Override
	public int deleteById(Long id) {
		Optional<Conductor> encontrado = conductorRep.findById(id);
		if (encontrado.isPresent()) {
			conductorRep.delete(encontrado.get());
			return 0;
		}

		return 1;
	}

	@Override
	public int updateById(Long id, ConductorDTO data) {
		Optional<Conductor> encontrado = conductorRep.findById(id);
		if (encontrado.isPresent()) {
			Conductor temp = encontrado.get();
			temp.setCedula(data.getCedula());
			temp.setCorreo(data.getCorreo());
			temp.setNombre(data.getNombre());
			temp.setPlacaVehiculo(data.getPlacaVehiculo());
			temp.setTelefono(data.getTelefono());
			;
			temp.setTurno(data.getTurno());
			;
			conductorRep.save(temp);
			return 0;
		}

		return 1;
	}

	@Override
	public long count() {
		return conductorRep.count();
	}

	@Override
	public boolean exist(Long id) {
		return conductorRep.existsById(id) ? true : false;
	}
}