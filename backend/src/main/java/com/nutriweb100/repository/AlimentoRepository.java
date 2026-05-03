package com.nutriweb100.repository;

import com.nutriweb100.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    //para consultar los alimentos según la categoría
    @Query(value = "SELECT * FROM alimentos WHERE :categoria = ANY(categoria)", nativeQuery = true)
    List<Alimento> findByCategoria(@Param("categoria") String categoria);
}
