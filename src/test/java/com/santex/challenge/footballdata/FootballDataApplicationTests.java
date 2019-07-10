package com.santex.challenge.footballdata;

import com.santex.challenge.footballdata.bean.Wrapper;
import com.santex.challenge.footballdata.domain.Competition;
import com.santex.challenge.footballdata.service.CompetitionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FootballDataApplicationTests {

	private Competition competition;
	private CompetitionServiceImpl competitionService;
	private Wrapper<Competition> wrapperCompetition;

	@Before
	public void setupMock() {
		competition = new Competition(52802, "UEFA Champions League", "Europe", "CL");
		wrapperCompetition = new Wrapper<>(competition);
		competitionService = mock(CompetitionServiceImpl.class);
		when(competitionService.importCode("CL")).thenReturn(wrapperCompetition);
	}

	@Test
	public void same_object() {
		assertEquals(competition, wrapperCompetition.getT());
	}

	@Test
	public void same_object_and_verify_call_method(){
		assertEquals(competitionService.importCode("CL"), wrapperCompetition);
		verify(competitionService, times(1)).importCode("CL");
	}


}
