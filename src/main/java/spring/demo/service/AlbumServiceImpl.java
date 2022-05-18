package spring.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.demo.dao.AlbumDao;
import spring.demo.entity.Album;
import spring.demo.entity.Artist;

import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService{
    @Autowired
    private AlbumDao albumDao;
    @Override
    public void addAlbum(Album album) {

        albumDao.addAlbum(album);
    }

    @Override
    public void delete(int id) {
       albumDao.delete(id);
    }

    @Override
    public Album getAlbum(int id) {
        return albumDao.getAlbum(id);
    }

    @Override
    public Artist getArtist(int id) {
        return albumDao.getArtist(id);
    }

    @Override
    public List<Album> findAll() {
        return albumDao.findAll();
    }

    @Override
    public List<Album> searchBy(String theName) {
        return albumDao.searchBy(theName);
    }
}
