package com.bhg.posServer.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bhg.posServer.dao.AdvertisementCinemaMovieStatisticDao;
import com.bhg.posServer.po.AdvertisementCinemaMovieStatistic;

@Service
public class AdvertisementCinemaMovieStatisticService {

	@Autowired
	private AdvertisementCinemaMovieStatisticDao dao;

	@Transactional
	public void saveAdvertisementCinemaMovies(Collection<AdvertisementCinemaMovieStatistic> list) {
		int i = 0;
		for (AdvertisementCinemaMovieStatistic advertisementAreaVisitStatistic : list) {
			dao.create(advertisementAreaVisitStatistic);
			if (++i % 500 == 0) {
				System.out.println("saveAdvertisementCinemaMovies have process .." + i);
			}
		}
	}
}
