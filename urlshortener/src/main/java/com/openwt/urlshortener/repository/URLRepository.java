package com.openwt.urlshortener.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.openwt.urlshortener.model.URLModel;



@Repository
public interface URLRepository extends JpaRepository<URLModel, Long>{
	

	Optional<URLModel> findById(long id);
	
	List<URLModel> findAllByOrderByDateCreationAsc();
	
	@Query("select u from URLModel u where u.dateCreation < :oneMonthAgo")
	List<URLModel> findExpiredURLs(@Param("oneMonthAgo") Date oneMonthAgo);
	
	URLModel findTopByLongUrl(@Param("longUrl") String longUrl);
	
	@Modifying
    @Query(value = "UPDATE URLModel u SET u.enabled = '0' WHERE u.dateCreation < :oneMonthAgo AND u.enabled = '1'")
	int desactivateExpiredURLs(@Param("oneMonthAgo") Date oneMonthAgo);
}
