package spring.demo;


import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Validate;
import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.context.WebApplicationContext;
import spring.demo.controller.AlbumController;
import spring.demo.controller.ArtistController;
import spring.demo.controller.LoginController;
import spring.demo.controller.RegistrationController;
import spring.demo.dao.AlbumDao;
import spring.demo.dao.ArtistDao;
import spring.demo.entity.Album;
import spring.demo.entity.Artist;
import spring.demo.entity.User;
import spring.demo.service.AlbumService;
import spring.demo.service.ArtistService;
import spring.demo.service.UserService;
import spring.demo.user.CrmUser;

import javax.validation.Valid;
import java.util.ArrayList;
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
	@Autowired
	private UserService userService;
	@MockBean
	private ArtistDao artistDao;
	@Autowired
	private WebApplicationContext wac;
	@Mock
	private Model model;
	@Mock
	BindingResult bResult;
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
//		artistDao=new ArtistDaoImpl(sessionFactory);
//		artistService = new ArtistServiceImpl(artistDao);
//		AlbumController albumController=new AlbumController(albumService,artistService);
//		String actual=albumController.showFormForAdd(id,model);
//		String expected="add-album-form";
//		Assertions.assertEquals(expected,actual);
//		AlbumController albumController=new AlbumController(albumService,artistService);
//		String actual=albumController.showFormForAdd(2,model);
//		String expected="add-album-form";
//		Assertions.assertEquals(expected,actual);
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/showFormForAdd").queryParam("artistId","2")).andExpect(status().isOk());


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void updateForm() throws Exception {
		int id =2;
		Album album = albumService.getAlbum(2);//new Album("The Album","Korean", albumdao.getArtist(id));
//		albumdao=new AlbumDaoImpl(sessionFactory);
//		albumService = new AlbumServiceImpl(albumdao);
//		AlbumController albumController=new AlbumController(albumService,artistService);
//		String actual=albumController.showFormForUpdate(id,model);
//		String expected="album-form";
//		Assertions.assertEquals(expected,actual);
		when(albumService.getAlbum(id)).thenReturn(album);

		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/album/showFormForAdd").queryParam("albumId","2")).andExpect(status().is(400));


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void addFormError() throws Exception {
		int id =-1;
		AlbumController albumController=new AlbumController(albumService,artistService);
		String actual=albumController.showFormForAdd(id,model);
		String expected="undefined";
		Assertions.assertEquals(expected,actual);
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/album/showFormForAdd").queryParam("artistId","-1")).andExpect(status().isOk());


	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void updateFormError() throws Exception {
		int id =40;
		AlbumController albumController=new AlbumController(albumService,artistService);
		String actual=albumController.showFormForUpdate(id,model);
		String expected="undefined";
		Assertions.assertEquals(expected,actual);

//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/album/showFormForUpdate").queryParam("albumId","40")).andExpect(status().is(200));


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
		AlbumController albumController=new AlbumController(albumService,artistService);
		String actual=albumController.delete(id);
		String expected="redirect:/album/list";
		Assertions.assertEquals(expected,actual);
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/album/delete").queryParam("id","16"))
//				.andExpect(status().is(400));
	}

	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void updateAlbum() throws Exception {
		int id =8;
		AlbumController albumController=new AlbumController(albumService,artistService);
		Artist artist=new Artist("Blackpink");
		artist.setId(2);
		Album album = new Album("SquareOne","Korean",122,artist);
		String actual=albumController.saveAlbum(album,bResult,id,model);
		String expected="redirect:/album/list";
//		when(albumService.getAlbum(id)).thenReturn( album);
//
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/album/save").queryParam("thAlbum","album")).andExpect(status().is(405));
	}
	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void addAlbum() throws Exception {
		int id =8;
		AlbumController albumController=new AlbumController(albumService,artistService);
		List<Album> list=new ArrayList();
		//list.add(new Album(""))
		Artist artist=new Artist("Ava Max",list);
		Album album = new Album("Hell And Heaven","English",140,artist);
//		when(albumdao.addAlbum(album)).thenReturn(Optional.of(album));

        String actual=albumController.addAlbum(album,bResult,11,model);
		String expected="redirect:/album/list";
		Assertions.assertEquals(expected,actual);
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/album/save").queryParam("thAlbum","album")).andExpect(status().is(405));
	}
	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void updateAlbumError() throws Exception {
		int id =8;
		AlbumController albumController=new AlbumController(albumService,artistService);
		Artist artist=new Artist("Blackpink");
		artist.setId(2);
		Album album = new Album("","",122,artist);
		String actual=albumController.saveAlbum(album,bResult,id,model);
		String expected="UpdateAlbumError-form";
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
		ArtistController artistController = new ArtistController(artistService);
		String actual = artistController.showFormForUpdate(2,model);
		String expected = "artist-form";
		Assertions.assertEquals(expected,actual);
//		Artist artist = artistService.getArtist(2);//new Album("The Album","Korean", albumdao.getArtist(id));
//		when(artistService.getArtist(id)).thenReturn(artist);
//
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/artist/showFormForAdd").queryParam("artistId","2")).andExpect(status().isOk());


	}

	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void searchArtist() throws Exception {
		String key="a";
		ArtistController artistController=new ArtistController(artistService);
		String actual=artistController.search(key,model);
		String expected="list-artists";
		Assertions.assertEquals(expected,actual);
//		List<Artist> artists=artistDao.searchBy(key);
//		when(artistService.searchBy(key)).thenReturn(artists);
//
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/artist/search").queryParam("searchName","a")).andExpect(status().is(400));

	}
	@Test
	@WithMockUser(username = "mary" , authorities = {"ROLE_EMPLOYEE"})
	void searchAlbum() throws Exception {
		String key="a";
		AlbumController albumController=new AlbumController(albumService,artistService);
		String actual=albumController.search(key,model);
		String expected="list-albums";
		Assertions.assertEquals(expected,actual);
//		List<Album> albums=albumdao.searchBy(key);
//		when(albumService.searchBy(key)).thenReturn(albums);
//
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/album/search").queryParam("searchName","a")).andExpect(status().is(400));

	}

	@Test
	@WithMockUser(username = "susan" , authorities = {"ROLE_ADMIN"})
	void deleteArtist() throws Exception {
		int id=11;
		ArtistController artistController=new ArtistController(artistService);
		String expected="redirect:/artist/list";
		String actual=artistController.delete(id);
		Assertions.assertEquals(expected,actual);
//		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		mockMvc.perform(get("/artist/delete").queryParam("id","11"))
//				.andExpect(status().is(400));
	}
//	@Test
//	@WithMockUser(username = "susan" , authorities = {"ROLE_ADMIN"})
//	void getAlbumsArtist() throws Exception {
//		int id=3;
//		List<Album> albums=artistService.getAlbums(id);
//		System.out.println(albums);
//		Artist artist=artistService.getArtist(id);
//		System.out.println(artist.toString());
//		List<Album> albums1=artist.getAlbums();
//		Assertions.assertEquals(albums1,albums);
//	}
	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void updateArtist() throws Exception {
		int id =2;
		Artist artist =new Artist("abcdefg");
		artist.setId(40);
		ArtistController artistController = new ArtistController(artistService);
		String actual = artistController.saveArtist(artist,bResult,model);
		String expected = "redirect:/artist/list";
		Assertions.assertEquals(expected,actual);
	}
//	@Test
//	@WithMockUser(username = "mary", authorities = { "Admin"})
//	void updateArtistError() throws Exception {
//		int id =2;
//		Artist artist =new Artist("");
//		artist.setId(11);
//		bResult=mock(BindingResult.class);
//		ArtistController artistController = new ArtistController(artistService);
//		String actual = artistController.saveArtist(artist,bResult,model);
//		String expected = "ArtistSaveError-form";
//		Assertions.assertEquals(expected,actual);
//	}
	@Test
	@WithMockUser(username = "mary", authorities = { "Admin"})
	void addArtist() throws Exception {
		Artist artist=new Artist("NCT");
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		mockMvc.perform(get("/artist/save").queryParam("thArtist","artist")).andExpect(status().is(405));
	}
	@Test
	void setArtistAlbums() {
		Artist artist = new Artist("abcdefg");
		List<Album> alist=new ArrayList<>();
		List<Album> expected  =alist;
		artist.setAlbums(expected);
		Assertions.assertEquals(expected,artist.getAlbums());
	}
	@Test
	void setArtistId() {
		Artist artist = new Artist("abcdefg");
		int expected=15;
		artist.setId(expected);
		Assertions.assertEquals(expected,artist.getId());
	}
	@Test
	void setAlbumId() {
		Artist artist = new Artist("abcdefg");
		artist.setId(31);
		Album album= new Album("abcdefg","Spanish",123,artist);
		int expected=15;
		album.setId(expected);
		Assertions.assertEquals(expected,album.getId());
	}
	@Test
	void setAlbumTitle() {
		Artist artist = new Artist("abcdefg");
		artist.setId(31);
		Album album= new Album("abcdefg","Spanish",123,artist);
		String expected="xyz";
		album.setTitle(expected);
		Assertions.assertEquals(expected,album.getTitle());
	}
	@Test
	void setAlbumLang() {
		Artist artist = new Artist("abcdefg");
		artist.setId(31);
		Album album= new Album("abcdefg","Spanish",123,artist);
		String expected="french";
		album.setLanguage(expected);
		Assertions.assertEquals(expected,album.getLanguage());
	}
	@Test
	void setAlbumStock() {
		Artist artist = new Artist("abcdefg");
		artist.setId(31);
		Album album= new Album("abcdefg","Spanish",123,artist);
		int  expected=134;
		album.setStock(expected);
		Assertions.assertEquals(expected,album.getStock());
	}
	@Test
	void setAlbumArtist() {
		Artist artist = new Artist("abcdefg");
		artist.setId(31);
		Album album= new Album("abcdefg","Spanish",123,artist);
		Artist  expected=new Artist("abcdfg");
		album.setArtist(expected);
		Assertions.assertEquals(expected,album.getArtist());
	}
	@Test
	void registerUser() {
		RegistrationController registerController=new RegistrationController(userService);
		CrmUser user=new CrmUser("Evelynn", "fun123", "fun123", "Evelinn", "watson","evari@gmail.com");
		String actual= registerController.processRegistrationForm(user,bResult,model);
		String expected="registration-confirmation";
		Assertions.assertEquals(expected,actual);
	}
	@Test
	void registerUserError() {
		RegistrationController registerController=new RegistrationController(userService);
		CrmUser user=new CrmUser("mary", "fun123", "fun123", "Evelynn", "watson","evely@gmail.com");
		String actual= registerController.processRegistrationForm(user,bResult,model);
		String expected="registration-form";
		Assertions.assertEquals(expected,actual);
	}
	@Test
	void registerUserError2() {
		RegistrationController registerController=new RegistrationController(userService);
		CrmUser user=new CrmUser("", "fun", "fun123", "Evelynn", "watson","evely.com");
		bResult= mock(BindingResult.class);
		String actual= registerController.processRegistrationForm(user,bResult,model);
		String expected="registration-form";
		Assertions.assertEquals(expected,actual);
	}
	@Test
	void LoginPage() {
		RegistrationController registerController=new RegistrationController(userService);
		String actual= registerController.showMyLoginPage(model);
		String expected="registration-form";
		Assertions.assertEquals(expected,actual);
	}
	@Test
	void contextLoads() {
	}

}
