package com.bhg.posServer.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.bhg.posServer.po.AdvertisementCinemaMovieStatistic;

@Repository()
public class AdvertisementCinemaMovieStatisticDao extends BaseDao<AdvertisementCinemaMovieStatistic> {
	
	@SuppressWarnings("unchecked")
	public AdvertisementCinemaMovieStatistic getAdvertisementAreaVisitStatistic(AdvertisementCinemaMovieStatistic advertisementCinemaMovieStatistic){
		String hql = "from AdvertisementCinemaMovieStatistic a where a.time = :time and a.advertisementUuid = :advertisementUuid " +
				"and a.cinemaId = :cinemaId  and a.movieId = :movieId ";
		Query query = entityManager.createQuery(hql);
		query.setParameter("time", advertisementCinemaMovieStatistic.getTime());
		query.setParameter("advertisementUuid",advertisementCinemaMovieStatistic.getAdvertisementUuid());
		query.setParameter("cinemaId", advertisementCinemaMovieStatistic.getCinemaId());
        query.setParameter("movieId", advertisementCinemaMovieStatistic.getMovieId());
		query.setMaxResults(1);
		List<AdvertisementCinemaMovieStatistic> result = query.getResultList();
		return result.isEmpty() ? null : result.get(0);
	}
	
}
