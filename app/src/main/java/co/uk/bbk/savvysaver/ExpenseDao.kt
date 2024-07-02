package co.uk.bbk.savvysaver



import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * DAO interface for Expense entity
 */
@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<Expense>

    @Query("SELECT category, SUM(amount) as total FROM expenses WHERE date >= :today GROUP BY category")
    suspend fun getTotalByCategory(today: String): List<CategoryTotal>

    @Query("SELECT * FROM expenses WHERE date >= :today")
    suspend fun getExpensesFromToday(today: String): List<Expense>

    @Query("SELECT * FROM expenses ORDER BY date ASC")
    suspend fun getExpensesByDate(): List<Expense>

    @Query("SELECT * FROM expenses WHERE id = :id")
    suspend fun getExpenseById(id: Int): Expense

    @Query("SELECT SUM(amount) FROM expenses")
    suspend fun getTotal(): Double

    @Insert
    suspend fun addExpense(expense: Expense)

    @Update
    suspend fun updateExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)



}