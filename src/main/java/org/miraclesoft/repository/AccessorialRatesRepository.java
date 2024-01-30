package org.miraclesoft.repository;

import org.miraclesoft.domain.AccessorialRateId;
import org.miraclesoft.domain.AccessorialRates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessorialRatesRepository extends JpaRepository<AccessorialRates, AccessorialRateId> {
}
