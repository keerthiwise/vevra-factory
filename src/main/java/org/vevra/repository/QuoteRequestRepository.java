package org.vevra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vevra.entity.QuoteRequest;

public interface QuoteRequestRepository extends JpaRepository<QuoteRequest, Long> {
}
