package com.plcoding.stockmarketapp.data.mapper

import com.plcoding.stockmarketapp.data.repository.local.CompanyListingEntity
import com.plcoding.stockmarketapp.data.repository.remote.dto.CompanyInfoDto
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing =
    CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity =
    CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo =
    CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )