package spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import spring.demo.entity.Album;
import spring.demo.entity.Artist;
import spring.demo.service.AlbumService;
import spring.demo.service.ArtistService;

import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@Controller
@RequestMapping("/album")
public class AlbumController {
    private Logger myLogger = Logger.getLogger(getClass().getName());
    private AlbumService albumService;
    @Autowired
    private ArtistService artistService;
    public AlbumController(AlbumService theAlbumService,ArtistService theArtistService) {
        albumService = theAlbumService;
        artistService = theArtistService;
    }

    @GetMapping(value="/list")
    public String listAlbums(Model theModel) {

        List<Album> theAlbums = albumService.findAll();

        theModel.addAttribute("albums", theAlbums);

        return "list-albums";
    }
    @GetMapping("/showFormForAdd")
    public String showFormForAdd( @RequestParam("artistId") int id, Model theModel) {

        if(artistService.getArtist(id)==null){
            theModel.addAttribute("artistId",id);
            return "undefined";
        }
        Album theAlbum = new Album();
        theModel.addAttribute("album", theAlbum);
        theModel.addAttribute("artistId",id);
        return "add-album-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("albumId") int theId,
                                    Model theModel) {
        if(albumService.getAlbum(theId)==null){
            theModel.addAttribute("artistId",theId);
            return "undefined";
        }
        Album theAlbum = albumService.getAlbum(theId);
        theModel.addAttribute("album", theAlbum);

        return "album-form";
    }

    @PostMapping("/save")
    public String saveAlbum(@Valid @ModelAttribute("album") Album theAlbum,BindingResult result,@RequestParam("artistId") int theId, Model theModel) {
        if(result.hasErrors()){
            theModel.addAttribute("artistId",theId);
            return "UpdateAlbumError-form";
        }
        myLogger.info("theAlbum.getId()");
        theAlbum.setArtist(artistService.getArtist(theId));
        albumService.addAlbum(theAlbum);
        return "redirect:/album/list";
    }
    @PostMapping("/add")
    public String addAlbum(@Valid @ModelAttribute("album") Album album, BindingResult result,@RequestParam("artistId") int theId, Model theModel) {//@ModelAttribute("album") Album theAlbum,

        if(result.hasErrors()){
            theModel.addAttribute("artistId",theId);
           return "AlbumAddError-form";
        }
        album.setArtist(artistService.getArtist(theId));
        albumService.addAlbum(album);
        return "redirect:/album/list";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("albumId") int theId) {

        albumService.delete(theId);
        return "redirect:/album/list";

    }

    @GetMapping("/search")
    public String search(@RequestParam("albumName") String theName,
                         Model theModel) {
        List<Album> theAlbums = albumService.searchBy(theName);
        theModel.addAttribute("albums", theAlbums);
        theModel.addAttribute("searchWord", theName);
        return "list-albums";

    }


}
