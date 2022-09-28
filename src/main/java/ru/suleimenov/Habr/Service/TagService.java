package ru.suleimenov.Habr.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.suleimenov.Habr.Repository.TagRepo;
import ru.suleimenov.Habr.entity.Tag;

import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepo tagRepo;

    public void save(Tag tag){
        if (findByName(tag) == true){
            tagRepo.save(tag);
        }
    }
    public boolean findByName(Tag tag){
        if (tagRepo.findTagByName(tag.getName()).isPresent()){
            return false;
            //if tag with this name is present, we don't have to add it again
        }else return true;// if not, send true
    }
    public Tag findByNameAndGetTag(String tag){
        Optional<Tag> tag1 = tagRepo.findTagByName(tag);
        if (tag1.isPresent()){
            return tag1.get();
        }else return null;
    }
}
