package com.pinatafarms.app.di

import coil.annotation.ExperimentalCoilApi
import com.pinatafarms.app.personlist.PersonListViewModel
import com.pinatafarms.app.utils.CoilCustomLruCacheInterceptor
import com.pinatafarms.data.AppDispatchers
import com.pinatafarms.data.utils.KotlinSerialization
import com.pinatafarms.data.repositories.PersonRepositoryImpl
import com.pinatafarms.data.source.image.PersonBitmapLruCacheDataSource
import com.pinatafarms.data.source.image.PersonBitmapLruCacheDataSourceImpl
import com.pinatafarms.data.source.person.PersonDataSource
import com.pinatafarms.data.source.person.PersonFaceDetectionDataSource
import com.pinatafarms.data.source.person.PersonFaceDetectionDataSourceImpl
import com.pinatafarms.data.source.person.PersonLocalDataSource
import com.pinatafarms.domain.repositories.PersonRepository
import com.pinatafarms.domain.usecases.PersonListReceiveUseCase
import com.pinatafarms.domain.usecases.PersonListReceiveUseCaseImpl
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCasesModule = module {
    single<PersonListReceiveUseCase> { PersonListReceiveUseCaseImpl(get()) }
}

val repositoriesModule = module {
    single<PersonRepository> { PersonRepositoryImpl(get(), get(), get()) }
}

val dataSourcesModule = module {
    single<PersonDataSource> { PersonLocalDataSource(androidContext(), get()) }
    single<PersonFaceDetectionDataSource> { PersonFaceDetectionDataSourceImpl(get(), get()) }
    single<PersonBitmapLruCacheDataSource> { PersonBitmapLruCacheDataSourceImpl(androidContext(), get()) }
}

val viewModelsModule = module {
    viewModel { PersonListViewModel(get()) }
}

@ExperimentalCoilApi
val appModule = module {
    single { AppDispatchers() }
    single<KotlinSerialization> { Json }
    single { CoilCustomLruCacheInterceptor(androidContext(), get()) }
}