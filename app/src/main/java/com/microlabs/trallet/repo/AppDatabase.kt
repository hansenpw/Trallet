package com.microlabs.trallet.repo

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.microlabs.trallet.R
import com.microlabs.trallet.model.Book
import com.microlabs.trallet.model.Category
import com.microlabs.trallet.model.Currency
import com.microlabs.trallet.model.Expense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Book::class, Expense::class, Currency::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao

    abstract fun expenseDao(): ExpenseDao

    abstract fun currencyDao(): CurrencyDao

    abstract fun categoryDao(): CategoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            buildDatabase(context).also {
                INSTANCE = it
                Log.i("appdatabase", "set instance")
            }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "TralletDatabase")
//                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                Log.i("trallet appdatabase", "oncreate callback")
                                GlobalScope.launch(Dispatchers.IO) {
                                    Log.i("trallet appdatabase", "start execute callback")
                                    val category1 = Category(name = "Foods", imgId = R.drawable.ic_local_dining_black_24dp)
                                    val category2 = Category(name = "Transport", imgId = R.drawable.ic_local_taxi_black_24dp)
                                    val category3 = Category(name = "Shop", imgId = R.drawable.ic_shopping_basket_black_24dp)
                                    val category4 = Category(name = "Others", imgId = R.drawable.ic_local_atm_black_24dp)
                                    val appDatabase = AppDatabase.getInstance(context)
                                    appDatabase.categoryDao().insertCategory(category1, category2, category3, category4)
                                    val currency1 = Currency(name = "IDR Rp", value = 1.0)
                                    val currency2 = Currency(name = "USD $", value = 14800.0)
                                    appDatabase.currencyDao().insertCurrency(currency1, currency2)
                                    Log.i("trallet appdatabase", "done execute callback")
                                }
                            }
                        })
                        .build()
    }
}