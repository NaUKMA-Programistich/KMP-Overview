package ukma.edu.ua.server.spring

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import jakarta.persistence.*
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ukma.edu.ua.shared.data.DatabaseConfig
import java.time.Instant
import javax.sql.DataSource

@Entity
@Table(name = "TODOS")
data class TodoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Int = 0,

    @Column(name  = "TITLE", nullable = false, length = 150)
    var title: String,

    @Column(name = "COMPLETED", nullable = false)
    var completed: Boolean,

    @Column(name = "CREATED_DATE", nullable = false, updatable = false)
    val created: Instant = Instant.now(),

    @Column(name = "UPDATED_DATE", nullable = false)
    var updated: Instant = Instant.now()
)

@Repository
interface TodoDatabase: JpaRepository<TodoEntity, Int>

@Configuration
class DataSourceConfig {
    @Bean
    fun dataSource(): DataSource =
        DataSourceBuilder.create()
            .driverClassName(DatabaseConfig.DRIVER)
            .url(DatabaseConfig.URL)
            .username(DatabaseConfig.USER)
            .password(DatabaseConfig.PASSWORD)
            .build()
}
