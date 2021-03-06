package com.artyomefimov.expensescontrol.workmanager

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.artyomefimov.expensescontrol.data.db.DB_NAME
import com.artyomefimov.expensescontrol.data.db.ExpensesDao
import com.artyomefimov.expensescontrol.data.db.ExpensesDb
import com.artyomefimov.expensescontrol.di.DbModule
import com.artyomefimov.expensescontrol.infrastructure.CheckTodayExpensesWorker
import com.artyomefimov.expensescontrol.room.TestUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.Executors.newSingleThreadExecutor
import javax.inject.Inject
import javax.inject.Singleton

@UninstallModules(DbModule::class)
@HiltAndroidTest
class CheckTodayExpensesWorkerTest {

    @Module
    @InstallIn(SingletonComponent::class)
    class FakeDbModule {

        @Provides
        @Singleton
        fun provideFakeDb(
            @ApplicationContext context: Context
        ): ExpensesDb = Room.inMemoryDatabaseBuilder(
            context,
            ExpensesDb::class.java
        ).build()
    }

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var context: Context
    private lateinit var dao: ExpensesDao

    @Inject
    lateinit var db: ExpensesDb
    @Inject
    lateinit var workerFactory: WorkerFactory
    private val executor = newSingleThreadExecutor()
    private val dispatcher = executor.asCoroutineDispatcher()

    @Before
    fun setup() {
        hiltRule.inject()
        context = ApplicationProvider.getApplicationContext()
        dao = db.expensesDao()
        val config = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setExecutor(executor)
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun notificationIsNotShownIfThereAreExpensesForToday() = runBlocking(context = dispatcher) {
        val request = CheckTodayExpensesWorker.buildWorkRequest()
        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)!!

        dao.addExpense(entity = TestUtil.createEntityWithTimestamp(Clock.System.now()))
        workManager.enqueue(request).result.get()
        testDriver.setInitialDelayMet(request.id)
        delay(1000L)
        val workInfo = workManager.getWorkInfoById(request.id).get()


        assertEquals(WorkInfo.State.ENQUEUED, workInfo.state)
    }
}
