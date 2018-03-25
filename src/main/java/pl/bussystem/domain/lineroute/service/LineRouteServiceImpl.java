package pl.bussystem.domain.lineroute.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import pl.bussystem.domain.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineroute.persistence.repository.LineRouteRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LineRouteServiceImpl implements LineRouteService {
  private LineRouteRepository lineRouteRepository;

  @Autowired
  public LineRouteServiceImpl(LineRouteRepository lineRouteRepository) {
    this.lineRouteRepository = lineRouteRepository;
  }

  @Override
  public LineRouteEntity create(LineRouteEntity lineRouteEntity) {
    return lineRouteRepository.save(lineRouteEntity);
  }

  @Override
  public List<LineRouteEntity> read() {
    return lineRouteRepository.findAll();
  }

  @Override
  public void deleteById(Integer id) {
    try {
      lineRouteRepository.deleteById(id);
    } catch (EmptyResultDataAccessException e) {
      throw new NoSuchElementException("Line route with id: " + id + " does not exists!");
    }
  }
}
