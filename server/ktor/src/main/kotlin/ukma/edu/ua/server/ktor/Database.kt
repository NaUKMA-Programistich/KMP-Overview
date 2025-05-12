package ukma.edu.ua.server.ktor

import org.jetbrains.exposed.sql.Database
import ukma.edu.ua.shared.data.DatabaseConfig

fun configureDatabases(): Database {
    return Database.connect(
        url = DatabaseConfig.URL,
        driver = DatabaseConfig.DRIVER,
        user = DatabaseConfig.USER,
        password = DatabaseConfig.PASSWORD
    )
}
