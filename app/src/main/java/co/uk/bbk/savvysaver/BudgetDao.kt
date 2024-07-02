package co.uk.bbk.savvysaver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query



@Dao
    interface BudgetDao {

        @Query("SELECT * FROM budget WHERE id = 1")
        suspend fun getBudget(): Budget?

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertBudget(budget: Budget)



    }
