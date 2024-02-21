import com.example.kbackend.model.UserPrincipal
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


interface TokenService {

    fun generateRefreshToken(userPrincipal: UserPrincipal): String

    fun generateAccessToken(userPrincipal: UserPrincipal): String

    fun extractEmail(token: String): String?

    fun isExpired(token: String): Boolean

    fun createContext(token: String): UserPrincipal
}