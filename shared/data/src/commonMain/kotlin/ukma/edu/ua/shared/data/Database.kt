package ukma.edu.ua.shared.data

public object DatabaseConfig {
    public const val URL: String = "jdbc:h2:file:./db/ukma_db;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1"
    public const val DRIVER: String = "org.h2.Driver"
    public const val USER: String = "root"
    public const val PASSWORD: String = ""
}