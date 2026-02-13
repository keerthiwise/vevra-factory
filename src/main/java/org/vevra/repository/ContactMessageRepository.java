package org.vevra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vevra.entity.ContactMessage;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
