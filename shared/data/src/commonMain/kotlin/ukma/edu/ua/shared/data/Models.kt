package ukma.edu.ua.shared.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.datetime.Instant

@Serializable
public data class Todo(
    @SerialName("id") val id: Int,
    @SerialName("title") val title: String,
    @SerialName("completed") val completed: Boolean,
    @SerialName("created_date") val created: Instant,
    @SerialName("updated_date") val updated: Instant
)

@Serializable
public data class CreateTodo(
    @SerialName("title") val title: String,
    @SerialName("completed") val completed: Boolean
)

@Serializable
public data class UpdateTodo(
    @SerialName("title") val title: String,
    @SerialName("completed") val completed: Boolean
)

