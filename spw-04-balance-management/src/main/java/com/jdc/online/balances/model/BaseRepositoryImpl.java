package com.jdc.online.balances.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
	
	private EntityManager em;
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager); 
		this.em = entityManager;
	}

	@Override
	public <R> List<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		return em.createQuery(queryFunc.apply(em.getCriteriaBuilder())).getResultList();
	}

	@Override
	public <R> PageResult<R> search(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc,
			Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc, int page, int size) {
		
		var count = count(countFunc);
		var query = em.createQuery(queryFunc.apply(em.getCriteriaBuilder()));
		query.setMaxResults(size);
		query.setFirstResult(page * size);
		
		var list = query.getResultList();
		PageResult.Builder<R> builder = PageResult.builder();
		builder.contents(list);
		builder.count(count);
		builder.page(page);
		builder.size(size);
		
		return builder.build();
	}

	@Override
	public <R> Optional<R> searchOne(Function<CriteriaBuilder, CriteriaQuery<R>> queryFunc) {
		return em.createQuery(queryFunc.apply(em.getCriteriaBuilder()))
				.getResultList().stream().findAny();
	}

	@Override
	public Long count(Function<CriteriaBuilder, CriteriaQuery<Long>> queryFunc) {
		return em.createQuery(queryFunc.apply(em.getCriteriaBuilder()))
				.getSingleResult();
	}

}
