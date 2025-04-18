package org.loop.troop.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * The interface Book repository.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

}
