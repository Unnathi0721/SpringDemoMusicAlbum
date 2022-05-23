package spring.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;
import spring.demo.controller.ArtistController;
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
	@Mock
	private Model model;
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
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void addForm() throws Exception {
		int id =2;
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/showFormForAdd").queryParam("artistId","2")).andExpect(status().isOk());


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void updateForm() throws Exception {
		int id =2;
		Album album = albumService.getAlbum(2);//new Album("The Album","Korean", albumdao.getArtist(id));
		when(albumService.getAlbum(id)).thenReturn(album);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/showFormForAdd").queryParam("albumId","2")).andExpect(status().is(400));


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void addFormError() throws Exception {
		int id =-1;
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/showFormForAdd").queryParam("artistId","-1")).andExpect(status().isOk());


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void updateFormError() throws Exception {
		int id =40;


		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/showFormForUpdate").queryParam("albumId","40")).andExpect(status().is(200));


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void search() throws Exception {
		String key="a";
		List<Album> albums=albumdao.searchBy(key);
		when(albumService.searchBy(key)).thenReturn(albums);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/search").queryParam("searchName","a")).andExpect(status().is(400));

    }

	@Test
	@WithMockUser(username = "susan" , authorities = {"ROLE_ADMIN"})
	void deleteAlbum() throws Exception {
		int id=16;
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/delete").queryParam("id","16"))
				.andExpect(status().is(400));
	}

	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void updateAlbum() throws Exception {
		int id =8;
		Album album = new Album("SquareOne","Korean",albumdao.getArtist(id));
		when(albumService.getAlbum(id)).thenReturn( album);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/save").queryParam("thAlbum","album")).andExpect(status().is(405));
	}
	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void addAlbum() throws Exception {
		int id =8;
		Album album = new Album("Hell And Heaven","English",artistDao.getArtist(11));
//		when(albumdao.addAlbum(album)).thenReturn(Optional.of(album));

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/save").queryParam("thAlbum","album")).andExpect(status().is(405));
	}

	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void addArtistForm() throws Exception {
		//Model model=new model();
		ArtistController artistController = new ArtistController(artistService);
		String actual = artistController.showFormForAdd(model);
		String expected = "artist-form";
		Assertions.assertEquals(expected,actual);


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void updateArtistForm() throws Exception {
		int id =2;
		Artist artist = artistService.getArtist(2);//new Album("The Album","Korean", albumdao.getArtist(id));
		when(artistService.getArtist(id)).thenReturn(artist);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/artist/showFormForAdd").queryParam("artistId","2")).andExpect(status().isOk());


	}

	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void searchArtist() throws Exception {
		String key="a";
		List<Artist> artists=artistDao.searchBy(key);
		when(artistService.searchBy(key)).thenReturn(artists);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/artist/search").queryParam("searchName","a")).andExpect(status().is(400));

	}

	@Test
	@WithMockUser(username = "susan" , authorities = {"ROLE_ADMIN"})
	void deleteArtist() throws Exception {
		int id=11;
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/artist/delete").queryParam("id","11"))
				.andExpect(status().is(400));
	}

	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void updateArtist() throws Exception {
		int id =2;
		Artist artist =new Artist("BlackPink");
		//artist=artistService.getArtist(2);
		artist.setArtist("Black Pink");
		artistService.addArtist(artist);
		when(artistService.getArtist(id)).thenReturn(artist);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/artist/save").queryParam("thArtist","artist")).andExpect(status().is(405));
	}
	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void addArtist() throws Exception {
		//int id =8;
		Artist artist=new Artist("NCT");
		//when(artistService.addArtist(artist)).thenReturn( new Artist("NCT"));
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/artist/save").queryParam("thArtist","artist")).andExpect(status().is(405));
	}
	@Test
	void contextLoads() {
	}

}
