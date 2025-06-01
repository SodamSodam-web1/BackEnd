package goormton.backend.sodamsodam.domain.place.repository;

import goormton.backend.sodamsodam.domain.place.entity.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import goormton.backend.sodamsodam.domain.user.entity.User;
import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findAllByUser(User user);
} 