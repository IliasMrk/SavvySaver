package co.uk.bbk.savvysaver

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget")
data class Budget(
    @PrimaryKey
    val id: Int = 1,
    val value: Double
)