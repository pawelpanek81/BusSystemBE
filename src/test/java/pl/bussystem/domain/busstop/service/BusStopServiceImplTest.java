package pl.bussystem.domain.busstop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineinfo.lineroute.persistence.repository.LineRouteRepository;
import pl.bussystem.domain.lineinfo.lineroute.service.LineRouteService;
import pl.bussystem.domain.lineinfo.lineroute.service.LineRouteServiceImpl;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.busstop.persistence.repository.BusStopRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusStopServiceImplTest {
  private BusStopService busStopService;
  private LineRouteService lineRouteService;

  @Mock
  private BusStopRepository busStopRepositoryMock;
  @Mock
  private LineRouteRepository lineRouteRepositoryMock;

  @Before
  public void setUp() {
    this.lineRouteService = new LineRouteServiceImpl(lineRouteRepositoryMock, null);
    this.busStopService = new BusStopServiceImpl(busStopRepositoryMock, lineRouteService, null);
  }

  @Test
  public void readByBusLineIdShouldReturnListOfBusStopsConnectedWithBusLine() {
    // given
    BusStopEntity busStop1 = new BusStopEntity(1, "Tarnobrzeg", "Przystanek 01", null, null, null);
    BusStopEntity busStop2 = new BusStopEntity(2, "Nowa Dęba", "Centrum", null, null, null);
    BusStopEntity busStop3 = new BusStopEntity(3, "Rzeszów", "Główny", null, null, null);

    BusStopEntity busStop4 = new BusStopEntity(4, "Mielec", "Dworzec Główny PKS", null, null, null);
    BusStopEntity busStop5 = new BusStopEntity(5, "Stalowa Wola", "Przystanek", null, null, null);


    List<BusStopEntity> busStops = Arrays.asList(busStop1, busStop2, busStop3, busStop4, busStop5);

    BusLineEntity busLine1 = new BusLineEntity(1, "N1", busStop1, busStop3, 60);
    LineRouteEntity lineRoute = new LineRouteEntity(1, busLine1, busStop2, 1, 30);

    when(busStopRepositoryMock.findAll()).thenReturn(busStops);
    when(lineRouteRepositoryMock.findAll()).thenReturn(Collections.singletonList(lineRoute));

    List<BusStopEntity> expected = Arrays.asList(busStop1, busStop2, busStop3);

    // when
    List<BusStopEntity> actual = busStopService.readByBusLineId(1);

    // then
    assertEquals(actual.size(), expected.size());
    assertThat(actual, is(expected));
  }
}