package i.part.app.course.todo.features.board.data

import androidx.room.Embedded
import androidx.room.Relation

class TodoSpecificationDto {

    @Embedded
    var todoEntity = TodoEntity(0, "", 0, listOf())

    @Relation(parentColumn = "id", entityColumn = "id", entity = TaskEntity::class)
    var tasks: List<TaskEntity> = listOf()

    fun toTodoSpecificationResponse() = TodoSpecificationResponse(
        todoEntity,
        tasks
    )
}