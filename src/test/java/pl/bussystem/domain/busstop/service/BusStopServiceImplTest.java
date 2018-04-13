package pl.bussystem.domain.busstop.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.bussystem.domain.busstop.persistence.entity.BusStopEntity;
import pl.bussystem.domain.lineinfo.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.lineinfo.busline.service.BusLineService;
import pl.bussystem.domain.lineinfo.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.lineinfo.lineroute.persistence.repository.LineRouteRepository;
import pl.bussystem.domain.lineinfo.lineroute.service.LineRouteService;
import pl.bussystem.domain.lineinfo.lineroute.service.LineRouteServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BusStopServiceImplTest {
  private BusStopService busStopService;
  private LineRouteService lineRouteService;

  @Mock
  private LineRouteRepository lineRouteRepositoryMock;
  @Mock
  private BusLineService busLineServiceMock;

  @Before
  public void setUp() {
    this.lineRouteService = new LineRouteServiceImpl(lineRouteRepositoryMock, busLineServiceMock);
    this.busStopService = new BusStopServiceImpl(null, lineRouteService, busLineServiceMock);
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

    when(lineRouteRepositoryMock.findAllByOrderBySequence()).thenReturn(Collections.singletonList(lineRoute));
    when(busLineServiceMock.readById(1)).thenReturn(busLine1);
    when(busLineServiceMock.notExistsById(1)).thenReturn(false);

    List<BusStopEntity> expected = Arrays.asList(busStop1, busStop2, busStop3);

    // when
    List<BusStopEntity> actual = busStopService.readByBusLineId(1);
    verify(lineRouteRepositoryMock, times(1)).findAllByOrderBySequence();
    verify(busLineServiceMock, times(1)).readById(1);
    verify(busLineServiceMock, times(1)).notExistsById(1);

    // then
    assertThat(actual, is(expected));
    verifyNoMoreInteractions(lineRouteRepositoryMock, busLineServiceMock);
  }
}