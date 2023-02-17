package com.example.mvvmwithdaggerhilt.data.database
import android.content.Context
import androidx.room.Database;
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mvvmwithdaggerhilt.api.RemoteKeyDao
import com.example.mvvmwithdaggerhilt.api.UserDao
import com.example.mvvmwithdaggerhilt.api.UserDataListDao
import com.example.mvvmwithdaggerhilt.data.RemoteKeys
import com.example.mvvmwithdaggerhilt.demo.PersonDao
import com.example.mvvmwithdaggerhilt.demo.PersonModel
import com.example.mvvmwithdaggerhilt.demo.TypeConverter
import com.example.mvvmwithdaggerhilt.ui.model.Data
import com.example.mvvmwithdaggerhilt.ui.model.UserRequest
import com.example.mvvmwithdaggerhilt.utils.Constant

@Database(version = 6, exportSchema = false, entities = [PersonModel::class, UserRequest::class, Data::class, RemoteKeys::class])
@TypeConverters(TypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    companion object{
        private var INSTANCE : AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context = context,
                        klass = AppDatabase::class.java,
                        name = Constant.databaseName
                    ).fallbackToDestructiveMigration()
                     .build()
                }
            }
            return INSTANCE!!
        }
    }
abstract fun personDao(): PersonDao
abstract fun userDao(): UserDao
abstract fun userDataListDao(): UserDataListDao
abstract fun remoteKeyDao(): RemoteKeyDao
}