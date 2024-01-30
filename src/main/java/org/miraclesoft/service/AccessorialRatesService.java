package org.miraclesoft.service;

import org.miraclesoft.domain.AccessorialRateId;
import org.miraclesoft.domain.AccessorialRates;
import org.miraclesoft.repository.AccessorialRatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessorialRatesService {
    private final AccessorialRatesRepository accessorialRatesRepository;

    @Autowired
    public AccessorialRatesService(AccessorialRatesRepository accessorialRatesRepository) {
        this.accessorialRatesRepository = accessorialRatesRepository;
    }

    public List<AccessorialRates> getAllAccessorialRates() {
        return accessorialRatesRepository.findAll();
    }

    public AccessorialRates getAccessorialRateById(AccessorialRateId id) {
        return accessorialRatesRepository.findById(id).orElse(null);
    }

    public AccessorialRates saveAccessorialRate(AccessorialRates accessorialRate) {
        return accessorialRatesRepository.save(accessorialRate);
    }

    public AccessorialRateId deleteAccessorialRate(AccessorialRateId id) {
        if (accessorialRatesRepository.existsById(id)) {
            accessorialRatesRepository.deleteById(id);
            return id;
        } else {
            return null;
        }
    }
}
