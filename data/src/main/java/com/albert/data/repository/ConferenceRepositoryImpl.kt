package com.albert.data.repository

import com.albert.data.ConferenceApi
import com.albert.data.ConferenceRepository
import com.albert.data.cache.LocalCacheProvider
import com.albert.shared.model.Event
import com.albert.shared.model.Session
import com.albert.shared.model.Sponsor
import javax.inject.Inject

class ConferenceRepositoryImpl @Inject constructor(
    private val conferenceApi: ConferenceApi,
    private val localCacheProvider: LocalCacheProvider
) : ConferenceRepository {
    override suspend fun getEventHistory(): List<Event> {
        return runCatching {
            conferenceApi.getEventHistory()
        }.getOrDefault(localCacheProvider.getEventHistory())
    }

    override suspend fun getSessions(): List<Session> {
        return kotlin.runCatching {
            conferenceApi.getSessions()
        }.getOrDefault(localCacheProvider.getSessions())
    }

    override suspend fun getSponsors(): List<Sponsor> {
        return runCatching {
            conferenceApi.getSponsors()
        }.getOrDefault(localCacheProvider.getSponsors())
    }
}