package spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.demo.dao.ArtistDao;
import spring.demo.entity.Album;
import spring.demo.entity.Artist;

import java.util.List;
@Service
public class ArtistServiceImpl implements ArtistService{
    @Autowired
    private ArtistDao artistDao;
    @Override
    public void addArtist(Artist artist) {
        artistDao.addArtist(artist);
    }

    @Override
    public void delete(int id) {
        artistDao.delete(id);
    }

    @Override
    public Artist getArtist(int id) {
        return artistDao.getArtist(id);
    }

    @Override
    public List<Album> getAlbums(int id) {
        return artistDao.getAlbums(id);
    }

    @Override
    public List<Artist> findAll() {
        return artistDao.findAll();
    }

    @Override
    public List<Artist> searchBy(String theName) {
        return artistDao.searchBy(theName);
    }
}
