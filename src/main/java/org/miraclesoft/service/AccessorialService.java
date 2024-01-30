package org.miraclesoft.service;

import org.miraclesoft.domain.Accessorials;
import org.miraclesoft.repository.AccessorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessorialService {
    private final AccessorialRepository accessorialRepository;

    @Autowired
    public AccessorialService(AccessorialRepository accessorialRepository) {
        this.accessorialRepository = accessorialRepository;
    }

    public List<Accessorials> getAllAccessorials() {
        return accessorialRepository.findAll();
    }

    public Accessorials getAccessorialById(String id) {
        return accessorialRepository.findById(id).orElse(null);
    }

    public Accessorials saveAccessorial(Accessorials accessorial) {
        return accessorialRepository.save(accessorial);
    }

    public String deleteAccessorial(String id) {
        if (accessorialRepository.existsById(id)) {
            accessorialRepository.deleteById(id);
            return id;
        } else {
            return null;
        }
    }
}
