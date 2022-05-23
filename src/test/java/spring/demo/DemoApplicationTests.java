package spring.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.demo.controller.LoginController;
import spring.demo.dao.AlbumDao;
import spring.demo.dao.ArtistDao;
import spring.demo.entity.Album;
import spring.demo.entity.Artist;
import spring.demo.entity.User;
import spring.demo.service.AlbumService;
import spring.demo.service.ArtistService;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {
	@Autowired
	private AlbumService albumService;
	@MockBean
    private AlbumDao albumdao;
	@Autowired
	private ArtistService artistService;
	@MockBean
	private ArtistDao artistDao;
	@Autowired
	private WebApplicationContext wac;

	@Test
	void loginPage()
	{
		LoginController loginController = new LoginController();
		String actual = loginController.showMyLoginPage();
		String expected = "login";
		Assertions.assertEquals(expected,actual);
	}

	@Test
	void accessPage()
	{
		LoginController loginController = new LoginController();
		String actual = loginController.showAccessDenied();
		String expected = "access-denied";
		Assertions.assertEquals(expected,actual);
	}

	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void EmployeeHomePage() throws Exception {
		LoginController loginController = new LoginController();
		String actual = loginController.showLogin();
		String expected = "home";
		Assertions.assertEquals(expected,actual);
	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_ADMIN"})
	void AdminHomePage() throws Exception {
		LoginController loginController = new LoginController();
		String actual = loginController.showLogin();
		String expected = "home";
		Assertions.assertEquals(expected,actual);
	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void getAlbumList() throws Exception {
		List<Album> albums=albumService.findAll();
		when(albumdao.findAll()).thenReturn(albums);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/list")).andExpect(status().is(200));
	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void getArtistList() throws Exception {
		List<Artist> artists=artistService.findAll();
		when(artistDao.findAll()).thenReturn(artists);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/artist/list")).andExpect(status().is(200));
	}
	@Test
	void contextLoads() {
	}

}
