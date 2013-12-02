package org.pizzashack.data.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends
		JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {

	/**
	 * Deletes an entity
	 * 
	 * @param id
	 *            The id of the deleted entity.
	 * @return The deleted entity
	 * @throws NotFoundException
	 *             if an entity is not found with the given id.'
	 */
//	public T deleteById(ID id) throws NotFoundException;
}
