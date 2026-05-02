package com.nutriweb100.repository;

import com.nutriweb100.model.RegistroNutricional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<RegistroNutricional, Long> {
}
