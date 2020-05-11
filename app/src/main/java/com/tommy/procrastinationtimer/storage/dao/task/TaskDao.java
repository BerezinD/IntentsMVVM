package com.tommy.procrastinationtimer.storage.dao.task;

import androidx.room.*;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM taskdb")
    List<TaskDb> getAll();

    @Query("SELECT * FROM taskdb WHERE uid = :uid")
    TaskDb getById(long uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(TaskDb... taskDbs);

    @Delete
    void delete(TaskDb taskDb);

    @Query("DELETE FROM taskdb")
    void deleteAll();
}
