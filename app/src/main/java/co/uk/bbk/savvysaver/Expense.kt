package co.uk.bbk.savvysaver

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


//data class for the Expenses
@Entity (tableName= "expenses")
data class Expense (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val amount: Double,
    val date: String,
    val category: String

): Serializable

