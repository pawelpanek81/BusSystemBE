package pl.bussystem.domain.lineroute.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.bussystem.domain.busline.busline.persistence.entity.BusLineEntity;
import pl.bussystem.domain.busline.lineroute.persistence.entity.LineRouteEntity;
import pl.bussystem.domain.busline.lineroute.persistence.repository.LineRouteRepository;
import pl.bussystem.domain.busline.lineroute.service.LineRouteService;
import pl.bussystem.domain.busline.lineroute.service.LineRouteServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LineRouteServiceImplTest {
  private LineRouteService lineRouteService;

  @Mock
  private LineRouteRepository lineRouteRepositoryMock;

  @Before
  public void setUp() {
    lineRouteService = new LineRouteServiceImpl(lineRouteRepositoryMock, null);
  }

  @Test
  public void readByBusLineIdShouldReturnLineRoutesByBusLineId() {
    //given
    LineRouteEntity lineRoute1 = new LineRouteEntity(
        1,
        new BusLineEntity(1, "bus line1", null, null, null),
        null, null, null
    );
    LineRouteEntity lineRoute2 = new LineRouteEntity(
        2,
        new BusLineEntity(1, "bus line2", null, null, null),
        null, null, null
    );
    LineRouteEntity lineRoute3 = new LineRouteEntity(
        3,
        new BusLineEntity(2, "bus line3", null, null, null),
        null, null, null
    );
    List<LineRouteEntity> lineRoutes = Arrays.asList(lineRoute1, lineRoute2, lineRoute3);

    when(lineRouteRepositoryMock.findAll()).thenReturn(lineRoutes);

    List<LineRouteEntity> expected = new ArrayList<>(Arrays.asList(lineRoute1, lineRoute2));

    //when
    List<LineRouteEntity> actual = lineRouteService.readByBusLineId(1);
    verify(lineRouteRepositoryMock, times(1)).findAll();

    //then
    assertEquals("Lists have different size", expected.size(), actual.size());
    assertThat(actual, is(expected));
    verifyNoMoreInteractions(lineRouteRepositoryMock);
  }
}