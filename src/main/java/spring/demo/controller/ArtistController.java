package spring.demo.controller;

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

@Controller
@RequestMapping("/artist")
public class ArtistController {
    private ArtistService artistService;

    public ArtistController(ArtistService theArtistService) {
        artistService = theArtistService;
    }


    @GetMapping(value="/list")
    public String listArtists(Model theModel) {
        List<Artist> theArtists = artistService.findAll();
        theModel.addAttribute("artists", theArtists);

        return "list-artists";
    }
    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Artist theArtist=new Artist();

        theModel.addAttribute("artist", theArtist);

        return "artist-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("artistId") int theId,
                                    Model theModel) {
        Artist theArtist= artistService.getArtist(theId);
        theModel.addAttribute("artist", theArtist);
        return "artist-form";
    }


    @PostMapping("/save")
    public String saveArtist(@Valid @ModelAttribute("artist") Artist theArtist, BindingResult result,Model theModel) {
        System.out.println(theArtist.toString());
        if(result.hasErrors()){
            theModel.addAttribute("artist",theArtist);
            return "ArtistSaveError-form";
        }
        artistService.addArtist(theArtist);
        return "redirect:/artist/list";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("artistId") int theId) {
        artistService.delete(theId);
        return "redirect:/artist/list";

    }

    @GetMapping("/search")
    public String search(@RequestParam("artistName") String theName,
                         Model theModel) {
        List<Artist> theArtists = artistService.searchBy(theName);
        theModel.addAttribute("artists", theArtists);
        theModel.addAttribute("searchWord", theName);
        return "list-artists";

    }
}
