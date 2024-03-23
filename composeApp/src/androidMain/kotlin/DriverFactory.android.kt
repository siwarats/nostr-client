import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import app.database.Database
import org.example.project.MyApplication

class AndroidDriverFactory : DriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = Database.Schema,
            context = MyApplication.APPLICATION_CONTEXT,
            name = "test.db"
        )
    }
}

actual fun createDriver() = AndroidDriverFactory().createDriver()