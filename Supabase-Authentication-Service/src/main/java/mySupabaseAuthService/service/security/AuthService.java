package mySupabaseAuthService.service.security;

import mySupabaseAuthService.model.User;

@Service
@Slf4j
public class AuthService {
    private final SupabaseClient supabaseClient;

    @Autowired
    public AuthService(SupabaseClient supabaseClient) {
        this.supabaseClient = supabaseClient;
    }

    public User signup(String email, String password, String fullName) {
        try {
            UserResponse response = supabaseClient.auth.signUp(email, password);
            User user = new User();
            user.setId(response.getUser().getId());
            user.setEmail(email);
            user.setFullName(fullName);
            user.setRoles(Collections.singletonList("USER"));

            // Store additional user data in Supabase
            supabaseClient.from("users")
                    .insert(user)
                    .execute();

            return user;
        } catch (Exception e) {
            log.error("Error during signup: ", e);
            throw new RuntimeException("Signup failed");
        }
    }

    public User login(String email, String password) {
        try {
            UserResponse response = supabaseClient.auth.signIn(email, password);
            return supabaseClient.from("users")
                    .select()
                    .eq("id", response.getUser().getId())
                    .single()
                    .executeAndGetSingle(User.class);
        } catch (Exception e) {
            log.error("Error during login: ", e);
            throw new RuntimeException("Login failed");
        }
    }
}